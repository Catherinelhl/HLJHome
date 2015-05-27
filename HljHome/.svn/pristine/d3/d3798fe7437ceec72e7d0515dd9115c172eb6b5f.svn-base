package com.hlj.HomeActivity;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.hj.widget.CommonToast;
import com.hj.widget.VodBackCtrBot;
import com.hj.widget.VodListBackCtrBot;
import com.hlj.HomeActivity.TVBackActivity.Reminder.RemindTask;
import com.hlj.adapter.VideoBackChannelAdapter;
import com.hlj.adapter.VideoBackColumnAdapter;
import com.hlj.net.HttpHomeLoadDataTask;
import com.hlj.utils.CommonTools;
import com.hlj.utils.Logger;
import com.hlj.utils.TimerTools;
import com.hlj.view.CommonTitleView;
import com.hlj.view.CustomProgressDialog;
import com.hlj.widget.BackVideoView;
import com.hlj.widget.ListVideoView;
import com.hlj.widget.MediaController;
import com.live.video.constans.ChannelInfo;
import com.live.video.constans.ChapterInfo;
import com.live.video.constans.Constants;
import com.live.video.constans.EpgInfo;
import com.live.video.db.TvBackDbOperator;
import com.live.video.net.GetChannelRequest;
import com.live.video.net.GetChannelResponse;
import com.live.video.net.GetColumnRequest;
import com.live.video.net.GetColumnResponse;
import com.live.video.net.GetPlayUrlRequest;
import com.live.video.net.GetPlayUrlResponse;
import com.live.video.net.callback.IUpdateData;

/**
 * 电视回看(实际使用)
 * 
 * @author office
 * 
 */
public class TvListBackActivity extends BaseActivity {
	ListVideoView videoView;
	// BackVideoView videoView;

	TextView tv_back_current_channel;// 当前频道
	TextView tv_back_current_tv;// 当前播放栏目
	TextView tv_back_next_tv;// 下一个栏目

	ListView tv_back_channles;
	ListView tv_back_videos;
	VideoBackChannelAdapter channelAdapter;
	ArrayList<ChannelInfo> channelList = new ArrayList<ChannelInfo>();

	VideoBackColumnAdapter columnAdapter;
	ArrayList<EpgInfo> epgList = new ArrayList<EpgInfo>();

	RadioGroup tv_back_weekdays;
	RadioButton tv_back_rd_Sunday;
	RadioButton tv_back_rd_Monday;
	RadioButton tv_back_rd_Tuesday;
	RadioButton tv_back_rd_Wednesday;
	RadioButton tv_back_rd_Thursday;
	RadioButton tv_back_rd_Friday;
	RadioButton tv_back_rd_Saturday;

	public static TvListBackActivity instance;

	int prePosition = -1;

	public final static int SCREEN_FULL = 0;
	public final static int SCREEN_DEFAULT = 1;

	boolean isReturn = false;

	public static int REQUEST_FULL = 0;

	private int balanceIndex = 0;

	CommonTitleView commonTitleView;

	TvBackDbOperator dbOperator;

	private VodListBackCtrBot ctrbot;

