package com.hlj.utils;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;

public class BitmapUtils {

	private static LruCache<String, Bitmap> memoryCache;
	private Context mContext;
	private static final int MEMORY_CACHE_SIZE = 20971520;
	private static final long DISK_CACHE_SIZE = 83886080L;

	private static DiskLruCache mDiskCache;

	private static BitmapUtils instance;

	private BitmapUtils(Context context) {
		this.mContext = context;
		memoryCache = new LruCache(MEMORY_CACHE_SIZE);
		File file = new File(mContext.getCacheDir(), "diskCache");
		mDiskCache = DiskLruCache.openCache(context, file, DISK_CACHE_SIZE);
	}

	public static BitmapUtils getInstance(Context context) {
		if (instance == null) {
			instance = new BitmapUtils(context);
		}
		return instance;
	}

	public Bitmap getBitmapFromMemory(String s) {
		String str = Md5Utils.getMD5String(s);
		Logger.log("cache str:" + str);
		if (str != null) {
			return (Bitmap) memoryCache.get(str);
		}
		return null;
	}

	public Bitmap getBitmapFromDisk(String s) {
		String str = Md5Utils.getMD5String(s);
		Logger.log("cache str:" + str);
		if (str != null) {
			return (Bitmap) mDiskCache.get(str);
		}
		return null;
	}

	public void addToCache(String s, Bitmap bitmap, boolean isMemoryCache,
			boolean isDiskCache) {
		String str = Md5Utils.getMD5String(s);
		Logger.log("cache str:" + str);
		if (isMemoryCache && str != null && bitmap != null) {
			memoryCache.put(str, bitmap);
		}
		if (isDiskCache && str != null && bitmap != null) {
			mDiskCache.put(str, bitmap);
		}

	}

	public Bitmap getBitmapFromNet(String url) {
		Bitmap bitmap = null;
		DefaultHttpClient client = new DefaultHttpClient();
		client.getParams().setIntParameter("http.socket.timeout", 60000);
		client.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(5,
				false));
		HttpGet httpGet = new HttpGet();
		try {
			httpGet.setURI(new URI(url));
			HttpResponse response = client.execute(httpGet);
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				bitmap = BitmapFactory.decodeStream(response.getEntity()
						.getContent());
			}
			if (bitmap == null) {
				Logger.log("-----------------getBitmapFromNet failed--------------");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return bitmap;
	}
}
