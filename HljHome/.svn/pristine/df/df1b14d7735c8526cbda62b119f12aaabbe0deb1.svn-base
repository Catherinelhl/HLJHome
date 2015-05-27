package com.live.video.db;

import java.util.ArrayList;

import com.hlj.utils.CommonTools;
import com.hlj.utils.PrefrenceHandler;
import com.hlj.utils.StringTools;
import com.live.video.constans.TVInfo;

import android.R.integer;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	public static String DBName = "HljTvInfo.db";

	public static String cataTableName = "hljcata";

	public static String channelTableName = "hljchannel";

	public static String tvlistTableName = "hljtvlist";

	public static String tvversionTableName = "hljtvversion";

	private SQLiteDatabase mDB = null;

	public static int version = 1;

	static PrefrenceHandler prefrenceHandler;

	public static int getVersion(Context context) {

		if (prefrenceHandler == null) {
			prefrenceHandler = new PrefrenceHandler(context);
			version = prefrenceHandler.getLiveVersion();
		}

		return version;
	}

	public static void setVersion(int newVersion) {
		version = newVersion;
	}

	private Context mContext;

	public DBHelper(Context context) {
		super(context, DBName, null, getVersion(context));
		mDB = this.getReadableDatabase();
	}

	String sql1 = "CREATE TABLE IF NOT EXISTS " + cataTableName
			+ " ( type INT NOT NULL , name TEXT );";
	String sql2 = "CREATE TABLE IF NOT EXISTS " + channelTableName
			+ " ( channelId INT NOT NULL , channelName TEXT );";
	String sql3 = "CREATE TABLE IF NOT EXISTS "
			+ tvlistTableName
			+ " ( id INT , title TEXT NOT NULL ,url TEXT NOT NULL ,mode INT ,isCollected INT ,isDefault INT ,type TEXT ,channelID INT ,epgurl TEXT ,tvid INT );";
	String sql4 = "CREATE TABLE IF NOT EXISTS " + tvversionTableName
			+ " ( version TEXT );";

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.v(CommonTools.TAG, "SQLitehelper onCreate!");
		// 随意创建

		// 创建数据库表
		db.execSQL(sql1);
		db.execSQL(sql2);
		db.execSQL(sql3);
		db.execSQL(sql4);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("DROP TABLE IF EXISTS hljtvversion");
		db.execSQL("DROP TABLE IF EXISTS hljcata");
		db.execSQL("DROP TABLE IF EXISTS hljchannel");
		db.execSQL("DROP TABLE IF EXISTS hljtvlist");

		onCreate(db);
	}

}
