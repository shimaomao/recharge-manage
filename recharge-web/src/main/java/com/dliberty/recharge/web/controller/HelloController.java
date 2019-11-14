package com.dliberty.recharge.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dliberty.recharge.common.redis.RedisClient;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("hello")
@Api(value = "hello测试类")
public class HelloController {
	
	@Autowired
	RedisClient redisClient;

	@GetMapping("home")
	@ApiOperation(value = "home")
	public String home(){
		boolean set = redisClient.setNx("1234", "123",60*60);
		System.out.println(set);
		boolean set2 = redisClient.setNx("1234", "123",60*60);
		System.out.println(set2);
		return "hello home";
	}
}
