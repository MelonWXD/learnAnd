package com.dongua.interview.view;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @CreateDate: 2019/1/16 下午4:36
 * @Author: Lewis Weng
 * @Description: 处理回弹的viewGroup
 */
public class FlexibleScrollLayout extends LinearLayout implements NestedScrollingParent {


    private View footerView;
    private int footerHeight = 400;//px
    private int footerWidth = 400;//px

    private boolean isRunAnim;
    private String footerColor = "0xDAFFF8";

    public FlexibleScrollLayout(Context context) {
        this(context, null);

    }

    public FlexibleScrollLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public FlexibleScrollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context ctx) {
        footerView = new TextView(ctx);
        ((TextView) footerView).setText("12312312312");

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutParams params;
        if (getOrientation() == VERTICAL) {
            params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, footerHeight);
        } else {
            params = new LayoutParams(footerWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        addView(footerView);
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {

    }

    /**
     * 返回true代表处理本次事件
     */
    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        if (target instanceof RecyclerView && !isRunAnim) {
            return true;
        }
        return false;
    }

    /**
     * 复位初始位置
     */
    @Override
    public void onStopNestedScroll(View target) {

    }


    class GoBackAnimation extends Animation {

    }
}
