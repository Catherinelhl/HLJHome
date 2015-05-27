package com.hlj.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.hlj.HomeActivity.R;

public class TimerTools {

	/**
	 * 由日期获取星期
	 * 
	 * @param sDate
	 * @return
	 */
	public static String getFullDateWeekTime(String sDate) {
		try {
			String formater = "yyyy-MM-dd";
			SimpleDateFormat format = new SimpleDateFormat(formater);
			Date date = format.parse(sDate);
			format.applyPattern("EEEE");
			return format.format(date);
		} catch (Exception ex) {
			System.out.println("TimeUtil  getFullDateWeekTime"
					+ ex.getMessage());
			return "";
		}
	}

	/**
	 * 由日期获取资源
	 * 
	 * @param date
	 * @return
	 */
	public static int getResource(String date) {
		String weekStr = getFullDateWeekTime(date);
		Logger.log("week:" + weekStr);
		int resourceId = 0;
		if ("星期一".equals(weekStr)) {
			resourceId = R.drawable.tv_back_monday_selector;
		} else if ("星期二".equals(weekStr)) {
			resourceId = R.drawable.tv_back_tuesday_selector;
		} else if ("星期三".equals(weekStr)) {
			resourceId = R.drawable.tv_back_wenesday_seletor;
		} else if ("星期四".equals(weekStr)) {
			resourceId = R.drawable.tv_back_thuresday_selector;
		} else if ("星期五".equals(weekStr)) {
			resourceId = R.drawable.tv_back_friday_selector;
		} else if ("星期六".equals(weekStr)) {
			resourceId = R.drawable.tv_back_saturday_selector;
		} else if ("星期日".equals(weekStr)) {
			resourceId = R.drawable.tv_back_sunday_selector;
		}
		return resourceId;
	}
	
	public static String getDayStr(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		// 格式化当前日期
		String sdate = sdf.format(time);
		return sdate;
	}

}
