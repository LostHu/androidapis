package com.lity.android.apis.aidl;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/*
 * 1. aidl的客户端使用Intent接连到aidl的服务端时, 若设备上有几个Intent.action完全相同的Service, 
 * 	  那么c会连接到最先安装s上.
 */
public class ClientActivity extends Activity {

	public static final String TAG = ClientActivity.class.getSimpleName();

	private IMyService mRemoteService;

	private Handler mHandler = new Handler();

	private ServiceConnection mRemoteConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {
			Log.v(TAG, "onServiceConnected, ComponentName:" + className + ", IBinder:" + service);
			mRemoteService = IMyService.Stub.asInterface(service);
			try {
				mRemoteService.savePersonInfo("lity");
				mRemoteService.savePersonInfo("lity_lee");
				Log.v(TAG, "" + mRemoteService.getAllPersons().toString());
			} catch (RemoteException e) {
				e.printStackTrace();
				Log.e(TAG, e.toString());
			}
		}

		public void onServiceDisconnected(ComponentName className) {
			Log.v(TAG, "onServiceDisconnected");
			mRemoteService = null;
		}
	};

	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = new Intent("com.nduoa.channel.service");

		// getSystemService(Context.s)
		// re

		boolean result = bindService(intent, mRemoteConnection, Context.BIND_AUTO_CREATE);
		Log.v(TAG, "bind result:" + result);

		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				Context base = getBaseContext();
				Log.v(TAG, base.toString());

			}
		}, 5000);

	}
}
