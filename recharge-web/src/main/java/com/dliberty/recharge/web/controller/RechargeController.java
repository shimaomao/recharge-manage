package com.dliberty.recharge.web.controller;

import com.dliberty.recharge.api.service.ITbRechargeOrderService;
import com.dliberty.recharge.common.vo.Response;
import com.dliberty.recharge.vo.RechargeVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author changzzc
 */
@RestController
@RequestMapping("/recharge")
@Api(value = "充值相关接口", tags = "RechargeController")
public class RechargeController extends BaseController {

    @Autowired
    private ITbRechargeOrderService rechargeOrderService;

    @PostMapping("/submit")
    public Response<Boolean> recharge(RechargeVo rechargeVo){

        return Response.ofData(rechargeOrderService.rechargeOrder(rechargeVo));
    }
}
