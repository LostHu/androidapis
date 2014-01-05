package com.lity.android.apis.system;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SmsPopActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView textView = new TextView(this);
		textView.setText("你有最新短消息");
		setContentView(textView);
	}
}
