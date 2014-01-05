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
	 * 此类中,第一次用到IBinder.
	 * 如果在manifest文件中未添加权限android.permission.CALL_PHONE,将导致无法挂断电话.
	 * 
	 * TODO 如果当前程序不在前台,则会闪过过一个"是否接听电话的窗口?",临时解决,减少onReceive中中断电话前的逻辑
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
			Toast.makeText(context, "来电号码：" + incomingTel, Toast.LENGTH_LONG)
					.show();
			Log.v("DEBUG", "来电号码：" + incomingTel);
			if (incomingTel.equals("15901782875")) {// 拦截指定的电话号码
				// 先静音处理
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
					// 挂断电话
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
			// 再恢复正常铃声
			am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
			break;

		default:
			break;
		}
	}

}
