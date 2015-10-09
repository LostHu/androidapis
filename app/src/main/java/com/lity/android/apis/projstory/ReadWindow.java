package com.lity.android.apis.projstory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lity.android.apis.R;

public class ReadWindow extends Activity {
	
	public static final String TAG = "DEBUG";

	private static final int BUFFER_LENGTH = 1024 * 1024;
	
	private ScrollView mScrollView;
	private TextView mContentTextView;
	
	private byte[] mBuffer = new byte[BUFFER_LENGTH];
	
	private String mFilePath;
	
	
	private boolean mHasInited;
	private int mCurrentRead;
	private int mReadCount;
	private int mCurrentReadLength;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.projstory_read_window);
		mScrollView = (ScrollView)findViewById(R.id.projstory_read_scrollview);
		mContentTextView = (TextView)findViewById(R.id.projstory_read_content_textview);
		mFilePath = "/sdcard/blow_cn.txt";
		firstRead();
		String text = readTextToCacheByLevel(0);
		text = text.replace("\r\n", "\n");
		mContentTextView.setText(text);
		
		mScrollView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				Log.v(TAG, "========== scrollview ==========");
				Log.v(TAG, "scrollview, measuredwidth:" + mScrollView.getMeasuredWidth() + ", measuredheight:" + mScrollView.getMeasuredHeight());
				Log.v(TAG, "scrollview, width:" + mScrollView.getWidth() + ", height:" + mScrollView.getHeight());
				
				Log.v(TAG, "textview, measuredwidth:" + mContentTextView.getMeasuredWidth() + ", measuredheight:" + mContentTextView.getMeasuredHeight());
				Log.v(TAG, "textview, width:" + mContentTextView.getWidth() + ", height:" + mContentTextView.getHeight());
				Log.v(TAG, "textview, scrollx:" + mScrollView.getScrollX() + ", scrolly:" + mScrollView.getScrollY());
				
				
				return false;
			}
		});
	}
	
	
	private void init() {
		mHasInited = false;
		mCurrentRead = 0;
		mReadCount = 0;
	}
	
	@Override
	protected void onResume() {
		super.onResume();

	}
	
	
	private void firstRead() {
		init();
		if (null == mFilePath || mFilePath.equals("")) return;
		File file = new File(mFilePath);
		if (!file.exists() || 0 == file.length()) {
			return ;
		}
		
		mReadCount = (int)(file.length() / BUFFER_LENGTH);
		if (0 != file.length() % BUFFER_LENGTH) {
			mReadCount +=1;
		}
	}

	private String readTextToCacheByLevel(int level) {
		FileInputStream is = null;
		if (0 > level && level < mReadCount) {
			return null;
		}
		int seek = level * BUFFER_LENGTH;
		if (mReadCount-1 == level) {
			Arrays.fill(mBuffer, (byte)0);
		}
		int readLength = -1;
		try {
			is = new FileInputStream(mFilePath);
			is.skip(seek);
			readLength = is.read(mBuffer, 0, BUFFER_LENGTH);
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (IOException e) {
					Log.e(TAG, e.toString());
				}
			}
		}
		if (-1 == readLength) {
			return null;
		}
		String result = null;
		try {
			result = new String(mBuffer, 0, readLength, "GB2312");
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, e.toString());
		}
		return result;
	}
}

class ReadTextView extends TextView {

	public ReadTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ReadTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ReadTextView(Context context) {
		super(context);
	}
	
	
}
