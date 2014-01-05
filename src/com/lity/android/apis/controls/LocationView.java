package com.lity.android.apis.controls;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lity.android.apis.App;
import com.lity.android.apis.R;

public class LocationView extends LinearLayout  {
    
    protected static final int DURATION = 300;
    protected static final int RETAIN_WIDTH = 26;
    
    private static final String SHOW_BG_PATH = "/resources/controls_location_show.png"; 
    private static final String HIDE_BG_PATH = "/resources/controls_location_hide.png";

    private ImageButton mLeft;
    private TextView mText;
    private ImageButton mRight;
    
    private AnimationSet mShow;
    private AnimationSet mHide;
    private boolean mIsShow = true;
    
    private Drawable mShowDrawable;
    private Drawable mHideDrawable;
    
    // 处理左右按钮的点击事件
    private View.OnClickListener mEventHandler = new OnClickListener() {
        
        @Override
        public void onClick(View v) {
            if (mLeft == v) {
//              clearAnimation();
              if (mIsShow) {
                  startAnimation(mHide);
                  mIsShow = false;
                  setBackgroundResource(R.drawable.controls_location_hide);
              }
              else {
                  startAnimation(mShow);
                  mIsShow = true;
                  setBackgroundResource(R.drawable.controls_location_show);
              }
              if (App.DEBUG) {
                  Log.v(App.TAG, "click left button");
              }   
          }
          else if (mRight == v) {
              if (App.DEBUG) {
                  Log.v(App.TAG, "click right button");
              }    
          }
        }
    };
    
    /**
     * @param context
     * @param attrs
     */
    public LocationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @param context
     */
    public LocationView(Context context) {
        super(context);
    }

    private void createSubView() {
        // TODO 从jar中读取图片文件
//        try {
//            InputStream is = getContext().getAssets().open("controls_location_bg.png");
//            bg = new BitmapDrawable(BitmapFactory.decodeStream(is));
//        } catch (Exception e) {
//            // TODO: handle exception
////            bg = null;
//            throw new RuntimeException("The file \"assets/controls_location_bg.png\" has not exist");
//        }
//        URL url = LocationView.class.getResource(BG_PATH);
        
//        InputStream is = LocationView.class.getResourceAsStream(SHOW_BG_PATH);
//        if (App.DEBUG) {
//            Log.v(App.TAG, "is:" + is);
//        }
        
        setBackgroundResource(R.drawable.controls_location_bg);
        setOrientation(LinearLayout.HORIZONTAL);
        
        mLeft = new ImageButton(getContext());
        mLeft.setBackgroundColor(Color.TRANSPARENT);
        LayoutParams p = new LayoutParams(26, 23);
        addView(mLeft, p);
        
        mText = new TextView(getContext());
        LayoutParams midP = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT);
        midP.weight = 1;
        mText.setText("");
        addView(mText, midP);
        
        mRight = new ImageButton(getContext());
        mRight.setBackgroundColor(Color.TRANSPARENT);
        addView(mRight, p);
        
        mLeft.setOnClickListener(mEventHandler);
        mRight.setOnClickListener(mEventHandler);
        
    }
    
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLeft = (ImageButton)findViewById(R.id.locationview_left_imageButton);
        mText = (TextView)findViewById(R.id.locationview_mid_textview);
        mRight = (ImageButton)findViewById(R.id.locationview_right_imageButton);
        
        mLeft.setOnClickListener(mEventHandler);
        mRight.setOnClickListener(mEventHandler);
        
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        
        int width = getMeasuredWidth();
        if (App.DEBUG) {
            Log.v(App.TAG, "onMeasurd method, measuredWidth:" + width);
            Log.v(App.TAG, "onMeasurd method, Width:" + getWidth());
            Log.v(App.TAG, "onMeasurd method, mRight.getRight():" + mRight.getRight());
            Log.v(App.TAG, "onMeasurd method, mRight.getLeft():" + mRight.getLeft());
        }
        
        width -= mRight.getMeasuredWidth();
        mHide = new AnimationSet(false);
        Animation animation = new TranslateAnimation(0, width, 0, 0);
        
        BounceInterpolator interpolator = new BounceInterpolator();
        animation.setInterpolator(interpolator);
        
        mHide.addAnimation(animation);
        animation = new AlphaAnimation(1.0f, 0.5f);
        mHide.addAnimation(animation);
        mHide.setFillAfter(true);
        mHide.setFillBefore(true);
        mHide.setDuration(DURATION);

        mShow = new AnimationSet(false);
        animation = new TranslateAnimation(width, 0, 0, 0);
        mShow.addAnimation(animation);
        animation = new AlphaAnimation(0.5f, 1.0f);
        mShow.addAnimation(animation);
        mShow.setFillAfter(true);
        mShow.setFillBefore(true);
        mShow.setDuration(DURATION);
    }   

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!mIsShow) {
//            if (App.DEBUG) {
//                Log.v(App.TAG, "ev.x:" + ev.getX() + ", ev.y:" + ev.getY());
//            }
//            Region region = new Region(mRight.getLeft(), mRight.getTop(), 
//                    mRight.getRight(), mRight.getBottom());
//            if (App.DEBUG) {
//                Log.v(App.TAG, "region: " + region);
//            }
//            if (region.contains((int)ev.getX(), (int)ev.getY())) {
//                mEventHandler.onClick(mLeft);
//            }
//            return true;
            return false;
        }
        return super.dispatchTouchEvent(ev);
    }


}
