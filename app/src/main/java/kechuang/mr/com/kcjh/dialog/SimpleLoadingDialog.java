package kechuang.mr.com.kcjh.dialog;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;

/**
 * 简单 LoadingDialog
 */
public class SimpleLoadingDialog extends CommonLoadingDialog {

	private View.OnClickListener mOnCancelListener;

	public SimpleLoadingDialog(Activity context, View.OnClickListener onCancelListener, boolean canCancel) {
		super(context);
		mOnCancelListener = onCancelListener;
		setCanCancel(canCancel);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mOnCancelListener != null) {
				mOnCancelListener.onClick(null);
			}
			if (isCanCancel()) {
				cancel();
			}
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}
}