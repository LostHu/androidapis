package com.lity.android.apis.tabs;


import android.app.TabActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

import com.lity.android.apis.R;

public class Tab1 extends TabActivity {
	private Button button1;
	private Button button2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TabHost tabHost = getTabHost();
		LayoutInflater.from(this).inflate(R.layout.tabs, tabHost.getTabContentView());
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("100").setContent(R.id.textView1));
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("200").setContent(R.id.textView2));
		tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("300").setContent(R.id.textView3));
		tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator("400").setContent(R.id.textView3));
		for (int i = 5; i < 10; i++) {
			tabHost.addTab(tabHost.newTabSpec("tab" + i).setIndicator(i + "00 test hello").setContent(R.id.textView3));
		}
//		tabHost.getTabWidget().getChildTabViewAt(2).setVisibility(View.GONE);
		for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
			tabHost.getTabWidget().getChildTabViewAt(i).setLayoutParams(new LayoutParams(40, 80));
		}
		TabSpec tabSpec = tabHost.newTabSpec("tab5");
		tabHost.setCurrentTab(2);
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				Log.v("you click tab is ", tabId);
			}
		});
		//TabSpec tabSpec = tabHost.newTabSpec("tab5");
		button1 = (Button)findViewById(R.id.button1);
		button2 = (Button)findViewById(R.id.button2);
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.v("clicked button is ", v.toString());
			}
		});
	}

}
