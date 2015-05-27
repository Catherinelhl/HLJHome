package com.hlj.utils;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;

import android.os.AsyncTask;

public class Player {

	public static String getbaseip() {
		String str = null;

		try {
			Jsoup.connect("http://proxy.shntv.cn/cntv-5-cctv1.m3u8")
					.userAgent("Mozilla").timeout(5000).get();
			return str;
		} catch (UnsupportedMimeTypeException localUnsupportedMimeTypeException) {
			String[] arrayOfString = localUnsupportedMimeTypeException
					.toString().split("URL=");
			if ((arrayOfString != null) && (arrayOfString.length > 1))
				;
			Matcher localMatcher = Pattern.compile("http://([\\d\\.:]+)/")
					.matcher(arrayOfString[1]);
			if (localMatcher.find())
				;
			str = localMatcher.group(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	public static String getredirect(String paramString) {
		String str = null;

		if (!paramString.startsWith("http"))
			str = "";

		// while (true) {
		// return str;
		int i = 3;
		try {

			// do {
			Jsoup.connect(paramString)
					.header("User-Ver", "VSTlive/3.0.0")
					.header("User-Key",
							SwissArmyKnife.md5(
									"time-"
											+ String.valueOf(
													System.currentTimeMillis())
													.substring(0, 8)
											+ "/key-52itvlive")
									.substring(0, 16))
					.header("User-Agent", "GGwlPlayer/QQ243944493")
					.timeout(5000).get();
			// str = null;
			// if (0 != 0)
			// break;
			// }

			// while (i > 0);
			return str;
		} catch (UnsupportedMimeTypeException localUnsupportedMimeTypeException) {
			String[] arrayOfString;
			do
				arrayOfString = localUnsupportedMimeTypeException.toString()
						.split("URL=");
			while (arrayOfString.length <= 1);
			if (arrayOfString[1].contains("youku"))
				return "";
			return arrayOfString[1];// ��Ҫ�ķ��ص�ַ
		} catch (IOException localIOException) {
			while (true) {
				i--;
				return "";
			}
		}
	}

}
