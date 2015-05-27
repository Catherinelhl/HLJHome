package com.hlj.HomeActivity.set;

import java.util.ArrayList;

import com.hlj.HomeActivity.R;
import com.hlj.adapter.CursorAdapters;
import com.hlj.receiver.WeatherReceiver;
import com.hlj.receiver.WeatherReceiver.WeatherUpdateListener;
import com.hlj.utils.CommonTools;
import com.hlj.utils.Logger;
import com.hlj.utils.WeatherUtils;
import com.live.video.constans.WeatherInfo;
import com.live.video.db.DBHelperWeather;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * 天气设置
 * 
 * @author huangyuchao
 * 
 */
public class SettingWeather extends Activity implements OnClickListener,
		WeatherUpdateListener {

	TextView set_name1, set_name2;
	private ImageView setItemLog;

	WeatherReceiver receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.setting_main);
		super.onCreate(savedInstanceState);
		((ViewStub) findViewById(R.id.set_weather)).inflate();

		initView();
	}

	ArrayList<String> list = new ArrayList<String>();

	// ArrayAdapter<String> adapter;
	TextView setting_weather_provice, setting_weather_city;

	ImageView weather_set_infoimage1, weather_set_infoimage2;
	TextView weatherytext, temptext;

	PopupWindow popup = null;
	private SQLiteDatabase db;

	private Cursor provinceCursor = null;
	private Cursor cityCursor = null;

	private CursorAdapters provinceAdapter;
	private CursorAdapters cityAdapter;

	private void initView() {

		receiver = new WeatherReceiver();
		registerReceiver(receiver, new IntentFilter("com.hlj.weather"));

		set_name1 = (TextView) findViewById(R.id.set_name1);
		set_name2 = (TextView) findViewById(R.id.set_name2);
		setItemLog = (ImageView) findViewById(R.id.set_item_log);
		set_name1.setText("天气设置");
		set_name2.setText("天气设置");
		setItemLog.setImageResource(R.drawable.weather_setup);

		// addCity();

		// adapter = new ArrayAdapter<String>(this,
		// R.layout.type_details_submenu,list);

		setting_weather_provice = (TextView) findViewById(R.id.setting_weather_provice);
		// setting_weather_provice.setAdapter(adapter);

		setting_weather_city = (TextView) findViewById(R.id.setting_weather_city);
		// setting_weather_city.setAdapter(adapter);

		weather_set_infoimage1 = (ImageView) findViewById(R.id.weather_set_infoimage1);
		weather_set_infoimage2 = (ImageView) findViewById(R.id.weather_set_infoimage2);

		weatherytext = (TextView) findViewById(R.id.weatherytext);
		temptext = (TextView) findViewById(R.id.temptext);

		setting_weather_provice.setOnClickListener(this);
		setting_weather_city.setOnClickListener(this);

		if (CommonTools.hasWeatherdb(this)) {
			db = SQLiteDatabase.openDatabase(
					getDatabasePath(DBHelperWeather.DTATABANSENAME).toString(),
					null, SQLiteDatabase.OPEN_READONLY);
			provinceCursor = db.query("provinces", null, null, null, null,
					null, null);
		} else {
			String path = WeatherUtils.getDataBasePath(this);
			db = SQLiteDatabase.openDatabase(path, null,
					SQLiteDatabase.OPEN_READONLY);
			provinceCursor = db.query("provinces", null, null, null, null,
					null, null);
		}

		provinceAdapter = new CursorAdapters(this, provinceCursor, false);



		int provinceId = this.getSharedPreferences("settingSPF",
				this.MODE_PRIVATE).getInt("weather_province_id", 101010100);
		setting_weather_provice.setText(getProvice(provinceId));

		int cityId = this.getSharedPreferences("settingSPF", this.MODE_PRIVATE)
				.getInt("weather_city_id", 101010100);
		cityCursor=db.query("citys", null, "provinceid= ? ", new String[]{String.valueOf(provinceId)}, null, null, null);
		cityAdapter = new CursorAdapters(this, cityCursor, false);
		setting_weather_city.setText(getCity(cityId).toString());

	}

	private String getProvice(int id) {
		String name = null;
		SQLiteDatabase database = this.db;
		Cursor result = database.query("provinces", null, " _id = ? ",
				new String[] { String.valueOf(id) }, null, null, null);
		Log.d("=====result=========", "" + result);
		if (result != null) {
			if (result.moveToFirst()) {
				name = result.getString(result.getColumnIndex("name"));
			}
		}
		result.close();
		Log.d("=====name=========", "" + name);
		return name;
	}

	private String getCity(int id) {
		String name = null;
		SQLiteDatabase localSQLiteDatabase = this.db;
		Cursor result = localSQLiteDatabase.query("citys", null, "_id = ? ",
				new String[] { String.valueOf(id) }, null, null, null);
		if (result != null) {
			if (result.moveToFirst()) {
				name = result.getString(result.getColumnIndex("name"));
			}

		}
		result.close();
		return name;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.setting_weather_provice:
			showProvinceWindow();
			provinceListView.setSelection(provinceSelection);
			break;
		case R.id.setting_weather_city:
			showCityWindow();
			cityListView.setSelection(citySelection);
			break;
		}
	}

	LinearLayout view;
	ListView provinceListView;
	PopupWindow provinceWindow;
	private int provinceSelection = 0;

	private void showProvinceWindow() {
		LayoutInflater inflater = LayoutInflater.from(this);
		view = (LinearLayout) inflater.inflate(R.layout.city_list, null);

		provinceListView = (ListView) view.findViewById(R.id.city_list);
		LayoutParams lp = (LayoutParams) provinceListView.getLayoutParams();
		lp.height = 300;
		provinceListView.setLayoutParams(lp);
		provinceListView.setAdapter(provinceAdapter);
		provinceWindow = new PopupWindow(view,
				setting_weather_provice.getWidth() - 1,
				LayoutParams.WRAP_CONTENT);
		// 必须设置背景
		provinceWindow.setBackgroundDrawable(new BitmapDrawable());
		// 设置焦点
		provinceWindow.setFocusable(true);
		// 设置点击其他地方 就消失
		provinceWindow.setOutsideTouchable(true);
		provinceWindow.showAsDropDown(setting_weather_provice);

		provinceListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				provinceSelection = position;

				provinceCursor = (Cursor) provinceListView
						.getItemAtPosition(position);

				String currentProvince = provinceCursor
						.getString(provinceCursor.getColumnIndex("name"));
				Logger.log("province=" + currentProvince);
				Logger.log("id=" + id);

				setting_weather_provice.setText(currentProvince);
				provinceCursor.moveToPosition(provinceSelection);

				cityCursor = db.query("citys", null, "provinceid = ? ",
						new String[] { String.valueOf(id) }, null, null, null);
				citySelection = 0;
				cityAdapter.changeCursor(cityCursor);
				cityCursor.moveToPosition(citySelection);
				setting_weather_city.setText(cityCursor.getString(cityCursor
						.getColumnIndex("name")));

				SharedPreferences.Editor editor = getSharedPreferences(
						"settingSPF", 0).edit();
				editor.putInt("weather_province_id", provinceCursor
						.getInt(provinceCursor.getColumnIndex("_id")));
				String city = cityCursor.getString(cityCursor
						.getColumnIndex("name"));
				int cityid = cityCursor.getInt(cityCursor.getColumnIndex("_id"));
				editor.putString("weather_city_code", city);
				editor.putInt("weather_city_id", cityid);
				editor.commit();

				provinceWindow.dismiss();
			}
		});

	}

	ListView cityListView;
	PopupWindow cityWindow;
	private int citySelection = 0;

	private void showCityWindow() {
		LayoutInflater inflater = LayoutInflater.from(this);
		view = (LinearLayout) inflater.inflate(R.layout.city_list, null);

		cityListView = (ListView) view.findViewById(R.id.city_list);
		LayoutParams lp = (LayoutParams) cityListView.getLayoutParams();
		lp.height = 300;
		cityListView.setLayoutParams(lp);
		cityListView.setAdapter(cityAdapter);
		cityWindow = new PopupWindow(view, setting_weather_city.getWidth() - 1,
				LayoutParams.WRAP_CONTENT);
		// 必须设置背景
		cityWindow.setBackgroundDrawable(new BitmapDrawable());
		// 设置焦点
		cityWindow.setFocusable(true);
		// 设置点击其他地方 就消失
		cityWindow.setOutsideTouchable(true);
		cityWindow.showAsDropDown(setting_weather_city);

		cityListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				citySelection = position;
				cityCursor = (Cursor) cityListView.getItemAtPosition(position);
				String city = cityCursor.getString(cityCursor
						.getColumnIndex("name"));
				Logger.log("city=" + city);
				setting_weather_city.setText(city);

				SharedPreferences.Editor editor = getSharedPreferences(
						"settingSPF", 0).edit();
				int cityId = cityCursor.getInt(cityCursor.getColumnIndex("_id"));
				Logger.log("city=" + city);
				editor.putString("weather_city_code", city);
				editor.putInt("weather_city_id", cityId);
				editor.commit();
				WeatherReceiver.isForceGet=true;
				sendBroadcast(new Intent("com.hlj.weather"));
				cityWindow.dismiss();
			}
		});
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(receiver);
		if (provinceCursor != null) {
			provinceCursor.close();
		}
		if (cityCursor != null) {
			cityCursor.close();
		}
		if (db != null) {
			db.close();
		}
		super.onDestroy();
	}

	int[] drawables;

	@Override
	public void updateWeather(WeatherInfo weatherInfo) {
		weather_set_infoimage1.setVisibility(View.GONE);
		weather_set_infoimage2.setVisibility(View.GONE);
		if (weatherInfo != null) {
			setting_weather_city.setText(weatherInfo.getCity());
			weatherytext.setText(weatherInfo.getWeather1());
			temptext.setText(weatherInfo.getTemp1());
			drawables = WeatherUtils.getWeaResByWeather(weatherInfo
					.getWeather1());
			if (drawables != null && drawables[0] != 0) {
				weather_set_infoimage1.setVisibility(View.VISIBLE);
				weather_set_infoimage1.setImageResource(drawables[0]);
				if (drawables[1] != 0) {
					weather_set_infoimage2.setVisibility(View.VISIBLE);
					weather_set_infoimage2.setImageResource(drawables[1]);
				}
			}
		}
	}

}
