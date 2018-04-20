package com.dongua.interview.webviewlearn;

import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import com.dongua.interview.BaseActivity;
import com.dongua.interview.R;
import com.elvishew.xlog.XLog;

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
        mWebView.loadUrl("file:///android_asset/js.html");

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(WebViewActivity.this);
                b.setTitle("Alert");
                b.setMessage(message);
                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                b.setCancelable(false);
                b.create().show();
                return true;
            }

        });

    }

    @OnClick({R.id.btn_calljs})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_calljs:
                mWebView.post(new Runnable() {
                    @Override
                    public void run() {

                        mWebView.loadUrl("javascript:callJS()");
                    }
                });

                break;

            default:
                break;
        }
    }


}
