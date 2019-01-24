package com.dongua.interview.view;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * @CreateDate: 2019/1/18 上午10:51
 * @Author: Lewis Weng
 * @Description: 支持对控件添加回弹效果，对多指操作不做额外处理
 */
public class UpDownScrollListener extends RecyclerView.OnFlingListener implements View.OnTouchListener {

    public static final float DEFAULT_DAMPING = 2F;
    public static final int MSG_COUNT_START = 1;
    public static final int MAX_COUNTS = 100;
    public static final int COUNT_DELAY_MS = 20;
    private static final int DEFAULT_DURATION_MS = 200;//默认的动画时间
    public static final float MAX_TRANS_HEIGHT = 150;//最高回弹距离

    //惯性超出相关
    private int countTimes;
    private View targetView; //子view
    private float curVelocity;//当前fling的速度
    private int maxFlingVelocity;//系统最大支持的fling速度
    private int minFlingVelocity;//同上，最小
    private boolean everChangedDir = false;

    //触边拖动相关
    private boolean fingerUp = true; //默认开启底部托边
    private boolean fingerDown = true;
    private Rect emptyRect;
    private float startY;
    private float endY;
    private float damping = DEFAULT_DAMPING;//阻尼值
    private boolean restore = false;//是否需要回弹
    private boolean canFling = true;//触边时 优先响应拖动 无视惯性回弹

    private DecelerateInterpolator decInterpolator = new DecelerateInterpolator();//弹出来减速
    private AccelerateInterpolator accInterpolator = new AccelerateInterpolator();//弹回去加速


    public UpDownScrollListener(View view) {
        targetView = view;
        init(targetView.getContext());
    }

    private void init(Context context) {
        maxFlingVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        minFlingVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
    }


    public void setDamping(float damping) {
        this.damping = damping;
    }


    public void setFingerUp(boolean fingerUp) {
        this.fingerUp = fingerUp;
    }

    public void setFingerDown(boolean fingerDown) {
        this.fingerDown = fingerDown;
    }

    Handler uiHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_COUNT_START:
                    countTimes++;
                    if (curVelocity < 0 && !targetView.canScrollVertically(-1)
                            || curVelocity > 0 && !targetView.canScrollVertically(1)) {
                        flingAnimate(calcDistance(curVelocity), DEFAULT_DURATION_MS);
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


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (emptyRect == null) {
                    //拿到view在父布局中的l t r b
                    emptyRect = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                }
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!v.canScrollVertically(-1) && fingerDown && event.getY() > startY
                        || !v.canScrollVertically(1) && fingerUp && event.getY() < startY) {
                    //与startY的绝对值变小 说明变换了方向
                    if (restore && !everChangedDir) {
                        everChangedDir = Math.abs(event.getY() - startY) < Math.abs(endY - startY);
                    }

                    endY = event.getY();
                    //拖得越远 阻尼应该越大
                    damping = calDamping(endY - startY);
                    int deltaY = (int) ((endY - startY) * damping);
                    v.layout(emptyRect.left,
                            emptyRect.top + deltaY,
                            emptyRect.right,
                            emptyRect.bottom + deltaY);
                    restore = true;
                    return everChangedDir;//一旦改变了方向 就吃下move事件
                }
                break;
            case MotionEvent.ACTION_UP:
                if (restore) {
//                    flingAnimate(0,DEFAULT_DURATION_MS);
                    restoreAnimation(v, emptyRect);
                    v.layout(emptyRect.left,
                            emptyRect.top,
                            emptyRect.right,
                            emptyRect.bottom);
                    restore = false;
                    damping = DEFAULT_DAMPING;//恢复默认值
                    everChangedDir = false;
                }
                break;
        }
        return false;
    }


    @Override
    public boolean onFling(int velocityX, int velocityY) {
        curVelocity = velocityY;
        if (canFling)
            uiHandler.sendEmptyMessage(MSG_COUNT_START);
        return false;
    }


    private float calcDistance(float velocity) {
        float v = Math.abs(velocity);
        return -Math.signum(velocity) * (v - minFlingVelocity) / maxFlingVelocity * MAX_TRANS_HEIGHT;
    }


    //根据滑动距离计算阻尼值
    private float calDamping(float y) {
        float x = (float) Math.sqrt(Math.abs(y));
        return x / 50f;
    }

    private void restoreAnimation(View view, Rect rect) {
        canFling = false;

        TranslateAnimation animation = new TranslateAnimation(
                0.0f, 0.0f,
                view.getTop(), rect.top);
        animation.setDuration(DEFAULT_DURATION_MS);
//        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                canFling = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //恢复的时候应该反过来，用减速插值
        animation.setInterpolator(decInterpolator);
        view.setAnimation(animation);
    }

    public void flingAnimate(float height, long duration) {
        ViewPropertyAnimatorCompat compat = ViewCompat
                .animate(targetView)
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
                    flingAnimate(0, DEFAULT_DURATION_MS);
                }

                @Override
                public void onAnimationCancel(View view) {

                }
            });
        }
        compat.start();
    }


}
