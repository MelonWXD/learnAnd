package com.dongua.interview;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import com.alibaba.fastjson.JSON;
import com.dongua.interview.act2service.CommunicateActivity;
import com.dongua.interview.actlaunch.FirstActivity;
import com.dongua.interview.animate.AnimActivity;
import com.dongua.interview.bean.OrderInfo;
import com.dongua.interview.eventbus3.EventBusActivity;
import com.dongua.interview.glvideo.VideoActivity;
import com.dongua.interview.krpano.PanoActivity;
import com.dongua.interview.livecycle.AACActivity;
import com.dongua.interview.notification.NotificationActivity;
import com.dongua.interview.touchevent.TouchActivity;
import com.dongua.interview.view.CustomViewActivity;
import com.dongua.interview.webviewlearn.WebViewActivity;
import com.example.scan.ScanActivity;

import java.nio.ByteBuffer;
import java.util.Random;
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


    }

    public void testFastJson() {

        String jsonStr = "{" +
                "\"orderId\": \"123\"," +
                "\"orderName\": \"456\"," +
                "\"returnCouponInfo\": [{" +
                "\"couponNo\": \"1\"," +
                "\"couponName\": \"2\"," +
                "\"validStartTime\": \"3\"," +
                "\"validEndTime\": \"4\"," +
                "\"introduce\": \"5\"" +
                "}," +
                "{" +
                "\"couponNo\": \"11\"," +
                "\"couponName\": \"22\"," +
                "\"validStartTime\": \"33\"," +
                "\"validEndTime\": \"44\"" +
                "}" +
                "]" +
                "}";
        OrderInfo o = new OrderInfo();

        String json2 = " {\"orderId\":\"123\"} ";
        OrderInfo order = JSON.parseObject(jsonStr, OrderInfo.class);
        Log.i(TAG, "testFastJson: " + order.toString());
    }

    private static final String TAG = "MainActivity";

    @Override
    protected void onDestroy() {

        super.onDestroy();
        if (unbinder != null)
            unbinder.unbind();
    }


    @OnClick({R.id.btn_touch, R.id.btn_anim, R.id.btn_service
            , R.id.btn_act, R.id.btn_dialog, R.id.btn_eventbus
            , R.id.btn_notify, R.id.btn_thread, R.id.btn_webview
            , R.id.btn_video, R.id.btn_pano, R.id.btn_livebus
            , R.id.btn_scan,R.id.btn_custom_view})
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
            case R.id.btn_livebus:
                startActivity(AACActivity.class);
                break;
            case R.id.btn_scan:
                startActivity(ScanActivity.class);
                break;   case R.id.btn_custom_view:
                startActivity(CustomViewActivity.class);
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

    public static Bitmap createBitmapWithFrame(Bitmap src, int width, int parting) {
        //暂时不对src 宽超出打印纸处理 默认为由工具类创建出的bitmap
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        ByteBuffer buf = ByteBuffer.allocate(src.getByteCount());
        src.copyPixelsToBuffer(buf);
        byte[] srcData = buf.array();

        //宽为最宽
        int retWidth = width;
        int remain = srcHeight % parting;
        //整数倍高
        int retHeight = remain == 0 ? srcHeight : srcHeight + parting - remain;
        //构造数据集
        byte[] retData = new byte[retWidth * retHeight * 4]; //ARGB 8888 4个byte1个像素
        //逐字节拷贝数据
        int byteWidth = retWidth * 4;
        for (int h = 0; h < retHeight; h++) {
            for (int w = 0; w < byteWidth; w++) {
                int p = h * byteWidth + w; //当前数组位置

                if (w < 40 || w > byteWidth - 40) {//ARGB_8888
                    retData[p] = (byte) new Random().nextInt(255);
//                    if (w % 4 == 0)
//                        retData[p] = (byte) 255;
//
//                        //首尾加黑
//                    else
//                        retData[p] = (byte) 0;
                }
                if (w >= (retWidth - srcWidth) * 4 / 2
                        && w < (retWidth + srcWidth) * 4 / 2
                        && h < srcHeight) {
                    //宽差值 高差值
                    int dw = w - (retWidth - srcWidth) * 4 / 2;
                    retData[p] = srcData[h * srcWidth * 4 + dw];
//                    retData[p] = 1;
                }
            }
        }
        Bitmap ret = Bitmap.createBitmap(retWidth, retHeight, Bitmap.Config.ARGB_8888);
        ret.copyPixelsFromBuffer(ByteBuffer.wrap(retData));
        return ret;
    }


}
