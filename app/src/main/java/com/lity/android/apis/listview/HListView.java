package com.lity.android.apis.listview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ListView;

public class HListView extends ListView {

    public HListView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public HListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }
    public HListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see android.widget.ListView#onMeasure(int, int)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        System.out.println("onMeasure method");
        System.out.println("childCount == " + getChildCount());
//        System.out.println("before width == " + getMeasuredWidth());
//        for (int i = 0; i < getChildCount(); i++) {
//            System.out.println("child" + i + "== " + getChildAt(i).getMeasuredWidth()
//                    + "," + getChildAt(i).getMeasuredHeight());
//        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        System.out.println("after width == " + getMeasuredWidth());
        System.out.println("childCount == " + getChildCount());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO Auto-generated method stub
        System.out.println("onLayout method");
        System.out.println("childCount == " + getChildCount());
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        System.out.println("onDraw method");
        System.out.println("childCount == " + getChildCount());
        super.onDraw(canvas);
    }
    

}
