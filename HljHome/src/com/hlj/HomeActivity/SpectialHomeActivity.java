package com.hlj.HomeActivity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.HttpHandler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.hj.widget.CommonToast;
import com.hlj.adapter.AllPagesAdapter;
import com.hlj.net.BitmapWorkerTask;
import com.hlj.net.GetAllAppVersionRequest;
import com.hlj.net.GetAllAppVersionResponse;
import com.hlj.net.GetCheckUpdateFirmwareRequest;
import com.hlj.net.GetCheckUpdateFirmwareResponse;
import com.hlj.net.GetCheckUpdateLauncherRequest;
import com.hlj.net.GetCheckUpdateLauncherResponse;
import com.hlj.net.GetCustomMetroDataRequest;
import com.hlj.net.GetCustomNavigationRequest;
import com.hlj.net.GetCustomResponse;
import com.hlj.net.GetRecmodMetroDataResponse;
import com.hlj.net.GetShouCangMetroDataResponse;
import com.hlj.net.GetTvMetroDataResponse;
import com.hlj.net.HttpHomeLoadDataTask;
import com.hlj.net.BitmapWorkerTask.ImageCallBack;
import com.hlj.utils.ACache;
import com.hlj.utils.FileUtils;
import com.hlj.utils.ImageLoader;
import com.hlj.utils.LauncherBiz;
import com.hlj.utils.Logger;
import com.hlj.utils.PrefrenceHandler;
import com.hlj.utils.StringTools;
import com.hlj.utils.StringUtils;
import com.hlj.utils.TimerTools;
import com.hlj.view.ApplicationLayout;
import com.hlj.view.CommonTitleView;
import com.hlj.view.FavRecodLayout;
import com.hlj.view.LatesHotLayout;
import com.hlj.view.ScaleAnimEffect;
import com.hlj.view.SettingsLayout;
import com.hlj.view.StudyLayout;
import com.hlj.view.TvShowLayout;
import com.hlj.view.VersionSwitchLayout;
import com.hlj.view.VideoInfo;
import com.hlj.view.VideoTypeLayout;
import com.hlj.widget.UpdateDialog;
import com.live.video.constans.DeviceInfo;
import com.live.video.constans.HomeConstants;
import com.live.video.net.GetFilmMetroDataResponse;
import com.live.video.net.callback.IUpdateData;

/**
 * 针对友诺的版本
 * 
 * @author hyc
 * 
 */
public class SpectialHomeActivity extends BaseActivity {

	ViewPager main_layout_pager;

	AllPagesAdapter adapter;

	private ArrayList<View> pages = new ArrayList();
	
	ScaleAnimEffect animEffect = new ScaleAnimEffect();

	private LatesHotLayout hot;// 最新推荐
	private StudyLayout studyLayout;// 教学教育
	private TvShowLayout live;// 网络电视
	private VideoTypeLayout videoType;// 影视分类
	// private VodRecodLayout vodRecord;// 我的收藏
	private VersionSwitchLayout versionSwitch;// 版本切换
	private FavRecodLayout favRecod;// 我的收藏
	private ApplicationLayout appManager;// 应用程序

	// private ApplicationLayoutTest appTest;
	private SettingsLayout set;// 设置

	ArrayList<VideoInfo> tjRecInfos = new ArrayList<VideoInfo>();
	ArrayList<VideoInfo> tjTeachInfos = new ArrayList<VideoInfo>();
	ArrayList<VideoInfo> tjCollectionInfos = new ArrayList<VideoInfo>();
	ArrayList<VideoInfo> tvInfos = new ArrayList<VideoInfo>();
	ArrayList<VideoInfo> tjFilmInfos = new ArrayList<VideoInfo>();

	RadioGroup radioGroup;

	public static ImageLoader imageLoader;

	LinearLayout ll_all;

	CommonTitleView titleView;

	RelativeLayout rl_bg;

	String pageBgUrl;

	ACache cache;

	DisplayMetrics dm;

	int screenWidthDip;

	public static SpectialHomeActivity instance;

