package kechuang.mr.com.kcjh.utils;

import android.content.Context;

import kechuang.mr.com.kcjh.R;


/**
 * 双击退出Activity的类
 */
public class ExitDoubleClick extends DoubleClick {

	private static ExitDoubleClick exit;

	private ExitDoubleClick(Context context) {
		super(context);
		setDoubleClickListener(new DoubleClickListener() {
			@Override
			public void afteDoubleClick() {
				ActivityStackManager.getAppManager().AppExit(mContext);
				destroy();
			}
		});
	}

	/**
	 * 返回一个双击退出的实例
	 * 
	 * @param context
	 * @return ExitDoubleClick
	 */
	public static synchronized ExitDoubleClick getInstance(Context context) {
		if (exit == null) {
			exit = new ExitDoubleClick(context);
		}
		return exit;
	}

	/**
	 * 双击退出Activity，如果msg为null，而默认显示的提示语为"再按一次退出"
	 */
	@Override
	public void doDoubleClick(int delayTime, String msg) {
		if (msg == null || msg.equals("")) {
			msg = mContext.getResources().getString(R.string.toast_click_exit);
		}
		super.doDoubleClick(delayTime, msg);
	}

	private static void destroy() {
		exit = null;
	}
}
