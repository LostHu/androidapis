package com.lity.android.apis.draw;

import com.lity.android.apis.R;
import com.stickycoding.rokon.Movement;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class ViewDrawWindow extends Activity implements OnClickListener {

	private View mTextView;
	private Button mTestButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.draw_viewdraw_window);
		mTextView = findViewById(R.id.draw_viewdraw_test_view);
//		mTextView.setLongClickable(false);
		mTestButton = (Button)findViewById(R.id.draw_viewdraw_test_button);
		mTestButton.setOnClickListener(this);
		
//		mTextView.setScrollContainer(true);
//		mTextView.setVerticalScrollBarEnabled(true);
//		mTextView.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
//		mTextView.setFadingEdgeLength(100);
//		mTextView.setMovementMethod(ScrollingMovementMethod.getInstance());
//		mTextView.setFocusable(true);
//		mTextView.setClickable(true);
//		mTextView.setLongClickable(true);
		TextView t;
	}

	@Override
	public void onClick(View v) {
		mTextView.scrollBy(10, 10);
//		v.scrollBy(10, 10);
	}
}

class View1 extends View {

	public View1(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public View1(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public View1(Context context) {
		super(context);
	}
	
	@Override
	protected int computeVerticalScrollRange() {
		return super.computeVerticalScrollRange() + 300;
	}
	
	@Override
	protected int computeHorizontalScrollRange() {
		return super.computeHorizontalScrollRange() + 200;
	}
}
