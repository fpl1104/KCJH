package kechuang.mr.com.kcjh;

import android.os.Bundle;
import android.os.Message;

import kechuang.mr.com.kcjh.activity.MainActivity;
import kechuang.mr.com.kcjh.base.CommonBaseActivity;
import kechuang.mr.com.kcjh.utils.JumpUtils;
import kechuang.mr.com.kcjh.utils.LaunchModeUtils;


public class SplashActivity extends CommonBaseActivity {

    @Override
    protected void initData() {
        handler.sendEmptyMessage(1);
    }

    private Class<?> getNextClass() {
//		 如果不是首次启动，则查询数据库中的用户
        if (LaunchModeUtils.getInstance().isFirstOpen()) {
            return IntroActivity.class;
        }
        return MainActivity.class;

    }

    /**
     * 页面跳转
     */
    private void goNext() {
        // 页面跳转
        final Class<?> nextClass = getNextClass();
        Runnable jumpThread = new Runnable() {
            public void run() {
                JumpUtils.goNext(mContext, nextClass, "", paras, true);
            }
        };
        handler.postDelayed(jumpThread, 1000);

    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        /** 打开标记 是否更新和首次启动 */
        LaunchModeUtils.getInstance().markOpenApp(mContext);
        // 启动两个守护服务
//        startService( new Intent( this, GuardProcessA.class ) );
//        startService( new Intent( this, GuardProcessB.class ) );
    }


    @Override
    protected void onListener() {
    }


    @Override
    protected void handler(Message msg) {
        switch (msg.what) {
            case 1:
                goNext();
                break;
            default:
                break;
        }
    }
}
