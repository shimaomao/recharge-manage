package com.dliberty.recharge.vo;

import com.dliberty.recharge.common.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "自动创建充值卡vo")
public class CreateCardVo extends BaseVo {

    @ApiModelProperty(value = "批量生产卡设置")
    private List<BatchCreateCardVo> cardList;
}
