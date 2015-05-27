package com.hlj.tuisongvideo;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.HttpHandler;
import com.hlj.utils.Logger;
import de.tavendo.autobahn.Wamp;
import de.tavendo.autobahn.Wamp.CallHandler;
import de.tavendo.autobahn.Wamp.ConnectionHandler;
import de.tavendo.autobahn.Wamp.EventHandler;
import de.tavendo.autobahn.WampConnection;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * 推送服务
 * 
 * @author huangyuchao
 * 
 */
public class PushService extends Service {

	String host = "192.168.102.200";
	String port = "9000";

	int mPingDelay = 10 * 1000;

	Handler mTimerHandler = new Handler();
	Runnable mTimerRunnable = new Runnable() {

		@Override
		public void run() {

			if (mConnection.isConnected()) {
				Logger.log("============Timer run isConnected");

				getClientSession();

			} else {
				Logger.log("============Timer run ! isConnected");
			}
			mTimerHandler.postDelayed(this, mPingDelay);
		}
	};

	private void getClientSession() {
		mConnection.call("cloudpush:getClientSession", String.class,
				new CallHandler() {

					@Override
					public void onResult(Object result) {
						String res = (String) result;

						Logger.log("============ " + res);

						alert("cloudpush:getClientSession result ============== "
								+ res);
					}

					@Override
					public void onError(String errorId, String errorInfo) {
						alert("cloudpush:getClientSession RPC error - "
								+ errorInfo);
					}
				});
	}

	@Override
	public void onCreate() {
		setConnect();

		try {
			Thread.sleep(1 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (mConnection.isConnected()) {
			getClientSession();
		}
		super.onCreate();
	}

	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	private void setConnect() {
		mConnection.connect(wsuri, new ConnectionHandler() {

			@Override
			public void onOpen() {
				// TODO Auto-generated method stub
				mConnection.prefix("cloudpush",
						"http://www.itvyun.com/tvmedia/cloudpush#");

				mConnection.prefix("event",
						"http://www.itvyun.com/tvmedia/event#");

				mConnection.subscribe("event:PushUri", CloudPushEvent.class,
						new EventHandler() {

							@Override
							public void onEvent(String topicUri, Object event) {

								// when we get an event, we safely can cast to
								// the type we specified previously
								CloudPushEvent evt = (CloudPushEvent) event;
								Logger.log("============!event:PushUri "
										+ evt.id);

								String urls = evt.url;

								VideoInfo info = new VideoInfo();
								info.parseJson(urls);

								String link = info.link;

								if ("play".equals(info.action)) {
									// 开启视频类
									// Intent it = new
									// Intent(PushService.this,VideoActivity.class);

									Intent it = new Intent(PushService.this,
											MediaPlayerVideo.class);
									it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 再次推送时候打开新的Activity
									it.putExtra("urls", urls);
									startActivity(it);
								} else if ("view".equals(info.action)) {
									// 打开网页(调用内置浏览器)
									Uri uri = Uri.parse(link);
									Intent it = new Intent(Intent.ACTION_VIEW,
											uri);
									it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									startActivity(it);
								} else if ("download".equals(info.action)) {
									// 下载
									download(link, info.user);
								} else if ("install".equals(info.action)) {
									download(link, info.user);
								} else if ("delete".equals(info.action)) {

									if ("user".equals(info.user)) {
										// 用户删除
										delelePackage(info.link);
									} else if ("admin".equals(info.user)) {
										// 后台删除
										removePackage(info.link);
									}
								}

								alert("Event received : " + evt.toString());
							}

						});
				mTimerHandler.postDelayed(mTimerRunnable, mPingDelay);//
			}

			@Override
			public void onClose(int arg0, String reason) {
				mTimerHandler.removeCallbacks(mTimerRunnable);
				alert(reason);
				setConnect();
			}
		});
	}

	HttpHandler httpHandler;

	Handler handler = new Handler();

	String target = "/mnt/sdcard/";

	public void download(String link, final String isUser) {
		FinalHttp fh = new FinalHttp();

		final String extensionName = getExtensionName(link);

		final String fileName = new Date().getTime() + extensionName;

		httpHandler = fh.download(link, target + fileName, true,
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

						handler.post(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								Toast.makeText(PushService.this,
										"下载成功，存放路径为：" + t.getAbsolutePath(),
										Toast.LENGTH_SHORT).show();
							}
						});

						// 如果是apk的话就执行安装
						if (".apk".equals(extensionName)) {
							if ("user".equals(isUser)) {
								// 用户安装
								installPackage(target + fileName);

							} else if ("admin".equals(isUser)) {
								// 后台安装
								boolean isInstall = execCommand("system/bin/pm install -r "
										+ "sdcard/" + fileName);
								Logger.log("isInstall:" + isInstall);
							}
						}
					}

					@Override
					public void onFailure(Throwable t, String strMsg) {
						Logger.log("下载失败");
						Logger.log(strMsg + "=====================");
					}

				});

	}

	/**
	 * 获取后缀名
	 * 
	 * @param filename
	 * @return
	 */
	public String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot);
			}
		}
		return filename;
	}

	/**
	 * 安装应用
	 */
	private void installPackage(String updateFile) {
		File f = new File(updateFile);
		Uri uri = Uri.fromFile(f); // 获取文件的Uri
		Intent installIntent = new Intent(Intent.ACTION_VIEW);
		installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		installIntent.setDataAndType(uri,
				"application/vnd.android.package-archive");// 设置intent的数据类型
		startActivity(installIntent);
	}

	/**
	 * 静默安装
	 * 
	 * @param cmd
	 * @return
	 */
	private boolean execCommand(String cmd) {
		Process process = null;

		try {
			process = Runtime.getRuntime().exec(cmd);
			process.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
			Logger.log(e.toString() + "<><><><><><>");
			return false;
		} finally {
			process.destroy();
		}
		return true;
	}

	/**
	 * 删除应用
	 * 
	 * @param packageName
	 */
	public void delelePackage(String packageName) {
		Intent i = new Intent();
		Uri uri = Uri.parse("package:" + packageName);// 获取删除包名的URI
		i.setAction(Intent.ACTION_DELETE);// 设置我们要执行的卸载动作
		i.setData(uri);// 设置获取到的URI
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
	}

	/**
	 * 静默卸载
	 * 
	 * @param packageName
	 */
	public void removePackage(String packageName) {
		String cmd = "system/bin/pm uninstall " + packageName;
		execCommand(cmd);
	}

	String wsuri = "ws://" + host + ":" + port;

	private Wamp mConnection = new WampConnection();

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Logger.log("-------------------onStartCommand-------------------------");
		flags = START_STICKY;
		//return super.onStartCommand(intent, flags, startId);
		return super.onStartCommand(intent, flags, startId);
		//return START_REDELIVER_INTENT;
	}

	private void alert(String message) {
		System.out.println("=================" + message
				+ "=====================");
		// Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
		// .show();
	}

}
