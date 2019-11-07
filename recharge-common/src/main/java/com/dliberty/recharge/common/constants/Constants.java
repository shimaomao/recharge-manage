package com.dliberty.recharge.common.constants;

/**
 * 常用常量
 * @author LG
 *
 */
public class Constants {
	
	/**
	 * 通用否
	 */
	public static int COMMON_FLAG_NO = 0;
	
	/**
	 * 通用是
	 */
	public static int COMMON_FLAG_YES = 1;
	
	public enum ErrorCode {
		UN_KNOWN_ERROR	(-1, "未知错误"), 
		PARAM_ERROR		(10000001, "请求参数为空/验证錯誤"), 
		UN_AUTHORIZE	(10000002, "未授权"), 
		OLD_VERSION		(10000003, "请求参数验证失败"), 
		REQ_ERROR		(10000004, "请求接口失败"), 
		UN_LOGIN		(10000005, "未登录，请重新登录"), 
		SQL_ERROR		(10000006, "sql语句错误/执行SQL超时"), 
		UN_INVALID		(10000007, "账号已禁用，请联系客服电话");

		private int code;
		private String desc;

		ErrorCode(int code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getDesc() {
			return desc;
		}
	}
	
	public enum VerifyStatus {
		VERIFY_SAVE	(10, "已保存"), 
		VERIFY_PUBLISH		(20, "已发布"), 
		VERIFY_EFFECT	(30, "已失效"); 

		private int code;
		private String desc;

		VerifyStatus(int code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getDesc() {
			return desc;
		}
	}
	
	
	
	
	
}
