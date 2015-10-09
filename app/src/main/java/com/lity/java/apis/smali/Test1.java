package com.lity.java.apis.smali;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class Test1 extends FrameLayout {

	public Test1(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public Test1(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Test1(Context context) {
		super(context);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Activity activity = (Activity) getContext();
		it(activity.getWindow().getDecorView());
		return super.dispatchTouchEvent(ev);

	}

	private void it(View view) {
		if (!(view instanceof ViewGroup)) {
			Log.v("smali", "smali" + view.getClass());
			return;
		}
		ViewGroup vg = (ViewGroup) view;
		for (int i = 0; i < vg.getChildCount(); i++) {
			it(vg.getChildAt(i));
		}
	}

}
