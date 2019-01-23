package com.dongua.interview.livecycle.data;

import android.arch.core.internal.SafeIterableMap;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Looper;
import android.util.Log;


import com.dongua.interview.livecycle.Utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class BaseEventData extends LiveData<Object> {
    public static final int CMD_HAHA = 1;
    public static final int CMD_HEHE = 2;
    public static final int CMD_GAGA = 3;
    private int cmd;

    public BaseEventData(int cmd) {
        this.cmd = cmd;
    }

    @Override
    public void postValue(Object value) {
        super.postValue(value);
    }

    @Override
    public void setValue(Object value) {
        super.setValue(value);
    }

    public void update(Object object) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            setValue(object);
        } else {
            postValue(object);
        }
    }

    /**
     * 通过反射获取version值来同步新增的重复key值订阅
     */
    public int getVer() {
        try {
            return (int) Utils.invokeMethod(LiveData.class, this, "getVersion", (Object) null);

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return -1;
    }


    public void subscribe(LifecycleOwner owner, Observer action) {
        observe(owner, action);
        sync(action);
    }

    private void sync(Observer observer) {
        int curVersion = getVer();
        if (curVersion == -1)
            return;

        Object map = null;
        try {
            Field observers = Utils.getFiled(LiveData.class, "mObservers");
            map = observers.get(this);

            if (map instanceof SafeIterableMap) {

                //LifecycleBoundObserver wrapper;
                Object wrapperObserver = null;
                // SafeIterableMap.get()
                Method methodGet = Utils.getMethodFromObj(map, "get", Object.class);
                Object entry = methodGet.invoke(map, observer);
                if (entry instanceof Map.Entry) {
                    wrapperObserver = ((Map.Entry) entry).getValue();
                }
                if (wrapperObserver == null)
                    return;
                Field lastVersion = Utils.getFiled(wrapperObserver.getClass(), "lastVersion");
                int lastV = (int) lastVersion.get(wrapperObserver);
                Log.i("wxdq", "sync: "+lastV +"cur = "+curVersion);
                lastVersion.set(wrapperObserver, curVersion);
                Log.i("wxdq", "sync: "+Utils.getFiled(wrapperObserver.getClass(), "lastVersion").get(wrapperObserver));

            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }
}
