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
	 * ����Ϊ�㲥�����࣬���������յ�����Ϣ�Ĺ㲥��
	 * ������δ�ҵ����������Ϣ�Ĺ㲥�����Բ���������������Ϣ�Ķ�̬��
	 * ��ʱ����Щ�࣬ȡ���ķ������ã�ContentObserver,
	 * ���������ʹ�ã���manifest �и�Ϊandroid:enabled="true"
	 * 
	 * ������������������������;,��Ϊ�յ�����Ϣ�󵯳��Ի���
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
//		// ��һ������ȡ���ŵ����ݺͷ�����
//		StringBuilder body = new StringBuilder();// ��������
//		StringBuilder number = new StringBuilder();// ���ŷ�����
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
//		// �ڶ���:ȷ�ϸö��������Ƿ������������
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
//			// ������Ի����activity
//			Intent popIntent = new Intent(context, SmsPopActivity.class);
//			popIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//			context.startActivity(popIntent);
//		}
		
		
		Log.v(TAG, "onReceive, action:" + intent.getAction());
	}

}
