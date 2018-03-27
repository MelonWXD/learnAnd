package com.dongua.interview.touchevent.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * author: Lewis
 * data: On 18-2-23.
 */

public class ViewC extends android.support.v7.widget.AppCompatTextView {
    private static final String TAG = "TouchEvent";

    public ViewC(Context context) {
        super(context);
    }

    public ViewC(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewC(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "dispatchTouchEvent: ViewC");
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent: ViewC");
        return super.onTouchEvent(event);
    }
}