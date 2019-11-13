package com.dliberty.recharge.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * @author changzzc
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ExportRechargeCardDto implements Serializable {

    /**
     * 卡号
     */
    @ExcelProperty(value = "卡号" , index = 0)
    private String cardNo;

    /**
     * 密钥
     */
    @ExcelProperty(value = "密钥" , index = 1)
    private String secretKey;

    /**
     * 面额
     */
    private String money;
}
