package com.hlj.HomeActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.HttpHandler;

import org.json.JSONObject;

import com.android.userregister.UserRegisterActivity;
import com.hlj.net.GetDeviceCheckRequest;
import com.hlj.net.GetDeviceCheckResponse;
import com.hlj.net.GetDeviceDataRequest;
import com.hlj.net.GetDeviceResponse;
import com.hlj.net.HttpHomeLoadDataTask;
import com.hlj.utils.ACache;
import com.hlj.utils.CommonTools;
import com.hlj.utils.DeviceCheck;
import com.hlj.utils.FileUtils;
import com.hlj.utils.ImageLoader;
import com.hlj.utils.JsonTools;
import com.hlj.utils.Logger;
import com.hlj.utils.PrefrenceHandler;
import com.hlj.utils.StringTools;
import com.hlj.view.NetErrorDialog;
import com.live.video.constans.Constants;
import com.live.video.constans.EthernetInfo;
import com.live.video.constans.HomeConstants;
import com.live.video.db.DBHelper;
import com.live.video.db.DBOperator;
import com.live.video.net.GetLiveCatsRequest;
import com.live.video.net.GetLiveCatsResponse;
import com.live.video.net.GetLiveChannelResponse;
import com.live.video.net.GetLiveChannelsRequest;
import com.live.video.net.GetLiveTvsRequest;
import com.live.video.net.GetLiveVersionRequest;
import com.live.video.net.GetTvListsResponse;
import com.live.video.net.callback.IUpdateData;
import com.live.video.network.FinalTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/***
 * 
 * 这里是在loading前的一个页面，就是在所有返回的参数都成功后，我们才能进入loading页面进行获取数据
 * 
 * 
 * * 在这里面我们要进行五步的验证
 * 
 * 第一步：有线网卡，无线网卡 第二步：内网以及外网的判断 第三步：
 * 
 * @author Catherine.Brain
 * 
 * */

