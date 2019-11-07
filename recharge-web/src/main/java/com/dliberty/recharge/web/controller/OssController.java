package com.dliberty.recharge.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dliberty.recharge.api.dto.OssCallbackResult;
import com.dliberty.recharge.api.dto.OssPolicyResult;
import com.dliberty.recharge.api.service.OssService;
import com.dliberty.recharge.common.vo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Oss相关操作接口 .
 */
@RestController
@Api(tags = "OssController", value = "Oss管理")
@RequestMapping("/aliyun/oss")
public class OssController {
	@Autowired
	private OssService ossService;

	@ApiOperation(value = "oss上传签名生成")
	@GetMapping("/policy")
	public Response<OssPolicyResult> policy() {
		OssPolicyResult result = ossService.policy();
		return Response.ofData(result);
	}

	@ApiOperation(value = "oss上传成功回调")
	@PostMapping("callback")
	public Response<OssCallbackResult> callback(HttpServletRequest request) {
		OssCallbackResult ossCallbackResult = ossService.callback(request);
		return Response.ofData(ossCallbackResult);
	}

}
