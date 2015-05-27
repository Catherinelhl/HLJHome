package com.hlj.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.Random;

import com.hlj.view.CustomDialog;
import com.live.video.db.DBHelper;
import com.live.video.db.DBHelperWeather;

import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

public class CommonTools {

	public static final String TAG = "TV_DB";

	public static String getDataBasePath(Context context) {

		String packageName = context.getPackageName();

		String baseDir = "/data/data/" + packageName + "/databases";

		String DB_PATH = baseDir + File.separator + DBHelper.DBName;

		// if ((new File(DB_PATH).exists() == false)) {
		try {
			File f = new File(DB_PATH);
			if (!f.exists()) {
				f.mkdir();
				// f.createNewFile();

				// 得到assets目录下Sqlite数据库文件作为输入流
				InputStream is = context.getAssets().open(DBHelper.DBName);

				OutputStream os = new FileOutputStream(DB_PATH);

				byte[] buffer = new byte[1024];
				int length;
				while ((length = is.read(buffer)) > 0) {
					os.write(buffer, 0, length);
				}
				// 关闭文件流
				os.flush();
				os.close();
				is.close();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// }

		return DB_PATH;
	}

	/**
	 * @param context
	 * @param id
	 * @param viewGroup
	 * @return 返回 从配置文件中实例化的view对象
	 */
	public static View getView(Context context, int id, ViewGroup viewGroup) {
		return LayoutInflater.from(context).inflate(id, viewGroup);
	}

	/**
	 * 获取随机数的新数组
	 * 
	 * @param list
	 * @return
	 */
	public static int[] getRandomList(int[] list) {
		int count = list.length;

		// 结果集
		int[] resultList = new int[count];

		// 用一个LinkedList作为中介
		LinkedList<Integer> temp = new LinkedList<Integer>();

		// 初始化temp
		for (int i = 0; i < count; i++) {
			temp.add((Integer) list[i]);
		}
		// 取数
		Random rand = new Random();
		for (int i = 0; i < count; i++) {
			int num = rand.nextInt(count - i);
			resultList[i] = (Integer) temp.get(num);
			temp.remove(num);
		}
		return resultList;
	}

	/**
	 * 是否已存在天气的数据库
	 * 
	 * @param context
	 * @return
	 */
	public static boolean hasWeatherdb(Context context) {
		Boolean b = false;
		String packageName = context.getPackageName();
		String baseDir = "/data/data/" + packageName + "/databases";
		String DB_PATH = baseDir + File.separator
				+ DBHelperWeather.DTATABANSENAME;
		File f = new File(DB_PATH);
		if (f.exists()) {
			b = true;
		}
		return b;
	}

	// 是否有SDCARD
	public static final boolean hasSDCard() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	private static CustomDialog customDialog;

	public static void showEditPasssDialog(Context context, String btn1Text,
			String btn2Text, OnClickListener onclick) {
		// 防止同时弹出多个错误提示框，引用覆盖的问题。 每弹出一个，dismiss掉该引用之前指向的对象
		if (customDialog != null && customDialog.isShowing()) {
			customDialog.dismiss();
		}
		customDialog = new CustomDialog(context);

	}

	public static int getScreenHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}

	public static int getScreenWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	private static String videoTitle;
	private static String videoName;

	public static String getVideoName() {
		return videoName;
	}

	public static void setVideoName(String videoName) {
		CommonTools.videoName = videoName;
	}

	public static void saveTitle(String title) {
		videoTitle = title;
	}

	public static String getTitle() {
		return videoTitle;
	}

	public static String getCookie(Context context) {
		CookieSyncManager.createInstance(context);
		CookieManager cookieManager = CookieManager.getInstance();
		String cookie = cookieManager.getCookie("Cookie");
		if (cookie != null) {
			Logger.log(cookie);
		} else {
			cookie = "fastweb_title=fastwebtongzhuo100";
			cookieManager.setCookie("Cookie", cookie);
		}
		return cookie;
	}

}
