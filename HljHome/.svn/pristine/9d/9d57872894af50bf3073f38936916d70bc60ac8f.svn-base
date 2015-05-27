package com.live.video.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created with IntelliJ IDEA.
 * User: ${shime}
 * Date: 13-8-26
 * Time: 下午1:15
 * To change this template use File | Settings | File Templates.
 */
public class DBHelperWeather extends SQLiteOpenHelper {
	public static final String DTATABANSENAME = "weather.db";
	private static final int VERSION = 1;
	private SQLiteDatabase mDB = null;
	public DBHelperWeather(Context context) {
		super(context, DTATABANSENAME, null, VERSION);
		mDB=this.getReadableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql = " create table if not exists provinces (" + " _id varchar(10) primary key, "
				+ " name varchar(50) not null "
				+ ")";
		//创建数据库表
		db.execSQL(sql);
		String sql1 = " create table if not exists citys (" + " _id varchar(10) primary key, "
				+" provinceid varchar(10) not null ,"
				+ " name varchar(50) not null "
				+ " )" ;
		db.execSQL(sql1);

	}
	/**
	 * 当检测与前一次创建数据库版本不一样时，先删除表再创建新表
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String sql=" drop table if exists provinces ";
		String sql1=" drop table if exists citys ";
		db.execSQL(sql);
		db.execSQL(sql1);
		this.onCreate(db);
	}
}
