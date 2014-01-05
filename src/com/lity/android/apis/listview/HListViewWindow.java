package com.lity.android.apis.listview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.lity.android.apis.R;

public class HListViewWindow extends Activity {

    private HorizontalAdapter mAdapter;
    private HListView mListView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hlistview_window);
        
        mListView = (HListView)findViewById(R.id.h_listview);
        mAdapter = new HorizontalAdapter(this);
        mListView.setAdapter(mAdapter);
        
        ImageView view = null;
    }


}
