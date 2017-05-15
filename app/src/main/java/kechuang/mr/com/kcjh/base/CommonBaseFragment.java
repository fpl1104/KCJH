package kechuang.mr.com.kcjh.base;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kechuang.mr.com.kcjh.fragment.CardFragment;
import kechuang.mr.com.kcjh.fragment.HomeFragment;
import kechuang.mr.com.kcjh.fragment.InfoFragment;
import kechuang.mr.com.kcjh.fragment.LifeFragment;
import kechuang.mr.com.kcjh.fragment.MyFragment;
import kechuang.mr.com.kcjh.fragment.constant.FragmentConstant;
import kechuang.mr.com.kcjh.impl.HandlerImpl;
import kechuang.mr.com.kcjh.ui.thread.UIHandler;


/**
 * Fragment 基类
 */
@SuppressLint("NewApi")
public abstract class CommonBaseFragment extends Fragment {

	protected final String TAG = getClass().getSimpleName();
	protected static UIHandler handler = new UIHandler(Looper.getMainLooper());
	protected View thisView;
	public Context mContext;
	public Bundle paras;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setBase();
		thisView = initContentView(inflater, container, savedInstanceState);
		setHandler();
		initData();
		onListener();
		return thisView;
	}

	/**
	 * 设置基础信息
	 */
	private void setBase() {
		if (mContext == null) {
			mContext = this.getActivity();
		}
		/** 判断paras为空 */
		if (paras == null) {
			paras = new Bundle();
		}
	}


	/**
	 * 初始化UI
	 * 
	 * @param inflater
	 * @param container
	 * @param savedInstanceState
	 */
	protected abstract View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

	/** 初始化数据 */
	public abstract void initData();

	/** 事件监听 */
	protected abstract void onListener();

	/**
	 * 让子类处理消息
	 * 
	 * @param msg
	 */
	protected abstract void handler(Message msg);

	/**
	 * 设置Handler
	 */
	private void setHandler() {
		handler.setHandler(new HandlerImpl() {
			public void handleMessage(Message msg) {
				/** 有消息就提交给子类实现 */
				handler(msg);
			}
		});
	}

	public void setParas(Bundle paras) {
		this.paras = paras;
	}

	public Bundle getParas() {
		return paras;
	}

	public void refreshView() {
		initData();
		onListener();
	}

	/**
	 * 初始化 BaseFragment
	 * 
	 * @param tag
	 * @return
	 */
	public static CommonBaseFragment getInstance(String tag) {

		CommonBaseFragment fragment = null;
		if (TextUtils.equals(tag, FragmentConstant.STR_FRAGMENT_INFO)) {
			fragment = new InfoFragment();
		} else if (TextUtils.equals(tag, FragmentConstant.STR_FRAGMENT_HOME)) {
			fragment = new HomeFragment();
		} else if (TextUtils.equals(tag, FragmentConstant.STR_FRAGMENT_CARD)) {
			fragment = new CardFragment();
		} else if (TextUtils.equals(tag, FragmentConstant.STR_FRAGMENT_MY)) {
			fragment = new MyFragment();
		} else if (TextUtils.equals(tag, FragmentConstant.STR_FRAGMENT_LIFE)) {
			fragment = new LifeFragment();
		}
		return fragment;
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		/** 弹出框取消 */
		// ToastUtils.cancel();
		// MR_ApplicationController.cancelPendingRequests(TAG);
	}
}
