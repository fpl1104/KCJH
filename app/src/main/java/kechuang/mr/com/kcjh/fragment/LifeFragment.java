package kechuang.mr.com.kcjh.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kechuang.mr.com.kcjh.R;
import kechuang.mr.com.kcjh.base.CommonBaseFragment;


/**
 * 理财
 */
public class LifeFragment extends CommonBaseFragment {


	@Override
	protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		thisView = inflater.inflate(R.layout.fragment_life_layout, container, false);
		return thisView;
	}


	@Override
	public void initData() {
	}


	@Override
	protected void handler(Message msg) {
	}

	@Override
	protected void onListener() {
	}


	@Override
	public void setParas(Bundle paras) {
		super.setParas(paras);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

}
