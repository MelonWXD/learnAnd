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
    LinearLayoutManager layoutManager;

    @Override
    public int getLayoutID() {
        return R.layout.activity_custom_view;
    }

    @Override
    public void init() {
        super.init();

        data = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            data.add("文本" + i);
        }
        adapter = new TestAdapter();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        helper = new FlexibleAdapterHelper(recyclerView, adapter);
        recyclerView.setAdapter(adapter);
        //去掉滑动边界阴影
        recyclerView.setOverScrollMode(OVER_SCROLL_NEVER);
        recyclerView.setNestedScrollingEnabled(true);
        UpDownScrollListener scrollListener = new UpDownScrollListener(true, true);
        recyclerView.setOnTouchListener(scrollListener);
        recyclerView.setOnFlingListener(new FlingBackListener(recyclerView));

    }

    private boolean handleFling(int velocityY) {
//        recyclerView.viewf
        return true;
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
