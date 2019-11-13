package com.dliberty.recharge.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author changzzc
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RechargeOrderDto implements Serializable {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商户订单号
     */
    private String orderNo;

    /**
     * 商户订单金额
     */
    private String orderMoney;

    /**
     * 订单状态(0：保存，1:充值成功，2：充值中，3：充值失败)
     */
    private Integer orderStatus;

    /**
     * 充值卡id
     */
    private Long rechargeCardId;

    /**
     * 充值卡号
     */
    private String rechargeCardNo;

    /**
     * 充值手机号
     */
    private String rechargeMobile;

    /**
     * 第三方订单号
     */
    private String threeOrderNo;

    /**
     * 第三方订单金额
     */
    private String threeOrderMoney;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 是否删除(0:未删除，1:已删除)
     */
    private Integer isDeleted;

    /**
     * 异步回调返回json
     */
    private String asyncCallbackJson;
}
