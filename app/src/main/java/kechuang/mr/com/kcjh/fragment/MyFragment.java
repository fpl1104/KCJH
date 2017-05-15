package kechuang.mr.com.kcjh.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kechuang.mr.com.kcjh.R;
import kechuang.mr.com.kcjh.base.CommonBaseFragment;


/**
 * 我的主页面
 */
public class MyFragment extends CommonBaseFragment {


    @SuppressLint( "ResourceAsColor" )
    @Override
    protected View initContentView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        thisView = inflater.inflate( R.layout.fragment_my_layout, container, false );
        return thisView;
    }


    @Override
    public void initData() {

    }

    @Override
    public void setParas( Bundle paras ) {
        super.setParas( paras );
    }

    @Override
    protected void onListener() {
    }

    @Override
    protected void handler( Message msg ) {
        switch ( msg.what ) {
            case 0:
                break;
        }
    }


    @Override
    public void onActivityResult( int requestCode, int resultCode, Intent data ) {
    }

}
