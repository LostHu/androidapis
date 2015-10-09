package com.lity.android.apis.jarload;

import java.io.File;

import com.lity.android.apis.R;

import dalvik.system.DexClassLoader;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class JarLoadWindow extends Activity implements OnClickListener {
	
	public static final String TAG = JarLoadWindow.class.getSimpleName();

	private Button mStartButton;
	private IJarLoad mJarLoader;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jarload_window);
		
		mStartButton = (Button)findViewById(R.id.jarload_startload_button);
		mStartButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
	    final File optimizedDexOutputPath = new File("/sdcard/jarloadtest.jar");
            DexClassLoader cl = new DexClassLoader(optimizedDexOutputPath.getAbsolutePath(),
                Environment.getExternalStorageDirectory().toString(), null, getClassLoader());
            Class libProviderClazz = null;
            try {
                libProviderClazz = cl.loadClass("com.lity.android.apis.jarload.JarLoadImpl");
                mJarLoader = (IJarLoad)libProviderClazz.newInstance();
            } catch (Exception exception) {
                exception.printStackTrace();
                Log.e(TAG, "onClick(), exception:" + exception);
            }
            if (null != mJarLoader) {
				mStartButton.setText("success");
				int result = mJarLoader.add(10, 80);
				Log.v(TAG, "result:" + result);
				result = mJarLoader.sub(10, 80);
				Log.v(TAG, "result:" + result);
			}
	}
	
}
