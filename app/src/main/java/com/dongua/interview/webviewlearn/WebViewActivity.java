package com.dongua.interview.webviewlearn;

import android.webkit.WebView;

import com.dongua.interview.BaseActivity;
import com.dongua.interview.R;

import butterknife.BindView;

/**
 * author: Lewis
 * date: On 18-4-19.
 */

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.wv_main)
    WebView mWebView;

    @Override
    public int getLayoutID() {
        return R.layout.activity_webview;
    }

    @Override
    public void init() {
        super.init();
    }


}
