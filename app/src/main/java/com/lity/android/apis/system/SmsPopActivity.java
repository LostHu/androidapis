package com.lity.android.apis.system;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SmsPopActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView textView = new TextView(this);
		textView.setText("�������¶���Ϣ");
		setContentView(textView);
	}
}
