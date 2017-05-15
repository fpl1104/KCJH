package kechuang.mr.com.kcjh.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kechuang.mr.com.kcjh.R;
import kechuang.mr.com.kcjh.activity.MainActivity;
import kechuang.mr.com.kcjh.base.CommonBaseFragment;
import kechuang.mr.com.kcjh.utils.PermissionHelper;


/**
 * 主页
 */
public class HomeFragment extends CommonBaseFragment {

    private PermissionHelper mHelper;


    @SuppressLint("ResourceAsColor")
    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        thisView = inflater.inflate(R.layout.fragment_home_layout, container, false);
        return thisView;
    }

    @Override
    public void initData() {
        mHelper = ((MainActivity) getActivity()).permissionHelper;
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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mHelper.handleRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
