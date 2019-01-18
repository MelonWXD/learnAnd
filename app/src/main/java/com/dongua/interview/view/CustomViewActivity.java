package com.dongua.interview.view;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.dongua.interview.R;
import com.example.common.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;

import static android.view.View.OVER_SCROLL_NEVER;

/**
 * @CreateDate: 2019/1/16 下午5:13
 * @Author: Lewis Weng
 * @Description:
 */
public class CustomViewActivity extends BaseActivity {
    @BindView(R.id.rv_main)
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    FlexibleAdapterHelper helper;
    ArrayList<String> data;

    @Override
    public int getLayoutID() {
        return R.layout.activity_custom_view;
    }

    @Override
    public void init() {
        super.init();

        data = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            data.add("文本" + i);
        }
        adapter = new TestAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        helper = new FlexibleAdapterHelper(recyclerView, adapter);
        recyclerView.setAdapter(adapter);
        //去掉滑动边界阴影
        recyclerView.setOverScrollMode(OVER_SCROLL_NEVER);
        recyclerView.setOnTouchListener(new UpDownScrollListener(true, true));

    }

    private Rect topRect = new Rect();
    // y方向上当前触摸点的前一次记录位置
    private int previousY = 0;
    // y方向上的触摸点的起始记录位置
    private int startY = 0;
    // y方向上的触摸点当前记录位置
    private int currentY = 0;
    // y方向上两次移动间移动的相对距离
    private int deltaY = 0;

    private float moveHeight;

    private boolean handleTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) event.getY();
                previousY = startY;

                topRect.set(recyclerView.getLeft(), recyclerView.getTop(),
                        recyclerView.getRight(), recyclerView.getBottom());
                Log.i("wxddd", "ACTION_DOWN: " + topRect.toShortString());
                moveHeight = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                currentY = (int) event.getY();
                deltaY = currentY - previousY;
                previousY = currentY;

                //判定是否在顶部或者滑到了底部
                if ((!recyclerView.canScrollVertically(-1) && (currentY - startY) > 0) || (!recyclerView.canScrollVertically(1) && (currentY - startY) < 0)) {
                    //计算阻尼
                    float distance = currentY - startY;
                    if (distance < 0) {
                        distance *= -1;
                    }

                    float damping = 0.5f;//阻尼值
                    float height = recyclerView.getHeight();
                    if (height != 0) {
                        if (distance > height) {
                            damping = 0;
                        } else {
                            damping = (height - distance) / height;
                        }
                    }
                    if (currentY - startY < 0) {
                        damping = 1 - damping;
                    }

                    //阻力值限制再0.3-0.5之间，平滑过度
                    damping *= 0.25;
                    damping += 0.25;

                    moveHeight = moveHeight + (deltaY * damping);

                    recyclerView.layout(topRect.left, (int) (topRect.top + moveHeight), topRect.right,
                            (int) (topRect.bottom + moveHeight));



                }
                break;
            case MotionEvent.ACTION_UP:
                if (!topRect.isEmpty()) {
                    //开始回移动画
                    upDownMoveAnimation();
                    // 子控件回到初始位置
                    recyclerView.layout(topRect.left, topRect.top, topRect.right,
                            topRect.bottom);
                }
                //重置一些参数
                startY = 0;
                currentY = 0;
                topRect.setEmpty();
                break;

        }
        return false;

    }

    private void upDownMoveAnimation() {
        TranslateAnimation animation = new TranslateAnimation(0.0f, 0.0f,
                recyclerView.getTop(), topRect.top);
        animation.setDuration(600);
        animation.setFillAfter(true);
        //设置阻尼动画效果
        animation.setInterpolator(new DampInterpolator());
        recyclerView.setAnimation(animation);
    }

    public class DampInterpolator implements Interpolator {
        @Override
        public float getInterpolation(float input) {
            //没看过源码，猜测是input是时间（0-1）,返回值应该是进度（0-1）
            //先快后慢，为了更快更慢的效果，多乘了几次，现在这个效果比较满意
            return 1 - (1 - input) * (1 - input) * (1 - input) * (1 - input) * (1 - input);
        }
    }

    class TestAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_test, parent, false);
            return new TestHolder(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            TestHolder testHolder = (TestHolder) holder;
            testHolder.textView.setText(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class TestHolder extends RecyclerView.ViewHolder {
            TextView textView;

            public TestHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.tv_test);
            }
        }
    }
}
