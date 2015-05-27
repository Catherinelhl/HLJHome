package com.hlj.HomeActivity;

import java.util.ArrayList;

import com.hlj.HomeActivity.set.SettingAcademic;
import com.hlj.HomeActivity.set.SettingGrade;
import com.hlj.adapter.VideoInfoAdapter;
import com.hlj.adapter.VodRecodeAdapter;
import com.hlj.net.GetContentRecResponse;
import com.hlj.net.GetCourseGradeResponse;
import com.hlj.net.GetFavListRequest;
import com.hlj.net.GetStudyGradeRequest;
import com.hlj.net.GetStudyGradeResponse;
import com.hlj.net.GetStudySubjectRequest;
import com.hlj.net.GetSubjectGradeRequest;
import com.hlj.net.HttpHomeLoadDataTask;
import com.hlj.utils.Logger;
import com.hlj.utils.PrefrenceHandler;
import com.hlj.utils.StringTools;
import com.hlj.view.CommonTitleView;
import com.hlj.view.VideoInfo;
import com.live.video.constans.VodRecode;
import com.live.video.net.GetFavListResponse;
import com.live.video.net.GetPlayListRequest;
import com.live.video.net.GetPlayListResponse;
import com.live.video.net.callback.IUpdateData;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.Preference;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 收藏列表
 * 
 * @author huangyuchao
 * 
 */
public class FavVideoActivity extends BaseActivity implements OnClickListener {

	CommonTitleView titleView;

	VodRecodeAdapter favadapter;

	private ArrayList<VodRecode> recodes = new ArrayList<VodRecode>();

	TextView fav_empty;

	String type;

	ImageView whiteBorder;

