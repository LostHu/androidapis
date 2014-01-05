package com.lity.android.apis.tabs;

import com.lity.android.apis.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.widget.TabHost;
import android.widget.TabWidget;

public class MainTab extends TabActivity {
	
	public static final String TAG = MainTab.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, TAG + " onCreate() method");
		super.onCreate(savedInstanceState);
		
		TabHost tabHost = getTabHost();
//		LayoutInflater.from(this).inflate(R.layout.tabs, tabHost.getTabContentView());
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("tab1").setContent(new Intent(this, TabOne.class)));
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("tab2").setContent(new Intent(this, TabTwo.class)));
//		tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("300").setContent(R.id.textView3));
//		tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator("400").setContent(R.id.textView3));
		
	}
	
	@Override
	protected void onStart() {
		Log.v(TAG, TAG + " onStart() method");
		super.onStart();
	}
	
	@Override
	protected void onRestart() {
		Log.v(TAG, TAG + " onRestart() method");
		super.onRestart();
	}
	
	@Override
	protected void onResume() {
		Log.v(TAG, TAG + " onResume() method");
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		Log.v(TAG, TAG + " onPause() method");
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		Log.v(TAG, TAG + " onStop() method");
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		Log.v(TAG, TAG + " onDestroy() method");
		super.onDestroy();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.v(TAG, TAG + " onKeyDown() method");
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		Log.v(TAG, TAG + " onKeyUp() method");
		return super.onKeyUp(keyCode, event);
	}
}
