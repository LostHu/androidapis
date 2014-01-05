/*
 * HorizontalListView.java v1.5
 *
 * 
 * The MIT License
 * Copyright (c) 2011 Lity Lee (lity_lee@163.com)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */

package com.lity.android.apis.listview;

import java.util.LinkedList;
import java.util.Queue;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Scroller;

public class HorizontalListView2 extends AbsListView {

	public boolean mAlwaysOverrideTouch = true;
	protected ListAdapter mAdapter;
	
	
	/**
	 * the index of the leftmost view
	 */
	private int mLeftViewIndex = -1;
	
	/**
	 * the index of the rightmost view
	 */
	private int mRightViewIndex = 0;
	
	/**
	 * current x value in coordinate 
	 */
	protected int mCurrentX;
	
	/**
	 * x value listview will scroll to 
	 */
	protected int mNextX;
	
	
	private int mMaxX = Integer.MAX_VALUE;
	
	/**
	 * offset between the left edge of this view and the left edge of first child view
	 */
	private int mDisplayOffset = 0;
	
	/**
	 * scroller object to record scroll action 
	 */
	protected Scroller mScroller;
	
	/**
	 * a Gesture object, provide onScroll and onFling handler
	 */
	private GestureDetector mGesture;
	
	/**
	 * view queue which is removed 
	 */
	private Queue<View> mRemovedViewQueue = new LinkedList<View>();
	
	/**
	 * listener to listen selected items is changed  
	 */
	private OnItemSelectedListener mOnItemSelected;
	
	/**
	 * listener to listen items is clicked 
	 */
	private OnItemClickListener mOnItemClicked;
	
	/**
	 * flags invalidate children view or its data had changed 
	 */
	private boolean mDataChanged = false;
	
//	/**
//	 * flags invalidate this view need to layout
//	 */
//	private boolean mNeedLayout = false;
	

	public HorizontalListView2(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}
	
	private void initView() {
		mLeftViewIndex = -1;
		mRightViewIndex = 0;
		mDisplayOffset = 0;
		mCurrentX = 0;
		mNextX = 0;
		mMaxX = Integer.MAX_VALUE;
		mScroller = new Scroller(getContext());
		mGesture = new GestureDetector(getContext(), mOnGesture);
//		setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
	}
	
	@Override
	public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener listener) {
		mOnItemSelected = listener;
	}
	
	@Override
	public void setOnItemClickListener(AdapterView.OnItemClickListener listener){
		mOnItemClicked = listener;
	}
	
	private DataSetObserver mDataObserver = new DataSetObserver() {

		@Override
		public void onChanged() {
			synchronized(HorizontalListView2.this){
				mDataChanged = true;
			}
//			invalidate();
			requestLayout();
		}

		@Override
		public void onInvalidated() {
			reset();
//			invalidate();
			requestLayout();
		}
		
	};

	@Override
	public ListAdapter getAdapter() {
		return mAdapter;
	}

	@Override
	public View getSelectedView() {
		//TODO: implement
		return null;
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		if(mAdapter != null) {
			mAdapter.unregisterDataSetObserver(mDataObserver);
		}
		mAdapter = adapter;
		mAdapter.registerDataSetObserver(mDataObserver);
		reset();
	}
	
	private void reset(){
		initView();
		removeAllViewsInLayout();
        requestLayout();
	}

	@Override
	public void setSelection(int position) {
		//TODO: implement
	}
	
	/**
	 * measure child and add it to children array 
	 * @param child this view operated 
	 * @param viewPos position where the view is added to children array 
	 */
	private void addAndMeasureChild(final View child, int viewPos) {
		LayoutParams params = null;
//		child.getLayoutParams();
		if(params == null) {
			params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		}

		addViewInLayout(child, viewPos, params, true);
		child.measure(MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.AT_MOST),
				MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.AT_MOST));
	}

	@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        ListView l;
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);

		if(mAdapter == null){
			return;
		}
		
		if(mDataChanged){
//		    System.out.println("the Data has changed");
			int oldCurrentX = mCurrentX;
			initView();
			removeAllViewsInLayout();
			mNextX = oldCurrentX;
			mDataChanged = false;
		}

		if(mScroller.computeScrollOffset()){
			int scrollx = mScroller.getCurrX();
			mNextX = scrollx;
		}
		
		if(mNextX < 0){
			mNextX = 0;
			mScroller.forceFinished(true);
		}
		if(mNextX > mMaxX) {
			mNextX = mMaxX;
			mScroller.forceFinished(true);
		}
		
		int dx = mCurrentX - mNextX;
		
		removeNonVisibleItems(dx);
		fillList(dx);
		positionItems(dx);
		
		mCurrentX = mNextX;
		
