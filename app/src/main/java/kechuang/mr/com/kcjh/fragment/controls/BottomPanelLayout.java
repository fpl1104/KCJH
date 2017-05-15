package kechuang.mr.com.kcjh.fragment.controls;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import kechuang.mr.com.kcjh.R;


/**
 */
public class BottomPanelLayout extends LinearLayout {

    private int normalIcon;
    private int focusIcon;
    private boolean isFocused = false;
    private ImageView ivIcon;
    private TextView tvText;
    /**
     * 文字默认颜色
     */
    private int defaulColor = 0xff878A91;
    /**
     * 文字选中颜色
     */
    private int focusedColor = 0xff666666;

    public BottomPanelLayout(Context context, AttributeSet attrs ) {
        super( context, attrs );
        //加载布局文件，与setContentView()效果一样
        LayoutInflater.from( context ).inflate( R.layout.fragment_bottom_layout, this );
        ivIcon = (ImageView) findViewById( R.id.img_main_bottom_icon );
        tvText = (TextView) findViewById( R.id.txt_main_bottom_text );
    }

    public void setNormalIcon( int normalIcon ) {
        this.normalIcon = normalIcon;
        ivIcon.setImageResource( normalIcon );
    }

    public void setFocusIcon( int focusIcon ) {
        this.focusIcon = focusIcon;
    }

    public void setIconText( String text ) {
        if ( text == null )
            tvText.setVisibility( GONE );
        tvText.setText( text );
    }

    public void setFocused( boolean isFocused ) {
        //如果已经处在设置的状态中，就不进行操作
        if ( this.isFocused != isFocused ) {
            this.isFocused = isFocused;
            if ( isFocused ) {
                //设置获得焦点后的图片
                //文字加粗
                ivIcon.setImageResource( focusIcon );
                tvText.setTypeface( Typeface.defaultFromStyle( Typeface.NORMAL ) );
                tvText.setTextColor( focusedColor );
            } else {
                //设置获得普通状态的图片
                //文字不加粗
                ivIcon.setImageResource( normalIcon );
                tvText.setTextColor( defaulColor );
                tvText.setTypeface( Typeface.defaultFromStyle( Typeface.NORMAL ) );
            }
        }
    }

    public ImageView getIvIcon() {
        return ivIcon;
    }
}
