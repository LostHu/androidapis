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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.GestureDetector.OnGestureListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Scroller;
import android.widget.AbsListView.RecyclerListener;

import com.lity.android.apis.App;

public class HorizontalListView extends AdapterView<Adapter> {
	
	/*
	 * 20120307实现Fling功能
	 * 20120312添加滚动条(BUG:没有背景时不显示滚动条).
	 * 20120313实现此View布局和Items的布局(Bug:Items布局未实现)
	 * 20120313实现分隔线
	 */
	
	private static final boolean DEBUG = true;
	private static final String TAG = "HorizontalListView";

	public boolean mAlwaysOverrideTouch = true;
	protected Adapter mAdapter;
	
	public static final int TOUCH_MODE_OTHER = 0;
	public static final int TOUCH_MODE_FLING = 1;
	private int mTouchMode;
	
	int mItemCount;
	
	/**
	 * the index of the leftmost view
	 */
	private int mLeftViewIndex = -1;
	
	/**
	 * the index of the rightmost view
	 */
	private int mRightViewIndex = 0;
	
	
	/*
	 * 第一个子视图的position
	 */
	private int mFirstPosition = -1;
	
	/*
	 * 最后一个子视图的position 
	 */
	private int mLastPosition = -1;
	
    Drawable mDivider;
    int mDividerWidth;
    Paint mDividerPaint;
	Rect mTempRect = new Rect();
    {
//    	Canvas c;
//    	mDivider.draw(canvas)
    	View child;
    }
	
	
	private boolean mSmoothScrollbarEnabled;
	
	/*
	 * view的垃圾回收器 
	 */
	private RecycleBin mRecycler = new RecycleBin();
	
	
	
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
	
	private int mDispalyOffsetRight = 0;
	
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
	

