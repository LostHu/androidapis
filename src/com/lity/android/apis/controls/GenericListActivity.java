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
		
		map.put("name", "����ܰ");
		map.put("symbol", "ĸ���Ұ���");
		map.put("imgId", R.drawable.space_go);
		listData.add(map);
		
		map = new HashMap<String, Object>();
		map.put("name", "���տ�");
		map.put("symbol", "��Ľ����ԡ��ҳ� ");
		map.put("imgId", R.drawable.space_go);
		listData.add(map);
	
		map = new HashMap<String, Object>();
		map.put("name", "ɽ��");
		map.put("symbol", "�ɰ���ǫ�á�����İ����˲��������");
		map.put("imgId", R.drawable.space_go);
		listData.add(map);
		
		map = new HashMap<String, Object>();
		map.put("name", "�ٺ�");
		map.put("symbol", "˳���������³ɡ�ף�����߹� ");
		map.put("imgId", R.drawable.space_go);
		listData.add(map);
		
		map = new HashMap<String, Object>();
		map.put("name", "������");
		map.put("symbol", "���ı�ס�������ף������ ");
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
