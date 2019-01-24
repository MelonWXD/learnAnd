package com.dongua.interview.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * @CreateDate: 2019/1/18 上午10:51
 * @Author: Lewis Weng
 * @Description: 支持对控件添加回弹效果，对多指操作不做额外处理
 * todo 支持左右滑动  更好的支持双指
 */
public class UpDownScrollListener extends RecyclerView.OnFlingListener implements View.OnTouchListener {

    public static final float DEFAULT_DAMPING = 2F;

    //是否打开上滑 下拉回弹效果
    private boolean upElastic = false;
    private boolean downElastic = false;
    private Rect emptyRect;
    private float startY;
    private float endY;
    private int deltaY;
    private float damping = DEFAULT_DAMPING;//阻尼值
    private boolean restore = false;

    public UpDownScrollListener(boolean upElastic, boolean downElastic) {
        this.upElastic = upElastic;
        this.downElastic = downElastic;
    }

    public void setDamping(float damping) {
        this.damping = damping;
    }

    //这里默认只对第一次点击的手指做处理，即getX(0),getY(0)

    boolean overScroll = false;//判断是否过度滑动
    OverScrollListener overScrollListener = new OverScrollListener();
    private GestureDetector detector = new GestureDetector(overScrollListener);
    boolean everChangedDir = false;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        detector.onTouchEvent(event);//传递点击事件
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (emptyRect == null) {
                    //拿到view在父布局中的l t r b
                    emptyRect = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                }
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!v.canScrollVertically(-1) && downElastic && event.getY()>startY
                        || !v.canScrollVertically(1) && upElastic && event.getY()<startY) {
                    //与startY的绝对值变小 说明变换了方向
                    if (restore && !everChangedDir) {
                        everChangedDir = Math.abs(event.getY() - startY) < Math.abs(endY - startY);
                    }

                    endY = event.getY();
                    //拖得越远 阻尼应该越大
                    damping = calDamping(endY - startY);
                    deltaY = (int) ((endY - startY) * damping);
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
                    restoreAnimation(v, emptyRect);
                    v.layout(emptyRect.left,
                            emptyRect.top,
                            emptyRect.right,
                            emptyRect.bottom);
                    restore = false;
                    damping = 2f;//恢复默认值
                    everChangedDir = false;
                }
                break;
        }
        return false;
    }

    //根据滑动距离计算阻尼值
    //有必要改成可配置?
    private float calDamping(float y) {
        float x = (float) Math.sqrt(Math.abs(y));
        return x / 50f;
    }

    private void restoreAnimation(View view, Rect rect) {
        TranslateAnimation animation = new TranslateAnimation(
                0.0f, 0.0f,
                view.getTop(), rect.top);
        animation.setDuration(500);
        animation.setFillAfter(true);
        //恢复的时候应该反过来，用减速插值
        animation.setInterpolator(new DecelerateInterpolator());
        view.setAnimation(animation);
    }

    //吃掉Fling事件 要帮view滑动
    @Override
    public boolean onFling(int velocityX, int velocityY) {
        return false;
    }
}
