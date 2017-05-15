package com.mr.http.request;

import com.mr.http.MR_NetworkResponse;
import com.mr.http.MR_Request;
import com.mr.http.MR_Response;
import com.mr.http.MR_Response.ErrorListener;
import com.mr.http.MR_Response.Listener;
import com.mr.http.error.MR_AuthFailureError;
import com.mr.http.error.MR_ParseError;
import com.mr.http.init.MR_ApplicationController;
import com.mr.http.toolbox.MR_CommonUtil;
import com.mr.http.toolbox.MR_HttpHeaderParser;
import com.mr.http.util.LogManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class MR_JsonObjectPostRequest extends MR_Request<JSONObject> {

	private Map<String, String> mMap;
	private Listener<JSONObject> mListener;
	private static Map<String, String> mHeader = new HashMap<String, String>();

	public MR_JsonObjectPostRequest(String url, Listener<JSONObject> listener, ErrorListener errorListener, Map<String, String> map) {
		super(MR_Request.Method.POST, url, errorListener);
		LogManager.i( "HTTP请求地址", url);
		mListener = listener;
		mMap = map;
	}

	@Override
	public Map<String, String> getHeaders() throws MR_AuthFailureError {
		/** 默认返回 return Collections.emptyMap(); */
		mHeader.put("Cookie", MR_ApplicationController.responseCommon.getCookieFromResponse());
		// LogManager.i("HTTP请求头上送参数",   mHeader);
		return mHeader;
	}

	/**
	 * Map是已经按照前面的方式,设置了参数的实例
	 */
	@Override
	protected Map<String, String> getParams() throws MR_AuthFailureError {
		/** 公共头数据 与 接口数据合并 */
		mMap = MR_CommonUtil.merge2Map(mMap, MR_ApplicationController.mHttpHeader);
//		 LogManager.i("HTTP请求上送参数",   mMap);
		// 上送为空字段捕获
		if (MR_CommonUtil.toJSON(mMap).contains("null")) {
			LogManager.json(MR_CommonUtil.toJSON(mMap));
		}
		return mMap;
	}

	/**
	 * 此处因为response返回值需要json数据,和JsonObjectRequest类一样即可
	 */
	@Override
	protected MR_Response<JSONObject> parseNetworkResponse(MR_NetworkResponse response) {

		try {
			// 读取返回cookie
			String rspHeadCookie = response.headers.get("Set-Cookie");
			// LogManager.i("HTTP请求头返回Cookies",   rspHeadCookie);

			// 1、会话结束 2、session失效
			if (rspHeadCookie != null) {
				MR_ApplicationController.responseCommon.setCookieFromResponse(rspHeadCookie);
			}
			// 返回数据
			String jsonString = new String(response.data, MR_HttpHeaderParser.parseCharset(response.headers));

			LogManager.i( "HTTP响应参数", jsonString);
			JSONObject rspJsonObject = new JSONObject(jsonString);

			/** 交易码 交易信息 */
			MR_ApplicationController.responseCommon.setReturnCode(rspJsonObject.optString("repCode"));
			MR_ApplicationController.responseCommon.setMessage(rspJsonObject.optString("repMsg"));
			MR_ApplicationController.responseCommon.setServiceTime(rspJsonObject.optString("serviceTime"));

			return MR_Response.success(rspJsonObject, MR_HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return MR_Response.error(new MR_ParseError(e));
		} catch (JSONException je) {
			return MR_Response.error(new MR_ParseError(je));
		}
	}

	@Override
	protected void deliverResponse(JSONObject response, String requestType) {
		// TODO Auto-generated method stub
		mListener.onResponse(response, requestType);
	}

}