package com.lity.android.apis.notification;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.lity.android.apis.R;
import com.lity.android.apis.service.ServerService;

public class NotificationWindow1 extends Activity implements OnClickListener {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_window1);
        
        Button button = (Button)findViewById(R.id.notification_testbutton);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        
        // TODO ����֮��
        ActivityManager amActivityManager = (ActivityManager)getSystemService(Service.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServiceInfos = amActivityManager.getRunningServices(100);
        for (ActivityManager.RunningServiceInfo runningServiceInfo : runningServiceInfos) {
            if (runningServiceInfo.service.getClassName().equals(ServerService.class.getName())) {
                break;
            }
            Log.v("bug", runningServiceInfo.service.getClassName());
        }
        
        // ����һ��֪ͨ
        NotificationManager notifyManager = (NotificationManager)NotificationWindow1.this.getSystemService(Service.NOTIFICATION_SERVICE);

        Intent intent = new Intent().setClass(this, NotificationWindow2.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
    
        Notification notify = new Notification();
        notify.icon = R.drawable.icon;
        notify.tickerText = "Button1 ֪ͨ����";
        notify.defaults = Notification.DEFAULT_SOUND;
        notify.setLatestEventInfo(this, "button1", "button ֪ͨ", pendingIntent);

        notifyManager.notify(1, notify);
    }

}
