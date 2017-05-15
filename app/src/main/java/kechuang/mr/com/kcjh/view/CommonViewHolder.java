package kechuang.mr.com.kcjh.view;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CommonViewHolder {

	/** 存储ListView 的 item中的View */
	private SparseArray<View> mViews;
	/** 存放convertView */
	private View item;
	/** 游标 */
	private int position;

	/**
	 * 构造方法，完成相关初始化
	 *
	 * @param context
	 * @param parent
	 * @param layoutRes
	 */
	private CommonViewHolder(Context context, ViewGroup parent, int layoutRes) {
		mViews = new SparseArray<>();
		View convertView = LayoutInflater.from(context).inflate(layoutRes, parent, false);
		convertView.setTag(this);
		item = convertView;
	}

	/**
	 * 绑定ViewHolder与item
	 */
	public static CommonViewHolder bind(Context context, View convertView, ViewGroup parent, int layoutRes, int position) {
		CommonViewHolder holder;
		if (convertView == null) {
			holder = new CommonViewHolder(context, parent, layoutRes);
		} else {
			holder = (CommonViewHolder) convertView.getTag();
			holder.item = convertView;
		}
		holder.position = position;
		return holder;
	}

	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int id) {
		T t = (T) mViews.get(id);
		if (t == null) {
			t = (T) item.findViewById(id);
			mViews.put(id, t);
		}
		return t;
	}

	/**
	 * 获取当前条目
	 */
	public View getItemView() {
		return item;
	}

	/**
	 * 获取条目位置
	 */
	public int getItemPosition() {
		return position;
	}

	/**
	 * 设置文字
	 */
	public CommonViewHolder setText(int id, CharSequence text) {
		View view = getView(id);
		if (view instanceof TextView) {
			((TextView) view).setText(text);
		}
		return this;
	}

	/**
	 * 设置文字颜色
	 */
	public CommonViewHolder setTextColor(int id, int color) {
		View view = getView(id);
		if (view instanceof TextView) {
			((TextView) view).setTextColor(color);
		}
		return this;
	}

	/**
	 * 设置图片
	 */
	public CommonViewHolder setImageResource(int id, int drawableRes) {
		View view = getView(id);
		if (view instanceof ImageView) {
			((ImageView) view).setImageResource(drawableRes);
		} else {
			view.setBackgroundResource(drawableRes);
		}
		return this;
	}

	/**
	 * 设置控件背景
	 */
	public CommonViewHolder setBackground(int id, int color) {
		View view = getView(id);
		view.setBackgroundColor(color);
		return this;
	}
	/**
	 * 设置控件背景
	 */
	public CommonViewHolder setBackgroundResource(int id, int resid) {
		View view = getView(id);
		view.setBackgroundResource(resid);
		return this;
	}

	/**
	 * 设置点击监听
	 */
	public CommonViewHolder setOnClickListener(int id, View.OnClickListener listener) {
		getView(id).setOnClickListener(listener);
		return this;
	}

	/**
	 * 设置可见
	 */
	public CommonViewHolder setVisibility(int id, int visible) {
		getView(id).setVisibility(visible);
		return this;
	}

	/**
	 * 设置标签
	 */
	public CommonViewHolder setTag(int id, Object obj) {
		getView(id).setTag(obj);
		return this;
	}

	/**
	 * 其他方法可自行扩展
	 */
}