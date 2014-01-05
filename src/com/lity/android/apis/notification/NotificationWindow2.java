package com.lity.android.apis.notification;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;

import com.lity.android.apis.service.ServerService;

public class NotificationWindow2 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // È¥µôÍ¨Öª
        NotificationManager notify = (NotificationManager)getSystemService(ServerService.NOTIFICATION_SERVICE);
        notify.cancel(1);
    }

}
