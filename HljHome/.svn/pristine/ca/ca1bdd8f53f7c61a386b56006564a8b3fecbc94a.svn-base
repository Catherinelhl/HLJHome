package com.hlj.utils;

import java.io.File;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.URLConnectionImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.widget.ImageView;

/**
 * 异步加载图片
 * 
 * @author huangyuchao
 * 
 */
public class ImageLoader {

	public static final String FOLDER_NAME = "IMAGELOADER";

	Context context;
	private int stub_id;// 默认图片

	DisplayImageOptions options;

	com.nostra13.universalimageloader.core.ImageLoader imageLoader = null;

	private static ImageLoader instance;

	public static ImageLoader getInstance(Context context, int iconDefaultResId) {
		if (instance == null) {
			instance = new ImageLoader(context, iconDefaultResId);
		}
		return instance;
	}

	public ImageLoader(Context context, int iconDefaultResId) {
		// 默认图片
		this.context = context;
		stub_id = iconDefaultResId;
		init();

		instance = this;
	}

	public void init() {
		// File cacheDir = StorageUtils.getOwnCacheDirectory(context,
		// FOLDER_NAME);
		File cacheDir = StorageUtils.getIndividualCacheDirectory(context);
		System.out.println("<------path------>" + cacheDir.getAbsolutePath());

		if (Environment.getExternalStorageDirectory().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			System.out.println("<---------true>" + cacheDir.exists());
		} else {
			System.out.println("<---------false>");
		}
		imageLoader = com.nostra13.universalimageloader.core.ImageLoader
				.getInstance();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				.memoryCacheExtraOptions(720, 1280)
				.threadPoolSize(3)
				.threadPriority(Thread.NORM_PRIORITY - 1)
				.denyCacheImageMultipleSizesInMemory()
				.offOutOfMemoryHandling()
				.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
				.discCache(new UnlimitedDiscCache(cacheDir))
				.discCacheFileNameGenerator(new HashCodeFileNameGenerator())
				.imageDownloader(
						new URLConnectionImageDownloader(5 * 1000, 20 * 1000))
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.enableLogging().build();

		imageLoader.init(config);

		options = new DisplayImageOptions.Builder().showStubImage(stub_id)
				.showImageForEmptyUri(stub_id).cacheInMemory().cacheOnDisc()
				.imageScaleType(ImageScaleType.EXACTLY)
				// .displayer(new RoundedBitmapDisplayer(20))
				.build();
	}

	public void displayImage(String url, ImageView imageView) {
		imageLoader.displayImage(url, imageView, options,
				new ImageLoadingListener() {

					@Override
					public void onLoadingStarted() {
						if (listener != null) {
							listener.onLoadingStarted();
						}
					}

					@Override
					public void onLoadingFailed(FailReason failReason) {
						if (listener != null) {
							listener.onLoadingFailed(failReason);
						}
					}

					@Override
					public void onLoadingComplete(Bitmap loadedImage) {
						if (listener != null) {
							listener.onLoadingComplete(loadedImage);
						}
					}

					@Override
					public void onLoadingCancelled() {
						if (listener != null) {
							listener.onLoadingCancelled();
						}
					}
				});
	}

	public interface OnImageLoadingListener {
		public void onLoadingStarted();

		public void onLoadingFailed(FailReason failReason);

		public void onLoadingComplete(Bitmap loadedImage);

		public void onLoadingCancelled();
	}

	OnImageLoadingListener listener = null;

	public void setOnLoadListtener(OnImageLoadingListener listener) {
		this.listener = listener;
	}
}
