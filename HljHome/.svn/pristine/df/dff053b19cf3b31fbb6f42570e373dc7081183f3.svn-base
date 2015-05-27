package com.hlj.HomeActivity;

import java.util.ArrayList;
import java.util.regex.Pattern;

import com.hj.widget.HljVideoView;
import com.hlj.adapter.VideoColumnAdapter;
import com.hlj.adapter.VideoLiveAdapter;
import com.hlj.utils.CommonTools;
import com.hlj.utils.Logger;
import com.hlj.utils.SwissArmyKnife;
import com.hlj.utils.TVOffAnimation;
import com.live.video.constans.CataInfo;
import com.live.video.constans.ChannelInfo;
import com.live.video.constans.EpgInfo;
import com.live.video.constans.TVInfo;
import com.live.video.db.DBOperator;
import com.live.video.network.GetBaseIpTask;
import com.live.video.network.GetEpgUrlTask;
import com.live.video.network.GetRedicitIpTask;
import com.live.video.network.ICallBack;
import com.live.video.network.ICallBackResult;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 直播
 * 
 * @author huangyuchao
 * 
 */
public class TVLiveActivity extends Activity {

//	HljVideoView videoView;
//	TextView play_title;
//	ListView listView;
//	VideoLiveAdapter adapter;
//
//	// 默认为2(CCTV)
//	public static int type = 2;
//	ArrayList<TVInfo> list;
//	DBOperator operator;
//	CataInfo info;
//
//	// 播放路径
//	String url = "";
//	// 标题
//	public String title;
//	// id
//	public int id;
//
//	FrameLayout fl_tvlive;
//
//	TextView tv_back_current_channel;// 当前频道
//	TextView tv_back_current_tv;// 当前播放栏目
//	TextView tv_back_next_tv;// 下一个栏目
//
//	boolean itemClick = true;
//
//	ListView tv_back_videos;
//
//	ArrayList<EpgInfo> epgList = new ArrayList<EpgInfo>();
//	VideoColumnAdapter columnAdapter;
//
//	HljVideoView fullVideoView;
//	private final static int SCREEN_FULL = 0;
//	private final static int SCREEN_DEFAULT = 1;
//
//	int prePosition = 0;
//
//	boolean isReturn = false;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.tv_live_view);
//
//		play_title = (TextView) findViewById(R.id.play_title);
//		listView = (ListView) findViewById(R.id.play_list);
//		videoView = (HljVideoView) findViewById(R.id.myVideoView);
//		videoView.setOnErrorListener(errorListener);
//		fl_tvlive = (FrameLayout) findViewById(R.id.fl_tvlive);
//
//		fullVideoView = (HljVideoView) findViewById(R.id.fullVideoView);
//
//		tv_back_current_channel = (TextView) findViewById(R.id.tv_back_current_channel);
//		tv_back_current_tv = (TextView) findViewById(R.id.tv_back_current_tv);
//		tv_back_next_tv = (TextView) findViewById(R.id.tv_back_next_tv);
//
//		tv_back_videos = (ListView) findViewById(R.id.tv_back_videos);
//		columnAdapter = new VideoColumnAdapter(this, epgList);
//		tv_back_videos.setAdapter(columnAdapter);
//		columnAdapter.notifyDataSetChanged();
//		operator = new DBOperator(this);
//
//		list = operator.getTvInfoByType(type);
//		info = operator.getCataInfoByType(type);
//		play_title.setText(info.name);
//
//		url = list.get(0).url;
//		title = list.get(0).title;
//		id = list.get(0).id;
//
//		adapter = new VideoLiveAdapter(this, list);
//		listView.setAdapter(adapter);
//		listView.setSelection(0);
//		adapter.notifyDataSetChanged();
//
//		// getScreenSize();
//
//		listView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long num) {
//
//				playNum = position;
//				url = list.get(position).url;
//				title = list.get(position).title;
//				id = list.get(position).id;
//
//				if (prePosition == position) {
//
//					// 一种是跳转Activity的方式
//					// Intent it = new Intent(TVLiveActivity.this,
//					// MainActivity.class);
//					// it.putExtra("url", url);
//					// it.putExtra("id", id);
//					// it.putExtra("title", title);
//					// it.putExtra("type", type);
//					//
//					// startActivity(it);
//
//					// 另一种是直接设置全屏方式
//					getScreenSize();
//					setVideoScale(SCREEN_FULL);
//
//					isReturn = true;
//				} else {
//					setDataSource(url, title);
//				}
//
//				prePosition = position;
//
//			}
//		});
//
//		listViewGetFocus();
//
//		// setDataSource(url, title);
//	}
//
//	private void setVideoScale(int flag) {
//		if (flag == SCREEN_FULL) {
//			Logger.log("screenWidth: " + HljVideoView.mVideoWidth
//					+ " screenHeight: " + HljVideoView.mVideoHeight);
//			videoView.setVideofullScale(HljVideoView.mVideoWidth,
//					HljVideoView.mVideoHeight);
//			// videoView.setPadding(0, 0, 0, 0);
//			// fullVideoView.setVisibility(View.VISIBLE);
//			getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		} else if (flag == SCREEN_DEFAULT) {
//			videoView.setVideoDefaultScale(500, 285);
//			// getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		}
//
//	}
//
//	/**
//	 * 获取屏幕的宽和高
//	 */
//	private void getScreenSize() {
//		DisplayMetrics dm = new DisplayMetrics();
//		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
//		HljVideoView.mVideoWidth = dm.widthPixels;
//		HljVideoView.mVideoHeight = dm.heightPixels;
//	}
//
//	@Override
//	protected void onResume() {
//		setDataSource(url, title);
//		super.onResume();
//	}
//
//	/**
//	 * listview获取焦点
//	 */
//	private void listViewGetFocus() {
//		listView.setFocusable(true);
//		listView.requestFocus();
//		listView.setFocusableInTouchMode(true);
//	}
//
//	ArrayList<TVInfo> titleList = new ArrayList<TVInfo>();
//	TVInfo tvInfo;
//	public String epgUrl;
//
//	public void setDataSource(String url, String title) {
//		Log.e(CommonTools.TAG, "old url=" + url);
//
//		tv_back_current_channel.setText(title);
//
//		// 由id和type在tvlist来确定epgurl
//		tvInfo = operator.getTVInfoByIdAndType(id, type);
//		epgUrl = tvInfo.epgUrl;
//		Logger.log("epgUrl=" + epgUrl);
//
//		if (null != epgUrl && !"".equals(epgUrl)) {
//			new GetEpgUrlTask(callbackResult, id, type)
//					.execute(new String[] { epgUrl });
//		}
//		// 由title去查出有多少线路
//		titleList = DBOperator.getTvInfoByTitle(title);
//
//		if ((url.contains("52itv.cn/live")) || (url.contains("myvst.net/live"))
//				|| (url.contains("hdplay.cn/live"))) {
//			new GetRedicitIpTask(callback).execute(new String[] { url });
//			return;
//		} else if (Pattern.compile("hdp_ty\\.php\\?uuid=\\d+$").matcher(url)
//				.find()) {
//			// else if (url.contains("cntv")) {
//			new GetBaseIpTask(callback).execute(new String[] { url });
//			return;
//		} else {
//			playVideo(url);
//		}
//
//	}
//
//	ICallBack callback = new ICallBack() {
//
//		@Override
//		public void handleResult(String result) {
//			if ("".equals(result)) {
//				// Toast.makeText(MainActivity.this, "目前该线路不可用,按左右键切换其他线路",
//				// 500).show();
//				playVideo("");
//			} else {
//				playVideo(result);
//			}
//		}
//	};
//
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		Logger.log("onKeyDown  ----------------执行了------------------");
//		switch (keyCode) {
//		// 按向下键
//		case KeyEvent.KEYCODE_DPAD_DOWN:
//			// TimeMannager.getinstance().distory();
//			// TimeMannager.getinstance().startCount(this);
//			// Toast.makeText(this, "按向下键", 500).show();
//			if (listView.getVisibility() == View.VISIBLE) {
//
//			} else {
//
//				if (event.getAction() == KeyEvent.ACTION_DOWN
//						&& event.getRepeatCount() == 0) {
//					playNum++;
//					if (playNum >= list.size()) {
//						playNum = 0;
//					}
//					Log.v(CommonTools.TAG, playNum + "<><><><><><>");
//					listView.setSelection(playNum);
//					url = list.get(playNum).url;
//					id = list.get(playNum).id;
//					Logger.log("id=" + id);
//					String title = titleList.get(titleNum).title;
//					setDataSource(url, title);
//				}
//			}
//
//			break;
//		// 按向上键
//		case KeyEvent.KEYCODE_DPAD_UP:
//			// TimeMannager.getinstance().distory();
//			// TimeMannager.getinstance().startCount(this);
//			// Toast.makeText(this, "按向上键", 500).show();
//			if (listView.getVisibility() == View.VISIBLE) {
//
//			} else {
//				if (event.getAction() == KeyEvent.ACTION_DOWN
//						&& event.getRepeatCount() == 0) {
//					playNum--;
//					if (playNum < 0) {
//						playNum = list.size() - 1;
//					}
//					Log.v(CommonTools.TAG, playNum + "<><><><><><>");
//					listView.setSelection(playNum);
//					url = list.get(playNum).url;
//					id = list.get(playNum).id;
//					Logger.log("id=" + id);
//					String title = titleList.get(titleNum).title;
//					setDataSource(url, title);
//				}
//			}
//
//			break;
//
//		// 按向左键
//		case KeyEvent.KEYCODE_DPAD_LEFT:
//			// TimeMannager.getinstance().distory();
//			// TimeMannager.getinstance().startCount(this);
//			// Toast.makeText(this, "按向左键", 500).show();
//			if (listView.getVisibility() == View.VISIBLE) {
//				// 切换频道
//				type--;
//				// 31为贵州台
//				if (type < 2) {
//					type = 31;
//				}
//				list = operator.getTvInfoByType(type);
//				info = operator.getCataInfoByType(type);
//				String name = info.name;
//				play_title.setText(name);
//				adapter = new VideoLiveAdapter(this, list);
//				listView.setAdapter(adapter);
//				adapter.notifyDataSetChanged();
//				listViewGetFocus();
//				listView.setSelection(0);
//			} else {
//				miniteChannel();
//			}
//
//			break;
//		// 按向右键
//		case KeyEvent.KEYCODE_DPAD_RIGHT:
//			// Toast.makeText(this, "按向右键", 500).show();
//			// TimeMannager.getinstance().distory();
//			// TimeMannager.getinstance().startCount(this);
//			if (listView.getVisibility() == View.VISIBLE) {
//				// 切换频道
//				type++;
//				// 31为贵州台
//				if (type > 31) {
//					type = 2;
//				}
//				list = operator.getTvInfoByType(type);
//				info = operator.getCataInfoByType(type);
//				String name = info.name;
//				play_title.setText(name);
//				adapter = new VideoLiveAdapter(this, list);
//				listView.setAdapter(adapter);
//				adapter.notifyDataSetChanged();
//				listViewGetFocus();
//				listView.setSelection(0);
//			} else {
//				addChannel();
//			}
//			break;
//		// OK键
//		case KeyEvent.KEYCODE_DPAD_CENTER:
//			// Toast.makeText(this, "OK键", 500).show();
//			listView.setVisibility(View.VISIBLE);
//			listViewGetFocus();
//			// 重新选的时候置为初始状态
//			titleNum = 0;
//			// TimeMannager.getinstance().distory();
//			// TimeMannager.getinstance().startCount(this);
//			// ListViewRunnable userRun = new ListViewRunnable();
//			// new Handler().postDelayed(userRun, 10 * 1000L);
//			break;
//		case KeyEvent.KEYCODE_BACK:
//
//			if (isReturn) {
//				setVideoScale(SCREEN_DEFAULT);
//				isReturn = false;
//			} else {
//				showExitDialog();
//				isReturn = true;
//			}
//			break;
//		}
//		return true;
//	}
//
//	private void showExitDialog() {
//		new AlertDialog.Builder(this)
//				.setIcon(android.R.drawable.ic_dialog_alert)
//				.setTitle("真的要退出吗，不再看会了？")
//				.setPositiveButton("不看了",
//						new DialogInterface.OnClickListener() {
//							public void onClick(DialogInterface dialog,
//									int whichButton) {
//								exitApp();
//							}
//						})
//				.setNegativeButton("再看会",
//						new DialogInterface.OnClickListener() {
//							public void onClick(DialogInterface dialog,
//									int whichButton) {
//								isReturn = false;
//							}
//						}).create().show();
//	}
//
//	protected void exitApp() {
//		fl_tvlive.startAnimation(new TVOffAnimation());
//		UserDialogRunnable userRun = new UserDialogRunnable();
//		new Handler().postDelayed(userRun, 300L);
//	}
//
//	class UserDialogRunnable implements Runnable {
//
//		@Override
//		public void run() {
//			finish();
//			overridePendingTransition(R.anim.hyperspace_in,
//					R.anim.hyperspace_out);
//			// android.os.Process.killProcess(android.os.Process.myPid());
//		}
//
//	}
//
//	ICallBackResult callbackResult = new ICallBackResult() {
//
//		@Override
//		public void handleResult(ArrayList list) {
//			epgList = list;
//			Logger.log("-----------" + epgList.size() + "------------");
//			columnAdapter = new VideoColumnAdapter(TVLiveActivity.this, epgList);
//			tv_back_videos.setAdapter(columnAdapter);
//			columnAdapter.notifyDataSetChanged();
//
//			for (int i = 0; i < epgList.size(); i++) {
//				if (i != epgList.size() - 1
//						&& i > 0
//						&& SwissArmyKnife.getTimeDiff(epgList.get(i).time) < 0
//						&& SwissArmyKnife.getTimeDiff(epgList.get(i + 1).time) > 0) {
//					String s = "前一个节目:" + epgList.get(i - 1).time + "-"
//							+ epgList.get(i - 1).detail + "\n当前节目："
//							+ epgList.get(i).time + "-" + epgList.get(i).detail
//							+ "\n后一个节目：" + epgList.get(i + 1).time + "-"
//							+ epgList.get(i + 1).detail;
//
//					tv_back_current_tv.setText(epgList.get(i).detail);
//					tv_back_next_tv.setText(epgList.get(i + 1).detail);
//					tv_back_videos.setSelection(i);
//					// Toast.makeText(TVLiveActivity.this, s, 3000).show();
//				}
//			}
//		}
//
//	};
//
//	OnErrorListener errorListener = new OnErrorListener() {
//
//		@Override
//		public boolean onError(MediaPlayer mp, int what, int extra) {
//			addChannel();
//			return true;
//		}
//	};
//
//	private void miniteChannel() {
//		// 切换线路(线路向前-)
//		titleNum--;
//		if (titleNum < 0) {
//			titleNum = titleList.size() - 1;
//		}
//		int channelId = titleList.get(titleNum).channelID;
//		id = titleList.get(titleNum).id;
//		ChannelInfo info = DBOperator.getChannelByIndex(channelId);
//		Logger.log(info.name + "-------------------");
//		// Toast.makeText(this, info.name, 500).show();
//		String url = titleList.get(titleNum).url;
//		String title = titleList.get(titleNum).title;
//
//		setDataSource(url, title);
//	}
//
//	private void addChannel() {
//		// 切换线路(线路向后+)
//		titleNum++;
//		if (titleNum > titleList.size() - 1) {
//			titleNum = 0;
//		}
//		int channelId = titleList.get(titleNum).channelID;
//		id = titleList.get(titleNum).id;
//		ChannelInfo info = DBOperator.getChannelByIndex(channelId);
//		Logger.log(info.name + "-------------------");
//		// Toast.makeText(this, info.name, 500).show();
//		String url = titleList.get(titleNum).url;
//		String title = titleList.get(titleNum).title;
//
//		setDataSource(url, title);
//	}
//
//	int playNum = 0;
//	int titleNum = 0;
//
//	public void playVideo(String url) {
//		Log.e(CommonTools.TAG, "new url=" + url);
//		Uri uri = Uri.parse(url);
//		// videoView.setMediaController(new MediaController(this));
//		videoView.setVideoURI(uri);
//		videoView.start();
//		// videoView.requestFocus();
//	}

}
