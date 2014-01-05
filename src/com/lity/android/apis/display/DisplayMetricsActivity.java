package com.lity.android.apis.display;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

public class DisplayMetricsActivity extends Activity {

	public static final String TAG = DisplayMetricsActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		String msg = metrics.toString();

		TextView tv = new TextView(this);
		setContentView(tv);

		tv.setText(msg);
		Log.v(TAG, msg);
	}
}
