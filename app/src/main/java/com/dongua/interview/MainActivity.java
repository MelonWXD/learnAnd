package com.dongua.interview;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import com.dongua.interview.act2service.CommunicateActivity;
import com.dongua.interview.actlaunch.FirstActivity;
import com.dongua.interview.animate.AnimActivity;
import com.dongua.interview.eventbus3.EventBusActivity;
import com.dongua.interview.glvideo.VideoActivity;
import com.dongua.interview.krpano.PanoActivity;
import com.dongua.interview.notification.NotificationActivity;
import com.dongua.interview.touchevent.TouchActivity;
import com.dongua.interview.webviewlearn.WebViewActivity;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.btn_touch)
    Button toucheventBtn;


    Unbinder unbinder;

    @BindView(R.id.iv_loadfile)
    ImageView loadImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        int a = Build.VERSION_CODES.O_MR1;
        String[] per = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
//        requestPermissions(per,0);
//        Bitmap b1 = BitmapFactory.decodeFile("/storage/emulated/0/temp/tes2.png");
//        Bitmap b2 = BitmapFactory.decodeResource(getResources(),R.drawable.tes2);
//        Log.i("a", "onCreate: ");
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
            , R.id.btn_notify, R.id.btn_thread, R.id.btn_webview
            , R.id.btn_video, R.id.btn_pano, R.id.btn_adapter})
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
                showNormalDialog(MainActivity.this);
                break;

            case R.id.btn_eventbus:
//                showNormalDialog(this);
                startActivity(EventBusActivity.class);
                break;

            case R.id.btn_notify:
                startActivity(NotificationActivity.class);
            case R.id.btn_thread:
                final ThreadPoolExecutor executor = new ThreadPoolExecutor(30, 30, 2, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
                for (int i = 0; i < 30; i++)
                    executor.submit(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(5 * 1000);
                            executor.shutdown();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            case R.id.btn_webview:
                startActivity(WebViewActivity.class);
                break;
            case R.id.btn_video:
                startActivity(VideoActivity.class);
                break;
            case R.id.btn_pano:
                startActivity(PanoActivity.class);
                break;
            case R.id.btn_adapter:
//                startActivity(AdapterActivity.class);
                break;
            default:
                break;
        }
    }

    private void showNormalDialog(Context context) {
        // 创建构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // 设置参数
        builder.setTitle("请做出选择")
                .setMessage("我美不美")
                .setPositiveButton("美", new DialogInterface.OnClickListener() {// 积极

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {

                    }
                }).setNegativeButton("不美", new DialogInterface.OnClickListener() {// 消极

            @Override
            public void onClick(DialogInterface dialog,
                                int which) {

            }
        }).setNeutralButton("不知道", new DialogInterface.OnClickListener() {// 中间级

            @Override
            public void onClick(DialogInterface dialog,
                                int which) {

            }
        });
        builder.create().show();

    }

    private void startActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);

    }


}
