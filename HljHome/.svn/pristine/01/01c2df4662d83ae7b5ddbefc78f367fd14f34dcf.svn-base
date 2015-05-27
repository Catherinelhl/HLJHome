package com.hlj.net;

import com.live.video.constans.WeatherInfo;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: ${shime}
 * Date: 13-11-26
 * Time: 下午12:21
 * To change this template use File | Settings | File Templates.
 */
public class GetWeatherResponse extends BaseResponse {
	public WeatherInfo weatherInfo = new WeatherInfo();
	@Override
	public void paseRespones(String js) {
		try {
			JSONObject obj = new JSONObject(js).getJSONObject("weatherinfo");
			weatherInfo.setCity(obj.getString("city"));
			weatherInfo.setTemp1(obj.getString("temp1"));
			weatherInfo.setWeather1(obj.getString("weather1"));
		} catch (JSONException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}
}
