package com.dongua.interview.act2service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.elvishew.xlog.XLog;

import static com.dongua.interview.act2service.CommunicateActivity.BROADCAST_ACTION;
import static com.dongua.interview.act2service.CommunicateActivity.BROADCAST_EXTRA_KEY;

/**
 * author: Lewis
 * date: On 18-2-27.
 */

public class TestService extends Service {


    OnProgressListener listener;

    public void setListener(OnProgressListener listener) {
        this.listener = listener;
    }

    private int progress = 0;
    boolean stop = false;
    Thread task;

    public void startDownLoad() {
        task = new Thread(new Runnable() {

            @Override
            public void run() {
                while (progress < 100 && !stop) {
                    XLog.i("downloading :" + progress);
                    progress += 5;
                    listener.onUpdate(progress);
                    try {
                        Thread.sleep(700);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        task.start();
    }

    public void sendLocalBroadcast() {
        XLog.i("sendLocalBroadcast");
        Intent intent2 = new Intent();
        intent2.setAction(BROADCAST_ACTION);
        intent2.putExtra(BROADCAST_EXTRA_KEY, 123);
//        sendBroadcast(intent2);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent2);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sendLocalBroadcast();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        try {
            sendLocalBroadcast();

        }catch (Exception e){
            XLog.e(e);
        }
        return new TestBinder();
    }


    class TestBinder extends Binder {
        public TestService getService() {
            return TestService.this;
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        stop = true;
        return super.onUnbind(intent);

    }
}
