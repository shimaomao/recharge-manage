package com.dliberty.recharge.vo;

import com.dliberty.recharge.common.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "批量创建卡片请求实体")
public class BatchCreateCardVo extends BaseVo {

    @ApiModelProperty(value = "充值卡面额")
    private String money;

    @ApiModelProperty(value = "数量")
    private Integer number;
}
