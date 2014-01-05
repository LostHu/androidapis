package com.lity.android.apis.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lity.android.apis.R;

public class HListViewAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    
    public HListViewAdapter(Context mContext) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        System.out.println("position == " + position);
        if (null == convertView) {
            convertView = mLayoutInflater.inflate(R.layout.hlistview_listitem, null);
        }
        ((TextView)convertView.findViewById(R.id.hlistview_listitem_textview)).setText(String.valueOf(position));
        return convertView;
    }

}
