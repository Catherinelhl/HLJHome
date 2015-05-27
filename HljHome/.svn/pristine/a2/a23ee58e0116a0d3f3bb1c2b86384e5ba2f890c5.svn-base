package com.hlj.HomeActivity;

import java.util.ArrayList;

import com.hlj.adapter.VideoInfoAdapter;
import com.hlj.net.GetCategoryAllRequest;
import com.hlj.net.GetCategoryContentListRequest;
import com.hlj.net.GetCategoryContentListResponse;
import com.hlj.net.GetCourseAllRequest;
import com.hlj.net.GetSubjectGradeRequest;
import com.hlj.net.GetCourseGradeResponse;
import com.hlj.net.GetStudyGradeRequest;
import com.hlj.net.GetSubjectGradeResponse;
import com.hlj.net.HttpHomeLoadDataTask;
import com.hlj.utils.Logger;
import com.hlj.view.CommonTitleView;
import com.hlj.view.MyImageView;
import com.hlj.view.VideoInfo;
import com.live.video.net.callback.IUpdateData;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 教育分类详情
 * 
 * @author huangyuchao
 * 
 */
public class StudyTypeDetailsActivity extends BaseActivity {

	TextView type_details_type, type_details_sum, type_details_fliter_type;

	GridView type_details_grid;

	VideoInfoAdapter adapter;

	View type_details_menulayout;

	CommonTitleView commonTitleView;

