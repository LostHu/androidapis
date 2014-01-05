package com.lity.android.apis.controls;

import com.lity.android.apis.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.widget.Button;
import android.widget.PopupWindow;

public class PopupViewWindow extends Activity implements View.OnClickListener {

    private Button mMenuButton;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupview_window);
        mMenuButton = (Button)findViewById(R.id.popupview_menu_button);
        mMenuButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        PopupWindow popup = new PopupWindow((View)LayoutInflater.from(this).inflate(R.layout.popup_menu, null), 
                200, 200);
        popup.showAsDropDown(mMenuButton, 50, 50);
    }
}
