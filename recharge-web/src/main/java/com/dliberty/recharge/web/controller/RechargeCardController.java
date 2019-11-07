package com.dliberty.recharge.web.controller;

import com.dliberty.recharge.api.service.ITbRechargeCardService;
import com.dliberty.recharge.common.vo.Response;
import com.dliberty.recharge.dto.PmsVoteDto;
import com.dliberty.recharge.dto.RechargeCardDto;
import com.dliberty.recharge.vo.BatchCreateCardVo;
import com.dliberty.recharge.vo.CreateCardVo;
import com.dliberty.recharge.vo.conditions.PmsVoteQueryVo;
import com.dliberty.recharge.vo.conditions.RechargeCardQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(value = "充值卡相关接口",tags="RechargeCardController")
@RestController
@RequestMapping("/recharge/card")
public class RechargeCardController extends BaseController{

    @Autowired
    private ITbRechargeCardService tbRechargeCardService;

    @ApiOperation("批量创建充值卡")
    @PostMapping("/batchCreate")
    public Response<Boolean> batchCreate(@RequestBody CreateCardVo cardVo){
        cardVo.setUserId(getUserId());
        return Response.ofData(tbRechargeCardService.batchCreateCard(cardVo));
    }

    @ApiOperation("查询充值卡列表")
    @GetMapping("/listPage")
    public Response<RechargeCardDto> listPage(RechargeCardQueryVo vo) {
        return Response.ofPage(tbRechargeCardService.listPage(vo));
    }

    @ApiOperation("删除充值卡")
    @DeleteMapping("/delete/{id}")
    public Response<Boolean> delete(@PathVariable Integer id) {
        return Response.of(tbRechargeCardService.delete(id));
    }
}
