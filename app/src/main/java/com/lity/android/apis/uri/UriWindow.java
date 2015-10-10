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
		
		// http:// 链接
		View view = findViewById(R.id.uri_httplink_1);
		view.setOnClickListener(this);
		
		view = findViewById(R.id.uri_httplink_2);
		view.setOnClickListener(this);
		
		view = findViewById(R.id.uri_httplink_3);
		view.setOnClickListener(this);
		
		view = findViewById(R.id.uri_httplink_4);
		view.setOnClickListener(this);
		
		
		// market 链接
		Button btn = (Button)findViewById(R.id.uri_marketlink_1);
		btn.setOnClickListener(this);
		
		btn = (Button)findViewById(R.id.uri_marketlink_2);
		btn.setOnClickListener(this);
		
		btn = (Button)findViewById(R.id.uri_marketlink_3);
		btn.setOnClickListener(this);
		
		btn = (Button)findViewById(R.id.uri_marketlink_4);
		btn.setOnClickListener(this);

		view = findViewById(R.id.uri_marketlink_serializable);
		view.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (R.id.uri_marketlink_serializable == v.getId()) {
			Intent intent = new Intent();
			intent.setAction("cn.jpush.android.ui.PushActivity");
			intent.addCategory("android.intent.category.DEFAULT");
			Custom cus = new Custom();
			cus.aaa = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
			cus.bbb = 100000;
			intent.putExtra("aaaa", cus);
			intent.putExtra("bbbb", "bbbb");
			startActivity(intent);
		} else {
			Button btn = (Button)v;
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(btn.getText().toString()));
//		intent.setAction(Intent.action_NDEF_DISCOVERED);
			intent.addCategory(Intent.CATEGORY_BROWSABLE);
			intent.addCategory(Intent.CATEGORY_DEFAULT);
			startActivity(intent);
		}
	}
}
