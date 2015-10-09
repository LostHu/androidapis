package com.lity.android.apis.aidl;

import java.util.LinkedList;
import java.util.List;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class RemoteService extends Service {

	public static final String TAG = RemoteService.class.getSimpleName();
	
	private LinkedList<String> mPersonsList = new LinkedList<String>();
	@Override
	public IBinder onBind(Intent intent) {
		Log.v(TAG, "onBind method");
		return mBinder;
	}

	private final IMyService.Stub mBinder = new IMyService.Stub(){

		@Override
		public void savePersonInfo(String name) throws RemoteException {
			Log.v(TAG, "savePersonInfo method");
            if (name != null){ 
                mPersonsList.add(name); 
            } 
		}

		@Override
		public List<String> getAllPersons() throws RemoteException {
			Log.v(TAG, "getAllPersons method");
			return mPersonsList;
		}
		
	};
}
