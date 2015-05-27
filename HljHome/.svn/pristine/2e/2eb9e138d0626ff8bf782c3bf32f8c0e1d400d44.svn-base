package com.live.video.db;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import com.live.video.constans.AppBean;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class AppDb extends SQLiteOpenHelper {
	private Context mContext;

	String tableName = "user_app";

	public AppDb(Context context) {
		super(context, "launcherapp.db", null, 1);
		mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table if not exists "
				+ tableName
				+ " ( _id INTEGER primary key autoincrement,packageName text not null,app_image blob not null,data_dir text not null,name text not null,pageIndex INTEGER not null,index_in_dex INTEGER not null)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists " + tableName);
		onCreate(db);
	}

	public boolean recordApp(AppBean appBean, int position) {
		SQLiteDatabase localSQLiteDatabase = getWritableDatabase();

		ContentValues localContentValues = new ContentValues();
		localContentValues.put("packageName", appBean.getPackageName());
		localContentValues.put("app_image", drawableChange(appBean.getIcon()));
		localContentValues.put("name", appBean.getName());
		localContentValues.put("data_dir", appBean.getDataDir());
		localContentValues.put("pageIndex", Integer.valueOf(0));
		localContentValues.put("index_in_dex", Integer.valueOf(position));
		if (localSQLiteDatabase.insert(tableName, null, localContentValues) != -1L) {
			return true;
		} else {
			localSQLiteDatabase.close();
			return false;
		}
	}

	private byte[] drawableChange(Drawable drawable) {
		Bitmap localBitmap = ((BitmapDrawable) drawable).getBitmap();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		localBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
		return stream.toByteArray();
	}

	public HashMap<Integer, AppBean> queryTopApps() {
		SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
		HashMap localHashMap = new HashMap();
		Cursor cursor = localSQLiteDatabase.query(tableName, null,
				"pageIndex=?", new String[] { "0" }, null, null,
				"index_in_dex ASC", null);
		if (cursor != null && cursor.getCount() > 0) {
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
					.moveToNext()) {
				AppBean appBean = new AppBean();
				byte[] arrayOfByte = cursor.getBlob(cursor
						.getColumnIndex("app_image"));
				appBean.setIcon(new BitmapDrawable(BitmapFactory
						.decodeByteArray(arrayOfByte, 0, arrayOfByte.length)));
				appBean.setPackageName(cursor.getString(cursor
						.getColumnIndex("packageName")));
				appBean.setPosition(cursor.getInt(cursor
						.getColumnIndex("index_in_dex")));
				appBean.setPageIndex(cursor.getInt(cursor
						.getColumnIndex("pageIndex")));
				appBean.setDataDir(cursor.getString(cursor
						.getColumnIndex("data_dir")));
				appBean.setName(cursor.getString(cursor.getColumnIndex("name")));
				appBean.setId(cursor.getString(cursor.getColumnIndex("_id")));
				localHashMap.put(Integer.valueOf(appBean.getPosition()),
						appBean);
			}
		}
		return localHashMap;
	}

	public boolean removeRecode(AppBean appBean, int position) {
		SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
		String[] arrayOfString = new String[1];
		arrayOfString[0] = String.valueOf(position);
		if (localSQLiteDatabase.delete(tableName,
				"pageIndex=0 and index_in_dex=?", arrayOfString) > 0) {
			return true;
		} else {
			localSQLiteDatabase.close();
			return false;
		}
	}

	public boolean deleteApp(String packageName) {
		SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
		int num = localSQLiteDatabase.delete(tableName, "packageName=?",
				new String[] { packageName });
		if (num > 0) {
			return true;
		} else {
			localSQLiteDatabase.close();
			return false;
		}
	}

	public AppBean queryAppInfo(String packageName) {
		SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
		Cursor cursor = localSQLiteDatabase.query(tableName, null,
				"packageName=?", new String[] { packageName }, null, null,
				null, null);
		AppBean appBean = new AppBean();
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			byte[] arrayOfByte = cursor.getBlob(cursor
					.getColumnIndex("app_image"));
			appBean.setIcon(new BitmapDrawable(BitmapFactory.decodeByteArray(
					arrayOfByte, 0, arrayOfByte.length)));
			appBean.setPackageName(cursor.getString(cursor
					.getColumnIndex("packageName")));
			appBean.setPosition(cursor.getInt(cursor
					.getColumnIndex("index_in_dex")));
			appBean.setPageIndex(cursor.getInt(cursor
					.getColumnIndex("pageIndex")));
			appBean.setDataDir(cursor.getString(cursor
					.getColumnIndex("data_dir")));
			appBean.setName(cursor.getString(cursor.getColumnIndex("name")));
			appBean.setId(cursor.getString(cursor.getColumnIndex("_id")));
		}
		return appBean;
	}

}
