package com.hlj.HomeActivity;

import io.vov.vitamio.LibsChecker;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.json.JSONObject;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.HttpHandler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hlj.HomeActivity.MinHomeActivity.Job;
import com.hlj.adapter.AllPagesAdapter;
import com.hlj.net.BitmapWorkerTask;
import com.hlj.net.BitmapWorkerTask.ImageCallBack;
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
import com.hlj.net.GetCustomResponse.Custom;
import com.hlj.receiver.WeatherReceiver;
import com.hlj.receiver.WeatherReceiver.WeatherUpdateListener;
import com.hlj.service.ThreadTask;
import com.hlj.service.UpdateService;
import com.hlj.utils.ACache;
import com.hlj.utils.BitmapTask;
import com.hlj.utils.FileUtils;
import com.hlj.utils.ImageLoader;
import com.hlj.utils.LauncherBiz;
import com.hlj.utils.Logger;
import com.hlj.utils.NetUtils;
import com.hlj.utils.PrefrenceHandler;
import com.hlj.utils.StringTools;
import com.hlj.utils.StringUtils;
import com.hlj.utils.TimerTools;
import com.hlj.utils.WeatherUtils;
import com.hlj.view.ApplicationLayout;
import com.hlj.view.ApplicationLayoutTest;
import com.hlj.view.CommonTitleView;
import com.hlj.view.LatesHotLayout;
import com.hlj.view.SettingsLayout;
import com.hlj.view.StudyLayout;
import com.hlj.view.TvShowLayout;
import com.hlj.view.VideoInfo;
import com.hlj.view.VideoTypeLayout;
import com.hlj.view.MusicLayout;
import com.hlj.widget.UpdateDialog;
import com.live.video.constans.Constants;
import com.live.video.constans.DeviceInfo;
import com.live.video.constans.HomeConstants;
import com.live.video.constans.Response;
import com.live.video.constans.WeatherInfo;
import com.live.video.net.GetFilmMetroDataResponse;
import com.live.video.net.callback.IUpdateData;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

/**
 * Launcher页面(弃用)
 * 
 * @author huangyuchao
 * 
 */
