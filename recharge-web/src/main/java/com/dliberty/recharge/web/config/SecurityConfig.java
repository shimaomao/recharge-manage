package com.dliberty.recharge.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.dliberty.recharge.web.compont.JwtAuthenticationEntryPoint;
import com.dliberty.recharge.web.compont.JwtAuthenticationTokenFilter;

/**
 * SpringSecurity的配置 Created by macro on 2018/4/26.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("myUserDetailsService")
	private UserDetailsService myUserDetailsService;
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	@Autowired
	private AccessDeniedHandler accessDeniedHandler;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf()// 由于使用的是JWT，我们这里不需要csrf
				.disable().sessionManagement()// 基于token，所以不需要session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/swagger-ui.html", "/swagger-resources/**", "/v2/**", "/webjars/**", "/hello/home",
						"/user/login", "/admin/**", "/favicon.ico","/recharge/submit","/recharge/callback","/recharge/card/**","/export/rechargeCard")
				.permitAll().anyRequest()// 除上面外的所有请求全部需要鉴权认证
				.authenticated();
		// 禁用缓存
		httpSecurity.headers().cacheControl();
		// 添加JWT filter
		httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		// 添加自定义未授权和未登录结果返回
		httpSecurity.exceptionHandling().accessDeniedHandler(accessDeniedHandler)
				.authenticationEntryPoint(jwtAuthenticationEntryPoint);
	}

	@Bean
	public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
		return new JwtAuthenticationTokenFilter();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

}
