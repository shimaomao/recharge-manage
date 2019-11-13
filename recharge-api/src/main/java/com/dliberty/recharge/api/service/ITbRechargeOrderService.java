package com.dliberty.recharge.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dliberty.recharge.common.vo.Response;
import com.dliberty.recharge.dto.RechargeCallBackDto;
import com.dliberty.recharge.dto.RechargeOrderDto;
import com.dliberty.recharge.entity.TbRechargeOrder;
import com.dliberty.recharge.vo.RechargeVo;
import com.dliberty.recharge.vo.conditions.RechargeOrderQueryVo;

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
    Response<Boolean> rechargeOrder(RechargeVo rechargeVo);

    /**
     *
     * 回调放法
     * @param callBackDto
     * @return
     */
    String rechargeCallBack(RechargeCallBackDto callBackDto);

    /**
     * 查询列表
     *
     * @return
     */
    IPage<RechargeOrderDto> listPage(RechargeOrderQueryVo vo);
}
