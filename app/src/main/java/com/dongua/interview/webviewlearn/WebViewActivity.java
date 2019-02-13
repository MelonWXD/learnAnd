package com.dongua.interview.webviewlearn;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.http.SslError;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.PopupWindow;

import com.dongua.interview.R;
import com.example.common.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: Lewis
 * date: On 18-4-19.
 */

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.wv_main)
    WebView mWebView;
    @BindView(R.id.btn_calljs)
    Button callJsBtn;

    @Override
    public int getLayoutID() {
        return R.layout.activity_webview;
    }

    @SuppressLint("HandlerLeak")
    Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (window != null)
                window.dismiss();
            showPop();
            h.sendEmptyMessageDelayed(1, 100);
        }
    };
    PopupWindow window;

    private void showPop() {
// 用于PopupWindow的View
        View contentView = LayoutInflater.from(this).inflate(R.layout.activity_webview, null, false);
        // 创建PopupWindow对象，其中：
        // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
        // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
        window = new PopupWindow(contentView, 1000, 1000, true);
        // 设置PopupWindow的背景
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 设置PopupWindow是否能响应外部点击事件
        window.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        window.setTouchable(true);
        // 显示PopupWindow，其中：
        // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
        window.showAsDropDown(mWebView, 0, -200);
        // 或者也可以调用此方法显示PopupWindow，其中：
        // 第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
        // 第三和第四个参数分别是PopupWindow相对父View的x、y偏移
        // window.showAtLocation(parent, gravity, x, y);
    }

    @Override
    public void init() {
        super.init();

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDomStorageEnabled(true);
        mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i("wxddd", "shouldOverrideUrlLoading: " + url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                XLog.i("shouldOverrideUrlLoading"+request.getUrl());
                Log.i("wxddd", "shouldOverrideUrlLoading: " + request.getUrl());
//                mWebView.loadUrl(request.getUrl().toString());
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
//                XLog.i("shouldInterceptRequest"+request.getUrl());
                Log.i("wxddd", "shouldInterceptRequest: " + request.getUrl());
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.i("wxddd", "onPageStarted: ");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.i("wxddd", "onPageFinished: ");
            }
        });


        mWebView.loadUrl("https://www.baidu.com");

        h.sendEmptyMessageDelayed(1, 1000);
    }

    @OnClick({R.id.btn_calljs})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_calljs:
                mWebView.post(new Runnable() {
                    @Override
                    public void run() {

                        mWebView.loadUrl("https://www.sina.com.cn/");
                    }
                });

                break;


            default:
                break;
        }
    }


}
