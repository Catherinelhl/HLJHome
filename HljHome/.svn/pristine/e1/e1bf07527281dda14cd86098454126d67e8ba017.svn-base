package com.hlj.utils;

import com.live.video.constans.HomeConstants;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefrenceHandler {

	SharedPreferences settings;

	static Context mContext;

	public static final String PREFS_DOWNLOAD_TARGET = "download_target";

	public static final String PREFS_DOWNLOAD_POSITION = "download_position";

	public static final String LIVE_VERSION = "live_version";

	public PrefrenceHandler(Context context) {
		this.settings = PreferenceManager.getDefaultSharedPreferences(context);
		mContext = context;
	}

	public String getBaseIP() {
		return this.settings.getString("BASEIP", "");
	}

	public void setBaseIP(String baseIp) {
		this.settings.edit().putString("BASEIP", baseIp).commit();
	}

	public static String getGradeStr(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				"settingGrade", context.MODE_PRIVATE);
		String currentGrade = preferences.getString("grade", "一年级");
		return currentGrade;
	}

	public static String getTermStr(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				"settingGrade", context.MODE_PRIVATE);
		String currentTerm = preferences.getString("term", "上学期");
		return currentTerm;
	}

	public static String getIsSetStr(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				"settingGrade", context.MODE_PRIVATE);
		String isSet = preferences.getString("isSet", "false");
		return isSet;
	}

	public static String getLanguageId(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				"settingGrade", context.MODE_PRIVATE);
		String languageId = preferences.getString("languageId", "");
		return languageId;
	}

	public static String getMathId(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				"settingGrade", context.MODE_PRIVATE);
		String mathId = preferences.getString("mathId", "");
		return mathId;
	}

	public static String getEnglishId(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				"settingGrade", context.MODE_PRIVATE);
		String englishId = preferences.getString("englishId", "");
		return englishId;
	}

	public static String getServerAddress(Context context) {
		String address = context.getSharedPreferences("settingServer",
				context.MODE_PRIVATE).getString("SERVER",
				"http://apidev.itvyun.com/");
		return address;
	}

	public static String getInfos(Context context) {
		String infos = context.getSharedPreferences("aboutus",
				context.MODE_PRIVATE).getString("infos", "");
		return infos;
	}

	public static String getContract(Context context) {
		String contract = context.getSharedPreferences("aboutus",
				context.MODE_PRIVATE).getString("contract", "");
		return contract;
	}

	public void setLiveVersion(int version) {
		setInt(LIVE_VERSION, version);
	}

	public int getLiveVersion() {
		return settings.getInt(LIVE_VERSION, 1);
	}

	public void setDownloadTarget(String targetFile) {
		setString(PREFS_DOWNLOAD_TARGET, targetFile);
	}

	private void setString(String key, String str) {
		SharedPreferences.Editor mEditor = settings.edit();
		mEditor.putString(key, str);
		mEditor.commit();
	}

	public String getDownloadTarget() {
		return settings.getString(PREFS_DOWNLOAD_TARGET, "");
	}

	public void setDownLoadPos(long position) {
		setLong(PREFS_DOWNLOAD_POSITION, position);
	}

	private void setLong(String key, long Long) {
		SharedPreferences.Editor mEditor = settings.edit();
		mEditor.putLong(key, Long);
		mEditor.commit();
	}

	private void setInt(String key, int num) {
		SharedPreferences.Editor mEditor = settings.edit();
		mEditor.putInt(key, num);
		mEditor.commit();
	}

	public long getDownloadPos() {
		return settings.getLong(PREFS_DOWNLOAD_POSITION, 0);
	}

	/**
	 * 获取临时锁状态
	 * 
	 * @param context
	 * @return
	 */
	public static String getHomeTempLock(Context context) {
		String lock = context.getSharedPreferences("setLOCK",
				Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE)
				.getString("isTempLock", HomeConstants.isTempLock);
		return lock;
	}

	/**
	 * 获取永久锁状态
	 * 
	 * @param context
	 * @return
	 */
	public static String getHomePrimaryLock(Context context) {
		String lock = context.getSharedPreferences("setLOCK",
				Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE)
				.getString("isPrimaryLock", HomeConstants.isPrimaryLock);
		return lock;
	}

}
