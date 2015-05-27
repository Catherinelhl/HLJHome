package com.hlj.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class UpdateService extends Service {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		initInstance();
	}

	private static ThreadTask mCheckingTask;

	private static ThreadTask mDownloadTask;

	private void initInstance() {
		if (mCheckingTask == null) {
			mCheckingTask = new CheckingTask(this);
		}

		if (mDownloadTask == null) {
			mDownloadTask = new DownloadTask(this);
		}
	}

	public class CheckBinder extends Binder {

	}

	private CheckBinder mBinder = new CheckBinder();

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}

}
