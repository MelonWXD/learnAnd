package com.dongua.interview;

import android.app.ActivityManager;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Looper;
import android.util.ArraySet;
import android.util.Log;
import android.util.LruCache;
import android.util.SparseArray;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.squareup.leakcanary.LeakCanary;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;

/**
 * author: Lewis
 * date: On 18-2-23.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        LogConfiguration config = new LogConfiguration.Builder()
                .tag("WXD")                                         // Specify TAG, default: "X-LOG"
                .st(1)                                                 // Enable stack trace info with depth 2, disabled by default
                .b()                                                   // Enable border, disabled by default
                .build();

        XLog.init(config);

        appContext = getApplicationContext();
//        LeakCanary.install(this);
        Glide.with(this).load("").into(new ImageView(this));

    }

    private static Context appContext;

    public static Context getAppContext() {
        return appContext;
    }
}
