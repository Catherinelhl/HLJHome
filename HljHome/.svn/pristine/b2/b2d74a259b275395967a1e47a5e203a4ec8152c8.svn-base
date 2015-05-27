package com.hlj.receiver;

import android.R.integer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.hlj.net.GetWeatherRequest;
import com.hlj.net.GetWeatherResponse;
import com.hlj.net.HttpHomeLoadDataTask;
import com.hlj.utils.ACache;
import com.hlj.utils.Logger;
import com.hlj.utils.StringTools;
import com.hlj.utils.WeatherUtils;
import com.live.video.constans.WeatherInfo;
import com.live.video.net.callback.IUpdateData;

/**
 * Created with IntelliJ IDEA. User: ${shime} Date: 13-10-17 Time: 下午5:17 To
 * change this template use File | Settings | File Templates.
 */
public class WeatherReceiver extends BroadcastReceiver {
	private WeatherInfo weatherInfo;
	private WeatherUpdateListener weatherUpdateListener;

	private ACache aCache;

	// 是否强制获取
	public static boolean isForceGet;

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals("com.hlj.weather")) {
			weatherUpdateListener = (WeatherUpdateListener) context;
			aCache = ACache.get(context);
			String str = aCache.getAsString("weatherInfo");
			
			Logger.log("isForceGet:"+isForceGet);
			if (isForceGet) {
				Logger.log("网络读取：" + str);
				getWeather(context);
			} else {
				if (!StringTools.isNullOrEmpty(str)) {
					Logger.log("缓存读取：" + str);
					weatherInfo = WeatherUtils.getWeatherInfo(str);
					weatherUpdateListener.updateWeather(weatherInfo);
				} else {
					Logger.log("网络读取：" + str);
					getWeather(context);
				}
			}

		}
	}

	public void getWeather(Context context) {
		GetWeatherRequest request = new GetWeatherRequest(context);
		new HttpHomeLoadDataTask(context, weatherCallback, false, "", false)
				.execute(request);
	}

	IUpdateData weatherCallback = new IUpdateData() {
		@Override
		public void updateUi(Object o) {
			Logger.log("==========加入缓存==========");
			aCache.put("weatherInfo", o.toString(), 2*60*60);

			GetWeatherResponse response = new GetWeatherResponse();
			response.paseRespones(o.toString());
			weatherInfo = response.weatherInfo;
			Log.d("========GetWeatherResponse=========", ""
					+ response.weatherInfo);
			weatherUpdateListener.updateWeather(response.weatherInfo);
		}

		@Override
		public void handleErrorData(String info) {
			weatherInfo = null;
		}
	};

	public static abstract interface WeatherUpdateListener {
		public abstract void updateWeather(WeatherInfo weatherInfo);
	}
}
