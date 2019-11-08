package com.dliberty.recharge.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.qianmi.open.api.DefaultOpenClient;
import com.qianmi.open.api.OpenClient;

/**
 * 投票配置
 * 
 * @author LG
 *
 */
@Configuration
public class RechargeConfig {

	@Value("${RECHARGE.API.URL}")
	private String API_URL;

	@Value("${RECHARGE.APP.KEY}")
	private String APP_KEY;
	
	@Value("${RECHARGE.APP.SECRET}")
	private String APP_SECRET;

	@Value("${RECHARGE.ACCESS.TOKEN}")
	private String accessToken;
	
	@Bean
	public OpenClient init() {
		// 创建一个客户端，默认连接超时时间为3秒，请求超时时间为15秒。
		return new DefaultOpenClient(API_URL, APP_KEY, APP_SECRET);
	}
}
