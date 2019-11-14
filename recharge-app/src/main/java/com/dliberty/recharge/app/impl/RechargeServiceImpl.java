package com.dliberty.recharge.app.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dliberty.recharge.api.service.IRechargeService;
import com.dliberty.recharge.app.util.ConfigUtil;
import com.dliberty.recharge.dao.mapper.TbApiRecordMapper;
import com.dliberty.recharge.entity.TbApiRecord;
import com.qianmi.open.api.ApiException;
import com.qianmi.open.api.OpenClient;
import com.qianmi.open.api.QianmiRequest;
import com.qianmi.open.api.QianmiResponse;
import com.qianmi.open.api.request.BmOrderCustomGetRequest;
import com.qianmi.open.api.request.BmRechargeMobileGetItemInfoRequest;
import com.qianmi.open.api.request.BmRechargeMobileGetPhoneInfoRequest;
import com.qianmi.open.api.request.BmRechargeMobilePayBillRequest;
import com.qianmi.open.api.request.BmRechargeOrderInfoRequest;
import com.qianmi.open.api.response.BmOrderCustomGetResponse;
import com.qianmi.open.api.response.BmRechargeMobileGetItemInfoResponse;
import com.qianmi.open.api.response.BmRechargeMobileGetPhoneInfoResponse;
import com.qianmi.open.api.response.BmRechargeMobilePayBillResponse;
import com.qianmi.open.api.response.BmRechargeOrderInfoResponse;

@Service
public class RechargeServiceImpl implements IRechargeService {

	@Autowired
	private OpenClient openClient;

	@Autowired
	private TbApiRecordMapper tbApiRecordMapper;

	@Override
	public BmRechargeMobileGetItemInfoResponse getItemInfo(String mobile, String rechargeAmount)
			throws ApiException {
		BmRechargeMobileGetItemInfoRequest req = new BmRechargeMobileGetItemInfoRequest();
		req.setMobileNo(mobile);
		req.setRechargeAmount(rechargeAmount);
		//RechargeMobileGetItemInfoResponse response = openClient.execute(req, accessToken);
		return execute(req ,  0);
	}

	@Override
	public BmRechargeMobileGetPhoneInfoResponse getPhoneInfo(String mobile, String respType) throws ApiException {
		BmRechargeMobileGetPhoneInfoRequest req = new BmRechargeMobileGetPhoneInfoRequest();
		req.setPhoneNo(mobile);
		req.setRespType(respType);
		//RechargeMobileGetPhoneInfoResponse response = openClient.execute(req, accessToken);
		return execute(req , 1);
	}

	@Override
	public BmRechargeMobilePayBillResponse payBill(String mobile, String rechargeAmount, String outerTid,
			String callback, String itemId) throws ApiException {
		BmRechargeMobilePayBillRequest request = new BmRechargeMobilePayBillRequest();
		request.setItemId(itemId);
		request.setMobileNo(mobile);
		request.setRechargeAmount(rechargeAmount);
		request.setOuterTid(outerTid);
		request.setCallback(callback);
		//RechargeMobileCreateBillResponse createBillResponse = openClient.execute(request, accessToken);
		return execute(request , 2);
	}

	@Override
	public BmRechargeOrderInfoResponse getOrderInfo(String billId) throws ApiException{
		BmRechargeOrderInfoRequest req = new BmRechargeOrderInfoRequest();
		req.setBillId(billId);
		//return openClient.execute(req, accessToken);
		return execute(req , 3);
	}

	@Override
	public BmOrderCustomGetResponse getOrderInfoByOuterTid(String outerTid) throws ApiException {
		BmOrderCustomGetRequest req = new BmOrderCustomGetRequest();
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
		
		String accessToken = ConfigUtil.getString("RECHARGE.ACCESS.TOKEN");
		Map<String , Object> params = new HashMap<>(16);
		params.put("param" , request);
		params.put("accessToken" , accessToken);
		TbApiRecord record = new TbApiRecord();
		record.setApiType(apiType);
		record.setRequestTime(new Date());
		record.setInParam(JSONObject.toJSONString(params));

		T response = openClient.execute(request, accessToken);
		record.setIsSuccess(response!=null?"1":"0");
		record.setCreateTime(new Date());
		record.setOutResult(JSONObject.toJSONString(response));
		tbApiRecordMapper.insert(record);
		return response;
	}

}
