package com.lity.android.apis.map;

import android.content.Context;
import android.location.Geocoder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.google.android.maps.MapView;
import com.lity.android.apis.App;
import com.lity.android.apis.draw.View3DWindow.View3D;

public class SearchMapView extends MapView {
    /**
     * ˫�����
     */
    public static final int DOUBLE_DURATION = 600;
    
    /**
     * ��һ�δ���ACTION_DOWNʱ��MotionEvent
     */
    private MotionEvent mFirstMotionEvent;
    
    /**
     * ������runnable
     */
    private Runnable mLongRunnable = new Runnable() {
        @Override
        public void run() {
            onLongClick(mFirstMotionEvent);
        }
    };
    
    /**
     * ��ʶ,�Ƿ񴥷��˵�һ��ACTION_DOWN
     */
    @Deprecated
    private boolean mTouchDownFirst = false;
    
    private onMapLongClickListener mMapLongClickListener;
    
    
    public SearchMapView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SearchMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchMapView(Context context, String apiKey) {
        super(context, apiKey);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
        case MotionEvent.ACTION_UP:
            removeCallbacks(mLongRunnable);
            break;
        case MotionEvent.ACTION_MOVE:
            if (null != mFirstMotionEvent) {
                if (Math.abs(event.getX() - mFirstMotionEvent.getX()) > ViewConfiguration.getTouchSlop() 
                        || Math.abs(event.getY() - mFirstMotionEvent.getY()) > ViewConfiguration.getTouchSlop()) {
                    removeCallbacks(mLongRunnable);
                }
            }
            break;
        case MotionEvent.ACTION_DOWN:
            if (App.DEBUG) {
                Log.v(App.TAG, "MotionEvent.ACTION_DOWN, mTouchDownFirst:" + mTouchDownFirst);
                Log.v(App.TAG, "MotionEvent.ACTION_DOWN, mFirstMotionEvent:" + mFirstMotionEvent);
            }
            // �ڶ���ACTION_DOWN
            if (null != mFirstMotionEvent) {
                if (event.getEventTime() - mFirstMotionEvent.getEventTime() < DOUBLE_DURATION) {
                    if (Math.abs(event.getX() - mFirstMotionEvent.getX()) < ViewConfiguration.getTouchSlop() 
                            && Math.abs(event.getY() - mFirstMotionEvent.getY()) < ViewConfiguration.getTouchSlop()) {
                        onDoubleClick(mFirstMotionEvent);
                    }
                }
            }
            // ��¼���һ�ε�MotionEvent
            mFirstMotionEvent = MotionEvent.obtain(event);
            
            
            // Ϊʵ�� �����¼�
            postDelayed(mLongRunnable, ViewConfiguration.getLongPressTimeout());
            break;
        case MotionEvent.ACTION_OUTSIDE:
            removeCallbacks(mLongRunnable);
            break;
        case MotionEvent.ACTION_CANCEL:
            removeCallbacks(mLongRunnable);
            break;
        default:
            break;
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 
     * @return unusefull
     */
    protected boolean onDoubleClick(MotionEvent event) {
        if (App.DEBUG) {
            Log.v(App.TAG, "onDoubleClick method!");
        }
        getController().zoomInFixing((int)mFirstMotionEvent.getX(), (int)mFirstMotionEvent.getY());
        return true;
    }
    
    /**
     * 
     * @return unusefull
     */
    protected boolean onLongClick(MotionEvent event) {
        if (App.DEBUG) {
            Log.v(App.TAG, "onLongClick method!");
        }
        if (null != mMapLongClickListener) {
            mMapLongClickListener.onMapLongClick(event);
        }
        return true;
    }
    
    
    
    @Override
	public boolean onTrackballEvent(MotionEvent event) {
		return super.onTrackballEvent(event);
	}



	public static interface onMapLongClickListener {
        public boolean onMapLongClick(MotionEvent event);
    }

    public onMapLongClickListener getOnMapLongClickListener() {
        return mMapLongClickListener;
    }

    public void setOnMapLongClickListener(onMapLongClickListener l) {
        mMapLongClickListener = l;
    }
}
