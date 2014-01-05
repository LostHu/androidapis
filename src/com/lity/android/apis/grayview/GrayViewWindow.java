package com.lity.android.apis.grayview;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.lity.android.apis.R;

public class GrayViewWindow extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grayview_window);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        System.out.println(displayMetrics);
    }

}
