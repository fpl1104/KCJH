package com.mr.http.util;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.mr.http.MR_Response;
import com.mr.http.error.MR_VolleyError;
import com.mr.http.error.MR_VolleyErrorHelper;
import com.mr.http.init.MR_ApplicationController;
import com.mr.http.request.MR_JsonObjectPostRequest;
import com.mr.http.request.MR_MultipartRequest;
import com.mr.http.toolbox.MR_CommonUtil;

import android.app.Activity;

public class HttpUtils {

	/**
	 * 服务器请求封装
	 *
	 * @param ServiceAPI
	 * @param url
	 * @param requestListener
	 * @param params
	 * @param
	 */
	//测试studio上传
	public static void request(final Activity activity, final String ServiceType, final IRequestListener requestListener, Map<String, String> params, String webUrl,String TAG) {


		MR_ApplicationController.getRequestQueue();
		MR_JsonObjectPostRequest mJsonObjectRequest = new MR_JsonObjectPostRequest(

		webUrl, new MR_Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response, String requestType) {
				requestListener.onResponse(ServiceType, response, requestType);
			}
		}, new MR_Response.ErrorListener() {
			@Override
			public void onErrorResponse(MR_VolleyError error, String requestType) {
				requestListener.onErrorResponse(ServiceType, error, requestType);
				MR_CommonUtil.show(activity, MR_VolleyErrorHelper.getMessage(error));
			}
		}, params) {

		};
		mJsonObjectRequest.setShouldCache(true);
		MR_ApplicationController.addToRequestQueue(mJsonObjectRequest, TAG);
	}

	/**
	 * 上传文件请求封装, 单个文件
	 *
	 * @param activity
	 * @param ServiceType
	 * @param requestListener
	 * @param filePartName
	 * @param file
	 * @param params
	 * @param TAG
	 */
	public static void multipartRequest(final Activity activity, final String ServiceType, final IRequestListener requestListener, File file, Map<String, String> params,String webUrl, String TAG) {


		MR_ApplicationController.getRequestQueue();
		MR_MultipartRequest mMultipartRequest = new MR_MultipartRequest(webUrl, new MR_Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response, String requestType) {
				requestListener.onResponse(ServiceType, response, requestType);
			}
		}, new MR_Response.ErrorListener() {
			@Override
			public void onErrorResponse(MR_VolleyError error, String requestType) {
				// toast 提示
				MR_CommonUtil.show(activity, MR_VolleyErrorHelper.getMessage(error));
				requestListener.onErrorResponse(ServiceType, error, requestType);
			}
		},

		"f_file[]",// 注意这个key必须是f_file[],后面的[]不能少
				file, params) {

		};
		mMultipartRequest.setShouldCache(true);
		MR_ApplicationController.addToRequestQueue(mMultipartRequest, TAG);
	}

	/**
	 * 上传文件请求封装  多个文件
	 *
	 * @param activity
	 * @param ServiceType
	 * @param requestListener
	 * @param filePartName
	 * @param file
	 * @param params
	 * @param TAG
	 */
	public static void multipartRequest(final Activity activity, final String ServiceType, final IRequestListener requestListener, List<File> files, Map<String, String> params,String webUrl, String TAG) {


		MR_ApplicationController.getRequestQueue();
		MR_MultipartRequest mMultipartRequest = new MR_MultipartRequest(webUrl, new MR_Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response, String requestType) {
				requestListener.onResponse(ServiceType, response, requestType);
			}
		}, new MR_Response.ErrorListener() {
			@Override
			public void onErrorResponse(MR_VolleyError error, String requestType) {
				// toast 提示
				MR_CommonUtil.show(activity, MR_VolleyErrorHelper.getMessage(error));
				requestListener.onErrorResponse(ServiceType, error, requestType);
			}
		},

		"f_file[]",// 注意这个key必须是f_file[],后面的[]不能少
				files, params) {

		};
		mMultipartRequest.setShouldCache(true);
		MR_ApplicationController.addToRequestQueue(mMultipartRequest, TAG);
	}

}