public class HomeActivity extends BaseActivity {

//	ViewPager main_layout_pager;
//
//	AllPagesAdapter adapter;
//
//	private ArrayList<View> pages = new ArrayList();
//
//	private LatesHotLayout hot;// 最新推荐
//	private StudyLayout studyLayout;// 教学教育
//	private TvShowLayout live;// 网络电视
//	private VideoTypeLayout videoType;// 影视分类
//	private VodRecodLayout vodRecord;// 我的收藏
//	private ApplicationLayout appManager;// 应用程序
//
//	// private ApplicationLayoutTest appTest;
//	private SettingsLayout set;// 设置
//
//	ArrayList<VideoInfo> tjRecInfos = new ArrayList<VideoInfo>();
//	ArrayList<VideoInfo> tjTeachInfos = new ArrayList<VideoInfo>();
//	ArrayList<VideoInfo> tjCollectionInfos = new ArrayList<VideoInfo>();
//	ArrayList<VideoInfo> tvInfos = new ArrayList<VideoInfo>();
//	ArrayList<VideoInfo> tjFilmInfos = new ArrayList<VideoInfo>();
//
//	RadioGroup radioGroup;
//
//	// public static FinalBitmap fb;
//
//	public static ImageLoader imageLoader;
//
//	LinearLayout ll_all;
//
//	RadioButton latest_recommend, study_type, tv_show, video_type,
//			play_collect, application, settings;
//
//	CommonTitleView titleView;
//
//	RelativeLayout rl_bg;
//
//	String pageBgUrl;
//
//	ACache cache;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.main_home);
//
//		titleView = (CommonTitleView) findViewById(R.id.commonTitle);
//
//		HomeConstants.homePath = PrefrenceHandler.getServerAddress(this);
//
//		// 每次重启的时候临时加锁状态
//		HomeConstants.isTempLock = "2";
////		SharedPreferences.Editor editor = HomeActivity.this
////				.getSharedPreferences("setLOCK", Context.MODE_MULTI_PROCESS)
////				.edit();
////		editor.putString("isTempLock", HomeConstants.isTempLock);
////		editor.commit();
//		
//		try {
//			FileUtils.saveData(HomeConstants.isTempLock,
//					HomeConstants.isTempLockPath);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		// imageLoader = new ImageLoader(this, 0);
//
//		hot = new LatesHotLayout(this);
//		// this.hot.setTopRecommends(tjRecInfos);
//		this.hot.requestFocus();
//		studyLayout = new StudyLayout(this);
//		live = new TvShowLayout(this);
//		videoType = new VideoTypeLayout(this);
//		vodRecord = new VodRecodLayout(this);
//		appManager = new ApplicationLayout(this);
//		set = new SettingsLayout(this);
//		this.hot.initView();
//
//		this.studyLayout.initView();
//		this.live.initView();
//		this.videoType.initView();
//		this.vodRecord.initView();
//		this.appManager.initView();
//		this.set.initView();
//
//		ll_all = (LinearLayout) findViewById(R.id.ll_all);
//
//		radioGroup = (RadioGroup) findViewById(R.id.title_group);
//		radioGroup.requestFocus();
//		this.radioGroup.setFocusable(true);
//		this.radioGroup.check(R.id.latest_recommend);
//
//		main_layout_pager = (ViewPager) findViewById(R.id.main_layout_pager);
//
//		main_layout_pager.setCurrentItem(0);
//		main_layout_pager.setOffscreenPageLimit(2);
//		// main_layout_pager.setNextFocusUpId(R.id.title_group);
//
//		latest_recommend = (RadioButton) findViewById(R.id.latest_recommend);
//		tv_show = (RadioButton) findViewById(R.id.tv_show);
//		study_type = (RadioButton) findViewById(R.id.study_type);
//		video_type = (RadioButton) findViewById(R.id.video_type);
//		play_collect = (RadioButton) findViewById(R.id.play_collect);
//		application = (RadioButton) findViewById(R.id.application);
//		settings = (RadioButton) findViewById(R.id.settings);
//
//		// fb = FinalBitmap.create(this);
//		// fb.configLoadingImage(0);
//
//		// loadData();
//
//		initListener();
//
//		adapter = new AllPagesAdapter(pages);
//		main_layout_pager.setAdapter(adapter);
//
//		rl_bg = (RelativeLayout) findViewById(R.id.rl_bg);
//
//		cache = ACache.get(this);
//
//		pageBgUrl = cache.getAsString("pageBg");
//
//		updateData();
//
//	}
//
//	ImageCallBack pageBgcallback = new ImageCallBack() {
//
//		@Override
//		public void post(Bitmap bitmap) {
//			Drawable drawable = new BitmapDrawable(bitmap);
//			rl_bg.setBackgroundDrawable(drawable);
//		}
//
//	};
//
//	Handler updateLauncherHandler = new Handler() {
//		public void handleMessage(android.os.Message msg) {
//			if (0 == msg.what) {
//				GetCheckUpdateLauncherRequest checkRequest = new GetCheckUpdateLauncherRequest(
//						HomeActivity.this);
//				new HttpHomeLoadDataTask(HomeActivity.this, updateCallBack,
//						false, "", true).execute(checkRequest);
//			}
//			if (1 == msg.what) {
//				// Intent intent=new
//				// Intent(HomeActivity.this,UpdateService.class);
//				// bindService(intent,mConn, Service.BIND_AUTO_CREATE);
//
//				GetCheckUpdateFirmwareRequest request = new GetCheckUpdateFirmwareRequest(
//						HomeActivity.this);
//				new HttpHomeLoadDataTask(HomeActivity.this, firewareCallBack,
//						false, "", true).execute(request);
//			}
//		};
//	};
//
//	PrefrenceHandler prefrenceHandler;
//	private long mPosition = 0;
//
//	String zipUrl;
//
//	IUpdateData firewareCallBack = new IUpdateData() {
//
//		@Override
//		public void updateUi(Object o) {
//			prefrenceHandler = new PrefrenceHandler(HomeActivity.this);
//			mPosition = prefrenceHandler.getDownloadPos();
//
//			GetCheckUpdateFirmwareResponse response = new GetCheckUpdateFirmwareResponse();
//			response.paseRespones(o.toString());
//
//			String ver = response.updateInfo.ver;
//			zipUrl = response.updateInfo.zipUrl;
//
//			String fireName = "firmware-" + ver
//					+ StringUtils.getExtensionName(zipUrl);
//
//			prefrenceHandler.setDownloadTarget(target + fireName);
//
//			if (StringUtils.compare(DeviceInfo.getFirmWare(), ver)) {
//				// 后台下载zip包(支持断点续传)
//
//				// if (!isDownloading) {
//				// new downloadThread().start();
//				// isDownloading = true;
//				// }
//
//				afinalDownLoadZip(zipUrl);
//			}
//
//		}
//
//		@Override
//		public void handleErrorData(String info) {
//			// TODO Auto-generated method stub
//
//		}
//	};
//
//	// 判断是否正在下载
//	public static boolean isDownloading = false;
//
//	public class downloadThread extends Thread {
//
//		@Override
//		public void run() {
//			downloadZip(zipUrl);
//			super.run();
//		}
//
//	}
//
//	RandomAccessFile mRandomAccessFile;
//
//	public void afinalDownLoadZip(String zipUrl) {
//		httpHandler = finalHttp.download(zipUrl,
//				prefrenceHandler.getDownloadTarget(), true,
//				new AjaxCallBack<File>() {
//					@Override
//					public void onStart() {
//						Logger.log("开始下载");
//						super.onStart();
//					}
//
//					@Override
//					public void onLoading(long count, long current) {
//						Logger.log(current + "/" + count);
//						super.onLoading(count, current);
//					}
//
//					@Override
//					public void onSuccess(File t) {
//						Logger.log("下载成功");
//						Logger.log(t.getAbsolutePath() + "<><><><><><>");
//						goToRevovery();
//						super.onSuccess(t);
//					}
//
//					@Override
//					public void onFailure(Throwable t, String strMsg) {
//						// TODO Auto-generated method stub
//						super.onFailure(t, strMsg);
//					}
//				});
//	}
//
//	public void downloadZip(String zipUrl) {
//		try {
//			URL url = new URL(zipUrl);
//			String mTargetFile = prefrenceHandler.getDownloadTarget();
//
//			mRandomAccessFile = new RandomAccessFile(mTargetFile, "rw");
//
//			Logger.log("zipUrl:" + zipUrl + "   ,   mTargetFile:" + mTargetFile);
//
//			byte[] buf = new byte[1024 * 1024 * 8];
//			HttpURLConnection cn = (HttpURLConnection) url.openConnection();
//			int mFileSize = cn.getContentLength();
//			Logger.log("mFileSize:" + mFileSize);
//			if (mFileSize < 0) {
//				Logger.log("mFileSize <0");
//			}
//
//			mRandomAccessFile.setLength(mFileSize);
//			Logger.log("mPosition:" + mPosition);
//			mRandomAccessFile.seek(mPosition);
//			cn = (HttpURLConnection) url.openConnection();
//			cn.setRequestProperty("Range", "bytes=" + mPosition + "-"
//					+ mFileSize);
//			BufferedInputStream bis = new BufferedInputStream(
//					cn.getInputStream());
//
//			int len;
//			Logger.log("===========开始下载=============");
//			while ((len = bis.read(buf)) > 0) {
//				Thread.sleep(1000);
//				synchronized (mRandomAccessFile) {
//					mRandomAccessFile.write(buf, 0, len);
//					mPosition += len;
//					Logger.log("percent:" + mPosition + "/" + mFileSize);
//					prefrenceHandler.setDownLoadPos(mPosition);
//				}
//			}
//
//			if (mPosition >= mFileSize) {
//				Logger.log("===========开始完成=============");
//				goToRevovery();
//			}
//
//		} catch (Exception e) {
//			Logger.log("error:" + e.toString());
//		}
//	}
//
//	public void goToRevovery() {
//		// 下载完成(升级)
//		Intent intent = new Intent("com.softwinner.settingsassist.Recovery");
//
//		intent.setAction("softwinner.intent.action.RECOVREY");
//		startActivityForResult(intent, 0);
//	}
//
//	private ServiceConnection mConn = new ServiceConnection() {
//
//		@Override
//		public void onServiceDisconnected(ComponentName name) {
//			// TODO Auto-generated method stub
//
//		}
//
//		@Override
//		public void onServiceConnected(ComponentName name, IBinder service) {
//			// TODO Auto-generated method stub
//
//		}
//	};
//
//	FinalHttp finalHttp = new FinalHttp();
//	HttpHandler httpHandler;
//
//	String target = "/mnt/sdcard/";
//	String extensionName;
//	String fileName;
//	String url;
//	String forceUpdateCode;
//	String ver;
//	String message;
//
//	IUpdateData updateCallBack = new IUpdateData() {
//
//		@Override
//		public void updateUi(Object o) {
//			final GetCheckUpdateLauncherResponse response = new GetCheckUpdateLauncherResponse();
//			response.paseRespones(o.toString());
//			// Toast.makeText(HomeActivity.this, "升级", 1000);
//
//			url = response.updateLaInfo.zipUrl;
//			forceUpdateCode = response.updateLaInfo.forceUpdateCode;
//			ver = response.updateLaInfo.ver;
//			message = response.updateLaInfo.message;
//			extensionName = StringUtils.getExtensionName(url);
//			// fileName = StringUtils.getFileName(url);
//
//			String name = TimerTools.getDayStr(new Date().getTime());
//
//			fileName = "HljHome-" + name + "-" + ver + extensionName;
//
//			if (StringUtils.compare(DeviceInfo.getVerName(HomeActivity.this),
//					ver)) {
//				if ("yes".equals(forceUpdateCode)) {
//					finaldownloadApk();
//				} else {
//					new UpdateDialog(HomeActivity.this, message, url, fileName,
//							extensionName).show();
//				}
//			}
//
//		}
//
//		@Override
//		public void handleErrorData(String info) {
//			// TODO Auto-generated method stub
//
//		}
//
//	};
//
//	/**
//	 * 下载
//	 * 
//	 * @param url
//	 */
//	public void finaldownloadApk() {
//		// 判断文件是否存在(如果存在则删除)
//		try {
//			boolean isFileDelete = FileUtils.delectFile(target + fileName);
//			Logger.log("isFileDelete:" + isFileDelete);
//		} catch (IOException e) {
//			Logger.log("deleteFoder failed:" + e.getLocalizedMessage());
//		}
//
//		httpHandler = finalHttp.download(url, target + fileName, true,
//				new AjaxCallBack<File>() {
//					@Override
//					public void onStart() {
//						Logger.log("开始下载");
//					}
//
//					@Override
//					public void onLoading(long count, long current) {
//						Logger.log(current + "/" + count);
//					}
//
//					@Override
//					public void onSuccess(final File t) {
//						Logger.log("下载成功");
//						Logger.log(t.getAbsolutePath() + "<><><><><><>");
//
//						// 如果是apk的话就执行安装
//						if (".apk".equals(extensionName)) {
//							if ("yes".equals(forceUpdateCode)) {
//								// 强制安装
//								boolean isInstall = LauncherBiz
//										.execCommand("system/bin/pm install -r "
//												+ "mnt/sdcard/" + fileName);
//
//								// if (isInstall) {
//								// PackageManager manager = getPackageManager();
//								// Intent it = manager
//								// .getLaunchIntentForPackage("com.hlj.HomeActivity");
//								// if (it != null) {
//								// startActivity(it);
//								// }
//
//								Logger.log("isInstall:" + isInstall);
//							} else {
//								// 用户安装
//								LauncherBiz.installPackage(HomeActivity.this,
//										target + fileName);
//							}
//						} else {
//							Logger.log("下载文件不是apk文件");
//						}
//					}
//
//					@Override
//					public void onFailure(Throwable t, String strMsg) {
//						Logger.log("下载失败");
//						Logger.log(strMsg + "=====================");
//					}
//
//				});
//	}
//
//	@Override
//	protected void onResume() {
//		// if ("launcher".equals(PreActivity.has_update)) {
//		updateLauncherHandler.sendEmptyMessageDelayed(0, 20 * 1000);
//		// }
//
//		updateLauncherHandler.sendEmptyMessageDelayed(1, 20 * 1000);
//		CommonTitleView.instance = titleView;
//
//		// updateData();
//
//		super.onResume();
//	}
//
//	@Override
//	protected void onDestroy() {
//		if (null != appManager) {
//			appManager.destroy();
//		}
//
//		super.onDestroy();
//	}
//
//	/**
//	 * 获取栏目
//	 */
//	public void getCustomNavigationTab() {
//		GetCustomNavigationRequest navi = new GetCustomNavigationRequest(this);
//		new HttpHomeLoadDataTask(this, customCallBack, false, "", false)
//				.execute(navi);
//	}
//
//	IUpdateData customCallBack = new IUpdateData() {
//
//		@Override
//		public void updateUi(Object o) {
//			// Logger.log(o.toString());
//			// radioGroup.requestFocus();
//
//			radioGroup.requestFocus();
//			radioGroup.setFocusable(true);
//
//			GetCustomResponse response = new GetCustomResponse();
//			response.paseRespones(o.toString());
//
//			Logger.log("count:" + response.count + "," + response.list.size());
//
//			for (int i = 0; i < response.list.size(); i++) {
//				String title = response.list.get(i).title;
//				String metroId = response.list.get(i).metroID;
//
//				if (response.list.size() > 6) {
//					latest_recommend.setText(response.list.get(0).title);
//
//					study_type.setText(response.list.get(1).title);
//					tv_show.setText(response.list.get(2).title);
//					video_type.setText(response.list.get(3).title);
//					play_collect.setText(response.list.get(4).title);
//					application.setText(response.list.get(5).title);
//					settings.setText(response.list.get(6).title);
//				}
//
//			}
//
//			pages.add(hot);
//			pages.add(studyLayout);
//			pages.add(live);
//			pages.add(videoType);
//			pages.add(vodRecord);
//			pages.add(appManager);
//			pages.add(set);
//
//			initListener();
//		}
//
//		@Override
//		public void handleErrorData(String info) {
//			isMove = false;
//		}
//	};
//
//	Handler recRemHandler = new Handler() {
//
//		public void handleMessage(android.os.Message msg) {
//			getRecomMetroData("recommend");
//		};
//	};
//
//	Handler getTeachHandler = new Handler() {
//
//		public void handleMessage(android.os.Message msg) {
//			getTeachMetroData("teaching");
//		};
//	};
//
//	Handler getTvHandler = new Handler() {
//
//		public void handleMessage(android.os.Message msg) {
//			getTvMetroData("tv");
//		};
//	};
//
//	Handler getFilmHandler = new Handler() {
//
//		public void handleMessage(android.os.Message msg) {
//			getFilmMetroData("film");
//		};
//	};
//
//	Handler getFavHandler = new Handler() {
//
//		public void handleMessage(android.os.Message msg) {
//			getFavMetroData("favorite");
//		};
//	};
//
//	/**
//	 * 获取收藏内容
//	 * 
//	 * @param metroID
//	 */
//	public void getFavMetroData(String metroID) {
//		GetCustomMetroDataRequest request = new GetCustomMetroDataRequest(this);
//		request.metroID = metroID;
//		new HttpHomeLoadDataTask(this, metroFavCallBack, false, "", false)
//				.execute(request);
//
//	}
//
//	IUpdateData metroFavCallBack = new IUpdateData() {
//
//		@Override
//		public void updateUi(Object o) {
//
//			GetShouCangMetroDataResponse response = new GetShouCangMetroDataResponse();
//			response.paseRespones(o.toString());
//			tjCollectionInfos.clear();
//			for (int i = 0; i < response.list.size(); i++) {
//				videoInfo = new VideoInfo();
//				videoInfo.title = response.list.get(i).title;
//				videoInfo.imageUrl = response.list.get(i).imageurl;
//				videoInfo.childLock = response.list.get(i).childLock;
//				tjCollectionInfos.add(videoInfo);
//			}
//			vodRecord.setList(tjCollectionInfos);
//			vodRecord.updateData();
//
//			hot.firstGetFocusable();
//
//			// HttpHomeLoadDataTask.instance.setMoreDialog(false);
//			// moveHandler.sendEmptyMessageDelayed(0, 5 * 1000);
//
//		}
//
//		@Override
//		public void handleErrorData(String info) {
//			isMove = false;
//			// HttpHomeLoadDataTask.instance.setMoreDialog(false);
//			// moveHandler.sendEmptyMessageDelayed(0, 5 * 1000);
//		}
//
//	};
//
//	Handler moveHandler = new Handler() {
//
//		@Override
//		public void handleMessage(Message msg) {
//			isMove = false;
//			HttpHomeLoadDataTask.instance.setMoreDialog(false);
//			super.handleMessage(msg);
//		}
//
//	};
//
//	public boolean isMove = true;
//
//	// public boolean onKeyDown(int arg0, android.view.KeyEvent arg1) {
//	// switch (arg0) {
//	// case KeyEvent.KEYCODE_DPAD_LEFT:
//	//
//	// return isMove;
//	// case KeyEvent.KEYCODE_DPAD_RIGHT:
//	//
//	// return isMove;
//	// case KeyEvent.KEYCODE_DPAD_UP:
//	//
//	// return isMove;
//	// case KeyEvent.KEYCODE_DPAD_DOWN:
//	//
//	// return isMove;
//	// }
//	// return super.onKeyDown(arg0, arg1);
//	// };
//
//	/**
//	 * 获取教学教育内容
//	 */
//	public void getTeachMetroData(String metroID) {
//		GetCustomMetroDataRequest request = new GetCustomMetroDataRequest(this);
//		request.metroID = metroID;
//		new HttpHomeLoadDataTask(this, metroTeachCallBack, false, "", false)
//				.execute(request);
//	}
//
//	IUpdateData metroTeachCallBack = new IUpdateData() {
//
//		@Override
//		public void updateUi(Object o) {
//			radioGroup.requestFocus();
//			radioGroup.setFocusable(true);
//			GetRecmodMetroDataResponse response = new GetRecmodMetroDataResponse();
//			response.paseRespones(o.toString());
//			tjTeachInfos.clear();
//			for (int i = 0; i < response.list.size(); i++) {
//				videoInfo = new VideoInfo();
//				videoInfo.title = response.list.get(i).title;
//				videoInfo.shortTilte = response.list.get(i).shortTitle;
//				videoInfo.imageUrl = response.list.get(i).imageurl;
//				videoInfo.action = response.list.get(i).extra.action;
//				videoInfo.url = response.list.get(i).extra.url;
//				videoInfo.catId = response.list.get(i).item.catId;
//				videoInfo.contentId = response.list.get(i).item.contentId;
//				videoInfo.grade = response.list.get(i).item.grade;
//				videoInfo.type = response.list.get(i).item.type;
//				videoInfo.subject = response.list.get(i).item.subject;
//				videoInfo.versions = response.list.get(i).item.versions;
//				videoInfo.childLock = response.list.get(i).childLock;
//				tjTeachInfos.add(videoInfo);
//			}
//			studyLayout.setList(tjTeachInfos);
//			studyLayout.updateData();
//
//			// hot.firstGetFocusable();
//			// isMove=false;
//
//		}
//
//		@Override
//		public void handleErrorData(String info) {
//			isMove = false;
//		}
//
//	};
//
//	/**
//	 * 获取影视分类内容
//	 */
//	public void getFilmMetroData(String metroID) {
//		GetCustomMetroDataRequest request = new GetCustomMetroDataRequest(this);
//		request.metroID = metroID;
//		new HttpHomeLoadDataTask(this, metroFilmCallBack, false, "", false)
//				.execute(request);
//	}
//
//	IUpdateData metroFilmCallBack = new IUpdateData() {
//
//		@Override
//		public void updateUi(Object o) {
//			radioGroup.requestFocus();
//			radioGroup.setFocusable(true);
//			GetFilmMetroDataResponse response = new GetFilmMetroDataResponse();
//			response.paseRespones(o.toString());
//			tjFilmInfos.clear();
//			for (int i = 0; i < response.list.size(); i++) {
//				videoInfo = new VideoInfo();
//				videoInfo.title = response.list.get(i).title;
//				videoInfo.shortTilte = response.list.get(i).shortTitle;
//				videoInfo.imageUrl = response.list.get(i).imageurl;
//				videoInfo.action = response.list.get(i).extra.action;
//				videoInfo.catId = response.list.get(i).item.catId;
//				videoInfo.contentId = response.list.get(i).item.contentId;
//				videoInfo.url = response.list.get(i).item.url;
//				videoInfo.childLock = response.list.get(i).childLock;
//				tjFilmInfos.add(videoInfo);
//			}
//			videoType.setList(tjFilmInfos);
//			videoType.updateData();
//
//			// hot.firstGetFocusable();
//			// isMove=false;
//
//			moveHandler.sendEmptyMessageDelayed(0, 5 * 1000);
//		}
//
//		@Override
//		public void handleErrorData(String info) {
//			isMove = false;
//
//			moveHandler.sendEmptyMessageDelayed(0, 5 * 1000);
//		}
//
//	};
//
//	/**
//	 * 获取metro的内容(推荐)
//	 * 
//	 * @param metroID
//	 */
//	public void getRecomMetroData(String metroID) {
//		GetCustomMetroDataRequest request = new GetCustomMetroDataRequest(this);
//		request.metroID = metroID;
//		new HttpHomeLoadDataTask(this, metroRecomCallBack, true, "", false)
//				.execute(request);
//		HttpHomeLoadDataTask.instance.setMoreDialog(true);
//	}
//
//	IUpdateData metroRecomCallBack = new IUpdateData() {
//
//		@Override
//		public void updateUi(Object o) {
//			radioGroup.requestFocus();
//			radioGroup.setFocusable(true);
//			tjRecInfos.clear();
//			GetRecmodMetroDataResponse response = new GetRecmodMetroDataResponse();
//			response.paseRespones(o.toString());
//
//			for (int i = 0; i < response.list.size(); i++) {
//				videoInfo = new VideoInfo();
//				videoInfo.title = response.list.get(i).title;
//				videoInfo.shortTilte = response.list.get(i).shortTitle;
//				videoInfo.imageUrl = response.list.get(i).imageurl;
//				videoInfo.action = response.list.get(i).extra.action;
//				videoInfo.url = response.list.get(i).extra.url;
//				videoInfo.catId = response.list.get(i).item.catId;
//				videoInfo.contentId = response.list.get(i).item.contentId;
//				videoInfo.grade = response.list.get(i).item.grade;
//				videoInfo.type = response.list.get(i).item.type;
//				videoInfo.subject = response.list.get(i).item.subject;
//				videoInfo.versions = response.list.get(i).item.versions;
//				videoInfo.childLock = response.list.get(i).childLock;
//				videoInfo.mode = response.list.get(i).mode;
//				tjRecInfos.add(videoInfo);
//			}
//			System.out.println("size:" + tjRecInfos.size());
//			hot.setTopRecommends(tjRecInfos);
//			loadData();
//
//			// hot.firstGetFocusable();
//			// isMove=false;
//		}
//
//		@Override
//		public void handleErrorData(String info) {
//			isMove = false;
//		}
//
//	};
//
//	/**
//	 * 获取tv的内容
//	 * 
//	 * @param metroID
//	 */
//	public void getTvMetroData(String metroID) {
//		GetCustomMetroDataRequest request = new GetCustomMetroDataRequest(this);
//		request.metroID = metroID;
//		new HttpHomeLoadDataTask(this, metroTvCallBack, false, "", false)
//				.execute(request);
//	}
//
//	IUpdateData metroTvCallBack = new IUpdateData() {
//
//		@Override
//		public void updateUi(Object o) {
//			radioGroup.requestFocus();
//			radioGroup.setFocusable(true);
//			GetTvMetroDataResponse response = new GetTvMetroDataResponse();
//			response.paseRespones(o.toString());
//			tvInfos.clear();
//			for (int i = 0; i < response.list.size(); i++) {
//				videoInfo = new VideoInfo();
//				videoInfo.title = response.list.get(i).title;
//				videoInfo.imageUrl = response.list.get(i).imageurl;
//				videoInfo.childLock = response.list.get(i).childLock;
//				tvInfos.add(videoInfo);
//			}
//			live.setList(tvInfos);
//			live.updateData();
//
//			// hot.firstGetFocusable();
//			// isMove=false;
//		}
//
//		@Override
//		public void handleErrorData(String info) {
//			isMove = false;
//
//		}
//
//	};
//
//	private ColorStateList createColorStateList(int focused, int selected,
//			int checked, int normal) {
//		int[] colors = new int[] { focused, selected, checked, normal };
//		int[][] states = new int[4][];
//		states[0] = new int[] { android.R.attr.state_focused };
//		states[1] = new int[] { android.R.attr.state_selected };
//		states[2] = new int[] { android.R.attr.state_checked };
//		states[3] = new int[] {};
//		ColorStateList colorList = new ColorStateList(states, colors);
//		return colorList;
//	}
//
//	public void initListener() {
//		// radioGroup.requestFocus();
//		// radioGroup.setFocusable(true);
//		// titleView.setFouseble();
//		// latest_recommend.requestFocus();
//		Logger.log("count:" + radioGroup.getChildCount());
//		for (int i = 0; i < radioGroup.getChildCount(); i++) {
//			View localView = this.radioGroup.getChildAt(i);
//			localView.setTag(Integer.valueOf(i));
//			localView
//					.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//
//						@Override
//						public void onFocusChange(View v, boolean hasFocus) {
//
//							if (hasFocus) {
//
//								int i = Integer.valueOf(String.valueOf(v
//										.getTag()));
//								main_layout_pager.setCurrentItem(i, true);
//
//								v.requestFocus();
//								v.setFocusable(true);
//							}
//						}
//					});
//
//			main_layout_pager
//					.setOnPageChangeListener(new OnPageChangeListener() {
//
//						@Override
//						public void onPageSelected(int position) {
//							if (position < radioGroup.getChildCount()) {
//								((RadioButton) radioGroup.getChildAt(position))
//										.setChecked(true);
//							}
//
//						}
//
//						@Override
//						public void onPageScrolled(int arg0, float arg1,
//								int arg2) {
//							// TODO Auto-generated method stub
//
//						}
//
//						@Override
//						public void onPageScrollStateChanged(int arg0) {
//							// TODO Auto-generated method stub
//
//						}
//					});
//
//		}
//
//	}
//
//	VideoInfo videoInfo;
//
//	public void loadData() {
//		this.hot.loadData();
//	}
//
//	@Override
//	protected void onNewIntent(Intent intent) {
//
//		// updateData();
//
//		super.onNewIntent(intent);
//	}
//
//	/**
//	 * 更新数据
//	 */
//	public void updateData() {
//
//		getCustomNavigationTab();
//
//		recRemHandler.sendEmptyMessageDelayed(0, 1 * 1000);
//		getTeachHandler.sendEmptyMessageDelayed(0, 2 * 1000);
//		getTvHandler.sendEmptyMessageDelayed(0, 3 * 1000);
//		getFilmHandler.sendEmptyMessageDelayed(0, 4 * 1000);
//		getFavHandler.sendEmptyMessageDelayed(0, 5 * 1000);
//
//		if (!StringTools.isNullOrEmpty(pageBgUrl)) {
//			new BitmapWorkerTask(this, rl_bg, true, true, pageBgcallback)
//					.execute(new String[] { pageBgUrl });
//		}
//
//	}
//
//	@Override
//	public void onBackPressed() {
//
//		// super.onBackPressed();
//	}

}
