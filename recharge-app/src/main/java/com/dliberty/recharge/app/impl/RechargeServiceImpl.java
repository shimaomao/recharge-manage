package com.dliberty.recharge.app.impl;

import com.alibaba.fastjson.JSONObject;
import com.dliberty.recharge.dao.mapper.TbApiRecordMapper;
import com.dliberty.recharge.entity.TbApiRecord;
import com.qianmi.open.api.QianmiRequest;
import com.qianmi.open.api.QianmiResponse;
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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class RechargeServiceImpl implements IRechargeService {

	@Autowired
	private OpenClient openClient;

	@Value("${RECHARGE.ACCESS.TOKEN}")
	private String accessToken;

	@Autowired
	private TbApiRecordMapper tbApiRecordMapper;

	@Override
	public RechargeMobileGetItemInfoResponse getItemInfo(String mobile, String rechargeAmount)
			throws ApiException {
		RechargeMobileGetItemInfoRequest req = new RechargeMobileGetItemInfoRequest();
		req.setMobileNo(mobile);
		req.setRechargeAmount(rechargeAmount);
		//RechargeMobileGetItemInfoResponse response = openClient.execute(req, accessToken);
		return execute(req ,  0);
	}

	@Override
	public RechargeMobileGetPhoneInfoResponse getPhoneInfo(String mobile, String respType) throws ApiException {
		RechargeMobileGetPhoneInfoRequest req = new RechargeMobileGetPhoneInfoRequest();
		req.setPhoneNo(mobile);
		req.setRespType(respType);
		//RechargeMobileGetPhoneInfoResponse response = openClient.execute(req, accessToken);
		return execute(req , 1);
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
		//RechargeMobileCreateBillResponse createBillResponse = openClient.execute(request, accessToken);
		return execute(request , 2);
	}

	@Override
	public RechargeOrderInfoResponse getOrderInfo(String billId) throws ApiException{
		RechargeOrderInfoRequest req = new RechargeOrderInfoRequest();
		req.setBillId(billId);
		//return openClient.execute(req, accessToken);
		return execute(req , 3);
	}

	@Override
	public OrderCustomGetResponse getOrderInfoByOuterTid(String outerTid) throws ApiException {
		OrderCustomGetRequest req = new OrderCustomGetRequest();
		req.setOuterTid(outerTid);
		//return openClient.execute(req , accessToken);
		return execute(req , 4);
	}

	/**
	 *
	 * @param request
	 * @param apiType 接口类型 （0：getItemInfo   1: getPhoneInfo  2: payBill  3: getOrderInfo  4: getOrderInfoByOuterTid）
	 * @param <T>
	 * @return
	 * @throws ApiException
	 */
	private <T extends QianmiResponse> T execute(QianmiRequest<T> request , Integer apiType) throws ApiException {
		Map<String , Object> params = new HashMap<>(16);
		params.put("param" , request);
		params.put("accessToken" , accessToken);
		TbApiRecord record = new TbApiRecord();
		record.setApiType(apiType);
		record.setRequestTime(new Date());
		record.setInParam(JSONObject.toJSONString(params));

		T response = openClient.execute(request, accessToken);
		record.setIsSuccess(response.getErrorCode());
		record.setCreateTime(new Date());
		tbApiRecordMapper.insert(record);
		return response;
	}

}
