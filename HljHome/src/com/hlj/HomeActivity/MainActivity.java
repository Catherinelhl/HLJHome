package com.hlj.HomeActivity;

import java.util.ArrayList;
import java.util.ResourceBundle.Control;
import java.util.regex.Pattern;

import com.hj.widget.CommonToast;
import com.hj.widget.VitaVideoView;
import com.hlj.adapter.VideoListAdapter;
import com.hlj.utils.CommonTools;
import com.hlj.utils.Logger;
import com.hlj.utils.PrefrenceHandler;
import com.hlj.utils.StringTools;
import com.hlj.utils.StringUtils;
import com.hlj.utils.SwissArmyKnife;
import com.hlj.utils.TVOffAnimation;
import com.hlj.utils.TimeMannager;
import com.hlj.view.CustomProgressDialog;
import com.hlj.widget.LiveControl;
import com.live.video.constans.CataInfo;
import com.live.video.constans.ChannelInfo;
import com.live.video.constans.EpgInfo;
import com.live.video.constans.TVInfo;
import com.live.video.db.DBHelper;
import com.live.video.db.DBOperator;
import com.live.video.network.GetBaseIpTask;
import com.live.video.network.GetEpgUrlTask;
import com.live.video.network.GetRedicitIpTask;
import com.live.video.network.ICallBack;
import com.live.video.network.ICallBackResult;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 主界面
 * 
 * @author huangyuchao
 * 
 */
public class MainActivity extends Activity {

	VitaVideoView videoView;
	// 播放路径
	String url = "";

	ListView listView;
	VideoListAdapter adapter;

	ArrayList<TVInfo> list;

	public static int type = 6;// 6为北京

	FrameLayout ll_all;
	public static LinearLayout ll_full;
	TextView play_title;

	DBOperator operator;

	CataInfo info;

	static PrefrenceHandler prefrenceHandler;

	// 标题
	public String title;
	// id
	public int id;

	public String epgUrl;

	MainActivity instance;
	TimeMannager tm;

	TVInfo tvInfo;

	ArrayList<EpgInfo> epgList;

