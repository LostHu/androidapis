package com.lity.android.apis.contract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.PhoneLookup;
import android.provider.ContactsContract.RawContacts;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.lity.android.apis.App;
import com.lity.android.apis.R;

public class ContactManagerWindow extends Activity {

	/** 菜单Id,根据名字删除联系人 **/
	private final static int MENU_ID_DELETE_BY_NAME = Menu.FIRST + 0;
	
	/** 菜单Id,根据电话号码删除联系人 **/
	private final static int MENU_ID_DELETE_BY_MOBILE = Menu.FIRST + 1;
	
	/** 对话框Id,输入名字删除联系人 **/
	private final static int DIALOG_ID_INPUTNAME_TO_DELETE = 1000 + 1;
	
	/** 对话框Id,输入mobile号码删除联系人 **/
	private final static int DIALOG_ID_INPUTMOBILE_TO_DELETE = 1000 + 2;
	
	
	
	
//	private final static int MENU_ID_DELETE_BY_NAME = Menu.FIRST + 2;
	
	
	/** 菜单的id **/
    private final int[] mOptionId = new int[]{MENU_ID_DELETE_BY_NAME, MENU_ID_DELETE_BY_MOBILE};
    private final String[] mOptionTitle = new String[]{"delete by Name", "delete by mobile"};
    
    private Cursor mCursor;
    
