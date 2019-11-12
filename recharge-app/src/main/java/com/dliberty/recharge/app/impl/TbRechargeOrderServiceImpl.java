package com.dliberty.recharge.app.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dliberty.recharge.api.service.IRechargeService;
import com.dliberty.recharge.app.util.ConfigUtil;
import com.dliberty.recharge.common.lang.data.DoubleUtils;
import com.dliberty.recharge.common.lang.data.StringUtils;
import com.dliberty.recharge.common.utils.DateFormatUtils;
import com.dliberty.recharge.common.utils.EntityUtil;
import com.dliberty.recharge.common.utils.GeneratorCardInfoUtil;
import com.dliberty.recharge.dao.mapper.TbRechargeCardMapper;
import com.dliberty.recharge.dto.RechargeCallBackDto;
import com.dliberty.recharge.entity.TbRechargeCard;
import com.dliberty.recharge.vo.RechargeVo;
import com.qianmi.open.api.ApiException;
import com.qianmi.open.api.response.RechargeMobileCreateBillResponse;
import com.qianmi.open.api.response.RechargeMobileGetItemInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Autowired
    private TbRechargeOrderMapper tbRechargeOrderMapper;

    @Override
    public Boolean rechargeOrder(RechargeVo rechargeVo) {
        //校验数据合法性
        TbRechargeCard rechargeCard = null;
        boolean flag = false;
        if(rechargeVo.getId() != null){
            rechargeCard = tbRechargeCardMapper.selectById(rechargeVo.getId());

            if(rechargeCard != null && rechargeCard.getIsUse() == 0 &&
                    rechargeCard.getCardNo().equals(rechargeVo.getCardNo()) &&
                    rechargeCard.getSecretKey().equals(rechargeVo.getSecretKey()) &&
                    rechargeVo.getMoney().equals(rechargeVo.getMoney())){
                flag = true;
            }
        }else if(StringUtils.isNotEmpty(rechargeVo.getCardNo())){
            rechargeCard = tbRechargeCardMapper.selectOne(new QueryWrapper<TbRechargeCard>().eq("card_no" , rechargeVo.getCardNo()));

            if(rechargeCard != null && rechargeCard.getIsUse() == 0 &&
                    rechargeCard.getSecretKey().equals(rechargeVo.getSecretKey()) &&
                    rechargeVo.getMoney().equals(rechargeVo.getMoney())){
                flag = true;
            }
        }else if(StringUtils.isNotEmpty(rechargeVo.getSecretKey())){
            rechargeCard = tbRechargeCardMapper.selectOne(new QueryWrapper<TbRechargeCard>().eq("secret_key" , rechargeVo.getCardNo()));

            if(rechargeCard != null && rechargeCard.getIsUse() == 0 &&
                    rechargeCard.getCardNo().equals(rechargeVo.getCardNo()) &&
                    rechargeVo.getMoney().equals(rechargeVo.getMoney())){
                flag = true;
            }
        }
        if(!flag){
            return flag;
        }

        try {
            String reverseFlag = "1";
            //查询所需要的信息
            RechargeMobileGetItemInfoResponse itemInfo = rechargeService.getItemInfo(rechargeVo.getMobile(), String.valueOf(rechargeVo.getMoney() / 100));
            if(StringUtils.isNotEmpty(itemInfo.getErrorCode()) || itemInfo.getMobileItemInfo() == null || reverseFlag.equals(itemInfo.getMobileItemInfo().getReverseFlag())){
                return false;
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
            tbRechargeOrderMapper.insert(order);

            //调用第三方接口
            String callBackUrl = ConfigUtil.getString("callback.url");
            RechargeMobileCreateBillResponse rechargeMobileCreateBillResponse = rechargeService.payBill(rechargeVo.getMobile(), String.valueOf(rechargeVo.getMoney() / 100), order.getOrderNo(), callBackUrl, itemInfo.getMobileItemInfo().getItemId());
            //根据状态修改订单状态
            if(StringUtils.isNotEmpty(rechargeMobileCreateBillResponse.getErrorCode()) || rechargeMobileCreateBillResponse.getOrderDetailInfo() == null){
                resultFlag = false;

            }
            if (rechargeMobileCreateBillResponse.getOrderDetailInfo() != null) {
                double cost = DoubleUtils.defaultDouble(rechargeMobileCreateBillResponse.getOrderDetailInfo().getOrderCost() , 0);
                order.setThreeOrderMoney(Integer.valueOf(String.valueOf(cost * 100)));
                order.setThreeOrderNo(rechargeMobileCreateBillResponse.getOrderDetailInfo().getBillId());
            }
            order.setOrderStatus(resultFlag?2:3);
            EntityUtil.setUpdateValue(order);
            tbRechargeOrderMapper.updateById(order);
            return resultFlag;

        } catch (ApiException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String rechargeCallBack(RechargeCallBackDto callBackDto) {

        TbRechargeOrder order = tbRechargeOrderMapper.selectOne(new QueryWrapper<TbRechargeOrder>().eq("three_order_no", callBackDto.getBillId()));
        if(order != null){
            order.setOrderStatus(1);
            order.setUpdateTime(new Date());
            tbRechargeOrderMapper.updateById(order);
        }else{
            //查不到
        }
        return "success";
    }

}
