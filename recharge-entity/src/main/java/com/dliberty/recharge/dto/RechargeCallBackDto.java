package com.dliberty.recharge.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author zhangzhichang
 * @date 2019-11-12 9:42
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RechargeCallBackDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

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
