package com.lity.android.apis.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.lity.android.apis.App;
import com.lity.android.apis.R;
import com.lity.android.apis.notification.NotificationWindow2;

public class ServerService extends Service {

    /**
     * 通知的ID
     */
    private final int NOTIFICATION_ID = 0x1;
    
    /**
     * 通知管理器的引用
     */
    private NotificationManager mNotificationManager;
    
    /**
     * 一个通知
     */
    private Notification notify;
    
    /**
     * 后台运行的线程
     */
    private ServiceThread mThread;
    
    /**
     * 点击通知跳转的intent
     */
    private PendingIntent pendingIntent;
    
    
    /**
     * 标识是否中断线程执行
     */
    private boolean mIsStop = false;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        NotificationManager notifyManager = (NotificationManager)getSystemService(Service.NOTIFICATION_SERVICE);
        notify = new Notification();
        notify.defaults = Notification.DEFAULT_SOUND;
        notify.icon = R.drawable.icon;
        
        if (App.DEBUG) {
            Log.v(App.TAG,  "excuting:" + Thread.currentThread());
        }
        mThread = new ServiceThread();
        mThread.start();
    }
    
    

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        mIsStop = true;
        super.onDestroy();
    }
    
    class ServiceThread extends Thread {

        @Override
        public void run() {
            super.run();
            while (!mIsStop) {
                try {
                    sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (App.DEBUG) {
                    Log.v(App.TAG,  "excuting:" + Thread.currentThread().toString());
                    Log.v(App.TAG, "application:" + getApplication());
                }
            }
        }
        
    }

}
