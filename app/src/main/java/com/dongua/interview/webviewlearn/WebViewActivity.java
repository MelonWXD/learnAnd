package com.dongua.interview.webviewlearn;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.net.http.SslError;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.dongua.interview.R;
import com.elvishew.xlog.XLog;
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

    @Override
    public void init() {
        super.init();

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDomStorageEnabled(true);
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
