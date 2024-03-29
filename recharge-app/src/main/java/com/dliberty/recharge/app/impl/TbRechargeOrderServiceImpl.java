package com.dliberty.recharge.app.impl;

import java.util.Date;

import com.dliberty.recharge.common.redis.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dliberty.recharge.api.service.IRechargeService;
import com.dliberty.recharge.api.service.ITbRechargeOrderService;
import com.dliberty.recharge.app.util.ConfigUtil;
import com.dliberty.recharge.app.util.DistributedLockUtil;
import com.dliberty.recharge.app.util.GeneratorCardInfoUtil;
import com.dliberty.recharge.common.lang.data.StringUtils;
import com.dliberty.recharge.common.utils.DateFormatUtils;
import com.dliberty.recharge.common.utils.EntityUtil;
import com.dliberty.recharge.common.utils.Md5;
import com.dliberty.recharge.common.vo.Response;
import com.dliberty.recharge.dao.mapper.TbRechargeCardMapper;
import com.dliberty.recharge.dao.mapper.TbRechargeOrderMapper;
import com.dliberty.recharge.dto.RechargeCallBackDto;
import com.dliberty.recharge.dto.RechargeOrderDto;
import com.dliberty.recharge.entity.TbRechargeCard;
import com.dliberty.recharge.entity.TbRechargeOrder;
import com.dliberty.recharge.vo.RechargeVo;
import com.dliberty.recharge.vo.conditions.RechargeOrderQueryVo;
import com.qianmi.open.api.response.BmRechargeMobileGetItemInfoResponse;
import com.qianmi.open.api.response.BmRechargeMobilePayBillResponse;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author LG
 * @since 2019-11-07
 */
