package com.lity.android.apis.controls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lity.android.apis.R;

public class GenericListActivity extends ListActivity {

	private List<Map<String, Object>> dataSource;
	private GenericAdapter genericAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flowerlist);
		dataSource = getData();
		genericAdapter = new GenericAdapter(this);
		setListAdapter(genericAdapter);
	}
	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		dataSource.remove(position);
		//dataSource.
		genericAdapter.notifyDataSetChanged();
		
	}


	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("name", "康乃馨");
		map.put("symbol", "母亲我爱您");
		map.put("imgId", R.drawable.space_go);
		listData.add(map);
		
		map = new HashMap<String, Object>();
		map.put("name", "向日葵");
		map.put("symbol", "爱慕、光辉、忠诚 ");
		map.put("imgId", R.drawable.space_go);
		listData.add(map);
	
		map = new HashMap<String, Object>();
		map.put("name", "山茶");
		map.put("symbol", "可爱、谦让、理想的爱、了不起的魅力");
		map.put("imgId", R.drawable.space_go);
		listData.add(map);
		
		map = new HashMap<String, Object>();
		map.put("name", "百合");
		map.put("symbol", "顺利、心想事成、祝福、高贵 ");
		map.put("imgId", R.drawable.space_go);
		listData.add(map);
		
		map = new HashMap<String, Object>();
		map.put("name", "郁金香");
		map.put("symbol", "爱的表白、荣誉、祝福永恒 ");
		map.put("imgId", R.drawable.space_go);
		listData.add(map);
		map.get("name");
		return listData;
	}

	
	final class GenericAdapter extends BaseAdapter {

		private LayoutInflater inflater;
		
		public GenericAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}
		@Override
		public int getCount() {
			return dataSource.size();
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
			ViewHolder holder = null;
			if(null == convertView) {
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.listview_listitem, null);
				holder.name = (TextView)convertView.findViewById(R.id.name);
				holder.symbol = (TextView)convertView.findViewById(R.id.symbol);
				holder.rightImageView = (ImageView)convertView.findViewById(R.id.img);
				convertView.setTag(holder);
			}
			else {
				holder = (ViewHolder)convertView.getTag();
			}
			holder.name.setText((String)dataSource.get(position).get("name"));
			holder.symbol.setText((String)dataSource.get(position).get("symbol"));
			holder.rightImageView.setBackgroundResource((Integer)dataSource.get(position).get("imgId"));
			return convertView;
		}
	}

	final class ViewHolder extends Object {
		public TextView name;
		public TextView symbol;
		public ImageView rightImageView;
	}
}
