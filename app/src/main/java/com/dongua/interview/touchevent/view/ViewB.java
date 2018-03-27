package com.dongua.interview.touchevent.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * author: Lewis
 * data: On 18-2-23.
 */

public class ViewB extends android.support.v7.widget.AppCompatTextView {
    private static final String TAG = "TouchEvent";

    public ViewB(Context context) {
        super(context);
    }

    public ViewB(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewB(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "dispatchTouchEvent: ViewB");
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent: ViewB");
        return super.onTouchEvent(event);
    }
}