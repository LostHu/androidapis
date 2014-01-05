package com.lity.android.apis.exgridview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class ExTestWindow extends Activity {
	
	ExGridView mGridView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mGridView = new ExGridView(this);
		mGridView.setNumColumns(2);
		mGridView.setAdapter(mAdapter);
		
		setContentView(mGridView);
	}
	
	
	private BaseAdapter mAdapter = new BaseAdapter() {
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (null == convertView) {
				convertView = new Button(getBaseContext());
			}
			Button button = (Button)convertView;
			button.setText("ext" + position);
			
			return convertView;
		}
		
		@Override
		public long getItemId(int position) {
			return 0;
		}
		
		@Override
		public Object getItem(int position) {
			return null;
		}
		
		@Override
		public int getCount() {
			return 50;
		}
	};
}
