package com.lity.android.apis.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * �㲥,���ֻ����������{@link#onRecerive()}��������service����
 * @author Lity
 *
 */
public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
/*        Intent intent2 = new Intent();
        intent2.setClass(context, ServerService.class);
        
        Log.v("DEBUG", "BootRecerver Process :" + context.getApplicationInfo().packageName);
        context.startService(intent2);
        abortBroadcast();*/
    }
    
    void printPro(Context context){
//        ActivityManager activityManager = context.get 
    }
    
}
