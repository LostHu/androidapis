package com.lity.android.apis.bluetooth;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.lity.android.apis.R;

public class BluetoothWindow extends Activity {

	private Button blueButton;
	private Button wifiButton;
	private Button gpsButton;
	private Button recordButton;
	private Button anotherButton;

	// ¼��
	private MediaRecorder mMediaRecorder;
	private boolean mIsRecording;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_window);

		blueButton = (Button) findViewById(R.id.open_bluetooth);
		blueButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// ������������
				BluetoothAdapter _blueAdapter = BluetoothAdapter.getDefaultAdapter();
				_blueAdapter.enable();
			}
		});

		wifiButton = (Button) findViewById(R.id.open_wifi);
		wifiButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// ����wifi����
				WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
				wifiManager.setWifiEnabled(true);
			}
		});

		gpsButton = (Button) findViewById(R.id.open_gps);
		gpsButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// ����GPS����
				LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
				if (!locationManager
						.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {

					Intent gpsIntent = new Intent();
					gpsIntent.setClassName("com.android.settings",
							"com.android.settings.widget.SettingsAppWidgetProvider");
					gpsIntent.addCategory("android.intent.category.ALTERNATIVE");
					gpsIntent.setData(Uri.parse("custom:3"));
					try {
						PendingIntent.getBroadcast(BluetoothWindow.this, 0, gpsIntent, 0).send();
					} catch (CanceledException e) {
						e.printStackTrace();
					}

				}
			}
		});

		recordButton = (Button) findViewById(R.id.open_record);
		recordButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// ¼�ƺ�ͣ¼����
				if (!mIsRecording) {
					mMediaRecorder = new MediaRecorder();
					mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
					mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
					mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
					mMediaRecorder.setOutputFile("/sdcard/com.luutoo.mocard/a.mp3");
					try {
						mMediaRecorder.prepare();
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println("++++++++++++++++++++++++++ throw exception");
						System.out.println(e);
					}
					mMediaRecorder.start();
					mIsRecording = true;
				} else {
					mMediaRecorder.stop();
					mMediaRecorder.reset();
					mMediaRecorder.release();
					mIsRecording = false;
				}
			}
		});

		anotherButton = (Button) findViewById(R.id.open_another);
		anotherButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Intent intent = new Intent();
				// intent.setComponent(new ComponentName("com.example.android.apis", "text.Link"));
				// // intent.setc
				// //pkgName Ӧ�ó���İ���
				// //activityName Ӧ�ó����е�activity����
				// startActivity(intent);
				PackageManager pManager = getPackageManager();
				Intent intent = pManager.getLaunchIntentForPackage("com.example.android.apis");
				startActivity(intent);
			}
		});

	}

}
