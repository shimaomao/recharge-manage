package com.dliberty.recharge.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.dliberty.recharge.api.service.UsersService;
import com.dliberty.recharge.common.exception.CommonException;
import com.dliberty.recharge.common.lang.data.StringUtils;
import com.dliberty.recharge.common.utils.WebUtil;
import com.dliberty.recharge.entity.Users;
import com.dliberty.recharge.web.util.JwtTokenUtil;

@Service
public class UserLoginService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private UsersService usersService;
	@Autowired
	@Qualifier("myUserDetailsService")
	private UserDetailsService myUserDetailsService;

	/**
	 * 注册
	 * 
	 * @param openId
	 * @return
	 */
	public Users register(String userName, String password) {
		return usersService.register(userName, password);
	}

	/**
	 * 后台用户登陆
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	public String login(String username, String password) {
		String token = null;
		// 密码需要客户端加密后传递
		Users users = usersService.selectUserByUsername(username);
		if (null == users) {
			throw new CommonException("用户名不存在");
		}

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);
		try {
			Authentication authentication = authenticationManager.authenticate(authenticationToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
			token = jwtTokenUtil.generateToken(userDetails, WebUtil.getIpAddr(null));
		} catch(DisabledException e) {
			throw new CommonException("用户被禁用");
		} catch (AuthenticationException e) {
			logger.warn("登录异常:{}", e.getMessage());
		}
		return token;
	}

	public String loginWeixin(String openId) {

		logger.info("openId={}登陆", openId);
		if (StringUtils.isEmpty(openId)) {
			throw new CommonException("openId is null");
		}
		// 默认注册
		register(openId, openId);

		return login(openId, openId);

	}

}
