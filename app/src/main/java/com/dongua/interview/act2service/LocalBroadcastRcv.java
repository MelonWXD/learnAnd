package com.dongua.interview.act2service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.LruCache;

import com.elvishew.xlog.XLog;

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
        XLog.i("asdasda");
        XLog.i(intent.getIntExtra("key",2)+"");
    }

    @Override
    public IBinder peekService(Context myContext, Intent service) {
        return super.peekService(myContext, service);
    }

}
