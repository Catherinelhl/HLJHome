package com.android.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;


public final class ManagerNetwork {
	private static String TAG = "ManagerNetwork=============>";

	// 网络连接超时时间
	private static final int DEF_CONN_TIMEOUT = 30 * 1000;
	// 网络sock通信超时时间
	private static final int DEF_SOCKET_TIMEOUT = 30 * 1000;

	private ManagerNetwork() {
	};

	/**
	 * 设置访问网络的post请求
	 * 
	 * @param url
	 *            访问网络的基本URL
	 * @param map
	 *            访问网络地址的需要参数
	 * @return
	 */
	public static String responseFromServiceByPost(String url,
			HashMap<String, String> map) {
		// 判断是否为空
		if (url == null || url.equals("") || map == null) {
			return null;
		}
		HttpPost httpPost = null;
		URI encodedUri = null;
		try {
			encodedUri = new URI(url);
			// 创建post请求对象
			httpPost = new HttpPost(encodedUri);
		} catch (URISyntaxException e) {
			// 清理一些空格
			String encodedUrl = url.replace(' ', '+');
			httpPost = new HttpPost(encodedUrl);
			e.printStackTrace();
		}
		System.out.println("url===" + url);
		// 创建访问客户端对象
		HttpClient httpClient = new DefaultHttpClient();
		// 设置网络连接超时
		httpClient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, DEF_CONN_TIMEOUT);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				DEF_SOCKET_TIMEOUT);
		try {
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
			// 循环得到访问参数
			for (Map.Entry<String, String> entry : map.entrySet()) {
				String key = entry.getKey().toString();
				String value = null;
				if (entry.getValue() == null) {
					value = "";
				} else {
					value = entry.getValue().toString();
				}
				// 得到一个参数，并添加到集合中
				BasicNameValuePair basicNameValuePair = new BasicNameValuePair(
						key, value);

				System.out.println("********************>"
						+ basicNameValuePair.toString());
				nameValuePair.add(basicNameValuePair);
			}
			Log.v(TAG, nameValuePair.toString());
			// 设置请求格式
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8")); // 此处也可以改为GBK
			Log.v(TAG, "httpPost.getEntity()==" + httpPost.getEntity());
			// 执行请求
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse != null) {
				int code = httpResponse.getStatusLine().getStatusCode();
				System.out
						.println(" httpResponse.getStatusLine().getStatusCode()=====>"
								+ code);
				// 如果请求成功
				if (code == HttpStatus.SC_OK) {
					// 得到请求回来的数据
					HttpEntity entity = httpResponse.getEntity();
					String result = EntityUtils.toString(entity).trim();
					Log.v(TAG, "the reslut data is=========>" + result
							+ "========>");
					return result;
				} else {
					httpPost.abort();
				}
			} else {
				httpPost.abort();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
			return null;
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
				httpClient = null;
			}
		}
		return null;
	}

	/**
	 * 使用get方式访问服务器
	 * 
	 * @param url
	 *            基础URL
	 * @param map
	 *            携带的参数
	 * @return
	 */
	public static String responseFromServiceByGet(String url,
			HashMap<String, String> map) {
		System.out.println("url====" + url);
		if (url == null || url.equals("")) {
			return null;
		}
		if (map != null) {
			StringBuilder sb = new StringBuilder(url);
			sb.append('?');
			for (Map.Entry<String, String> entry : map.entrySet()) {
				String key = entry.getKey().toString();
				String value = null;
				if (entry.getValue() == null) {
					value = "";
				} else {
					value = entry.getValue().toString();
				}
				sb.append(key);
				sb.append('=');
				try {
					value = URLEncoder.encode(value, HTTP.UTF_8);
					sb.append(value);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				sb.append('&');
			}
			sb.deleteCharAt(sb.length() - 1);// 删除最后一个"&"
			url = sb.toString();
		}
		System.out.println("url====" + url);

		HttpGet httpGet = null;
		URI encodedUri = null;
		InputStream is = null;
		try {
			encodedUri = new URI(url);
			httpGet = new HttpGet(encodedUri);
		} catch (URISyntaxException e) {
			// 清理一些空格
			String encodedUrl = url.replace(' ', '+');
			httpGet = new HttpGet(encodedUrl);
			e.printStackTrace();
		}
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, DEF_CONN_TIMEOUT);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				DEF_SOCKET_TIMEOUT);
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse != null) {
				System.out
						.println("httpResponse.getStatusLine().getStatusCode()"
								+ httpResponse.getStatusLine().getStatusCode());
				int httpCode = httpResponse.getStatusLine().getStatusCode();
				if (httpCode == HttpStatus.SC_OK) {
					HttpEntity entity = httpResponse.getEntity();
					is = entity.getContent();
					if (is != null) {
						return getStr1FromInputstream(is);
					}
				} else {
					httpGet.abort();
				}
			} else {
				httpGet.abort();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
			return null;
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
				httpClient = null;
			}

		}
		return null;
	}

	/**
	 * 判断网络链接状态
	 * 
	 * @param url
	 * @return
	 */
	public static boolean checkNetworkIsGood(String url) {
		if (url == null || url.equals("")) {
			return false;
		}
		HttpGet httpGet = null;
		URI encodedUri = null;
		try {
			encodedUri = new URI(url);
			httpGet = new HttpGet(encodedUri);
		} catch (URISyntaxException e) {
			// 清理一些空格
			String encodedUrl = url.replace(' ', '+');
			httpGet = new HttpGet(encodedUrl);
			e.printStackTrace();
		}
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				5000);
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse != null) {
				int uRC = httpResponse.getStatusLine().getStatusCode();
				if (uRC == HttpStatus.SC_OK) {
					return true;
				} else {
					httpGet.abort();
				}
			} else {
				httpGet.abort();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
				httpClient = null;
			}
		}
		return false;
	}

	/**
	 * 转换数据类型：把InputStream转换为字符串
	 * 
	 * @param input
	 *            数据
	 * @return
	 */
	private static String getStr1FromInputstream(InputStream input) {
		String result = null;
		int i = -1;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			while ((i = input.read()) != -1) {
				baos.write(i);
			}
			result = baos.toString();
			System.out.println("the got result is==========>" + result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
