package com.lity.android.apis.listview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.lity.android.apis.R;

public class AutoScrollWindow extends Activity {
	
	private ListView mListView;
	
	private List<Map<String, String>> mData = new ArrayList<Map<String,String>>();
	{
		for (int i = 0; i < 100; i++) {
			HashMap<String, String> item = new HashMap<String, String>();
			item.put("text", "text" + i);
			mData.add(item);
		}
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_autoscroll_window);
		mListView = (ListView)findViewById(R.id.listview_autoscroll_testlist);
		
		ListAdapter adapter = new SimpleAdapter(this, mData, 
				android.R.layout.simple_list_item_1, new String[]{"text"}, new int[]{android.R.id.text1});
		mListView.setAdapter(adapter);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (KeyEvent.KEYCODE_7 == keyCode) {
			Log.v("DEBUG", "scrolling");
//			mListView.smoothScrollBy(2000, 1000 * 60); 
//			Log.v("DEBUG", mListView.get)
//			mListView.
		}
		return super.onKeyDown(keyCode, event);
	}
}

