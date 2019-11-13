package com.dliberty.recharge.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RechargeCardDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 卡号
     */
    private String cardNo;

    /**
     * 密钥
     */
    private String secretKey;

    /**
     * 面额
     */
    private String money;

    /**
     * 是否使用(0:未使用，1:已使用)
     */
    private Integer isUse;

    /**
     * 使用手机号
     */
    private String useMobile;

    /**
     * 使用时间
     */
    private Date useTime;

    /**
     * 二维码路径
     */
    private String qrCodePath;

    /**
     * 是否删除(0:未删除，1:已删除)
     */
    private Integer isDeleted;

    /**
     * 创建人id
     */
    private Long createUserId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 验签
     */
    private String sign;
}
