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
	 * ͨ��adb��װ����Ĺ���
	 * 1. apk�ļ��ӵ�����copy��/data/local/tmp/Ŀ¼��
	 * 2. ��װ�ɹ���, /data/app/Ŀ¼�»��һ���԰�-1(com.nduoa.nmarket-1.apk)�ļ�, ���ļ�����Ϊ�ǿ�ִ���ļ�.
	 * 3. �ɹ��� /data/local/temp/Ŀ¼����ʱ�ļ�װ��ɾ��
	 * 4. ��װapk��, ����ͨ��getPackageManager().getPackageInfo(pkname,0).applicationInfo.sourceDir����ȡ�����Ŀ�ִ���ļ�.
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
