package com.hlj.HomeActivity;

import java.util.ArrayList;
import java.util.HashMap;

import org.videolan.vlc.AudioService;
import org.videolan.vlc.AudioServiceController;
import org.videolan.vlc.LibVLC;
import org.videolan.vlc.LibVlcException;
import org.videolan.vlc.MediaLibrary;
import org.videolan.vlc.ThumbnailerManager;
import org.videolan.vlc.Util;
import org.videolan.vlc.VLCCallbackTask;
import org.videolan.vlc.gui.CompatErrorActivity;
import org.videolan.vlc.gui.PreferencesActivity;
import org.videolan.vlc.gui.SidebarAdapter;
import org.videolan.vlc.gui.video.VideoPlayerActivity;
import org.videolan.vlc.widget.AudioMiniPlayer;

import com.actionbarsherlock.app.ActionBar;
import com.hj.widget.CommonToast;
import com.hlj.adapter.AllPagesAdapter;
import com.hlj.adapter.DetailsKeyGridAdapter;
import com.hlj.adapter.DetailsKeyListAdapter;
import com.hlj.adapter.DetailsKeyTabAdapter;
import com.hlj.adapter.HotVideoAdapter;
import com.hlj.adapter.VideoInfoAdapter;
import com.hlj.net.AddFavRequest;
import com.hlj.net.BitmapWorkerTask;
import com.hlj.net.DeleteFavRequest;
import com.hlj.net.GetContentInfoRequest;
import com.hlj.net.GetContentInfoResponse;
import com.hlj.net.GetContentRecRequest;
import com.hlj.net.GetContentRecResponse;
import com.hlj.net.BitmapWorkerTask.ImageCallBack;
import com.hlj.net.GetCourseCategorySearchRequest;
import com.hlj.net.GetCourseGradeResponse.Item.PolymAddress;
import com.hlj.net.GetCourseGradeResponse.Item.PolymAddress.Video;
import com.hlj.net.GetSubjectGradeRequest;
import com.hlj.net.GetCourseGradeResponse;
import com.hlj.net.GetCourseInfoResponse;
import com.hlj.net.GetCourseRecRequest;
import com.hlj.net.GetCursorContentInfoRequest;
import com.hlj.net.HttpHomeLoadDataTask;
import com.hlj.utils.CommonTools;
import com.hlj.utils.ImageLoader;
import com.hlj.utils.Logger;
import com.hlj.utils.Reflect3DImage;
import com.hlj.utils.StringTools;
import com.hlj.utils.StringUtils;
import com.hlj.view.CommonTitleView;
import com.hlj.view.VideoInfo;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.live.video.constans.Constants;
import com.live.video.net.AddPlayInfoRequest;
import com.live.video.net.callback.IUpdateData;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * 教育视频详情
 * 
 * @author huangyuchao
 * 
 */
