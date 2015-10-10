package com.lity.android.apis.map;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Analyzes a series of MotionEvent and detects gestures. Right now only very simple gestures are detected: scroll, tap, and double-tap. 
 * @author Lity
 *
 */
public class TrackballGestureDetector {
	
	/*
	 * 单击、双击、滚动是互斥事件,一次分析后以上三个事件最多只会产生一种事件
	 * 长按是回调处理机制,所以可以与上述事件同时发生(暂时先不考虑)
	 */
	
	/*
	 * 20120221,全面修改此类,
	 * 事件以回调的方式传出,原因是单击和双击不通过回调方式判断不出来
	 * 之前事件是在收到事件ACTION_UP后进行分析处理单击和双击,现在改为ACTION_DOWN后来处理
	 */
	
	/*
	 * 从GestureDetector类中分析到事件的定义可能如下：
	 * Tap, 手指触到屏幕downEvent, 过DoubleTimeOut时,如果手指已离开屏幕, 为一个tap
	 * DoubleTap, 在DoubleTimeOut内(可简单标记tap是否已处理),再产生一个upEvent并记录下来,再次产生downEvent2,downEvent2的时间与upEvent不相差doubleTimeOut。
	 * 应该先处理doubleTap,是的话不处理tap,从以上的过程中可以看出,tap和doubleTap都是在第一次down之后的TimeOut时间后发生
	 * LongPress, 手指触到屏幕downEvent, 过 TAP_TIMEOUT + LONGPRESS_TIMEOUT时间后,定为长按,如果中间有其它事件发生,则取消LongPress
	 * Scroll, 未参考GestureDetector类(原因是trackball的scroll只与ACTION_MOVE有关)
	 * 
	 */
	
	
    /**
     * Defines the duration in milliseconds we will wait to see if a touch event 
     * is a tap or a scroll. If the user does not move within this interval, it is
     * considered to be a tap. 
     */
	private static final long TAP_TIMEOUT = ViewConfiguration.getTapTimeout();
	
    /**
     * Defines the duration in milliseconds between the first tap's up event and
     * the second tap's down event for an interaction to be considered a
     * double-tap.
     */
	private static final long DOUBLE_TAP_TIMEOUT = ViewConfiguration.getDoubleTapTimeout();
	
    /**
     * Defines the duration in milliseconds before a press turns into
     * a long press
     */
	private static final long LONG_PRESS_TIMEOUT = ViewConfiguration.getLongPressTimeout();
	
	
	private static final float SCROLL_RANGE = 1.0F;
	private final static int TAP = 0;
	private final static int DOUBLE_TAP = 1;
	private final static int SCROLL = 2;
	private final static int LONG_PRESS = 3;
	
	
	private static final String TAG = "DEBUG";

    /**
     * True when the user is still touching for the second tap (down, move, and
     * up events). Can only be true if there is a double tap listener attached.
     */
    private boolean mIsDoubleTapping;
	private boolean mIsSingleTaping;
	private boolean mIsScrolling;
	
    private boolean mStillDown;
    private boolean mInLongPress;
    
	private MotionEvent mPreviousDownEvent;
	private MotionEvent mPreviousUpEvent;
	private MotionEvent mCurrentDownEvent;
	
	private float mScrollX;
	private float mScrollY;
	
