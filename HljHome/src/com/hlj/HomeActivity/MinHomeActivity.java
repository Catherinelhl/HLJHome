package com.hlj.HomeActivity;

import java.util.ArrayList;
import java.util.TimerTask;

import net.tsz.afinal.FinalBitmap;
import com.hlj.adapter.AllPagesAdapter;
import com.hlj.utils.ImageLoader;
import com.hlj.utils.Logger;
import com.hlj.utils.NetUtils;
import com.hlj.view.ApplicationLayout;
import com.hlj.view.ApplicationLayoutTest;
import com.hlj.view.CommonTitleView;
import com.hlj.view.SettingsLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Launcher页面(无网状态下)
 * 
 * @author huangyuchao
 * 
 */
public class MinHomeActivity extends Activity {

	ViewPager main_layout_pager;

	AllPagesAdapter adapter;

	private ArrayList<View> pages = new ArrayList();

	private ApplicationLayoutTest appManager;// 应用程序
	private SettingsLayout set;// 设置

	RadioGroup radioGroup;

	public static FinalBitmap fb;

	public static ImageLoader imageLoader;

	LinearLayout ll_all;

	RadioButton application, settings;

	CommonTitleView titleView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_min_home);

		// setContentView(io.vov.vitamio.R.layout.mediacontroller);

		titleView = (CommonTitleView) findViewById(R.id.commonTitle);

		appManager = new ApplicationLayoutTest(this);
		set = new SettingsLayout(this);

		this.appManager.initView();
		this.set.initView();

		pages.add(appManager);
		pages.add(set);

		titleView.setSumNumberText("  当前为无网状态，按遥控器右下角的设置键可以进入到设置网络的界面");

		ll_all = (LinearLayout) findViewById(R.id.ll_all);

		radioGroup = (RadioGroup) findViewById(R.id.title_group);
		radioGroup.requestFocus();
		this.radioGroup.setFocusable(true);
		this.radioGroup.check(R.id.application);

		main_layout_pager = (ViewPager) findViewById(R.id.main_layout_pager);

		main_layout_pager.setCurrentItem(0);
		// main_layout_pager.setOffscreenPageLimit(2);
		main_layout_pager.setNextFocusUpId(R.id.title_group);

		application = (RadioButton) findViewById(R.id.application);
		settings = (RadioButton) findViewById(R.id.settings);

		fb = FinalBitmap.create(this);
		fb.configLoadingImage(0);

		// loadData();

		initListener();

		adapter = new AllPagesAdapter(pages);
		main_layout_pager.setAdapter(adapter);

		radioGroup.requestFocus();
		radioGroup.setFocusable(true);

	}

	java.util.Timer timer;

	@Override
	protected void onResume() {
		CommonTitleView.instance = titleView;
		timer = new java.util.Timer();
		// 启动计时器
		timer.schedule(new Job(), 5 * 1000, 10 * 1000);

		super.onResume();
	}

	class Job extends TimerTask {

		@Override
		public void run() {
			// 判断网络
			if (NetUtils.getAPNType(MinHomeActivity.this) != -1) {
				// Intent it = new Intent(MinHomeActivity.this,
				// PreActivity.class);
				Intent it = new Intent(MinHomeActivity.this,
						BeforePreActivity.class);
				startActivity(it);
				finish();
				timer.cancel();
			}
		}
	}

	@Override
	protected void onDestroy() {
		if (null != appManager) {
			appManager.destroy();
		}
		super.onDestroy();
	}

	public void initListener() {
		radioGroup.requestFocus();
		radioGroup.setFocusable(true);
		// titleView.setFouseble();
		// latest_recommend.requestFocus();
		Logger.log("count:" + radioGroup.getChildCount());
		for (int i = 0; i < radioGroup.getChildCount(); i++) {
			View localView = this.radioGroup.getChildAt(i);
			localView.setTag(Integer.valueOf(i));
			localView
					.setOnFocusChangeListener(new View.OnFocusChangeListener() {

						@Override
						public void onFocusChange(View v, boolean hasFocus) {

							if (hasFocus) {

								int i = Integer.valueOf(String.valueOf(v
										.getTag()));
								main_layout_pager.setCurrentItem(i, true);

								v.requestFocus();
								v.setFocusable(true);
							}
						}
					});

			main_layout_pager
					.setOnPageChangeListener(new OnPageChangeListener() {

						@Override
						public void onPageSelected(int position) {
							if (position < radioGroup.getChildCount()) {
								((RadioButton) radioGroup.getChildAt(position))
										.setChecked(true);
							}

						}

						@Override
						public void onPageScrolled(int arg0, float arg1,
								int arg2) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onPageScrollStateChanged(int arg0) {
							// TODO Auto-generated method stub

						}
					});

		}

	}

	@Override
	public void onBackPressed() {

		// super.onBackPressed();
	}

}
