package com.dliberty.recharge.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dliberty.recharge.api.service.UsersService;
import com.dliberty.recharge.api.vo.UsersAddVo;
import com.dliberty.recharge.api.vo.UsersUpdateStatusVo;
import com.dliberty.recharge.common.vo.Response;
import com.dliberty.recharge.dto.UsersDto;
import com.dliberty.recharge.vo.conditions.UsersQueryVo;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user")
public class UsersController {

	@Autowired
	private UsersService usersService;
	
	@ApiOperation("查询用户列表")
	@GetMapping("/listPage")
	public Response<UsersDto> listPage(UsersQueryVo vo) {
		return Response.ofPage(usersService.listPage(vo));
	}
	
	@ApiOperation("修改用户状态")
	@PutMapping("/updateStatus")
	public Response<Boolean> updateStatus(@RequestBody @Valid UsersUpdateStatusVo vo) {
		return Response.of(usersService.updateStatus(vo));
	}
	
	@ApiOperation("添加用户")
	@PostMapping("/addUser")
	public Response<Boolean> addUser(@RequestBody @Valid UsersAddVo vo) {
		return Response.of(usersService.addUser(vo));
	}
	
}
