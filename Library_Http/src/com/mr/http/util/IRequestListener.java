package com.mr.http.util;

import org.json.JSONObject;

import com.mr.http.error.MR_VolleyError;

public interface IRequestListener {

	/**
	 * @param ServiceAPI
	 *            请求标签
	 * @param response
	 *            返回数据
	 * @param requestType
	 */
	void onResponse(String ServiceAPI, JSONObject response, String requestType);

	void onErrorResponse(String ServiceAPI, MR_VolleyError error, String requestType);
}
