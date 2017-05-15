package kechuang.mr.com.kcjh.utils;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

public class AutoScrollViewUtils {

	/**
	 * 这里判断出软键盘弹出,之后的逻辑可以自己定,1.隐藏 2.移动3.改变高度4.改变paddingTop.......都可以
	 * 
	 * @param root
	 *            最外层的View
	 * @param scrollToView
	 *            不想被遮挡的View,会移动到这个Veiw的可见位置
	 */

	private static void autoScrollView(final View root, final View scrollToView) {
		root.getViewTreeObserver().addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						Rect rect = new Rect();
						// 获取root在窗体的可视区域
						root.getWindowVisibleDisplayFrame(rect);
						// 获取root在窗体的不可视区域高度(被遮挡的高度)
						int rootInvisibleHeight = root.getRootView()
								.getHeight() - rect.bottom;
						// 若不可视区域高度大于150，则键盘显示
						if (rootInvisibleHeight > 150) {
							int[] location = new int[2];
							// 获取scrollToView在窗体的坐标
							scrollToView.getLocationInWindow(location);
							// 计算root滚动高度，使scrollToView在可见区域的底部
							int srollHeight = (location[1] + scrollToView
									.getHeight()) - rect.bottom;
							root.scrollTo(0, srollHeight);
						} else {
							// 键盘隐藏
							root.scrollTo(0, 0);
						}
					}
				});
	}
}
