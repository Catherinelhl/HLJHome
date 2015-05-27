package com.hlj.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.hlj.utils.Logger;
import com.hlj.view.VideoInfo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends Activity {

	List<VideoInfo> list = new ArrayList<VideoInfo>();

	private WebView webView;

	String url = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hlj_webview);

		list = getVideoList();
		webView = (WebView) findViewById(R.id.webView);
		webView.getSettings().setJavaScriptEnabled(true);// 允许webkit执行js代码；
		webView.addJavascriptInterface(new Video(), "video");// 为webkit添加插件
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.clearCache(true);

		url = this.getIntent().getStringExtra("url");
		Logger.log("WebViewActivity url:" + url);
		
		//url="";
		if ("".equals(url)) {
			webView.loadUrl("file:///android_asset/f4m.html");// 加载固定html文件
		} else {
			webView.loadUrl(url);// 加载html文件

			// webView.setWebViewClient(new WebViewClient() {
			// public boolean shouldOverrideUrlLoading(WebView view, String url)
			// {
			// view.loadUrl(url);
			// return true;
			// }
			// });
		}

	}

	public class Video {

		Handler mHandler = new Handler();

		// android API Level 17及以上的版本中
		@JavascriptInterface
		public void getvideo() {

			try {
				JSONArray array = new JSONArray();

				for (VideoInfo info : list) {
					JSONObject item = new JSONObject();
					item.put("num", info.id);
					item.put("name", info.title);
					item.put("address", info.address);
					array.put(item);
				}
				final String json = array.toString();

				mHandler.post(new Runnable() {
					public void run() {
						webView.loadUrl("javascript:show('" + json + "')");
					}
				});

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		@JavascriptInterface
		public void call(String mobile) {
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
					+ mobile));
			startActivity(intent);
		}

		@JavascriptInterface
		public void playVideo(String title) {
			Intent it = new Intent(WebViewActivity.this,
					VideoPlayActivity.class);

			String videoaddress = "http://down.233.com/2013_2014/2013/xiaoxue/renjiaoban/yinianji_yuwen_shangce/1_1pamabar7zq2gphiffahyaxzaff2oex8v61h3pz5.mp4";

			it.putExtra("url", videoaddress);
			it.putExtra("title", title);
			startActivity(it);
			finish();
		}

		@JavascriptInterface
		public void goToInfo(String contentId) {
			Intent it = new Intent(WebViewActivity.this,
					RecomdVideoDetailsActivity.class);
			it.putExtra("contentid", contentId);
			startActivity(it);
			// finish();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return false;
	}

	public List<VideoInfo> getVideoList() {
		List<VideoInfo> videoList = new ArrayList<VideoInfo>();
		VideoInfo info = new VideoInfo();
		info.id = 1;
		info.title = "收费视频";
		info.address = "试看";
		videoList.add(info);
		return videoList;
	}

	@Override
	protected void onDestroy() {
		// webview清理缓存
		webView.removeAllViews();
		webView.destroy();
		super.onDestroy();
	}

}
