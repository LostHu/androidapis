package com.lity.android.apis.grayview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * a subclass of ralativelayout that is covered by gray transparent layer while user touch it down. 
 * all views in this are not clickable ,so it is not useful to set clickable in xml.  
 * 
 * @author Lity
 *
 */
public class GrayRelativeLayout extends RelativeLayout {
    
    /**
     * Paint, to draw the over layer
     */
    private Paint mPaint = new Paint();
    /**
     * flags invalidate the over layer will be drawn
     */
    private Boolean mIsTouchDown = false;
    
    private Canvas mCanvas;
    
    public GrayRelativeLayout(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        initPaint();
    }

    public GrayRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        initPaint();
    }

    public GrayRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        initPaint();
    }
    
    /**
     * set paint to satisfy the requirement of drawing over layer 
     * internal method ,called by constructors 
     */
    private void initPaint(){
        // required 
        setClickable(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.GRAY);
        mPaint.setAlpha(100);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO Auto-generated method stub
        super.onLayout(changed, l, t, r, b);
//        for (int i = 0; i < getChildCount(); i++) {
//            final View child = getChildAt(i);
//            child.setClickable(false);
//        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        mCanvas = canvas;
        super.dispatchDraw(canvas);
//        System.out.println("dispatchDraw");
//        Log.w("bug", "dispatchDraw--");
        if (mIsTouchDown) {
            int valL = getLeft();
            int valT = getTop();
            int valR = getRight();
            int valB = getBottom();
            
//            Log.e("bug", "isTouchDown ["+valL+"] ["+valT+"] ["+valR+"] ["+valB+"] ");
//            Log.w("bug", "isTouchDown TRUe");
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(Color.GRAY);
            mPaint.setAlpha(100);
            canvas.save();
            //canvas.drawRect(getLeft(), getTop(), getRight(), getBottom(), mPaint);
            canvas.scale(2.0f, 2.0f);
            Rect mRect = new Rect(0, 0, 2 * getWidth(), 2 * getHeight());
            canvas.getClipBounds(mRect);
            canvas.drawRect(mRect, mPaint);
            canvas.restore();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        final int action = event.getAction();
        Log.v("bug", "onTouchEvent");
        Log.v("bug", String.valueOf(action));
        switch (action) {
        case MotionEvent.ACTION_DOWN:
            mIsTouchDown = true;
            break;
        case MotionEvent.ACTION_UP:
            Log.v("bug", "Action_UP");
            mIsTouchDown = false;
            break;
        case MotionEvent.ACTION_CANCEL:
            mIsTouchDown = false;
            break;
        case MotionEvent.ACTION_MOVE:
            Log.v("bug", "move");
            Log.v("bug", String.valueOf(event.getX()) + "," + String.valueOf(event.getY()));
            final int touchDotX = (int)(event.getX() + 0.5);
            final int touchDotY = (int)(event.getY() + 0.5);
            final Rect selfRect = new Rect(0, 0, getWidth(), getHeight());
            if (!selfRect.contains(touchDotX, touchDotY)) {
                mIsTouchDown = false;
            }
            break;
        case MotionEvent.ACTION_OUTSIDE:
            Log.v("bug", "outside");
            mIsTouchDown = false;
        default:
            break;
        }
        invalidate();
        return super.onTouchEvent(event);
    }
    
}
