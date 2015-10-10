package com.lity.android.apis.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsReceive extends BroadcastReceiver {


	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		Bundle mBundle = arg1.getExtras();
		
		Object messages[] = (Object [])mBundle.get("pdus");
		SmsMessage smsMessage[] = new SmsMessage[messages.length];
		
		for (int i = 0; i < messages.length; i++){
			smsMessage[i] = SmsMessage.createFromPdu((byte []) messages[i]);
		}
		
		Toast mToast = Toast.makeText(arg0, "¶ÌÐÅÄÚÈÝ£º" + smsMessage[0].getMessageBody(), Toast.LENGTH_LONG);
		System.out.println("SmsReceive onRecerve Method");
//		abortBroadcast();
		mToast.show();
	}

}
