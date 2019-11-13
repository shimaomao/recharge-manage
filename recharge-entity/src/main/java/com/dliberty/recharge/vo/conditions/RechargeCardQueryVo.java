package com.dliberty.recharge.vo.conditions;

import com.dliberty.recharge.common.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "充值卡查询实体")
public class RechargeCardQueryVo extends BaseVo {

    @ApiModelProperty( value = "关键字")
    private String keyword;

    @ApiModelProperty(value = "是否使用（0：未使用，1：已使用")
    private Integer useFlag;

    @ApiModelProperty(value = "开始时间")
    private Date startDate;

    @ApiModelProperty(value = "结束时间")
    private Date endDate;

    @ApiModelProperty(value = "面额")
    private String money;
}
