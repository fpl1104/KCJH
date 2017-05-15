package kechuang.mr.com.kcjh.base;


import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import java.util.ArrayList;

import kechuang.mr.com.kcjh.view.CommonViewHolder;


/**
 * 通用适配器
 */
public abstract class CommonBaseAdapter<T> extends BaseAdapter {

	/** 实体类数据 */
	private ArrayList<T> mData;
	/** 布局id */
	private int mLayoutRes;

	public CommonBaseAdapter() {
	}

	public CommonBaseAdapter(ArrayList<T> mData, int mLayoutRes) {
		this.mData = mData;
		this.mLayoutRes = mLayoutRes;
	}

	@Override
	public int getCount() {
		return mData != null ? mData.size() : 0;
	}

	@Override
	public T getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CommonViewHolder holder = CommonViewHolder.bind(parent.getContext(), convertView, parent, mLayoutRes, position);
		bindView(holder, getItem(position));
		return holder.getItemView();
	}

	/**
	 * 控件绑定抽象类
	 *
	 * @param holder
	 * @param obj
	 */
	public abstract void bindView(CommonViewHolder holder, T obj);

	/**
	 * 添加一个元素
	 */
	public void add(T data) {
		if (mData == null) {
			mData = new ArrayList<>();
		}
		mData.add(data);
		notifyDataSetChanged();
	}

	/**
	 * 往特定位置，添加一个元素
	 *
	 * @param position
	 * @param data
	 */
	public void add(int position, T data) {
		if (mData == null) {
			mData = new ArrayList<>();
		}
		mData.add(position, data);
		notifyDataSetChanged();
	}

	/**
	 * 更新适配器数据源
	 */
	public void refresh(ArrayList<T> mData){
		if (mData == null) {
			mData = new ArrayList<>();
		}
		this.mData = mData;
		notifyDataSetChanged();
	}

	/**
	 * 在原数据基础上添加一个集合
	 */
	public void addAll(ArrayList<T> mData){
		if (mData == null) {
			mData = new ArrayList<>();
		}
		this.mData.addAll(mData);
		notifyDataSetChanged();
	}

	/**
	 * 移除一个元素
	 *
	 * @param data
	 */
	public void remove(T data) {
		if (mData != null) {
			mData.remove(data);
		}
		notifyDataSetChanged();
	}

	/**
	 * 移除特定位置一个元素
	 *
	 * @param position
	 */
	public void remove(int position) {
		if (mData != null) {
			mData.remove(position);
		}
		notifyDataSetChanged();
	}

	/**
	 * 清除数据
	 */
	public void clear() {
		if (mData != null) {
			mData.clear();
		}
		notifyDataSetChanged();
	}
}
