package com.lity.android.apis.listview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lity.android.apis.R;

public class TextViewWindow extends Activity {
    
    private ListView mListView;
    private TestAdapter mAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.horizontallistview_window);
        
        mListView = (ListView)findViewById(R.id.horizontal_listview);
        
        mAdapter = new TestAdapter(this);
        mListView.setAdapter(mAdapter);
        
    }
    
    
}

class TestAdapter extends BaseAdapter {
    private static final int CACHE_COUNT = 10;
    private HashMap<Integer, View> mCache = new HashMap<Integer, View>();
    private ArrayList<View> mUsingViews = new ArrayList<View>();
    private LayoutInflater mLayoutInflater;

    /**
     * 
     */
    public TestAdapter(Context context) {
        super();
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 50;
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
        if (null == convertView) {
            convertView = mLayoutInflater.inflate(R.layout.display_normal_coupon, null);
        }
        return convertView;
    }
    
}

