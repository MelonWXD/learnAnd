package com.dongua.interview.livecycle;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dongua.interview.BaseActivity;
import com.dongua.interview.R;
import com.dongua.interview.livecycle.data.BaseEventData;
import com.dongua.interview.livecycle.viewmodel.StrMsgViewModel;

public class AACActivity extends BaseActivity {
    @Override
    public int getLayoutID() {
        return R.layout.activity_aac;
    }

    private static final String TAG = "AACActivity";
    AndroidViewModel model;
    StrMsgViewModel strMsgViewModel;
    BaseEventData liveData1, liveData2;
    MutableLiveData<String> strLiveData = new MutableLiveData<String>();
    LiveData<Integer> intLiveData;
    Observer<String> strObserver1 = new Observer<String>() {
        @Override
        public void onChanged(@Nullable String s) {
            Log.i(TAG, "ob1 onChanged: " + s);
        }
    };
    Observer<String> strObserver2 = new Observer<String>() {
        @Override
        public void onChanged(@Nullable String s) {
            //测试在他妈的回调中做这种傻逼操作 来达到嵌套的效果
            strLiveData.removeObserver(this);
            strLiveData.observe(AACActivity.this, strObserver3);
        }
    };

    Observer<String> strObserver3 = new Observer<String>() {
        @Override
        public void onChanged(@Nullable String s) {
            Log.i(TAG, "ob3 onChanged: " + s);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        strLiveData.observe(this, strObserver1);
    }


    @Override
    protected void onStart() {
        super.onStart();
        strLiveData.setValue("haha");
    }

    @Override
    protected void onResume() {
        super.onResume();
        strLiveData.setValue("hehe");
        strLiveData.observe(this, strObserver2);
    }


}
