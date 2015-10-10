package com.lity.android.apis.sdcard;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;

public class Md5SameWindow extends Activity {
	/*
	 * 通过adb安装软件的过程
	 * 1. apk文件从电脑上copy到/data/local/tmp/目录下
	 * 2. 安装成功后, /data/app/目录下会多一个以包-1(com.nduoa.nmarket-1.apk)文件, 此文件可认为是可执行文件.
	 * 3. 成功后 /data/local/temp/目录的临时文件装被删除
	 * 4. 安装apk后, 可以通过getPackageManager().getPackageInfo(pkname,0).applicationInfo.sourceDir来获取到包的可执行文件.
	 */
	
	public static final String TAG = Md5SameWindow.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		PackageManager pm = getPackageManager();
		PackageInfo info = null;
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		
		try {
			info = pm.getPackageInfo("com.nduoa.nmarket", 0);
			File file = new File(info.applicationInfo.sourceDir);
			is = new FileInputStream(file);
			
			int read;
			byte[] buffer = new byte[512];
			baos = new ByteArrayOutputStream();
			while (-1 != (read = is.read(buffer, 0, buffer.length))) {
				baos.write(buffer, 0, read);
			}
			Log.v(TAG, "read successfully file");
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			Log.e(TAG, "exception:" + e);
		} catch (IOException e) {
			e.printStackTrace();
			Log.e(TAG, "exception:" + e);
		}
	}
}