	public HorizontalListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}
	
	public HorizontalListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	public HorizontalListView(Context context) {
		super(context);
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
			synchronized(HorizontalListView.this){
				mDataChanged = true;
			}
//			invalidate();
			requestLayout();
		}

		@Override
		public void onInvalidated() {
			resetList();
//			invalidate();
			requestLayout();
		}
		
	};

	@Override
	public View getSelectedView() {
		return null;
	}

	@Override
	public void setAdapter(Adapter adapter) {
		// TODO 需要完善
		if(mAdapter != null) {
			mAdapter.unregisterDataSetObserver(mDataObserver);
		}
		mAdapter = adapter;
		mAdapter.registerDataSetObserver(mDataObserver);
		mRecycler.clear();
		mRecycler.setViewTypeCount(mAdapter.getViewTypeCount());
		resetList();
	}
	
    /**
     * Sets the drawable that will be drawn between each item in the list. If the drawable does
     * not have an intrinsic height, you should also call {@link #setDividerHeight(int)}
     *
     * @param divider The drawable to use.
     */
    public void setDivider(Drawable divider) {
        if (divider != null) {
            mDividerWidth = divider.getIntrinsicWidth();
//            mClipDivider = divider instanceof ColorDrawable;
        } else {
            mDividerWidth = 0;
//            mClipDivider = false;
        }
        mDivider = divider;
//        mDividerIsOpaque = divider == null || divider.getOpacity() == PixelFormat.OPAQUE;
        requestLayoutIfNecessary();
    }
    
    void requestLayoutIfNecessary() {
        if (getChildCount() > 0) {
            resetList();
            requestLayout();
            invalidate();
        }
    }
    
    @Override
    protected void dispatchDraw(Canvas canvas) {
        // Draw the dividers
        final int dividerHeight = mDividerWidth;

        if (dividerHeight > 0 && mDivider != null) {
            // Only modify the top and bottom in the loop, we set the left and right here
            final Rect bounds = mTempRect;
            bounds.left = getPaddingLeft();
            bounds.right = getRight() - getLeft() - getPaddingRight();

            final int count = getChildCount();
//            final int headerCount = mHeaderViewInfos.size();
//            final int footerLimit = mItemCount - mFooterViewInfos.size() - 1;
//            final boolean headerDividers = mHeaderDividersEnabled;
//            final boolean footerDividers = mFooterDividersEnabled;
            final int first = mFirstPosition;
//            final boolean areAllItemsSelectable = mAreAllItemsSelectable;
            final ListAdapter adapter = (ListAdapter)mAdapter;
            // If the list is opaque *and* the background is not, we want to
            // fill a rect where the dividers would be for non-selectable items
            // If the list is opaque and the background is also opaque, we don't
            // need to draw anything since the background will do it for us
            final boolean fillForMissingDividers = isOpaque() && !super.isOpaque();

            if (fillForMissingDividers && mDividerPaint == null) {
                mDividerPaint = new Paint();
                mDividerPaint.setColor(Color.RED);
            }
            final Paint paint = mDividerPaint;

            if (true) {
                int left;
                int listRight = getRight() - getLeft() - 0;

                for (int i = 0; i < count; i++) {
                        View child = getChildAt(i);
                        left = child.getRight();
                        // Don't draw dividers next to items that are not enabled
                        if (left < listRight) {
                            if ((true ||
                                    (adapter.isEnabled(first + i) && (i == count - 1 ||
                                            adapter.isEnabled(first + i + 1))))) {
                                bounds.left = left;
                                bounds.right = left + dividerHeight;
                                drawDivider(canvas, bounds, i);
                            } else if (fillForMissingDividers) {
                                bounds.left = left;
                                bounds.right = left + dividerHeight;
                                canvas.drawRect(bounds, paint);
                            }
                        }
                }
            } 
//            else {
//                int top;
//                int listTop = mListPadding.top;
//
//                for (int i = 0; i < count; i++) {
//                    if ((headerDividers || first + i >= headerCount) &&
//                            (footerDividers || first + i < footerLimit)) {
//                        View child = getChildAt(i);
//                        top = child.getTop();
//                        // Don't draw dividers next to items that are not enabled
//                        if (top > listTop) {
//                            if ((areAllItemsSelectable ||
//                                    (adapter.isEnabled(first + i) && (i == count - 1 ||
//                                            adapter.isEnabled(first + i + 1))))) {
//                                bounds.top = top - dividerHeight;
//                                bounds.bottom = top;
//                                // Give the method the child ABOVE the divider, so we
//                                // subtract one from our child
//                                // position. Give -1 when there is no child above the
//                                // divider.
//                                drawDivider(canvas, bounds, i - 1);
//                            } else if (fillForMissingDividers) {
//                                bounds.top = top - dividerHeight;
//                                bounds.bottom = top;
//                                canvas.drawRect(bounds, paint);
//                            }
//                        }
//                    }
//                }
//            }
        }

        // Draw the indicators (these should be drawn above the dividers) and children

    	super.dispatchDraw(canvas);
    }
    
    /**
     * Draws a divider for the given child in the given bounds.
     *
     * @param canvas The canvas to draw to.
     * @param bounds The bounds of the divider.
     * @param childIndex The index of child (of the View) above the divider.
     *            This will be -1 if there is no child above the divider to be
     *            drawn.
     */
    void drawDivider(Canvas canvas, Rect bounds, int childIndex) {
        // This widget draws the same divider for all children
        final Drawable divider = mDivider;
        final boolean clipDivider = true;

        if (!clipDivider) {
            divider.setBounds(bounds);
        } else {
            canvas.save();
            canvas.clipRect(bounds);
        }

        divider.draw(canvas);

        if (clipDivider) {
            canvas.restore();
        }
    }
    
	
    @Override
    public Adapter getAdapter() {
        return mAdapter;
    }
    
	private void resetList(){
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
        
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        
        if (App.DEBUG) {
            Log.v(TAG, "MeasureSpec.AT_MOST:" + MeasureSpec.AT_MOST);
            Log.v(TAG, "MeasureSpec.UNSPECIFIED:" + MeasureSpec.UNSPECIFIED);
            Log.v(TAG, "MeasureSpec.EXACTLY:" + MeasureSpec.EXACTLY);
            Log.v(TAG, "widthMode:" + widthMode + ", heightMode:" + heightMode);
            Log.v(TAG, "widthSize:" + widthSize + ", heightSize:" + heightSize);
        }
        
        int childWidth = 0;
        int childHeight = 0;
        
        mItemCount = mAdapter != null ? mAdapter.getCount() : 0;
        if (mItemCount > 0 && (MeasureSpec.EXACTLY != widthMode || 
                MeasureSpec.EXACTLY != heightMode)) {
            final View child = obtainView(0);
            measureScrapChild(child, 0, heightMeasureSpec);
            
            childWidth = child.getMeasuredWidth();
            childHeight = child.getMeasuredHeight();
            
            Log.v(TAG, "childWidth:" + childWidth + ", childHeight:" + childHeight);
            
            if (recycleOnMeasure()) {
                mRecycler.addScrapView(child);
            }
        }
        
        if (MeasureSpec.UNSPECIFIED == heightMode) {
            heightSize = childHeight;
        }
        
        if (MeasureSpec.UNSPECIFIED == widthMode) {
            widthSize = childHeight;
        }
        
        if (MeasureSpec.AT_MOST == widthMode) {
			widthSize = Math.min(widthSize, childWidth * mItemCount);
		}
        
        if (MeasureSpec.AT_MOST == heightMode) {
			heightSize = Math.min(heightSize, childHeight);
		}
        
        setMeasuredDimension(widthSize, heightSize);
        Log.v(TAG, "onMeasure widthSize:" + widthSize + ", heightSize" + heightSize);
    }
	
	// TODO position 以后会用
	private void measureScrapChild(View child, int position, int heightMeasureSpec) {
		// TODO 未处理直接从xml文件读取的View
		Log.v(TAG, "measureScrapChild, child:" + child);
	    LayoutParams p = (LayoutParams)child.getLayoutParams();
	    if (null == p) {
            p = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT);
            child.setLayoutParams(p);
        }
	    p.viewType = mAdapter.getItemViewType(position);
	    
	    Log.v(TAG, "measureScrapChild, p.width:" + p.width + ", p.height:" + p.height);
	    
	    int childHeightSpec = ViewGroup.getChildMeasureSpec(heightMeasureSpec, 0, p.height);
	    int childWidthSpec;
	    
	    if (p.width > 0) {
            childWidthSpec = MeasureSpec.makeMeasureSpec(p.width, MeasureSpec.EXACTLY);
        }
	    else {
	        childWidthSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
	    child.measure(childWidthSpec, childHeightSpec);
	}
	
    /**
     * @return True to recycle the views used to measure this ListView in
     *         UNSPECIFIED/AT_MOST modes, false otherwise.
     */
    protected boolean recycleOnMeasure() {
        return true;
    }
	
	/**
	 * obtain view form adapter
	 * TODO 完善
	 * @param position
	 * @return
	 */
	View obtainView(int position) {
	    return mAdapter.getView(position, null, null);
	}

    @Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		layoutChildren();
		
