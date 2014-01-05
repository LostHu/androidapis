package com.lity.android.apis.aidl;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.nduoa.nmarket.aidl.IChannelService;

public class ConnectNduoaActivity extends Activity implements OnClickListener {
	
	public static final String TAG = ConnectNduoaActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		Button btn = new Button(this);
		btn.setOnClickListener(this);
		setContentView(btn);
		
		Intent intent = new Intent();
		intent.setAction("com.nduoa.channel.service");
		boolean result = bindService(intent, mRemoteConnection, Context.BIND_AUTO_CREATE);
		Log.v(TAG, "bind result:" + result);
	}
	
	private IChannelService mChannelService;
	
	private ServiceConnection mRemoteConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName arg0, IBinder arg1) {
			Log.v(TAG, "connect success");
			Log.v(TAG, "onServiceConnected,ComponentName:" + arg0 + ", IBinder arg1:" + arg1);
			mChannelService = IChannelService.Stub.asInterface(arg1);
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			Log.v(TAG, "onServiceDisconnected,ComponentName:" + arg0);
		}
		
	};
	
	
	protected void onDestroy() {
		super.onDestroy();
		unbindService(mRemoteConnection);
	}

	@Override
	public void onClick(View arg0) {
		String channel = null;
		try {
			channel = mChannelService.getChannel();
		} catch (RemoteException e) {
			e.printStackTrace();
			Log.e(TAG, "getChannel(), exception:" + e);
		}
		Log.v(TAG, "ChannelService:" + channel);
	}
}
