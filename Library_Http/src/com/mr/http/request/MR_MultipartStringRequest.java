package com.mr.http.request;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import android.os.Bundle;

import com.mr.http.MR_NetworkResponse;
import com.mr.http.MR_Request;
import com.mr.http.MR_Response;
import com.mr.http.error.MR_AuthFailureError;
import com.mr.http.error.MR_ParseError;
import com.mr.http.mime.MR_MultipartEntity;
import com.mr.http.mime.content.MR_FileBody;
import com.mr.http.mime.content.MR_StringBody;
import com.mr.http.toolbox.MR_FLog;
import com.mr.http.toolbox.MR_HttpHeaderParser;

public class MR_MultipartStringRequest extends MR_Request<String> {

	private MR_MultipartEntity entity = new MR_MultipartEntity();
	private static String FILE_PART_NAME = "";

	private final MR_Response.Listener<String> mListener;
	private final File mFilePart;

	public MR_MultipartStringRequest(String url, MR_Response.ErrorListener errorListener,
			MR_Response.Listener<String> listener, String key, File file, Bundle parameters) {
		super(Method.POST, url, errorListener);
		FILE_PART_NAME = key;
		mListener = listener;
		mFilePart = file;

		buildMultipartEntity(parameters);
	}

	private void buildMultipartEntity(Bundle parameters) {
		entity = encodePOSTUrl(entity, parameters);
		entity.addPart(FILE_PART_NAME, new MR_FileBody(mFilePart));
	}

	public static MR_MultipartEntity encodePOSTUrl(MR_MultipartEntity mEntity, Bundle parameters) {
		if (parameters != null && parameters.size() > 0) {
			boolean first = true;
			for (String key : parameters.keySet()) {
				if (key != null) {
					if (first) {
						first = false;
					}
					String value = "";
					Object object = parameters.get(key);
					if (object != null) {
						value = String.valueOf(object);
					}
					try {
						mEntity.addPart(key, new MR_StringBody(value));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return mEntity;
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
			MR_FLog.e("IOException writing to ByteArrayOutputStream");
		}
		return bos.toByteArray();
	}

	@Override
	protected MR_Response<String> parseNetworkResponse(MR_NetworkResponse response) {
		try {
			try {
				String jsonString = new String(response.data, "utf-8");
				return MR_Response.success(jsonString, MR_HttpHeaderParser.parseCacheHeaders(response));
			} catch (UnsupportedEncodingException e) {
				return MR_Response.error(new MR_ParseError(e));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void deliverResponse(String response, String requestType) {
		try {
			mListener.onResponse(response, requestType);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
