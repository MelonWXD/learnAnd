package com.dongua.interview.eventbus3;

import android.app.Fragment;

import com.dongua.interview.BaseActivity;
import com.dongua.interview.R;
import com.elvishew.xlog.XLog;

import org.greenrobot.eventbus.EventBus;

/**
 * author: Lewis
 * date: On 18-3-12.
 */

public class BusTestActivity extends BaseActivity {
    @Override
    public int getLayoutID() {
        return R.layout.activity_bustest;
    }


    @Override
    public void init() {
        super.init();
//        EventBus.getDefault().register(this);
        EventBus.getDefault().post(new MyEvent("this msg from BusTestActivity"));

    }


//
//    public void onHandleMyEvent(MyEvent event){
//        XLog.i(event.getMessage());
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }
}
