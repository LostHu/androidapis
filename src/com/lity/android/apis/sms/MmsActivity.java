package com.lity.android.apis.sms;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

/**
 * ∂¡»°≤ –≈
 * @author litl
 *
 */
public class MmsActivity extends Activity {
	private static final boolean DEBUG = true;
	private static final String TAG = Activity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		readMms();
	}
	
	
	private void readMms() {
//		ContentResolver cr = getContentResolver();
		Cursor cur = getContentResolver().query(Uri.parse("content://mms"),
				null, null, null, null);
		cur.moveToFirst();
		Map<String, String> data = new HashMap<String, String>();
		
		for (int i = 0; i < cur.getColumnCount(); i++){
			data.put(cur.getColumnName(i), cur.getString(i));
		}
		Log.v(TAG, "mms:" + data);
		
		cur = getContentResolver().query(Uri.parse("content://mms/part/1"), null, null, null, null);
		cur.moveToFirst();
		data.clear();
		for (int i = 0; i < cur.getColumnCount(); i++){
			data.put(cur.getColumnName(i), cur.getString(i));
		}
		Log.v(TAG, "mms/part:" + data);
		
//		cur = getContentResolver().query(Uri.parse("content://mms/address"), null, null, null, null);
//		cur.moveToFirst();
//		data.clear();
//		for (int i = 0; i < cur.getColumnCount(); i++){
//			data.put(cur.getColumnName(i), cur.getString(i));
//		}
//		Log.v(TAG, "mms/address" + data);
	}
}
