package com.hlj.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.json.JSONObject;

import com.hlj.HomeActivity.R;
import com.hlj.encrypt.Base64;
import com.live.video.constans.HomeConstants;
import com.live.video.constans.WeatherInfo;
import com.live.video.db.DBHelperWeather;

import android.content.Context;
import android.util.Log;

public class WeatherUtils {

	public static String getDataBasePath(Context context) {
		String packageName = context.getPackageName();
		String baseDir = "/data/data/" + packageName + "/databases";
		// String baseDir = "/data/data/" + packageName;
		String DB_PATH = baseDir + File.separator
				+ DBHelperWeather.DTATABANSENAME;
		try {
			File f = new File(DB_PATH);
			if (!f.getParentFile().exists()) {
				// 如果父文件夹不存在，先创建
				f.getParentFile().mkdirs();
			}
			f.createNewFile();
			/*
			 * if (!f.exists()) { f.mkdir(); //f.createNewFile(); }
			 */
			// 得到assets目录下Sqlite数据库文件作为输入流
			InputStream is = context.getAssets().open(
					DBHelperWeather.DTATABANSENAME);
			OutputStream os = new FileOutputStream(DB_PATH);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
				Log.d("=========================", "" + os);
			}
			// 关闭文件流
			os.flush();
			os.close();
			is.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return DB_PATH;
	}

	public static String getCityCode(Context context) {
		int id = context.getSharedPreferences("settingSPF", 0).getInt(
				"weather_city_id", 0);
		String str1 = String.valueOf(id);
		if (str1.equals("0")) {
			return "101010100";
		}
		return str1;
	}

	public static WeatherInfo getWeatherInfo(String str) {
		//Logger.log(str);
			WeatherInfo weatherInfo = new WeatherInfo();

		try {
					JSONObject obj = new JSONObject(str).getJSONObject("weatherinfo");
					weatherInfo.setCity(obj.getString("city"));
					weatherInfo.setTemp1(obj.getString("temp1"));
					weatherInfo.setWeather1(obj.getString("weather1"));
				} catch (Exception e) {
			e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.
				}
				return weatherInfo;
	}

	public static int[] getWeaResByWeather(String s) {
		String[] spit = null;
		int[] ints = null;
		if (!StringTools.isNullOrEmpty(s)) {
			spit = s.split("转");
			ints = new int[spit.length];
		}

		if (spit != null && ints != null && ints.length != -1) {
			if (ints.length == 1) {
				int[] ints1 = new int[2];
				ints1[0] = ints[0];
				ints1[1] = 0;
				ints1[0] = getWeaResByWeather1(spit[0]);
				return ints1;
			} else {
				for (int i = 0; i < spit.length; i++) {
					ints[i] = getWeaResByWeather1(spit[i]);
					return ints;
				}
			}
		}
		return null;
	}

	public static int getWeaResByWeather1(String weatherName) {
		int i = R.drawable.ic_weather_heavy_rain_l;
		if (weatherName.equals("阴")) {
			i = R.drawable.ic_weather_cloudy_l;
		}
		if (weatherName.equals("多云")) {
			i = R.drawable.ic_weather_partly_cloudy_l;
		}
		if (weatherName.equals("晴")) {
			i = R.drawable.ic_weather_clear_day_l;
		}
		if (weatherName.equals("小雨")) {
			i = R.drawable.ic_weather_chance_of_rain_l;
		}
		if (weatherName.equals("中雨")) {
			i = R.drawable.ic_weather_rain_xl;
		}
		if (weatherName.equals("阵雨")) {
			i = R.drawable.ic_weather_chance_storm_l;
		}
		if (weatherName.equals("雷阵雨")) {
			i = R.drawable.ic_weather_thunderstorm_l;
		}
		if (weatherName.equals("小雪")) {
			i = R.drawable.ic_weather_chance_snow_l;
		}
		if (weatherName.equals("中雪")) {
			i = R.drawable.ic_weather_flurries_l;
		}
		if (weatherName.equals("大雪")) {
			i = R.drawable.ic_weather_snow_l;
		}
		if (weatherName.equals("暴雪")) {
			i = R.drawable.ic_weather_snow_l;
		}
		if (weatherName.equals("冰雹")) {
			i = R.drawable.ic_weather_icy_sleet_l;
		}
		if (weatherName.equals("雨夹雪")) {
			i = R.drawable.ic_weather_icy_sleet_l;
		}
		if (weatherName.equals("风")) {
			i = R.drawable.ic_weather_windy_l;
		}
		if (weatherName.equals("龙卷风")) {
			i = R.drawable.ic_weather_windy_l;
		}
		if (weatherName.equals("雾")) {
			i = R.drawable.ic_weather_fog_l;
		}
		return i;
	}

}
