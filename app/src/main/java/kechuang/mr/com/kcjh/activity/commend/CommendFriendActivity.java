package kechuang.mr.com.kcjh.activity.commend;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.mr.utils.ui.JumpUtils;

import butterknife.BindView;
import butterknife.OnClick;
import kechuang.mr.com.kcjh.R;
import kechuang.mr.com.kcjh.activity.MainActivity;
import kechuang.mr.com.kcjh.base.CommonBaseActivity;

public class CommendFriendActivity extends CommonBaseActivity {
    @BindView(R.id.my_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.btn_follow)Button btn_follow;

    /**
     * 初始化UI，setContentView等
     *
     * @param savedInstanceState 缓存
     */
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_commend_friend);

    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {

    }
    @OnClick(R.id.btn_follow)
    public void oneKeyFollow(View view){
        JumpUtils.invokeActivity(mContext, MainActivity.class,"",paras);
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
