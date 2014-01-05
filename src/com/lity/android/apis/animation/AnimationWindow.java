package com.lity.android.apis.animation;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;

import com.lity.android.apis.R;

public class AnimationWindow extends Activity implements AnimationListener {

    private TranslateAnimation mAnimation;
    private AnimationView mAnimationView;
    private Button mButton;
    private Transformation mTransformation = new Transformation();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_window);
        
        mAnimationView = (AnimationView)findViewById(R.id.animation_test_imageview);
        mButton = ((Button)findViewById(R.id.animation_test_button));
        mButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                mAnimation = new TranslateAnimation(0, 200, 0, 0);
                mAnimation.setAnimationListener(AnimationWindow.this);
                mAnimation.setDuration(800);
                mAnimation.setFillBefore(true);
                mAnimationView.setAnimation(mAnimation);
                mAnimation.start();
            }
        });
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        
    }

    @Override
    public void onAnimationStart(Animation animation) {
        Log.v("DEBUG", "ImageView time:" + mAnimationView.getDrawingTime() + "button drawingtime = " + mButton.getDrawingTime());
        Log.v("DEBUG", "transformation:" + mTransformation);

    }
    
    
    class Rotate3D extends Animation {

        @Override
        protected void applyTransformation(float interpolatedTime,
                Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            
        }
        
    }

}