//		if(!mScroller.isFinished()){
//			post(new Runnable(){
//				@Override
//				public void run() {
//				    invalidate();
////					requestLayout();
//				}
//			});
//			
//		}
	}
    
	
    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        System.out.println("onDraw method");
        computeScrolling();
        super.onDraw(canvas);
    }

    private void fillList(final int dx) {
		int edge = 0;
		View child = getChildAt(getChildCount()-1);
		if(child != null) {
			edge = child.getRight();
		}
		fillListRight(edge, dx);
		
		edge = 0;
		child = getChildAt(0);
		if(child != null) {
			edge = child.getLeft();
		}
		fillListLeft(edge, dx);
//		invalidate();
//        System.out.println(mRemovedViewQueue.size());
	}
	
	/**
	 * fill right children children array with the view from adapter view 
	 * @param rightEdge 
	 * @param dx
	 */
	private void fillListRight(int rightEdge, final int dx) {
		while(rightEdge + dx < getWidth() && mRightViewIndex < mAdapter.getCount()) {
			
			View child = mAdapter.getView(mRightViewIndex, mRemovedViewQueue.poll(), this);
			addAndMeasureChild(child, -1);
			rightEdge += child.getMeasuredWidth();
			
			if(mRightViewIndex == mAdapter.getCount()-1){
				mMaxX = mCurrentX + rightEdge - getWidth();
			}
			mRightViewIndex++;
		}
		
	}
	
	/**
	 * fill left children children array with the view from adapter view 
	 * @param leftEdge 
	 * @param dx
	 */
	private void fillListLeft(int leftEdge, final int dx) {
		while(leftEdge + dx > 0 && mLeftViewIndex >= 0) {
			View child = mAdapter.getView(mLeftViewIndex, mRemovedViewQueue.poll(), this);
			addAndMeasureChild(child, 0);
			leftEdge -= child.getMeasuredWidth();
			mLeftViewIndex--;
			mDisplayOffset -= child.getMeasuredWidth();
		}
	}
	
	/**
	 * remove the unviable view to user during scrolling 
	 * @param dx distance scroll by 
	 */
	private void removeNonVisibleItems(final int dx) {
		View child = getChildAt(0);
		while(child != null && child.getRight() + dx <= 0) {
			mDisplayOffset += child.getMeasuredWidth();
			mRemovedViewQueue.offer(child);
			removeViewInLayout(child);
			mLeftViewIndex++;
			child = getChildAt(0);
			
		}
		
		child = getChildAt(getChildCount()-1);
		while(child != null && child.getLeft() + dx >= getWidth()) {
			mRemovedViewQueue.offer(child);
			removeViewInLayout(child);
			mRightViewIndex--;
			child = getChildAt(getChildCount()-1);
		}
	}
	
	/**
	 * layout all children view
	 * @param dx 
	 */
	private void positionItems(final int dx) {
		if(getChildCount() > 0){
			mDisplayOffset += dx;
			int left = mDisplayOffset;
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                int childWidth = child.getMeasuredWidth();
                child.layout(left, 0, left + childWidth, child
                        .getMeasuredHeight());
                left += childWidth + 1;
            }
		}
	}
	
	/**
	 * 计算是否需要向children array 中添加或减少视图
	 */
	private void computeScrolling(){
        if(mScroller.computeScrollOffset()){
            int scrollx = mScroller.getCurrX();
            mNextX = scrollx;
        }
        
        if(mNextX < 0){
            mNextX = 0;
            mScroller.forceFinished(true);
        }
        if(mNextX > mMaxX) {
            mNextX = mMaxX;
            mScroller.forceFinished(true);
        }
        
        int dx = mCurrentX - mNextX;
        
        removeNonVisibleItems(dx);
        fillList(dx);
        positionItems(dx);
        
        mCurrentX = mNextX;
	}
	
	public void scrollTo(int x) {
		mScroller.startScroll(mNextX, 0, x - mNextX, 0);
		
//		requestLayout();
		invalidate();
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		boolean handled = mGesture.onTouchEvent(ev);
		return handled;
	}
	
	protected boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
        mScroller.fling(mNextX, 0, (int) -velocityX, 0, 0, mMaxX, 0, 0);
        System.out.println("will be filing");
		return true;
	}
	
	protected boolean onDown(MotionEvent e) {
		mScroller.forceFinished(true);
		return true;
	}
	
	private OnGestureListener mOnGesture = new GestureDetector.SimpleOnGestureListener() {

		@Override
		public boolean onDown(MotionEvent e) {
			return HorizontalListView2.this.onDown(e);
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			return HorizontalListView2.this.onFling(e1, e2, velocityX, velocityY);
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
		    
            mNextX += (int) distanceX;
            computeScrolling();
			return true;
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
            Rect viewRect = new Rect();
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                int left = child.getLeft();
                int right = child.getRight();
                int top = child.getTop();
                int bottom = child.getBottom();
                viewRect.set(left, top, right, bottom);
                if (viewRect.contains((int) e.getX(), (int) e.getY())) {
                    if (mOnItemClicked != null) {
                        mOnItemClicked.onItemClick(HorizontalListView2.this,
                                child, mLeftViewIndex + 1 + i, mAdapter
                                        .getItemId(mLeftViewIndex + 1 + i));
                    }
                    if (mOnItemSelected != null) {
                        mOnItemSelected.onItemSelected(
                                HorizontalListView2.this, child, mLeftViewIndex
                                        + 1 + i, mAdapter
                                        .getItemId(mLeftViewIndex + 1 + i));
                    }
                    break;
                }

            }
            return true;
		}
		
		
		
	};

	

}
