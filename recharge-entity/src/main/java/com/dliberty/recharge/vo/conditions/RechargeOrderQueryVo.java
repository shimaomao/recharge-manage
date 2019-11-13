package com.dliberty.recharge.vo.conditions;

import com.dliberty.recharge.common.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author changzzc
 */
@Data
@ApiModel(value = "充值订单查询实体")
public class RechargeOrderQueryVo extends BaseVo {

    @ApiModelProperty( value = "关键字")
    private String keyword;

    @ApiModelProperty(value = "充值状态（0：保存，1:充值成功，2：充值中，3：充值失败")
    private Integer orderStatus;

    @ApiModelProperty(value = "开始时间")
    private Date startDate;

    @ApiModelProperty(value = "结束时间")
    private Date endDate;
}
