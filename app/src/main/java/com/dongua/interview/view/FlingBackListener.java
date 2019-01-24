package com.dongua.interview.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 * @CreateDate: 2019/1/23 2:38 PM
 * @Author: Lewis Weng
 * @Description:
 */
public class FlingBackListener extends RecyclerView.OnFlingListener {

    public static final int MSG_COUNT_START = 1;
    public static final int MAX_COUNTS = 100;
    public static final int COUNT_DELAY_MS = 20;
    private static final int DEFAULT_DURATION_MS = 200;//默认的动画时间
    public static final float MAX_TRANS_HEIGHT = 200;//最高回弹距离

    private int countTimes;
    private View targetChild; //子view
    private float curVelocity;//当前fling的速度
    private int maxFlingVelocity;//系统最大支持的fling速度
    private int minFlingVelocity;//同上，最小
    DecelerateInterpolator decInterpolator = new DecelerateInterpolator();//弹出来减速
    AccelerateInterpolator accInterpolator = new AccelerateInterpolator();//弹回去加速

    Handler uiHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_COUNT_START:
                    countTimes++;
                    if (curVelocity < 0 && !targetChild.canScrollVertically(-1)
                            || curVelocity > 0 && !targetChild.canScrollVertically(1)) {
                        doAnimate(calcDistance(curVelocity), DEFAULT_DURATION_MS);
                        removeCallbacksAndMessages(null);
                        countTimes = 0;
                    } else if (countTimes < MAX_COUNTS) {
                        sendEmptyMessageDelayed(MSG_COUNT_START, COUNT_DELAY_MS);
                    }
                    break;

                default:
                    break;
            }
        }
    };

    private float calcDistance(float velocity) {
        float v = Math.abs(velocity);
        return -Math.signum(velocity) * (v - minFlingVelocity) / maxFlingVelocity * MAX_TRANS_HEIGHT;
    }

    public FlingBackListener(View child) {
        targetChild = child;
        init(child.getContext());
    }


    private void init(Context context) {
        maxFlingVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        minFlingVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
    }

    public void doAnimate(float height, long duration) {
        ViewPropertyAnimatorCompat compat = ViewCompat
                .animate(targetChild)
                .translationY(height)
                .setDuration(duration);
        if (height == 0) {
            compat.setInterpolator(accInterpolator);
            compat.setListener(null);
        } else {
            compat.setInterpolator(decInterpolator);
            compat.setListener(new ViewPropertyAnimatorListener() {
                @Override
                public void onAnimationStart(View view) {

                }

                @Override
                public void onAnimationEnd(View view) {
                    doAnimate(0, DEFAULT_DURATION_MS);
                }

                @Override
                public void onAnimationCancel(View view) {

                }
            });
        }
        compat.start();
    }


    @Override
    public boolean onFling(int velocityX, int velocityY) {
        curVelocity = velocityY;
        uiHandler.sendEmptyMessage(MSG_COUNT_START);
        return false;
    }
}
