package com.live.video.db;

import java.util.ArrayList;

import com.hlj.utils.CommonTools;
import com.hlj.utils.Logger;
import com.live.video.constans.ChannelInfo;
import com.live.video.constans.LiveInfo;
import com.live.video.constans.TVInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TvBackDbOperator {

	private static TvBackDbHelper dbHelper;

	public TvBackDbOperator(Context context) {
		dbHelper = new TvBackDbHelper(context);
	}

	/**
	 * 插入频道列表
	 * 
	 * @param list
	 */
	public static void insertBackChannelInfo(ArrayList<ChannelInfo> list) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.beginTransaction();
		try {
			ContentValues contentValue = new ContentValues();
			for (int i = 0; i < list.size(); i++) {
				ChannelInfo info = list.get(i);
				contentValue.put("channel", info.channel);
				contentValue.put("name", info.name);
				db.insert(TvBackDbHelper.tvBackTableName, null, contentValue);
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
	 * 查出所有的频道列表
	 * 
	 * @param channelId
	 * @return
	 */
	public static ArrayList<ChannelInfo> getChannelInfo() {
		ArrayList<ChannelInfo> list = new ArrayList<ChannelInfo>();

		String str = "select * from " + TvBackDbHelper.tvBackTableName;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(str, null);
		Log.v(CommonTools.TAG, "count:" + cursor.getCount());
		if (cursor != null && cursor.getCount() > 0) {
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
					.moveToNext()) {
				ChannelInfo info = new ChannelInfo();
				info.channel = cursor.getString(0);
				info.name = cursor.getString(1);
				list.add(info);
			}
			cursor.close();
		}
		return list;
	}

}
