package com.lity.android.apis.system;

import java.lang.reflect.Method;

import com.android.internal.telephony.ITelephony;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.os.ServiceManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class CallReceive extends BroadcastReceiver {
	/*
	 * ������,��һ���õ�IBinder.
	 * �����manifest�ļ���δ���Ȩ��android.permission.CALL_PHONE,�������޷��Ҷϵ绰.
	 * 
	 * TODO �����ǰ������ǰ̨,���������һ��"�Ƿ�����绰�Ĵ���?",��ʱ���,����onReceive���жϵ绰ǰ���߼�
	 *
	 */
	
	@Override
	public void onReceive(Context context, Intent intent) {
		TelephonyManager tm = (TelephonyManager)context.getSystemService(Service.TELEPHONY_SERVICE);
		AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		ITelephony telephony = null;
		switch (tm.getCallState()) {
		case TelephonyManager.CALL_STATE_RINGING:
			String incomingTel = intent.getStringExtra("incoming_number");
			Toast.makeText(context, "������룺" + incomingTel, Toast.LENGTH_LONG)
					.show();
			Log.v("DEBUG", "������룺" + incomingTel);
			if (incomingTel.equals("15901782875")) {// ����ָ���ĵ绰����
				// �Ⱦ�������
				am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
				Log.d("mayingcai", "Turn Ringtone Silent");
				Class<TelephonyManager> c = TelephonyManager.class;
				Method getITelephonyMethod = null;
				try {
					getITelephonyMethod = c.getDeclaredMethod("getITelephony",
							(Class[])null);
					getITelephonyMethod.setAccessible(true);
					telephony = (ITelephony) getITelephonyMethod.invoke(tm,
							(Object[]) null);
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					// �Ҷϵ绰
					if (null != telephony) {
						Log.v("DEBUG", "will abort call");
				         IBinder binder = ServiceManager.getService(Service.TELEPHONY_SERVICE);
						 ITelephony telephony2 = ITelephony.Stub.asInterface(binder);
				         telephony2.endCall();
					} else {
						Log.v("DEBUG", "have income call, but have not find ITelephony");
						throw new RuntimeException("have income call, but have not find ITelephony");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// �ٻָ���������
			am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
			break;

		default:
			break;
		}
	}

}
