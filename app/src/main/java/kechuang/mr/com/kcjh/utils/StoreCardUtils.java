package kechuang.mr.com.kcjh.utils;

import android.os.Environment;

public class StoreCardUtils {

	/**
	 * 判断SDCard是否可用
	 *
	 * @return
	 */
	public static boolean isSDCardEnable() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

}
