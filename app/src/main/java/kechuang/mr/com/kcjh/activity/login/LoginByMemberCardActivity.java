package kechuang.mr.com.kcjh.activity.login;

import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.mr.utils.ui.JumpUtils;

import butterknife.OnClick;
import kechuang.mr.com.kcjh.R;
import kechuang.mr.com.kcjh.activity.MainActivity;
import kechuang.mr.com.kcjh.base.CommonBaseActivity;

public class LoginByMemberCardActivity extends CommonBaseActivity {



    /**
     * 初始化UI，setContentView等
     *
     * @param savedInstanceState 缓存
     */
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login_by_member_card);

    }
    @OnClick(R.id.btn_login)
    public void goToHomePage(View view){
        JumpUtils.invokeActivity(mContext, MainActivity.class,"",paras);
    }
    /**
     * 初始化数据
     */
    @Override
    protected void initData() {

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
}
