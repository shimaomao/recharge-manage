package com.dliberty.recharge.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dliberty.recharge.api.service.ITbRechargeOrderService;
import com.dliberty.recharge.common.vo.Response;
import com.dliberty.recharge.dto.RechargeCallBackDto;
import com.dliberty.recharge.vo.RechargeVo;

import io.swagger.annotations.Api;

/**
 * @author changzzc
 */
@RestController
@RequestMapping("/recharge")
@Api(value = "充值相关接口", tags = "RechargeController")
public class RechargeController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(RechargeController.class);

	@Autowired
	private ITbRechargeOrderService rechargeOrderService;

	@PostMapping("/submit")
	public Response<Boolean> recharge(@RequestBody RechargeVo rechargeVo) {

		return rechargeOrderService.rechargeOrder(rechargeVo);
	}

	@RequestMapping(value = "/callback", method = { RequestMethod.POST, RequestMethod.GET })
	public String postCallback(HttpServletRequest request, HttpServletResponse response) {

		String tid = (String) request.getParameter("tid");
		String timestamp = (String) request.getParameter("timestamp");
		String shopId = (String) request.getParameter("user_id");
		String rechargeState = (String) request.getParameter("recharge_state");
		logger.info("充值回调tid={},timestamp={},shopId={},rechargeState={}", tid, timestamp, shopId, rechargeState);
		RechargeCallBackDto dto = new RechargeCallBackDto();
		dto.setBillId(tid);
		dto.setTimestamp(timestamp);
		dto.setRechargeState(rechargeState);
		dto.setShopId(shopId);
		// 验证签名，其他业务逻辑
		return rechargeOrderService.rechargeCallBack(dto);
		// 给服务器响应success字符串（收到请求后5秒内），否则将每一分钟发送回调信息一次，共发送4次。
		// 如果4次还未返回success，则服务器丢弃消息
		// return "success";
	}
}
