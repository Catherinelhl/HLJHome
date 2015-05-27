package org.videolan.utils;

import android.util.Log;

public class Logger {

	public static void log(String log) {
		// if (BuildConfig.DEBUG )
		Log.d("TV_DB", log);
	}

	public static void logError(String log) {
		// if (BuildConfig.DEBUG )
		Log.e("TV_DB", log);
	}

	public static void logWarning(String log) {
		// if (BuildConfig.DEBUG )
		Log.w("TV_DB", log);
	}

	public static void logVerbose(String log) {
		// if (BuildConfig.DEBUG )
		Log.v("TV_DB", log);
	}

	public static void logInfo(String log) {
		// if (BuildConfig.DEBUG )
		Log.i("TV_DB", log);
	}

}
