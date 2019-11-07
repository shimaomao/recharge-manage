package com.dliberty.recharge.web.compont;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.dliberty.recharge.common.constants.Constants.ErrorCode;
import com.dliberty.recharge.common.vo.Response;

/**
 * 认证失败处理类，返回401
 * 
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		// 验证为未登陆状态会进入此方法，认证错误
		System.out.println("认证失败：" + authException.getMessage());
		response.setStatus(200);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		
		String requestURI = request.getRequestURI();
		System.out.println(requestURI);

		String body = JSONObject.toJSONString(Response.ofError(ErrorCode.UN_LOGIN, "未登录"));

		printWriter.write(body);
		printWriter.flush();
	}

}
