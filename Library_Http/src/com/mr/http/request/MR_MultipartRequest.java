package com.mr.http.request;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.mr.http.MR_NetworkResponse;
import com.mr.http.MR_Request;
import com.mr.http.MR_Response;
import com.mr.http.MR_VolleyLog;
import com.mr.http.config.MR_HttpConfig;
import com.mr.http.error.MR_AuthFailureError;
import com.mr.http.error.MR_ParseError;
import com.mr.http.init.MR_ApplicationController;
import com.mr.http.mime.MR_MultipartEntity;
import com.mr.http.mime.content.MR_FileBody;
import com.mr.http.mime.content.MR_StringBody;
import com.mr.http.toolbox.MR_CommonUtil;
import com.mr.http.toolbox.MR_FLog;
import com.mr.http.toolbox.MR_HttpHeaderParser;
import com.mr.http.util.LogManager;

/**
 * 单一/多文件/图片上传
 */
public class MR_MultipartRequest extends MR_Request<JSONObject> {

	private MR_MultipartEntity entity = new MR_MultipartEntity();
	private MR_Response.Listener<JSONObject> mListener;
	private List<File> mFileParts;
	private String mFilePartName;
	private Map<String, String> mParams;

	/**
	 * 单个文件＋参数 上传
	 * 
	 * @param url
	 * @param listener
	 * @param errorListener
	 * @param filePartName
	 * @param file
	 * @param params
	 */
	public MR_MultipartRequest(String url, MR_Response.Listener<JSONObject> listener, MR_Response.ErrorListener errorListener, String filePartName, File file,
			Map<String, String> params) {
		super(Method.POST, url, errorListener);
		mFileParts = new ArrayList<File>();
		if (file != null && file.exists()) {
			mFileParts.add(file);
		} else {
			MR_FLog.e("MultipartRequest---file not found");
		}
		mFilePartName = filePartName;
		mListener = listener;
		mParams = params;
		buildMultipartEntity();

	}

	/**
	 * 多个文件＋参数上传
	 * 
	 * @param url
	 * @param listener
	 * @param errorListener
	 * @param filePartName
	 * @param files
	 * @param params
	 */
	public MR_MultipartRequest(String url, MR_Response.Listener<JSONObject> listener, MR_Response.ErrorListener errorListener, String filePartName, List<File> files,
			Map<String, String> params) {
		super(Method.POST, url, errorListener);
		mFilePartName = filePartName;
		mListener = listener;
		mFileParts = files;
		mParams = params;
		buildMultipartEntity();
	}

	@Override
	protected MR_Response<JSONObject> parseNetworkResponse(MR_NetworkResponse response) {
		try {
			// 读取返回cookie
			String rspHeadCookie = response.headers.get("Set-Cookie");
			 LogManager.i("HTTP请求头返回 Cookies" ,  rspHeadCookie);

			// 1、会话结束 2、session失效
			if (rspHeadCookie != null) {
				MR_ApplicationController.responseCommon.setCookieFromResponse(rspHeadCookie);
			}

			// 返回数据
			String jsonString = new String(response.data, MR_HttpHeaderParser.parseCharset(response.headers));
			LogManager.i("HTTP响应参数", jsonString);
			JSONObject rspJsonObject = new JSONObject(jsonString);
			/** 交易码 交易信息 */
			MR_ApplicationController.responseCommon.setReturnCode(rspJsonObject.optString("returnCode"));
			MR_ApplicationController.responseCommon.setMessage(rspJsonObject.optString("returnMsg"));
			MR_ApplicationController.responseCommon.setServiceTime(rspJsonObject.optString("serviceTime"));

			return MR_Response.success(rspJsonObject, MR_HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return MR_Response.error(new MR_ParseError(e));
		} catch (JSONException je) {
			return MR_Response.error(new MR_ParseError(je));
		}
	}

	@Override
	public Map<String, String> getHeaders() throws MR_AuthFailureError {
		Map<String, String> headers = super.getHeaders();

		if (headers == null || headers.equals(Collections.emptyMap())) {
			headers = new HashMap<String, String>();
		}
		headers.put("Cookie", MR_ApplicationController.responseCommon.getCookieFromResponse());
//		 LogManager.i(TAG+"请求头上送参数 : ",   headers);

		return headers;
	}

	@Override
	public String getBodyContentType() {
		return entity.getContentType().getValue();
	}

	@Override
	public byte[] getBody() throws MR_AuthFailureError {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			entity.writeTo(bos);
		} catch (IOException e) {
			MR_VolleyLog.e("IOException writing to ByteArrayOutputStream");
		}
		return bos.toByteArray();
	}

	private void buildMultipartEntity() {
		if (mFileParts != null && mFileParts.size() > 0) {
			for (File file : mFileParts) {
				entity.addPart(mFilePartName, new MR_FileBody(file));
			}
			long l = entity.getContentLength();
			MR_VolleyLog.v(mFileParts.size() + "个，长度：" + l);
		}

		try {
			if (mParams != null && mParams.size() > 0) {
				/** 公共头数据 与 接口数据合并 */
				mParams = MR_CommonUtil.merge2Map(mParams, MR_ApplicationController.mHttpHeader);

				// 上送为空字段捕获
				if (MR_CommonUtil.toJSON(mParams).contains("null")) {
					LogManager.json(MR_CommonUtil.toJSON(mParams));
				}
				
				for (Map.Entry<String, String> entry : mParams.entrySet()) {
					entity.addPart(entry.getKey(), new MR_StringBody(entry.getValue(), Charset.forName(MR_HttpConfig.CONFIG_WEB_CHARSET)));
				}
			}
		} catch (UnsupportedEncodingException e) {
			MR_VolleyLog.e("UnsupportedEncodingException");
		}
	}

	@Override
	protected void deliverResponse(JSONObject response, String requestType) {
		// TODO Auto-generated method stub
		mListener.onResponse(response, requestType);
	}
}