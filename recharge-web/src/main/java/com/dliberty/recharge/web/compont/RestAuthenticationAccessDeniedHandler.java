package com.dliberty.recharge.web.compont;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.dliberty.recharge.common.constants.Constants.ErrorCode;
import com.dliberty.recharge.common.vo.Response;

/**
 * 权限不足错误类
 * @author LG
 *
 */
@Component("restAuthenticationAccessDeniedHandler")
public class RestAuthenticationAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
			throws IOException, ServletException {
		//登陆状态下，权限不足执行该方法
        System.out.println("权限不足：" + e.getMessage());
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        
        String body = JSONObject.toJSONString(Response.ofError(ErrorCode.UN_AUTHORIZE, "没有访问权限"));
        printWriter.write(body);
        printWriter.flush();

	}

}
