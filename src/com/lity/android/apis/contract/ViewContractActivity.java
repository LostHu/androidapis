package com.lity.android.apis.contract;

import com.lity.android.apis.R;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.TextView;

public class ViewContractActivity extends Activity {
	
	protected static final String CONTRACT_ROW_ID = "CONTRACT_ROW_ID";
	
	TextView mNameTextView;
	TextView mTelTextView;

	public static Intent createActivity(Context context, long rowId){
		Intent intent = new Intent();
		intent.setClass(context, ViewContractActivity.class);
		intent.putExtra(CONTRACT_ROW_ID, rowId);
		return intent;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewcontract_activity);
		mNameTextView = (TextView)findViewById(R.id.viewcontract_name_textview);
		mTelTextView = (TextView)findViewById(R.id.viewcontract_tel_textview);
		
		final long rowId = getIntent().getLongExtra(CONTRACT_ROW_ID, Long.MIN_VALUE);
		if (Long.MIN_VALUE == rowId) {
			Log.v("DEBUG", "²ÎÊý´íÎó");
		}else {
	        ContentResolver cr = getContentResolver();
//	        Uri uri = ContentUris.withAppendedId(ContactsContract.RawContacts.CONTENT_URI, rowId);
//	        Cursor mCursor = cr.query(uri, null, null, null, null);
//	        Uri uri = ContentUris.withAppendedId(, rowId);
	        Cursor mCursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.Contacts._ID + " = ?", new String[]{String.valueOf(rowId)}, null);
	        if (null != mCursor && mCursor.moveToFirst()) {
	        	Log.v("DEBUG", mCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME) + "  " + mCursor.getColumnIndex(ContactsContract.PhoneLookup.NUMBER));
	        	for (int i = 0; i < mCursor.getColumnCount(); i++) {
					Log.v("DEBUG", i + ":" + mCursor.getString(i) + ", " + mCursor.getColumnName(i));
				}
	        	mNameTextView.setText(mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
	        	mTelTextView.setText(mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts.Data.DATA1)));
			}
		}
	}
}
