package com.lity.android.apis.listview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lity.android.apis.R;

public class HorizontalListViewWindow extends Activity implements OnTouchListener {

    private HorizontalAdapter mAdapter;
    private HorizontalListView mListView;
    private LinearLayout mLinearLayout;
    private int mMoveY;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_horizontallistview_window);
        
        mListView = (HorizontalListView)findViewById(R.id.horizontal_listview);
        mListView.setDivider(getResources().getDrawable(R.drawable.listview_divider));
        mAdapter = new HorizontalAdapter(this);
        mListView.setAdapter(mAdapter);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            System.out.println("actiondown");
            mLinearLayout.scrollTo(0, mMoveY += 20);
            break;

        default:
            break;
        }
        return false;
    }

}
class HorizontalAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    
    public HorizontalAdapter(Context mContext) {
        super();
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

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
    public int getViewTypeCount() {
        return 1;
    }  

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        System.out.println("position == " + position);
        if (null == convertView) {
            convertView = mLayoutInflater.inflate(R.layout.hlistview_listitem, null);
        }
        ((TextView)convertView.findViewById(R.id.hlistview_listitem_textview)).setText(String.valueOf(position));
        return convertView;
    }


}
