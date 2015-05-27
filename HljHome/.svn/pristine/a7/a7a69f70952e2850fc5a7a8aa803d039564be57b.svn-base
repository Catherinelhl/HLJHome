package com.live.video.net;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import com.hlj.utils.Logger;
import com.hlj.utils.PrefrenceHandler;
import com.live.video.constans.ErrorCode;
import com.live.video.constans.HomeConstants;

/**
 * HTTP 请求的get和post
 * 
 * @author huangyuchao
 * 
 */
public class HttpRequest {

	static HttpClient hc;

	public static HttpClient getInstance() {
		if (hc == null) {
			hc = new DefaultHttpClient();
		}
		return hc;
	}

	public static String httpRequestPost(HttpPost post) {
		hc = getInstance();

		return post(hc, post);
	}

	/**
	 * 关闭连接
	 */
	public static void closeConn() {
		hc.getConnectionManager().shutdown();
	}

	public static String httpRequestGet(HttpGet get) {
		hc = getInstance();
		return get(hc, get);
	}

	public static boolean httpPostState;

	private static String post(HttpClient client, HttpPost post) {
		HttpResponse response = null;

		String message = "";
		try {
			response = client.execute(post);

			if (response.getStatusLine().getStatusCode() != 200) {
				httpPostState = false;
				post.abort();
				try {
					String msg = EntityUtils.toString(response.getEntity(),
							"UTF-8");
					Logger.log("HttpRequest original response:" + msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return "{\"RESULT\":\"FAIL\",\"ERROR_TYPE\":\""
						+ ErrorCode.ERROR_CODE_SERVER_ERROR + "\"}";
			}
			HttpEntity responseEntity = response.getEntity();

			if (responseEntity != null) {
				message = EntityUtils.toString(responseEntity, "UTF-8");
				httpPostState = true;
			}

		} catch (Exception e) {
			post.abort();
			httpPostState = false;
			if (e instanceof SocketTimeoutException) {
				// 连接超时
				return "{\"RESULT\":\"FAIL\",\"ERROR_TYPE\":\""
						+ ErrorCode.ERROR_CODE_NET_TIME_OUT + "\"}";
			} else if (e instanceof SocketException) {
				// 网络连接异常
				if (e.getMessage().indexOf("time out") == -1)
					return "{\"RESULT\":\"FAIL\",\"ERROR_TYPE\":\""
							+ ErrorCode.ERROR_CODE_NET + "\"}";
				else
					return "{\"RESULT\":\"FAIL\",\"ERROR_TYPE\":\""
							+ ErrorCode.ERROR_CODE_SERVER_ERROR + "\"}";
			} else {
				// 未知错误
				return "{\"RESULT\":\"FAIL\",\"ERROR_TYPE\":\""
						+ ErrorCode.ERROR_CODE_ERROR + "\"}";
			}

		}

		return message;

	}

	public static boolean getState(String url) {
		HttpGet get = new HttpGet(url);
		HttpClient client = new DefaultHttpClient();
		get.addHeader("Content-Type", "application/json;charset=utf-8");

		HttpResponse response = null;
		try {
			response = client.execute(get);

			client.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
					20000);

			if (response.getStatusLine().getStatusCode() == 200) {

				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	private static String get(HttpClient client, HttpGet get) {
		HttpResponse response = null;
		try {
			response = client.execute(get);

			client.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
					20000);

			if (response.getStatusLine().getStatusCode() != 200) {
				get.abort();
				try {
					String msg = EntityUtils.toString(response.getEntity(),
							"UTF-8");
					Logger.log("HttpRequest original response:" + msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return "{\"isSuccess\":\"false\",\"ERROR_TYPE\":\""
						+ ErrorCode.ERROR_CODE_SERVER_ERROR + "\"}";
			}
			HttpEntity responseEntity = response.getEntity();

			String message = "";
			if (responseEntity != null) {
				message = EntityUtils.toString(responseEntity, "UTF-8");
			}
			return message;
		} catch (Exception e) {
			Logger.log("HttpRequest's exception:" + e.getMessage());
			get.abort();
			if (e instanceof SocketTimeoutException) {
				// 连接超时
				return "{\"isSuccess\":\"false\",\"ERROR_TYPE\":\""
						+ ErrorCode.ERROR_CODE_NET_TIME_OUT + "\"}";
			} else if (e instanceof SocketException) {
				// 网络连接异常
				if (e.getMessage().indexOf("time out") == -1)

					return "{\"isSuccess\":\"false\",\"ERROR_TYPE\":\""
							+ ErrorCode.ERROR_CODE_NET + "\"}";
				else
					Logger.log("e.getMessage()");
				return "{\"isSuccess\":\"false\",\"ERROR_TYPE\":\""
						+ ErrorCode.ERROR_CODE_SERVER_ERROR + "\"}";
			} else {
				// 数据请求错误
				return "{\"isSuccess\":\"false\",\"ERROR_TYPE\":\""
						+ ErrorCode.ERROR_CODE_ERROR + "\"}";
			}
		}
	}

}
