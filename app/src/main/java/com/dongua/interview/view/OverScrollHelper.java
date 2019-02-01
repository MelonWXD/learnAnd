package com.dongua.interview.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @CreateDate: 2019/1/24 2:24 PM
 * @Author: Lewis Weng
 * @Description: 配置子view滑动入口类
 */
public class OverScrollHelper {
    public static void setup(View view) {
        if (view instanceof RecyclerView)
            setupRecyclerView((RecyclerView) view);
    }

    static void setupRecyclerView(RecyclerView view) {
        UpDownScrollListener scrollListener = new UpDownScrollListener(view);
//        view.setOnFlingListener(scrollListener);
        view.setOnTouchListener(scrollListener);
    }
}