	ImageView whiteBorder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.type_details);

		title = this.getIntent().getStringExtra("title");

		shortTitle = this.getIntent().getStringExtra("shortTitle");

		commonTitleView = (CommonTitleView) findViewById(R.id.commonTitle);
		// commonTitleView.setMenuTextVisible();
		commonTitleView.setTypeDetailsText(shortTitle);
		// commonTitleView.setSumNumberText("共6804部");

		initView();
	}

	@Override
	protected void onResume() {
		CommonTitleView.instance = commonTitleView;
		super.onResume();
	}

	ArrayList<VideoInfo> list = new ArrayList<VideoInfo>();

	int catId;
	String url;
	String title;
	String shortTitle;
	String grade = "";
	String versions = "";

	private void initView() {

		catId = this.getIntent().getIntExtra("catId", 0);
		url = this.getIntent().getStringExtra("url");
		grade = this.getIntent().getStringExtra("grade");
		versions = this.getIntent().getStringExtra("versions");

		type_details_menulayout = findViewById(R.id.type_details_menulayout);
		type_details_grid = (GridView) findViewById(R.id.type_details_grid);

		// type_details_type = (TextView) findViewById(R.id.type_details_type);
		// type_details_type.setText(title);

		// type_details_sum = (TextView) findViewById(R.id.type_details_sum);
		// type_details_sum.setText("共12部");

		type_details_fliter_type = (TextView) findViewById(R.id.type_details_fliter_type);
		type_details_fliter_type.setText("");

		list = new ArrayList<VideoInfo>();

		// addVideoInfo();

		type_details_grid.setSelector(new ColorDrawable(Color.TRANSPARENT));

		adapter = new VideoInfoAdapter(this, list);
		type_details_grid.setAdapter(adapter);

		whiteBorder = (ImageView) findViewById(R.id.white_border);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(162, 238);
		params.leftMargin = 55;
		params.topMargin = 105;
		whiteBorder.setLayoutParams(params);

		type_details_grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent it = new Intent(StudyTypeDetailsActivity.this,
						StudyVideoDetailsActivity.class);
				it.putExtra("catId", list.get(position).catId);
				it.putExtra("contentid", list.get(position).contentId);
				it.putExtra("grade", list.get(position).grade);
				it.putExtra("subject", list.get(position).subject);
				it.putExtra("versions", list.get(position).versions);
				it.putExtra("title", list.get(position).title);

				it.putExtra("imgUrl", list.get(position).imageUrl);
				startActivity(it);
			}
		});

		// getCourseAll();

		// getCategotyContentList();

		getCourseGradeList();

	}

	private void getCourseGradeList() {
		GetSubjectGradeRequest request = new GetSubjectGradeRequest(this);
		request.grade = grade;
		request.versions = versions;
		request.catId = catId;
		new HttpHomeLoadDataTask(this, courseGradecallBack, true, url, false)
				.execute(request);
	}

	IUpdateData courseGradecallBack = new IUpdateData() {

		@Override
		public void updateUi(Object o) {
			Logger.log(o.toString());
			GetSubjectGradeResponse response = new GetSubjectGradeResponse();
			response.paseRespones(o.toString());

			commonTitleView.setSumNumberText("共" + response.count + "部");

			list = response.list;

			adapter = new VideoInfoAdapter(StudyTypeDetailsActivity.this, list);
			type_details_grid.setAdapter(adapter);
			adapter.notifyDataSetChanged();

			type_details_grid
					.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> parent,
								View view, int position, long id) {
							//whiteBorder.setVisibility(View.VISIBLE);
							//flyWhiteBorder(162, 238, 55.0F + view.getX(),105.0F + view.getY());
						}

						@Override
						public void onNothingSelected(AdapterView<?> parent) {
							//whiteBorder.setVisibility(View.INVISIBLE);
						}
					});
		}

		@Override
		public void handleErrorData(String info) {
			// TODO Auto-generated method stub

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

	private void getCategotyContentList() {
		GetCategoryContentListRequest request = new GetCategoryContentListRequest(this);
		request.catId = catId;
		new HttpHomeLoadDataTask(this, getCategotyContentCallBack, true, url,
				false).execute(request);
	}

	IUpdateData getCategotyContentCallBack = new IUpdateData() {

		@Override
		public void updateUi(Object o) {
			Logger.log(o.toString());

			GetCategoryContentListResponse response = new GetCategoryContentListResponse();
			response.paseRespones(o.toString());

			commonTitleView.setSumNumberText("共" + response.count + "部");

			list = response.list;

			adapter = new VideoInfoAdapter(StudyTypeDetailsActivity.this, list);
			type_details_grid.setAdapter(adapter);
			adapter.notifyDataSetChanged();

			type_details_grid
					.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> parent,
								View view, int position, long id) {
							whiteBorder.setVisibility(View.VISIBLE);
							flyWhiteBorder(162, 238, 55.0F + view.getX(),
									105.0F + view.getY());
						}

						@Override
						public void onNothingSelected(AdapterView<?> parent) {
							whiteBorder.setVisibility(View.INVISIBLE);
						}
					});
		}

		@Override
		public void handleErrorData(String info) {

		}
	};

	private void getCourseAll() {
		GetCourseAllRequest request = new GetCourseAllRequest(this);
		new HttpHomeLoadDataTask(this, getCategotyAllCallBack, true, "", false)
				.execute(request);
	}

	IUpdateData getCategotyAllCallBack = new IUpdateData() {

		@Override
		public void updateUi(Object o) {
			Logger.log(o.toString());
		}

		@Override
		public void handleErrorData(String info) {

		}
	};

	private void addVideoInfo() {

		for (int i = 0; i < 12; i++) {
			VideoInfo info = new VideoInfo();
			info.title = "僵尸大战世界";
			info.banben = "评分:8.9";
			info.imageUrl = "http://t3.baidu.com/it/u=2201589579,464456501&fm=21&gp=0.jpg";
			list.add(info);
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_MENU:
			// if (type_details_menulayout.getVisibility() == View.GONE) {
			// type_details_menulayout.setVisibility(View.VISIBLE);
			// this.type_details_grid.clearFocus();
			// this.type_details_grid.setFocusable(false);
			// this.type_details_menulayout.requestFocus();
			// this.type_details_menulayout.setFocusable(true);
			// } else if (type_details_menulayout.getVisibility() ==
			// View.VISIBLE) {
			// type_details_menulayout.setVisibility(View.GONE);
			// this.type_details_menulayout.clearFocus();
			// this.type_details_menulayout.setFocusable(false);
			// this.type_details_grid.requestFocus();
			// this.type_details_grid.setFocusable(true);
			// }

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