//		if(mAdapter == null){
//			return;
//		}
//		
//		if(mDataChanged){
////		    System.out.println("the Data has changed");
//			int oldCurrentX = mCurrentX;
//			initView();
//			removeAllViewsInLayout();
//			mNextX = oldCurrentX;
//			mDataChanged = false;
//		}
//
//		if(mScroller.computeScrollOffset()){
//			int scrollx = mScroller.getCurrX();
//			mNextX = scrollx;
//		}
//		
//		if(mNextX < 0){
//			mNextX = 0;
//			mScroller.forceFinished(true);
//		}
//		if(mNextX > mMaxX) {
//			mNextX = mMaxX;
//			mScroller.forceFinished(true);
//		}
//		
//		int dx = mCurrentX - mNextX;
//		
//		removeNonVisibleItems(dx);
//		fillList(dx);
//		positionItems(dx);
//		
//		mCurrentX = mNextX;
		
	}
    
    /*
     * 布局所有child
     */
    void layoutChildren() {
    	int position = 0;
    	removeAllViewsInLayout();
    	View child = null;
    	do {
        	child = obtainView(position);
        	addAndMeasureChild(child, position);
        	layoutNewChild(child, position);
        	position++;
		} while (child.getRight() < getRight());
    	mFirstPosition = 0;
    	mLastPosition = getChildCount() - 1;
    }
	
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    boolean canMoveBy(int dx) {
		final int willScrollX = getScrollX() + dx;
		final int firstPosition = mFirstPosition;
		final int lastPosition = mLastPosition;
		
		View child = getChildAt(getChildCount() - 1);
		if (null != child && dx > 0) {
			// 最后一个未显示出来
			if (lastPosition < mItemCount - 1 && child.getRight() + (mItemCount - lastPosition - 1) * child.getWidth() > willScrollX + getWidth()) {
				return true;
			}
			// 最后一个已经显示出来
			else if (lastPosition == mItemCount - 1 && child.getRight() > willScrollX + getWidth()) {
				return true;
			}
		}
		child = getChildAt(0);
		if (null != child && dx < 0) {
			// 第0个未显示出来
			if (firstPosition > 0 && child.getLeft() - (firstPosition) * child.getWidth() < willScrollX) {
				return true;
			}
			// 第0个显示出来
			else if (firstPosition == 0 && child.getLeft() < willScrollX) {
				return true;
			}
		}
		
		return false;
	}
	
	void layoutNewChild(View child, int index) {
		int left = 0,top = 0,right = 0,bottom = 0;
		View v = null;
		if (index > 1 && null != (v = getChildAt(index - 1))) {
			left = v.getRight();
		}
		child.layout(left, 0, left + child.getMeasuredWidth(), 0 + child.getMeasuredHeight());
	}
	
	
	void measureChild(View child) {
//	    ViewGroup.LayoutParams p = child.getLayoutParams();
//	    if (null == p) {
//            p = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT);
//            child.setLayoutParams(p);
//        }
//	    
//	    int childHeightSpec = ViewGroup.getChildMeasureSpec(, 0, p.height);
//	    int childWidthSpec;
//	    
//	    if (p.width > 0) {
//            childWidthSpec = MeasureSpec.makeMeasureSpec(p.width, MeasureSpec.EXACTLY);
//        }
//	    else {
//	        childWidthSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
//        }
//	    child.measure(childWidthSpec, childHeightSpec);
	}
	
	/**
	 * layout child while scrolling.
	 * @param child 将添加的view
	 * @param toRight 布局到右边还是左边.
	 */
	void layoutNewChild(View child, boolean toRight) {
		int left = 0,top = 0,right = 0,bottom = child.getMeasuredHeight();
		View v = null;
		if (toRight) {
			v = getChildAt(getChildCount() - 1);
			left = v.getRight(); right = left + child.getMeasuredWidth();
		}else {
			v = getChildAt(0);
			right = v.getLeft(); left = right - child.getMeasuredWidth();
		}
		child.layout(left, top, right, bottom);
	}
	
	/**
	 * 移动delta, 移动的核心
	 * 注：手指从左向右移动,delta为正;从右向左移动,delta为负
	 * 
	 * @param det 
	 */
	void moveBy(int deltaX) {
		deltaX = - deltaX;
		Log.v(TAG, "deltaX：" + deltaX);
		if (!canMoveBy(deltaX)) {
			return ;
		}
		Log.v(TAG, "can move");
		final int willScrollX = getScrollX() + deltaX;
		
		View child = null;
		// 向右移动
		if (deltaX > 0) {
			// 移除左边不显示的child View.
			child = getChildAt(0);
			while (null != child && child.getRight() < willScrollX) {
				mFirstPosition++;
				removeViewInLayout(child);
				child = getChildAt(0);
			}
			// 添加右边将显示出来的child view.
			child = getChildAt(getChildCount() - 1);
			// 右边有空隙,添加下一个child view.
//			int right = 0;
			while (null != child && child.getRight() < willScrollX + getWidth()) {
				child = obtainView(++mLastPosition);
				int heightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.UNSPECIFIED, 0);
				measureScrapChild(child, mLastPosition, heightMeasureSpec);
				layoutNewChild(child, true);
				addViewInLayout(child, getChildCount(), new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT));
			}
			
		} else if (deltaX < 0) { // 向左移动(画布)
			// 移除右边不显示的child view.
			child = getChildAt(getChildCount() - 1);
			while (null != child && child.getLeft() > willScrollX + getWidth()) {
				mLastPosition--;
				removeViewInLayout(child);
				child = getChildAt(getChildCount() - 1);
			}
			// 左边有空隙,添加child view.
			child = getChildAt(0);
			while (null != child && child.getLeft() > willScrollX) {
				View addChild = obtainView(--mFirstPosition);
				int heightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.UNSPECIFIED, 0);
				measureScrapChild(addChild, mFirstPosition, heightMeasureSpec);
				layoutNewChild(addChild, false);
				addViewInLayout(addChild, 0, new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT));
				child = getChildAt(0);
			}
			
		}
		
		
		
        scrollBy(deltaX, 0);
