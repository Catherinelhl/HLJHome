package org.videolan.utils;

import java.text.DecimalFormat;

public class StringUtils {

	/**
	 * 时间转化显示
	 * 
	 * @param time
	 * @return
	 */
	public static String generateTime(long time) {
		time = time / 1000;
		long hh = time / 3600;
		long mm = time % 3600 / 60;
		long ss = time % 60;
		String strTemp = null;
		if (0 != hh) {
			strTemp = String.format("%02d:%02d:%02d", hh, mm, ss);
		} else {
			strTemp = String.format("%02d:%02d", mm, ss);
		}

		return strTemp;
	}

	/**
	 * 秒的格式转化
	 * 
	 * @param paramLong
	 * @return
	 */
	public static String longToSec(long paramLong) {
		float f = (float) paramLong / 1000.0F;
		return new DecimalFormat("#.#").format(f) + "秒";
	}

	/**
	 * 获取后缀名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot);
			}
		}
		return filename;
	}

	/**
	 * 获取文件名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getFileName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('/');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				filename = filename.substring(dot + 1);
				Logger.log("filename:" + filename);
				return filename;
			}
		}
		return filename;
	}

	/**
	 * 字符串比较大小
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static boolean compare(String s1, String s2) {

		int i = s2.compareTo(s1);
		boolean update;
		if (i <= 0) {
			update = false;
		} else {
			update = true;
		}
		return update;
	}

}
