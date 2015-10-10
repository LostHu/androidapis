package com.lity.android.apis.system;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.provider.Telephony.Mms;
import android.provider.Telephony.MmsSms;
import android.provider.Telephony.Sms;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class SystemListenerActivity extends Activity {
	
	public static final String TAG = SystemListenerActivity.class.getSimpleName();
	
	/* д�ռ��ļ����  */
	public static final String LOG_PATH = "/sdcard/userlog.txt";
	public static final String OP_TYPE = "op_type";
	
	private Handler mMainHandler = new Handler();
	
	private final Uri SMS_CONTENT_URI = Uri.parse("content://sms/");
	
	// ������,��onDestroy()Ҫ�Ƴ�����
	SmsContentObserver mSmsContentObserver = new SmsContentObserver(mMainHandler);
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
		
		// ע��Contacts��ContentObserver
		getContentResolver().registerContentObserver(ContactsContract.Contacts.CONTENT_URI, 
				false, new ContactContentObserver(mMainHandler));
		
		// ע�����Ϣ��ContentObserver
		getContentResolver().registerContentObserver(SMS_CONTENT_URI, 
				true, mSmsContentObserver);
		
		// ע����ŵ�ContentObserver
//		getContentResolver().registerContentObserver(MmsSms.CONTENT_URI, 
//				true, new MmsContentObserver(mMainHandler));
		
		
		// ע����յ�����Ϣ, ������������
		getContentResolver().registerContentObserver(Sms.Inbox.CONTENT_URI, 
				true, new SmsReceiveContentObserver(mMainHandler));
		
		// ע�ᷢ������Ϣ, ������������
		getContentResolver().registerContentObserver(Sms.Outbox.CONTENT_URI, 
				true, new SmsSendContentObserver(mMainHandler));
		
		
		// call log
		getContentResolver().registerContentObserver(CallLog.Calls.CONTENT_URI, 
				true, new CallLogContentObserver(mMainHandler));
	}
	
	@Override
	protected void onDestroy() {
		// TODO �Ƴ�����ע���ContentObserver
		getContentResolver().unregisterContentObserver(mSmsContentObserver);
		super.onDestroy();
	}
	
	
	/** ��ϵ�˺�ͨ�ż�¼��ContentObserver **/
	/*
	 * �½���ϵ��,��ӵ��ֻ���sim�����лص�
	 * ɾ����ϵ��,ɾ���ֻ��ϻ�sim���϶��лص�
	 * �޸���ϵ��,�޸��ֻ��ϻ�sim���϶��лص�
	 * 
	 * �����绰(�ҶϺ�)�лص�(���ζ�δ��ͨ)
	 * ����绰(�ҶϺ�)�лص�(���ζ�δ��ͨ)
	 * ɾ��ͨ����¼**********û�лص�
	 */
	class ContactContentObserver extends ContentObserver {
		public ContactContentObserver(Handler handler) {
			super(handler);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			Toast.makeText(SystemListenerActivity.this, "��ϵ������ѱ��޸�", Toast.LENGTH_LONG).show();
			Log.v(TAG, "��ϵ������ѱ��޸�");
		}
	}
	
	/** ���ŵ�ContentObserver **/
	class SmsContentObserver extends ContentObserver {
		/*
		 * ����ԭ��
		 */
		
		
		
		/*
		 * �������յ�����Ϣ,���浽�ռǵĸ�ʽ���£�
		 * {"op_type":"sms_in","sender":"","body":"","send_time":""}
		 * 
		 */
		
		public SmsContentObserver(Handler handler) {
			super(handler);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			Toast.makeText(SystemListenerActivity.this, "��������ѱ��޸�", Toast.LENGTH_LONG).show();
			Log.v(TAG, "��������ѱ��޸�");
			//TODO �п�����ͨ����¼���ContentObserver,����Ҫ������
			/*
			 * ����Ĵ����ǲ������,ԭ�������еĶ��Ų����Ǵ���һ������,��ȡ����Ϣ��id��֪
			 */
//			Cursor cursor = getContentResolver().query(Sms.CONTENT_URI, null, null, null, Sms.DEFAULT_SORT_ORDER);
//			if (null != cursor && cursor.moveToFirst()) {
//				final int smsType = cursor.getInt(cursor.getColumnIndex(Sms.TYPE));
//				final int smsId = cursor.getInt(cursor.getColumnIndex(Sms._ID));
//				JSONObject jsonObject = new JSONObject();
//				switch (smsType) {
//				case Sms.MESSAGE_TYPE_INBOX:
//					Log.v(TAG, "MESSAGE_TYPE_INBOX");
//					try {
//						jsonObject.put(OP_TYPE, "MESSAGE_TYPE_INBOX");
//					} catch (JSONException e) {
//						e.printStackTrace();
//						// TODO �ݲ�������
//					}
//					break;
//				case Sms.MESSAGE_TYPE_OUTBOX:
//					Log.v(TAG, "MESSAGE_TYPE_OUTBOX");
//					try {
//						jsonObject.put(OP_TYPE, "MESSAGE_TYPE_OUTBOX");
//					} catch (JSONException e) {
//						e.printStackTrace();
//						// TODO �ݲ�������
//					}
//					break;
//				case Sms.MESSAGE_TYPE_DRAFT:
//					// �ݸ���,����ݸ���
//					Log.v(TAG, "MESSAGE_TYPE_DRAFT");
//					try {
//						jsonObject.put(OP_TYPE, "MESSAGE_TYPE_DRAFT");
//					} catch (JSONException e) {
//						e.printStackTrace();
//						// TODO �ݲ�������
//					}
//					break;
//				case Sms.MESSAGE_TYPE_SENT:
//					Log.v(TAG, "MESSAGE_TYPE_SENT");
//					try {
//						jsonObject.put(OP_TYPE, "MESSAGE_TYPE_SENT");
//					} catch (JSONException e) {
//						e.printStackTrace();
//						// TODO �ݲ�������
//					}
//					break;
//				case Sms.MESSAGE_TYPE_QUEUED:
//					Log.v(TAG, "MESSAGE_TYPE_QUEUED");
//					try {
//						jsonObject.put(OP_TYPE, "MESSAGE_TYPE_QUEUED");
//					} catch (JSONException e) {
//						e.printStackTrace();
//						// TODO �ݲ�������
//					}
//					break;
//				default:
//					break;
//				}
			JSONObject inJsonObject = null;
			JSONObject sendJsonObject = null;
			boolean inChange = false; // �յ���Ϣ
			boolean sendChange = false; // ������Ϣ
			
			// ����ռ����Ƿ����µ���Ϣ
			Cursor cursor = getContentResolver().query(Sms.Inbox.CONTENT_URI, null, 
					Sms.Inbox._ID + ">?", new String[]{String.valueOf(mInboxMaxId)}, Sms.Inbox.DEFAULT_SORT_ORDER);
			
			if (null != cursor && cursor.moveToFirst()) {
				if (1 == cursor.getCount()) {
					String smsId = cursor.getString(cursor.getColumnIndex(Sms.Inbox._ID));
					String smsBody = cursor.getString(cursor.getColumnIndex(Sms.Inbox.BODY));
					String smsAddress = cursor.getString(cursor.getColumnIndex(Sms.Inbox.ADDRESS));
					String smsDate = "" + new Date(cursor.getLong(cursor.getColumnIndex(Sms.Inbox.DATE)));
					
					Log.v(TAG, "�յ��¶���, smsId: " + smsId + ", smsBody:" + smsBody + ", smsSender: " + smsAddress + ", smsDate: " + smsDate);
					// TODO д����־��
					inJsonObject = new JSONObject();
					try {
						inJsonObject.put(OP_TYPE, "sms_in");
						inJsonObject.put("body", smsBody);
						inJsonObject.put("sender", smsAddress);
						inJsonObject.put("send_time", smsDate);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					writeRecordToLog(inJsonObject);
					mInboxMaxId = Long.parseLong(smsId);
					inChange = true;
				}else if (1 < cursor.getCount()) {
					// TODO ��init()����֮��,�û����Ĺ����ݿ�,�ݲ�������
					Log.e(TAG, "��init()����֮��,�û����Ĺ����ݿ�");
				}
			}
			if (null != cursor) {
				cursor.close();
			}
			
			// ��鷢�����Ƿ����µ���Ϣ
			cursor = getContentResolver().query(Sms.Sent.CONTENT_URI, null, 
					Sms.Inbox._ID + ">?", new String[]{String.valueOf(mSendMaxId)}, Sms.Sent.DEFAULT_SORT_ORDER);
			
			if (null != cursor && cursor.moveToFirst()) {
				if (1 == cursor.getCount()) {
					String smsId = cursor.getString(cursor.getColumnIndex(Sms.Sent._ID));
					String smsBody = cursor.getString(cursor.getColumnIndex(Sms.Sent.BODY));
					String smsAddress = cursor.getString(cursor.getColumnIndex(Sms.Sent.ADDRESS));
					String smsDate = "" + new Date(cursor.getLong(cursor.getColumnIndex(Sms.Sent.DATE)));
					
					Log.v(TAG, "��������, smsId: " + smsId + ", smsBody:" + smsBody + ", smsSender: " + smsAddress + ", smsDate: " + smsDate);
					// TODO д����־��
					sendJsonObject = new JSONObject();
					try {
						sendJsonObject.put(OP_TYPE, "sms_send");
						sendJsonObject.put("body", smsBody);
						sendJsonObject.put("sender", smsAddress);
						sendJsonObject.put("send_time", smsDate);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					writeRecordToLog(sendJsonObject);
					mSendMaxId = Long.parseLong(smsId);
					sendChange = true;
				}else if (1 < cursor.getCount()) {
					// TODO ��init()����֮��,�û����Ĺ����ݿ�,�ݲ�������
					Log.e(TAG, "��init()����֮��,�û����Ĺ����ݿ�");
				}
			}
			if (null != cursor) {
				cursor.close();
			}
			if (inChange && sendChange) {
				Log.e(TAG, "�ռ���ͷ����䶼���µ���Ϣ");
			}else if (!inChange && !sendChange) {
				Log.v(TAG, "���ռ���򷢼���֮��,�������и���");
			} 
		}
	}
	
	/** ���ŵ�ContentObserver **/
	class MmsContentObserver extends ContentObserver {

		public MmsContentObserver(Handler handler) {
			super(handler);
		}
		
		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			Toast.makeText(SystemListenerActivity.this, "���ŵ���", Toast.LENGTH_LONG).show();
			Log.v(TAG, "���ŵ���");
		}
	}
	
	// ����Ϣ���յ�ContentObserver
	@Deprecated
	class SmsReceiveContentObserver extends ContentObserver {
		public SmsReceiveContentObserver(Handler handler) {
			super(handler);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			Toast.makeText(SystemListenerActivity.this, "�ж���Ϣ����", Toast.LENGTH_LONG).show();
			Log.v(TAG, "�ж���Ϣ����");
			Cursor cursor = getContentResolver().query(Telephony.Sms.Inbox.CONTENT_URI, null, null, null, Sms.DEFAULT_SORT_ORDER);
			
			if (null != cursor && cursor.moveToFirst()) {
				JSONObject jsonObject = new JSONObject();
				try {
					jsonObject.put(OP_TYPE, "sms_in");
				} catch (JSONException e) {
					e.printStackTrace();
					//TODO �ݲ�����
				}
				if (null != jsonObject) {
					writeRecordToLog(jsonObject);
				}
			}
		}
	}
	
	// ����Ϣ���͵�ContentObserver
	@Deprecated
	class SmsSendContentObserver extends ContentObserver {
		public SmsSendContentObserver(Handler handler) {
			super(handler);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			Toast.makeText(SystemListenerActivity.this, "�ж���Ϣ����", Toast.LENGTH_LONG).show();
			Log.v(TAG, "�ж���Ϣ����");
			Cursor cursor = getContentResolver().query(Telephony.Sms.Outbox.CONTENT_URI, null, null, null, Sms.DEFAULT_SORT_ORDER);
			if (null != cursor && cursor.moveToFirst()) {
				JSONObject jsonObject = new JSONObject();
				try {
					jsonObject.put(OP_TYPE, "sms_out");
				} catch (JSONException e) {
					e.printStackTrace();
					//TODO �ݲ�����
				}
				if (null != jsonObject) {
					writeRecordToLog(jsonObject);
				}
			}
		}
	}
	
	
	
	// ͨ����¼��contentObserver
	class CallLogContentObserver extends ContentObserver {
		/*
		 * finish:
		 * ���յ����硢ȥ��
		 * TODO
		 * ɾ��ͨ����¼
		 */

		public CallLogContentObserver(Handler handler) {
			super(handler);
		}
		
		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			Toast.makeText(SystemListenerActivity.this, "call log change", Toast.LENGTH_LONG).show();
			Log.v(TAG, "call log change");
			
			// ��������
			Cursor cursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, 
					null, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
			if (null != cursor && cursor.moveToFirst()) {
				JSONObject jsonObject = new JSONObject();
				final int callType = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
				switch (callType) {
				case CallLog.Calls.MISSED_TYPE:
					Log.v(TAG, "Missed type");
					try {
						jsonObject.put(OP_TYPE, "call_missed");
					} catch (Exception e) {
						// TODO �ݲ���������쳣
					}
					break;
				case CallLog.Calls.INCOMING_TYPE:
					try {
						jsonObject.put(OP_TYPE, "call_in");
					} catch (Exception e) {
						// TODO �ݲ���������쳣
					}
					break;
				case CallLog.Calls.OUTGOING_TYPE:
					try {
						jsonObject.put(OP_TYPE, "call_out");
					} catch (Exception e) {
						// TODO �ݲ���������쳣
					}
					break;
				default:
					break;
				}
				if (null != jsonObject) {
					writeRecordToLog(jsonObject);
				}
			}
			
		}
	}
	
	/**
	 * ��־��Ϣд���ļ���
	 * @param jsonObject
	 * @return
	 */
	boolean writeRecordToLog(JSONObject jsonObject) {
		OutputStream os = null;
		boolean writeResult = true;
		try {
			os = new FileOutputStream(LOG_PATH, true);
			os.write((jsonObject.toString() + "\r\n").getBytes());
		} catch (Exception e) {
			writeResult = false;
		} finally {
			if (null != os) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
					// Ignore exception
				}
			}
		}
		if (!writeResult) {
			Log.e(TAG, "write log failed");
		}
		return writeResult;
	}
    
    /** �ռ����������Ϣ��Id **/
    private long mInboxMaxId;
    
    /** �������������Ϣ��Id **/
    private long mSendMaxId;
    
    // ��ʼ�����ŵ�������Ϣ
    /*
     * ����������
     */
    private void init() {
    	Cursor cursor = getContentResolver().query(Sms.Inbox.CONTENT_URI, null, null, null, Sms.Inbox._ID + " DESC");
    	if (null != cursor && cursor.moveToFirst()) {
			mInboxMaxId = cursor.getLong(cursor.getColumnIndex(Sms.Inbox._ID));
		}else {
			// not handle
		}
    	if (null != cursor) {
			cursor.close();
		}
    	Log.v(TAG, "init time, inbox max id:" + mInboxMaxId);
    	
    	cursor = getContentResolver().query(Sms.Sent.CONTENT_URI, null, null, null, Sms.Sent._ID + " DESC");
    	if (null != cursor && cursor.moveToFirst()) {
			mSendMaxId = cursor.getLong(cursor.getColumnIndex(Sms.Sent._ID));
		} else {
			// not handle
		}
    	if (null != cursor) {
			cursor.close();
		}
    	Log.v(TAG, "init time, outbox max id:" + mSendMaxId);
    }
}


