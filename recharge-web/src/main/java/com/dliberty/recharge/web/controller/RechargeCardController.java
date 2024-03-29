package com.dliberty.recharge.web.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dliberty.recharge.api.service.ITbRechargeCardService;
import com.dliberty.recharge.common.vo.Response;
import com.dliberty.recharge.dto.RechargeCardDto;
import com.dliberty.recharge.vo.BatchCreateCardVo;
import com.dliberty.recharge.vo.conditions.RechargeCardQueryVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author changzzc
 */
@Api(value = "充值卡相关接口", tags = "RechargeCardController")
@RestController
@RequestMapping("/recharge/card")
public class RechargeCardController extends BaseController {

	@Autowired
	private ITbRechargeCardService tbRechargeCardService;

	@ApiOperation("批量创建充值卡")
	@PostMapping("/batchCreate")
	public Response<Boolean> batchCreate(@RequestBody BatchCreateCardVo cardVo) {
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

	@ApiOperation(value = "查询充值卡详情")
	@GetMapping("/query/{keyword}/{type}")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "keyword" , value = "查询关键字" , dataType = "String"),
			@ApiImplicitParam(name = "type" , value = "查询类型（0：id、1:卡号、2:密钥）" ,dataType = "Integer")
	})
	public Response<RechargeCardDto> getRechargeCard(@PathVariable("keyword") String keyword , @PathVariable("type")Integer type){
		return Response.ofData(tbRechargeCardService.getRechargeCardInfo(keyword, type));
	}
}
