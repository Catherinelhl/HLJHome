package org.videolan.vlc.gui;

import java.util.ArrayList;

import org.videolan.vlc.AudioServiceController;
import org.videolan.vlc.VLCCallbackTask;

import com.hj.widget.CommonToast;
import com.hlj.HomeActivity.R;
import com.hlj.HomeActivity.RecomdVideoDetailsActivity;
import com.hlj.HomeActivity.VideoPlayActivity;
import com.hlj.adapter.AllPagesAdapter;
import com.hlj.adapter.DetailsKeyTabAdapter;
import com.hlj.adapter.HotVideoAdapter;
import com.hlj.net.AddFavRequest;
import com.hlj.net.BitmapWorkerTask;
import com.hlj.net.DeleteFavRequest;
import com.hlj.net.GetContentInfoRequest;
import com.hlj.net.GetContentInfoResponse;
import com.hlj.net.GetContentRecRequest;
import com.hlj.net.GetContentRecResponse;
import com.hlj.net.HttpHomeLoadDataTask;
import com.hlj.net.BitmapWorkerTask.ImageCallBack;
import com.hlj.net.GetContentInfoResponse.Item.PolymAddress;
import com.hlj.net.GetContentInfoResponse.Item.PolymAddress.Video;
import com.hlj.utils.ImageLoader;
import com.hlj.utils.Logger;
import com.hlj.utils.Reflect3DImage;
import com.hlj.utils.StringTools;
import com.hlj.utils.StringUtils;
import com.hlj.view.CommonTitleView;
import com.hlj.view.VideoInfo;
import com.live.video.net.callback.IUpdateData;

import com.actionbarsherlock.app.SherlockListFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils.TruncateAt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * videoinfo
 * 
 * @author hyc
 * 
 */
public class VideoInfoFragment extends Fragment implements OnClickListener {

	CommonTitleView commonTitleView;

	CommonToast toast;

	String contentid, from, url;

	int favid;

