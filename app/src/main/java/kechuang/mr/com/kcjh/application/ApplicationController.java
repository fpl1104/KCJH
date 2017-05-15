package kechuang.mr.com.kcjh.application;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.mr.http.init.MR_ApplicationController;
import com.mr.http.util.LogManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ApplicationController extends Application {

	public static final String TAG = "ApplicationController";
	private static ApplicationController application;


	@Override
	public void onCreate() {
		super.onCreate();

		application = this;

		init();




	}

	/**
	 * 初始化
	 */
	public void init() {
		// 初始化文件夹
		createFolder();
		// // 获取公共参数
		SetRequestHeaderData.getData(this);
		// 初始化 网络框架
		MR_ApplicationController.initData(this, SetRequestHeaderData.mHeader);
		// 初始化是否打印日志
		LogManager.init(true);
	}


	/**
	 * @return ApplicationController singleton instance
	 */
	public static synchronized ApplicationController getInstance() {
		return application;
	}

	/**
	 * 系统字体修改处理
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		if (newConfig.fontScale != 1)// 非默认值
			getResources();
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public Resources getResources() {
		Resources res = super.getResources();
		if (res.getConfiguration().fontScale != 1) {// 非默认值
			Configuration newConfig = new Configuration();
			newConfig.setToDefaults();// 设置默认
			res.updateConfiguration(newConfig, res.getDisplayMetrics());
		}
		return res;
	}


	public void createFolder() {
		File file = new File(Environment.getExternalStorageDirectory() + "/XDL/");
		if (!file.exists() && !file.isDirectory())
			file.mkdir();
		return;
	}


	/**
	 * 分包处理
	 */
	@Override
	protected void attachBaseContext(Context base) {
		// TODO Auto-generated method stub
		super.attachBaseContext(base);
		MultiDex.install(this);

	}
	/**
	 * 获取进程号对应的进程名
	 *
	 * @param pid 进程号
	 * @return 进程名
	 */
	private static String getProcessName(int pid) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
			String processName = reader.readLine();
			if (!TextUtils.isEmpty(processName)) {
				processName = processName.trim();
			}
			return processName;
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
		return null;
	}
}
