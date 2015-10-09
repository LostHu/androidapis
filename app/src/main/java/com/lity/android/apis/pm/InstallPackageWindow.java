package com.lity.android.apis.pm;

import java.io.InputStream;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class InstallPackageWindow extends Activity {

	private static final String TAG = "InstallPackageWindow";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Process p;
		InputStream is;
		try {
			// p = Runtime.getRuntime().exec("pm install -t /sdcard/market.apk");
			p = Runtime.getRuntime().exec("adb shell pm install -t /sdcard/market.apk");
			is = p.getInputStream();
			byte[] buffer = new byte[512];
			int read;
			while (-1 != (read = is.read(buffer, 0, buffer.length))) {
				String out = new String(buffer, 0, read);
				Log.v(TAG, out);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, e.toString());
		}
	}
}
