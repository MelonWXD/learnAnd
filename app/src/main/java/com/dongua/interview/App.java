package com.dongua.interview;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.XLog;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;


/**
 * author: Lewis
 * date: On 18-2-23.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // (Optional) Whether to show thread info or not. Default true
                .tag("WXD")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();


        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

        appContext = getApplicationContext();
//        LeakCanary.install(this);
//        Glide.with(this).load("").into(new ImageView(this));

    }

    private static Context appContext;

    public static Context getAppContext() {
        return appContext;
    }


}
