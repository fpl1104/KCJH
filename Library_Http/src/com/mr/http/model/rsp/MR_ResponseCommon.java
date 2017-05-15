package com.mr.http.model.rsp;

/**
 * 公共返回信息
 */
public class MR_ResponseCommon {

	/** 返回错误信息 */
	private  String message;
	/** 返回错误码 */
	private  String returnCode;
	/** 系统时间 */
	private  String serviceTime;
	/** session Id */
	private  String cookieFromResponse;

	public String getCookieFromResponse() {
		return cookieFromResponse;
	}

	public void setCookieFromResponse(String cookieFromResponse) {
		this.cookieFromResponse = cookieFromResponse;
	}

	public  String getMessage() {
		return message;
	}

	public  void setMessage(String message) {
		this.message = message;
	}

	public  String getReturnCode() {
		return returnCode;
	}

	public  void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public  String getServiceTime() {
		return serviceTime;
	}

	public  void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}

}