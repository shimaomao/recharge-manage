package com.dliberty.recharge.dto;

import com.dliberty.recharge.common.vo.BaseVo;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangzhichang
 * @date 2019-11-12 9:42
 * @description
 */
@Data
public class RechargeCallBackDto extends BaseVo implements Serializable {

    /**
     * 订单编号
     */
    private String billId;

    /**
     * 直销商编号
     */
    private String shopId;

    /**
     * 时间戳
     */
    private String timestamp;

    /**
     * 状态（1：成功，9：失败）
     */
    private String rechargeState;
}
