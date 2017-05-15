package kechuang.mr.com.kcjh.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;

/**
 * Created by LXZ on 2016-10-27.
 */
public class ViewHelper {

    private final String TAG = "ViewHelper";

    @TargetApi( Build.VERSION_CODES.JELLY_BEAN )
    public static void setListViewHeightBasedOnChildren( GridView listView ) {
        // 获取listview的adapter
        ListAdapter listAdapter = listView.getAdapter();
        if ( listAdapter == null ) {
            return;
        }
        // 固定列宽，有多少列
        int col = 4;// listView.getNumColumns();
        int totalHeight = 0;
        // i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
        // listAdapter.getCount()小于等于8时计算两次高度相加
        for ( int i = 0; i < listAdapter.getCount(); i += col ) {
            // 获取listview的每一个item
            View listItem = listAdapter.getView( i, null, listView );
            listItem.measure( 0, 0 );
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
            if ( i > 0 )
                totalHeight += listView.getVerticalSpacing();
        }

        // 获取listview的布局参数
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // 设置高度
        params.height = totalHeight;
        // 设置margin
        ( (ViewGroup.MarginLayoutParams) params ).setMargins( 10, 10, 10, 10 );
        // 设置参数
        listView.setLayoutParams( params );
    }

    public static int[] getViewSize(View view){
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int height = view.getMeasuredHeight();
        int width = view.getMeasuredWidth();
        return new int[]{width, height};
    }

    public static int[] getDeviceSize( Activity activity ){
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int Displaywidth = metric.widthPixels; // 屏幕宽度（像素）
        int Displayheight = metric.heightPixels; // 屏幕高度（像素）
        float density = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi; // 屏幕密度DPI（120 / 160 / 240）
        return new int[]{Displaywidth, Displayheight};
    }
}
