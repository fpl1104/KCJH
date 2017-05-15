package kechuang.mr.com.kcjh.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Toast 工具类
 */
public class ToastUtils {

	private static Toast toast;

	public static void show(Context context, int resId) {
		show(context, context.getResources().getText(resId), Toast.LENGTH_SHORT);
	}

	public static void show(Context context, int resId, int duration) {
		show(context, context.getResources().getText(resId), duration);
	}

	public static void show(Context context, CharSequence text) {
		show(context, text, Toast.LENGTH_SHORT);
	}

	public static void show(Context context, int resId, Object... args) {
		show(context, String.format(context.getResources().getString(resId), args), Toast.LENGTH_SHORT);
	}

	public static void show(Context context, String format, Object... args) {
		show(context, String.format(format, args), Toast.LENGTH_SHORT);
	}

	public static void show(Context context, int resId, int duration, Object... args) {
		show(context, String.format(context.getResources().getString(resId), args), duration);
	}

	public static void show(Context context, String format, int duration, Object... args) {
		show(context, String.format(format, args), duration);
	}


	/**
	 * 普通 Toast 显示
	 * 
	 * @param context
	 * @param text
	 * @param duration
	 */
	public static void show(Context context, CharSequence text, int duration) {
		if (toast == null) {
			toast = Toast.makeText(context, text, duration);
		} else {
			toast.setText(text);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.setDuration(duration);
		}
		toast.show();
	}

	public static void showCenter(Context context, CharSequence text,  int gravity) {
		if (toast == null) {
			toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		} else {
			toast.setText(text);
			toast.setGravity(gravity, 0, 0);

		}
		toast.show();
	}

	public static void showCenter(Context context, int resId,  int gravity) {
		if (toast == null) {
			toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
		} else {
			toast.setText(resId);
			toast.setGravity(gravity, 0, 0);

		}
		toast.show();
	}

	/**
	 * Toast 取消
	 */
	public static void cancel() {
		if (toast != null) {
			toast.cancel();
		}
	}
}
