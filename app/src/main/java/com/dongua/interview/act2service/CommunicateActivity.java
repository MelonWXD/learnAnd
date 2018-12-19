package com.dongua.interview.act2service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.TextView;

import com.example.common.BaseActivity;
import com.dongua.interview.R;
import com.elvishew.xlog.XLog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: Lewis
 * date: On 18-2-27.
 */

public class CommunicateActivity extends BaseActivity {


    public static final String BROADCAST_NAME = "myaction";
    public static final String BROADCAST_KEY = "key";
    public static final String BROADCAST_ACTION = "jeson.broadcast";
    public static final String BROADCAST_EXTRA_KEY = "message";
    LocalBroadcastManager localBroadcastManager;
    //    LocalBroadcastRcv broadcastReceiver;
    @BindView(R.id.tv_progress)
    TextView progressTv;

    @OnClick({R.id.btn_start_service, R.id.btn_sned_broadcast})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start_service:
                Intent intent = new Intent(this, TestService.class);
                bindService(intent, connection, Service.BIND_AUTO_CREATE);
                break;
            case R.id.btn_sned_broadcast:
                XLog.i("btn_sned_broadcast");
                Intent intent2 = new Intent();
                intent2.setAction(BROADCAST_ACTION);
                intent2.putExtra(BROADCAST_EXTRA_KEY, 123);
//                sendBroadcast(intent2);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent2);
                break;
            default:
                break;
        }
    }

    TestService mService;
    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            XLog.i("onServiceConnected");
//            mService = ((TestService.TestBinder) service).getService();
//            mService.setListener(new OnProgressListener() {
//                @Override
//                public void onUpdate(final int val) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            XLog.i(Thread.currentThread().getId());
//
//                            progressTv.setText(val + "");
//                        }
//                    });
//                }
//            });
//            mService.startDownLoad();
//            mService.sendLocalBroadcast();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public int getLayoutID() {
        return R.layout.activity_communicate;
    }

    @Override
    public void init() {

        registerLocalBroadcast();
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(BROADCAST_ACTION);
//        registerReceiver(new LocalBroadcastRcv(),intentFilter);
    }

    //注册广播
    private void registerLocalBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BROADCAST_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(new LocalBroadcastRcv(), intentFilter);

    }





    @Override
    protected void onPause() {
        super.onPause();
//        localBroadcastManager.unregisterReceiver(broadcastReceiver);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
