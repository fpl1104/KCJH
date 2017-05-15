package kechuang.mr.com.kcjh.resource;

import android.view.View;

import java.util.List;

/**
 * 引导界面的资源类，用于设置需要显示的引导界面、底部指示器的格式
 */
public class ResourceIntro {

	/** 显示在view pager的界面 */
	public List<? extends View> views;
	/** 底部指示器中被选择的图片id */
	public int indicatorSelectedId;
	/** 底部指示器中没被选择的图片id */
	public int indicatorNoSelectedId;
	/** 指示器的外延边距。默认为24 */
	public int indicatorMarginBottom = 24;
	/** 指示器的各个图片外延边距 */
	public int indicatorImagePadding = 15;

	/**
	 * 引导界面资源类的构造方法。
	 */
	public ResourceIntro() {
		super();
	}

	/**
	 * 引导界面资源类的构造方法。
	 * 
	 * @param views
	 *            显示在view pager的界面
	 * @param indicatorSelectedId
	 *            底部指示器中被选择的图片id
	 * @param indicatorNoSelectedId
	 *            底部指示器中没被选择的图片id
	 */
	public ResourceIntro(List<? extends View> views, int indicatorSelectedId, int indicatorNoSelectedId) {
		super();
		this.views = views;
		this.indicatorSelectedId = indicatorSelectedId;
		this.indicatorNoSelectedId = indicatorNoSelectedId;
	}
}
