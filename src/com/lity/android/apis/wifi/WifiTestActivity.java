package com.lity.android.apis.wifi;

import com.android.internal.telephony.ITelephony;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.database.ContentObservable;
import android.database.ContentObserver;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.ContactsContract;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

/*
 * ��ȡwifi����,��2.2���Ͽ���
 */
public class WifiTestActivity extends Activity {
	static final String TAG = WifiTestActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WifiManager wm;
		
		// TODO ʹ�����������ȡ������Ϣ
//		android.net.TrafficStats;
		
		// (֪ͨ��¼)������ϵ�˵�uri: ContactsContract.Contacts.CONTENT_URI, false
		// �������ŵ�uri:content://sms/, false(����Ҳ�����)
		
//		getContentResolver().registerContentObserver(ContactsContract.Contacts.CONTENT_URI, 
//				false, new ContactContentObserver(new Handler(getMainLooper())));
		
//		TelephonyManager tm = (TelephonyManager)getSystemService(Service.TELEPHONY_SERVICE);
//		tm.listen(new PhoneStateListener(){
//			@Override
//			public void onCallStateChanged(int state, String incomingNumber) {
//				super.onCallStateChanged(state, incomingNumber);
//                switch (state) {
//                // �ж��Ƿ��е绰����  
//                case TelephonyManager.CALL_STATE_RINGING:
//                	try {
//                		// ���绰����ʱ���Զ��Ҷϡ�
//						 Log.v("DEBUG", "will abort call");
//				         IBinder binder = ServiceManager.getService(Service.TELEPHONY_SERVICE);
//						 ITelephony telephony2 = ITelephony.Stub.asInterface(binder);
//				         telephony2.endCall();
//                	} catch (RemoteException e) {
//                		e.printStackTrace();
//                	}
//                }
//			}
//		}, PhoneStateListener.LISTEN_CALL_STATE);

	}
	
	

}
