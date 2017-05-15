package kechuang.mr.com.kcjh.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.view.View;

import kechuang.mr.com.kcjh.dialog.SimpleLoadingDialog;


/**
 * Dialog 工具类
 */
public class DialogUtils {

	private static SimpleLoadingDialog mSimpleLoadingDialog = null ;

	/**
	 * 显示加载框
	 * 
	 * @param activity
	 * @param text
	 * @param onCancelListener
	 * @param canCancel
	 */
	public static void showSimpleLoadingDialog(Activity activity, String text, View.OnClickListener onCancelListener, boolean canCancel) {
			if (mSimpleLoadingDialog != null && mSimpleLoadingDialog.isShowing()) {
				return;
			}
			mSimpleLoadingDialog = null;
			mSimpleLoadingDialog = new SimpleLoadingDialog(activity, onCancelListener, canCancel);
			mSimpleLoadingDialog.setCanceledOnTouchOutside(false);// 点击弹出框外部是否取消显示
			mSimpleLoadingDialog.setMessage(text);
			mSimpleLoadingDialog.show();
	}

	/**
	 * 显示系统弹出框
	 * 
	 * @param context
	 * @param intTitle
	 * @param intText
	 * @param icon
	 * @param textPositive
	 * @param listenPositive
	 * @param textNegative
	 * @param listenNegative
	 */
	public static void showSystemDialog(Context context, String intTitle, String intText, int icon, String textPositive, OnClickListener listenPositive,
			String textNegative, OnClickListener listenNegative) {
		AlertDialog.Builder tDialog = new AlertDialog.Builder(context);
		tDialog.setIcon(icon);
		tDialog.setTitle(intTitle);
		tDialog.setMessage(intText);
		tDialog.setCancelable(false);
		tDialog.setPositiveButton(textPositive, listenPositive);
		tDialog.setNegativeButton(textNegative, listenNegative);
		tDialog.show();
	}

	/**
	 * 关闭加载框
	 * 
	 * @param activity
	 * @param text
	 * @param onCancelListener
	 * @param canCancel
	 */
	public static void closeSimpleLoadingDialog() {
			if (mSimpleLoadingDialog != null && mSimpleLoadingDialog.isShowing()) {
				mSimpleLoadingDialog.dismiss();
				return;
			}
	}

	public static boolean isSimpleLoadingDialogShow() {
		return mSimpleLoadingDialog != null && mSimpleLoadingDialog.isShowing();
	}
}
