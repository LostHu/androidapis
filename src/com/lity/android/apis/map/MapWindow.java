package com.lity.android.apis.map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.media.AudioRecord;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.SearchRecentSuggestions;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.util.Log;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;
import com.google.android.maps.MapView.LayoutParams;
import com.lity.android.apis.App;
import com.lity.android.apis.R;

public class MapWindow extends MapActivity {
    
    public static final int SEARCHITEM_MAXCOUNT = 10;
    
    private SearchMapView mMapView;
	
	private MapController mMapController;
	
	private List<Overlay> mOverlay;
	
	private CustomItemizedOverlay mCustomItemizedOverlay;
	
	/**
	 * 和google服务器交互的线程
	 */
	private Thread mGoogleThread = new Thread(){

        @Override
        public void run() {
            super.run();
            Looper.prepare();
            mGoogleHandler = new GoogleHandler();
            Looper.loop();
        }
	    
	};
	
	/**
	 * 与google线程相关的handler
	 */
	private Handler mGoogleHandler;
	
	/**
	 * 与UI主线程相关的handler
	 */
	private Handler mMainHandler = new Handler();
	
	/** 
	 * 地址转码
	 */
	private Geocoder mGeocoder;
	
	/**
	 * 长按某一点弹出的textview
	 */
	private TextView mLongText;
	
  
	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.googlemap); 
		
		mMapView = (SearchMapView)findViewById(R.id.mapView);  
		mMapController = mMapView.getController();
		
	    mMapView.setBuiltInZoomControls(true);//可以多点触摸放大
	    mMapView.setSatellite(false);//使用卫星图
	    
	    mMapView.setOnMapLongClickListener(new MapEvent());

	    
	    GeoPoint point = new GeoPoint((int)(39.92E6), (int)(114.46E6));
//	    mMapController = mMapView.getController();
//	    mMapController.setCenter(point);
//	    mMapView.postInvalidate();
//	    mMapController.animateTo(point);
//	    
	    mOverlay = mMapView.getOverlays();
	    Drawable drawable = getResources().getDrawable(R.drawable.map_mark);
	    mCustomItemizedOverlay = new CustomItemizedOverlay(drawable);
	    mOverlay.add(mCustomItemizedOverlay);

	    OverlayItem item = new OverlayItem(point, "123", "456");
	    mCustomItemizedOverlay.addOverlay(item);
	    
	    // 启动google线程
	    mGoogleThread.start();
	    
	    mGeocoder = new Geocoder(this, Locale.CHINA);

	    mLongText = new TextView(this);
	    mLongText.setBackgroundResource(R.drawable.map_label);
	}

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        final String query = intent.getStringExtra(SearchManager.QUERY);

        
        //获得搜索框里值
//        final String query=intent.getStringExtra(SearchManager.QUERY);
        
        //保存搜索记录
        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                SearchSuggestionProvider.AUTHORITY, SearchSuggestionProvider.MODE);
        suggestions.saveRecentQuery(query, null);
        
        if (null == mGoogleHandler) {
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                Log.v(App.TAG, "an exception occurs:" + e);
            }
        }
