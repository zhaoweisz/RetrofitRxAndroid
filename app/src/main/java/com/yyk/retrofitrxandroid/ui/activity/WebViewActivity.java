package com.yyk.retrofitrxandroid.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yyk.retrofitrxandroid.R;
import com.yyk.retrofitrxandroid.service.view.WebViewActivityView;

/**
 * Created by YYK on 2017/8/29.
 */

public class WebViewActivity extends AppCompatActivity implements WebViewActivityView {

    private WebView mWebView;
    private Bundle mBundle;
    private WebViewClient mClient;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mWebView = (WebView) findViewById(R.id.web_view);
        mBundle = getIntent().getExtras();
        mClient = new MyWebViewClient();
        mWebView.setWebViewClient(mClient);
//        mWebView.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                if(newProgress == 100) {
//                    hideProgress();
//                }
//            }
//        });
        String url = mBundle.getString("url");
        if (!url.isEmpty()) {
            mWebView.loadUrl(url);
            showProgress();
        }
    }

    @Override
    public void showProgress() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            showProgress();
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            hideProgress();
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            hideProgress();
        }
    }
}
