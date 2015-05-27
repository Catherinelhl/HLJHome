package com.hlj.utils;

import android.util.Log;

/**
 * 日志输出类
 * 
 * @author office
 * 
 */
public class Logger {

	public static String TAG = "TV_DB";

	public static boolean isDebug = true;

	public static void v(String msg) {
		if (isDebug)
			Log.v(TAG, buildMessage(msg));

	}

	public static void log(String msg) {
		if (isDebug)
			Log.v(TAG, buildMessage(msg));

	}

	public static void v(String msg, Throwable thr) {
		if (isDebug)
			Log.v(TAG, buildMessage(msg), thr);

	}

	public static void d(String msg) {
		if (isDebug)
			Log.d(TAG, buildMessage(msg));

	}

	public static void d(String msg, Throwable thr) {
		if (isDebug)
			Log.d(TAG, buildMessage(msg), thr);
	}

	public static void i(String msg) {
		Log.i(TAG, buildMessage(msg));
	}

	public static void i(String msg, Throwable thr) {
		Log.i(TAG, buildMessage(msg), thr);
	}

	public static void e(String msg) {
		Log.e(TAG, buildMessage(msg));
	}

	public static void w(String msg) {
		Log.w(TAG, buildMessage(msg));
	}

	public static void w(String msg, Throwable thr) {
		Log.w(TAG, buildMessage(msg), thr);
	}

	public static void w(Throwable thr) {
		Log.w(TAG, buildMessage(""), thr);
	}

	public static void e(String msg, Throwable thr) {
		Log.e(TAG, buildMessage(msg), thr);
	}

	private static String buildMessage(String msg) {
		StackTraceElement caller = new Throwable().fillInStackTrace()
				.getStackTrace()[2];

		String mess = new StringBuilder().append(caller.getClassName())
				.append(".").append(caller.getMethodName()).append("():")
				.append(msg).toString();

		return mess;
	}

}
