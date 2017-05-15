package kechuang.mr.com.kcjh;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;



import java.util.ArrayList;
import java.util.List;

import kechuang.mr.com.kcjh.activity.MainActivity;
import kechuang.mr.com.kcjh.base.CommonIntroActivity;
import kechuang.mr.com.kcjh.resource.ResourceIntro;
import kechuang.mr.com.kcjh.utils.AnimUtils;
import kechuang.mr.com.kcjh.utils.JumpUtils;

/**
 * 引导介绍页面
 */
public class IntroActivity extends CommonIntroActivity {

	@Override
	protected void setIntroViews(ResourceIntro resource) {

		List<View> views = new ArrayList<View>();
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		/** 初始化引导图片列表 */
		int[] pics = { R.drawable.img_guide_1, R.drawable.img_guide_2, };
		for (int i = 0; i < pics.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setLayoutParams(params);
			imageView.setImageResource(pics[i]);
			views.add(imageView);
			/** 图片是否拉伸满 */
			imageView.setScaleType(ScaleType.FIT_XY);
		}

		/** ---此处为开始按钮，此处可扩展--- */
		View lastView = View.inflate(this, R.layout.activity_intro, null);
		Button entryApp = (Button) lastView.findViewById(R.id.btn_enter_app );
		AnimUtils.viewAnimatio(entryApp, 1500, 0);
		entryApp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				JumpUtils.goNext(mContext, MainActivity.class, true);
			}
		});
		views.add(lastView);
		/** --------------------------- */

		resource.views = views;
		/** 设置联动小圆点 */
		resource.indicatorNoSelectedId = R.drawable.point;
		resource.indicatorSelectedId = R.drawable.point_hightlight;
	}
}
