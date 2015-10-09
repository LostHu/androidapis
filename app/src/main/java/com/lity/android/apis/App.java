package com.lity.android.apis;

import android.app.Application;
import android.database.Cursor;
import android.util.Log;

public class App extends Application {
    
    /**
     * 是否打印log
     */
    public static final boolean DEBUG = true;
    
    /**
     * 打印log的tag
     */
    public static final String TAG = App.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        if (DEBUG) {
            Log.v(TAG, this + "oncreate method");
        }
    }
}
