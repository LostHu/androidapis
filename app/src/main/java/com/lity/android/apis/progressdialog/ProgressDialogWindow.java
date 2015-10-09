package com.lity.android.apis.progressdialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.lity.android.apis.R;

public class ProgressDialogWindow extends Activity implements OnClickListener, android.content.DialogInterface.OnClickListener {

    /**
     * is clicked, will appear progressdialog whose stylel is spinner 
     */
    private Button mButton1;
    /**
     * is clicked, will appear progressdialog whose stylel is horizontal 
     */
    private Button mButton2;
    /**
     * 
     */
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progressdialog_window);
        mButton1 = (Button)findViewById(R.id.progressdialog_button1);
        mButton2 = (Button)findViewById(R.id.progressdialog_button2);
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("notice");
        mProgressDialog.setMessage("进度条");
        if (mButton1 == v) {
            mProgressDialog.setButton("确定", this);
            mProgressDialog.show();
        }
        else if (mButton2 == v) {
            
        }
    }
    @Override
    public void onClick(DialogInterface dialog, int which) {
        
    }

}