	CommonToast toast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_main_back);

		instance = this;

		toast = new CommonToast(this);

		ll_all = (FrameLayout) findViewById(R.id.ll_all);
		ll_full = (LinearLayout) findViewById(R.id.ll_full);
		play_title = (TextView) findViewById(R.id.play_title);

		videoView = (VitaVideoView) findViewById(R.id.myVideoView);
		videoView.setOnErrorListener(errorListener);

		listView = (ListView) findViewById(R.id.play_list);

		operator = new DBOperator(this);
		prefrenceHandler = new PrefrenceHandler(this);

		String dbPath = CommonTools.getDataBasePath(this);
		Log.v(CommonTools.TAG, dbPath);

		// type = this.getIntent().getIntExtra("type", 0);

		list = operator.getTvInfoByType(type);
		info = operator.getCataInfoByType(type);
		play_title.setText(info.name);

		if (list != null && list.size() > 0) {
			Log.v(CommonTools.TAG, list.get(0).title + " : " + list.get(0).url);
			url = list.get(0).url;
			title = list.get(0).title;
			id = list.get(0).id;
		}

		// id = this.getIntent().getIntExtra("id", 0);
		// url = this.getIntent().getStringExtra("url");
		// title = this.getIntent().getStringExtra("title");

		adapter = new VideoListAdapter(this, list);
		listView.setAdapter(adapter);
		listView.setSelection(0);
		adapter.notifyDataSetChanged();

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long num) {
				// tm.refreshCount();
				playNum = position;
				url = list.get(position).url;
				title = list.get(position).title;
				id = list.get(position).id;

				setDataSource(url, title);

				controlHandler.sendEmptyMessageDelayed(0, 1 * 1000);
			}
		});

		listView.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Logger.log("OnItemSelected  ----------------执行了------------------");
				// tm.refreshCount();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		ll_full.setVisibility(View.VISIBLE);
		listView.setVisibility(View.VISIBLE);
		listViewGetFocus();
		// getScreenSize();

		initOverlay();

		initController();

		epgList = new ArrayList<EpgInfo>();

		// tm = TimeMannager.getinstance();
		// tm.updateView(ll_full);
		// tm.startCount(this);

		setDataSource(url, title);
	}

	LiveControl liveControl;

	private void initController() {
		liveControl = new LiveControl(this);

		controlHandler.sendEmptyMessageDelayed(0, 1 * 1000);
	}

	Handler controlHandler = new Handler() {

		public void handleMessage(Message msg) {

			switch (msg.what) {
			case 0:
				liveControl.show();
				liveControl.setState(0);
				liveControl.startTestSpeed();
				liveControl.setChannelName(title);

				liveControl.setchannelSource((titleNum + 1) + "/"
						+ titleList.size());
				liveControl.setchannleNum((playNum + 1) + "");
				// liveControl.setcwTime("");
				break;

			}

		};

	};

	@Override
	protected void onResume() {
		// setDataSource(url, title);
		super.onResume();
	}

	public WindowManager windowManager;
	private Handler handler;
	private OverlayThread overlayThread;

	private LinearLayout overlay_ll;
	public TextView overlay;

	private Handler progressHandler = new Handler() {

		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				dialog.cancel();
				// dialog.dismiss();
				liveControl.setState(0);
			}
		};

	};

	// private LinearLayout overlay_bottom;
	// public TextView tv_bottom;

	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				ll_full.setVisibility(View.GONE);
				// videoView.setMediaController(new MediaController(this));
				if (listView != null
						&& listView.getVisibility() == View.VISIBLE) {
					listView.setVisibility(View.GONE);
				}
			}
		};
	};

	public void initOverlay() {
		handler = new Handler();
		overlayThread = new OverlayThread();
		final LayoutInflater inflater = LayoutInflater.from(instance);
		overlay_ll = (LinearLayout) inflater.inflate(R.layout.overlay, null);
		overlay = (TextView) overlay_ll.findViewById(R.id.tv_center);

		// overlay_bottom = (LinearLayout)
		// inflater.inflate(R.layout.overlay_bottom, null);
		// tv_bottom = (TextView) overlay_bottom.findViewById(R.id.tv_center);

		final WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_APPLICATION,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
				PixelFormat.TRANSLUCENT);
		windowManager = (WindowManager) instance
				.getSystemService(Context.WINDOW_SERVICE);
		windowManager.addView(overlay_ll, lp);
		// windowManager.addView(overlay_bottom, lp);
	}

	/**
	 * 设置overlay不可见
	 * 
	 * 
	 */
	class OverlayThread implements Runnable {

		@Override
		public void run() {
			overlay_ll.setVisibility(View.GONE);
			// overlay_bottom.setVisibility(View.GONE);
		}
	}

	ArrayList<TVInfo> titleList = new ArrayList<TVInfo>();

	public void setDataSource(String url, String title) {
		Log.e(CommonTools.TAG, "old url=" + url);

		// 由id和type在tvlist来确定epgurl
		// tvInfo = operator.getTVInfoByIdAndType(id, type);
		tvInfo = operator.getTvInfoByTitleAndDefault(title);
		epgUrl = tvInfo.epgUrl;
		Logger.log("epgUrl=" + epgUrl);

		// 由title去查出有多少线路
		titleList = DBOperator.getTvInfoByTitle(title);

		if (StringTools.isNullOrEmpty(epgUrl)) {
			liveControl.setchannleEPG1("当前节目：以实际播放为准");
			liveControl.setchannleEPG2("下个节目：以实际播放为准");
		} else {
			new GetEpgUrlTask(callbackResult, id, type)
					.execute(new String[] { epgUrl });
		}

		if ((url.contains("52itv.cn/live")) || (url.contains("myvst.net/live"))
				|| (url.contains("hdplay.cn/live"))) {
			new GetRedicitIpTask(callback).execute(new String[] { url });
			return;
		} else if (Pattern.compile("hdp_ty\\.php\\?uuid=\\d+$").matcher(url)
				.find()) {
			// else if (url.contains("cntv")) {
			new GetBaseIpTask(callback).execute(new String[] { url });
			return;
		} else {
			playVideo(url);
		}

	}

	ICallBackResult callbackResult = new ICallBackResult() {

		@Override
		public void handleResult(ArrayList list) {
			epgList = list;
			Logger.log("-----------" + epgList.size() + "------------");

			for (int i = 0; i < epgList.size(); i++) {
				if (i != epgList.size() - 1
						&& i > 0
						&& SwissArmyKnife.getTimeDiff(epgList.get(i).time) < 0
						&& SwissArmyKnife.getTimeDiff(epgList.get(i + 1).time) > 0) {
					String s = "前一个节目:" + epgList.get(i - 1).time + "-"
							+ epgList.get(i - 1).detail + "\n当前节目："
							+ epgList.get(i).time + "-" + epgList.get(i).detail
							+ "\n后一个节目：" + epgList.get(i + 1).time + "-"
							+ epgList.get(i + 1).detail;

					// Toast.makeText(MainActivity.this, s, 3000).show();

					// overlay_bottom.setVisibility(View.VISIBLE);
					// tv_bottom.setText(s);

					String epg1 = " " + epgList.get(i).time + " - "
							+ epgList.get(i).detail;
					String epg2 = " " + epgList.get(i + 1).time + " - "
							+ epgList.get(i + 1).detail;

					if (StringTools.isNullOrEmpty(epgList.get(i).time)
							|| StringTools.isNullOrEmpty(epgList.get(i).detail)) {
						liveControl.setchannleEPG1("当前节目：以实际播放为准");
					} else {
						liveControl.setchannleEPG1(epg1);
					}

					if (StringTools.isNullOrEmpty(epgList.get(i + 1).time)
							|| StringTools
									.isNullOrEmpty(epgList.get(i + 1).detail)) {
						liveControl.setchannleEPG2("下个节目：以实际播放为准");
					} else {
						liveControl.setchannleEPG2(epg2);
					}

					handler.removeCallbacks(overlayThread);
					// 延迟一秒后执行，让overlay为不可见
					handler.postDelayed(overlayThread, 1 * 1000);
				}
			}
		}

	};

	ICallBack callback = new ICallBack() {

		@Override
		public void handleResult(String result) {
			if ("".equals(result)) {
				ll_full.setVisibility(View.GONE);
				// Toast.makeText(MainActivity.this, "目前该线路不可用,按左右键切换其他线路",
				// 500).show();
				playVideo("");
			} else {
				playVideo(result);
			}
		}
	};

	public static void saveBaseIp(String basseIp) {
		prefrenceHandler.setBaseIP(basseIp);
	}

	/**
	 * 获取屏幕的宽和高
	 */
	private void getScreenSize() {
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		VitaVideoView.mVideoWidth = dm.widthPixels;
		VitaVideoView.mVideoHeight = dm.heightPixels;
	}

	// 展示一个进度
	CustomProgressDialog dialog;

	public long requestTime = 0L;

	public void playVideo(String url) {
		// 延迟一秒后执行，让overlay为不可见
		handler.postDelayed(overlayThread, 1 * 1000);
		Log.e(CommonTools.TAG, "new url=" + url);
		// Uri uri = Uri.parse(url);

		Message msg = new Message();
		msg.what = 0;
		// 发送一个隐藏的消息
		mHandler.sendMessageDelayed(msg, 2 * 1000);

		videoView.setVideoPath(url);

		if (dialog == null) {
			dialog = new CustomProgressDialog(instance);
		}
		requestTime = System.currentTimeMillis();

		mHandler.postDelayed(nextRunnable, 4 * 1000);

		if (null != dialog && !dialog.isShowing()) {
			dialog.show();
		}

		videoView.setOnPreparedListener(new OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer mp) {
				mHandler.removeCallbacks(nextRunnable);
				// 取消进度
				if (null != dialog) {
					// 发送延迟消息取消
					progressHandler.sendEmptyMessageDelayed(0, 0 * 1000);

					long rwTime = System.currentTimeMillis() - requestTime;
					liveControl.setcwTime(StringUtils.longToSec(rwTime));

				}
				videoView.start();
			}
		});

		videoView.requestFocus();
	}

	private boolean autoNext = false;

	Runnable nextRunnable = new Runnable() {

		@Override
		public void run() {

			// if (titleList.size() > 1) {
			autoNext = true;
			addChannel();
			// }
		}

	};

	static int playNum = 0;
	static int titleNum = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Logger.log("onKeyDown  ----------------执行了------------------");
		// tm.refreshCount();
		switch (keyCode) {
		// 按向下键
		case KeyEvent.KEYCODE_DPAD_DOWN:
			// TimeMannager.getinstance().distory();
			// TimeMannager.getinstance().startCount(this);
			// Toast.makeText(this, "按向下键", 500).show();
			if (listView.getVisibility() == View.VISIBLE) {

			} else {

				if (event.getAction() == KeyEvent.ACTION_DOWN
						&& event.getRepeatCount() == 0) {
					// 重新选的时候置为初始状态
					titleNum = 0;
					playNum++;
					if (playNum >= list.size()) {
						playNum = 0;
					}
					Log.v(CommonTools.TAG, playNum + "<><><><><><>");
					listView.setSelection(playNum);
					url = list.get(playNum).url;
					id = list.get(playNum).id;
					Logger.log("id=" + id);
					title = list.get(playNum).title;
					setDataSource(url, title);
					controlHandler.sendEmptyMessageDelayed(0, 1 * 1000);
				}
			}

			return true;
			// 按向上键
		case KeyEvent.KEYCODE_DPAD_UP:
			// TimeMannager.getinstance().distory();
			// TimeMannager.getinstance().startCount(this);
			// Toast.makeText(this, "按向上键", 500).show();
			if (listView.getVisibility() == View.VISIBLE) {

			} else {
				if (event.getAction() == KeyEvent.ACTION_DOWN
						&& event.getRepeatCount() == 0) {
					// 重新选的时候置为初始状态
					titleNum = 0;
					playNum--;
					if (playNum < 0) {
						playNum = list.size() - 1;
					}
					Log.v(CommonTools.TAG, playNum + "<><><><><><>");
					listView.setSelection(playNum);
					url = list.get(playNum).url;
					id = list.get(playNum).id;
					Logger.log("id=" + id);
					title = list.get(playNum).title;
					setDataSource(url, title);
					controlHandler.sendEmptyMessageDelayed(0, 1 * 1000);
				}
			}

			return true;

			// 按向左键
		case KeyEvent.KEYCODE_DPAD_LEFT:
			// TimeMannager.getinstance().distory();
			// TimeMannager.getinstance().startCount(this);
			// Toast.makeText(this, "按向左键", 500).show();
			if (listView.getVisibility() == View.VISIBLE) {
				// 切换频道
				type--;
				// 35为宁夏
				if (type < 3) {
					type = 35;
				}
				list = operator.getTvInfoByType(type);
				info = operator.getCataInfoByType(type);
				String name = info.name;
				play_title.setText(name);
				adapter = new VideoListAdapter(this, list);
				listView.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				listViewGetFocus();
				listView.setSelection(0);
			} else {
				if (titleList.size() > 1) {
					miniteChannel();
				}
			}

			return true;
			// 按向右键
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			// Toast.makeText(this, "按向右键", 500).show();
			// TimeMannager.getinstance().distory();
			// TimeMannager.getinstance().startCount(this);
			if (listView.getVisibility() == View.VISIBLE) {
				// 切换频道
				type++;
				// 3为空目录(CCTV)
				if (type > 35) {
					type = 3;
				}
				list = operator.getTvInfoByType(type);
				info = operator.getCataInfoByType(type);
				String name = info.name;
				play_title.setText(name);
				adapter = new VideoListAdapter(this, list);
				listView.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				listViewGetFocus();
				listView.setSelection(0);
			} else {
				if (titleList.size() > 1) {
					autoNext = false;
					addChannel();
				}
			}
			return true;
			// OK键
		case KeyEvent.KEYCODE_DPAD_CENTER:
			// Toast.makeText(this, "OK键", 500).show();

			ll_full.setVisibility(View.VISIBLE);
			listView.setVisibility(View.VISIBLE);
			listViewGetFocus();
			// 重新选的时候置为初始状态
			titleNum = 0;
			// TimeMannager.getinstance().distory();
			// TimeMannager.getinstance().startCount(this);
			// ListViewRunnable userRun = new ListViewRunnable();
			// new Handler().postDelayed(userRun, 10 * 1000L);
			return true;
		case KeyEvent.KEYCODE_BACK:
			// showExitDialog();
			// finish();

			if (listView.getVisibility() == View.VISIBLE) {
				listView.setVisibility(View.GONE);
				ll_full.setVisibility(View.GONE);
			} else {
				showExitToast();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private long mExitTime;

	private void showExitToast() {
		if ((System.currentTimeMillis() - mExitTime) > 3000) {
			toast.setText("再按一次退出......");
			toast.setIcon(R.drawable.toast_shut);
			toast.show();
			mExitTime = System.currentTimeMillis();
		} else {
			exitApp();
		}
	}

	private void showExitDialog() {
		new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle("真的要退出吗，不再看会了？")
				.setPositiveButton("不看了",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								exitApp();
							}
						})
				.setNegativeButton("再看会",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {

							}
						}).create().show();
	}

	protected void exitApp() {
		ll_all.startAnimation(new TVOffAnimation());
		UserDialogRunnable userRun = new UserDialogRunnable();
		new Handler().postDelayed(userRun, 300L);
	}

	class UserDialogRunnable implements Runnable {

		@Override
		public void run() {
			
			
			finish();
			overridePendingTransition(R.anim.hyperspace_in,
					R.anim.hyperspace_out);
			// android.os.Process.killProcess(android.os.Process.myPid());
		}

	}

	/**
	 * listview获取焦点
	 */
	private void listViewGetFocus() {
		listView.setFocusable(true);
		listView.requestFocus();
		listView.setFocusableInTouchMode(true);
	}

	class ListViewRunnable implements Runnable {

		@Override
		public void run() {
			ll_full.setVisibility(View.GONE);
			listView.setVisibility(View.GONE);
		}

	}

	private void miniteChannel() {
		// 切换线路(线路向前-)
		titleNum--;
		if (titleNum < 0) {
			titleNum = titleList.size() - 1;
		}
		int channelId = titleList.get(titleNum).channelID;
		id = titleList.get(titleNum).id;
		ChannelInfo info = DBOperator.getChannelByIndex(channelId);
		Logger.log(info.name + "-------------------");
		// Toast.makeText(this, info.name, 500).show();
		String url = titleList.get(titleNum).url;
		String title = titleList.get(titleNum).title;

		overlay.setText(title + " (" + (titleNum + 1) + "/" + titleList.size()
				+ " " + info.name + ")");

		overlay_ll.setVisibility(View.VISIBLE);
		handler.removeCallbacks(overlayThread);
		// 延迟一秒后执行，让overlay为不可见
		handler.postDelayed(overlayThread, 1 * 1000);

		setDataSource(url, title);
		controlHandler.sendEmptyMessageDelayed(0, 1 * 1000);

		liveControl.setchannelSource((titleNum + 1) + "/" + titleList.size());
	}

	private int addCycleNum = 0;

	private void addChannel() {

		// 切换线路(线路向后+)
		titleNum++;
		if (titleNum > titleList.size() - 1) {

			titleNum = 0;

			if (autoNext && titleNum == 0) {
				addCycleNum += 1;
			}
			if (addCycleNum >= 3) {
				autoNext = false;
				// 移除线程
				mHandler.removeCallbacks(nextRunnable);
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("提示");
				builder.setMessage("您的网络欠佳或当前的源都不可用");
				builder.create();
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// 重新计算
								addCycleNum = 0;
								if (null != dialog && dialog.isShowing()) {
									dialog.cancel();
								}

								videoView.stopPlayback();
							}

						});
				builder.show();
				return;
			}

		}
		int channelId = titleList.get(titleNum).channelID;
		id = titleList.get(titleNum).id;
		ChannelInfo info = DBOperator.getChannelByIndex(channelId);
		Logger.log(info.name + "-------------------");
		// Toast.makeText(this, info.name, 500).show();
		String url = titleList.get(titleNum).url;
		String title = titleList.get(titleNum).title;

		overlay.setText(title + " (" + (titleNum + 1) + "/" + titleList.size()
				+ " " + info.name + ")");
		overlay_ll.setVisibility(View.VISIBLE);
		handler.removeCallbacks(overlayThread);
		// 延迟一秒后执行，让overlay为不可见
		handler.postDelayed(overlayThread, 1 * 1000);
		setDataSource(url, title);
		controlHandler.sendEmptyMessageDelayed(0, 1 * 1000);

		liveControl.setchannelSource((titleNum + 1) + "/" + titleList.size());
	}

	OnErrorListener errorListener = new OnErrorListener() {

		@Override
		public boolean onError(MediaPlayer mp, int what, int extra) {
			// 取消进度
			if (null != dialog) {
				// 发送延迟消息取消
				progressHandler.sendEmptyMessage(0);
			}
			//mHandler.removeCallbacks(nextRunnable);

			// if (titleList.size() > 1) {
			autoNext = false;
			addChannel();
			// }
			return true;
		}
	};

	protected void onDestroy() {
		// tm.distory();
		windowManager.removeView(overlay_ll);
		liveControl.dismiss();
		mHandler.removeCallbacks(nextRunnable);
		super.onDestroy();
	};

}
