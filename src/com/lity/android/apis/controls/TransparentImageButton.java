package com.lity.android.apis.controls;

import com.lity.android.apis.App;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageButton;

public class TransparentImageButton extends ImageButton {

    public TransparentImageButton(Context context) {
        this(context, null);
    }

    public TransparentImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TransparentImageButton(Context context, AttributeSet attrs,
            int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final Region region = getDrawable().getTransparentRegion();
//        Drawable drawable = null;
//        Bitmap bitmap = null;
//        bitmap.
        if (App.DEBUG) {
            Log.v(App.TAG, "event, x:" + event.getX() + ",y:" + event.getY());
            Log.v(App.TAG, "region: " + region);
        }
        int x = (int)(event.getX() + 0.5) - getPaddingLeft();
        int y = (int)(event.getY() + 0.5) - getPaddingTop();
        Bitmap bitmap = ((BitmapDrawable)getDrawable()).getBitmap();
        if (App.DEBUG) {
            Log.v(App.TAG, "bitmap, x:" + x + ", y:" + y);
            Log.v(App.TAG, "bitmap, width:" + bitmap.getWidth() + ", height:" + bitmap.getHeight());
        }
        
        if (0 <= x && x < bitmap.getWidth() && 0 <= y && y < bitmap.getHeight() && Color.TRANSPARENT != bitmap.getPixel(x, y)) {
            return super.onTouchEvent(event);
        }
        else if (MotionEvent.ACTION_CANCEL == event.getAction()) {
            return super.onTouchEvent(event); 
        }
        return false;
    }

}
