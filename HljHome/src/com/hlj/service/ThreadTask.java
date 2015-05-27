package com.hlj.service;

public class ThreadTask implements Runnable {

	public static final int NO_ERROR = 0;

	public static final int RUNNING_STATUS_UNSTART = 0;

	public static final int RUNNING_STATUS_RUNNING = 1;

	public static final int RUNNING_STATUS_PAUSE = 2;

	public static final int RUNNING_STATUS_FINISH = 3;

	protected Thread mThread;

	protected int mProgress = 0;

	protected int mErrorCode = NO_ERROR;

	protected Object mResult;

	protected int mRunningStatus = RUNNING_STATUS_UNSTART;

	// ----------------Runtime Method

	public void start() {
		if (mRunningStatus != RUNNING_STATUS_RUNNING) {
			mRunningStatus = RUNNING_STATUS_RUNNING;
			mThread = new Thread(this);
			mThread.start();
		}
		onStart();
	}

	protected void onStart() {
	}

	@Override
	public void run() {
		onRunning();
		stop();
	}

	public void stop() {
		if (mRunningStatus != RUNNING_STATUS_UNSTART) {
			onStop();
			mRunningStatus = RUNNING_STATUS_FINISH;
		}
	}

	protected void onStop() {
	}

	protected void onRunning() {
	}

	public void resume() {
		if (mThread == null || !mThread.isAlive()) {
			start();
		}
		mRunningStatus = RUNNING_STATUS_RUNNING;
		onResume();
	}

	protected void onResume() {
	}

	public void pause() {
		mRunningStatus = RUNNING_STATUS_PAUSE;
		onPause();
	}

	protected void onPause() {
	}

	public boolean reset() {
		if (mRunningStatus == RUNNING_STATUS_FINISH
				|| mRunningStatus == RUNNING_STATUS_PAUSE
				|| mRunningStatus == RUNNING_STATUS_RUNNING) {
			mProgress = 0;
			mRunningStatus = RUNNING_STATUS_UNSTART;
			return true;
		}

		return false;
	}

	// -----------------Public Method

	public int getErrorCode() {
		return mErrorCode;
	}

	public int getRunningStatus() {
		return mRunningStatus;
	}

	public int getProgress() {
		return mProgress;
	}

	public Object getResult() {
		return mResult;
	}

}
