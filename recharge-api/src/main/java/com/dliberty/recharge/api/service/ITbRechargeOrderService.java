package com.dliberty.recharge.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dliberty.recharge.entity.TbRechargeOrder;
import com.dliberty.recharge.vo.RechargeVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LG
 * @since 2019-11-07
 */
public interface ITbRechargeOrderService extends IService<TbRechargeOrder> {

    /**
     * 充值订单接口
     * @param rechargeVo
     * @return
     */
    Boolean rechargeOrder(RechargeVo rechargeVo);
}