	CommonToast toast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_spectial_home);
		titleView = (CommonTitleView) findViewById(R.id.commonTitle);

		instance = this;

		// 每次重启的时候临时加锁状态
		HomeConstants.isTempLock = HomeConstants.HAS_LOCK;
		// HomeConstants.isPrimaryLock="2";

		try {
			FileUtils.saveData(HomeConstants.isTempLock,
					HomeConstants.isTempLockPath);

		} catch (Exception e) {
			Logger.log(e.toString());
		}

		File file = new File(HomeConstants.isPrimaryLockPath);

		if (!file.exists()) {
			HomeConstants.isPrimaryLock = HomeConstants.HAS_LOCK;

			try {
				FileUtils.saveData(HomeConstants.isPrimaryLock,
						HomeConstants.isPrimaryLockPath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		hot = new LatesHotLayout(this);
		// this.hot.setTopRecommends(tjRecInfos);
		this.hot.requestFocus();
		studyLayout = new StudyLayout(this);
		live = new TvShowLayout(this);
		videoType = new VideoTypeLayout(this);
		// vodRecord = new VodRecodLayout(this);
		versionSwitch = new VersionSwitchLayout(this);
		favRecod = new FavRecodLayout(this);
		appManager = new ApplicationLayout(this);
		set = new SettingsLayout(this);

		this.hot.initView();
		this.studyLayout.initView();
		this.live.initView();
		this.videoType.initView();
		// this.vodRecord.initView();
		this.favRecod.initView();
		this.appManager.initView();
		this.set.initView();

		ll_all = (LinearLayout) findViewById(R.id.ll_all);

		radioGroup = (RadioGroup) findViewById(R.id.title_group);
		radioGroup.requestFocus();
		this.radioGroup.setFocusable(true);
		this.radioGroup.check(R.id.latest_recommend);

		main_layout_pager = (ViewPager) findViewById(R.id.main_layout_pager);

		main_layout_pager.setCurrentItem(0);
		main_layout_pager.setOffscreenPageLimit(2);
		// main_layout_pager.setNextFocusUpId(R.id.title_group);

		// loadData();

		initListener();

		adapter = new AllPagesAdapter(pages);
		main_layout_pager.setAdapter(adapter);

		rl_bg = (RelativeLayout) findViewById(R.id.rl_bg);

		cache = ACache.get(this);

		pageBgUrl = cache.getAsString("pageBg");

		Logger.log("pageBgUrl:" + pageBgUrl);

		updateData();

		dm = new DisplayMetrics();
		// dm = getResources().getDisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidthDip = dm.widthPixels;

	}

	ImageCallBack pageBgcallback = new ImageCallBack() {

		@Override
		public void post(Bitmap bitmap) {
			Drawable drawable = new BitmapDrawable(bitmap);
			rl_bg.setBackgroundDrawable(drawable);

		}

	};

	Handler updateLauncherHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (0 == msg.what) {
				GetCheckUpdateLauncherRequest checkRequest = new GetCheckUpdateLauncherRequest(
						SpectialHomeActivity.this);
				new HttpHomeLoadDataTask(SpectialHomeActivity.this,
						updateCallBack, false, "", true).execute(checkRequest);
			}
			if (1 == msg.what) {
				// Intent intent=new
				// Intent(HomeActivity.this,UpdateService.class);
				// bindService(intent,mConn, Service.BIND_AUTO_CREATE);

				GetCheckUpdateFirmwareRequest request = new GetCheckUpdateFirmwareRequest(
						SpectialHomeActivity.this);
				new HttpHomeLoadDataTask(SpectialHomeActivity.this,
						firewareCallBack, false, "", true).execute(request);
			}
			if (2 == msg.what) {
				GetAllAppVersionRequest gaavrRequest = new GetAllAppVersionRequest(
						SpectialHomeActivity.this);
				new HttpHomeLoadDataTask(SpectialHomeActivity.this,
						allAppVersionCallBack, false, "", true)
						.execute(gaavrRequest);
			}
		};
	};

	String timename;

	IUpdateData allAppVersionCallBack = new IUpdateData() {

		@Override
		public void updateUi(Object o) {
			GetAllAppVersionResponse response = new GetAllAppVersionResponse();
			response.paseRespones(o.toString());

			// Launcher升级
			url = response.launcher.zipUrl;
			forceUpdateCode = response.launcher.forceUpdateCode;
			ver = response.launcher.ver;
			message = response.launcher.message;
			extensionName = StringUtils.getExtensionName(url);
			timename = TimerTools.getDayStr(new Date().getTime());
			fileName = "HljHome-" + timename + "-" + ver + extensionName;
			if (StringUtils.compare(
					DeviceInfo.getVerName(getApplicationContext()), ver)) {
				if ("yes".equals(forceUpdateCode)) {
					downloadapk(fileName, url);

					return;
				} else {
					updateDialog = new UpdateDialog(SpectialHomeActivity.this,
							message, url, fileName, extensionName);
					updateDialog.show();
					return;
				}
			}

			// 固件升级
			prefrenceHandler = new PrefrenceHandler(SpectialHomeActivity.this);
			mPosition = prefrenceHandler.getDownloadPos();
			String fiewVer = response.fireware.ver;
			zipUrl = response.fireware.zipUrl;
			String fireName = "firmware-" + fiewVer
					+ StringUtils.getExtensionName(zipUrl);
			prefrenceHandler.setDownloadTarget(target + fireName);
			if (StringUtils.compare(DeviceInfo.getFirmWare(), fiewVer)) {
				// 后台下载zip包(支持断点续传)
				afinalDownLoadZip(zipUrl);
				return;
			}

			// 本地应用升级
			String localurl = response.localApps.zipUrl;
			String localForceUpdateCode = response.localApps.forceUpdateCode;
			String localVer = response.localApps.ver;
			Logger.log("localVer:" + localVer);
			String localmessage = response.localApps.message;
			extensionName = StringUtils.getExtensionName(localurl);
			String localfileName = "localApp-" + timename + "-" + localVer
					+ extensionName;

			try {
				Context localAppContext = createPackageContext(
						"com.home.homeaccpication",
						Context.CONTEXT_IGNORE_SECURITY
								| Context.CONTEXT_INCLUDE_CODE);
				Logger.log("比较成功否："
						+ StringUtils.compare(
								DeviceInfo.getVerName(localAppContext),
								localVer));
				// <<<<<<< .mine
				// if
				// (StringUtils.compare(DeviceInfo.getVerName(localAppContext),
				// ver)) {
				// // if ("yes".equals(forceUpdateCode)) {
				// // downloadapk();
				// // } else {
				// if (updateDialog == null) {
				// updateDialog = new UpdateDialog(
				// SpectialHomeActivity.this, message, url,
				// fileName, extensionName);
				// updateDialog.show();
				// =======
				if (StringUtils.compare(DeviceInfo.getVerName(localAppContext),
						localVer)) {
					if ("yes".equals(localForceUpdateCode)) {
						// ----------------先卸载
						downloadapk(localfileName, localurl);
						return;
						// >>>>>>> .r64
					} else {
						updateDialog = new UpdateDialog(
								SpectialHomeActivity.this, localmessage,
								localurl, localfileName, extensionName);
						updateDialog.show();
						return;
					}

				}

			} catch (NameNotFoundException e1) {
				// <<<<<<< .mine
				// Logger.log("NameNotFoundException:" + e1.toString());
				// e1.printStackTrace();
				// =======
				Logger.log("NameNotFoundException:" + e1.toString());
				// e1.printStackTrace();
				// >>>>>>> .r64
			}

			// 应用商城升级
			String shopurl = response.shopApps.zipUrl;
			String shopForceUpdateCode = response.shopApps.forceUpdateCode;
			String shopVer = response.shopApps.ver;
			Logger.log("shopVer:" + shopVer);
			String shopMessage = response.shopApps.message;
			extensionName = StringUtils.getExtensionName(shopurl);
			// 不能用中文
			String shopFileName = "AppCenter-" + timename + "-" + shopVer
					+ extensionName;

			try {
				Context shopContext = createPackageContext(
						"com.home.appcenter", Context.CONTEXT_IGNORE_SECURITY
								| Context.CONTEXT_INCLUDE_CODE);

				if (StringUtils.compare(DeviceInfo.getVerName(shopContext),
						shopVer)) {
					if ("yes".equals(shopForceUpdateCode)) {
						downloadapk(shopFileName, shopurl);
						return;
					} else {
						updateDialog = new UpdateDialog(
								SpectialHomeActivity.this, shopMessage,
								shopurl, shopFileName, extensionName);
						updateDialog.show();
						return;

					}
				}
			} catch (NameNotFoundException e) {
				Logger.log("NameNotFoundException:" + e.toString());
				// e.printStackTrace();
			}

		}

		@Override
		public void handleErrorData(String info) {
			// TODO Auto-generated method stub

		}
	};

	// <<<<<<< .mine
	// =======

	public void haha() {

	}

	public void test() {

	}

	// >>>>>>> .r64

	PrefrenceHandler prefrenceHandler;
	private long mPosition = 0;

	String zipUrl;

	IUpdateData firewareCallBack = new IUpdateData() {

		@Override
		public void updateUi(Object o) {

			GetCheckUpdateFirmwareResponse response = new GetCheckUpdateFirmwareResponse();
			response.paseRespones(o.toString());

			String ver = response.updateInfo.ver;
			zipUrl = response.updateInfo.zipUrl;

			String fireName = "firmware-" + ver
					+ StringUtils.getExtensionName(zipUrl);

			prefrenceHandler.setDownloadTarget(target + fireName);

			if (StringUtils.compare(DeviceInfo.getFirmWare(), ver)) {
				// 后台下载zip包(支持断点续传)

				// if (!isDownloading) {
				// new downloadThread().start();
				// isDownloading = true;
				// }

				afinalDownLoadZip(zipUrl);
			}

		}

		@Override
		public void handleErrorData(String info) {
			// TODO Auto-generated method stub

		}
	};

	// 判断是否正在下载
	public static boolean isDownloading = false;

	public class downloadThread extends Thread {

		@Override
		public void run() {
			downloadZip(zipUrl);
			super.run();
		}

	}

	RandomAccessFile mRandomAccessFile;

	public void afinalDownLoadZip(String zipUrl) {
		httpHandler = finalHttp.download(zipUrl,
				prefrenceHandler.getDownloadTarget(), true,
				new AjaxCallBack<File>() {
					@Override
					public void onStart() {
						Logger.log("开始下载");
						super.onStart();
					}

					@Override
					public void onLoading(long count, long current) {
						Logger.log(current + "/" + count);
						super.onLoading(count, current);
					}

					@Override
					public void onSuccess(File t) {
						Logger.log("下载成功");
						Logger.log(t.getAbsolutePath() + "<><><><><><>");
						goToRevovery();
						super.onSuccess(t);
					}

					@Override
					public void onFailure(Throwable t, String strMsg) {
						// TODO Auto-generated method stub
						super.onFailure(t, strMsg);
					}
				});
	}

	public void downloadZip(String zipUrl) {
		try {
			URL url = new URL(zipUrl);
			String mTargetFile = prefrenceHandler.getDownloadTarget();

			mRandomAccessFile = new RandomAccessFile(mTargetFile, "rw");

			Logger.log("zipUrl:" + zipUrl + "   ,   mTargetFile:" + mTargetFile);

			byte[] buf = new byte[1024 * 1024 * 8];
			HttpURLConnection cn = (HttpURLConnection) url.openConnection();
			int mFileSize = cn.getContentLength();
			Logger.log("mFileSize:" + mFileSize);
			if (mFileSize < 0) {
				Logger.log("mFileSize <0");
			}

			mRandomAccessFile.setLength(mFileSize);
			Logger.log("mPosition:" + mPosition);
			mRandomAccessFile.seek(mPosition);
			cn = (HttpURLConnection) url.openConnection();
			cn.setRequestProperty("Range", "bytes=" + mPosition + "-"
					+ mFileSize);
			BufferedInputStream bis = new BufferedInputStream(
					cn.getInputStream());

			int len;
			Logger.log("===========开始下载=============");
			while ((len = bis.read(buf)) > 0) {
				Thread.sleep(1000);
				synchronized (mRandomAccessFile) {
					mRandomAccessFile.write(buf, 0, len);
					mPosition += len;
					Logger.log("percent:" + mPosition + "/" + mFileSize);
					prefrenceHandler.setDownLoadPos(mPosition);
				}
			}

			if (mPosition >= mFileSize) {
				Logger.log("===========开始完成=============");
				goToRevovery();
			}

		} catch (Exception e) {
			Logger.log("error:" + e.toString());
		}
	}

	public void goToRevovery() {
		// 下载完成(升级)
		Intent intent = new Intent("com.softwinner.settingsassist.Recovery");

		intent.setAction("softwinner.intent.action.RECOVREY");
		startActivityForResult(intent, 0);
	}

	private ServiceConnection mConn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub

		}
	};

	FinalHttp finalHttp = new FinalHttp();
	HttpHandler httpHandler;

	String target = "/mnt/sdcard/";
	String extensionName;
	String fileName;
	String url;
	String forceUpdateCode;
	String ver;
	String message;

	UpdateDialog updateDialog;

	IUpdateData updateCallBack = new IUpdateData() {

		@Override
		public void updateUi(Object o) {
			// final GetCheckUpdateLauncherResponse response = new
			// GetCheckUpdateLauncherResponse();
			// response.paseRespones(o.toString());
			// // Toast.makeText(HomeActivity.this, "升级", 1000);
			//
			// url = response.updateLaInfo.zipUrl;
			// forceUpdateCode = response.updateLaInfo.forceUpdateCode;
			// ver = response.updateLaInfo.ver;
			// message = response.updateLaInfo.message;
			// extensionName = StringUtils.getExtensionName(url);
			// // fileName = StringUtils.getFileName(url);
			//
			// String name = TimerTools.getDayStr(new Date().getTime());
			//
			// fileName = "HljHome-" + name + "-" + ver + extensionName;
			//
			// if (StringUtils.compare(
			// DeviceInfo.getVerName(SpectialHomeActivity.this), ver)) {
			// if ("yes".equals(forceUpdateCode)) {
			// downloadapk(fileName);
			// } else {
			// if (updateDialog == null) {
			// updateDialog = new UpdateDialog(
			// SpectialHomeActivity.this, message, url,
			// fileName, extensionName);
			// updateDialog.show();
			// } else {
			// if (!updateDialog.isShowing()) {
			// updateDialog.show();
			// }
			// }
			// }
			// }

		}

		@Override
		public void handleErrorData(String info) {
			// TODO Auto-generated method stub

		}

	};

	/**
	 * 下载
	 * 
	 * @param url
	 */
	public void downloadapk(final String fileName, final String fileUrl) {
		Logger.log("currentFileName:" + fileName + ",fileUrl:" + fileUrl);
		// 判断文件是否存在(如果存在则删除)
		try {
			boolean isFileExist = FileUtils.delectFile(target + fileName);
			Logger.log("isFileExist:" + isFileExist);
		} catch (IOException e) {
			Logger.log("deleteFoder failed:" + e.getLocalizedMessage());
		}

		httpHandler = finalHttp.download(fileUrl, target + fileName, true,
				new AjaxCallBack<File>() {
					@Override
					public void onStart() {
						Logger.log("开始下载");
					}

					@Override
					public void onLoading(long count, long current) {
						Logger.log(current + "/" + count);
					}

					@Override
					public void onSuccess(final File t) {
						Logger.log("下载成功");
						Logger.log(t.getAbsolutePath() + "<><><><><><>");

						// 如果是apk的话就执行安装
						if (".apk".equals(extensionName)) {
							// 强制安装
							boolean isInstall = LauncherBiz
									.execCommand("system/bin/pm install -r "
											+ "mnt/sdcard/" + fileName);

							Logger.log("isInstall:" + isInstall);
						} else {
							Logger.log("下载文件不是apk文件");
							toast.setText("下载文件不是apk文件");
							toast.setIcon(R.drawable.toast_err);
							toast.show();
						}
					}

					@Override
					public void onFailure(Throwable t, String strMsg) {
						Logger.log("下载apk失败");
						Logger.log(strMsg + "=====================");
						toast.setText("下载apk失败");
						toast.setIcon(R.drawable.toast_err);
						toast.show();
					}

				});
	}

	public boolean isUpdate = false;

	@Override
	protected void onResume() {
		Logger.log("-------------onResume()---------------");

		// if ("launcher".equals(PreActivity.has_update)) {
		updateLauncherHandler.sendEmptyMessageDelayed(2, 1 * 60 * 1000);
		// }

		// updateLauncherHandler.sendEmptyMessageDelayed(1, 5 * 60 * 1000);
		CommonTitleView.instance = titleView;

		// 读取

		// updateData();

		// 单独更新收藏
		// favRecod.updateTestData();
		// true
		// if (isUpdate) {
		// getFavHandler.sendEmptyMessage(0);
		// }

		// favRecod.postInvalidate();
		// favRecod.invalidate();

		super.onResume();
	}

	@Override
	protected void onDestroy() {
		if (null != hot) {
			hot.destroy();
		}
		if (null != studyLayout) {
			studyLayout.destroy();
		}
		if (null != live) {
			live.destroy();
		}
		if (null != videoType) {
			videoType.destroy();
		}
		// if (null != music) {
		// music.destroy();
		// }
		if (null != appManager) {
			appManager.destroy();
		}
		if (null != favRecod) {
			favRecod.destroy();
		}
		super.onDestroy();
	}

	/**
	 * 获取栏目
	 */
	public void getCustomNavigationTab() {
		GetCustomNavigationRequest navi = new GetCustomNavigationRequest(this);
		new HttpHomeLoadDataTask(this, customCallBack, false, "", false)
				.execute(navi);
	}

	IUpdateData customCallBack = new IUpdateData() {

		@Override
		public void updateUi(Object o) {
			// Logger.log(o.toString());
			// radioGroup.requestFocus();
			radioGroup.requestFocus();
			radioGroup.setFocusable(true);

			GetCustomResponse response = new GetCustomResponse();
			response.paseRespones(o.toString());

			Logger.log("count:" + response.count + "," + response.list.size());

			for (int i = 0; i < response.list.size(); i++) {
				String title = response.list.get(i).title;
				String metroId = response.list.get(i).metroID;
				String mode = response.list.get(i).mode;

				RadioButton radioBtn = (RadioButton) LayoutInflater.from(
						SpectialHomeActivity.this).inflate(
						R.layout.home_radiobutton, null);

				radioBtn.setWidth((screenWidthDip - 120) / response.list.size());
				radioBtn.setTag(metroId);
				radioBtn.setText(title);
				radioGroup.addView(radioBtn);

				if ("special".equals(mode)) {
					radioBtn.setId(R.id.study_type);
					pages.add(studyLayout);
				} else if ("livevideo".equals(mode)) {
					radioBtn.setId(R.id.tv_show);
					pages.add(live);
				} else if ("settingversions".equals(mode)) {
					radioBtn.setId(R.id.version_switch);
					pages.add(versionSwitch);
				} else if ("film".equals(mode)) {
					radioBtn.setId(R.id.video_type);
					pages.add(videoType);
				} else if ("favorite".equals(mode)) {
					radioBtn.setId(R.id.play_collect);
					pages.add(favRecod);
				} else if ("appdesk".equals(mode)) {
					radioBtn.setId(R.id.application);
					pages.add(appManager);
				} else if ("setting".equals(mode)) {
					radioBtn.setId(R.id.settings);
					pages.add(set);
				}

				else if ("recommend".equals(mode)) {
					radioBtn.setId(R.id.latest_recommend);
					pages.add(hot);
					radioGroup.check(i);
					radioBtn.setChecked(true);
					radioGroup.requestFocus();
					radioGroup.setFocusable(true);
					radioBtn.requestFocus();
					radioBtn.setFocusable(true);
					radioBtn.setFocusableInTouchMode(true);
				}

			}

			initListener();
		}

		@Override
		public void handleErrorData(String info) {
			isMove = false;
		}
	};

	Handler recRemHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			getRecomMetroData("recommend");
		};
	};

	Handler getTeachHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			getTeachMetroData("teaching");
		};
	};

	Handler getTvHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			getTvMetroData("tv");
		};
	};

	Handler getFilmHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			getFilmMetroData("film");
		};
	};

	Handler getFavHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			getFavMetroData("favorite");
		};
	};

	/**
	 * 获取收藏内容
	 * 
	 * @param metroID
	 */
	public void getFavMetroData(String metroID) {
		GetCustomMetroDataRequest request = new GetCustomMetroDataRequest(this);
		request.metroID = metroID;
		// HttpHomeLoadDataTask.getInstance().setContext(this);
		// HttpHomeLoadDataTask.getInstance().setIUpdateData(metroFavCallBack);
		// HttpHomeLoadDataTask.getInstance().setShownDialog(false);
		// HttpHomeLoadDataTask.getInstance().setRequestUrl("");
		// HttpHomeLoadDataTask.getInstance().executeOnExecutor(
		// AsyncTask.THREAD_POOL_EXECUTOR, request);
		new HttpHomeLoadDataTask(this, metroFavCallBack, false, "", false)
				.execute(request);
	}

	IUpdateData metroFavCallBack = new IUpdateData() {

		@Override
		public void updateUi(Object o) {

			GetShouCangMetroDataResponse response = new GetShouCangMetroDataResponse();
			response.paseRespones(o.toString());
			tjCollectionInfos.clear();
			// favRecod.listClear();
			for (int i = 0; i < response.list.size(); i++) {
				videoInfo = new VideoInfo();
				videoInfo.title = response.list.get(i).title;
				videoInfo.imageUrl = response.list.get(i).imageurl;
				videoInfo.childLock = response.list.get(i).childLock;
				videoInfo.url = response.list.get(i).extra.url;
				videoInfo.catId = response.list.get(i).item.catId;
				videoInfo.type = response.list.get(i).item.type;
				tjCollectionInfos.add(videoInfo);
				// tjCollectionInfos.add(videoInfo);

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("title", videoInfo.title);
				map.put("imageUrl", videoInfo.imageUrl);
				map.put("childLock", videoInfo.childLock);
				map.put("url", videoInfo.url);
				map.put("catId", videoInfo.catId);
				map.put("type", videoInfo.type);

				favRecod.setMap(map);
			}
			favRecod.updateData();
			// favRecod.setList(tjCollectionInfos);

			// hot.firstGetFocusable();
		}

		@Override
		public void handleErrorData(String info) {
			isMove = false;
		}

	};

	Handler moveHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			isMove = false;
			Logger.log("-----------------moveHandler--------------");
			HttpHomeLoadDataTask.instance.setMoreDialog(false);
			super.handleMessage(msg);
		}

	};

	public static boolean isMove = true;

	// public boolean onKeyDown(int arg0, android.view.KeyEvent arg1) {
	// switch (arg0) {
	// case KeyEvent.KEYCODE_DPAD_LEFT:
	//
	// return isMove;
	// case KeyEvent.KEYCODE_DPAD_RIGHT:
	//
	// return isMove;
	// case KeyEvent.KEYCODE_DPAD_UP:
	//
	// return isMove;
	// case KeyEvent.KEYCODE_DPAD_DOWN:
	//
	// return isMove;
	// }
	// return super.onKeyDown(arg0, arg1);
	// };

	/**
	 * 获取教学教育内容
	 */
	public void getTeachMetroData(String metroID) {
		GetCustomMetroDataRequest request = new GetCustomMetroDataRequest(this);
		request.metroID = metroID;
		// HttpHomeLoadDataTask.getInstance().setContext(this);
		// HttpHomeLoadDataTask.getInstance().setIUpdateData(metroTeachCallBack);
		// HttpHomeLoadDataTask.getInstance().setShownDialog(false);
		// HttpHomeLoadDataTask.getInstance().setRequestUrl("");
		// HttpHomeLoadDataTask.getInstance().executeOnExecutor(
		// AsyncTask.THREAD_POOL_EXECUTOR, request);

		new HttpHomeLoadDataTask(this, metroTeachCallBack, false, "", false)
				.execute(request);
	}

	IUpdateData metroTeachCallBack = new IUpdateData() {

		@Override
		public void updateUi(Object o) {
			// radioGroup.requestFocus();
			// radioGroup.setFocusable(true);
			GetRecmodMetroDataResponse response = new GetRecmodMetroDataResponse();
			response.paseRespones(o.toString());
			tjTeachInfos.clear();
			for (int i = 0; i < response.list.size(); i++) {
				videoInfo = new VideoInfo();
				videoInfo.title = response.list.get(i).title;
				videoInfo.shortTilte = response.list.get(i).shortTitle;
				videoInfo.imageUrl = response.list.get(i).imageurl;
				videoInfo.action = response.list.get(i).extra.action;
				videoInfo.url = response.list.get(i).extra.url;
				videoInfo.catId = response.list.get(i).item.catId;
				videoInfo.contentId = response.list.get(i).item.contentId;
				videoInfo.grade = response.list.get(i).item.grade;
				videoInfo.type = response.list.get(i).item.type;
				videoInfo.subject = response.list.get(i).item.subject;
				videoInfo.versions = response.list.get(i).item.versions;
				videoInfo.childLock = response.list.get(i).childLock;
				tjTeachInfos.add(videoInfo);
			}
			studyLayout.setList(tjTeachInfos);
			studyLayout.updateData();

			// hot.firstGetFocusable();
			// isMove=false;

		}

		@Override
		public void handleErrorData(String info) {
			isMove = false;
		}

	};

	/**
	 * 获取影视分类内容
	 */
	public void getFilmMetroData(String metroID) {
		GetCustomMetroDataRequest request = new GetCustomMetroDataRequest(this);
		request.metroID = metroID;
		// HttpHomeLoadDataTask.getInstance().setContext(this);
		// HttpHomeLoadDataTask.getInstance().setIUpdateData(metroFilmCallBack);
		// HttpHomeLoadDataTask.getInstance().setShownDialog(false);
		// HttpHomeLoadDataTask.getInstance().setRequestUrl("");
		// HttpHomeLoadDataTask.getInstance().executeOnExecutor(
		// AsyncTask.THREAD_POOL_EXECUTOR, request);
		new HttpHomeLoadDataTask(this, metroFilmCallBack, false, "", false)
				.execute(request); 
	}

	IUpdateData metroFilmCallBack = new IUpdateData() {

		@Override
		public void updateUi(Object o) {
			// radioGroup.requestFocus();
			// radioGroup.setFocusable(true);
			GetFilmMetroDataResponse response = new GetFilmMetroDataResponse();
			response.paseRespones(o.toString());
			tjFilmInfos.clear();
			for (int i = 0; i < response.list.size(); i++) {
				videoInfo = new VideoInfo();
				videoInfo.title = response.list.get(i).title;
				videoInfo.shortTilte = response.list.get(i).shortTitle;
				videoInfo.imageUrl = response.list.get(i).imageurl;
				videoInfo.action = response.list.get(i).extra.action;
				videoInfo.catId = response.list.get(i).item.catId;
				videoInfo.contentId = response.list.get(i).item.contentId;
				videoInfo.url = response.list.get(i).item.url;
				videoInfo.childLock = response.list.get(i).childLock;
				tjFilmInfos.add(videoInfo);
			}
			videoType.setList(tjFilmInfos);
			videoType.updateData();

			moveHandler.sendEmptyMessageDelayed(0, 5 * 1000);
			// hot.firstGetFocusable();
			// isMove=false;
		}

		@Override
		public void handleErrorData(String info) {
			isMove = false;
			moveHandler.sendEmptyMessageDelayed(0, 5 * 1000);
		}

	};

	/**
	 * 获取metro的内容(推荐)
	 * 
	 * @param metroID
	 */
	public void getRecomMetroData(String metroID) {

		GetCustomMetroDataRequest request = new GetCustomMetroDataRequest(this);
		request.metroID = metroID;
		// HttpHomeLoadDataTask.getInstance().setContext(this);
		// HttpHomeLoadDataTask.getInstance().setIUpdateData(metroRecomCallBack);
		// HttpHomeLoadDataTask.getInstance().setShownDialog(true);
		// HttpHomeLoadDataTask.getInstance().setRequestUrl("");
		// HttpHomeLoadDataTask.getInstance().executeOnExecutor(
		// AsyncTask.THREAD_POOL_EXECUTOR, request);

		new HttpHomeLoadDataTask(this, metroRecomCallBack, true, "", false)
				.execute(request);
		HttpHomeLoadDataTask.instance.setMoreDialog(true);
	}

	IUpdateData metroRecomCallBack = new IUpdateData() {

		@Override
		public void updateUi(Object o) {
			// radioGroup.requestFocus();
			// radioGroup.setFocusable(true);
			tjRecInfos.clear();
			GetRecmodMetroDataResponse response = new GetRecmodMetroDataResponse();
			response.paseRespones(o.toString());

			for (int i = 0; i < response.list.size(); i++) {
				videoInfo = new VideoInfo();
				videoInfo.title = response.list.get(i).title;
				videoInfo.shortTilte = response.list.get(i).shortTitle;
				videoInfo.imageUrl = response.list.get(i).imageurl;
				videoInfo.action = response.list.get(i).extra.action;
				videoInfo.url = response.list.get(i).extra.url;
				videoInfo.catId = response.list.get(i).item.catId;
				videoInfo.contentId = response.list.get(i).item.contentId;
				videoInfo.grade = response.list.get(i).item.grade;
				videoInfo.type = response.list.get(i).item.type;
				videoInfo.subject = response.list.get(i).item.subject;
				videoInfo.versions = response.list.get(i).item.versions;
				videoInfo.childLock = response.list.get(i).childLock;
				videoInfo.mode = response.list.get(i).mode;
				tjRecInfos.add(videoInfo);
				Logger.log(tjRecInfos + "");
			}
			System.out.println("size:" + tjRecInfos.size());
			hot.setTopRecommends(tjRecInfos);
			loadData();

			// hot.firstGetFocusable();
			// isMove=false;
		}

		@Override
		public void handleErrorData(String info) {
			isMove = false;
		}

	};

	/**
	 * 获取tv的内容
	 * 
	 * @param metroID
	 */
	public void getTvMetroData(String metroID) {
		GetCustomMetroDataRequest request = new GetCustomMetroDataRequest(this);
		request.metroID = metroID;
		// HttpHomeLoadDataTask.getInstance().setContext(this);
		// HttpHomeLoadDataTask.getInstance().setIUpdateData(metroTvCallBack);
		// HttpHomeLoadDataTask.getInstance().setShownDialog(false);
		// HttpHomeLoadDataTask.getInstance().setRequestUrl("");
		// HttpHomeLoadDataTask.getInstance().executeOnExecutor(
		// AsyncTask.THREAD_POOL_EXECUTOR, request);

		new HttpHomeLoadDataTask(this, metroTvCallBack, false, "", false)
				.execute(request);
	}

	IUpdateData metroTvCallBack = new IUpdateData() {

		@Override
		public void updateUi(Object o) {
			// radioGroup.requestFocus();
			// radioGroup.setFocusable(true);
			GetTvMetroDataResponse response = new GetTvMetroDataResponse();
			response.paseRespones(o.toString());
			tvInfos.clear();
			for (int i = 0; i < response.list.size(); i++) {
				videoInfo = new VideoInfo();
				videoInfo.title = response.list.get(i).title;
				videoInfo.imageUrl = response.list.get(i).imageurl;
				videoInfo.childLock = response.list.get(i).childLock;
				tvInfos.add(videoInfo);
			}
			live.setList(tvInfos);
			live.updateData();

			hot.firstGetFocusable();
			// isMove=false;
		}

		@Override
		public void handleErrorData(String info) {
			isMove = false;

		}

	};

	private ColorStateList createColorStateList(int focused, int selected,
			int checked, int normal) {
		int[] colors = new int[] { focused, selected, checked, normal };
		int[][] states = new int[4][];
		states[0] = new int[] { android.R.attr.state_focused };
		states[1] = new int[] { android.R.attr.state_selected };
		states[2] = new int[] { android.R.attr.state_checked };
		states[3] = new int[] {};
		ColorStateList colorList = new ColorStateList(states, colors);
		return colorList;
	}

	public void initListener() {
		radioGroup.requestFocus();
		radioGroup.setFocusable(true);
		Logger.log("count:" + radioGroup.getChildCount());
		for (int i = 0; i < radioGroup.getChildCount(); i++) {
			View localView = this.radioGroup.getChildAt(i);
			localView.setTag(Integer.valueOf(i));
			localView
					.setOnFocusChangeListener(new View.OnFocusChangeListener() {

						@Override
						public void onFocusChange(View v, boolean hasFocus) {

							if (hasFocus) {

								int i = Integer.valueOf(String.valueOf(v
										.getTag()));
								main_layout_pager.setCurrentItem(i, true);

								v.requestFocus();
								v.setFocusable(true);
							}
						}
					});

			main_layout_pager
					.setOnPageChangeListener(new OnPageChangeListener() {

						@Override
						public void onPageSelected(int position) {
							if (position < radioGroup.getChildCount()) {
								((RadioButton) radioGroup.getChildAt(position))
										.setChecked(true);
							}

						}

						@Override
						public void onPageScrolled(int arg0, float arg1,
								int arg2) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onPageScrollStateChanged(int arg0) {
							// TODO Auto-generated method stub

						}
					});

		}

	}

	VideoInfo videoInfo;

	public void loadData() {
		this.hot.loadData();
	}

	@Override
	protected void onNewIntent(Intent intent) {

		// updateData();

		super.onNewIntent(intent);
	}

	/**
	 * 更新数据
	 */
	public void updateData() {

		getCustomNavigationTab();

		recRemHandler.sendEmptyMessageDelayed(0, 1 * 1000);
		getTeachHandler.sendEmptyMessageDelayed(0, 2 * 1000);
		getTvHandler.sendEmptyMessageDelayed(0, 3 * 1000);
		getFavHandler.sendEmptyMessageDelayed(0, 4 * 1000);
		getFilmHandler.sendEmptyMessageDelayed(0, 4 * 1000);

		if (!StringTools.isNullOrEmpty(pageBgUrl)) {
			new BitmapWorkerTask(this, rl_bg, true, true, pageBgcallback)
					.execute(new String[] { pageBgUrl });
		}

	}

	@Override
	public void onBackPressed() {

		// super.onBackPressed();
	}

}
