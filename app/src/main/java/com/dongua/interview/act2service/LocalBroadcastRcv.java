package com.dongua.interview.act2service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.LruCache;

import com.elvishew.xlog.XLog;

import static com.dongua.interview.act2service.CommunicateActivity.BROADCAST_EXTRA_KEY;

/**
 * author: Lewis
 * date: On 18-2-28.
 */

public class LocalBroadcastRcv extends BroadcastReceiver {
    public LocalBroadcastRcv() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        XLog.i("onReceive :"+Thread.currentThread().getName());
        XLog.i(intent.getIntExtra(BROADCAST_EXTRA_KEY,2)+"");
    }

    @Override
    public IBinder peekService(Context myContext, Intent service) {
        return super.peekService(myContext, service);
    }

}
