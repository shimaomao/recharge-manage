package com.dliberty.recharge.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dliberty.recharge.api.service.IPmsVoteService;
import com.dliberty.recharge.common.vo.Response;
import com.dliberty.recharge.dto.PmsVoteDto;
import com.dliberty.recharge.vo.PmsVoteParamVo;
import com.dliberty.recharge.vo.conditions.PmsVoteQueryVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author LG
 * @since 2019-11-01
 */
@Api(value = "投票相关接口",tags="PmsVoteController")
@RestController
@RequestMapping("/pmsVote")
public class PmsVoteController {

	@Autowired
	private IPmsVoteService pmsVoteService;

	@ApiOperation("创建投票活动")
	@PostMapping("/create")
	public Response<Boolean> create(@RequestBody @Valid PmsVoteParamVo vo) {
		return Response.of(pmsVoteService.create(vo));
	}

	@ApiOperation("更新投票活动")
	@PutMapping("/update/{id}")
	public Response<Boolean> update(@PathVariable Integer id, @RequestBody @Valid PmsVoteParamVo vo) {
		return Response.of(pmsVoteService.update(id, vo));
	}
	
	@ApiOperation("查询活动列表")
	@GetMapping("/listPage")
	public Response<PmsVoteDto> listPage(PmsVoteQueryVo vo) {
		return Response.ofPage(pmsVoteService.listPage(vo));
	}
	
	@ApiOperation("查询活动详情")
	@GetMapping("/view/{id}")
	public Response<PmsVoteDto> view(@PathVariable Integer id) {
		return Response.ofData(pmsVoteService.query(id));
	}
	
	@ApiOperation("删除")
	@DeleteMapping("/delete/{id}")
	public Response<Boolean> delete(@PathVariable Integer id) {
		return Response.of(pmsVoteService.delete(id));
	}
}