//        mGoogleHandler.sendEmptyMessage(GoogleHandler.MESSAGE_KEYTOADDRESS);
        Message msg = Message.obtain(mGoogleHandler, GoogleHandler.MESSAGE_KEYTOADDRESS);
        msg.getData().putString(GoogleHandler.MESSAGE_KEY, query);
        mGoogleHandler.sendMessage(msg);
    }
    
    
    @Override
    protected void onDestroy() {
        mGoogleHandler.sendEmptyMessage(GoogleHandler.MESSAGE_QUITE);
        super.onDestroy();
    }

    // 根据关键字搜索地点
    private void searchLocationByName(String addressName) {
        if (App.DEBUG) {
            Log.v(App.TAG, "输入的keyword: " + addressName);
        }
        Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.CHINA);
        List<Address> addresses = null;
        try {
            addresses = geoCoder.getFromLocationName(addressName, SEARCHITEM_MAXCOUNT);
            for (Address address : addresses) {
                if (App.DEBUG) {
                    Log.v(App.TAG, "address:" + address);
                }
            }
            if (App.DEBUG) {
                Log.v(App.TAG, "the count of addresses:" + addresses.size());
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (App.DEBUG) {
                Log.v(App.TAG, "an exception occurs:" + e);
            }
        }
        if (null != addresses && addresses.size() > 0) {
            final List<Address> tempAddresses = addresses;
            mMainHandler.post(new Runnable() {
                
                @Override
                public void run() {
                    mCustomItemizedOverlay.removeOverlay();
                    
                    for (Address address : tempAddresses) {
                        // TODO 添加标记
                        if (address.hasLatitude() && address.hasLongitude()) {
                            int latE6 = (int)(address.getLatitude() * 1E6);
                            int lonE6 = (int)(address.getLongitude() * 1E6);
                            OverlayItem item = new OverlayItem(new GeoPoint(latE6, lonE6), null, null);
                            mCustomItemizedOverlay.addOverlay(item);
                        }
                    }
                    mMapView.invalidate();
                }
            });
        }

    }
    
    private void searchLocationByPoint(GeoPoint point) {
        if (App.DEBUG) {
            Log.v(App.TAG, "searchLocationByPoint method!");
        }
        List<Address> addresses = null;
        double lat = (double)point.getLatitudeE6() / 1E6;
        double lon = (double)point.getLongitudeE6() / 1E6;
        try {
            addresses = mGeocoder.getFromLocation(lat, lon, 1);
        } catch (IOException e) {
            e.printStackTrace();
            addresses = null;
            if (App.DEBUG) {
                Log.v(App.TAG, "an exception occurs:" + e);
            }
        }
        final List<Address> tempAddresses = addresses;
        final GeoPoint temPoint = point;
        if (null == tempAddresses) {
            return;
        }
        runOnUiThread(new Runnable() {
            
            @Override
            public void run() {
                for (Address address : tempAddresses) {
                    System.out.println("address:" + address);
//                    OverlayItem item = new OverlayItem(temPoint, "title", address.getFeatureName());
//                    item.setMarker(getResources().getDrawable(R.drawable.map_label));
//                    mCustomItemizedOverlay.addOverlay(item);
                    
//                    mMapView.addView(child, params)
//                    MapView.LayoutParams params = new MapView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 
//                            temPoint, MapView.LayoutParams.BOTTOM_CENTER);
//                    TextView text = new TextView(MapWindow.this);
//                    text.setBackgroundResource(R.drawable.map_label);
//                    text.setText(address.getCountryName() + address.getFeatureName());
//                    mMapView.addView(text, params);
                    mLongText.setText(address.getCountryName() + address.getFeatureName());
                    mMapView.invalidate();
                }
            }
        });
    }
    
    class GoogleHandler extends Handler {
        // 线程不处理任何消息
        public static final int MESSAGE_NONE = 0;
        public static final int MESSAGE_KEYTOADDRESS = 1;
        public static final String MESSAGE_KEY = "key";
        public static final int MESSAGE_POINTTOADDRESS = 2;
        public static final int MESSAGE_QUITE = 3;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_KEYTOADDRESS:
                    searchLocationByName(msg.getData().getString(MESSAGE_KEY));
                    break;
                case MESSAGE_POINTTOADDRESS:
                    searchLocationByPoint((GeoPoint)msg.obj);
                    break;
                case MESSAGE_QUITE:
                    Looper.myLooper().quit();
                    break;
                default:
                    break;
            }
        } 
    }
    
    class MapEvent extends Object implements SearchMapView.onMapLongClickListener {

        @Override
        public boolean onMapLongClick(MotionEvent event) {
            Projection proj = mMapView.getProjection();
            GeoPoint point = proj.fromPixels((int)event.getX(), (int)event.getY());
            MapView.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 
                    point, LayoutParams.BOTTOM_CENTER);
            if (null == mLongText.getParent()) {
                mMapView.addView(mLongText, params);
            }
            mLongText.setLayoutParams(params);
            mLongText.setText("正在加载...");
            
            Message 
//                Message.obtain(mGoogleHandler, GoogleHandler.MESSAGE_POINTTOADDRESS, point.getLatitudeE6(), point.getLongitudeE6());
            msg = Message.obtain(mGoogleHandler, GoogleHandler.MESSAGE_POINTTOADDRESS, point);
            msg.sendToTarget();
            return false;
        }

    }
    

}
