package com.mr.http.init;

import java.util.HashMap;
import java.util.Map;

import android.app.Application;
import android.text.TextUtils;

import com.mr.http.MR_Request;
import com.mr.http.MR_RequestQueue;
import com.mr.http.MR_VolleyLog;
import com.mr.http.model.rsp.MR_ResponseCommon;
import com.mr.http.toolbox.MR_Volley;

public class MR_ApplicationController {

	public static final String TAG = "MR_ApplicationController";
	public static Application application;
	public static MR_RequestQueue mRequestQueue;
	public static MR_ResponseCommon responseCommon;
	public static Map<String, String> mHttpHeader;

	public static void initData(Application applicate, Map<String, String> requestHeader) {
		application = applicate;
		/** 公共参数实体类初始化 */
		responseCommon = new MR_ResponseCommon();
		// httpHeader为空处理
		if (requestHeader == null||requestHeader.size() == 0) {
			requestHeader = new HashMap<String, String>();
		}
		mHttpHeader = requestHeader;
	}

	/**
	 * @return ApplicationController singleton instance
	 */
	public static synchronized Application getInstance() {
		return application;
	}

	/**
	 * @return The Volley Request queue, the queue will be created if it is null
	 */
	public static MR_RequestQueue getRequestQueue() {
		// lazy initialize the request queue, the queue instance will be
		// created when it is accessed for the first time
		if (mRequestQueue == null) {
			synchronized (MR_ApplicationController.class) {
				if (mRequestQueue == null) {
					mRequestQueue = MR_Volley.newRequestQueue(application.getApplicationContext());
				}
			}
		}
		return mRequestQueue;
	}

	/**
	 * Adds the specified request to the global queue, if tag is specified then
	 * it is used else Default TAG is used.
	 *
	 * @param req
	 * @param tag
	 */
	public static <T> void addToRequestQueue(MR_Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		MR_VolleyLog.d("Adding request to queue: %s", req.getUrl());
		getRequestQueue().add(req);
	}

	/**
	 * Adds the specified request to the global queue using the Default TAG.
	 *
	 * @param req
	 * @param tag
	 */
	public static <T> void addToRequestQueue(MR_Request<T> req) {
		// set the default tag if tag is empty
		req.setTag(TAG);

		getRequestQueue().add(req);
	}

	/**
	 * Cancels all pending requests by the specified TAG, it is important to
	 * specify a TAG so that the pending/ongoing requests can be cancelled.
	 *
	 * @param tag
	 */
	public static void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}

}
