package kechuang.mr.com.kcjh.activity;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.mr.http.error.MR_VolleyError;
import com.mr.http.util.IRequestListener;

import org.json.JSONObject;

import kechuang.mr.com.kcjh.R;
import kechuang.mr.com.kcjh.base.CommonBaseActivity;
import kechuang.mr.com.kcjh.base.CommonBaseFragment;
import kechuang.mr.com.kcjh.fragment.constant.FragmentConstant;
import kechuang.mr.com.kcjh.fragment.controls.BottomPanelLayout;
import kechuang.mr.com.kcjh.utils.ExitDoubleClick;
import kechuang.mr.com.kcjh.utils.PermissionHelper;

@SuppressLint("NewApi")
public class MainActivity extends CommonBaseActivity implements View.OnClickListener ,IRequestListener {
    
    /**
     * Fragment 管理器
     */
    public FragmentManager mFragmentManager;
    /**
     * Fragment 事务处理
     */
    private FragmentTransaction mFragmentTransaction = null;
    /**
     * 当前页面标签
     */
    public static String currFagTag = null;

    private BottomPanelLayout bpl_life;
    private BottomPanelLayout bpl_home;
    private BottomPanelLayout bpl_card;
    private BottomPanelLayout bpl_info;
    private BottomPanelLayout bpl_my;
    public android.os.Handler refreshHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    setTabSection(FragmentConstant.STR_FRAGMENT_LIFE);
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * 设置默认第一屏
     */
    private void defaultFirstScreen() {
        setTabSection(FragmentConstant.STR_FRAGMENT_HOME);
    }


    /**
     * 初始化底部导航
     */
    private void initBottomLayout() {
        bpl_life = (BottomPanelLayout) findViewById(R.id.bottom_icon_life);
        bpl_home = (BottomPanelLayout) findViewById(R.id.bottom_icon_home);
        bpl_card = (BottomPanelLayout) findViewById(R.id.bottom_icon_card);
        bpl_info = (BottomPanelLayout) findViewById(R.id.bottom_icon_info);
        bpl_my = (BottomPanelLayout) findViewById(R.id.bottom_icon_my);

        bpl_home.setNormalIcon(R.drawable.agree_nomal);
        bpl_home.setFocusIcon(R.drawable.agree_nomal);
        bpl_home.setIconText(getString(R.string.fm_home));
        bpl_home.setFocused(true);
        bpl_home.setOnClickListener(this);

        bpl_life.setNormalIcon(R.drawable.agree_nomal);
        bpl_life.setFocusIcon(R.drawable.agree_nomal);
        bpl_life.setIconText(getString(R.string.fm_life));
        bpl_life.setFocused(false);
        bpl_life.setOnClickListener(this);

        bpl_card.setNormalIcon(R.drawable.agree_nomal);
        bpl_card.setFocusIcon(R.drawable.agree_nomal);
        bpl_card.setIconText(null);
        bpl_card.setFocused(false);
        bpl_card.setOnClickListener(this);

        bpl_info.setNormalIcon(R.drawable.agree_nomal);
        bpl_info.setFocusIcon(R.drawable.agree_nomal);
        bpl_info.setIconText(getString(R.string.fm_info));
        bpl_info.setFocused(false);
        bpl_info.setOnClickListener(this);

        bpl_my.setNormalIcon(R.drawable.agree_nomal);
        bpl_my.setFocusIcon(R.drawable.agree_nomal);
        bpl_my.setIconText(getString(R.string.fm_my));
        bpl_my.setFocused(false);
        bpl_my.setOnClickListener(this);

    }

