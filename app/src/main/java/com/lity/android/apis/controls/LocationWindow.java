package com.lity.android.apis.controls;

import com.lity.android.apis.App;
import com.lity.android.apis.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class LocationWindow extends Activity {

    private Button mButton = new Button(this);
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.controls_location_window);
        
//        LocationView view = (LocationView)findViewById(R.id.location_locationview);
//        if (App.DEBUG) {
//            Log.v(App.TAG, "Locationview:" + view);
//        }
    }

}
