package com.dliberty.recharge.vo;

import com.dliberty.recharge.common.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author changzzc
 */
@ApiModel(value = "充值接口请求实体")
@Data
public class RechargeVo extends BaseVo implements Serializable {

    @ApiModelProperty(value = "充值卡id" , required = true)
    private Long id;

    @ApiModelProperty(value = "充值卡卡号" , required = true)
    private String cardNo;

    @ApiModelProperty(value = "充值卡密钥" , required = true)
    private String secretKey;

    @ApiModelProperty(value = "充值金额" , required = true)
    private String money;

    @ApiModelProperty(value = "充值手机号" , required = true)
    private String mobile;

}
