package kechuang.mr.com.kcjh.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.mr.http.init.MR_ApplicationController;
import com.mr.http.util.HttpUtils;
import com.mr.http.util.IRequestListener;
import com.mr.http.util.LogManager;
import com.mr.utils.net.NetworkUtils;
import com.mr.utils.ui.ToastUtils;
import com.mr.utils.ui.V;

import java.util.Map;

import butterknife.ButterKnife;
import kechuang.mr.com.kcjh.R;
import kechuang.mr.com.kcjh.config.Config;
import kechuang.mr.com.kcjh.dialog.CommonLoadingDialog;
import kechuang.mr.com.kcjh.impl.HandlerImpl;
import kechuang.mr.com.kcjh.ui.thread.UIHandler;
import kechuang.mr.com.kcjh.utils.ActivityStackManager;
import kechuang.mr.com.kcjh.utils.PermissionHelper;


/**
 * activity基类
 */
public abstract class CommonBaseActivity extends FragmentActivity {

    protected final String TAG = getClass().getSimpleName();

    protected UIHandler handler = new UIHandler(Looper.getMainLooper());
    // 当前上下文
    protected Context mContext = null;
    // 意图跳转
    protected Intent mIntent = null;
    // 页面参数
    protected Bundle paras = null;
    // 权限
    public PermissionHelper permissionHelper;
    // 加载圈
    public CommonLoadingDialog loadingDialog = null;

    protected static final int RC_PERM = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBase();
        initContentView(savedInstanceState);
        ButterKnife.bind(this);
        setHandler();
        initData();
        onListener();
    }


    @Override
    protected void onResume() {
        super.onResume();
        setHandler();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ToastUtils.cancel();
        MR_ApplicationController.cancelPendingRequests(TAG);
        handler.removeCallbacksAndMessages(null); // 当前页面销毁的时候 不再接受消息
        // MR_ApplicationController.cancelPendingRequests( TAG );
        ActivityStackManager.getAppManager().finishActivity(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    /**
     * 初始化UI，setContentView等
     *
     * @param savedInstanceState 缓存
     */
    protected abstract void initContentView(Bundle savedInstanceState);


    /**
     * 初始化数据
     */
    protected abstract void initData();



    /**
     * activity.findViewById()
     *
     * @param id
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T f(int id) {
        return (T) V.f(this, id);
    }

    /**
     * activity.findViewById()
     *
     * @param activity
     * @param id
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T f(Activity activity, int id) {
        return (T) V.f(activity, id);
    }

    /**
     * rootView.findViewById()
     *
     * @param rootView
     * @param id
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T f(View rootView, int id) {
        return (T) V.f(rootView, id);
    }


    /**
     * 获取服务器数据
     *
     * @param
     * @param params
     */
    public void getData(String ServiceType, Map<String, String> params, IRequestListener requestListener) {
//		String webUrl = Config.getConfigWebUrl() + ServiceType + Config.getConfigWebUrlSuffix();
        String webUrl = Config.configWebUrl + ServiceType + Config.webUrlSuffix;
        /** 判断是否有网 */
        if (NetworkUtils.checkNet(mContext)) {
            /** 执行网络请求 */
            HttpUtils.request(CommonBaseActivity.this, ServiceType, requestListener, params, webUrl, TAG);
        } else {
            ToastUtils.show(mContext, getResources().getString(R.string.toast_no_internet));
        }
    }

    /**
     * 事件监听
     */
    protected abstract void onListener();

    /**
     * 让子类处理消息
     *
     * @param msg 消息
     */
    protected abstract void handler(Message msg);

    /**
     * 设置基础信息
     */
    private void setBase() {
        // 无标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 输入框默认隐藏
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // 当前上下文
        mContext = this;
        // 添加Activity到堆栈
        ActivityStackManager.getAppManager().addActivity(this);
        // 获取 Intent
        mIntent = getIntent();
        // 页面参数
        paras = mIntent.getExtras();
        // 判断paras为空
        if (paras == null) {
            paras = new Bundle();
        }
        if (loadingDialog == null) {
            loadingDialog = new CommonLoadingDialog(this);
        }
    }

    /**
     * 容错处理
     *
     * @param view
     */
    public void onClick(View view) {

    }

    /**
     * 设置Handler
     */
    @SuppressLint("HandlerLeak")
    private void setHandler() {
        handler.setHandler(new HandlerImpl() {
            public void handleMessage(Message msg) {
                /** 有消息就提交给子类实现 */
                handler(msg);
            }
        });
    }

    /**
     * 横竖屏切换，键盘等
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {

        super.onActivityResult(arg0, arg1, arg2);
        LogManager.d(this.getClass().getSimpleName() + ".onActivityResult", arg1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /** 弹出框取消 */
        // ToastUtils.cancel();
        ToastUtils.cancel();
        LogManager.i(TAG, getString(R.string.net_request_cancel));
        MR_ApplicationController.cancelPendingRequests(TAG);
    }
    /**
     * 隐藏加载圈
     */
    public void hideLoadingDialog() {
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permissionHelper != null)
            permissionHelper.handleRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
