package com.lity.android.apis.webview;

import java.util.Iterator;

import com.lity.android.apis.App;
import com.lity.android.apis.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.gesture.Gesture;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

public class DetailWindow extends Activity implements OnTouchListener {

    private WebView mWebView;
    private final Handler mMainHandler = new Handler();
    
    private static final String PARAM_TITLE = "title";
    private static final String PARAM_URL = "url";
    
    public static Intent createDetailWindow(Context curWindow, String title, String url){
        Intent intent = new Intent();
        intent.putExtra(PARAM_TITLE, title);
        intent.putExtra(PARAM_URL, url);
        intent.setClass(curWindow, DetailWindow.class);
        return intent;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_window);
        mWebView = (WebView)findViewById(R.id.detail_content_webview);
        
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        mWebView.addJavascriptInterface(new HtmlEvent(), HtmlEvent.INTERFACE_NAME);
        
        setTitle(getIntent().getStringExtra(PARAM_TITLE));
        if (App.DEBUG) {
            Log.v(App.TAG, "url: " + getIntent().getStringExtra(PARAM_URL));
        }
//        mWebView.loadUrl(getIntent().getStringExtra(PARAM_URL));
//        mWebView.loadUrl("file:///android_asset/webview.html");
        
//        mWebView.loadData(html(), "text/html", "utf-8");
        mWebView.loadDataWithBaseURL(null, html(), "text/html", "utf-8", "www.baidu.com");
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setOnTouchListener(this);
    }
    
    private String html(){
        String result = "<html><head><title>web view</title></head>" +
        "<body>";
        
        for (int i = 0; i < 10; i++) {
            String str = "<div style='border-bottom-width:thin; border-bottom-style:solid; border-bottom-color:#000; background:url(APK>'file:///android_asset/qq.png)'><ul>";
            str += "<li><input onClick='window.demo.clickOnAndroid(" + i + ", 0)' type='button' value='°Ù¶È' /></li>";
            str += "<li><input onClick='window.demo.clickOnAndroid(" + i + ", 1)' type='button' value='google' /></li>";
            str += "<li><input onClick='window.demo.clickOnAndroid(" + i + ", 2)' type='button' value='ÐÂÀË' /></li>";
            str += "<li><input onClick='window.demo.clickOnAndroid(" + i + ", 3)' type='button' value='yahoo' /></li>";
            str += "</ul></div>";
            result += str;
        }
//        result += "<img src=\"APK'>file:///android_asset/qq.jpg\" />";
        result += "<img src='file:///android_asset/qq.jpg' />";
        result += "aaasdfsdfsdfsdf</body></html>";
        if (App.DEBUG) {
            Log.v(App.TAG, "html method html code: " + result);
        }
        return result;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.reload();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    class ChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            setProgress(newProgress * 10000);
        }
        
    }
    class HtmlEvent extends Object {
        public static final String INTERFACE_NAME = "demo";
        
        public void clickOnAndroid(final int position, final int id){
            mMainHandler.post(new Runnable() {
                
                @Override
                public void run() {
                    if (App.DEBUG) {
                        Log.v(App.TAG, "click success");
                        Log.v(App.TAG, "postion: " + position + " ,logo Id: " + id);
                        Log.v(App.TAG, "Thread: " + Thread.currentThread());
                    }
                    String title = "Postion:" + position;
                    String url;
                    switch (id) {
                     case 0:
                         url = "http://www.baidu.com";
                         break;
                     case 1:
                         url = "http://www.google.com.hk";
                         break;
                     case 2:
                         url = "http://www.sina.com.cn";
                         break;
                     case 3:
                         url = "http://www.yahoo.com.cn";
                         break;
                     default:
                         url = null;
                         break;
                     }
                }
            });

        }
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (App.DEBUG) {
            Log.v(App.TAG, "webView, onTouch method");
        }
        GestureDetector gesture = null;
        gesture.onTouchEvent(event);
        return true;
    }
}
