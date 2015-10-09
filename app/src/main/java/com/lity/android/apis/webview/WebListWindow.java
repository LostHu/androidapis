package com.lity.android.apis.webview;

import com.lity.android.apis.App;
import com.lity.android.apis.R;

import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class WebListWindow extends ListActivity {
    
    private Handler mMainHandler = new Handler();
    
    private HtmlEvent mEvent = new HtmlEvent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getListView().setCacheColorHint(Color.TRANSPARENT);
        
        setListAdapter(new Adapter());
    }

    
   @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (App.DEBUG) {
            Log.v(App.TAG, "WebList Item click: " + position);
        }
    }


   class Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (App.DEBUG) {
                Log.v(App.TAG, "getview position: " + position + ", convertView: " + convertView);
            }
            WebView webView = null;
            if (null == convertView) {
                convertView = (View)LayoutInflater.from(WebListWindow.this).inflate(R.layout.detail_window, null);
                webView = (WebView)convertView.findViewById(R.id.detail_content_webview);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.addJavascriptInterface(mEvent, HtmlEvent.INTERFACE_NAME);
                webView.loadUrl("file:///android_asset/webview.html");
            }
            if (null == webView) {
                webView = (WebView)convertView.findViewById(R.id.detail_content_webview);
            }
            
            final int pos = position;
            webView.setWebViewClient(new WebViewClient(){

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    view.loadUrl("javascript:initPosition(" + pos + ")");
                    view.setWebViewClient(null);
                }
                
            });

            webView.loadUrl("javascript:initPosition(" + position + ")");
            
            return convertView;
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
                   startActivity(DetailWindow.createDetailWindow(WebListWindow.this, title, url));
               }
           });

       }
   }
}
