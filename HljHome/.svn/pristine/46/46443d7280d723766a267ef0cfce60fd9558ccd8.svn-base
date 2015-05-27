package com.hlj.HomeActivity;

import java.util.ArrayList;

import com.hlj.HomeActivity.set.SettingGrade;
import com.hlj.adapter.AllPagesAdapter;
import com.hlj.adapter.VideoInfoAdapter;
import com.hlj.adapter.VodRecodeAdapter;
import com.hlj.net.GetCourseGradeResponse;
import com.hlj.net.GetFavListRequest;
import com.hlj.net.GetStudyGradeRequest;
import com.hlj.net.GetStudyGradeResponse;
import com.hlj.net.HttpHomeLoadDataTask;
import com.hlj.utils.ImageLoader;
import com.hlj.utils.Logger;
import com.hlj.utils.PrefrenceHandler;
import com.hlj.utils.StringTools;
import com.hlj.view.CommonTitleView;
import com.hlj.view.ScaleAnimEffect;
import com.hlj.view.StudyLearnLayout;
import com.hlj.view.VideoInfo;
import com.live.video.constans.VodRecode;
import com.live.video.net.GetFavListResponse;
import com.live.video.net.callback.IUpdateData;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.Preference;
import android.support.v4.view.ViewPager;
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
 * 系列学习
 * 
 * @author huangyuchao
 * 
 */
public class StudyLearnActivity extends BaseActivity implements OnClickListener {

	CommonTitleView titleView;

	String type;

	ImageLoader imageLoader;

	ViewPager main_layout_pager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.study_series_home);

		type = this.getIntent().getStringExtra("type");

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

	private ScaleAnimEffect effect;

	AllPagesAdapter adapter;
	private ArrayList<View> pages = new ArrayList();

	private StudyLearnLayout learn;

	private void initView() {
		this.effect = new ScaleAnimEffect();
		imageLoader = new ImageLoader(this, 0);

		main_layout_pager = (ViewPager) findViewById(R.id.main_layout_pager);
		main_layout_pager.setCurrentItem(0);

		learn = new StudyLearnLayout(this);
		learn.initView();
		pages.add(learn);

		adapter = new AllPagesAdapter(pages);
		main_layout_pager.setAdapter(adapter);
	}

	private void initData() {

	}

	@Override
	public void onClick(View v) {

	}

}
