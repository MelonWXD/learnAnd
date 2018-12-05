package com.dongua.interview.livecycle;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;


import com.dongua.interview.livecycle.data.BaseEventData;

import java.util.HashMap;
import java.util.Map;

public class LiveDataBus {

    private final Map<String, BaseEventData> bus;
    private static volatile LiveDataBus sInstance;

    private LiveDataBus() {
        bus = new HashMap<>();
    }

    public static LiveDataBus get() {
        if (sInstance == null) {
            synchronized (LiveDataBus.class) {
                if (sInstance == null) {
                    sInstance = new LiveDataBus();
                }
            }
        }
        return sInstance;
    }

    public @Nullable
    BaseEventData findData(String key) {
        return bus.get(key);
    }

    public void registerData(String key, BaseEventData data) {
        if (!bus.keySet().contains(key))
            bus.put(key, data);
    }

    private static final String TAG = "wxd-LiveDataBus";

    public void subscribe(String key, LifecycleOwner owner, Observer action) {
        findData(key).subscribe(owner, action);
    }

    public void post(String key, int cmd) {
        findData(key).update(cmd);
    }


}
