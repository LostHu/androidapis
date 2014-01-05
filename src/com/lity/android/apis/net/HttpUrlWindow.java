package com.lity.android.apis.net;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.lity.android.apis.R;

public class HttpUrlWindow extends Activity {

	private TextView textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.httpactivity);
		
		textView = (TextView)findViewById(R.id.textView1);
		URL url;
		byte [] b = new byte[200];
		try {
			url = new URL("http://www.baidu.com");
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
		
			int nRC = http.getResponseCode();
			if (nRC == HttpURLConnection.HTTP_OK)
			{
				InputStream is = http.getInputStream();
				is.read(b);
				//is.
				
				textView.setText(new String(b, "GB2312"));
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			Log.v("123", "4556");
		}
	}
	
}
