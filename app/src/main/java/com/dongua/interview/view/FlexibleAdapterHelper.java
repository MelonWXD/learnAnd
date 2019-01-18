package com.dongua.interview.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dongua.interview.R;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * @CreateDate: 2019/1/17 上午11:28
 * @Author: Lewis Weng
 * @Description: 代理模式包装adapter，支持回弹
 */
public class FlexibleAdapterHelper extends RecyclerView.Adapter {
    private boolean hasFooter;
    private RecyclerView.Adapter delegate;
    private int footWidth = 200;//default width in px
    private static final int ITEM_TYPE_FOOTER = 1001;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    private static final float PULL_DAMP = 0.5f;//上滑阻尼

    public FlexibleAdapterHelper(RecyclerView view, RecyclerView.Adapter adapter) {
        super();
        delegate = adapter;
        recyclerView = view;
        layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();


        setupRecyclerView();
    }

    int pos = -1;

    private void setupRecyclerView() {
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                if (SCROLL_STATE_IDLE == newState) {
//                    if (layoutManager.findLastVisibleItemPosition() == delegate.getItemCount()) {//最后一个view
////                        layoutManager.scrollToPosition(delegate.getItemCount() - 1);
//                        recyclerView.smoothScrollToPosition(pos);
//                    }
//                }
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (pos < 0 && layoutManager.findLastVisibleItemPosition() == delegate.getItemCount()) {//最后一个view
//                    recyclerView.smoothScrollToPosition(delegate.getItemCount() - 16);
//                    pos = layoutManager.findFirstCompletelyVisibleItemPosition();
//                }
//            }
//        });
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_FOOTER)
            return newFooterView(parent);
        return delegate.onCreateViewHolder(parent, viewType);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < delegate.getItemCount())
            delegate.onBindViewHolder(holder, position);
        else {
            Holder footer = (Holder) holder;
            footer.textView.setText("我是底部");
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position < delegate.getItemCount())
            return delegate.getItemViewType(position);
        else {
            return ITEM_TYPE_FOOTER;
        }
    }

    @Override
    public int getItemCount() {
        return delegate.getItemCount() + 1;
    }


    private RecyclerView.ViewHolder newFooterView(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.view_empty, parent, false);
        return new Holder(itemView);
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView textView;

        public Holder(View itemView) {
            super(itemView);
            itemView.getLayoutParams().height = 1000;
            itemView.setPadding(50, 200, 50, 50);
            textView = itemView.findViewById(R.id.tv_text);
        }
    }
}