public class StudyVideoDetailsActivity extends BaseActivity implements
		OnClickListener {

	CommonTitleView commonTitleView;

	CommonToast toast;

	RadioGroup video_details_resources;

	String series;

	public final static String TAG = "VLC/MainActivity";

	protected static final String ACTION_SHOW_PROGRESSBAR = "org.videolan.vlc.gui.ShowProgressBar";
	protected static final String ACTION_HIDE_PROGRESSBAR = "org.videolan.vlc.gui.HideProgressBar";
	protected static final String ACTION_SHOW_TEXTINFO = "org.videolan.vlc.gui.ShowTextInfo";

	private static final String PREF_SHOW_INFO = "show_info";
	private static final String PREF_FIRST_RUN = "first_run";

	private static final int ACTIVITY_RESULT_PREFERENCES = 1;

	private ActionBar mActionBar;
	private SlidingMenu mMenu;
	private SidebarAdapter mSidebarAdapter;
	private AudioMiniPlayer mAudioPlayer;
	private AudioServiceController mAudioController;
	private ThumbnailerManager mThumbnailerManager;

	private View mInfoLayout;
	private ProgressBar mInfoProgress;
	private TextView mInfoText;
	private String mCurrentFragment;

	private SharedPreferences mSettings;

	private int mVersionNumber = -1;
	private boolean mFirstRun = false;
	private boolean mScanNeeded = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (!Util.hasCompatibleCPU()) {
			Logger.log(Util.getErrorMsg());
			// Log.e(TAG, Util.getErrorMsg());
			Intent i = new Intent(this, CompatErrorActivity.class);
			startActivity(i);
			finish();
			super.onCreate(savedInstanceState);
			return;
		}

		/* Get the current version from package */
		PackageInfo pinfo = null;
		try {
			pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			Logger.log("package info not found.");
			// Log.e(TAG, "package info not found.");
		}
		if (pinfo != null)
			mVersionNumber = pinfo.versionCode;

		/* Get settings */
		mSettings = PreferenceManager.getDefaultSharedPreferences(this);

		/* Check if it's the first run */
		mFirstRun = mSettings.getInt(PREF_FIRST_RUN, -1) != mVersionNumber;
		if (mFirstRun) {
			Editor editor = mSettings.edit();
			editor.putInt(PREF_FIRST_RUN, mVersionNumber);
			editor.commit();
		}

		try {
			// Start LibVLC
			LibVLC.getInstance();
		} catch (LibVlcException e) {
			e.printStackTrace();
			Intent i = new Intent(this, CompatErrorActivity.class);
			i.putExtra("runtimeError", true);
			i.putExtra("message",
					"LibVLC failed to initialize (LibVlcException)");
			startActivity(i);
			finish();
			super.onCreate(savedInstanceState);
			return;
		}

		super.onCreate(savedInstanceState);

		setContentView(R.layout.study_video_details);

		commonTitleView = (CommonTitleView) findViewById(R.id.commonTitle);

		toast = new CommonToast(this);

		mInfoLayout = findViewById(R.id.info_layout);
		mInfoProgress = (ProgressBar) findViewById(R.id.info_progress);
		mInfoText = (TextView) findViewById(R.id.info_text);

		/* Set up the mini audio player */
		mAudioPlayer = new AudioMiniPlayer();
		mAudioController = AudioServiceController.getInstance();
		mAudioPlayer.setAudioPlayerControl(mAudioController);
		mAudioPlayer.update();

		initView();
		initData();

		/* Show info/alpha/beta Warning */
		if (mSettings.getInt(PREF_SHOW_INFO, -1) != mVersionNumber) {
			// showInfoDialog();
		} else if (mFirstRun) {
			/*
			 * The sliding menu is automatically opened when the user closes the
			 * info dialog. If (for any reason) the dialog is not shown, open
			 * the menu after a short delay.
			 */
			final Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					mMenu.showMenu();
				}
			}, 500);
		}

		/* Prepare the progressBar */
		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION_SHOW_PROGRESSBAR);
		filter.addAction(ACTION_HIDE_PROGRESSBAR);
		filter.addAction(ACTION_SHOW_TEXTINFO);
		registerReceiver(messageReceiver, filter);

		/* Reload the latest preferences */
		reloadPreferences();

		/* Load the thumbnailer */
		mThumbnailerManager = new ThumbnailerManager(this, getWindowManager()
				.getDefaultDisplay());
	}

	private final BroadcastReceiver messageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			if (action.equalsIgnoreCase(ACTION_SHOW_PROGRESSBAR)) {
				// setSupportProgressBarIndeterminateVisibility(true);
				getWindow().addFlags(
						WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			} else if (action.equalsIgnoreCase(ACTION_HIDE_PROGRESSBAR)) {
				// setSupportProgressBarIndeterminateVisibility(false);
				getWindow().clearFlags(
						WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			} else if (action.equalsIgnoreCase(ACTION_SHOW_TEXTINFO)) {
				String info = intent.getStringExtra("info");
				int max = intent.getIntExtra("max", 0);
				int progress = intent.getIntExtra("progress", 100);
				mInfoText.setText(info);
				mInfoProgress.setMax(max);
				mInfoProgress.setProgress(progress);
				mInfoLayout.setVisibility(info != null ? View.VISIBLE
						: View.GONE);
			}
		}
	};

	private void reloadPreferences() {
		SharedPreferences sharedPrefs = getSharedPreferences("MainActivity",
				MODE_PRIVATE);
		mCurrentFragment = sharedPrefs.getString("fragment", "video");
	}

	@Override
	protected void onResume() {
		CommonTitleView.instance = commonTitleView;

		details_key_list.setSelection(0);

		super.onResume();

		mAudioController.addAudioPlayer(mAudioPlayer);
		AudioServiceController.getInstance().bindAudioService(this);

		/*
		 * FIXME: this is used to avoid having MainActivity twice in the
		 * backstack
		 */
		if (getIntent().hasExtra(AudioService.START_FROM_NOTIFICATION))
			getIntent().removeExtra(AudioService.START_FROM_NOTIFICATION);

		/* Load media items from database and storage */
		if (mScanNeeded)
			MediaLibrary.getInstance(this).loadMediaItems(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		/* Check for an ongoing scan that needs to be resumed during onResume */
		mScanNeeded = MediaLibrary.getInstance(this).isWorking();
		/* Stop scanning for files */
		MediaLibrary.getInstance(this).stop();
		/* Stop the thumbnailer */
		mThumbnailerManager.stop();
		/* Save the tab status in pref */
		SharedPreferences.Editor editor = getSharedPreferences("MainActivity",
				MODE_PRIVATE).edit();
		editor.putString("fragment", mCurrentFragment);
		editor.commit();

		mAudioController.removeAudioPlayer(mAudioPlayer);
		AudioServiceController.getInstance().unbindAudioService(this);
	}

	ImageView details_poster;

	TextView details_name, details_year, details_rate;
	TextView details_director, details_update, details_actors,
			details_playTimes, details_person;
	TextView details_video_introduce;

	Button details_replay, details_play, details_choose, details_colection;

	int catId;

	String contentid;

	ImageLoader loader;

	LayoutInflater inflater;

	GridView recommend_grid;

	LinearLayout details_key_tv, details_key_arts;

	HotVideoAdapter adapter;

	ArrayList<VideoInfo> recList;

	String url;

	String title, grade, versions, subject, imgUrl;

	private void initView() {
		catId = this.getIntent().getIntExtra("catId", 0);
		contentid = this.getIntent().getStringExtra("contentid");
		grade = this.getIntent().getStringExtra("grade");
		versions = this.getIntent().getStringExtra("versions");
		subject = this.getIntent().getStringExtra("subject");
		title = this.getIntent().getStringExtra("title");
		url = this.getIntent().getStringExtra("url");

		imgUrl = this.getIntent().getStringExtra("imgUrl");
		Logger.log("imgUrl:" + imgUrl);

		details_poster = (ImageView) findViewById(R.id.details_poster);

		if (!StringTools.isNullOrEmpty(imgUrl)) {
			new BitmapWorkerTask(StudyVideoDetailsActivity.this,
					details_poster, false, false, imageCallBack)
					.execute(imgUrl);
		} else {
			details_poster.setImageBitmap(Reflect3DImage.skewImage(
					BitmapFactory.decodeResource(this.getResources(),
							R.drawable.hao260x366), 260, 366, 50));
		}

		details_name = (TextView) findViewById(R.id.details_name);
		details_year = (TextView) findViewById(R.id.details_year);
		details_rate = (TextView) findViewById(R.id.details_rate);

		details_director = (TextView) findViewById(R.id.details_director);
		details_update = (TextView) findViewById(R.id.details_update);
		details_actors = (TextView) findViewById(R.id.details_actors);
		details_playTimes = (TextView) findViewById(R.id.details_playTimes);
		details_person = (TextView) findViewById(R.id.details_person);

		details_video_introduce = (TextView) findViewById(R.id.details_video_introduce);

		details_replay = (Button) findViewById(R.id.details_replay);
		details_replay.setOnClickListener(this);
		details_play = (Button) findViewById(R.id.details_play);
		details_play.setOnClickListener(this);

		details_play.setFocusable(true);
		details_play.requestFocus();

		details_choose = (Button) findViewById(R.id.details_choose);
		details_choose.setOnClickListener(this);
		details_colection = (Button) findViewById(R.id.details_colection);
		details_colection.setVisibility(View.GONE);
		details_colection.setOnClickListener(this);

		video_details_resources = (RadioGroup) findViewById(R.id.video_details_resources);

		// loader = new ImageLoader(this, 0);

		inflater = LayoutInflater.from(this);

		recommend_grid = (GridView) findViewById(R.id.details_recommend);

		recommend_grid.setSelector(new ColorDrawable(0));

		recList = new ArrayList<VideoInfo>();
		adapter = new HotVideoAdapter(StudyVideoDetailsActivity.this, recList);
		recommend_grid.setAdapter(adapter);

		recommend_grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent it = new Intent(StudyVideoDetailsActivity.this,
						StudyVideoDetailsActivity.class);
				it.putExtra("catId", recList.get(position).catId);
				it.putExtra("contentid", recList.get(position).contentId);
				it.putExtra("grade", recList.get(position).grade);
				it.putExtra("subject", recList.get(position).subject);
				it.putExtra("versions", recList.get(position).versions);
				it.putExtra("title", recList.get(position).title);
				it.putExtra("imgUrl", recList.get(position).imageUrl);
				startActivity(it);
				finish();
			}
		});

		details_key_tv = (LinearLayout) findViewById(R.id.details_key_tv);
		details_key_arts = (LinearLayout) findViewById(R.id.details_key_arts);

		createSourceLayout(videoInfoList);

		video_details_resources
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						if (checkedId != -1) {
							// video_details_resources.check(checkedId);
							RadioButton rb = (RadioButton) video_details_resources
									.findViewById(checkedId);
							int position = video_details_resources
									.indexOfChild(rb);
							// 切换list
							videoList = videoInfoList.get(position).videoList;
						}

					}
				});

		details_key_list = (ListView) findViewById(R.id.details_key_list);
		details_key_grid = (GridView) findViewById(R.id.details_key_grid);

		getSubjectGrade();

		// getCourseContentInfo();
	}

	private void createSourceLayout(ArrayList<PolymAddress> list) {
		video_details_resources.removeAllViews();
		int size = list.size();
		if (size > 0) {
			for (int j = 0; j < size; j++) {
				RadioButton radionButton = (RadioButton) this.inflater.inflate(
						R.layout.vediodetail_rb, null);
				radionButton.setCompoundDrawablesWithIntrinsicBounds(0, 0,
						StringUtils.sourceStringToResourceID(videoInfoList
								.get(j).videoSource), 0);
				radionButton
						.setBackgroundResource(R.drawable.detailsource_bg_s);
				// radionButton.setFocusable(true);

				video_details_resources.addView(radionButton);
			}
			video_details_resources.check(video_details_resources.getChildAt(0)
					.getId());
		}

	}

	ArrayList<String> addresslist = new ArrayList<String>();

	ArrayList<PolymAddress> videoInfoList = new ArrayList<PolymAddress>();

	ArrayList<Video> videoList = new ArrayList<Video>();

	String videoaddress, imgurl;

	private void initData() {

	}

	private void getSubjectGrade() {
		GetCursorContentInfoRequest request = new GetCursorContentInfoRequest(
				this);
		request.grade = grade;
		request.versions = versions;
		request.catId = catId;
		request.subject = subject;
		request.contentId = contentid;
		new HttpHomeLoadDataTask(this, courseGradecallBack, true, url, false)
				.execute(request);
	}

	PolymAddress address;

	String versionsname, subjectsname, intro;

	int count = 0;

	GetCourseGradeResponse response = new GetCourseGradeResponse();

	IUpdateData courseGradecallBack = new IUpdateData() {

		@Override
		public void updateUi(Object o) {
			Logger.log(o.toString());

			response.paseRespones(o.toString());

			videoInfoList = response.list;

			if (!StringTools.isNullOrEmpty(response.info.series)) {
				count = Integer.parseInt(response.info.series);
			}

			if (!StringTools.isNullOrEmpty(response.info.images)) {
				new BitmapWorkerTask(StudyVideoDetailsActivity.this,
						details_poster, false, false, imageCallBack)
						.execute(response.info.images);
			}

			if (videoInfoList != null && videoInfoList.size() > 0) {
				address = videoInfoList.get(0);
				videoList = address.videoList;
			}

			if (!StringTools.isNullOrEmpty(title)) {
				details_name.setText(title);
			} else {
				title = response.info.title;
				details_name.setText(title);
			}

			details_director.setText("年级："
					+ StringTools.getStrGrade(response.info.grade));
			details_update.setText("学期："
					+ StringTools.getStrTerm(response.info.grade));

			details_actors.setText("学科：" + response.info.subjectsname);
			details_playTimes.setText("版本：" + address.videoSource);
			details_person.setText("主讲人：" + response.info.director);
			details_video_introduce.setText(response.info.intro);

			if (count > 0) {
				details_choose.setVisibility(View.VISIBLE);
				getGridList(count);
			} else {
				details_choose.setVisibility(View.GONE);
			}

			// getCourseCategorySearch();

			getCourseRecInfo();
		}

		@Override
		public void handleErrorData(String info) {
			// TODO Auto-generated method stub

		}

	};

	// private void getCourseCategorySearch() {
	// GetCourseCategorySearchRequest request = new
	// GetCourseCategorySearchRequest();
	// request.versions = versions;
	// request.catId = catId;
	// request.subject = subject;
	// new HttpHomeLoadDataTask(this, courseCategorySearchcallBack, false, "",
	// false).execute(request);
	// }
	//
	// IUpdateData courseCategorySearchcallBack = new IUpdateData() {
	//
	// @Override
	// public void updateUi(Object o) {
	// Logger.log(o.toString());
	// GetContentRecResponse response = new GetContentRecResponse();
	// response.paseRespones(o.toString());
	//
	// recList = response.list;
	//
	// ArrayList<VideoInfo> newRandomList = new ArrayList<VideoInfo>();
	// ArrayList<VideoInfo> shortRandomList = new ArrayList<VideoInfo>();
	// int[] array = new int[12];
	// if (recList.size() > 7 && recList.size() < 13) {
	// for (int i = 0; i < recList.size(); i++) {
	// array[i] = i;
	// }
	// int[] newRandomArray = CommonTools.getRandomList(array);
	// for (int j = 0; j < newRandomArray.length; j++) {
	// newRandomList.add(recList.get(newRandomArray[j]));
	// }
	// for (int i = 0; i < 7; i++) {
	// shortRandomList.add(newRandomList.get(i));
	// }
	// }
	//
	// adapter = new HotVideoAdapter(StudyVideoDetailsActivity.this,
	// shortRandomList);
	// recommend_grid.setAdapter(adapter);
	// adapter.notifyDataSetChanged();
	// }
	//
	// @Override
	// public void handleErrorData(String info) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// };

	private void getCourseContentInfo() {
		GetCursorContentInfoRequest request = new GetCursorContentInfoRequest(
				this);
		request.contentId = contentid;
		request.catId = catId;
		request.grade = grade;
		request.versions = versions;
		new HttpHomeLoadDataTask(this, getCourseContentCallBack, true, url,
				false).execute(request);
	}

	IUpdateData getCourseContentCallBack = new IUpdateData() {

		@Override
		public void updateUi(Object o) {
			Logger.log(o.toString());
			GetCourseInfoResponse response = new GetCourseInfoResponse();
			response.paseRespones(o.toString());

			// response.paseRespones(o.toString());
			// imgurl = response.images;
			// loader.displayImage(imgurl, details_poster);

			//
			title = response.title;
			details_name.setText(title);
			// details_year.setText(response.info.releaseDate);
			// details_rate.setText(response.info.timeLength);

			details_director.setText("年级："
					+ StringTools.getStrGrade(response.grade));
			details_update.setText("学期："
					+ StringTools.getStrTerm(response.grade));
			details_actors.setText("学科：" + response.subject);
			details_playTimes.setText("版本：" + response.versions);
			details_video_introduce.setText(response.intro);

			addresslist.add(response.address.videoAddress);

			getCourseRecInfo();

		}

		@Override
		public void handleErrorData(String info) {
			// TODO Auto-generated method stub

		}

	};

	ImageCallBack imageCallBack = new ImageCallBack() {

		@Override
		public void post(Bitmap bitmap) {
			details_poster.setImageBitmap(Reflect3DImage.skewImage(bitmap, 260,
					366, 50));
		}
	};

	private ArrayList<String> getGridList(int totalSum) {
		double num = Math.ceil(totalSum / 10.0);
		double currentSum = 0;
		for (int i = 0; i < num; i++) {
			String s = new String((1 + i * 10) + "-" + (i + 1) * 10);
			if (totalSum < (i + 1) * 10) {
				s = new String((1 + i * 10) + "-" + totalSum);
			}

			detailsKeyList.add(s);
		}
		// System.out.println("size:" + detailsKeyList.size());
		return detailsKeyList;
	}

	/**
	 * 获取学习的推荐内容
	 */
	private void getCourseRecInfo() {
		GetCourseRecRequest request = new GetCourseRecRequest(this);
		// request.catId = response.catId;

		// if (!StringTools.isNullOrEmpty(grade)) {
		// request.grade = grade;
		// } else {
		// request.grade = response.info.grade;
		// }

		// if (!StringTools.isNullOrEmpty(versions)) {
		// request.versions = versions;
		// } else {
		// // request.versions = response.versions;
		// }

		if (!StringTools.isNullOrEmpty(subject)) {
			request.subject = subject;
		} else {
			request.subject = response.info.subject;
		}

		request.type = response.info.type;

		new HttpHomeLoadDataTask(this, getRecCallBack, false, "", false)
				.execute(request);
	}

	IUpdateData getRecCallBack = new IUpdateData() {

		public void updateUi(Object o) {
			Logger.log(o.toString());
			GetContentRecResponse recResponse = new GetContentRecResponse();
			recResponse.paseRespones(o.toString());

			recList = recResponse.list;
			adapter = new HotVideoAdapter(StudyVideoDetailsActivity.this,
					recList);
			recommend_grid.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		};

		@Override
		public void handleErrorData(String info) {
			// TODO Auto-generated method stub

		}

	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.details_play:

			if (null != videoList && videoList.size() > 0) {
				videoaddress = videoList.get(0).videoAddress;
				contentid = response.info.contentId;
				title = response.info.title;
				Logger.log("contentid:" + contentid);

				// 存储title
				CommonTools.saveTitle(title);

				Constants.isDefaultPlay = videoList.get(0).isDefaultPlay;

				if (Constants.isDefaultPlay == 0
						|| Constants.isDefaultPlay == 1||Constants.isDefaultPlay==4) {
					androidPlay("");
				} else if(Constants.isDefaultPlay==2){
					vlcPlay();
				}
			} else {
				toast.setIcon(R.drawable.toast_err);
				toast.setText("暂时无此视频");
				toast.show();
			}

			break;
		case R.id.details_colection:

			Intent it = new Intent(StudyVideoDetailsActivity.this,
					WebViewActivity.class);
			startActivity(it);

			break;
		case R.id.details_choose:
			// 选课
			if (details_key_tv.getVisibility() == View.VISIBLE
					|| details_key_arts.getVisibility() == View.VISIBLE) {
				recommend_grid.setVisibility(View.VISIBLE);
				details_key_tv.setVisibility(View.GONE);
				details_key_arts.setVisibility(View.GONE);
			} else {
				recommend_grid.setVisibility(View.GONE);
				details_key_tv.setVisibility(View.GONE);
				details_key_arts.setVisibility(View.VISIBLE);
				// createTvLayout();
				createLessionLayout();
			}

			break;
		}
	}

	ViewPager viewPager;
	Gallery gallery;

	DetailsKeyTabAdapter dktAdapter;
	AllPagesAdapter apAdapter;

	ArrayList<String> detailsKeyList = new ArrayList<String>();

	ListView details_key_list;
	GridView details_key_grid;

	DetailsKeyListAdapter keyListAdapter;

	// ArrayList<HashMap<Integer, ArrayList>> allList = new
	// ArrayList<HashMap<Integer, ArrayList>>();

	public ArrayList<VideoInfo> everyTenVideoList(ArrayList<Video> list, int i) {
		ArrayList<VideoInfo> newList = new ArrayList<VideoInfo>();
		// double num = Math.ceil(list.size() / 10.0);
		int sum = (i + 1) * 10;
		if (sum > list.size()) {
			sum = list.size();
		}
		for (int j = i * 10; j < sum; j++) {
			VideoInfo videoInfo = new VideoInfo();
			videoInfo.address = list.get(j).videoAddress;
			videoInfo.title = list.get(j).title;
			videoInfo.lessonid = list.get(j).lesson;
			videoInfo.isDefaultPlay= list.get(j).isDefaultPlay;
			newList.add(videoInfo);
		}
		return newList;
	}

	ArrayList<VideoInfo> al = new ArrayList<VideoInfo>();
	VideoInfo videoInfo;

	/**
	 * 创建课程视图
	 */
	private void createLessionLayout() {
		Logger.log("detailsKeyList size:" + detailsKeyList.size());

		details_key_list.setSelector(new ColorDrawable());
		details_key_grid.setSelector(new ColorDrawable());

		details_key_grid.setSelection(0);

		details_key_grid.setAdapter(new DetailsKeyGridAdapter(this,
				detailsKeyList));

		al = everyTenVideoList(videoList, 0);

		Logger.log("new videoList size:" + al.size());

		keyListAdapter = new DetailsKeyListAdapter(this, al);

		details_key_list.setAdapter(keyListAdapter);
		details_key_grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Logger.log("position:" + position);
				// 切换listview的Adapter
				al = everyTenVideoList(videoList, position);
				keyListAdapter.setDataChanged(al);
				keyListAdapter.notifyDataSetChanged();
			}
		});

		details_key_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				videoInfo = al.get(position);
				// 进入到播放
				videoaddress = videoInfo.address;
				// title = videoInfo.title + "(第" + videoInfo.lessonid + "课)";
				title = videoInfo.title;
				contentid = videoInfo.contentId;
				Logger.log("contentid:" + contentid);
				Logger.log("title:" + title);

				// 存储title
				CommonTools.saveTitle(title);
				
				Constants.isDefaultPlay=videoInfo.isDefaultPlay;

				if (Constants.isDefaultPlay == 0
						|| Constants.isDefaultPlay == 1||Constants.isDefaultPlay==4) {
					androidPlay("");
				} else if(Constants.isDefaultPlay==2){
					vlcPlay();
				}
			}
		});

	}

	private void androidPlay(String name) {
		Intent it = new Intent(StudyVideoDetailsActivity.this,
				VideoPlayActivity.class);
		it.putExtra("url", videoaddress);
		it.putExtra("title", title);
		it.putExtra("grade", grade);
		it.putExtra("contentId", contentid);
		it.putExtra("subject", subject);
		it.putExtra("versions", versions);
		startActivity(it);
	}

	VLCCallbackTask task;

	/**
	 * vlc播放
	 */
	public void vlcPlay() {
		task = new VLCCallbackTask(this) {

			@Override
			public void run() {
				AudioServiceController c = AudioServiceController.getInstance();
				Logger.log("videoaddress:" + videoaddress);
				c.append(videoaddress);
			}
		};
		task.execute();
	}

	private void addFav() {
		AddFavRequest request = new AddFavRequest(this);
		request.title = title;
		request.url = imgurl;
		request.sourceID = contentid;
		new HttpHomeLoadDataTask(this, addFavcallBack, true, "", false)
				.execute(request);
	}

	IUpdateData addFavcallBack = new IUpdateData() {

		@Override
		public void updateUi(Object o) {
			Logger.log(o.toString());

			// details_colection.setText("取消");
			// details_colection.setTag("cancel");

			toast.setIcon(R.drawable.toast_smile);
			toast.setText("新增收藏成功！");
			toast.show();
		}

		@Override
		public void handleErrorData(String info) {
			toast.setIcon(R.drawable.toast_err);
			toast.setText("新增收藏失败！");
			toast.show();
		}

	};

	public boolean onKeyDown(int keyCode, android.view.KeyEvent arg1) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if (details_key_tv.getVisibility() == View.VISIBLE
					|| details_key_arts.getVisibility() == View.VISIBLE) {
				recommend_grid.setVisibility(View.VISIBLE);
				details_key_tv.setVisibility(View.GONE);
				details_key_arts.setVisibility(View.GONE);
				return true;
			}

		}
		return super.onKeyDown(keyCode, arg1);
	};

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		// AudioServiceController.getInstance().unbindAudioService(this);

		if (task != null && !task.isCancelled()) {
			task.cancel(true);
		}

		try {
			unregisterReceiver(messageReceiver);
		} catch (IllegalArgumentException e) {
		}
		if (mThumbnailerManager != null)
			mThumbnailerManager.clearJobs();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		/* Reload the latest preferences */
		reloadPreferences();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == ACTIVITY_RESULT_PREFERENCES) {
			if (resultCode == PreferencesActivity.RESULT_RESCAN)
				MediaLibrary.getInstance(this).loadMediaItems(this, true);
		}
	}

}
