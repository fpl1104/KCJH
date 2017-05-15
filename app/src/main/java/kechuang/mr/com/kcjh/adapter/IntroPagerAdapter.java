package kechuang.mr.com.kcjh.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 引导页面的适配器
 */
public class IntroPagerAdapter extends PagerAdapter {

	private List<? extends View> mListViews;

	public IntroPagerAdapter(List<? extends View> listviews) {
		mListViews = listviews;
	}

	@Override
	public int getCount() {
		return mListViews != null ? mListViews.size() : 0;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(mListViews.get(position));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View view = mListViews.get(position);
		container.addView(view);
		return view;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
}