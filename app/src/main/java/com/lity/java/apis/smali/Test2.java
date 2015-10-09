package com.lity.java.apis.smali;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class Test2 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Toast.makeText(this, "hell0", Toast.LENGTH_LONG).show();
	}
}
