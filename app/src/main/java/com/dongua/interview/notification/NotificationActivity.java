package com.dongua.interview.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.dongua.interview.BaseActivity;
import com.dongua.interview.R;
import com.elvishew.xlog.XLog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: Lewis
 * data: On 18-3-26.
 */

public class NotificationActivity extends BaseActivity {
    @BindView(R.id.btn_sys_notify)
    Button sendBtn;

    @BindView(R.id.sp_type)
    Spinner typeSp;
    NotificationManager manager;
    ArrayList<String> data_list;
    ArrayAdapter arr_adapter;

    private int CUR_TYPE = 0;

    @Override
    public void init() {
        super.init();
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        //数据
        data_list = new ArrayList<String>();
        data_list.add("普通样式");
        data_list.add("下载进度");
        data_list.add("点击后展开");
        data_list.add("深圳");

        //适配器
        arr_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        typeSp.setAdapter(arr_adapter);
        typeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                XLog.i(position);
                CUR_TYPE = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick({R.id.btn_sys_notify })
    public void OnClick(View view) {
        switch (CUR_TYPE) {
            case 0:
                sendNormal();
                break;
            case 1:
                sendProgress();
                break;
            case 2:
                sendBigText();
                break;
            case 3:
                break;
            default:
                ;
        }
//        switch (view.getId()) {
//            case R.id.btn_sys_notify:
//                sendSysNotify();
//                break;
//            case R.id.btn_custom_notify:
//                sendProgressNotify();
//
//                break;
//            default:
//                break;
//        }
    }

    private void sendBigText(){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("点击展开 title");
        builder.setContentText("点击展开 text");
        builder.setSmallIcon(R.drawable.notify_small);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.notify_big));


        android.support.v4.app.NotificationCompat.BigTextStyle style = new android.support.v4.app.NotificationCompat.BigTextStyle();
        style.bigText("这里是点击通知后要显示的正文，可以换行可以显示很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长很长");
        style.setBigContentTitle("点击后的标题");

        style.setSummaryText("末尾只一行的文字内容");
        builder.setStyle(style);
        builder.setAutoCancel(true);

        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        Notification notification = builder.build();
        manager.notify(3,notification);
    }

    private void sendProgress() {
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.notify_small);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.notify_big));
        //禁止用户点击删除按钮删除
        builder.setAutoCancel(false);
        //禁止滑动删除
        builder.setOngoing(true);
        //取消右上角的时间显示
        builder.setShowWhen(false);
        builder.setContentTitle("下载中...");
        builder.setProgress(100, 0, false);
        //builder.setContentInfo(progress+"%");
        builder.setOngoing(true);
        builder.setShowWhen(false);
        Intent intent = new Intent(this, DownloadService.class);
        intent.putExtra("command", 1);

        manager.notify(2, builder.build());

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 100; i++) {
                    builder.setProgress(100, i, false);
                    //下载进度提示
                    builder.setContentText("下载" + i + "%");
                    manager.notify(2, builder.build());

                    try {
                        Thread.sleep(50);//演示休眠50毫秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                builder.setContentTitle("开始安装");
                builder.setContentText("安装中...");
                //设置进度为不确定，用于模拟安装
                builder.setProgress(0, 0, true);
                manager.notify(2, builder.build());

                try {
                    Thread.sleep(3 * 1000);//演示休眠50毫秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                manager.cancel(2);//设置关闭通知栏
            }
        }).start();
    }

    private void sendNormal() {
        //为了版本兼容  选择V7包下的NotificationCompat进行构造
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "c-id");
        //Ticker是状态栏显示的提示
        builder.setTicker("简单Notification");
        //第一行内容  通常作为通知栏标题
        builder.setContentTitle("标题");
        //第二行内容 通常是通知正文
        builder.setContentText("通知内容");
        //第三行内容 通常是内容摘要什么的 在低版本机器上不一定显示
        builder.setSubText("第三行通知内容");

        //ContentInfo 在通知的右侧 时间的下面 用来展示一些其他信息
//        builder.setContentInfo("333");
        //number设计用来显示同种通知的数量和ContentInfo的位置一样，如果设置了ContentInfo则number会被隐藏
        builder.setNumber(22);
        //可以点击通知栏的删除按钮删除
        builder.setAutoCancel(true);
        //系统状态栏显示的小图标
        builder.setSmallIcon(R.drawable.notify_small);
        //下拉显示的大图标
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.notify_big));
//        Intent intent = new Intent(this,SettingsActivity.class);
//        PendingIntent pIntent = PendingIntent.getActivity(this,1,intent,0);
        //点击跳转的intent
//        builder.setContentIntent(pIntent);
        //通知默认的声音 震动 呼吸灯
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        Notification notification = builder.build();
        notification.iconLevel = 10000;
        manager.notify(1, notification);
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_notify;
    }
}
