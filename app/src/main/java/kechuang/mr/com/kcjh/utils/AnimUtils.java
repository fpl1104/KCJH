package kechuang.mr.com.kcjh.utils;

import android.view.View;
import android.view.animation.AlphaAnimation;

/**
 * 动画 工具类
 */
public class AnimUtils {

	/**
	 * 开场动画
	 * 
	 * @param view
	 *            资源view
	 * @param playerTime
	 *            播放时间，单位为毫秒
	 * @param startAlpha
	 *            开始时的透明程度。0-255之间
	 */
	public static void viewAnimatio(View view, int playerTime, float startAlpha) {
		AlphaAnimation animation = new AlphaAnimation(startAlpha, 1f);
		animation.setDuration(playerTime);
		view.startAnimation(animation);
	}
}
