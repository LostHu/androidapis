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
	
	/* 写日记文件相关  */
	public static final String LOG_PATH = "/sdcard/userlog.txt";
	public static final String OP_TYPE = "op_type";
	
	private Handler mMainHandler = new Handler();
	
	private final Uri SMS_CONTENT_URI = Uri.parse("content://sms/");
	
	// 监听器,在onDestroy()要移除监听
	SmsContentObserver mSmsContentObserver = new SmsContentObserver(mMainHandler);
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
		
		// 注册Contacts的ContentObserver
		getContentResolver().registerContentObserver(ContactsContract.Contacts.CONTENT_URI, 
				false, new ContactContentObserver(mMainHandler));
		
		// 注册短消息的ContentObserver
		getContentResolver().registerContentObserver(SMS_CONTENT_URI, 
				true, mSmsContentObserver);
		
		// 注册彩信的ContentObserver
//		getContentResolver().registerContentObserver(MmsSms.CONTENT_URI, 
//				true, new MmsContentObserver(mMainHandler));
		
		
		// 注册接收到短消息, 这样监听不到
		getContentResolver().registerContentObserver(Sms.Inbox.CONTENT_URI, 
				true, new SmsReceiveContentObserver(mMainHandler));
		
		// 注册发出短消息, 这样监听不到
		getContentResolver().registerContentObserver(Sms.Outbox.CONTENT_URI, 
				true, new SmsSendContentObserver(mMainHandler));
		
		
		// call log
		getContentResolver().registerContentObserver(CallLog.Calls.CONTENT_URI, 
				true, new CallLogContentObserver(mMainHandler));
	}
	
	@Override
	protected void onDestroy() {
		// TODO 移除所有注册的ContentObserver
		getContentResolver().unregisterContentObserver(mSmsContentObserver);
		super.onDestroy();
	}
	
	
	/** 联系人和通信记录的ContentObserver **/
	/*
	 * 新建联系人,添加到手机或sim卡都有回调
	 * 删除联系人,删除手机上或sim卡上都有回调
	 * 修改联系人,修改手机上或sim卡上都有回调
	 * 
	 * 呼出电话(挂断后)有回调(两次都未接通)
	 * 呼入电话(挂断后)有回调(两次都未接通)
	 * 删除通话记录**********没有回调
	 */
	class ContactContentObserver extends ContentObserver {
		public ContactContentObserver(Handler handler) {
			super(handler);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			Toast.makeText(SystemListenerActivity.this, "联系人相关已被修改", Toast.LENGTH_LONG).show();
			Log.v(TAG, "联系人相关已被修改");
		}
	}
	
	/** 短信的ContentObserver **/
	class SmsContentObserver extends ContentObserver {
		/*
		 * 处理原则
		 */
		
		
		
		/*
		 * 监听接收到的信息,保存到日记的格式如下：
		 * {"op_type":"sms_in","sender":"","body":"","send_time":""}
		 * 
		 */
		
		public SmsContentObserver(Handler handler) {
			super(handler);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			Toast.makeText(SystemListenerActivity.this, "短信相关已被修改", Toast.LENGTH_LONG).show();
			Log.v(TAG, "短信相关已被修改");
			//TODO 有可能是通话记录相关ContentObserver,所以要以区别
			/*
			 * 下面的处理是不合理的,原因是所有的短信并不是存在一个表中,获取到信息的id可知
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
//						// TODO 暂不作处理
//					}
//					break;
//				case Sms.MESSAGE_TYPE_OUTBOX:
//					Log.v(TAG, "MESSAGE_TYPE_OUTBOX");
//					try {
//						jsonObject.put(OP_TYPE, "MESSAGE_TYPE_OUTBOX");
//					} catch (JSONException e) {
//						e.printStackTrace();
//						// TODO 暂不作处理
//					}
//					break;
//				case Sms.MESSAGE_TYPE_DRAFT:
//					// 草稿箱,存进草稿箱
//					Log.v(TAG, "MESSAGE_TYPE_DRAFT");
//					try {
//						jsonObject.put(OP_TYPE, "MESSAGE_TYPE_DRAFT");
//					} catch (JSONException e) {
//						e.printStackTrace();
//						// TODO 暂不作处理
//					}
//					break;
//				case Sms.MESSAGE_TYPE_SENT:
//					Log.v(TAG, "MESSAGE_TYPE_SENT");
//					try {
//						jsonObject.put(OP_TYPE, "MESSAGE_TYPE_SENT");
//					} catch (JSONException e) {
//						e.printStackTrace();
//						// TODO 暂不作处理
//					}
//					break;
//				case Sms.MESSAGE_TYPE_QUEUED:
//					Log.v(TAG, "MESSAGE_TYPE_QUEUED");
//					try {
//						jsonObject.put(OP_TYPE, "MESSAGE_TYPE_QUEUED");
//					} catch (JSONException e) {
//						e.printStackTrace();
//						// TODO 暂不作处理
//					}
//					break;
//				default:
//					break;
//				}
			JSONObject inJsonObject = null;
			JSONObject sendJsonObject = null;
			boolean inChange = false; // 收到信息
			boolean sendChange = false; // 发出信息
			
			// 检查收件箱是否有新的消息
			Cursor cursor = getContentResolver().query(Sms.Inbox.CONTENT_URI, null, 
					Sms.Inbox._ID + ">?", new String[]{String.valueOf(mInboxMaxId)}, Sms.Inbox.DEFAULT_SORT_ORDER);
			
			if (null != cursor && cursor.moveToFirst()) {
				if (1 == cursor.getCount()) {
					String smsId = cursor.getString(cursor.getColumnIndex(Sms.Inbox._ID));
					String smsBody = cursor.getString(cursor.getColumnIndex(Sms.Inbox.BODY));
					String smsAddress = cursor.getString(cursor.getColumnIndex(Sms.Inbox.ADDRESS));
					String smsDate = "" + new Date(cursor.getLong(cursor.getColumnIndex(Sms.Inbox.DATE)));
					
					Log.v(TAG, "收到新短信, smsId: " + smsId + ", smsBody:" + smsBody + ", smsSender: " + smsAddress + ", smsDate: " + smsDate);
					// TODO 写到日志里
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
					// TODO 在init()方法之后,用户更改过数据库,暂不作处理
					Log.e(TAG, "在init()方法之后,用户更改过数据库");
				}
			}
			if (null != cursor) {
				cursor.close();
			}
			
			// 检查发件箱是否有新的消息
			cursor = getContentResolver().query(Sms.Sent.CONTENT_URI, null, 
					Sms.Inbox._ID + ">?", new String[]{String.valueOf(mSendMaxId)}, Sms.Sent.DEFAULT_SORT_ORDER);
			
			if (null != cursor && cursor.moveToFirst()) {
				if (1 == cursor.getCount()) {
					String smsId = cursor.getString(cursor.getColumnIndex(Sms.Sent._ID));
					String smsBody = cursor.getString(cursor.getColumnIndex(Sms.Sent.BODY));
					String smsAddress = cursor.getString(cursor.getColumnIndex(Sms.Sent.ADDRESS));
					String smsDate = "" + new Date(cursor.getLong(cursor.getColumnIndex(Sms.Sent.DATE)));
					
					Log.v(TAG, "发出短信, smsId: " + smsId + ", smsBody:" + smsBody + ", smsSender: " + smsAddress + ", smsDate: " + smsDate);
					// TODO 写到日志里
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
					// TODO 在init()方法之后,用户更改过数据库,暂不作处理
					Log.e(TAG, "在init()方法之后,用户更改过数据库");
				}
			}
			if (null != cursor) {
				cursor.close();
			}
			if (inChange && sendChange) {
				Log.e(TAG, "收件箱和发件箱都有新的消息");
			}else if (!inChange && !sendChange) {
				Log.v(TAG, "除收件箱或发件箱之外,其它箱有更新");
			} 
		}
	}
	
	/** 彩信的ContentObserver **/
	class MmsContentObserver extends ContentObserver {

		public MmsContentObserver(Handler handler) {
			super(handler);
		}
		
		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			Toast.makeText(SystemListenerActivity.this, "彩信到来", Toast.LENGTH_LONG).show();
			Log.v(TAG, "彩信到来");
		}
	}
	
	// 短消息接收的ContentObserver
	@Deprecated
	class SmsReceiveContentObserver extends ContentObserver {
		public SmsReceiveContentObserver(Handler handler) {
			super(handler);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			Toast.makeText(SystemListenerActivity.this, "有短消息到来", Toast.LENGTH_LONG).show();
			Log.v(TAG, "有短消息到来");
			Cursor cursor = getContentResolver().query(Telephony.Sms.Inbox.CONTENT_URI, null, null, null, Sms.DEFAULT_SORT_ORDER);
			
			if (null != cursor && cursor.moveToFirst()) {
				JSONObject jsonObject = new JSONObject();
				try {
					jsonObject.put(OP_TYPE, "sms_in");
				} catch (JSONException e) {
					e.printStackTrace();
					//TODO 暂不处理
				}
				if (null != jsonObject) {
					writeRecordToLog(jsonObject);
				}
			}
		}
	}
	
	// 短消息发送的ContentObserver
	@Deprecated
	class SmsSendContentObserver extends ContentObserver {
		public SmsSendContentObserver(Handler handler) {
			super(handler);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			Toast.makeText(SystemListenerActivity.this, "有短消息发出", Toast.LENGTH_LONG).show();
			Log.v(TAG, "有短消息发出");
			Cursor cursor = getContentResolver().query(Telephony.Sms.Outbox.CONTENT_URI, null, null, null, Sms.DEFAULT_SORT_ORDER);
			if (null != cursor && cursor.moveToFirst()) {
				JSONObject jsonObject = new JSONObject();
				try {
					jsonObject.put(OP_TYPE, "sms_out");
				} catch (JSONException e) {
					e.printStackTrace();
					//TODO 暂不处理
				}
				if (null != jsonObject) {
					writeRecordToLog(jsonObject);
				}
			}
		}
	}
	
	
	
	// 通话记录的contentObserver
	class CallLogContentObserver extends ContentObserver {
		/*
		 * finish:
		 * 接收到来电、去电
		 * TODO
		 * 删除通话记录
		 */

		public CallLogContentObserver(Handler handler) {
			super(handler);
		}
		
		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			Toast.makeText(SystemListenerActivity.this, "call log change", Toast.LENGTH_LONG).show();
			Log.v(TAG, "call log change");
			
			// 处理来电
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
						// TODO 暂不处理添加异常
					}
					break;
				case CallLog.Calls.INCOMING_TYPE:
					try {
						jsonObject.put(OP_TYPE, "call_in");
					} catch (Exception e) {
						// TODO 暂不处理添加异常
					}
					break;
				case CallLog.Calls.OUTGOING_TYPE:
					try {
						jsonObject.put(OP_TYPE, "call_out");
					} catch (Exception e) {
						// TODO 暂不处理添加异常
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
	 * 日志信息写到文件里
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
    
    /** 收件箱内最大信息的Id **/
    private long mInboxMaxId;
    
    /** 发件箱内最大信息的Id **/
    private long mSendMaxId;
    
    // 初始化短信的配置信息
    /*
     * 包括发信箱
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


