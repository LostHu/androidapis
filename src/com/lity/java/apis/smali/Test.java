package com.lity.java.apis.smali;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class Test extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ViewGroup view = new LinearLayout(this);
		int size = view.getChildCount();
		for (int i = 0; i < size; i++) {
			Log.v("smali", "smali" + view.getChildAt(i).getClass());
		}
		getWindow().getDecorView();
	}
}
