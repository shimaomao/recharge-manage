package com.dliberty.recharge.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.dliberty.recharge.api.service.UsersService;
import com.dliberty.recharge.common.utils.WebUtil;
import com.dliberty.recharge.entity.Users;

@Controller
public class BaseController {
	
	@Autowired
	private UsersService usersService;

	public Long getUserId() {
		String userName = getUserName();
		Users users = usersService.selectUserByOpenId(userName);
		if (users != null) {
			return users.getId();
		}
		return null;
	}
	
	public String getUserName() {
		return WebUtil.getUserName();
	}
}