//        View child = getChildAt(getChildCount() - 1);
//        Log.v(TAG, "HorizontalListView, getScrollX():" + getScrollX());
//        Log.v(TAG, "last child, left:" + child.getLeft() + ", right" + child.getRight());
        
//		invalidate();
	}
	
    @Override
    protected int computeHorizontalScrollExtent() {
        final int count = getChildCount();
        if (count > 0) {
            if (mSmoothScrollbarEnabled) {
                int extent = count * 100;

                View view = getChildAt(0);
                final int top = view.getTop();
                int height = view.getHeight();
                if (height > 0) {
                    extent += (top * 100) / height;
                }

                view = getChildAt(count - 1);
                final int bottom = view.getBottom();
                height = view.getHeight();
                if (height > 0) {
                    extent -= ((bottom - getHeight()) * 100) / height;
                }

                return extent;
            } else {
                return 1;
            }
        }
        return 0;
    }

    @Override
    protected int computeHorizontalScrollOffset() {
        final int firstPosition = mFirstPosition;
        final int childCount = getChildCount();
        if (firstPosition >= 0 && childCount > 0) {
            if (mSmoothScrollbarEnabled) {
                final View view = getChildAt(0);
                final int top = view.getTop();
                int height = view.getHeight();
                if (height > 0) {
                    return Math.max(firstPosition * 100 - (top * 100) / height, 0);
                }
            } else {
                int index;
                final int count = mItemCount;
                if (firstPosition == 0) {
                    index = 0;
                } else if (firstPosition + childCount == count) {
                    index = count;
                } else {
                    index = firstPosition + childCount / 2;
                }
                return (int) (firstPosition + childCount * (index / (float) count));
            }
        }
        return 0;
    }

    @Override
    protected int computeHorizontalScrollRange() {
        return mSmoothScrollbarEnabled ? Math.max(mItemCount * 100, 0) : mItemCount;
    }
	
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		boolean handled = mGesture.onTouchEvent(ev);
		return handled;
	}
	
	private OnGestureListener mOnGesture = new GestureDetector.SimpleOnGestureListener() {

		@Override
		public boolean onDown(MotionEvent e) {
			mScroller.forceFinished(true);
			if (TOUCH_MODE_FLING == mTouchMode) {
				mTouchMode = TOUCH_MODE_OTHER;
			}
			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			int velocity = velocityX > 0 ? (int)(velocityX + 0.5) : (int)(velocityX - 0.5);
	        mFlingRunnable.start(-velocity);
			return true;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			int delta = distanceX > 0 ? (int)(distanceX + 0.5) : (int)(distanceX - 0.5);
            moveBy(-delta);
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
                        mOnItemClicked.onItemClick(HorizontalListView.this,
                                child, mLeftViewIndex + 1 + i, mAdapter
                                        .getItemId(mLeftViewIndex + 1 + i));
                    }
                    if (mOnItemSelected != null) {
                        mOnItemSelected.onItemSelected(
                                HorizontalListView.this, child, mLeftViewIndex
                                        + 1 + i, mAdapter
                                        .getItemId(mLeftViewIndex + 1 + i));
                    }
                    break;
                }

            }
            return true;
		}
	};

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }
    

    private FlingRunnable mFlingRunnable = new FlingRunnable();
    /**
     * Responsible for fling behavior. Use {@link #start(int)} to
     * initiate a fling. Each frame of the fling is handled in {@link #run()}.
     * A FlingRunnable will keep re-posting itself until the fling is done.
     *
     */
    private class FlingRunnable implements Runnable {
        /**
         * Tracks the decay of a fling scroll
         */
        private Scroller mScroller;

        /**
         * X value reported by mScroller on the previous fling
         */
        private int mLastFlingX;

        public FlingRunnable() {
            mScroller = new Scroller(getContext());
        }

        public void start(int initialVelocity) {
        	mTouchMode = TOUCH_MODE_FLING;
            int initialX = initialVelocity < 0 ? Integer.MAX_VALUE : 0;
            mLastFlingX = initialX;
            mScroller.fling(initialX, 0, initialVelocity, 0, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
            post(this);
        }

        private void endFling() {
        	mTouchMode = TOUCH_MODE_OTHER;
        }

        public void run() {
            if (mTouchMode != TOUCH_MODE_FLING) {
                return;
            }

            if (mItemCount == 0 || getChildCount() == 0) {
                endFling();
                return;
            }

            final Scroller scroller = mScroller;
            boolean more = scroller.computeScrollOffset();
            final int x = scroller.getCurrX();

//             Flip sign to convert finger direction to list items direction
//             (e.g. finger moving down means list is moving towards the top)
            int delta = mLastFlingX - x;
            moveBy(delta);

            if (more) {
                invalidate();
                mLastFlingX = x;
                post(this);
            } else {
                endFling();
            }
        }
    }

    
    /**
     * The RecycleBin facilitates reuse of views across layouts. The RecycleBin has two levels of
     * storage: ActiveViews and ScrapViews. ActiveViews are those views which were onscreen at the
     * start of a layout. By construction, they are displaying current information. At the end of
     * layout, all views in ActiveViews are demoted to ScrapViews. ScrapViews are old views that
     * could potentially be used by the adapter to avoid allocating views unnecessarily.
     *
     * @see android.widget.AbsListView#setRecyclerListener(android.widget.AbsListView.RecyclerListener)
     * @see android.widget.AbsListView.RecyclerListener
     */
    class RecycleBin {
        private RecyclerListener mRecyclerListener;

        /**
         * The position of the first view stored in mActiveViews.
         */
        private int mFirstActivePosition;

        /**
         * Views that were on screen at the start of layout. This array is populated at the start of
         * layout, and at the end of layout all view in mActiveViews are moved to mScrapViews.
         * Views in mActiveViews represent a contiguous range of Views, with position of the first
         * view store in mFirstActivePosition.
         */
        private View[] mActiveViews = new View[0];

        /**
         * Unsorted views that can be used by the adapter as a convert view.
         */
        private ArrayList<View>[] mScrapViews;

        private int mViewTypeCount;

        private ArrayList<View> mCurrentScrap;

        public void setViewTypeCount(int viewTypeCount) {
            if (viewTypeCount < 1) {
                throw new IllegalArgumentException("Can't have a viewTypeCount < 1");
            }
            //noinspection unchecked
            ArrayList<View>[] scrapViews = new ArrayList[viewTypeCount];
            for (int i = 0; i < viewTypeCount; i++) {
                scrapViews[i] = new ArrayList<View>();
            }
            mViewTypeCount = viewTypeCount;
            mCurrentScrap = scrapViews[0];
            mScrapViews = scrapViews;
        }

        public boolean shouldRecycleViewType(int viewType) {
            return viewType >= 0;
        }

        /**
         * Clears the scrap heap.
         */
        void clear() {
            if (mViewTypeCount == 1) {
                final ArrayList<View> scrap = mCurrentScrap;
                final int scrapCount = scrap.size();
                for (int i = 0; i < scrapCount; i++) {
                    removeDetachedView(scrap.remove(scrapCount - 1 - i), false);
                }
            } else {
                final int typeCount = mViewTypeCount;
                for (int i = 0; i < typeCount; i++) {
                    final ArrayList<View> scrap = mScrapViews[i];
                    final int scrapCount = scrap.size();
                    for (int j = 0; j < scrapCount; j++) {
                        removeDetachedView(scrap.remove(scrapCount - 1 - j), false);
                    }
                }
            }
        }

        /**
         * Fill ActiveViews with all of the children of the AbsListView.
         *
         * @param childCount The minimum number of views mActiveViews should hold
         * @param firstActivePosition The position of the first view that will be stored in
         *        mActiveViews
         */
        void fillActiveViews(int childCount, int firstActivePosition) {
            if (mActiveViews.length < childCount) {
                mActiveViews = new View[childCount];
            }
            mFirstActivePosition = firstActivePosition;

            final View[] activeViews = mActiveViews;
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                HorizontalListView.LayoutParams lp = (HorizontalListView.LayoutParams)child.getLayoutParams();
                // Don't put header or footer views into the scrap heap
                if (lp != null && lp.viewType != AdapterView.ITEM_VIEW_TYPE_HEADER_OR_FOOTER) {
                    // Note:  We do place AdapterView.ITEM_VIEW_TYPE_IGNORE in active views.
                    //        However, we will NOT place them into scrap views.
                    activeViews[i] = child;
                }
            }
        }

        /**
         * Get the view corresponding to the specified position. The view will be removed from
         * mActiveViews if it is found.
         *
         * @param position The position to look up in mActiveViews
         * @return The view if it is found, null otherwise
         */
        View getActiveView(int position) {
            int index = position - mFirstActivePosition;
            final View[] activeViews = mActiveViews;
            if (index >=0 && index < activeViews.length) {
                final View match = activeViews[index];
                activeViews[index] = null;
                return match;
            }
            return null;
        }

        /**
         * @return A view from the ScrapViews collection. These are unordered.
         */
        View getScrapView(int position) {
            ArrayList<View> scrapViews;
            if (mViewTypeCount == 1) {
                scrapViews = mCurrentScrap;
                int size = scrapViews.size();
                if (size > 0) {
                    return scrapViews.remove(size - 1);
                } else {
                    return null;
                }
            } else {
                int whichScrap = mAdapter.getItemViewType(position);
                if (whichScrap >= 0 && whichScrap < mScrapViews.length) {
                    scrapViews = mScrapViews[whichScrap];
                    int size = scrapViews.size();
                    if (size > 0) {
                        return scrapViews.remove(size - 1);
                    }
                }
            }
            return null;
        }

        /**
         * Put a view into the ScapViews list. These views are unordered.
         *
         * @param scrap The view to add
         */
        void addScrapView(View scrap) {
        	HorizontalListView.LayoutParams lp = (HorizontalListView.LayoutParams) scrap.getLayoutParams();
            if (lp == null) {
                return;
            }

            // Don't put header or footer views or views that should be ignored
            // into the scrap heap
            int viewType = lp.viewType;
            if (!shouldRecycleViewType(viewType)) {
                return;
            }

            if (mViewTypeCount == 1) {
                mCurrentScrap.add(scrap);
            } else {
                mScrapViews[viewType].add(scrap);
            }

            if (mRecyclerListener != null) {
                mRecyclerListener.onMovedToScrapHeap(scrap);
            }
        }

        /**
         * Move all views remaining in mActiveViews to mScrapViews.
         */
        void scrapActiveViews() {
            final View[] activeViews = mActiveViews;
            final boolean hasListener = mRecyclerListener != null;
            final boolean multipleScraps = mViewTypeCount > 1;

            ArrayList<View> scrapViews = mCurrentScrap;
            final int count = activeViews.length;
            for (int i = 0; i < count; ++i) {
                final View victim = activeViews[i];
                if (victim != null) {
                    int whichScrap = ((HorizontalListView.LayoutParams)
                            victim.getLayoutParams()).viewType;

                    activeViews[i] = null;

                    if (whichScrap == AdapterView.ITEM_VIEW_TYPE_IGNORE) {
                        // Do not move views that should be ignored
                        continue;
                    }

                    if (multipleScraps) {
                        scrapViews = mScrapViews[whichScrap];
                    }
                    scrapViews.add(victim);

                    if (hasListener) {
                        mRecyclerListener.onMovedToScrapHeap(victim);
                    }

                    if (ViewDebug.TRACE_RECYCLER) {
                        ViewDebug.trace(victim,
                                ViewDebug.RecyclerTraceType.MOVE_FROM_ACTIVE_TO_SCRAP_HEAP,
                                mFirstActivePosition + i, -1);
                    }
                }
            }

            pruneScrapViews();
        }

        /**
         * Makes sure that the size of mScrapViews does not exceed the size of mActiveViews.
         * (This can happen if an adapter does not recycle its views).
         */
        private void pruneScrapViews() {
            final int maxViews = mActiveViews.length;
            final int viewTypeCount = mViewTypeCount;
            final ArrayList<View>[] scrapViews = mScrapViews;
            for (int i = 0; i < viewTypeCount; ++i) {
                final ArrayList<View> scrapPile = scrapViews[i];
                int size = scrapPile.size();
                final int extras = size - maxViews;
                size--;
                for (int j = 0; j < extras; j++) {
                    removeDetachedView(scrapPile.remove(size--), false);
                }
            }
        }

        /**
         * Puts all views in the scrap heap into the supplied list.
         */
        void reclaimScrapViews(List<View> views) {
            if (mViewTypeCount == 1) {
                views.addAll(mCurrentScrap);
            } else {
                final int viewTypeCount = mViewTypeCount;
                final ArrayList<View>[] scrapViews = mScrapViews;
                for (int i = 0; i < viewTypeCount; ++i) {
                    final ArrayList<View> scrapPile = scrapViews[i];
                    views.addAll(scrapPile);
                }
            }
        }
    }
    
    /**
     * AbsListView extends LayoutParams to provide a place to hold the view type.
     */
    public static class LayoutParams extends ViewGroup.LayoutParams {
        /**
         * View type for this view, as returned by
         * {@link android.widget.Adapter#getItemViewType(int) }
         */
        int viewType;

        /**
         * When this boolean is set, the view has been added to the AbsListView
         * at least once. It is used to know whether headers/footers have already
         * been added to the list view and whether they should be treated as
         * recycled views or not.
         */
        boolean recycledHeaderFooter;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int w, int h) {
            super(w, h);
        }

        public LayoutParams(int w, int h, int viewType) {
            super(w, h);
            this.viewType = viewType;
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }
}

