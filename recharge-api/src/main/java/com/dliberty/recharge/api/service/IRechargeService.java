package com.dliberty.recharge.api.service;

import com.qianmi.open.api.ApiException;
import com.qianmi.open.api.response.BmOrderCustomGetResponse;
import com.qianmi.open.api.response.BmRechargeMobileGetItemInfoResponse;
import com.qianmi.open.api.response.BmRechargeMobileGetPhoneInfoResponse;
import com.qianmi.open.api.response.BmRechargeMobilePayBillResponse;
import com.qianmi.open.api.response.BmRechargeOrderInfoResponse;

/**
 * 充值服务类
 * @author LG
 *
 */
public interface IRechargeService {

	/**
	 * 查询单个话费直充商品
	 * @param mobile 充值手机
	 * @param rechargeAmount 充值金额
	 * @return
	 */
	BmRechargeMobileGetItemInfoResponse getItemInfo(String mobile,String rechargeAmount) throws ApiException;
	
	/**
	 * 查询手机账号详情
	 * @param mobile 查询手机号码
	 * @param respType 可选值： area:仅返回归属地信息(查询速度快) detail:仅返回账户余额(貌似不能用)，all:同时查询归属地和账户余额，默认则仅返回归属地信息
	 * @return
	 */
	BmRechargeMobileGetPhoneInfoResponse getPhoneInfo(String mobile,String respType) throws ApiException;
	
	/**
	 * 话费订单充值 
	 * @param mobile 充值手机号
	 * @param rechargeAmount 充值金额
	 * @param outerTid 商户订单号
	 * @param callback 回调url
	 * @param itemId 话费充值商品编号，使用getItemInfo接口查询，如果传递则使用传递的商品编号进行充值，否则系统自动内部匹配商品
	 * @return
	 */
	BmRechargeMobilePayBillResponse payBill(String mobile,String rechargeAmount,String outerTid,String callback,String itemId) throws ApiException;
	
	/**
	 * 根据第三方订单号查询订单详情（充值系统订单号）
	 * @param billId
	 * @return
	 */
	BmRechargeOrderInfoResponse getOrderInfo(String billId) throws ApiException;
	
	/**
	 * 根据商户订单号查询订单详情
	 * @param outerTid
	 * @return
	 */
	BmOrderCustomGetResponse getOrderInfoByOuterTid(String outerTid) throws ApiException;
}