	private Handler mHandler;
	private Runnable mLongPressRunnable;
	private OnTrackballGestureListener mTrackballListener = new OnTrackballGestureListener() {
		
		@Override
		public void onTap(TrackballGestureDetector detector) {
			Log.v(TAG, "单击");
		}
		
		@Override
		public void onScroll(TrackballGestureDetector detector) {
			Log.v(TAG, "滚动");
		}
		
		@Override
		public void onLongPress(TrackballGestureDetector detector) {
			Log.v(TAG, "长按");
		}
		
		@Override
		public void onDoubleTap(TrackballGestureDetector detector) {
			Log.v(TAG, "双击");
		}
	};
	
	
	interface OnTrackballGestureListener {
		public void onTap(TrackballGestureDetector detector);
		public void onDoubleTap(TrackballGestureDetector detector);
		public void onLongPress(TrackballGestureDetector detector);
		public void onScroll(TrackballGestureDetector detector);
	}

	
	private Handler.Callback mHandleEvent = new Callback() {
		
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case TAP:
				// If the user's finger is still down, do not count it as a tap
				mIsSingleTaping = true;
				if (!mStillDown && null != mTrackballListener) {
					mTrackballListener.onTap(TrackballGestureDetector.this);
				}
				mIsSingleTaping = false;
				break;
			case DOUBLE_TAP:
				mIsDoubleTapping = true;
				if (null != mTrackballListener) {
					mTrackballListener.onDoubleTap(TrackballGestureDetector.this);
				}
				mIsDoubleTapping = false;
				break;
			case SCROLL:
				mIsScrolling = true;
				if (null != mTrackballListener) {
					mTrackballListener.onScroll(TrackballGestureDetector.this);
				}
				mScrollX = mScrollY = 0;
				mIsScrolling = false;
				break;
			case LONG_PRESS:
				dispatchLongPress();
				break;
			default:
				break;
			}
			return false;
		}
	};

	@Deprecated
	TrackballGestureDetector() {
		this(new Handler());
	}
	
	TrackballGestureDetector(Handler handler) {
		super();
		mHandler = new Handler(handler.getLooper(), mHandleEvent);
	}

	/**
	 * Analyze a MotionEvent. Call this once for each MotionEvent that is received by a view.
	 * @param paramMotionEvent The MotionEvent to analyze.
	 */
	public void analyze(MotionEvent event) {
		
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		
		case MotionEvent.ACTION_DOWN:
			Log.v(TAG, "ACTION_DOWN");
			boolean hadTapMessage = mHandler.hasMessages(TAP);
			if (hadTapMessage) {
				mHandler.removeMessages(TAP);
			}
			if ((null != mCurrentDownEvent) && (null != mPreviousUpEvent) && hadTapMessage && isConsideredDoubleTap(mCurrentDownEvent, mPreviousUpEvent, event)) {
				// double tap
				mIsDoubleTapping = true;
				mHandler.sendEmptyMessage(DOUBLE_TAP);
			}else {
				// This is a first tap
				mHandler.sendEmptyMessageDelayed(TAP, DOUBLE_TAP_TIMEOUT);
			}
			mStillDown = true;
			mCurrentDownEvent = MotionEvent.obtain(event);
            mHandler.removeMessages(LONG_PRESS);
            mHandler.sendEmptyMessageAtTime(LONG_PRESS, mCurrentDownEvent.getDownTime()
                    + TAP_TIMEOUT + LONG_PRESS_TIMEOUT);
			break;
		case MotionEvent.ACTION_MOVE:
			Log.v(TAG, "ACTION_MOVE");
			Log.v(TAG, "event:" + event);
			mStillDown = false;
			mScrollX += event.getX();
			mScrollY += event.getY();
			if (-SCROLL_RANGE > mScrollX || mScrollX > SCROLL_RANGE
					|| -SCROLL_RANGE > mScrollY || mScrollY > SCROLL_RANGE) {
				mHandler.sendEmptyMessage(SCROLL);
			}
			break;

		case MotionEvent.ACTION_UP:
			Log.v(TAG, "ACTION_UP");
			mStillDown = false;
			if (mInLongPress) {
				mHandler.removeMessages(TAP);
				mInLongPress = false;
			}
			mPreviousUpEvent = MotionEvent.obtain(event);
			mHandler.removeMessages(LONG_PRESS);
			break;
		default:
			break;
		}
	}

	/**
	 * Register a runnable to be called when a longPress event is detected.
	 * @param paramRunnable The runnable to use for the callback.
	 */
	public void registerLongPressCallback(Runnable paramRunnable) {
		if (mLongPressRunnable != paramRunnable) {
			if (null != mLongPressRunnable) {
				mHandler.removeCallbacks(mLongPressRunnable);
			}
			mLongPressRunnable = paramRunnable;
		}
	}

	/**
	 * Checks whether the current MotionEvent is a scroll event.
	 * @return True if the current motion event is a scroll event; false otherwise.
	 */
	public boolean isScroll() {
		return mIsScrolling;
	}

	/**
	 * Returns the X-coordinate position of the current scroll event. This value is only defined if {@link#isScroll()} is true
	 * @return the X position of the current scroll event.
	 */
	public float scrollX() {
		return mScrollX;
	}

	/**
	 * Returns the Y-coordinate position of the current scroll event. This value is only defined if {@link#isScroll()} is true. 
	 * @return the Y position of the current scroll event.
	 */
	public float scrollY() {
		return mScrollY;
	}

	/**
	 * Checks whether the current MotionEvent is a single-tap event.
	 * @return True if the current motion event is a single-tap event; false otherwise.
	 */
	public boolean isTap() {
		return mIsSingleTaping;
	}

	/**
	 * Returns the X-cordinate position of the current tap event. This value is only defined if either {@link#isTap()} is true.
	 * @return The X position of the current tap event.
	 */
	public float getCurrentDownX() {
		return mCurrentDownEvent.getX();
	}

	/**
	 * Returns the Y-cordinate position of the current tap event. This value is only defined if either {@link#isTap()} is true.
	 * @return The Y position of the current tap event.
	 */
	public float getCurrentDownY() {
		return mCurrentDownEvent.getY();
	}

	/**
	 * Checks whether the current MotionEvent is a double-tap event.
	 * @return True if the current motion event is a double-tap event; false otherwise.
	 */
	public boolean isDoubleTap() {
		return mIsDoubleTapping;
	}

	/**
	 * Returns the X-cordinate position of the current double-tap event. This value is only defined if {@link#isDoubleTap()} is true.
	 * @return The X position of the (first tap in a) double-tap event.
	 */
	public float getFirstDownX() {
		if (null != mPreviousDownEvent) {
			return mPreviousDownEvent.getX();
		}
		return 0;
	}

	/**
	 * Returns the Y-cordinate position of the current double-tap event. This value is only defined if {@link#isDoubleTap()} is true.
	 * @return The Y position of the (first tap in a) double-tap event.
	 */
	public float getFirstDownY() {
		if (null != mPreviousDownEvent) {
			return mPreviousDownEvent.getY();
		}
		return 0;
	}
	
	// 断定是否为doubleTap
    private boolean isConsideredDoubleTap(MotionEvent firstDown, MotionEvent firstUp,
            MotionEvent secondDown) {
        if (secondDown.getEventTime() - firstUp.getEventTime() > DOUBLE_TAP_TIMEOUT) {
            return false;
        }
        return true;
    }
	
    // 分发LONG_PRESS事件
    private void dispatchLongPress() {
        mHandler.removeMessages(TAP);
        mInLongPress = true;
        if (null != mTrackballListener) {
			mTrackballListener.onLongPress(this);
		}
        if (null != mLongPressRunnable) {
			mLongPressRunnable.run();
		}
    }
}
