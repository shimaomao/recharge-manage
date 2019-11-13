package com.dliberty.recharge.web.controller;

import com.dliberty.recharge.api.service.ITbRechargeOrderService;
import com.dliberty.recharge.common.vo.Response;
import com.dliberty.recharge.dto.RechargeOrderDto;
import com.dliberty.recharge.vo.conditions.RechargeOrderQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author changzzc
 */
@RestController
@RequestMapping("/recharge/order")
@Api(value = "充值相关接口", tags = "RechargeOrderController")
public class RechargeOrderController extends BaseController {

    @Autowired
    private ITbRechargeOrderService tbRechargeOrderService;

    @ApiOperation("查询充值订单列表")
    @GetMapping("/listPage")
    public Response<RechargeOrderDto> listPage(RechargeOrderQueryVo vo) {
        return Response.ofPage(tbRechargeOrderService.listPage(vo));
    }
}
