package com.live.video.constans;

/**
 * Created with IntelliJ IDEA.
 * User: ${shime}
 * Date: 13-10-17
 * Time: 下午5:25
 * To change this template use File | Settings | File Templates.
 */
public class WeatherInfo {
   public String city;
   public String city_en;
   public String date_y;
   public String date;
   public String week;
   public String fchh;
   public String cityid;
   public String temp1;
   public String weather1;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity_en() {
		return city_en;
	}

	public void setCity_en(String city_en) {
		this.city_en = city_en;
	}

	public String getDate_y() {
		return date_y;
	}

	public void setDate_y(String date_y) {
		this.date_y = date_y;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getFchh() {
		return fchh;
	}

	public void setFchh(String fchh) {
		this.fchh = fchh;
	}

	public String getCityid() {
		return cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

	public String getTemp1() {
		return temp1;
	}

	public void setTemp1(String temp1) {
		this.temp1 = temp1;
	}

	public String getWeather1() {
		return weather1;
	}

	public void setWeather1(String weather1) {
		this.weather1 = weather1;
	}

	@Override
	public String toString() {
		return "WeatherInfo{" +
				"city='" + city + '\'' +
				", city_en='" + city_en + '\'' +
				", date_y='" + date_y + '\'' +
				", date='" + date + '\'' +
				", week='" + week + '\'' +
				", fchh='" + fchh + '\'' +
				", cityid='" + cityid + '\'' +
				", temp1='" + temp1 + '\'' +
				", weather1='" + weather1 + '\'' +
				'}';
	}
}
