package com.example.uimodule.titlebar;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.uimodule.R;

public class TitleBar extends LinearLayout {
    public TitleBar(Context context) {
        this(context,null);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
       super(context,attrs,defStyleAttr);
       init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {

        LayoutInflater.from(getContext()).inflate(R.layout.uimodule_title_bar, this);
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.TitleBar);//解析布局

        View viewById = findViewById(R.id.container);
        LinearLayout.LayoutParams layoutParams = (LayoutParams) viewById.getLayoutParams();
        layoutParams.topMargin = getStatusBarHeight();
        viewById.setLayoutParams(layoutParams);

    }


    public int getStatusBarHeight() {
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }


    public void setDarkStatusWhite(boolean bDark, Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        //修改状态栏颜色只需要这行代码
        activity.getWindow().setStatusBarColor(getResources().getColor(android.R.color.white));//这里对应的是状态栏的颜色，就是style中colorPrimaryDark的颜色
        int vis = decorView.getSystemUiVisibility();
        if (bDark) {
            vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        } else {
            vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
        decorView.setSystemUiVisibility(vis);
    }

}
