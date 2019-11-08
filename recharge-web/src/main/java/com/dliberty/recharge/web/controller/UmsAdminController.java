package com.dliberty.recharge.web.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.dliberty.recharge.api.service.UmsRoleService;
import com.dliberty.recharge.common.exception.CommonException;
import com.dliberty.recharge.common.utils.HttpClientUtils;
import com.dliberty.recharge.common.vo.Response;
import com.dliberty.recharge.dto.UmsAdminLoginParam;
import com.dliberty.recharge.web.service.UserLoginService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 后台用户管理 Created by macro on 2018/4/26.
 */
@RestController
@RequestMapping("/admin")
@Api(value="用户相关接口")
public class UmsAdminController extends BaseController {
	@Autowired
	private UserLoginService userLoginService;
	@Value("${jwt.header}")
	private String tokenHeader;
	@Value("${jwt.tokenHead}")
	private String tokenHead;
	@Autowired
	private UmsRoleService umsRoleService;
	@Value("${app.weixin.appid}")
	private String appId;
	@Value("${app.weixin.secret}")
	private String secret;

	@PostMapping("/login")
	@ApiOperation("登陆")
	public Response<HashMap<String, Object>> login(@RequestBody UmsAdminLoginParam umsAdminLoginParam) {
		String token = userLoginService.login(umsAdminLoginParam.getUsername(), umsAdminLoginParam.getPassword());
		if (token == null) {
			throw new CommonException("用户名或密码错误");
		}
		List<String> roleCodes = umsRoleService.selectCode(getUserId());
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("token", token);
		map.put("tokenHead", tokenHead);
		map.put("roleCodes", roleCodes);
		return Response.ofData(map);
	}

	@GetMapping("/getSessionKeyOropenid")
	public Response<HashMap<String, Object>> getSessionKeyOropenid(String code) {
		String requestUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
		String turl = String.format(requestUrl, appId, secret, code);
		String responseContent = HttpClientUtils.responseGet(turl);
		JSONObject json = JSONObject.parseObject(responseContent);

		HashMap<String, Object> map = new HashMap<String, Object>();
		if (json != null) {
			String openId = json.getString("openid");
			String token = userLoginService.loginWeixin(openId);
			map.put("token", token);
			map.put("tokenHead", tokenHead);
		}
		return Response.ofData(map);
	}
	
	@GetMapping("/register")
	@ApiOperation("注册")
	public Response<Boolean> register(String userName,String password) {
		userLoginService.register(userName, password);
		return Response.of(true);
	}
	
	@PostMapping("/logout")
	@ApiOperation("退出")
	public Response<Boolean> logout() {
		SecurityContextHolder.clearContext();
		return Response.of(true);
	}

}
