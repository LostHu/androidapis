package com.lity.android.apis.dialog;

import com.lity.android.apis.controls.PopupViewWindow;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View.MeasureSpec;
import android.widget.PopupWindow;

public class MyAlertDialogWindow extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	Dialog d = new Dialog(this);
    	d.show();
    }
}