public class BeforePreActivity extends Activity implements
		android.view.View.OnClickListener {

	private TextView Text_isNet, Text_inNet, Text_outNet, Text_DNS,
			Text_Authen;
	private Button btn_isNet, btn_inNet, btn_outNet, btn_DNS, btn_Authen,
			btn_Set, btn_reFulsh, btn_isRegister;
	public static BeforePreActivity instance;
	private boolean isShow = false;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.app_before_loading);
		instance = this;
		Text_isNet = (TextView) findViewById(R.id.text_isNet);
		Text_inNet = (TextView) findViewById(R.id.text_inNet);
		Text_outNet = (TextView) findViewById(R.id.text_outNet);
		Text_DNS = (TextView) findViewById(R.id.text_DNS);
		Text_Authen = (TextView) findViewById(R.id.text_Authen);

		btn_isNet = (Button) findViewById(R.id.btn_isNet);
		btn_inNet = (Button) findViewById(R.id.btn_inNet);
		btn_outNet = (Button) findViewById(R.id.btn_outNet);
		btn_DNS = (Button) findViewById(R.id.btn_DNS);
		btn_Authen = (Button) findViewById(R.id.btn_Authen);
		btn_Set = (Button) findViewById(R.id.btn_set);
		btn_reFulsh = (Button) findViewById(R.id.btn_reflush);
		btn_isRegister = (Button) findViewById(R.id.btn_register);
		Logger.log("onCreate<><><><><><> ");
		dialogToShow();

		btn_Set.setEnabled(true);
		btn_Set.setFocusable(true);
		btn_Set.setFocusableInTouchMode(true);
		btn_Set.requestFocus();
		btn_Set.setOnClickListener(this);
		btn_reFulsh.setOnClickListener(this);
		btn_isRegister.setOnClickListener(this);
	}

	int count = 0;

	public void dialogToShow() {
		Logger.log("dialogToShow<><><><><><> ");
		final AlertDialog mProgress = new AlertDialog.Builder(
				BeforePreActivity.this).create();
		mProgress.show();
		mProgress.getWindow().setContentView(R.layout.app_before_dialog);
		new Thread() {
			public void run() {
				try {
					sleep(1000);

				} catch (InterruptedException e) {
					Logger.log("error:" + e.toString());
				} finally {
					Logger.log("mProgress dismiss<><><><><><> ");
					mProgress.dismiss();
					count = 1;
					new myThread().start();
					timer_Text();
				}
			}
		}.start();
	}

	/**
	 * 
	 * to define a timer 's method
	 * */
	Timer timer;
	int i = 0;

	public void timer_Text() {

		timer = new Timer();

		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				i++;
				// 定义一个消息传过去
				// Message msg = new Message();
				// msg.what = i++;
				// myHandler.sendMessage(msg);

			}

		}, 1000, 1000);
	}

	Message msg = new Message();

	class myThread extends Thread {

		@Override
		public void run() {

			if (isNetworkAvalible(instance)
					&& EthernetInfo.getMacAddress() != null) {
				System.out.println("1");
				// isShow = true;
				msg.what = 1;

				myHandler.sendMessageDelayed(msg, 1000);
				if (pingIpAddr(DeviceCheck.getGateWay(instance))) {

					System.out.println("2");
					msg = new Message();
					msg.what = 2;
					myHandler.sendMessageDelayed(msg, 2000);
					if (pingIpAddr("www.baidu.com")) {
						System.out.println("3，ping baidu。com issuccess");
						msg = new Message();
						msg.what = 3;
						myHandler.sendMessageDelayed(msg, 2000);
						if (pingIpAddr("apidev.itvyun.com")) {
							System.out.println("4");
							msg = new Message();
							msg.what = 4;
							myHandler.sendMessageDelayed(msg, 4000);

						} else {
							msg = new Message();
							msg.what = -4;
							myHandler.sendMessageDelayed(msg, 5000);
						}

					} else {
						msg = new Message();
						msg.what = -3;
						myHandler.sendMessageDelayed(msg, 2000);
					}

				} else {
					msg = new Message();
					msg.what = -2;
					myHandler.sendMessageDelayed(msg, 2000);
				}

			} else {
				msg = new Message();
				msg.what = -1;
				myHandler.sendMessageDelayed(msg, 2000);
			}

		}
	}

	Handler myHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {

			case 1:
				Text_isNet.setTextColor(0xFF0000FF);
				btn_isNet.setBackgroundResource(R.drawable.right);
				break;
			case -1:
				Text_isNet.setTextColor(0xFFFF0000);
				btn_isNet.setBackgroundResource(R.drawable.wrong);
				showNetErrorDialog("网络连接失败，请确认！");
				break;
			case -2:
				Text_inNet.setTextColor(0xFFFF0000);
				btn_inNet.setBackgroundResource(R.drawable.wrong);
				showNetErrorDialog("内网连接失败，请确认！");
				break;
			case -3:
				Text_outNet.setTextColor(0xFFFF0000);
				btn_outNet.setBackgroundResource(R.drawable.wrong);
				showNetErrorDialog("外网连接失败，请确认！");
				break;
			case -4:
				Text_DNS.setTextColor(0xFFFF0000);
				btn_DNS.setBackgroundResource(R.drawable.wrong);
				showNetErrorDialog("DNS获取失败，请确认！");
				break;

			case 2:
				Text_inNet.setTextColor(0xFF0000FF);
				btn_inNet.setBackgroundResource(R.drawable.right);
				break;
			case 3:
				Text_outNet.setTextColor(0xFF0000FF);
				btn_outNet.setBackgroundResource(R.drawable.right);
				break;
			case 4:

				Text_DNS.setTextColor(0xFF0000FF);
				btn_DNS.setBackgroundResource(R.drawable.right);
				System.out.println("-------前去check中");
				check();
				break;

			}

		}
	};

	/**
	 * 
	 * the first step to judge the isnet and to check the mac
	 * */
	public static NetworkInfo getAvailableNetWorkInfo(Context context) {
		if (context == null) {
			return null;
		}

		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();

		if (activeNetInfo != null && activeNetInfo.isAvailable()) {
			return activeNetInfo;
		} else {
			return null;
		}

	}

	/**
	 * 判断网络情况
	 * 
	 * @param context
	 *            上下文
	 * @return false 表示没有网络 true 表示有网络
	 */
	public static boolean isNetworkAvalible(Context context) {
		// 获得网络状态管理器
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager == null) {
			return false;
		} else {
			// 建立网络数组
			NetworkInfo[] net_info = connectivityManager.getAllNetworkInfo();
			if (net_info != null) {
				for (int i = 0; i < net_info.length; i++) {
					// 判断获得的网络状态是否是处于连接状态
					if (net_info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	static WifiManager wifiMananger = null;

	public boolean getLocalWIFI() {
		wifiMananger = (WifiManager) instance
				.getSystemService(Context.WIFI_SERVICE);

		int state = wifiMananger.getWifiState();

		if (state == 3) {
			return true;
		}

		return false;
	}

	/**
	 * 
	 * the Third step to ping the internet of the out is connect
	 * */
	public boolean pingIpAddr(String ipAddress) {
		@SuppressWarnings("unused")
		String mPingIpAddrResult = null;
		try {
			// We would need to get rid of this before release.
			Process p = Runtime.getRuntime().exec("ping -c 1 " + ipAddress);
			int status = p.waitFor();
			if (status == 0) {
				mPingIpAddrResult = "Pass";
				return true;
			} else {
				mPingIpAddrResult = "Fail: IP addr not reachable";
				return false;
			}
		} catch (IOException e) {
			mPingIpAddrResult = "Fail: IOException";
			return false;
		} catch (InterruptedException e) {
			mPingIpAddrResult = "Fail: InterruptedException";
			return false;
		}
	}

	// ImageCallBack callback = new ImageCallBack() {
	//
	// @Override
	// public void post(Bitmap bitmap) {
	// // pre.setImageBitmap(bitmap);
	// }

	// };

	/**
	 * 
	 * 提示没有网络
	 * **/
	NetErrorDialog Dlog;

	private void showNetErrorDialog(String str) {
		// Logger.logWarning("=====showNetErrorDialog====");

		if (Dlog == null) {
			Dlog = new NetErrorDialog(this, str, 1);
		}

		if (Dlog != null && !Dlog.isShowing()) {
			Dlog.show();
		}
	}

	private void check() {
		try {
			if (CommonTools.hasSDCard()) {
				File file = new File(HomeConstants.deviceUsersUniqueIdPath);
				if (!file.exists()) {
					Logger.log("====前去注册");
					deviceUsersUniqueId = "";
					// 注册
					regDevice1();
				} else {
					Logger.log("====前去检测");
					deviceUsersUniqueId = FileUtils.readFile(
							HomeConstants.deviceUsersUniqueIdPath).trim();
					if (!deviceUsersUniqueId.equals("")) {
						// 检测
						regDevice2();
					} else {
						// 注册
						regDevice1();
					}

				}
			}
		} catch (IOException e) {
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
			try {
				android.os.Debug.dumpHprofData("/mnt/sdcard/hljhomedump.hprof");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

	/**
	 * 检测（是当进入的时候，发现有存储的文件，人后就进入检测，然后查看是否为空）
	 */
	private void regDevice2() {
		Log.d("================", "检测");
		GetDeviceCheckRequest request = new GetDeviceCheckRequest(this, "");
		request.deviceUsersUniqueId = deviceUsersUniqueId;
		new HttpHomeLoadDataTask(this, callback2, false, "", true)
				.execute(request);
	}

	/**
	 * 注册（只有第一次进来才会注册，怎么知道是第一次进来呢，就是检查设备里面是否有存储注册返回的文件，而且不能为空）
	 */
	private void regDevice1() {
		Log.d("================", "注册");
		GetDeviceCheckRequest request = new GetDeviceCheckRequest(this, "");
		// --------------------------------------------同步检测数据
		String judgeInfo;
		String info;
		judgeInfo = HttpHomeLoadDataTask.code + "这是第一次自动注册返回的code值"
				+ "===>时间为：==》" + getCurrentDate();
		if (CommonTools.hasSDCard()) {
			File file = new File(HomeConstants.deviceJudgeInfos);
			info = "这是第一次注册传送的值：" + request.androidId + "      "
					+ DeviceCheck.fetch_ethmac() + "     " + request.wifiMac
					+ "===>时间为：==》" + getCurrentDate();
			if (file.exists()) {
				try {
					FileUtils.delectFile(HomeConstants.deviceJudgeInfos);
					FileUtils.delectFile(HomeConstants.deviceSend);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				try {
					FileUtils.saveData(judgeInfo,
							HomeConstants.deviceJudgeInfos);
					FileUtils.saveData(info, HomeConstants.deviceSend);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		// ---------------------------------------
		request.deviceUsersUniqueId = deviceUsersUniqueId;
		new HttpHomeLoadDataTask(this, callback1, false, "", true)
				.execute(request);
	}

	int userNum;
	String deviceUsersUniqueId;
	IUpdateData callback1 = new IUpdateData() {

		@Override
		public void updateUi(Object o) {
			try {
				GetDeviceCheckResponse response = new GetDeviceCheckResponse();
				response.paseRespones(o.toString());

				deviceUsersUniqueId = response.deviceData.deviceUsersUniqueId;
				System.out.println("the deviceUsersUniqueId is==>"
						+ deviceUsersUniqueId + "=====>lenth is==>"
						+ deviceUsersUniqueId.length() + "and the usernum is"
						+ userNum);
				// 如果deviceUsersUniqueId长度<21,user_id值<=0
				if (deviceUsersUniqueId.length() < 21 && userNum <= 0) {
					isShow = true;
					Logger.log("注册失败");
					FileUtils.delectFile(HomeConstants.deviceUsersUniqueIdPath);
					FileUtils.delectFile(HomeConstants.userIdPath);
				} else {
					isShow = false;
					// Toast.makeText(
					// instance,
					// "the注册  register unique id is =====>"
					// + response.deviceData.deviceUsersUniqueId,
					// Toast.LENGTH_SHORT).show();
					Logger.log("the注册  register unique id is =====>"
							+ response.deviceData.deviceUsersUniqueId);
					String user_id = response.deviceData.user_id;
					String nick_id = response.deviceData.nick_id;
					String session_id = response.deviceData.session_id;
					String has_update = response.deviceData.has_update;
					String tags = response.deviceData.tags;
					Logger.log("注册成功" + deviceUsersUniqueId);
					FileUtils.saveData(deviceUsersUniqueId,
							HomeConstants.deviceUsersUniqueIdPath);
					FileUtils.saveData(user_id, HomeConstants.userIdPath);
					userNum = Integer.parseInt(user_id);
				}
				isGotoNext();
			} catch (Exception e) {
				e.printStackTrace();
				Logger.log(e.toString());
				try {
					android.os.Debug
							.dumpHprofData("/mnt/sdcard/hljhomedump.hprof");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

		@Override
		public void handleErrorData(String info) {
			// dialogToShowOfFail(info);
			isShow = true;
			isGotoNext();
		}
	};

	IUpdateData callback2 = new IUpdateData() {
		@Override
		public void updateUi(Object o) {
			GetDeviceCheckResponse response = new GetDeviceCheckResponse();
			response.paseRespones(o.toString());
			String deviceUsersUniqueIdGet = response.deviceData.deviceUsersUniqueId;
			String user_id = response.deviceData.user_id;
			String nick_id = response.deviceData.nick_id;
			String session_id = response.deviceData.session_id;
			String has_update = response.deviceData.has_update;
			String tags = response.deviceData.tags;
			try {
				if (FileUtils.readFile(HomeConstants.deviceUsersUniqueIdPath)
						.trim().equals(deviceUsersUniqueIdGet)
						&& FileUtils.readFile(HomeConstants.userIdPath).trim()
								.equals(user_id)) {
					// Toast.makeText(instance, "检测  验证成功!!",
					// Toast.LENGTH_SHORT)
					// .show();
					Log.d("============", "检测 验证成功" + deviceUsersUniqueIdGet);
					System.out.println("============检测 验证成功"
							+ deviceUsersUniqueIdGet);
					isShow = false;
				} else {
					isShow = true;
					// Toast.makeText(instance, "检测 验证错误!!", Toast.LENGTH_SHORT)
					// .show();
					Log.d("============", "检测 验证错误" + deviceUsersUniqueIdGet);
					System.out.println("============检测 验证cuowu"
							+ deviceUsersUniqueIdGet);
					FileUtils.delectFile(HomeConstants.deviceUsersUniqueIdPath);
					FileUtils.delectFile(HomeConstants.userIdPath);
					FileUtils.saveData(deviceUsersUniqueIdGet,
							HomeConstants.deviceUsersUniqueIdPath);
					FileUtils.saveData(user_id, HomeConstants.userIdPath);
				}

				isGotoNext();

			} catch (Exception e) {
				e.printStackTrace();
				Logger.log(e.toString());
				try {
					android.os.Debug
							.dumpHprofData("/mnt/sdcard/hljhomedump.hprof");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

		@Override
		public void handleErrorData(String info) {
			// dialogToShowOfFail(info);
			isShow = true;
			isGotoNext();
		}
	};

	public void isGotoNext() {
		if (isShow) {
			Text_Authen.setTextColor(0xFFFF0000);
			btn_Authen.setBackgroundResource(R.drawable.wrong);
			btn_isRegister.setEnabled(true);
			btn_isRegister.setVisibility(View.VISIBLE);
			btn_isRegister.setEnabled(true);
			btn_isRegister.setFocusable(true);
			btn_isRegister.setFocusableInTouchMode(true);
			btn_isRegister.requestFocus();
		} else {
			Text_Authen.setTextColor(0xFF0000FF);
			btn_Authen.setBackgroundResource(R.drawable.right);
			if (i >= 40) {
				Toast.makeText(BeforePreActivity.this,
						"当前网络质量不是很好，不是很利于在线观看哦！！", Toast.LENGTH_SHORT).show();
				i = 0;
				timer.cancel();

			} else {
				getDataOfDevice();
			}
		}

	}

	DBOperator operator;

	// private ImageView pre;

	static ImageLoader imageLoader;

	// private ImageView pre_logo;

	private void getDataOfDevice() {
		HomeConstants.homePath = PrefrenceHandler.getServerAddress(this);
		HomeConstants.isPrimaryLock = PrefrenceHandler.getHomePrimaryLock(this);
		HomeConstants.isTempLock = PrefrenceHandler.getHomeTempLock(this);

		// 获取锁的状态(从文件中读取)
		try {
			HomeConstants.isVersionLock = FileUtils
					.readFile(HomeConstants.isVersionLockPath);
			HomeConstants.isPrimaryLock = FileUtils
					.readFile(HomeConstants.isPrimaryLockPath);
			HomeConstants.isTempLock = FileUtils
					.readFile(HomeConstants.isTempLockPath);

		} catch (IOException e) {
			Logger.log(e.toString() + "<><><><><><>");
		}

		cache = ACache.get(BeforePreActivity.this);

		handler.sendEmptyMessage(4);

	}

	class FileWriteThread extends Thread {

		@Override
		public void run() {
			// 写入文件到system/media
			downloadApk();
			// super.run();
		}

	}

	public static boolean isRestart = false;

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:

				// 获取直播列表版本
				getLiveVersion();

				break;
			case 1:
				check();
				break;

			case 3:

				startHome(isRestart);
				break;

			case 4:

				// 获取数据
				getDeviceData();

				break;

			}
			super.handleMessage(msg);
		};
	};

	public String animationPath = "/system/media";
	FinalHttp finalHttp = new FinalHttp();
	HttpHandler httpHandler;
	String downloadZipUrl;
	String target = "/system/media/";
	String fileName = "bootanimation.zip";

	/**
	 * 下载文件，直接覆盖，无需先删除
	 */
	public void downloadApk() {
		try {
			// 构造URL
			URL url = new URL(downloadZipUrl);
			// 打开连接
			URLConnection con = url.openConnection();
			// 获得文件的长度
			int contentLength = con.getContentLength();
			System.out.println("长度 :" + contentLength);
			// 输入流
			InputStream is = con.getInputStream();
			// 1K的数据缓冲
			byte[] bs = new byte[1024];
			// 读取到的数据长度
			int len;
			// 输出的文件流
			OutputStream os = new FileOutputStream(new File(target + fileName));
			// 开始读取
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			Logger.log("downloadApk success");
			// 完毕，关闭所有链接
			os.close();
			is.close();

		} catch (Exception e) {
			Logger.log("downloadApk failed:" + e.toString());
			try {
				// MobclickAgent.reportError(getApplicationContext(),
				// e.toString());
				android.os.Debug.dumpHprofData("/mnt/sdcard/hljhomedump.hprof");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 下载文件，如果文件名相同的话，需要先删除
	 */
	public void writeZipToSystem() {
		// 判断文件是否存在(如果存在则删除)
		try {
			boolean isFileExist = FileUtils.delectZipFile(target + fileName);
			Logger.log("isFileExist:" + isFileExist);
			// 下载
			httpHandler = finalHttp.download(downloadZipUrl, target + fileName,
					true, new AjaxCallBack<File>() {

						@Override
						public void onStart() {
							Logger.log("开始下载");
						}

						@Override
						public void onLoading(long count, long current) {
							Logger.log(current + "/" + count);
						}

						@Override
						public void onFailure(Throwable t, String strMsg) {
							Logger.log("下载失败");
							Logger.log(strMsg + "=====================");
						}

						@Override
						public void onSuccess(File t) {
							Logger.log("下载成功");
							Logger.log(t.getAbsolutePath() + "<><><><><><>");

						}

					});

		} catch (Exception e) {
			Logger.log("system animation:" + e.toString());
			try {
				android.os.Debug.dumpHprofData("/mnt/sdcard/hljhomedump.hprof");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

	FinalTask finalTask;
	private HashMap map = new HashMap();

	/**
	 * 获取启动前的一些参数
	 */
	private void getDeviceData() {
		GetDeviceDataRequest request = new GetDeviceDataRequest(this);
		new HttpHomeLoadDataTask(this, getDeviceDataCallBack, false, "", false)
				.execute(request);
	}

	ACache cache;

	public static String infos, contact;

	IUpdateData getDeviceDataCallBack = new IUpdateData() {

		@Override
		public void updateUi(Object o) {
			GetDeviceResponse response = new GetDeviceResponse();
			response.paseRespones(o.toString());

			downloadZipUrl = response.startLogo;

			Logger.log("cache startLogo:" + cache.getAsString("startLogo"));
			Logger.log("cache epgId:" + cache.getAsString("epgId"));
			// 判断是否和之前的是同一个版本
			if (response.startLogo.equals(cache.getAsString("startLogo"))
					|| response.epgId.equals(cache.getAsString("epgId"))) {

			} else {
				new FileWriteThread().start();
			}

			cache.put("infos", response.infos);
			cache.put("contact", response.contact);
			cache.put("title", response.groupTitle);
			cache.put("logo", response.logo);
			cache.put("startBg", response.startBg);
			cache.put("pageBg", response.pageBg);
			cache.put("isGrade", response.isGrade);

			cache.put("startLogo", response.startLogo);
			cache.put("epgId", response.epgId);

			// new BitmapThread(response.startBg).start();

			// infos = response.infos;
			// contact = response.contact;
			Constants.infos = response.infos;
			Constants.contract = response.contact;
			Constants.isGrade = response.isGrade;
			Constants.title = response.groupTitle;
			Logger.log("Constants title:" + Constants.title + "<><><><><><>");

			// 保存到SharedPrefrence中
			saveInfosAndContact(response.infos, response.contact,
					response.groupTitle);

			try {
				HomeConstants.isVersionLock = response.epglock;
				// 保存epg锁的状态
				FileUtils.saveData(HomeConstants.isVersionLock,
						HomeConstants.isVersionLockPath);
			} catch (IOException e) {
				Logger.log(e.toString());
			}

			handler.sendEmptyMessage(0);
			// new BitmapWorkerTask(PreActivity.this, pre, true,
			// true, callback)
			// .execute(new String[] { cache.getAsString("startBg")
			// });
			// if (!StringTools.isNullOrEmpty(cache.getAsString("startBg"))) {
			// imageLoader.displayImage(cache.getAsString("startBg"), pre);
			// }

		}

		@Override
		public void handleErrorData(String info) {
			// dialogToShowOfFail(info);
		}

	};

	public class BitmapThread extends Thread {

		private String startBg;

		public BitmapThread(String start) {
			startBg = start;
		}

		@Override
		public void run() {
			try {
				Logger.log("startBg:" + startBg);
				URL url = new URL(startBg);
				Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());
				Logger.log("bitmap:" + bitmap
						+ "----------------------------------------------");
				cache.put("startBitmap", bitmap);
				Message msg = new Message();
				msg.obj = bitmap;
				imageHandler.sendMessage(msg);

			} catch (IOException e) {
				Logger.log("------------" + e.toString() + "<><><><><><>");
				try {
					android.os.Debug
							.dumpHprofData("/mnt/sdcard/hljhomedump.hprof");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	Handler imageHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			Bitmap bitmap = (Bitmap) msg.obj;
			// pre.setImageBitmap(bitmap);
			super.handleMessage(msg);
		}
	};

	public void saveInfosAndContact(String infos, String contract, String title) {
		SharedPreferences sp = getSharedPreferences("aboutus", MODE_PRIVATE);
		// 存入数据
		Editor editor = sp.edit();
		editor.putString("infos", infos);
		editor.putString("contract", contract);
		editor.putString("title", title);
		editor.commit();
	}

	// ImageCallBack callback = new ImageCallBack() {
	//
	// @Override
	// public void post(Bitmap bitmap) {
	// pre.setImageBitmap(bitmap);
	// }

	// };

	private void getLiveVersion() {
		GetLiveVersionRequest request = new GetLiveVersionRequest(this);
		new HttpHomeLoadDataTask(this, versionCallBack, false, "", false)
				.execute(request);
	}

	PrefrenceHandler prefrenceHandler;

	IUpdateData versionCallBack = new IUpdateData() {

		@Override
		public void updateUi(Object o) {
			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(o.toString());
				String newVersion = JsonTools.getString(jsonObject, "version");

				int newnumVer = Integer.parseInt(newVersion);
				DBHelper.setVersion(newnumVer);
				prefrenceHandler = new PrefrenceHandler(BeforePreActivity.this);
				prefrenceHandler.setLiveVersion(newnumVer);
				if (operator == null) {
					operator = new DBOperator(BeforePreActivity.this);
				}

				String oldVersion = operator.getLiveVersion();
				if (StringTools.isNullOrEmpty(oldVersion)) {
					oldVersion = "1";
				}

				int oldnumVer = Integer.parseInt(oldVersion);

				if (newnumVer > oldnumVer) {

					// new DBHelper(PreActivity.this);
					// 存储
					operator.insertLiveVersion(newVersion);
					// 获取直播列表信息
					getLiveCatas();
				} else {
					handler.sendEmptyMessageDelayed(3, 1 * 1000);
				}
			} catch (Exception e) {
				Logger.log(e.toString());
				handler.sendEmptyMessageDelayed(3, 1 * 1000);
				try {
					android.os.Debug
							.dumpHprofData("/mnt/sdcard/hljhomedump.hprof");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

		}

		@Override
		public void handleErrorData(String info) {
			// dialogToShowOfFail(info);
		}

	};

	private void getLiveCatas() {
		GetLiveCatsRequest request = new GetLiveCatsRequest(this);
		new HttpHomeLoadDataTask(this, liveCataCallBack, false, "", false)
				.execute(request);
	}

	IUpdateData liveCataCallBack = new IUpdateData() {

		@Override
		public void updateUi(Object o) {
			GetLiveCatsResponse response = new GetLiveCatsResponse();
			response.paseRespones(o.toString());
			// 存储
			operator.insertLiveCats(response.list);

			getLiveChannels();

		}

		@Override
		public void handleErrorData(String info) {
			getLiveChannels();

		}

	};

	private void getLiveChannels() {
		GetLiveChannelsRequest request = new GetLiveChannelsRequest(this);
		new HttpHomeLoadDataTask(this, liveChannelCallBack, false, "", false)
				.execute(request);
	}

	IUpdateData liveChannelCallBack = new IUpdateData() {

		@Override
		public void updateUi(Object o) {
			GetLiveChannelResponse response = new GetLiveChannelResponse();
			response.paseRespones(o.toString());
			// 存储
			operator.insertLiveChannels(response.list);

			getLiveTvs();

		}

		@Override
		public void handleErrorData(String info) {
			getLiveTvs();

		}

	};

	private void getLiveTvs() {
		GetLiveTvsRequest request = new GetLiveTvsRequest(this);
		new HttpHomeLoadDataTask(this, tvListsCallBack, false, "", false)
				.execute(request);

	}

	IUpdateData tvListsCallBack = new IUpdateData() {

		@Override
		public void updateUi(Object o) {
			GetTvListsResponse response = new GetTvListsResponse();
			response.paseRespones(o.toString());
			// 存储
			operator.insertTVLists(response.list);

			handler.sendEmptyMessageDelayed(3, 1 * 1000);

		}

		@Override
		public void handleErrorData(String info) {
			handler.sendEmptyMessageDelayed(3, 1 * 1000);
		}

	};

	private void startHome(boolean isRestart) {
		System.out.println("haha'm g......");
		Intent intent = new Intent(this, SpectialHomeActivity.class);
		if (isRestart) {
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		}
		startActivity(intent);
		finish();
	}

	@Override
	protected void onStop() {
		if (null != timer) {
			timer.cancel();

		}
		if (Dlog != null && Dlog.isShowing()) {
			Dlog.cancel();
		}

		// unregisterReceiver();
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// unregisterReceiver();
		if (null != timer) {
			timer.cancel();
		}
		if (Dlog != null && Dlog.isShowing()) {
			Dlog.cancel();
		}
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_set:
			String sdkVersion = android.os.Build.VERSION.SDK;
			Logger.log("sdkVersion:" + sdkVersion);
			Intent intent;
			if (Integer.valueOf(sdkVersion) > 10) {
				intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
				startActivity(intent);

			} else {
				// finish();
				intent = new Intent();
				ComponentName comp = new ComponentName("com.android.settings",
						"com.android.settings.Settings");
				intent.setComponent(comp);
				intent.setAction("android.intent.action.VIEW");
				startActivity(intent);
			}
			break;
		case R.id.btn_reflush:
			refresh();

			break;
		case R.id.btn_register:
			finish();
			// enter the pager of register
			Intent intent_re = new Intent().setClass(this,
					UserRegisterActivity.class);
			startActivity(intent_re);
			break;

		}

	}

	private void refresh() {
		finish();
		Intent it = new Intent(BeforePreActivity.this, BeforePreActivity.class);
		startActivity(it);

	}

	public static String getCurrentDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy'年'MM'月'dd'日' HH:mm:ss");
		return df.format(new Date());
	}

	public void dialogToShowOfFail(String str) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.create();
		builder.setTitle("提示").setMessage(str)
				.setPositiveButton("重新认证", new OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {

						check();

					}

				}).setNegativeButton("手动注册", new OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						finish();
						Intent intent = new Intent().setClass(
								BeforePreActivity.this,
								UserRegisterActivity.class);
						startActivity(intent);
					}
				});

		builder.show();
	}
}