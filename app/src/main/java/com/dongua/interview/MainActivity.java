package com.dongua.interview;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.dongua.interview.act2service.CommunicateActivity;
import com.dongua.interview.actlaunch.FirstActivity;
import com.dongua.interview.animate.AnimActivity;
import com.dongua.interview.eventbus3.EventBusActivity;
import com.dongua.interview.notification.NotificationActivity;
import com.dongua.interview.touchevent.TouchActivity;


import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.locks.Lock;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.btn_touch)
    Button toucheventBtn;


    Unbinder unbinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

         
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
         

    }

    @Override
    protected void onStart() {
        super.onStart();
         

    }

    @Override
    protected void onStop() {
        super.onStop();
         

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
         

    }

    @Override
    protected void onResume() {
        super.onResume();
         


    }

    @Override
    protected void onPause() {
        super.onPause();
         

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
         
        outState.putInt("1", 1);

    }


    @Override
    protected void onDestroy() {
         
        super.onDestroy();
        if (unbinder != null)
            unbinder.unbind();
    }

    @OnClick({R.id.btn_touch, R.id.btn_anim, R.id.btn_service
            , R.id.btn_act, R.id.btn_dialog, R.id.btn_eventbus
            , R.id.btn_notify})
    public void onButtonClick(View view) {
        switch (view.getId()) {
            case R.id.btn_touch:
                startActivity(TouchActivity.class);
                break;
            case R.id.btn_anim:
                startActivity(AnimActivity.class);
                break;
            case R.id.btn_service:
                startActivity(CommunicateActivity.class);
                break;

            case R.id.btn_act:
                startActivity(FirstActivity.class);
                break;

            case R.id.btn_dialog:
//                showNormalDialog(this);
                showNormalDialog(getApplicationContext());
                break;

            case R.id.btn_eventbus:
//                showNormalDialog(this);
                startActivity(EventBusActivity.class);
                break;

            case R.id.btn_notify:
                startActivity(NotificationActivity.class);
            default:
                break;
        }
    }

    private void showNormalDialog(Context context) {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        AlertDialog normalDialog =
                new AlertDialog.Builder(context)
                        .setTitle("我是一个普通Dialog")
                        .setMessage("你要点击哪一个按钮呢?").create();

        // 显示
        normalDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
        normalDialog.show();
    }

    private void startActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);

    }


}
