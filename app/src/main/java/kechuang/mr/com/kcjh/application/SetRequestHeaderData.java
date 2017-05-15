package kechuang.mr.com.kcjh.application;

import android.content.Context;

import com.mr.utils.app.ApplicationUtils;
import com.mr.utils.app.DeviceUtils;
import com.mr.utils.app.LocationUtils;
import com.mr.utils.net.NetworkUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于设置一些请求的基本数据，而且这些数据需要context，暂时放在application里面执行
 */
public class SetRequestHeaderData {

	public static Map<String, String> mHeader = new HashMap<>();

	public static void getData(Context context) {
		mHeader.put("plat", "0");// 0:android Mobile 1:android TV 2:ios
		mHeader.put("channel", "0");// 0:app 1:sdk
		mHeader.put("contentTyp", "application/x-www-form-urlencoded");// HTTP协议的Content-type(如text/html)
		mHeader.put("deviceMod", DeviceUtils.getDeviceMerchant());// 设备型号
		mHeader.put("deviceID", DeviceUtils.getPhoneIMEI(context));// 设备编号ID
		mHeader.put("opSys", "0");// 0:ANDROID 1:IOS
		mHeader.put("opSysVer", DeviceUtils.getSystemVersion());// 操作系统版本
		mHeader.put("networkTyp", String.valueOf(NetworkUtils.isNetworkAvailable(context)));// 联网方式
																							// 0：无网络
																							// 1：WIFI
																							// 2：CMWAP
																							// 3：CMNET
		mHeader.put("netServiceMer", DeviceUtils.getNetworkOperatorName(context));// 网络服务商
		mHeader.put("ipAddress", NetworkUtils.getIP_v4());// ip地址
		mHeader.put("deviceLanguage", DeviceUtils.getLanguage(context));// 语言环境
		mHeader.put("clientVer", String.valueOf(ApplicationUtils.getAppVersionCode(context)));// 客户端版本号
		mHeader.put("characterSet", "02");// 00-GBK
		mHeader.put("locationData", LocationUtils.mLocationData);//定位信息

	}
}
