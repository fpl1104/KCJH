package kechuang.mr.com.kcjh.activity.login;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.mr.http.error.MR_VolleyError;
import com.mr.http.util.IRequestListener;
import com.mr.utils.ui.JumpUtils;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import kechuang.mr.com.kcjh.R;
import kechuang.mr.com.kcjh.activity.commend.CommendFriendActivity;
import kechuang.mr.com.kcjh.base.CommonBaseActivity;

public class LoginActivity extends CommonBaseActivity implements IRequestListener {
    @BindView(R.id.btn_login)
    Button btn_next;



    /**
     * 初始化UI，setContentView等
     *
     * @param savedInstanceState 缓存
     */
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);

    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {

    }
    @OnClick(R.id.btn_login)
    public void login(View view){
        JumpUtils.invokeActivity(mContext, CommendFriendActivity.class,"",paras);
    }
    @OnClick(R.id.tv_card)
    public void goToMemberCard(View view){
        JumpUtils.invokeActivity(mContext,LoginByMemberCardActivity.class,"",paras);
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
