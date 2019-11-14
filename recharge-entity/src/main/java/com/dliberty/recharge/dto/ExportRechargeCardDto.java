package com.dliberty.recharge.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

/**
 * @author changzzc
 */
@Data
public class ExportRechargeCardDto{

    /**
     * 卡号
     */
    @ExcelProperty(value = "卡号" , index = 0)
    @ColumnWidth(350)
    private String cardNo;

    /**
     * 密钥
     */
    @ExcelProperty(value = "密钥" , index = 1)
    @ColumnWidth(250)
    private String secretKey;

    /**
     * 面额
     */
    @ExcelIgnore
    private String money;
}
