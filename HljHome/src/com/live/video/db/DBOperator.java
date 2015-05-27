package com.live.video.db;

import java.util.ArrayList;

import com.hlj.utils.CommonTools;
import com.hlj.utils.Logger;
import com.live.video.constans.CataInfo;
import com.live.video.constans.ChannelInfo;
import com.live.video.constans.LiveInfo;
import com.live.video.constans.TVInfo;

import android.R.anim;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Contacts.Intents.Insert;
import android.util.Log;

public class DBOperator {

	private static DBHelper dbHelper;

	public DBOperator(Context context) {
		dbHelper = new DBHelper(context);
	}

	/**
	 * 插入版本
	 * 
	 * @param list
	 */
	public static void insertLiveVersion(String version) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.beginTransaction();
		try {
			ContentValues contentValue = new ContentValues();
			contentValue.put("version", version);
			db.insert(DBHelper.tvversionTableName, null, contentValue);
			db.setTransactionSuccessful();
			db.endTransaction();
		} catch (Exception e) {
			Logger.log(e.toString());
			db.endTransaction();
		} finally {
			// db.endTransaction();
		}

	}

	/**
	 * 插入所有分类名
	 * 
	 * @param list
	 */
	public static void insertLiveCats(ArrayList<LiveInfo> list) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.beginTransaction();
		try {
			ContentValues contentValue = new ContentValues();
			for (int i = 0; i < list.size(); i++) {
				LiveInfo info = list.get(i);
				contentValue.put("type", info.type);
				contentValue.put("name", info.name);
				db.insert(DBHelper.cataTableName, null, contentValue);
			}
			db.setTransactionSuccessful();
			db.endTransaction();
		} catch (Exception e) {
			Logger.log(e.toString());
			db.endTransaction();
		} finally {
			db.endTransaction();
		}

	}

	/**
	 * 插入所有源的名
	 * 
	 * @param list
	 */
	public static void insertLiveChannels(ArrayList<LiveInfo> list) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.beginTransaction();
		try {
			ContentValues contentValue = new ContentValues();
			for (int i = 0; i < list.size(); i++) {
				LiveInfo info = list.get(i);
				contentValue.put("channelId", info.channelId);
				contentValue.put("channelName", info.channelName);
				db.insert(DBHelper.channelTableName, null, contentValue);
			}
			db.setTransactionSuccessful();
			db.endTransaction();
		} catch (Exception e) {
			Logger.log(e.toString());
			db.endTransaction();
		} finally {
			db.endTransaction();
		}

	}

	/**
	 * 插入所有频道
	 * 
	 * @param list
	 */
	public static void insertTVLists(ArrayList<LiveInfo> list) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.beginTransaction();
		try {
			ContentValues contentValue = new ContentValues();
			for (int i = 0; i < list.size(); i++) {
				LiveInfo info = list.get(i);
				contentValue.put("id", info.id);
				contentValue.put("title", info.title);
				contentValue.put("url", info.url);
				contentValue.put("mode", info.mode);
				contentValue.put("isCollected", info.isCollected);
				contentValue.put("isDefault", info.isDefault);
				contentValue.put("type", info.type);
				contentValue.put("channelID", info.channelID);
				contentValue.put("epgurl", info.epgurl);
				contentValue.put("tvid", info.tvid);
				db.insert(DBHelper.tvlistTableName, null, contentValue);
			}
			db.setTransactionSuccessful();
			db.endTransaction();
		} catch (Exception e) {
			Logger.log(e.toString());
			db.endTransaction();
		} finally {
			db.endTransaction();
		}

	}

	/**
	 * 由频道返回视频列表
	 * 
	 * @param channelId
	 * @return
	 */
	public static ArrayList<TVInfo> getTvInfoByChannel(int channelId) {
		ArrayList<TVInfo> list = new ArrayList<TVInfo>();

		String str = "select * from " + DBHelper.tvlistTableName
				+ " where channelID=" + channelId;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(str, null);
		Log.v(CommonTools.TAG, "count:" + cursor.getCount());
		if (cursor != null && cursor.getCount() > 0) {
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
					.moveToNext()) {
				TVInfo info = new TVInfo();
				info.id = cursor.getInt(0);
				info.title = cursor.getString(1);
				info.url = cursor.getString(2);
				info.mode = cursor.getInt(3);
				info.isCollected = cursor.getInt(4);
				info.isSelfDefine = 1;
				info.type = cursor.getString(6);
				info.channelID = cursor.getInt(7);
				info.epgUrl = cursor.getString(8);
				list.add(info);
			}
			cursor.close();
		}
		return list;
	}

	/**
	 * 由类型返回视频列表
	 * 
	 * @param channelId
	 * @return
	 */
	public static ArrayList<TVInfo> getTvInfoByType(int type) {
		ArrayList<TVInfo> list = new ArrayList<TVInfo>();

		String str = "select * from " + DBHelper.tvlistTableName
				+ " where type=" + type + " and isDefault=1";
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(str, null);
		Log.v(CommonTools.TAG, "count:" + cursor.getCount());
		if (cursor != null && cursor.getCount() > 0) {
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
					.moveToNext()) {
				TVInfo info = new TVInfo();
				info.id = cursor.getInt(0);
				info.title = cursor.getString(1);
				info.url = cursor.getString(2);
				info.mode = cursor.getInt(3);
				info.isCollected = cursor.getInt(4);
				info.isSelfDefine = 1;
				info.type = cursor.getString(6);
				info.channelID = cursor.getInt(7);
				info.epgUrl = cursor.getString(8);
				list.add(info);
			}
			cursor.close();
		}
		return list;
	}

	/**
	 * 由title返回视频列表
	 * 
	 * @param channelId
	 * @return
	 */
	public static ArrayList<TVInfo> getTvInfoByTitle(String title) {
		ArrayList<TVInfo> list = new ArrayList<TVInfo>();

		String str = "select * from " + DBHelper.tvlistTableName
				+ " where title='" + title + "'";
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(str, null);
		Log.v(CommonTools.TAG, "count:" + cursor.getCount());
		if (cursor != null && cursor.getCount() > 0) {
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
					.moveToNext()) {
				TVInfo info = new TVInfo();
				info.id = cursor.getInt(0);
				info.title = cursor.getString(1);
				info.url = cursor.getString(2);
				info.mode = cursor.getInt(3);
				info.isCollected = cursor.getInt(4);
				info.isSelfDefine = 1;
				info.type = cursor.getString(6);
				info.channelID = cursor.getInt(7);
				info.epgUrl = cursor.getString(8);
				list.add(info);
			}
			cursor.close();
		}
		return list;
	}

	/**
	 * 由类型返回视频列表
	 * 
	 * @param channelId
	 * @return
	 */
	public static CataInfo getCataInfoByType(int type) {
		CataInfo info = new CataInfo();

		String str = "select * from " + DBHelper.cataTableName + " where type="
				+ type;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(str, null);
		Log.v(CommonTools.TAG, "count:" + cursor.getCount());
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			info.type = cursor.getInt(0);
			info.name = cursor.getString(1);
			// info.isDelete = cursor.getInt(2);
		}
		cursor.close();
		return info;
	}

	/**
	 * 由类型返回视频列表
	 * 
	 * @param channelId
	 * @return
	 */
	public static ChannelInfo getChannelByIndex(int channelId) {
		ChannelInfo info = new ChannelInfo();

		String str = "select * from " + DBHelper.channelTableName
				+ " where channelId=" + channelId;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(str, null);
		Log.v(CommonTools.TAG, "count:" + cursor.getCount());
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			info.index = cursor.getInt(0);
			info.name = cursor.getString(1);
		}
		cursor.close();
		return info;
	}

	public static TVInfo getTvInfoByTypeAndDefault(int type) {
		TVInfo info = new TVInfo();

		String str = "select * from " + DBHelper.tvlistTableName
				+ " where type=" + type + " and isDefault=1";
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(str, null);
		Log.v(CommonTools.TAG, "count:" + cursor.getCount());
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			info.id = cursor.getInt(0);
			info.title = cursor.getString(1);
			info.url = cursor.getString(2);
			info.mode = cursor.getInt(3);
			info.isCollected = cursor.getInt(4);
			info.isSelfDefine = 1;
			info.type = cursor.getString(6);
			info.channelID = cursor.getInt(7);
			info.epgUrl = cursor.getString(8);
		}
		cursor.close();
		return info;
	}

	public static TVInfo getTvInfoByTitleAndDefault(String title) {
		TVInfo info = new TVInfo();

		String str = "select * from " + DBHelper.tvlistTableName
				+ " where title='" + title + "' and isDefault=1";
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(str, null);
		Log.v(CommonTools.TAG, "count:" + cursor.getCount());
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			info.id = cursor.getInt(0);
			info.title = cursor.getString(1);
			info.url = cursor.getString(2);
			info.mode = cursor.getInt(3);
			info.isCollected = cursor.getInt(4);
			info.isSelfDefine = 1;
			info.type = cursor.getString(6);
			info.channelID = cursor.getInt(7);
			info.epgUrl = cursor.getString(8);
		}
		cursor.close();
		return info;
	}

	/**
	 * 由id和类型确定获取节目列表的url的一部分
	 * 
	 * @param channelId
	 * @return
	 */
	public static TVInfo getTVInfoByIdAndType(int id, int type) {
		TVInfo info = new TVInfo();

		String str = "select * from " + DBHelper.tvlistTableName + " where id="
				+ id + " and type=" + type;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(str, null);
		Log.v(CommonTools.TAG, "count:" + cursor.getCount());
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			info.id = cursor.getInt(0);
			info.title = cursor.getString(1);
			info.url = cursor.getString(2);
			info.mode = cursor.getInt(3);
			info.isCollected = cursor.getInt(4);
			info.isSelfDefine = 1;
			info.type = cursor.getString(6);
			info.channelID = cursor.getInt(7);
			info.epgUrl = cursor.getString(8);
		}
		cursor.close();
		return info;
	}

	/**
	 * 查询版本
	 * 
	 * @param channelId
	 * @return
	 */
	public static String getLiveVersion() {
		String version = "";

		String str = "select * from " + DBHelper.tvversionTableName;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(str, null);
		Log.v(CommonTools.TAG, "count:" + cursor.getCount());
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			version = cursor.getString(0);
		}
		cursor.close();
		return version;
	}

}
