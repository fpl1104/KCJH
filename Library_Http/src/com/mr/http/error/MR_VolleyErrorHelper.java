package com.mr.http.error;

import com.mr.http.MR_NetworkResponse;
import com.mr.http.util.LogManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 网络 异常处理
 */
public class MR_VolleyErrorHelper {

	public final static String ERROR_GENERIC = "网络异常,请稍后再试";
	public final static String ERROR_GENERIC_NO_INTERNET = "无网络连接";
	public final static String ERROR_GENERIC_SERVER_DOWN = "连接服务器失败";
	public final static String ERROR_GENERIC_SERVER_DATA = "服务器异常";

	/**
	 * Returns appropriate message which is to be displayed to the user against
	 * the specified error object.
	 * 
	 * @param error
	 * @param context
	 * @return
	 */
	public static String getMessage(Object error) {
		LogManager.e(error);
		if (error instanceof MR_TimeoutError) {
			// TimeoutError：Socket超时，服务器太忙或网络延迟会产生这个异常。默认情况下，Volley的超时时间为2.5秒。如果得到这个错误可以使用RetryPolicy
			return ERROR_GENERIC_SERVER_DOWN;
		} else if (isServerProblem(error)) {
			// AuthFailureError：如果在做一个HTTP的身份验证，可能会发生这个错误。
			// ServerError：服务器的响应的一个错误，最有可能的4xx或5xx HTTP状态代码。
			return handleServerError(error);
		} else if (isNetworkProblem(error)) {
			// NetworkError：Socket关闭，服务器宕机，DNS错误都会产生这个错误。
			// NoConnectionError：和NetworkError类似，这个是客户端没有网络连接。
			return ERROR_GENERIC_NO_INTERNET;
		} else if (error instanceof MR_ParseError) {
			// ParseError：在使用JsonObjectRequest或JsonArrayRequest时，如果接收到的JSON是畸形，会产生异常。
			return ERROR_GENERIC_SERVER_DATA;
		}
		return ERROR_GENERIC;
	}

	/**
	 * Determines whether the error is related to network
	 * 
	 * @param error
	 * @return
	 */
	private static boolean isNetworkProblem(Object error) {
		return (error instanceof MR_NetworkError) || (error instanceof MR_NoConnectionError);
	}

	/**
	 * Determines whether the error is related to server
	 * 
	 * @param error
	 * @return
	 */
	private static boolean isServerProblem(Object error) {
		return (error instanceof MR_ServerError) || (error instanceof MR_AuthFailureError);
	}

	/**
	 * Handles the server error, tries to determine whether to show a stock
	 * message or to show a message retrieved from the server.
	 * 
	 * @param err
	 * @param context
	 * @return
	 */
	private static String handleServerError(Object err) {
		MR_VolleyError error = (MR_VolleyError) err;

		MR_NetworkResponse response = error.networkResponse;

		if (response != null) {
			switch (response.statusCode) {
			case 404:
			case 422:
			case 401:
				// server might return error like this { "error":
				// "Some error occured" }
				try {

					// Use "JSONObject" to parse the result
					JSONObject jsonObject = new JSONObject(new String(response.data));
					Map<String, String> result = new HashMap<String, String>();

					for (@SuppressWarnings("unchecked")
					Iterator<String> keys = jsonObject.keys(); keys.hasNext();) {
						String mapKey = keys.next();
						result.put(mapKey, jsonObject.optString(mapKey));
					}
					// // Use "Gson" to parse the result
					// HashMap<String, String> result = new Gson().fromJson(new
					// String(response.data),
					// new TypeToken<Map<String, String>>() {
					// }.getType());

					if (result != null && result.containsKey("error")) {
						return result.get("error");
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				// invalid request
				return error.getMessage();

			default:
				return ERROR_GENERIC_SERVER_DOWN;
			}
		}
		return ERROR_GENERIC;
	}
}