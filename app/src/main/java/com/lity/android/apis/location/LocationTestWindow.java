package com.lity.android.apis.location;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class LocationTestWindow extends Activity {
	public final String TAG = getClass().getSimpleName();
	
	private LocationManager mLocationManager;
	private LocationListener mLocationListener = new LocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			
		}
		
		@Override
		public void onLocationChanged(Location location) {
			String debug = "locationChanged currentThread:" + Thread.currentThread();
			Toast.makeText(LocationTestWindow.this, debug, Toast.LENGTH_LONG).show();
			File file = new File("/sdcard/location_test.txt");
			OutputStream os = null;
			try {
				os = new FileOutputStream(file);
				os.write(debug.getBytes());
			} catch (Exception e) {
				if (null != os) {
					try {
						os.close();
					} catch (IOException e1) {
						// ignore exception
					}
				}
			}
			
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, mLocationListener); 

	}
}
