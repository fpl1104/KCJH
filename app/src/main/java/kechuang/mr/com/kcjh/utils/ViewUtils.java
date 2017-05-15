package kechuang.mr.com.kcjh.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout.LayoutParams;

import java.lang.reflect.Field;

/**
 * View 工具类
 */
public class ViewUtils {

	private static final String CLASS_NAME_GRID_VIEW = "android.widget.GridView";
	private static final String FIELD_NAME_VERTICAL_SPACING = "mVerticalSpacing";


	/**
	 * 根据每一个children得到ListView高度
	 * 
	 * @param view
	 * @return
	 */
	public static int getListViewHeightBasedOnChildren(ListView view) {
		int height = getAbsListViewHeightBasedOnChildren(view);
		ListAdapter adapter;
		int adapterCount;
		if (view != null && (adapter = view.getAdapter()) != null && (adapterCount = adapter.getCount()) > 0) {
			height += view.getDividerHeight() * (adapterCount - 1);
		}
		return height;
	}

	/**
	 * 
	 * 获取GridView垂直间距
	 * 
	 * @param view
	 * @return
	 */
	public static int getGridViewVerticalSpacing(GridView view) {
		Class<?> demo = null;
		int verticalSpacing = 0;
		try {
			demo = Class.forName(CLASS_NAME_GRID_VIEW);
			Field field = demo.getDeclaredField(FIELD_NAME_VERTICAL_SPACING);
			field.setAccessible(true);
			verticalSpacing = (Integer) field.get(view);
			return verticalSpacing;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return verticalSpacing;
	}

	/**
	 * 根据每一个children得到AbsListView高度
	 * 
	 * @param view
	 * @return
	 */
	public static int getAbsListViewHeightBasedOnChildren(AbsListView view) {
		ListAdapter adapter;
		if (view == null || (adapter = view.getAdapter()) == null) {
			return 0;
		}
		int height = 0;
		for (int i = 0; i < adapter.getCount(); i++) {
			View item = adapter.getView(i, null, view);
			if (item instanceof ViewGroup) {
				item.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			}
			item.measure(0, 0);
			height += item.getMeasuredHeight();
		}
		height += view.getPaddingTop() + view.getPaddingBottom();
		return height;
	}

	/**
	 * 设置视图的高度
	 * 
	 * @param view
	 * @param height
	 */
	public static void setViewHeight(View view, int height) {
		if (view == null) {
			return;
		}

		ViewGroup.LayoutParams params = view.getLayoutParams();
		params.height = height;
	}

	/**
	 * 设置 ListView 高度
	 * 
	 * @param view
	 * @return
	 */
	public static void setListViewHeightBasedOnChildren(ListView view) {
		setViewHeight(view, getListViewHeightBasedOnChildren(view));
	}

	/**
	 * 设置 AbsListView 高度
	 * 
	 * @param view
	 * @return
	 */
	public static void setAbsListViewHeightBasedOnChildren(AbsListView view) {
		setViewHeight(view, getAbsListViewHeightBasedOnChildren(view));
	}

}
