package com.lity.android.apis.tabs;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

public class TabOne extends Activity {
	
	public static final String TAG = TabOne.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, TAG + " onCreate() method");
		super.onCreate(savedInstanceState);
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
