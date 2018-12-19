package com.dongua.interview.actlaunch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.common.BaseActivity;
import com.dongua.interview.R;
import com.elvishew.xlog.XLog;

import butterknife.OnClick;

/**
 * author: Lewis
 * date: On 18-3-5.
 */

public class SecondActivity extends BaseActivity {
    @Override
    public int getLayoutID() {
        return R.layout.activity_second;
    }

    @OnClick({R.id.btn_start_act})
    public void onClick(View view) {
        startActivity(FirstActivity.class);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        XLog.i("onNewIntent:" + this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        XLog.i("onCreate");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        XLog.i("onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        XLog.i("onResume");

    }

    @Override
    protected void onStart() {
        super.onStart();
        XLog.i("onResume");

    }


}
