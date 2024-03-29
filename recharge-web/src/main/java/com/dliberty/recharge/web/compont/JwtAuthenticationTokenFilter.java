package com.dliberty.recharge.web.compont;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dliberty.recharge.common.utils.WebUtil;
import com.dliberty.recharge.web.util.JwtTokenUtil;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	@Value("${jwt.header}")
	private String token_header;
	@Value("${jwt.tokenHead}")
	private String tokenHead;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	@Qualifier("myUserDetailsService")
	private UserDetailsService myUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		String auth_token = request.getHeader(token_header);

		Enumeration<String> headerNames = request.getHeaderNames();
		System.out.println(headerNames);
		final String auth_token_start = tokenHead;
		if (StringUtils.isNotEmpty(auth_token) && auth_token.startsWith(auth_token_start)) {
			auth_token = auth_token.substring(auth_token_start.length());
			String username = jwtTokenUtil.getUserNameFromToken(auth_token);

			// 非法ip 访问
			String salt = jwtTokenUtil.getObject(auth_token, "salt");
			if (StringUtils.isEmpty(salt) || !salt.equals(WebUtil.getIpAddr(request))) {
				logger.info("ip 与 token 不符");
			} else {
				logger.info(String.format("Checking authentication for user %s.", username));

				if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					UserDetails userDetails = this.myUserDetailsService.loadUserByUsername(username);
					if (userDetails != null && jwtTokenUtil.validateToken(auth_token, userDetails)) {
						UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
								userDetails, null, userDetails.getAuthorities());
						authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						logger.info(String.format("Authenticated user %s, setting security context", username));
						SecurityContextHolder.getContext().setAuthentication(authentication);
					}
				}
			}

		}

		chain.doFilter(request, response);
	}

}
