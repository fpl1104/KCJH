package kechuang.mr.com.kcjh.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;

import com.mr.http.util.HttpUtils;
import com.mr.http.util.IRequestListener;
import com.mr.http.util.LogManager;

import java.io.File;
import java.util.List;
import java.util.Map;

import kechuang.mr.com.kcjh.R;

public class RequestUtils {

	static String webUrl = null;

	public static void getData(String ServiceType, Map<String, String> params, Context context, IRequestListener requestListener,
			String TAG, Dialog dialog) {

//		if (TextUtils.equals(Service.MR_SNOWBALL, ServiceType)) {
//			webUrl = Config.getConfigOpenCardUrl();
////		}else if (TextUtils.equals(Service.MR_SNOWBALL_R, ServiceType)) {
////			webUrl = Config.getConfigSztRechargeUrl();
//		}else {
//			webUrl = Config.getConfigWebUrl() + ServiceType + Config.getConfigWebUrlSuffix();
//		}
		LogManager.e("TAG", webUrl);
		/** 判断是否有网 */
		if (NetworkUtils.checkNet(context) && !TextUtils.isEmpty(webUrl)) {
			/** 执行网络请求 */
			HttpUtils.request((Activity) context, ServiceType, requestListener, params, webUrl, TAG);
		} else {
			if (dialog !=null) {
				DialogDismiss(dialog);
			}
			ToastUtils.show(context, context.getResources().getString(R.string.toast_no_internet));
		}
	}

	/**
	 * 上传单个文件
	 * 
	 * @param ServiceType
	 * @param params
	 * @param file
	 * @param requestListener
	 */
	public static void upLoadFile(String ServiceType, Map<String, String> params, File file, Context context,
                                  IRequestListener requestListener, String TAG, Dialog dialog) {
//		String webUrl = Config.getConfigWebUrl() + ServiceType + ".jsp";
//		LogManager.e("TAG", webUrl);
		/** 判断是否有网 */
		if (NetworkUtils.checkNet(context)) {
			/** 执行网络请求 */
			HttpUtils.multipartRequest((Activity) context, ServiceType, requestListener, file, params, webUrl, TAG);
		} else {
			DialogDismiss(dialog);
			ToastUtils.show(context, context.getResources().getString(R.string.toast_no_internet));
		}
	}

	/**
	 * 上传多个文件
	 * 
	 * @param ServiceType
	 * @param params
	 * @param files
	 * @param requestListener
	 */
	public static void upLoadFile(String ServiceType, Map<String, String> params, List<File> files, Context context,
                                  IRequestListener requestListener, String TAG, Dialog dialog) {
//		String webUrl = Config.getConfigWebUrl() + ServiceType + ".jsp";
//		LogManager.e("TAG", webUrl);
		/** 判断是否有网 */
		if (NetworkUtils.checkNet(context)) {
			/** 执行网络请求 */
			HttpUtils.multipartRequest((Activity) context, ServiceType, requestListener, files, params, webUrl, TAG);
		} else {
			DialogDismiss(dialog);
			ToastUtils.show(context, context.getResources().getString(R.string.toast_no_internet));
		}
	}

	/**
	 * 隐藏加载圈
	 */
	public static void DialogDismiss(Dialog dialog) {
		if (dialog.isShowing() && dialog != null) {
			dialog.dismiss();
		}
	}
}