	public static int channelPosition = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tv_back_view_after);

		commonTitleView = (CommonTitleView) findViewById(R.id.commonTitle);
		commonTitleView.setTypeDetailsText("电视回看");
		
		request= new GetColumnRequest(this);

		videoView = (ListVideoView) findViewById(R.id.tv_back_video);

		ctrbot = new VodListBackCtrBot(this, videoView, handler);

		tv_back_current_channel = (TextView) findViewById(R.id.tv_back_current_channel);
		tv_back_current_tv = (TextView) findViewById(R.id.tv_back_current_tv);
		tv_back_next_tv = (TextView) findViewById(R.id.tv_back_next_tv);

		tv_back_weekdays = (RadioGroup) findViewById(R.id.tv_back_weekdays);

		tv_back_rd_Sunday = (RadioButton) findViewById(R.id.tv_back_rd_Sunday);
		tv_back_rd_Monday = (RadioButton) findViewById(R.id.tv_back_rd_Monday);
		tv_back_rd_Tuesday = (RadioButton) findViewById(R.id.tv_back_rd_Tuesday);
		tv_back_rd_Wednesday = (RadioButton) findViewById(R.id.tv_back_rd_Wednesday);
		tv_back_rd_Thursday = (RadioButton) findViewById(R.id.tv_back_rd_Thursday);
		tv_back_rd_Friday = (RadioButton) findViewById(R.id.tv_back_rd_Friday);
		tv_back_rd_Saturday = (RadioButton) findViewById(R.id.tv_back_rd_Saturday);

		tv_back_rd_Saturday.setChecked(true);
		tv_back_weekdays.setOnCheckedChangeListener(listener);

		tv_back_channles = (ListView) findViewById(R.id.tv_back_channles);

		tv_back_videos = (ListView) findViewById(R.id.tv_back_videos);
		columnAdapter = new VideoBackColumnAdapter(this, epgList);
		tv_back_videos.setAdapter(columnAdapter);

		dbOperator = new TvBackDbOperator(this);

		// 从数据库查
		channelList = dbOperator.getChannelInfo();

		if (channelList != null && channelList.size() > 0) {
			channelAdapter = new VideoBackChannelAdapter(
					TvListBackActivity.this, channelList);
			tv_back_channles.setAdapter(channelAdapter);
			tv_back_channles.setSelection(channelPosition);
			tv_back_channles.setItemsCanFocus(true);
			channelAdapter.notifyDataSetChanged();

			tv_back_current_channel
					.setText(channelList.get(channelPosition).name);

		} else {
			GetChannelRequest chanRequset = new GetChannelRequest(this);
			new HttpHomeLoadDataTask(this, chanCallBack, false, "", false)
					.execute(chanRequset);
		}

		new HttpHomeLoadDataTask(TvListBackActivity.this, callBack, true, "",
				false).execute(request);

		tv_back_channles.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				channelPosition = position;
				balanceIndex = 0;
				videoView.stopPlayback();
				isReturn = false;
				isError = false;
				prePosition = -1;

				request.channel = channelList.get(position).channel;
				tv_back_current_channel.setText(channelList.get(position).name);

				new HttpHomeLoadDataTask(TvListBackActivity.this, callBack,
						true, "", false).execute(request);
			}
		});

		tv_back_videos.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				isError = false;

				GetPlayUrlRequest playUrlRequest = new GetPlayUrlRequest(TvListBackActivity.this);
				playUrlRequest.channel = request.channel;
				EpgInfo info = epgList.get(position);
				playUrlRequest.starttime = info.starttime;
				playUrlRequest.endtime = info.endtime;

				changeRadioImg(balanceIndex, false);
				changeRadioImg(position, true);
				balanceIndex = position;

				if (position == epgList.size() - 1) {
					tv_back_current_tv.setText(epgList.get(position).title);
					tv_back_next_tv.setText("无");
				}

				if (epgList != null && epgList.size() > 0
						&& position + 1 < epgList.size()) {
					tv_back_current_tv.setText(epgList.get(position).title);
					tv_back_next_tv.setText(epgList.get(position + 1).title);
				}

				if (prePosition == position) {

					// Intent it = new Intent(TVBackActivity.this,
					// VideoViewPlayingActivity.class);
					// startActivityForResult(it, REQUEST_FULL);

					// 另一种是直接设置全屏方式
					getScreenSize();
					setVideoScale(SCREEN_FULL);

				} else {
					// 新的节目重新计算
					playPosition = 0;
					videoView.stopPlayback();

					new HttpHomeLoadDataTask(TvListBackActivity.this,
							playUrlCallBack, true, "", false)
							.execute(playUrlRequest);
				}
				prePosition = position;

			}
		});

		listViewGetFocus();

		initOperatHintPop();

		instance = this;

	}

	private void changeRadioImg(int selectedItemPosition, boolean b) {
		EpgInfo info = (EpgInfo) columnAdapter.getItem(selectedItemPosition);
		if (b) {
			info.imageMap.put("img", R.drawable.onplay);
			info.textMap.put("text", "(再次点击全屏)");
		} else {
			info.imageMap.put("img", R.drawable.huikan);
			info.textMap.put("text", "");
		}
		columnAdapter.notifyDataSetChanged();
	}

	protected void onResume() {
		CommonTitleView.instance = commonTitleView;
		super.onResume();
	};

	MediaController mMediaController;

	public void setVideoScale(int flag) {
		if (flag == SCREEN_FULL) {
			Logger.log("screenWidth: " + videoView.mVideoWidth
					+ " screenHeight: " + videoView.mVideoHeight);
			videoView.setVideofullScale(videoView.mVideoWidth,
					videoView.mVideoHeight);

			// 全屏之后获取焦点
			videoView.requestFocus();
			videoView.setFocusable(true);
			// mMediaController = new MediaController(this);
			// 全屏之后设置进度条
			// videoView.setMediaController(mMediaController);
			videoView.setPadding(0, 0, 0, 0);
			// mMediaController.show();
			// fullVideoView.setVisibility(View.VISIBLE);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
			isReturn = true;
		} else if (flag == SCREEN_DEFAULT) {
			videoView.setVideoDefaultScale(550, 280);

			isReturn = false;
			// getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}

	}

	/**
	 * 获取屏幕的宽和高
	 */
	private void getScreenSize() {
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		videoView.mVideoWidth = dm.widthPixels;
		videoView.mVideoHeight = dm.heightPixels;
	}

	/**
	 * listview获取焦点
	 */
	private void listViewGetFocus() {
		tv_back_channles.setFocusable(true);
		tv_back_channles.requestFocus();
		tv_back_channles.setFocusableInTouchMode(true);
	}

	OnCheckedChangeListener listener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			prePosition = -1;

			if (response.columnList.size() > 0) {
				switch (checkedId) {
				// 切换adapter数据
				case R.id.tv_back_rd_Sunday:
					epgList = (ArrayList<EpgInfo>) response.columnList.get(0)
							.get(response.date1);
					columnAdapter = new VideoBackColumnAdapter(
							TvListBackActivity.this, epgList);
					tv_back_videos.setAdapter(columnAdapter);
					break;
				case R.id.tv_back_rd_Monday:
					epgList = (ArrayList<EpgInfo>) response.columnList.get(0)
							.get(response.date2);
					columnAdapter = new VideoBackColumnAdapter(
							TvListBackActivity.this, epgList);
					tv_back_videos.setAdapter(columnAdapter);
					break;
				case R.id.tv_back_rd_Tuesday:
					epgList = (ArrayList<EpgInfo>) response.columnList.get(0)
							.get(response.date3);
					columnAdapter = new VideoBackColumnAdapter(
							TvListBackActivity.this, epgList);
					tv_back_videos.setAdapter(columnAdapter);
					break;
				case R.id.tv_back_rd_Wednesday:
					epgList = (ArrayList<EpgInfo>) response.columnList.get(0)
							.get(response.date4);
					columnAdapter = new VideoBackColumnAdapter(
							TvListBackActivity.this, epgList);
					tv_back_videos.setAdapter(columnAdapter);
					break;
				case R.id.tv_back_rd_Thursday:
					epgList = (ArrayList<EpgInfo>) response.columnList.get(0)
							.get(response.date5);
					columnAdapter = new VideoBackColumnAdapter(
							TvListBackActivity.this, epgList);
					tv_back_videos.setAdapter(columnAdapter);
					break;
				case R.id.tv_back_rd_Friday:
					epgList = (ArrayList<EpgInfo>) response.columnList.get(0)
							.get(response.date6);
					columnAdapter = new VideoBackColumnAdapter(
							TvListBackActivity.this, epgList);
					tv_back_videos.setAdapter(columnAdapter);
					break;
				case R.id.tv_back_rd_Saturday:
					epgList = (ArrayList<EpgInfo>) response.columnList.get(0)
							.get(response.date7);
					columnAdapter = new VideoBackColumnAdapter(
							TvListBackActivity.this, epgList);
					tv_back_videos.setAdapter(columnAdapter);
					break;

				}
			} else {
				CommonToast commonToast = new CommonToast(
						TvListBackActivity.this);
				commonToast.setText("无节目列表");
				commonToast.setIcon(R.drawable.toast_err);
				commonToast.show();
			}

		}

	};

	private long exitTime = 0;
	private PopupWindow hintPop;
	private ImageView hintPopImg;

	private void initOperatHintPop() {
		hintPop = new PopupWindow();
		hintPopImg = new ImageView(this);
		hintPop.setWindowLayoutMode(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		hintPop.setBackgroundDrawable(new BitmapDrawable());
		WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		this.hintPopImg.setLayoutParams(localLayoutParams);
		this.hintPop.setContentView(this.hintPopImg);
	}

	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {

		Logger.log("onKeyDown  ----------------执行了------------------");
		switch (keyCode) {

		case KeyEvent.KEYCODE_DPAD_CENTER:
			Logger.log("KEYCODE_DPAD_CENTER  ----------------执行了------------------");
			if (isReturn) {
				// mMediaController.show();
				// ctrbot.showAtLocation(videoView, Gravity.BOTTOM, 0, 100);
				if (videoView.isPlaying()) {
					// 暂停
					videoView.pause();
					// 展示暂停
					hintPopImg.setImageResource(R.drawable.osd_pause);
					hintPop.showAtLocation(videoView, 17, 0, 0);
				} else {
					hintPop.dismiss();
					videoView.start();
				}

			}
			break;

		case KeyEvent.KEYCODE_DPAD_LEFT:
			Logger.log("KEYCODE_DPAD_LEFT  ----------------执行了------------------");
			if (isReturn) {
				// mMediaController.show();

				ctrbot.showAtLocation(videoView, Gravity.BOTTOM, 0, 100);
			}
			// if (event.getRepeatCount() == 0 && event.getEventTime() > 2 *
			// 1000) {
			// if ((System.currentTimeMillis() - exitTime) > 3000) {
			// if (isReturn) {
			// // videoBack();
			// return true;
			// }
			// exitTime = System.currentTimeMillis();
			// }
			// }

			break;

		case KeyEvent.KEYCODE_DPAD_RIGHT:
			Logger.log("KEYCODE_DPAD_RIGHT  ----------------执行了------------------");
			if (isReturn) {
				// mMediaController.show();
				ctrbot.showAtLocation(videoView, Gravity.BOTTOM, 0, 100);
			}
			// if (event.getRepeatCount() == 0 && event.getEventTime() > 2 *
			// 1000) {
			//
			// if ((System.currentTimeMillis() - exitTime) > 3000) {
			// if (isReturn) {
			// // videoAdd();
			// return true;
			// }
			// exitTime = System.currentTimeMillis();
			// }
			//
			// }
			break;

		case KeyEvent.KEYCODE_BACK:
			Logger.log("KEYCODE_BACK  ----------------执行了------------------");
			if (isReturn) {
				setVideoScale(SCREEN_DEFAULT);
				videoView.setFocusable(false);
				// if (mMediaController.isShowing()) {
				// mMediaController.hide();
				// }
				tv_back_videos.requestFocus();
				tv_back_videos.setFocusable(true);

				if (!videoView.isPlaying()) {
					hintPop.dismiss();
					videoView.start();
				}
				isReturn = false;
			} else {
				finish();
				isReturn = true;
			}
			return true;

		}

		return super.onKeyDown(keyCode, event);
	};

	/**
	 * 跳回前一个
	 * 
	 * @param pos
	 * @param startPos
	 */
	private void switchToPreview(long pos, long startPos) {
		if (pos < startPos) {
			playPosition--;
			// 播放下一个
			if (playPosition < chapterList.size() && playPosition >= 0) {
				String url = (String) chapterList.get(playPosition).url;
				Message msg = new Message();
				msg.obj = url;
				handler.sendMessage(msg);
			}
			if (playPosition < 0) {
				// 播放到起始位置
				playPosition = 0;
			}
		}
	}

	/**
	 * 跳到下一个
	 * 
	 * @param pos
	 * @param endPos
	 */
	private void switchToNext(long pos, long endPos) {

		if (pos >= endPos) {
			playPosition++;
			// 播放下一个
			if (playPosition < chapterList.size()) {
				String url = (String) chapterList.get(playPosition).url;
				Message msg = new Message();
				msg.obj = url;
				handler.sendMessage(msg);
			}
			if (playPosition == chapterList.size()) {
				// 播放到最后清空
				playPosition = 0;
			}
		}
	}

	GetColumnRequest request;
	GetColumnResponse response = new GetColumnResponse();

	IUpdateData chanCallBack = new IUpdateData() {

		@Override
		public void updateUi(Object o) {
			String json = o.toString();
			Logger.log(json);
			GetChannelResponse chanResponse = new GetChannelResponse();
			chanResponse.paseRespones(json);
			channelList = chanResponse.list;
			channelAdapter = new VideoBackChannelAdapter(
					TvListBackActivity.this, channelList);
			tv_back_channles.setAdapter(channelAdapter);

			tv_back_current_channel.setText(channelList.get(0).name);

			// 插入到数据库中
			dbOperator.insertBackChannelInfo(chanResponse.list);
		}

		@Override
		public void handleErrorData(String info) {
			// TODO Auto-generated method stub

		}
	};

	IUpdateData callBack = new IUpdateData() {

		@Override
		public void updateUi(Object o) {
			String json = o.toString();
			Logger.log(json);

			response.paseRespones(json);

			epgList = (ArrayList<EpgInfo>) response.columnList.get(0).get(
					response.date7);
			columnAdapter = new VideoBackColumnAdapter(TvListBackActivity.this,
					epgList);
			tv_back_videos.setAdapter(columnAdapter);

			tv_back_rd_Sunday.setText(response.date1);
			tv_back_rd_Sunday.setBackgroundResource(TimerTools
					.getResource(response.date1));
			tv_back_rd_Monday.setText(response.date2);
			tv_back_rd_Monday.setBackgroundResource(TimerTools
					.getResource(response.date2));
			tv_back_rd_Tuesday.setText(response.date3);
			tv_back_rd_Tuesday.setBackgroundResource(TimerTools
					.getResource(response.date3));
			tv_back_rd_Wednesday.setText(response.date4);
			tv_back_rd_Wednesday.setBackgroundResource(TimerTools
					.getResource(response.date4));
			tv_back_rd_Thursday.setText(response.date5);
			tv_back_rd_Thursday.setBackgroundResource(TimerTools
					.getResource(response.date5));
			tv_back_rd_Friday.setText(response.date6);
			tv_back_rd_Friday.setBackgroundResource(TimerTools
					.getResource(response.date6));
			tv_back_rd_Saturday.setText(response.date7);
			tv_back_rd_Saturday.setBackgroundResource(TimerTools
					.getResource(response.date7));

			tv_back_rd_Saturday.setChecked(true);

			if (epgList != null && epgList.size() > 1) {
				tv_back_current_tv.setText(epgList.get(0).title);
				tv_back_next_tv.setText(epgList.get(1).title);
			}

			columnAdapter.notifyDataSetChanged();

			if (epgList != null && epgList.size() > 0) {
				GetPlayUrlRequest playUrlRequest = new GetPlayUrlRequest(TvListBackActivity.this);
				playUrlRequest.channel = request.channel;
				playUrlRequest.starttime = epgList.get(0).starttime;
				playUrlRequest.endtime = epgList.get(0).endtime;
				new HttpHomeLoadDataTask(TvListBackActivity.this,
						playUrlCallBack, false, "", false)
						.execute(playUrlRequest);
			}

		}

		@Override
		public void handleErrorData(String info) {
			// TODO Auto-generated method stub

		}
	};

	public static ArrayList<ChapterInfo> chapterList = new ArrayList<ChapterInfo>();

	public static int playPosition = 0;

	IUpdateData playUrlCallBack = new IUpdateData() {

		@Override
		public void updateUi(Object o) {
			String json = o.toString();
			Logger.log(json);

			GetPlayUrlResponse getPlayUrlRespnse = new GetPlayUrlResponse();
			getPlayUrlRespnse.paseRespones(json);
			chapterList = getPlayUrlRespnse.list;

			Constants.totalLength = getPlayUrlRespnse.totalLength;

			String url = chapterList.get(playPosition).url;

			String[] uris = new String[chapterList.size()];
			int[] durations = new int[chapterList.size()];

			for (int i = 0; i < chapterList.size(); i++) {
				uris[i] = chapterList.get(i).url;
				durations[i] = Integer.parseInt(chapterList.get(i).duration) * 1000;
			}

			// playVideo(url);
			ctrbot.isDragging = false;
			playVideo(uris, durations);

			// new Reminder(chapterList);

		}

		@Override
		public void handleErrorData(String info) {
			// TODO Auto-generated method stub

		}
	};

	private static boolean isPrepare;

	public static boolean isError = false;

	public void playVideo(String[] uris, int[] durations) {
		// Uri[] uriss = new Uri[uris.length];
		// for (int i = 0; i < uris.length; i++) {
		// uriss[i] = Uri.parse(uris[i]);
		// }
		videoView.setVideoURI(uris, durations);

		// 展示一个进度
		final CustomProgressDialog dialog = new CustomProgressDialog(instance);
		if (!dialog.isShowing()) {
			dialog.show();
		}

		isPrepare = false;

		videoView.setOnPreparedListener(new OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer mp) {
				// 取消进度
				if (null != dialog) {
					dialog.cancel();
				}

				isPrepare = true;
				// isError=false;
				videoView.start();
			}
		});

		videoView.setOnErrorListener(new OnErrorListener() {

			@Override
			public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
				// 取消进度
				if (null != dialog) {
					dialog.cancel();
				}
				// videoView.stopPlayback();
				isError = true;

				// String url = "http://v.cctv.com/live_back/dianpian.mp4";
				// Message msg = new Message();
				// msg.obj = url;
				// handler.sendMessage(msg);

				// 播放下一段
				videoView.playNextVideo();

				return true;
			}
		});

	}

	public void playVideo(String url) {
		Log.e(CommonTools.TAG, "new url=" + url);
		Uri uri = Uri.parse(url);
		// videoView.setMediaController(new MediaController(this));
		// videoView.setVideoURI(uri);
		videoView.setVideoPath(url);

		// 展示一个进度
		final CustomProgressDialog dialog = new CustomProgressDialog(instance);
		if (!dialog.isShowing()) {
			dialog.show();
		}

		isPrepare = false;

		videoView.setOnPreparedListener(new OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer mp) {
				// 取消进度
				if (null != dialog) {
					dialog.cancel();
				}

				isPrepare = true;
				// isError=false;
				videoView.start();
			}
		});

		// videoView.requestFocus();

		videoView.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {

			}
		});

		videoView.setOnErrorListener(new OnErrorListener() {

			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				// 取消进度
				if (null != dialog) {
					dialog.cancel();
				}

				isError = true;

				String url = "http://v.cctv.com/live_back/dianpian.mp4";
				Message msg = new Message();
				msg.obj = url;
				handler.sendMessage(msg);
				return true;
			}
		});

	}

	public static Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			String url = (String) msg.obj;
			instance.playVideo(url);

		};

	};

	// 回看每5分钟播放
	public static final int DURATION = 300;

	public class Reminder {
		Timer timer;

		public Reminder(ArrayList<ChapterInfo> list) {
			timer = new Timer();
			timer.schedule(new RemindTask(list), 0, DURATION * 1000);
		}

		public class RemindTask extends TimerTask {

			ArrayList<ChapterInfo> mArrayList;

			public RemindTask(ArrayList<ChapterInfo> list) {
				mArrayList = list;
			}

			int i = 0;

			@Override
			public void run() {
				if (i < mArrayList.size()) {
					String url = (String) mArrayList.get(i).url;
					Message msg = new Message();
					msg.obj = url;
					handler.sendMessage(msg);
					i++;
				} else {
					timer.cancel();
				}

			}
		}
	}

	@Override
	protected void onDestroy() {
		videoView.stopPlayback();
		if (ctrbot != null) {
			ctrbot.dismiss();
		}
		if (hintPop != null) {
			hintPop.dismiss();
		}
		super.onDestroy();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == requestCode && resultCode == RESULT_OK) {
			String url = (String) chapterList.get(playPosition).url;
			Message msg = new Message();
			msg.obj = url;
			handler.sendMessage(msg);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
