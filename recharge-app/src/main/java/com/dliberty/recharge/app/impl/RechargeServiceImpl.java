package com.dliberty.recharge.app.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.dliberty.recharge.api.service.IRechargeService;
import com.qianmi.open.api.ApiException;
import com.qianmi.open.api.OpenClient;
import com.qianmi.open.api.request.OrderCustomGetRequest;
import com.qianmi.open.api.request.RechargeMobileCreateBillRequest;
import com.qianmi.open.api.request.RechargeMobileGetItemInfoRequest;
import com.qianmi.open.api.request.RechargeMobileGetPhoneInfoRequest;
import com.qianmi.open.api.request.RechargeOrderInfoRequest;
import com.qianmi.open.api.response.OrderCustomGetResponse;
import com.qianmi.open.api.response.RechargeMobileCreateBillResponse;
import com.qianmi.open.api.response.RechargeMobileGetItemInfoResponse;
import com.qianmi.open.api.response.RechargeMobileGetPhoneInfoResponse;
import com.qianmi.open.api.response.RechargeOrderInfoResponse;

@Service
public class RechargeServiceImpl implements IRechargeService {

	@Autowired
	private OpenClient openClient;

	@Value("${RECHARGE.ACCESS.TOKEN}")
	private String accessToken;

	@Override
	public RechargeMobileGetItemInfoResponse getItemInfo(String mobile, String rechargeAmount)
			throws ApiException {
		RechargeMobileGetItemInfoRequest req = new RechargeMobileGetItemInfoRequest();
		req.setMobileNo(mobile);
		req.setRechargeAmount(rechargeAmount);
		RechargeMobileGetItemInfoResponse response = openClient.execute(req, accessToken);
		return response;
	}

	@Override
	public RechargeMobileGetPhoneInfoResponse getPhoneInfo(String mobile, String respType) throws ApiException {
		RechargeMobileGetPhoneInfoRequest req = new RechargeMobileGetPhoneInfoRequest();
		req.setPhoneNo(mobile);
		req.setRespType(respType);
		RechargeMobileGetPhoneInfoResponse response = openClient.execute(req, accessToken);
		return response;
	}

	@Override
	public RechargeMobileCreateBillResponse payBill(String mobile, String rechargeAmount, String outerTid,
			String callback, String itemId) throws ApiException {
		RechargeMobileCreateBillRequest request = new RechargeMobileCreateBillRequest();
		request.setItemId(itemId);
		request.setMobileNo(mobile);
		request.setRechargeAmount(rechargeAmount);
		request.setOuterTid(outerTid);
		request.setCallback(callback);
		RechargeMobileCreateBillResponse createBillResponse = openClient.execute(request, accessToken);
		return createBillResponse;
	}

	@Override
	public RechargeOrderInfoResponse getOrderInfo(String billId) throws ApiException{
		RechargeOrderInfoRequest req = new RechargeOrderInfoRequest();
		req.setBillId(billId);
		return openClient.execute(req, accessToken);
	}

	@Override
	public OrderCustomGetResponse getOrderInfoByOuterTid(String outerTid) throws ApiException {
		OrderCustomGetRequest req = new OrderCustomGetRequest();
		req.setOuterTid(outerTid);
		return openClient.execute(req, accessToken);
	}

}