	View v;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.video_details, container, false);

		contentid = getActivity().getIntent().getStringExtra("contentid");
		from = getActivity().getIntent().getStringExtra("from");
		favid = getActivity().getIntent().getIntExtra("favid", 0);
		url = getActivity().getIntent().getStringExtra("url");

		Logger.log("contentid:" + contentid);
		Logger.log("url:" + url);
		Logger.log("from:" + from);
		Logger.log("favid:" + favid);

		initView(v);

		initData();
		return v;
	}

	ImageView details_poster;

	TextView details_name, details_year, details_rate;
	TextView details_director, details_update, details_actors,
			details_playTimes;
	TextView details_video_introduce;

	Button details_replay, details_play, details_choose, details_colection;

	ImageLoader loader;

	LayoutInflater inflater;

	GridView recommend_grid;

	LinearLayout details_key_tv, details_key_arts;

	HotVideoAdapter adapter;

	ArrayList<VideoInfo> recList;

	RadioGroup video_details_resources;

	String videoSourceName;

	ArrayList<PolymAddress> list = new ArrayList<PolymAddress>();

	ArrayList<String> addresslist = new ArrayList<String>();

	ArrayList<Video> videoList = new ArrayList<Video>();

	String videoId, videoaddress, title, imgurl;

	private void initView(View v) {

		commonTitleView = (CommonTitleView) v.findViewById(R.id.commonTitle);
		toast = new CommonToast(getActivity());

		details_poster = (ImageView) v.findViewById(R.id.details_poster);
		details_poster.setImageBitmap(Reflect3DImage.skewImage(BitmapFactory
				.decodeResource(this.getResources(), R.drawable.hao260x366),
				260, 366, 50));

		details_name = (TextView) v.findViewById(R.id.details_name);
		details_year = (TextView) v.findViewById(R.id.details_year);
		details_rate = (TextView) v.findViewById(R.id.details_rate);

		details_director = (TextView) v.findViewById(R.id.details_director);
		details_update = (TextView) v.findViewById(R.id.details_update);
		details_actors = (TextView) v.findViewById(R.id.details_actors);
		details_playTimes = (TextView) v.findViewById(R.id.details_playTimes);
		details_video_introduce = (TextView) v
				.findViewById(R.id.details_video_introduce);

		details_replay = (Button) v.findViewById(R.id.details_replay);
		details_replay.setOnClickListener(this);
		details_play = (Button) v.findViewById(R.id.details_play);
		details_play.setOnClickListener(this);

		details_play.setFocusable(true);
		details_play.requestFocus();

		details_choose = (Button) v.findViewById(R.id.details_choose);
		details_choose.setOnClickListener(this);
		details_colection = (Button) v.findViewById(R.id.details_colection);
		details_colection.setOnClickListener(this);

		video_details_resources = (RadioGroup) v
				.findViewById(R.id.video_details_resources);

		loader = new ImageLoader(this.getActivity(), 0);

		inflater = LayoutInflater.from(this.getActivity());

		recommend_grid = (GridView) v.findViewById(R.id.details_recommend);

		recommend_grid.setSelector(new ColorDrawable(0));

		recList = new ArrayList<VideoInfo>();
		adapter = new HotVideoAdapter(this.getActivity(), recList);
		recommend_grid.setAdapter(adapter);

		recommend_grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent it = new Intent(getActivity(),
						RecomdVideoDetailsActivity.class);
				it.putExtra("contentid", recList.get(position).contentId);
				startActivity(it);
				getActivity().finish();
			}
		});

		details_key_tv = (LinearLayout) v.findViewById(R.id.details_key_tv);
		details_key_arts = (LinearLayout) v.findViewById(R.id.details_key_arts);

		createSourceLayout(list);

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
							videoList = list.get(position).videoList;

							videoSourceName = list.get(position).videoSource;
						}

					}
				});

	}

	private void createSourceLayout(ArrayList<PolymAddress> list) {
		video_details_resources.removeAllViews();
		int size = list.size();
		if (size > 0) {
			for (int j = 0; j < size; j++) {
				RadioButton radionButton = (RadioButton) this.inflater.inflate(
						R.layout.vediodetail_rb, null);
				radionButton
						.setCompoundDrawablesWithIntrinsicBounds(0, 0,
								StringUtils.sourceStringToResourceID(list
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

	private void initData() {
		getContentInfo();
	}

	public void getContentInfo() {
		GetContentInfoRequest request = new GetContentInfoRequest(getActivity());
		request.contentId = contentid;
		request.teleplayPage = 1;
		request.teleplayPageSize = 100;
		new HttpHomeLoadDataTask(getActivity(), getContentCallBack, true, url,
				false).execute(request);
	}

	GetContentInfoResponse response = new GetContentInfoResponse();

	ViewPager viewPager;
	Gallery gallery;

	DetailsKeyTabAdapter dktAdapter;
	AllPagesAdapter apAdapter;

	ArrayList<String> detailsKeyList = new ArrayList<String>();

	String series;

	IUpdateData getContentCallBack = new IUpdateData() {

		@Override
		public void updateUi(Object o) {
			Logger.log(o.toString());

			response.paseRespones(o.toString());
			imgurl = response.info.images;
			// loader.displayImage(imgurl, details_poster);

			// Bitmap bitmap = ImageUtils.convertViewToBitmap(details_poster);

			new BitmapWorkerTask(getActivity(), details_poster, true, false,
					imageCallBack).execute(imgurl);

			title = response.info.title;
			details_name.setText(title);
			details_year.setText(response.info.releaseDate);

			if (StringTools.isNullOrEmpty(response.info.timeLength)
					|| "0".endsWith(response.info.timeLength)) {
				details_rate.setText("");
			} else {
				details_rate.setText(response.info.timeLength + "分钟");
			}

			details_director.setText("导演：" + response.info.director);
			details_update.setText("类别：" + response.info.fileType);
			details_actors.setText("演员：" + response.info.actor);
			details_playTimes.setText("地区：" + response.info.zone);
			details_video_introduce.setText(response.info.intro);

			list = response.list;
			if (null != response.list && response.list.size() > 0) {
				videoList = response.list.get(0).videoList;
			}

			// videoId = videoList.get(0).videoId;

			series = response.info.series;

			if ("1".equals(series)) {
				details_choose.setVisibility(View.GONE);
			} else {
				details_choose.setVisibility(View.VISIBLE);
			}

			int totalSum = Integer.valueOf(series);
			if (totalSum > 1) {
				// for (int i = 0; i < totalSum; i++) {
				// // 有总集数返回几个块
				// detailsKeyList.add((i + 1) + "");
				// }
				getTabList(totalSum);
			}

			createSourceLayout(list);
			if (null != list && list.size() > 0) {
				videoSourceName = list.get(0).videoSource;
			}

			getRecInfo();

		}

		@Override
		public void handleErrorData(String info) {
			// TODO Auto-generated method stub

		}

	};

	private void getRecInfo() {
		GetContentRecRequest request = new GetContentRecRequest(getActivity());
		request.title = title;
		request.type = response.info.type;
		request.actor = response.info.actor;
		request.catId = response.info.catId;
		request.contentId = response.info.contentId;
		new HttpHomeLoadDataTask(getActivity(), getRecCallBack, false, "",
				false).execute(request);
	}

	IUpdateData getRecCallBack = new IUpdateData() {

		public void updateUi(Object o) {
			Logger.log(o.toString());
			GetContentRecResponse response = new GetContentRecResponse();
			response.paseRespones(o.toString());

			recList = response.list;
			adapter = new HotVideoAdapter(getActivity(), recList);
			recommend_grid.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		};

		@Override
		public void handleErrorData(String info) {
			// TODO Auto-generated method stub

		}

	};

	private ArrayList<String> getTabList(int totalSum) {
		double num = Math.ceil(totalSum / 5.0);
		double currentSum = 0;
		for (int i = 0; i < num; i++) {
			String s = new String((1 + i * 5) + "-" + (i + 1) * 5);
			if (totalSum < (i + 1) * 5) {
				s = new String((1 + i * 5) + "-" + totalSum);
			}
			detailsKeyList.add(s);
		}
		return detailsKeyList;
	}

	ImageCallBack imageCallBack = new ImageCallBack() {

		@Override
		public void post(Bitmap bitmap) {
			details_poster.setImageBitmap(Reflect3DImage.skewImage(bitmap, 260,
					366, 50));
		}
	};

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onResume() {
		CommonTitleView.instance = commonTitleView;
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.details_play:

			if (null != videoList && videoList.size() > 0) {
				videoaddress = videoList.get(0).videoAddress;
				videoId = videoList.get(0).videoId;

				//Intent it = new Intent(getActivity(), VideoPlayActivity.class);

				//videoaddress = "http://vod.cntv.lxdns.com/flash/live_back/nettv_cctv1/cctv1-2013-12-10-00-001.mp4";

				//it.putExtra("url", videoaddress);
				//it.putExtra("title", title);
				//it.putExtra("contentId", contentid);
				//it.putExtra("videoId", videoId);
				//it.putExtra("videoSourceName", videoSourceName);
				//startActivity(it);

				MainActivity.vlcplayVideo(videoaddress);
				
				//vlcPlay();

			} else {
				toast.setIcon(R.drawable.toast_err);
				toast.setText("暂时无此视频");
				toast.show();
			}
			break;
		case R.id.details_colection:

			String s = ((Button) v).getText().toString();

			if ("收藏".equals(s)) {
				addFav();
			}

			else if ("取消".equals(s)) {
				delFav();
			}

			break;
		case R.id.details_choose:
			// 选集
			if (details_key_tv.getVisibility() == View.VISIBLE
					|| details_key_arts.getVisibility() == View.VISIBLE) {
				recommend_grid.setVisibility(View.VISIBLE);
				details_key_tv.setVisibility(View.GONE);
				details_key_arts.setVisibility(View.GONE);
			} else {
				recommend_grid.setVisibility(View.GONE);
				details_key_tv.setVisibility(View.VISIBLE);
				details_key_arts.setVisibility(View.GONE);
				createTvLayout();
			}

			break;
		}

	}

	private void addFav() {
		AddFavRequest request = new AddFavRequest(getActivity());
		request.title = title;
		request.url = imgurl;
		request.sourceID = contentid;
		new HttpHomeLoadDataTask(getActivity(), addFavcallBack, false, "", true)
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

	private void delFav() {
		DeleteFavRequest request = new DeleteFavRequest(getActivity());
		request.id = favid;
		new HttpHomeLoadDataTask(getActivity(), delFavcallBack, false, "", true)
				.execute(request);
	}

	IUpdateData delFavcallBack = new IUpdateData() {

		@Override
		public void updateUi(Object o) {
			Logger.log(o.toString());

			// details_colection.setText("收藏");
			// details_colection.setTag("add");
			toast.setIcon(R.drawable.toast_smile);
			toast.setText("取消收藏成功！");
			toast.show();
		}

		@Override
		public void handleErrorData(String info) {
			toast.setIcon(R.drawable.toast_err);
			toast.setText("取消收藏失败！");
			toast.show();
		}

	};

	private void createTvLayout() {
		viewPager = (ViewPager) v.findViewById(R.id.details_key_pager);
		gallery = (Gallery) v.findViewById(R.id.details_key_gallery);

		dktAdapter = new DetailsKeyTabAdapter(getActivity(), detailsKeyList);
		gallery.setAdapter(dktAdapter);

		apAdapter = new AllPagesAdapter(addViewToPager(videoList));
		viewPager.setAdapter(apAdapter);

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				gallery.setSelection(arg0);

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				viewPager.setCurrentItem(position);

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
	}

	/**
	 * 添加到Viewpager
	 * 
	 * @param videoList
	 * @return
	 */
	public ArrayList<View> addViewToPager(ArrayList<Video> videoList) {
		ArrayList<View> list = new ArrayList<View>();
		int num = videoList.size();

		double number = Math.ceil(num / 5.0);

		for (int i = 0; i < number; i++) {
			int sum = (i + 1) * 5;
			if (sum > num) {
				sum = num;
			}
			LinearLayout ll = new LinearLayout(getActivity());
			ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, 50);
			ll.setLeft(40);
			ll.setLayoutParams(params);
			for (int j = i * 5; j < sum; j++) {
				ll.addView(createSetBtn(j, videoList.get(j).name));
			}
			list.add(ll);
		}

		return list;
	}

	private Button createSetBtn(final int i, final String name) {
		Button button = new Button(getActivity());
		button.setWidth(256);
		button.setHeight(65);

		final String numText = (i + 1) + ":" + name;

		button.setText(numText);
		button.setTextSize(17.0F);
		// button.setMaxLines(2);
		button.setSingleLine(true);
		button.setEllipsize(TruncateAt.MARQUEE);
		button.setMarqueeRepeatLimit(3);
		button.setTag(Integer.valueOf(i));
		button.setBackgroundResource(R.drawable.video_details_btn_selector);
		button.setTextColor(Color.WHITE);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(getActivity(), VideoPlayActivity.class);
				videoaddress = videoList.get(i).videoAddress;
				videoId = videoList.get(i).videoId;
				it.putExtra("url", videoaddress);
				it.putExtra("title", title);
				it.putExtra("contentId", contentid);
				it.putExtra("videoId", videoId);
				it.putExtra("numText", name);
				it.putExtra("videoSourceName", videoSourceName);
				startActivity(it);
			}
		});

		return button;
	}

	/**
	 * vlc播放
	 */
	public void vlcPlay() {
		VLCCallbackTask task = new VLCCallbackTask(getActivity()) {

			@Override
			public void run() {
				AudioServiceController c = AudioServiceController.getInstance();
				Logger.log("videoaddress:" + videoaddress);
				c.append(videoaddress);
			}
		};
		task.execute();
	}

}
