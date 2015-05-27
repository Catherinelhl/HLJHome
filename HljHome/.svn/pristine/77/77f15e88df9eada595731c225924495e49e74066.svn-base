package com.live.video.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class TvBackDbHelper extends SQLiteOpenHelper {

	private SQLiteDatabase mDB = null;

	public static String DBName = "HljTvbackInfo.db";

	public static String tvBackTableName = "hljtvbacklist";

	public TvBackDbHelper(Context context) {
		super(context, DBName, null, 1);
		mDB = this.getReadableDatabase();
	}

	String sql1 = "CREATE TABLE IF NOT EXISTS " + tvBackTableName
			+ " ( channel TEXT NOT NULL , name TEXT );";

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 创建数据库表
		db.execSQL(sql1);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("DROP TABLE IF EXISTS hljtvbacklist");
		onCreate(db);
	}

}