    /**
     * 初始化UI，setContentView等
     *
     * @param savedInstanceState 缓存
     */
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        permissionHelper = new PermissionHelper( this );
        initBottomLayout();
        initData();
		/*
		 * 初始化导航第一屏
		 */
        defaultFirstScreen();

    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        mFragmentManager = getFragmentManager();
    }
    /**
     * Tab点击切换
     *
     * @param tag
     *            标志
     */
    private void setTabSection(String tag) {
        if (TextUtils.equals(tag, currFagTag)) {
            return;
        }
        bpl_life.setFocused(false);
        bpl_home.setFocused(false);
        bpl_card.setFocused(false);
        bpl_info.setFocused(false);
        bpl_my.setFocused(false);
        switch (tag) {
            case FragmentConstant.STR_FRAGMENT_HOME:
                bpl_home.setFocused(true);
                break;
            case FragmentConstant.STR_FRAGMENT_INFO:
                bpl_info.setFocused(true);
                break;
            case FragmentConstant.STR_FRAGMENT_CARD:
                bpl_card.setFocused(true);
                break;
            case FragmentConstant.STR_FRAGMENT_MY:
                bpl_my.setFocused(true);
                break;
            case FragmentConstant.STR_FRAGMENT_LIFE:
                bpl_life.setFocused(true);
                break;
        }
        ensureTransaction();
        if (currFagTag != null && !currFagTag.equals(""))
            hideFragment(getFragment(currFagTag));
        showFragment(R.id.fragment_panel, getFragment(tag), tag);
        commitTransaction();
        currFagTag = tag;

    }
    /**
     * 将view从view tree中删除
     *
     * @param fragment
     */
    @SuppressLint("NewApi")
    private void hideFragment(Fragment fragment) {
        if (fragment != null && !fragment.isDetached()) {
            mFragmentTransaction.hide(fragment);
        }
    }

    /**
     * 创建Transaction
     */
    private void ensureTransaction() {
        if (mFragmentTransaction == null) {
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        }
    }

    /**
     * 获取当前Fragment
     *
     * @param tag
     * @return
     */
    private Fragment getFragment(String tag) {
        CommonBaseFragment fragment = (CommonBaseFragment) mFragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            fragment = CommonBaseFragment.getInstance(tag);
        }
        fragment.setParas(paras);
        return fragment;
    }

    /**
     * 提交section
     */
    private void commitTransaction() {
        if (mFragmentTransaction != null) {
            mFragmentTransaction.commit();
            mFragmentTransaction = null;
        }
    }

    @SuppressLint("NewApi")
    private void showFragment(int layoutId, Fragment fragment, String tag) {
        if (fragment != null) {
            if (!fragment.isAdded()) {
                mFragmentTransaction.add(layoutId, fragment, tag);
            }
            if (!fragment.isVisible()) {
                mFragmentTransaction.show(fragment);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bottom_icon_life:
                setTabSection(FragmentConstant.STR_FRAGMENT_LIFE);
                break;
            case R.id.bottom_icon_home:
                setTabSection(FragmentConstant.STR_FRAGMENT_HOME);
                break;
            case R.id.bottom_icon_card:
                setTabSection(FragmentConstant.STR_FRAGMENT_CARD);
                break;
            case R.id.bottom_icon_info:
                setTabSection(FragmentConstant.STR_FRAGMENT_INFO);
                break;
            case R.id.bottom_icon_my:
                setTabSection(FragmentConstant.STR_FRAGMENT_MY);
                break;
        }
    }

    /**
     * 事件监听
     */
    @Override
    protected void onListener() {

    }

    /**
     * 让子类处理消息
     *
     * @param msg 消息
     */
    @Override
    protected void handler(Message msg) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getFragment(currFagTag).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int pushId = getIntent().getIntExtra("pushID", 0);
        if (pushId == 1) {
			/*
			 * 初始化导航第一屏
			 */
            defaultFirstScreen();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ExitDoubleClick.getInstance(this).doDoubleClick(1500, R.string.toast_double_click);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    /**
     * @param ServiceAPI  请求标签
     * @param response    返回数据
     * @param requestType
     */
    @Override
    public void onResponse(String ServiceAPI, JSONObject response, String requestType) {

    }

    @Override
    public void onErrorResponse(String ServiceAPI, MR_VolleyError error, String requestType) {

    }
}