    private List<Map<String, String>> mCacheData;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactmanager_window);
        ListView listView = (ListView)findViewById(R.id.contactmanager_listview);
        
        TextView text = new TextView(this);
        text.setText("请点击菜单键!");
        
        ContactAdapter adapter = new ContactAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        // 创建菜单,并把已定义好的菜单全加进去
        for (int i = 0; i < mOptionId.length; i++) {
            menu.add(0, mOptionId[i], i, mOptionTitle[i]);
        }
        return result;
    }
    
    @Override
    protected Dialog onCreateDialog(int id) {
    	Dialog dialog;
    	switch (id) {
		case DIALOG_ID_INPUTNAME_TO_DELETE:
			AlertDialog.Builder builder = new Builder(this);
			builder.setTitle("请输入要删除联系人的名字:");
			final EditText inputEditText = new EditText(this);
			builder.setView(inputEditText);
			builder.setPositiveButton("确定", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					delContact(ContactManagerWindow.this, inputEditText.getText().toString());
			        mCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
			        ((BaseAdapter)((ListView)findViewById(R.id.contactmanager_listview)).getAdapter()).notifyDataSetChanged();
				}
			});
			builder.setNegativeButton("取消", null);
			dialog = builder.create();
			break;
		default:
			// 默认让父类处理
			dialog = super.onCreateDialog(id);
			break;
		}
    	return dialog;
    }
    

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	boolean result = super.onOptionsItemSelected(item);
    	Log.v(App.TAG, "item_id: " + item.getItemId());
    	switch (item.getItemId()) {
		case MENU_ID_DELETE_BY_NAME:
			showDialog(DIALOG_ID_INPUTNAME_TO_DELETE);
			break;
		default:
			break;
		}
        return result;
    }
    
    private void readContractToCache(){
        ContentResolver cr = getContentResolver();
        
        mCursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        if (null == mCacheData) {
			mCacheData = new ArrayList<Map<String,String>>();
		}
        // 打印出来所有列
        mCursor.moveToNext();
        Map<String, String> columns = new HashMap<String, String>();
        for (int i = 0; i < mCursor.getColumnCount(); i++) {
        	columns.put(mCursor.getColumnName(i), mCursor.getString(i));
		}
        Log.v(App.TAG, "columns: " + columns);
        
/*        while (mCursor.moveToNext()) {
			Log.v(App.TAG, "mCursor.getPosition():" + mCursor.getPosition());
			HashMap<String, String> itemMap = new HashMap<String, String>();
			// Row Id
			itemMap.put(ContactsContract.Contacts._ID, mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts._ID)));
			// contract name
			itemMap.put(ContactsContract.Contacts.DISPLAY_NAME, mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
			// 是否有电话号码
			itemMap.put(ContactsContract.Contacts.HAS_PHONE_NUMBER, mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
			// 电话号码
//			itemMap.put(ContactsContract.Contacts., mCursor.getString(mCursor.getColumnIndex(ContactsContract.PhoneLookup.NUMBER)));
			
	        Log.v(App.TAG, "itemMap:" + itemMap);
	        mCacheData.add(itemMap);
		}*/

    }
    
    
    @Override
    protected void onResume() {
        super.onResume();
//        readContractToCache();
        mCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        ((BaseAdapter)((ListView)findViewById(R.id.contactmanager_listview)).getAdapter()).notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCursor.close();
    }

    private void deleteContract(int position){
        mCursor.moveToPosition(position);
        int columnIndex = mCursor.getColumnIndex(PhoneLookup._ID);
        
        ContentResolver cr = getContentResolver();
        
        String sqlWhere = Contacts._ID + " = " + mCursor.getString(columnIndex);
        if (App.DEBUG) {
            Log.v(App.TAG, "sqlWhere: " + sqlWhere);
        }
        int affectRow = cr.delete(ContactsContract.RawContacts.CONTENT_URI, ContactsContract.RawContacts._ID + "=?", new String[]{mCursor.getString(columnIndex)});
        if (App.DEBUG) {
            Log.v(App.TAG, "affect row count: " + affectRow);
        }
        if (1 == affectRow) {
            mCursor.close();
        }
    }
    
    /**
     * 删除冗余的记录
     * @return
     */
    private int deleteRepeatContacts() {
    	ContentResolver cr = getContentResolver();
    	Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
    	int count = 0;
    	cursor.moveToFirst();
    	Map<String, String> all = new HashMap<String, String>();
    	while (mCursor.moveToNext()) {
			String display = mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
			String number = mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			if (all.containsKey(display) && all.containsValue(number)) {
				long _id = mCursor.getLong(mCursor.getColumnIndex(ContactsContract.Contacts._ID));
				count += cr.delete(ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, _id), null, null);
			}else {
				all.put(display, number);
			} 
		}
    	Log.v(App.TAG, "delete contact count:" + count);
    	cursor.close();
    	return count;
    	
    }

    /*
     * ListAdapter,
     */


    private class ContactAdapter extends BaseAdapter implements OnItemClickListener {

        public ContactAdapter() {
            super();
        }

        @Override
        public int getCount() {
            if (App.DEBUG) {
                Log.v(App.TAG, "getCount: " + (mCursor == null ? 0 : mCursor.getCount()));
            }
            return mCursor == null ? 0 : mCursor.getCount();
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
                convertView = LayoutInflater.from(ContactManagerWindow.this).inflate(R.layout.contactmanager_listitem, null);
            }
            if (mCursor.moveToPosition(position)) {
                TextView text = (TextView)convertView.findViewById(R.id.contactmanager_listitem_displayname);
                text.setText(mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
                text = (TextView)convertView.findViewById(R.id.contactmanager_listitem_telenumber);
                int columnIndex = mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID);
                if (-1 == columnIndex) {
                    text.setText(String.valueOf(columnIndex));
                }else {
                    text.setText(mCursor.getString(columnIndex));
                }
            }
            
            return convertView;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                long id) {
        	mCursor.moveToPosition(position);
        	long rowId = Long.parseLong(mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts._ID)));
        	Log.v("DEBUG", "RowId == " + rowId);
        	startActivity(ViewContractActivity.createActivity(ContactManagerWindow.this, rowId));
        }

    }
    
    
    private void delContact(Context context, String name) {

    	Cursor cursor = getContentResolver().query(Data.CONTENT_URI, 
    			new String[] {Data.RAW_CONTACT_ID}, ContactsContract.Contacts.DISPLAY_NAME + "=?", 
    			new String[] {name}, null);

    	ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

		if (cursor.moveToFirst()) {

			do {
				long Id = cursor.getLong(cursor.getColumnIndex(Data.RAW_CONTACT_ID));
				ops.add(ContentProviderOperation.newDelete(ContentUris.withAppendedId(RawContacts.CONTENT_URI, Id)).build());
				try {
					getContentResolver().applyBatch(ContactsContract.AUTHORITY,
							ops);
				}
				catch (Exception e)
				{
					
				}
			} while (cursor.moveToNext());

			cursor.close();

		}

	}
}
