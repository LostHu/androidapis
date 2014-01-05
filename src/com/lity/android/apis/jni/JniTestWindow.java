package com.lity.android.apis.jni;

import java.io.File;

import com.lity.android.apis.R;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class JniTestWindow extends Activity implements OnClickListener{
	
	public static final String JNI_LIB_PATH = "libjnitest.so";
	public static final String JNI_LIB_NAME = "jnitest";
	
	public static final String TAG = JniTestWindow.class.getSimpleName();

	private Button mAddButton;
	
	private Button mSubButton;
	
	private JniTest mJniTest;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jni_jnitest_window);
		mAddButton = (Button)findViewById(R.id.jnitest_add_button);
		mAddButton.setOnClickListener(this);
		
		mSubButton = (Button)findViewById(R.id.jnitest_sub_button);
		mSubButton.setOnClickListener(this);
		
		PackageManager pm = getPackageManager(); 
		String dataDir = null;
		try {
			dataDir = pm.getApplicationInfo(getPackageName(), 0).dataDir;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		} 
		Log.v(TAG, "dataDir:" + dataDir);
		
		mJniTest = new JniTest();
		
//		File lib = getFileStreamPath(JNI_LIB_PATH);
//		lib.
	}

	@Override
	public void onClick(View v) {
		if (v == mAddButton) {
			int result = mJniTest.add(10, 80);
			Log.v(TAG, "result:" + result);
		}else if (v == mSubButton) {
			int result = mJniTest.sub(10, 80);
			Log.v(TAG, "result:" + result);
		}
	}
}
