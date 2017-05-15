package kechuang.mr.com.kcjh.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;




import java.util.List;

import kechuang.mr.com.kcjh.activity.MainActivity;
import kechuang.mr.com.kcjh.adapter.IntroPagerAdapter;
import kechuang.mr.com.kcjh.application.ApplicationController;
import kechuang.mr.com.kcjh.resource.ResourceIntro;
import kechuang.mr.com.kcjh.utils.ActivityStackManager;
import kechuang.mr.com.kcjh.utils.JumpUtils;

/**
 * 引导介绍页封装
 */
public abstract class CommonIntroActivity extends Activity implements OnClickListener, OnPageChangeListener {

	protected final String TAG = getClass().getSimpleName();
	private ResourceIntro mIntroResource;
	private ViewPager mViewPager;
	private List<ImageView> mIndicator;
	protected ApplicationController mApplicationController;
	/** 当前上下文 */
	protected Context mContext = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setBase();
		initContentView();
	}

	/**
	 * 设置基础信息
	 */
	private void setBase() {

		/** 无标题栏 */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		/** 输入框默认隐藏 */
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		/** Application */
		mApplicationController = (ApplicationController) getApplication();
		/** 当前上下文 */
		mContext = this;
		/** 添加Activity到堆栈 */
		ActivityStackManager.getAppManager().addActivity(this);

	}

	private void initContentView() {
		createContentView();
		showIntro();
	}

	/**
	 * 显示引导页面
	 */
	@SuppressWarnings("deprecation")
	private void showIntro() {
		IntroPagerAdapter adapter = new IntroPagerAdapter(mIntroResource.views);
		mViewPager.setAdapter(adapter);
		mViewPager.setOnPageChangeListener(this);
		// mIndicator.get(0).setImageResource(mIntroResource.indicatorSelectedId);
	}

	/**
	 * 创建布局的内容
	 */
	private void createContentView() {
		/** 设置资源 */
		mIntroResource = new ResourceIntro();
		setIntroViews(mIntroResource);

		/** 根布局 */
		RelativeLayout rootView = new RelativeLayout(this);
		rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		/** 滑动页 */
		mViewPager = new ViewPager(this);
		RelativeLayout.LayoutParams pagerParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		rootView.addView(mViewPager, pagerParams);

		/** 跳过 */
		ImageView jump = new ImageView(this);
		RelativeLayout.LayoutParams rl_jump = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		rl_jump.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		rl_jump.rightMargin = dp(10);
		rl_jump.topMargin = dp(10);
//		jump.setImageResource(R.drawable.jump);
		rootView.addView(jump, rl_jump);

		setContentView(rootView);
		// 跳过
		jump.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				JumpUtils.goNext(mContext, MainActivity.class, true);
			}
		});
	}

	private int dp(int value) {
		final float scale = this.getResources().getDisplayMetrics().density;
		return (int) (value * scale + 0.5f);
	}

	/**
	 * 更新指示器。
	 * 
	 * @param position
	 */
	private void updateIndicator(int position) {
		int size = mIndicator.size();
		if (position >= 0 && position < size) {
			for (int i = 0; i < size; i++) {
				mIndicator.get(i).setImageResource(mIntroResource.indicatorNoSelectedId);
			}
			mIndicator.get(position).setImageResource(mIntroResource.indicatorSelectedId);
		}
	}

	/**
	 * 设置当前要显示的视图页
	 * 
	 * @param position
	 */
	private void setCurrentView(int position) {
		if (position >= 0 && position < mIntroResource.views.size()) {
			mViewPager.setCurrentItem(position);
		}
	}

	/**
	 * 设置引导界面的资源，如显示的view，底部指示器的图片，间隔边距等
	 * 
	 * @param resource
	 */
	protected abstract void setIntroViews(ResourceIntro resource);

	@Override
	public void onClick(View v) {
		if (v instanceof ImageView) {
			int position = (Integer) v.getTag();
			setCurrentView(position);
			// updateIndicator(position);
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int pos) {
		// updateIndicator(pos);
	}
}
