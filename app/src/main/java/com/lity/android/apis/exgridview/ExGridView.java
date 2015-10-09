package com.lity.android.apis.exgridview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

public class ExGridView extends GridView {

	public ExGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public ExGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ExGridView(Context context) {
		super(context);
		init();
	}

	private View mHeaderView;
	private void init() {
		Button temp = new Button(getContext());
		temp.setText("header view");
		mHeaderView = temp;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		mHeaderView.measure(widthMeasureSpec, heightMeasureSpec);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
//		t += 100;
		super.onLayout(changed, l, t, r, b);
	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.translate(0, 100);
		super.draw(canvas);
	}
}
