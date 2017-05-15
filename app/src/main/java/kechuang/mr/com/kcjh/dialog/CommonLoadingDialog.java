package kechuang.mr.com.kcjh.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.TextView;

import kechuang.mr.com.kcjh.R;


/**
 * 公共 Dialog基类
 */
public class CommonLoadingDialog extends Dialog {

	/** 加载监听 */
	private LoadingDialogListener mLoadingDialogListener;
	/** 当前Activity */
	private Activity mActivity;
	/** 是否返回键关闭Dialog */
	private boolean mIsCanCancel = true;
	/** 是否关闭Activity */
	private boolean mCanFinishActivity = false;

	public CommonLoadingDialog(Activity context) {
		super(context, R.style.DialogLoading);
		mActivity = context;
		this.setContentView(R.layout.dialog_loading);
		this.setOwnerActivity(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().getAttributes().gravity = Gravity.CENTER;
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	public void setLoadingDialogListener(LoadingDialogListener ls) {
		mLoadingDialogListener = ls;
	}

	public void setMessage(int resId) {
		TextView tv = (TextView) findViewById(R.id.loading_message);
		if (tv != null) {
			tv.setText(resId);
		}
	}

	public void setMessage(String message) {
		TextView tv = (TextView) findViewById(R.id.loading_message);
		if (tv != null) {
			tv.setText(message);
		}
	}

	public boolean isCanCancel() {
		return mIsCanCancel;
	}

	public void setCanCancel(boolean isCanCancel) {
		this.mIsCanCancel = isCanCancel;
	}

	public void setFinishActivity(boolean flag) {
		mCanFinishActivity = flag;
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mIsCanCancel) {
				cancel();
				if (mCanFinishActivity) {
					mActivity.finish();
				}
			}
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}

	public void cancel() {
		super.cancel();
		if (mLoadingDialogListener != null) {
			mLoadingDialogListener.onLoadingDialogCancel();
		}
	}

	public interface LoadingDialogListener {
		void onLoadingDialogCancel();
	}
}
