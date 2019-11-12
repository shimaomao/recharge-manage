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

    @ApiModelProperty(value = "充值卡密钥" , required = true)
    private String secretKey;

    @ApiModelProperty(value = "充值手机号" , required = true)
    private String mobile;
    /**
     * 验签
     */
    @ApiModelProperty(value = "验签" , required = true)
    private String sign;

}
