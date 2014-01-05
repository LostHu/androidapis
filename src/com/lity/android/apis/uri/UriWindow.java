package com.lity.android.apis.uri;

import com.lity.android.apis.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SimpleAdapter.ViewBinder;

public class UriWindow extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.uri_window);
		
		// http:// ����
		View view = findViewById(R.id.uri_httplink_1);
		view.setOnClickListener(this);
		
		view = findViewById(R.id.uri_httplink_2);
		view.setOnClickListener(this);
		
		view = findViewById(R.id.uri_httplink_3);
		view.setOnClickListener(this);
		
		view = findViewById(R.id.uri_httplink_4);
		view.setOnClickListener(this);
		
		
		// market ����
		Button btn = (Button)findViewById(R.id.uri_marketlink_1);
		btn.setOnClickListener(this);
		
		btn = (Button)findViewById(R.id.uri_marketlink_2);
		btn.setOnClickListener(this);
		
		btn = (Button)findViewById(R.id.uri_marketlink_3);
		btn.setOnClickListener(this);
		
		btn = (Button)findViewById(R.id.uri_marketlink_4);
		btn.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		Button btn = (Button)v;
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(btn.getText().toString()));
//		intent.setAction(Intent.action_NDEF_DISCOVERED);
		intent.addCategory(Intent.CATEGORY_BROWSABLE);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		startActivity(intent);
	}
}
