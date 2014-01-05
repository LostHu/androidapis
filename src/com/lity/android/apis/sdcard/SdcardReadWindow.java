package com.lity.android.apis.sdcard;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageEventListener;
import android.os.storage.StorageManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SdcardReadWindow extends Activity implements OnClickListener {
	public static final String TAG = SdcardReadWindow.class.getSimpleName();

	private volatile boolean mUnmounted;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Button btn = new Button(this);

		btn.setOnClickListener(this);
		setContentView(btn);

		StorageManager sm = (StorageManager) getSystemService(Context.STORAGE_SERVICE);
		sm.registerListener(new UmsHandler());

		// ×¢²á¶ÔsdcardµÄ¼àÌý
		// registerSDCardListener();
	}

	private void registerSDCardListener() {
		IntentFilter intentFilter = new IntentFilter(Intent.ACTION_MEDIA_MOUNTED);
		intentFilter.addAction(Intent.ACTION_MEDIA_SCANNER_STARTED);
		intentFilter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);
		intentFilter.addAction(Intent.ACTION_MEDIA_REMOVED);
		intentFilter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
		intentFilter.addAction(Intent.ACTION_MEDIA_BAD_REMOVAL);
		intentFilter.addDataScheme("file");
		registerReceiver(sdcardListener, intentFilter);
	}

	@Override
	public void onClick(View v) {
		new Thread(new Runnable() {

			@Override
			public void run() {

				try {

					int i = 0;
					while (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
						File file = new File("/sdcard/write.txt");
						OutputStream os = null;
						os = new FileOutputStream(file);
						Log.v(TAG, "writing into file");
						os.write(String.valueOf(i++).getBytes());
						os.close();
					}
					Log.v(TAG, "write end");
				} catch (Throwable e) {
					Log.e(TAG, "write file, exception:" + e);
				}
			}
		}).start();
	}

	class UmsHandler extends StorageEventListener {
		@Override
		public void onStorageStateChanged(String arg0, String arg1, String arg2) {

			Log.v(TAG, "onStorageStateChanged, arg0:" + arg0 + ", arg1:" + arg1 + ", arg2:" + arg2);
			if (Environment.MEDIA_UNMOUNTED.equals(arg2)) {
				mUnmounted = true;
			}

			super.onStorageStateChanged(arg0, arg1, arg2);
		}

		@Override
		public void onUsbMassStorageConnectionChanged(boolean arg0) {
			super.onUsbMassStorageConnectionChanged(arg0);
			Log.v(TAG, "onUsbMassStorageConnectionChanged, arg0:" + arg0);
		}
	}

	private final BroadcastReceiver sdcardListener = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			String action = intent.getAction();
			Log.d("TAG", "sdcard action:::::" + action);
			if (Intent.ACTION_MEDIA_MOUNTED.equals(action)
					|| Intent.ACTION_MEDIA_SCANNER_STARTED.equals(action)
					|| Intent.ACTION_MEDIA_SCANNER_FINISHED.equals(action)) {
				// SD¿¨³É¹¦¹ÒÔØ

			} else if (Intent.ACTION_MEDIA_REMOVED.equals(action)
					|| Intent.ACTION_MEDIA_UNMOUNTED.equals(action)
					|| Intent.ACTION_MEDIA_BAD_REMOVAL.equals(action)) {
				// SD¿¨¹ÒÔØÊ§°Ü

			}

		}
	};
}
