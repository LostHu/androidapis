package com.lity.android.apis.service;

import com.lity.android.apis.R;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class ExitWindow extends Activity {
    
    private Button mStartService;
    private Button mStopService;
    private Button mExitProcess;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_exit_window);
        
        LinearLayout l = (LinearLayout)findViewById(R.id.service_exit_linearlayout);
        mStartService = (Button)l.getChildAt(0);
        mStopService = (Button)l.getChildAt(1);
        mExitProcess = (Button)l.getChildAt(2);
        
        mIntent = new Intent(this, ServerService.class);
        
        mStartService.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                startService(mIntent);
            }
        });
        
        mStopService.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                stopService(mIntent);
            }
        });
        
        mExitProcess.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }

    
}
