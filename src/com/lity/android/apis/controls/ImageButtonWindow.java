package com.lity.android.apis.controls;

import com.lity.android.apis.App;
import com.lity.android.apis.R;

import android.app.Activity;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewDebug.CapturedViewProperty;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextSwitcher;
import android.widget.ViewSwitcher;
import android.widget.ZoomControls;

public class ImageButtonWindow extends Activity {
    private int clickCount = 0;

    private TransparentImageButton mTransparentImageButton;
    
    private TextSwitcher mSwitcher;
    
    private ZoomControls mZoomControls;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagebutton_window);
        mTransparentImageButton = (TransparentImageButton)findViewById(R.id.imagebutton_test_imagebutton);
        mSwitcher = (TextSwitcher)findViewById(R.id.imagebutton_test_textswitcher);
        mZoomControls = (ZoomControls)findViewById(R.id.imagebutton_test_zoomcontrols);
//        mZoomControls
        
        ImageButton button = (ImageButton)findViewById(R.id.imagebutton_button_imagebutton);
        button.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                if (App.DEBUG) {
//                    Log.v(App.TAG, "left:" + mTransparentImageButton.getLeft() + ", top:" + mTransparentImageButton.getTop() + 
//                            ", right:" + mTransparentImageButton.getRight() + ", bottom:" + mTransparentImageButton.getBottom());
//                    Rect frame = new Rect();
//                    mTransparentImageButton.getHitRect(frame);
//                    Log.v(App.TAG, "out frame: " + frame);
                    Log.v(App.TAG, "clickCount:" + clickCount++);
                    mSwitcher.setText("click:" + clickCount);
                }
            }
        });
    }
    
}
