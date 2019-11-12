package com.dliberty.recharge.app.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dliberty.recharge.api.service.IRechargeService;
import com.dliberty.recharge.app.util.ConfigUtil;
import com.dliberty.recharge.common.lang.data.StringUtils;
import com.dliberty.recharge.common.utils.DateFormatUtils;
import com.dliberty.recharge.common.utils.EntityUtil;
import com.dliberty.recharge.app.util.GeneratorCardInfoUtil;
import com.dliberty.recharge.common.vo.Response;
import com.dliberty.recharge.dao.mapper.TbRechargeCardMapper;
import com.dliberty.recharge.dto.RechargeCallBackDto;
import com.dliberty.recharge.entity.TbRechargeCard;
import com.dliberty.recharge.vo.RechargeVo;
import com.qianmi.open.api.ApiException;
import com.qianmi.open.api.response.RechargeMobileCreateBillResponse;
import com.qianmi.open.api.response.RechargeMobileGetItemInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dliberty.recharge.api.service.ITbRechargeOrderService;
import com.dliberty.recharge.dao.mapper.TbRechargeOrderMapper;
import com.dliberty.recharge.entity.TbRechargeOrder;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LG
 * @since 2019-11-07
 */
@Service
public class TbRechargeOrderServiceImpl extends ServiceImpl<TbRechargeOrderMapper, TbRechargeOrder> implements ITbRechargeOrderService {

    @Autowired
    private IRechargeService rechargeService;

    @Autowired
    private TbRechargeCardMapper tbRechargeCardMapper;

    @Override
    public Response<Boolean> rechargeOrder(RechargeVo rechargeVo) {
        //校验数据合法性
        TbRechargeCard rechargeCard = null;
        boolean flag = false;
        String errorMessage = "参数有误";
        if(rechargeVo.getId() != null){
            rechargeCard = tbRechargeCardMapper.selectById(rechargeVo.getId());
            if(rechargeCard == null ||
                    !rechargeCard.getCardNo().equals(rechargeVo.getCardNo()) ||
                    !rechargeCard.getMoney().equals(rechargeVo.getMoney())){
                errorMessage = "参数有误";
            }else if(!rechargeCard.getSecretKey().equals(rechargeVo.getSecretKey())){
                errorMessage = "密钥输入有误";
            }
            else if(rechargeCard.getIsUse() != 0){
                errorMessage = "充值卡已使用";
            }else{
                flag = true;
            }
        }
        if(!flag){
            return Response.ofData(flag , errorMessage);
        }

        try {
            String reverseFlag = "1";
            //查询所需要的信息
            RechargeMobileGetItemInfoResponse itemInfo = rechargeService.getItemInfo(rechargeVo.getMobile(), rechargeVo.getMoney());
            if(StringUtils.isNotEmpty(itemInfo.getErrorCode()) || itemInfo.getMobileItemInfo() == null || reverseFlag.equals(itemInfo.getMobileItemInfo().getReverseFlag())){
                return Response.ofData(false , "接口数据不能使用");
            }

            boolean resultFlag = true;
            //生成订单编号，并创建订单
            TbRechargeOrder order = new TbRechargeOrder();
            order.setCreateTime(new Date());
            order.setIsDeleted(0);
            order.setOrderMoney(rechargeVo.getMoney());
            order.setOrderNo(DateFormatUtils.format(new Date() , "yyyyMMddHHmmssSSS") + GeneratorCardInfoUtil.randomNumber(6));
            order.setOrderStatus(0);
            order.setRechargeCardId(rechargeCard.getId());
            order.setRechargeMobile(rechargeVo.getMobile());
            order.setIsDeleted(0);
            save(order);

            //调用第三方接口
            String callBackUrl = ConfigUtil.getString("callback.url");
            RechargeMobileCreateBillResponse rechargeMobileCreateBillResponse = rechargeService.payBill(rechargeVo.getMobile(), rechargeVo.getMoney(), order.getOrderNo(), callBackUrl, itemInfo.getMobileItemInfo().getItemId());
            //根据状态修改订单状态
            if(StringUtils.isNotEmpty(rechargeMobileCreateBillResponse.getErrorCode()) || rechargeMobileCreateBillResponse.getOrderDetailInfo() == null){
                resultFlag = false;

            }
            if (rechargeMobileCreateBillResponse.getOrderDetailInfo() != null) {
                order.setThreeOrderMoney(rechargeMobileCreateBillResponse.getOrderDetailInfo().getOrderCost());
                order.setThreeOrderNo(rechargeMobileCreateBillResponse.getOrderDetailInfo().getBillId());
            }
            order.setOrderStatus(resultFlag?2:3);
            EntityUtil.setUpdateValue(order);
            updateById(order);

            //修改卡位已使用
            EntityUtil.setUpdateValue(rechargeCard);
            rechargeCard.setIsUse(1);
            rechargeCard.setUseMobile(rechargeVo.getMobile());
            rechargeCard.setUseTime(new Date());
            tbRechargeCardMapper.updateById(rechargeCard);

            return Response.ofData(resultFlag);

        } catch (ApiException e) {
            e.printStackTrace();
        }
        return Response.ofData(false);
    }

    @Override
    public String rechargeCallBack(RechargeCallBackDto callBackDto) {

        TbRechargeOrder order = baseMapper.selectOne(new QueryWrapper<TbRechargeOrder>().eq("three_order_no", callBackDto.getBillId()));
        if(order != null){
            order.setOrderStatus(1);
            order.setUpdateTime(new Date());
            updateById(order);
        }else{
            //查不到
        }
        return "success";
    }


}
