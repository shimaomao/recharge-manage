package com.dliberty.recharge.api.service;

import javax.servlet.http.HttpServletRequest;

import com.dliberty.recharge.api.dto.OssCallbackResult;
import com.dliberty.recharge.api.dto.OssPolicyResult;

/**
 * oss上传管理Service Created by macro on 2018/5/17.
 */
public interface OssService {
	/**
	 * oss上传策略生成
	 */
	OssPolicyResult policy();

	/**
	 * oss上传成功回调
	 */
	OssCallbackResult callback(HttpServletRequest request);
}
