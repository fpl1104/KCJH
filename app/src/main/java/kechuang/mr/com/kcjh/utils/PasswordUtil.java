package kechuang.mr.com.kcjh.utils;

import android.content.Context;

import com.mr.http.util.LogManager;

import kechuang.mr.com.kcjh.config.Config;

/**
 * Created by keith_fan on 2017/2/27.
 */

public class PasswordUtil {
    /**
     * 获取设置过的密码
     */
    public static String getPin(Context context) {
        String password = ConfigUtil.getInstance(context).getString(Config.PASS_KEY);
        LogManager.e("TAG",password+"");
        return password;
    }
}
