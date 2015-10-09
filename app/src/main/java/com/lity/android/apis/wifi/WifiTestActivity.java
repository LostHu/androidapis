package com.lity.android.apis.wifi;


import android.app.Activity;
import android.net.wifi.WifiManager;
import android.os.Bundle;

/*
 * 获取wifi流量,仅2.2以上可用
 */
public class WifiTestActivity extends Activity {
	static final String TAG = WifiTestActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WifiManager wm;
		
		// TODO 使用这个类来获取流量信息
//		android.net.TrafficStats;
		
		// (通知记录)监听联系人的uri: ContactsContract.Contacts.CONTENT_URI, false
		// 监听短信的uri:content://sms/, false(彩信也是这个)
		
//		getContentResolver().registerContentObserver(ContactsContract.Contacts.CONTENT_URI, 
//				false, new ContactContentObserver(new Handler(getMainLooper())));
		
//		TelephonyManager tm = (TelephonyManager)getSystemService(Service.TELEPHONY_SERVICE);
//		tm.listen(new PhoneStateListener(){
//			@Override
//			public void onCallStateChanged(int state, String incomingNumber) {
//				super.onCallStateChanged(state, incomingNumber);
//                switch (state) {
//                // 判断是否有电话接入  
//                case TelephonyManager.CALL_STATE_RINGING:
//                	try {
//                		// 当电话接入时，自动挂断。
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
