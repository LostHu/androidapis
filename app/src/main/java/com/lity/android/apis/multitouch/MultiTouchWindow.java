package com.lity.android.apis.multitouch;

import com.lity.android.apis.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

public class MultiTouchWindow extends Activity {
	public static final String TAG = "DEBUG";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.multitouch_window);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			Log.v(TAG, "ACTION_DOWN,event:" + event);
			Log.v(TAG, "ACTION_DOWN,event.getX(0):" + event.getX(0) + ", event.getY(0):" + event.getY(0));
			break;
		case MotionEvent.ACTION_MOVE:
			Log.v(TAG, "ACTION_MOVE,event:" + event);
			Log.v(TAG, "ACTION_MOVE,event.getX(0):" + event.getX(0) + ", event.getY(0):" + event.getY(0));
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			Log.v(TAG, "ACTION_POINTER_DOWN,event:" + event);
			Log.v(TAG, "ACTION_POINTER_DOWN,event.getX(0):" + event.getX(0) + ", event.getY(0):" + event.getY(0));
			break;
		case MotionEvent.ACTION_POINTER_UP:
			Log.v(TAG, "ACTION_POINTER_UP,event:" + event);
			Log.v(TAG, "ACTION_POINTER_UP,event.getX(0):" + event.getX(0) + ", event.getY(0):" + event.getY(0));
			break;
		case MotionEvent.ACTION_UP:
			Log.v(TAG, "ACTION_UP,event:" + event);
			Log.v(TAG, "ACTION_UP,event.event.getX(0):" + event.getX(0) + ", event.getY(0):" + event.getY(0));
			break;
		case MotionEvent.ACTION_CANCEL:
			Log.v(TAG, "ACTION_CANCEL,event:" + event);
			Log.v(TAG, "ACTION_CANCEL,event.getX(0):" + event.getX(0) + ", event.getY(0):" + event.getY(0));
			break;
		case MotionEvent.ACTION_OUTSIDE:
			Log.v(TAG, "ACTION_OUTSIDE,event:" + event);
			Log.v(TAG, "ACTION_OUTSIDE,event.getX(0):" + event.getX(0) + ", event.getY(0):" + event.getY(0));
			break;
		default:
			break;
		}
		
		return true;
	}
}
