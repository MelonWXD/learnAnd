package com.dongua.interview.view;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * @CreateDate: 2019/1/21 4:01 PM
 * @Author: Lewis Weng
 * @Description:
 */
public class OverScrollListener extends GestureDetector.SimpleOnGestureListener {

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.i("wxddd", "onFling: ");
        return super.onFling(e1, e2, velocityX, velocityY);
    }
}