@Service
@Transactional
public class TbRechargeOrderServiceImpl extends ServiceImpl<TbRechargeOrderMapper, TbRechargeOrder>
		implements ITbRechargeOrderService {
	
	private static final Logger logger = LoggerFactory.getLogger(TbRechargeOrderServiceImpl.class);

	@Autowired
	private IRechargeService rechargeService;
	@Autowired
	private TbRechargeCardMapper tbRechargeCardMapper;
	@Autowired
	private RedisClient redisClient;

	@Override
	public Response<Boolean> rechargeOrder(RechargeVo rechargeVo) {

		if (DistributedLockUtil.tryLock(rechargeVo.getSecretKey(), 10L)) {
			try {
				// 校验数据合法性
				TbRechargeCard rechargeCard = null;
				boolean flag = false;
				String errorMessage = "参数有误";
				if (StringUtils.isNotEmpty(rechargeVo.getSecretKey())
						&& StringUtils.isNotEmpty(rechargeVo.getMobile())) {
					rechargeCard = tbRechargeCardMapper.selectOne(new QueryWrapper<TbRechargeCard>().eq("secret_key", rechargeVo.getSecretKey()).eq("is_deleted", 0));
					if (rechargeCard == null) {
						errorMessage = "密钥输入有误";
					} else if (rechargeCard.getIsUse() != 0) {
						errorMessage = "充值卡已使用";
					} else {
						// 验签
						String sign = Md5.MD5(rechargeCard.getCardNo() + rechargeCard.getSecretKey()
								+ rechargeCard.getMoney() + rechargeCard.getIsUse());
						if (sign.equals(rechargeVo.getSign())) {
							flag = true;
						} else {
							errorMessage = "数据篡改，验签失败";
						}
					}
				}
				if (!flag) {
					return Response.ofData(flag, errorMessage);
				}

				return submitOrder(rechargeCard , rechargeVo.getMobile() , null);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DistributedLockUtil.releaseLock(rechargeVo.getSecretKey());
			}
		} else {
			return Response.ofData(false, "充值卡已使用");
		}

		return Response.ofData(false,"充值失败");
	}

	@Override
	public String rechargeCallBack(RechargeCallBackDto callBackDto) {

		TbRechargeOrder order = baseMapper.selectOne(new QueryWrapper<TbRechargeOrder>().eq("three_order_no", callBackDto.getBillId()));
		if (order != null) {
			TbRechargeCard rechargeCard = tbRechargeCardMapper.selectById(order.getRechargeCardId());
			order.setUpdateTime(new Date());
			order.setAsyncCallbackJson(JSONObject.toJSONString(callBackDto));

			if ("1".equals(callBackDto.getRechargeState())) {
				order.setOrderStatus(1);
				redisClient.del(rechargeCard.getCardNo());
			} else {
				//充值失败
				order.setOrderStatus(3);
				Object o = redisClient.get(rechargeCard.getCardNo());
				if(o == null){
					redisClient.set(rechargeCard.getCardNo(),1);
					o = "1";
				}
				Integer num = Integer.valueOf(String.valueOf(o));
				if(num<=5){
					//重试操作
					new Thread(new Runnable() {
						@Override
						public void run() {
							redisClient.incr(rechargeCard.getCardNo(),1);
							submitOrder(rechargeCard , order.getRechargeMobile() , order.getOrderNo());
						}
					}).start();

				}else{
					redisClient.del(rechargeCard.getCardNo());
				}
			}
			updateById(order);
		} else {
			// 查不到
			logger.info("callback 查询不到数据");
		}
		return "success";
	}

	@Override
	public IPage<RechargeOrderDto> listPage(RechargeOrderQueryVo vo) {
		vo.getPage().setOptimizeCountSql(false);
		vo.getPage().setSearchCount(false);
		return baseMapper.listPage(vo.getPage(), vo);
	}

	@Override
	public Response<Boolean> submitOrder(TbRechargeCard rechargeCard , String mobile , String failOrderNo) {
		try{
			String reverseFlag = "1";
			// 查询所需要的信息
			BmRechargeMobileGetItemInfoResponse itemInfo = rechargeService.getItemInfo(mobile,rechargeCard.getMoney());

			if (StringUtils.isNotEmpty(itemInfo.getErrorCode()) || itemInfo.getMobileItemInfo() == null
					|| reverseFlag.equals(itemInfo.getMobileItemInfo().getReverseFlag())) {
				return Response.ofData(false, "接口数据不能使用");
			}

			boolean resultFlag = true;
			// 生成订单编号，并创建订单
			TbRechargeOrder order = new TbRechargeOrder();
			order.setCreateTime(new Date());
			order.setIsDeleted(0);
			order.setOrderMoney(rechargeCard.getMoney());
			order.setOrderNo(DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS")+ GeneratorCardInfoUtil.randomNumber(6));
			order.setOrderStatus(0);
			order.setRechargeCardId(rechargeCard.getId());
			order.setRechargeMobile(mobile);
			order.setIsDeleted(0);
			order.setFailOrderNo(failOrderNo);
			save(order);

			// 调用第三方接口
			String callBackUrl = ConfigUtil.getString("callback.url");
			BmRechargeMobilePayBillResponse cbResponse = rechargeService.payBill(mobile,rechargeCard.getMoney(), order.getOrderNo(), callBackUrl,
					itemInfo.getMobileItemInfo().getItemId());
			// 根据状态修改订单状态
			if (StringUtils.isNotEmpty(cbResponse.getErrorCode()) || cbResponse.getOrderDetailInfo() == null) {
				resultFlag = false;

			}
			if (cbResponse.getOrderDetailInfo() != null) {
				order.setThreeOrderMoney(cbResponse.getOrderDetailInfo().getOrderCost());
				order.setThreeOrderNo(cbResponse.getOrderDetailInfo().getBillId());
			}
			order.setOrderStatus(resultFlag ? 2 : 3);
			EntityUtil.setUpdateValue(order);
			updateById(order);

			// 修改卡位已使用
			if (resultFlag) {
				EntityUtil.setUpdateValue(rechargeCard);
				rechargeCard.setIsUse(1);
				rechargeCard.setUseMobile(mobile);
				rechargeCard.setUseTime(new Date());
				tbRechargeCardMapper.updateById(rechargeCard);
			}

			return Response.ofData(resultFlag,resultFlag?"":"充值失败");
		}catch (Exception e){
			e.printStackTrace();
		}
		return Response.ofData(false,"充值失败");
	}

}
