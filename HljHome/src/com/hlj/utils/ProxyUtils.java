package com.hlj.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ProxyUtils {
	
	/**
	 * 获取重定向url
	 * 
	 * @param url
	 * @return
	 */
	public static String getRedirectUrl(String url) {

		String redirectUrlString = "";

		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(url)
					.openConnection();
			connection.setInstanceFollowRedirects(false);
			System.out.println("code:" + connection.getResponseCode());
			if (connection.getResponseCode() == 302) {
				System.out.println("come in");
				redirectUrlString = connection.getHeaderField("Location");
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return redirectUrlString;

	}

	
}
