package com.lity.android.apis.animation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.lity.android.apis.R;

public class AnimationView extends View {
    private static final int STEP_COUNT = 100;
    private Bitmap mBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.tabbar_item_focus_left);;
    private int mCurrentStep = 0;
    private long mLastTime = 0;

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public AnimationView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * @param context
     * @param attrs
     */
    public AnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @param context
     */
    public AnimationView(Context context) {
        super(context);
    }

    @Override
    public void draw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.draw(canvas);
        Log.v("DEBUG", "draw method");
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        long time = System.currentTimeMillis();
//        Log.v("DEBUG", "两次绘图的时间间隔:" + (time - mLastTime));
//        mLastTime = time;
        Matrix matrix = new Matrix();
        canvas.getMatrix(matrix);
        matrix.postRotate(0.5f);
        canvas.setMatrix(matrix);
        super.onDraw(canvas);
//        if (mCurrentStep < STEP_COUNT) {
//            canvas.translate(mCurrentStep, 0);
//            canvas.drawBitmap(mBitmap, 0, 0, new Paint());
//            mCurrentStep  += 5;
//            invalidate();
//        }
//        else {
//            canvas.translate(mCurrentStep, 0);
//            canvas.drawBitmap(mBitmap, 0, 0, new Paint());   
//        }
        
//        while (mCurrentStep < STEP_COUNT) {
//           super.onDraw(canvas);
//          canvas.translate(mCurrentStep, 0);
//          canvas.drawBitmap(mBitmap, 0, 0, new Paint());
//          mCurrentStep  += 5;
//          invalidate();
//        }
    }

    @Override
    protected void onAnimationEnd() {
        super.onAnimationEnd();
        Log.v("DEBUG", "Animation onAnimationEnd method");
    }

    @Override
    protected void onAnimationStart() {
        // TODO Auto-generated method stub
        super.onAnimationStart();
        Log.v("DEBUG", "Animation onAnimationStart method");
    }
    
}
