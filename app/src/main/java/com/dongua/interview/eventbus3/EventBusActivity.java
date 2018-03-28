package com.dongua.interview.eventbus3;

import android.view.View;
import android.widget.TextView;

import com.dongua.interview.BaseActivity;
import com.dongua.interview.R;
import com.dongua.interview.act2service.CommunicateActivity;
import com.dongua.interview.actlaunch.FirstActivity;
import com.dongua.interview.animate.AnimActivity;
import com.dongua.interview.touchevent.TouchActivity;
import com.elvishew.xlog.XLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: Lewis
 * date: On 18-3-12.
 */

public class EventBusActivity extends BaseActivity {
    @Override
    public int getLayoutID() {
        return R.layout.activity_eventbus;
    }

    @BindView(R.id.tv_msg)
    TextView msgTv;

    @Override
    public void init() {
        super.init();
        EventBus.getDefault().register(this);
    }

    @OnClick({R.id.btn_send})
    public void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                startActivity(BusTestActivity.class);
                break;
            default:
                break;
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onHandleMyEvent(MyEvent event){
        XLog.i(event.getMessage());
        msgTv.setText(event.getMessage());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
