package com.hlj.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.StatFs;
import android.util.Log;

public class DiskLruCache {

	private File mCacheDir;
	private long maxCacheByteSize = 5242880L;

	private int cacheSize = 0;
	private int cacheByteSize = 0;

	private final Map<String, String> mLinkedHashMap = Collections
			.synchronizedMap(new LinkedHashMap(32, 0.75F, true));

	private DiskLruCache(File file, long paramLong) {
		mCacheDir = file;
		maxCacheByteSize = paramLong;
	}

	public static DiskLruCache openCache(Context context, File f, long maxSize) {

		if (!f.exists()) {
			f.mkdir();
		}
		if (f.isDirectory() && f.canWrite() && getUsableSpace(f) > maxSize) {
			return new DiskLruCache(f, maxSize);
		}
		return null;
	}

	public static long getUsableSpace(File file) {
		if (Build.VERSION.SDK_INT >= 9) {
			return file.getUsableSpace();
		}
		StatFs localStatFs = new StatFs(file.getPath());
		return localStatFs.getBlockSize() * localStatFs.getAvailableBlocks();
	}

	public void put(String paramStr, Bitmap bitmap) {

		synchronized (mLinkedHashMap) {
			String str = createFilePath(this.mCacheDir, paramStr);
			if (writeBitmapToFile(bitmap, str)) {
				put(paramStr, str);
				flushCache();
			}
		}

	}
	
	/**
	 * 将bitmap写入到文件
	 * @param bitmap
	 * @param paramStr
	 * @return
	 */
	private boolean writeBitmapToFile(Bitmap bitmap, String paramStr) {
		File f = new File(paramStr);
		// if (f.exists()) {
		// f.delete();
		// }
		boolean writeSuccess = false;
		try {
			FileOutputStream out = new FileOutputStream(f);
			writeSuccess = bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return writeSuccess;
	}

	public static String createFilePath(File file, String fileStr) {
		String str = null;
		try {
			str = file.getAbsolutePath() + file.separator + "cache_"
					+ URLEncoder.encode(fileStr.replace("*", ""), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	private void flushCache() {
		int i = 0;
		while (true) {
			if ((i >= 4)
					|| ((this.cacheSize <= 64) && (this.cacheByteSize <= this.maxCacheByteSize)))
				return;
			Map.Entry localEntry = (Map.Entry) this.mLinkedHashMap.entrySet()
					.iterator().next();
			File localFile = new File((String) localEntry.getValue());
			long l = localFile.length();
			this.mLinkedHashMap.remove(localEntry.getKey());
			localFile.delete();
			this.cacheSize = this.mLinkedHashMap.size();
			this.cacheByteSize = (int) (this.cacheByteSize - l);
			++i;
			Log.d("DiskLruCache", "flushCache - Removed cache file, "
					+ localFile + ", " + l);
		}
	}

	private void put(String str1, String str2) {
		mLinkedHashMap.put(str1, str2);
		this.cacheSize = this.mLinkedHashMap.size();
		this.cacheByteSize = (int) (this.cacheByteSize + new File(str2)
				.length());
	}

	public Bitmap get(String url) {
		synchronized (mLinkedHashMap) {
			String str1 = mLinkedHashMap.get(url);
			if (str1 != null) {
				Bitmap bitmap1 = BitmapFactory.decodeFile(str1);
				return bitmap1;
			} else {
				String str2 = createFilePath(this.mCacheDir, url);
				if (!new File(str2).exists()) {
					return null;
				} else {
					Bitmap bitmap2 = BitmapFactory.decodeFile(str2);
					return bitmap2;
				}
			}

		}
	}

}
