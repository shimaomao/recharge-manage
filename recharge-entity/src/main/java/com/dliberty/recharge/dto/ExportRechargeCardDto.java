package com.dliberty.recharge.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author changzzc
 */
@Data
@EqualsAndHashCode()
public class ExportRechargeCardDto{

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
    @ExcelIgnore
    private String money;
}
