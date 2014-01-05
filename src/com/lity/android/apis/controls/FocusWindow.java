package com.lity.android.apis.controls;

import java.lang.reflect.Field;

import com.lity.android.apis.R;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable.Creator;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FocusWindow extends Activity implements OnClickListener {
    
    private static final String TAG = "DEBUG";

    private Button mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.controls_focus_window);
        mButton = (Button)findViewById(R.id.focus_test_button);
        mButton.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
        try {
            Application app = getApplication();
            Context appContext = getApplicationContext();
            Class<?> baseClass = getApplication().getClass();
            Field baseField =  baseClass.getDeclaredField("mBase");
            Log.v(TAG, "app:" + app);
            Log.v(TAG, "appContext:" + appContext);
            Log.v(TAG, "baseClass:" + baseClass);
            Log.v(TAG, "baseField:" + baseField);
        } catch (Exception e) {
            // TODO: handle exception
        }
        
        Window window = getWindow();
        Class<?> winClass = getWindow().getClass();
        Log.v(TAG, "window:" + window);
        Log.v(TAG, "winClass:" + winClass);
        ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        Log.v(TAG, "am:" + am);
        
        Creator taskInfo = ActivityManager.RunningTaskInfo.CREATOR;
        Log.v(TAG, "taskInfo:" + taskInfo);
    }

}
