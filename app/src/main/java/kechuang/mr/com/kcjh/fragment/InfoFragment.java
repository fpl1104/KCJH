package kechuang.mr.com.kcjh.fragment;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kechuang.mr.com.kcjh.R;
import kechuang.mr.com.kcjh.base.CommonBaseFragment;


/**
 * 主页面
 */
public class InfoFragment extends CommonBaseFragment
{

    @Override
    public void onActivityCreated( Bundle savedInstanceState )
    {
        super.onActivityCreated( savedInstanceState );
    }

    @Override
    protected View initContentView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        thisView = inflater.inflate( R.layout.fragment_info_layout, container, false );
        return thisView;
    }

    @Override
    public void initData()
    {

    }

    @Override
    protected void onListener()
    {

    }

    @Override
    protected void handler( Message msg )
    {
    }

}
