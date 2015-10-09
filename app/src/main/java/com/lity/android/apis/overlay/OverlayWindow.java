package com.lity.android.apis.overlay;

import com.lity.android.apis.App;
import com.lity.android.apis.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class OverlayWindow extends Activity implements OnClickListener {
    private boolean isadded;

    public Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overlay_overlay_window);
        mButton = (Button)findViewById(R.id.overlay_button_button);
        mButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                showFakeTitleBar();
            }
        });
//        layout = new LinearLayout(this);
//        layout.setBackgroundColor(Color.GRAY);
//        
//        view = new View(this);
//        view.setBackgroundColor(Color.RED);
//        view.setOnClickListener(this);
//        
//        layout.addView(view);
    }

    private void hideFakeTitleBar(){ 
//        android.view.WindowManager.LayoutParams layoutparams = new android.view.WindowManager.LayoutParams(
//                -1, -1, WindowManager.LayoutParams.TYPE_APPLICATION_SUB_PANEL,
//                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, -1);
//        layoutparams.gravity = 48;
//        layoutparams.y = 25;
//        WindowManager windowmanager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//        View view1 = view;
//        windowmanager.updateViewLayout(view1, layoutparams);
//        View view2 = view;
//        windowmanager.removeView(view2);
//        isadded = false;
    }

    private void showFakeTitleBar() {
        // 让一个视图浮动在你的应用程序之上
//        View view1 = getWindow().peekDecorView();
//        
//        WindowManager windowmanager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//        android.view.WindowManager.LayoutParams layoutparams = new android.view.WindowManager.LayoutParams(
//                320,// 浮动的大小 宽
//                20, // 浮动的 高
//                WindowManager.LayoutParams.TYPE_APPLICATION_SUB_PANEL,
//                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, -1);
//        Rect rect = new Rect();
//        view1.getWindowVisibleDisplayFrame(rect);
//        if (App.DEBUG) {
//            Log.v(App.TAG, "view1:" + view1);
//            Log.v(App.TAG, "getWindowVisibleDisplayFrame:" + rect);   
//        }
//
//        int i = rect.bottom;
//        layoutparams.y = i - 20;
//        try {
//            if (isadded) {
//                windowmanager.removeView(view);
//            }
//        } catch (Exception e) {
//            // 
//            throw new RuntimeException(e);
//        }
//        windowmanager.addView(layout, layoutparams); 
//        isadded = true; 
        TextView text = new TextView(this);
        text.setBackgroundColor(Color.RED);
        text.setText("hello, popupwindow");
        text.setPadding(30, 30, 30, 30);
//        LayoutParams params = new LayoutParams(200, 200);
//        text.setLayoutParams(params);
        PopupWindow popup = 
        new PopupWindow(text, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
//        new pop
//        popup.setContentView(text);
//        popup.showAsDropDown(findViewById(R.id.main), 100, 100);
        popup.showAtLocation(findViewById(R.id.main), Gravity.CENTER, 100, 100);
    }

    @Override
    public void onClick(View v) {
        if (App.DEBUG) {
            Log.v(App.TAG, "onClick ");
        }
//        TranslateAnimation animation = new TranslateAnimation(0, 200, 0, 0);
//        animation.setDuration(5000);
//        view.startAnimation(animation);
//        animation.setAnimationListener(new AnimationListener() {
//            
//            @Override
//            public void onAnimationStart(Animation animation) {
//            }
//            
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//            }
//            
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                startActivity(new Intent(OverlayWindow.this, ChessWindow.class));
//            }
//        });
    }
}
