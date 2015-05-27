package com.hlj.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.View;

public class TimeMannager {
	private static TimeMannager instance;
	private int longs = 1000;// ---
	private boolean isRuning = false;

	private CountThreads thread;
	boolean isalive = false;

	Context c;

	View view;

	public void updateView(View view) {
		this.view = view;
	}

	private TimeMannager() {
		thread = new CountThreads();
	}

	public static TimeMannager getinstance() {
		if (instance == null) {
			instance = new TimeMannager();
		}
		return instance;
	}

	public void startCount(Context c) {
		this.c = c;
		if (thread != null) {
			isRuning = false;
			isalive = true;
			count = 10;
			thread.execute("");
		}
	}

	public void reseam() {
		isRuning = true;
		// thread.notify();
		// thread.resume();

	}

	public void reseam1() {
		isRuning = true;
		// thread.notify();
		// thread.resume();
		count = 10;
	}

	public void pause1() {

		// try {
		// thread.wait();
		isRuning = false;
		count = 10;
	}

	public void pause() {

		// try {
		// thread.wait();
		isRuning = false;
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// thread.run();
	}

	public void distory() {
		isRuning = false;
		isalive = false;
		count = 10;
	}

	public class CountThread extends Thread {

		@Override
		public void run() {
			while (isalive) {
				while (isRuning) {

					System.out.println("-----------heart beat");

				}
				try {
					Thread.sleep(longs);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

	}

	int count = 10;

	public class CountThreads extends AsyncTask<Object, Object, Object> {

		@Override
		protected Object doInBackground(Object... params) {
			// TODO Auto-generated method stub
			if (isalive) {
				if (isRuning && count > 0) {
					count--;
					System.out.println("<------count--->" + count);
					if (count == 0) {
						handler.sendEmptyMessage(1);
					}
					try {
						Thread.sleep(longs);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				try {
					Thread.sleep(longs);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			return null;

		}
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			view.setVisibility(View.GONE);
		}
	};

	public void refreshCount() {
		isRuning = false;
		count = 10;
		isRuning = true;
	}

}
