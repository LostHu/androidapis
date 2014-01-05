package com.lity.android.apis.system;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsPopReceive extends BroadcastReceiver {

	/*
	 * 此类为广播接收类，用来监听收到短消息的广播。
	 * 但由于未找到向个发送信息的广播，所以不能用来监听短消息的动态。
	 * 暂时不用些类，取代的方法是用：ContentObserver,
	 * 如果发重新使用，在manifest 中改为android:enabled="true"
	 * 
	 * 此类用再用于上面所述的用途,改为收到短消息后弹出对话框
	 * 
	 */
	
	public static final String TAG = SmsPopReceive.class.getSimpleName();
	
	private String[] mBlacks = new String[]{
		"15901782875",
		"18017693903",
	};

	@Override
	public void onReceive(Context context, Intent intent) {
		
//		Log.v(TAG, "onReceive()");
//		
//		// 第一步、获取短信的内容和发件人
//		StringBuilder body = new StringBuilder();// 短信内容
//		StringBuilder number = new StringBuilder();// 短信发件人
//		Bundle bundle = intent.getExtras();
//		String smsBody = null;
//		String smsNumber = null;
//		if (bundle != null) {
//			Object[] _pdus = (Object[]) bundle.get("pdus");
//			SmsMessage[] message = new SmsMessage[_pdus.length];
//			for (int i = 0; i < _pdus.length; i++) {
//				message[i] = SmsMessage.createFromPdu((byte[]) _pdus[i]);
//			}
//			for (SmsMessage currentMessage : message) {
//				body.append(currentMessage.getDisplayMessageBody());
//				number.append(currentMessage.getDisplayOriginatingAddress());
//			}
//			smsBody = body.toString();
//			smsNumber = number.toString();
//		}
//		Log.v(TAG, "smsBody:" + smsBody);
//		Log.v(TAG, "smsNumber:" + smsNumber);
//		
//		// 第二步:确认该短信内容是否满足过滤条件
//		boolean filterFlag = false;
//		if (null != smsNumber) {
//			for (String each : mBlacks) {
//				if (smsNumber.endsWith(each)) {
//					filterFlag = true;
//					break;
//				}
//			}
//		}
//
//		if (filterFlag) {
//			abortBroadcast();
//		}else {
//			// 弹出象对话框的activity
//			Intent popIntent = new Intent(context, SmsPopActivity.class);
//			popIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//			context.startActivity(popIntent);
//		}
		
		
		Log.v(TAG, "onReceive, action:" + intent.getAction());
	}

}
