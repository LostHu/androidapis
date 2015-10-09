package com.lity.android.apis.window;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lity.android.apis.R;

public class ListViewWindow extends Activity {

    private ListView mListView;
    private ListViewAdapter mAdapter;
    private HashMap<Integer, View> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_window);
        
        mListView = (ListView)findViewById(android.R.id.list);
        mData = new HashMap<Integer, View>();
        mAdapter = new ListViewAdapter();
        mListView.setAdapter(mAdapter);
    }

    class ListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 100;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.v("bug", "position :" + position + ", convertview:" + convertView);
            final int index = position % 15; 
            if (null == mData.get(index)) {
               convertView = LayoutInflater.from(ListViewWindow.this).inflate(
                        R.layout.listview_listitem, null);
               String str = "postion: " + position;
                ((TextView)convertView.findViewById(R.id.name)).setText(str);
                mData.put(index, convertView);
                return convertView;
            }
            else {
                return mData.get(index);
            }
        }
    }
}