	String url;
	int catId;
	String requestType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.favorite_grid_layout);

		type = this.getIntent().getStringExtra("type");
		url = this.getIntent().getStringExtra("url");
		catId = this.getIntent().getIntExtra("catId", 0);
		requestType = this.getIntent().getStringExtra("requestType");

		titleView = (CommonTitleView) findViewById(R.id.commonTitle);
		titleView.setTypeDetailsText(type);

		initView();
		initData();
	}

	// @Override
	protected void onResume() {
		CommonTitleView.instance = titleView;
		super.onResume();
	}

	GridView favorite_grid;

	private FrameLayout[] fls = new FrameLayout[3];
	private ImageView[] infos = new ImageView[3];
	private ImageView[] posters = new ImageView[3];

	private void initView() {

		fav_empty = (TextView) findViewById(R.id.fav_empty);

		favorite_grid = (GridView) findViewById(R.id.favorite_grid);

		favorite_grid.setSelector(new ColorDrawable(Color.TRANSPARENT));
		favadapter = new VodRecodeAdapter(this, recodes);
		favorite_grid.setAdapter(favadapter);

		whiteBorder = (ImageView) findViewById(R.id.white_border);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(162, 238);
		params.leftMargin = 55;
		params.topMargin = 105;
		whiteBorder.setLayoutParams(params);

	}

	private void initData() {
		CommonTitleView.instance = titleView;

		if ("系列学习".equals(type)) {
			String isSet = PrefrenceHandler.getIsSetStr(this);
			// 如果之前没有设置过，进入到设置界面设置
			// if ("false".equals(isSet)) {
			// // Intent it = new Intent(FavVideoActivity.this,
			// // SettingGrade.class);
			// Intent it = new Intent(FavVideoActivity.this,
			// SettingAcademic.class);
			// startActivity(it);
			// } else {
			// 设置过了的话直接获取列表
			getStudyGradeList("");
			// }
		} else if ("英语".equals(type)) {
			if (StringTools.isNullOrEmpty(PrefrenceHandler.getEnglishId(this))) {
				favorite_grid.setVisibility(View.GONE);
				fav_empty.setVisibility(View.VISIBLE);
				fav_empty.setText("您还没有设置英语系列的学习哦，快去学年设置里面设置一下吧！");
			} else {
				String subject = "3";
				getStudyGradeList(subject);
			}
		} else if ("语文".equals(type)) {
			if (StringTools.isNullOrEmpty(PrefrenceHandler.getLanguageId(this))) {
				favorite_grid.setVisibility(View.GONE);
				fav_empty.setVisibility(View.VISIBLE);
				fav_empty.setText("您还没有设置语文系列的学习哦，快去学年设置里面设置一下吧！");
			} else {
				String subject = "1";
				getStudyGradeList(subject);
			}

		} else if ("数学".equals(type)) {
			if (StringTools.isNullOrEmpty(PrefrenceHandler.getMathId(this))) {
				favorite_grid.setVisibility(View.GONE);
				fav_empty.setVisibility(View.VISIBLE);
				fav_empty.setText("您还没有设置数学系列的学习哦，快去学年设置里面设置一下吧！");
			} else {
				String subject = "2";
				getStudyGradeList(subject);
			}
		}

		else if ("我的收藏".equals(type)) {
			getFavList();
		} else {
			getPlayInfoList();
		}
	}

	private void getStudyGradeList(String subject) {

		GetStudySubjectRequest request = new GetStudySubjectRequest(this);
		// GetStudyGradeRequest request = new GetStudyGradeRequest();
		// request.grade = StringTools.getCurrentGrade(this) + "";
		// if ("3".equals(subject)) {
		// request.versions = PrefrenceHandler.getEnglishId(this);// versionid
		// } else if ("1".equals(subject)) {
		// request.versions = PrefrenceHandler.getLanguageId(this);// versionid
		// } else if ("2".equals(subject)) {
		// request.versions = PrefrenceHandler.getMathId(this);// versionid
		// }

		request.subject = subject;
		// request.englishVer = "english";
		request.catId = catId;
		request.type = requestType;

		new HttpHomeLoadDataTask(this, studyGradeCallBack, true, url, false)
				.execute(request);
	}

	ArrayList<VideoInfo> list = new ArrayList<VideoInfo>();

	VideoInfoAdapter videoInfoAdapter;

	IUpdateData studyGradeCallBack = new IUpdateData() {

		@Override
		public void updateUi(Object o) {
			Logger.log(o.toString());

			GetContentRecResponse response = new GetContentRecResponse();
			response.paseRespones(o.toString());
			list.clear();
			list = response.list;

			videoInfoAdapter = new VideoInfoAdapter(FavVideoActivity.this, list);
			favorite_grid.setAdapter(videoInfoAdapter);
			videoInfoAdapter.notifyDataSetChanged();

			favorite_grid
					.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> parent,
								View view, int position, long id) {
							// whiteBorder.setVisibility(View.VISIBLE);
							// flyWhiteBorder(162, 238, 55.0F +
							// view.getX(),105.0F + view.getY());
						}

						@Override
						public void onNothingSelected(AdapterView<?> parent) {
							// whiteBorder.setVisibility(View.INVISIBLE);
						}
					});

			favorite_grid.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// Intent it = new Intent(FavVideoActivity.this,
					// StudyVideoDetailsActivity.class);
					// it.putExtra("contentid", list.get(position).contentId);
					// it.putExtra("grade", list.get(position).grade);
					// it.putExtra("versions", list.get(position).versions);
					// it.putExtra("subject", list.get(position).subject);
					// startActivity(it);

					VideoInfo videoInfo = list.get(position);
					Intent it = new Intent(FavVideoActivity.this,
							StudyTypeDetailsActivity.class);
					it.putExtra("type", "系列学习");
					it.putExtra("url", videoInfo.url);
					it.putExtra("catId", videoInfo.catId);
					it.putExtra("requestType", videoInfo.type);
					startActivity(it);

				}
			});

		}

		@Override
		public void handleErrorData(String info) {
			// TODO Auto-generated method stub

		}

	};

	private void getPlayInfoList() {
		GetPlayListRequest request = new GetPlayListRequest(this);
		new HttpHomeLoadDataTask(this, getPlayInfoListCallBack, true, url,
				false).execute(request);
	}

	IUpdateData getPlayInfoListCallBack = new IUpdateData() {

		@Override
		public void updateUi(Object o) {
			GetPlayListResponse response = new GetPlayListResponse();
			response.paseRespones(o.toString());
			list.clear();
			list = response.list;
			videoInfoAdapter = new VideoInfoAdapter(FavVideoActivity.this, list);
			favorite_grid.setAdapter(videoInfoAdapter);
			videoInfoAdapter.notifyDataSetChanged();

			favorite_grid
					.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> parent,
								View view, int position, long id) {
							// whiteBorder.setVisibility(View.VISIBLE);
							// flyWhiteBorder(162, 238, 55.0F +
							// view.getX(),105.0F + view.getY());
						}

						@Override
						public void onNothingSelected(AdapterView<?> parent) {
							// whiteBorder.setVisibility(View.INVISIBLE);
						}
					});

			favorite_grid.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Intent it = new Intent(FavVideoActivity.this,
							RecomdVideoDetailsActivity.class);
					it.putExtra("contentid", list.get(position).contentId);
					startActivity(it);
				}
			});
		}

		@Override
		public void handleErrorData(String info) {
			// TODO Auto-generated method stub

		}

	};

	private void getFavList() {
		GetFavListRequest request = new GetFavListRequest(this);
		new HttpHomeLoadDataTask(this, favListCallBack, true, url, true)
				.execute(request);
	}

	IUpdateData favListCallBack = new IUpdateData() {

		@Override
		public void updateUi(Object o) {
			Logger.log(o.toString());

			GetFavListResponse response = new GetFavListResponse();
			response.paseRespones(o.toString());
			recodes.clear();
			recodes = response.list;
			favadapter.notifyDataSetChanged();
			favadapter = new VodRecodeAdapter(FavVideoActivity.this, recodes);
			favorite_grid.setAdapter(favadapter);

			favorite_grid
					.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> parent,
								View view, int position, long id) {
							// whiteBorder.setVisibility(View.VISIBLE);
							// flyWhiteBorder(162, 238, 55.0F + view.getX(),
							// 105.0F + view.getY());
						}

						@Override
						public void onNothingSelected(AdapterView<?> parent) {
							// whiteBorder.setVisibility(View.INVISIBLE);
						}
					});

			favorite_grid.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Intent it = new Intent(FavVideoActivity.this,
							RecomdVideoDetailsActivity.class);
					it.putExtra("contentid", recodes.get(position).sourceid);
					it.putExtra("from", "Collection");
					it.putExtra("favid", recodes.get(position).id);
					startActivity(it);
				}
			});

		}

		@Override
		public void handleErrorData(String info) {
			favorite_grid.setVisibility(View.GONE);
			fav_empty.setVisibility(View.VISIBLE);

			if ("系列学习".equals(type)) {
				fav_empty.setText("您还没有系列学习哦，快去看看有最近想学的东东吧！");
			} else if ("英语".equals(type)) {
				fav_empty.setText("您还没有英语系列的学习哦，快去看看有最近想学的东东吧！");
			} else if ("语文".equals(type)) {
				fav_empty.setText("您还没有语文系列的学习哦，快去看看有最近想学的东东吧！");
			} else if ("数学".equals(type)) {
				fav_empty.setText("您还没有数学系列的学习哦，快去看看有最近想学的东东吧！");
			}

			else if ("播放历史".equals(type)) {
				fav_empty.setText("您还没有播放记录啊，海量的电影、电视剧每天更新，更有大片抢先看，快去欣赏吧！");
			} else if ("我的收藏".equals(type)) {
				fav_empty.setText("您还没有收藏哦，“影视分类”里有海量的电影、电视剧，快去收藏吧！");
			}
		}

	};

	private void flyWhiteBorder(int paramInt1, int paramInt2,
			float paramFloat1, float paramFloat2) {
		if ((this.whiteBorder == null))
			return;
		int i = this.whiteBorder.getWidth();
		int j = this.whiteBorder.getHeight();
		ViewPropertyAnimator localViewPropertyAnimator = this.whiteBorder
				.animate();
		localViewPropertyAnimator.setDuration(100L);
		localViewPropertyAnimator.scaleX(paramInt1 / i);
		localViewPropertyAnimator.scaleY(paramInt2 / j);
		localViewPropertyAnimator.x(paramFloat1);
		localViewPropertyAnimator.y(paramFloat2);
		localViewPropertyAnimator.start();
	}

	@Override
	public void onClick(View v) {

	}

}
