package com.hlj.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.hj.widget.CommonToast;
import com.hlj.HomeActivity.ApplicationActivity;
import com.hlj.HomeActivity.R;
import com.hlj.adapter.AppChoseListAdapter;
import com.hlj.utils.ImageUtils;
import com.hlj.utils.LauncherBiz;
import com.hlj.widget.PwdcheckWindow;
import com.hlj.widget.PwdcheckWindow.PwdCheckCallBack;
import com.live.video.constans.AppBean;
import com.live.video.constans.HomeConstants;
import com.live.video.db.AppDb;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;

/**
 * Created with IntelliJ IDEA. User: ${shime} Date: 13-9-17 Time: 上午10:33 To
 * change this template use File | Settings | File Templates.
 */
public class ApplicationLayoutTest extends LinearLayout implements
		LayoutInterface, View.OnFocusChangeListener, View.OnClickListener,
		View.OnKeyListener {
	private Context mContext;
	private ScaleAnimEffect animEfffect;

	private Dialog chooseDialog;

	private HashMap<Integer, AppBean> topApps;

	AppDb appDb;

	AppDbDataChangeReceiver receiver;

	public static final String CHANEACTION = "com.launcher.app_db_change";

	ArrayList<AppBean> list;

	public ApplicationLayoutTest(Context context) {
		super(context);
		this.mContext = context;
		this.animEfffect = new ScaleAnimEffect();

		setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		View localView = LayoutInflater.from(mContext).inflate(
				R.layout.application_layout, null);

		appDb = new AppDb(mContext);
		receiver = new AppDbDataChangeReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(CHANEACTION);
		mContext.registerReceiver(receiver, intentFilter);

		list = LauncherBiz.getLauncherApps(mContext);

		AppBean appBean = null;
		for (int i = 0; i < list.size(); i++) {
			if ("文件管理器".equals(list.get(i).getName())) {
				appBean = list.get(i);
				this.appDb.recordApp(appBean, 0);
			}
		}

		this.topApps = this.appDb.queryTopApps();
		fatherview = (RelativeLayout) localView.findViewById(R.id.fatherview);
		addView(localView);
	}

	@Override
	public void destroy() {
		mContext.unregisterReceiver(receiver);
	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub

	}

	private FrameLayout[] fls;
	private ImageView[] bgs;
	private ImageView[] poster;
	private TextView[] appName;

	RelativeLayout fatherview;

	private int[] flIds = { R.id.app_fl_0, R.id.app_fl_1, R.id.app_fl_2,
			R.id.app_fl_3, R.id.app_fl_4, R.id.app_fl_5, R.id.app_fl_6,
			R.id.app_fl_7, R.id.app_fl_8, R.id.app_fl_9 };

	private int[] bgIds = { R.id.app_bg_0, R.id.app_bg_1, R.id.app_bg_2,
			R.id.app_bg_3, R.id.app_bg_4, R.id.app_bg_5, R.id.app_bg_6,
			R.id.app_bg_7, R.id.app_bg_8, R.id.app_bg_9 };

	private int[] postIds = { R.id.app_poster_0, R.id.app_poster_1,
			R.id.app_poster_2, R.id.app_poster_3, R.id.app_poster_4,
			R.id.app_poster_5, R.id.app_poster_6, R.id.app_poster_7,
			R.id.app_poster_8, R.id.app_poster_9 };

	private static int[] bgSelector = { R.drawable.blue_no_shadow_a,
			R.drawable.dark_no_shadow_a, R.drawable.green_no_shadow_a,
			R.drawable.orange_no_shadow_a, R.drawable.pink_no_shadow_a,
			R.drawable.red_no_shadow_a, R.drawable.yellow_no_shadow_a,
			R.drawable.pink_no_s1hadow_a, R.drawable.pink_no_shad2ow_a,
			R.drawable.pink_no_shadowa_a };

	private int[] nameIds = { R.id.app_name_0, R.id.app_name_1,
			R.id.app_name_2, R.id.app_name_3, R.id.app_name_4, R.id.app_name_5,
			R.id.app_name_6, R.id.app_name_7, R.id.app_name_8, R.id.app_name_9 };

	private ImageView whiteBorder;

	private ImageView[] refImg = new ImageView[5];
	int[] refImgInt = { R.id.app_refimg_0, R.id.app_refimg_1,
			R.id.app_refimg_2, R.id.app_refimg_3, R.id.app_refimg_4 };

	private int position = -1;

	private void createContentView() {

		fls = new FrameLayout[10];
		bgs = new ImageView[10];
		poster = new ImageView[10];
		appName = new TextView[10];

		int size = bgSelector.length;
		// int[] newRandomArray = CommonTools.getRandomList(bgSelector);

		for (int i = 0; i < 10; i++) {
			fls[i] = (FrameLayout) fatherview.findViewById(flIds[i]);
			bgs[i] = (ImageView) fatherview.findViewById(bgIds[i]);
			this.bgs[i].setVisibility(View.GONE);
			poster[i] = (ImageView) fatherview.findViewById(postIds[i]);

			this.poster[i].setTag(Integer.valueOf(i));
			this.poster[i].setOnFocusChangeListener(this);
			this.poster[i].setOnKeyListener(this);
			this.poster[i].setOnClickListener(this);

			// Logger.log("random num:" + newRandomArray[i]);
			this.poster[i].setBackgroundResource(bgSelector[i]);

			appName[i] = (TextView) fatherview.findViewById(nameIds[i]);

			if (topApps != null && null != topApps.get(i) && i < 9) {
				this.poster[i].setImageDrawable(topApps.get(i).getIcon());
				this.appName[i].setText(topApps.get(i).getName());

			} else {
				if (i >= 9) {
					this.poster[i].setImageResource(R.drawable.all_apps);
					this.appName[i].setText("所有应用");
				} else {
					this.poster[i].setImageResource(R.drawable.add_apps);
					this.appName[i].setText("（未添加）");
				}
			}
		}

		String packageName = "com.softwinner.TvdFileManager";
		AppBean appBean = appDb.queryAppInfo(packageName);

		System.out.println(appBean.getIcon() + "<============>"
				+ appBean.getName());
		this.poster[0].setImageDrawable(appBean.getIcon());
		this.appName[0].setText(appBean.getName());
	}

	@Override
	public void initView() {

		createContentView();

		this.whiteBorder = ((ImageView) findViewById(R.id.white_boder));
		FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(
				220, 220);
		localLayoutParams.leftMargin = 120;
		localLayoutParams.topMargin = 40;
		this.whiteBorder.setLayoutParams(localLayoutParams);

		for (int i = 0; i < this.refImg.length; ++i) {
			this.refImg[i] = ((ImageView) findViewById(refImgInt[i]));
			this.refImg[i].setImageBitmap(ImageUtils.createCutReflectedImage(
					ImageUtils.convertViewToBitmap(this.fls[i + 5]), 0));
		}

	}

	private void showOnFocusAnimationn(final int i) {
		this.fls[i].bringToFront();
		this.animEfffect.setAttributs(1.0F, 1.1F, 1.0F, 1.1F, 100L);
		Animation localAnimation = this.animEfffect.createAnimation();
		localAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				bgs[i].startAnimation(animEfffect.alphaAnimation(0.0F, 1.0F,
						150L, 0L));
				bgs[i].setVisibility(View.VISIBLE);

			}
		});
		this.poster[i].startAnimation(localAnimation);
	}

	private void showLooseFocusAinimation(int paramInt) {
		this.animEfffect.setAttributs(1.1F, 1.0F, 1.1F, 1.0F, 100L);
		this.poster[paramInt]
				.startAnimation(this.animEfffect.createAnimation());
		this.bgs[paramInt].setVisibility(View.GONE);
	}

	private void flyWhiteBorder(int paramInt1, int paramInt2,
			float paramFloat1, float paramFloat2) {
		if ((this.whiteBorder == null))
			return;
		this.whiteBorder.setVisibility(0);
		int i = this.whiteBorder.getWidth();
		int j = this.whiteBorder.getHeight();
		ViewPropertyAnimator localViewPropertyAnimator = this.whiteBorder
				.animate();
		localViewPropertyAnimator.setDuration(150L);
		localViewPropertyAnimator.scaleX(paramInt1 / i);
		localViewPropertyAnimator.scaleY(paramInt2 / j);
		localViewPropertyAnimator.x(paramFloat1);
		localViewPropertyAnimator.y(paramFloat2);
		localViewPropertyAnimator.start();
	}

	@Override
	public void loadData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		switch (v.getId()) {
		case R.id.app_poster_0:
			this.position = 0;
			doAnything(position, hasFocus);
			break;
		case R.id.app_poster_1:
			this.position = 1;
			doAnything(position, hasFocus);
			break;
		case R.id.app_poster_2:
			this.position = 2;
			doAnything(position, hasFocus);
			break;
		case R.id.app_poster_3:
			this.position = 3;
			doAnything(position, hasFocus);
			break;
		case R.id.app_poster_4:
			this.position = 4;
			doAnything(position, hasFocus);
			break;
		case R.id.app_poster_5:
			this.position = 5;
			doAnything(position, hasFocus);
			break;
		case R.id.app_poster_6:
			this.position = 6;
			doAnything(position, hasFocus);
			break;
		case R.id.app_poster_7:
			this.position = 7;
			doAnything(position, hasFocus);
			break;
		case R.id.app_poster_8:
			this.position = 8;
			doAnything(position, hasFocus);
			break;
		case R.id.app_poster_9:
			this.position = 9;
			doAnything(position, hasFocus);
			break;

		}

	}

	public void doAnything(int position, boolean hasFocus) {
		if (hasFocus) {
			flyWhiteBorder(220, 220, 119 + 205 * (this.position % 5),
					40 + 205 * (this.position / 5));
			showOnFocusAnimationn(position);
		} else {
			showLooseFocusAinimation(position);
			this.whiteBorder.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU
				&& event.getAction() == KeyEvent.ACTION_DOWN) {

			if (position != 0) {
				if (null == this.topApps && null == topApps.get(this.position)) {
					showAppChoosePop("添加应用");
				} else {
					showAppChoosePop("更换应用");
				}
			}

		}
		return false;
	}

	private TextView appNameTv;
	private ListView appListLv;

	AppChoseListAdapter adapter;

	private void showAppChoosePop(String title) {
		if (this.chooseDialog == null) {
			View localView = LayoutInflater.from(this.mContext).inflate(
					R.layout.app_dia_choose_list, null);

			this.appNameTv = (TextView) localView
					.findViewById(R.id.app_choose_pop_name);
			this.appListLv = (ListView) localView
					.findViewById(R.id.app_choose_pop_list);
			this.chooseDialog = new Dialog(this.mContext);
			this.chooseDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			this.chooseDialog.setContentView(localView);
			Window localWindow = this.chooseDialog.getWindow();
			WindowManager.LayoutParams localLayoutParams = localWindow
					.getAttributes();
			localWindow.setGravity(Gravity.CENTER);
			localLayoutParams.width = 300;
			localLayoutParams.height = 450;
			localWindow.setAttributes(localLayoutParams);

			this.appListLv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int itemposition, long id) {
					AppBean appBean = (AppBean) appListLv.getAdapter().getItem(
							itemposition);
					if (topApps != null && null != topApps.get(position)) {
						appDb.removeRecode(appBean, position);
					}
					appDb.recordApp(appBean, position);
					// adapter.notifyDataSetChanged();
					chooseDialog.dismiss();
					mContext.sendBroadcast(new Intent(CHANEACTION));

				}
			});

		}

		adapter = new AppChoseListAdapter(mContext, list);

		this.appNameTv.setText(title);
		this.appListLv.setAdapter(adapter);
		this.chooseDialog.show();
	}

	private class AppDbDataChangeReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(CHANEACTION)) {
				topApps = appDb.queryTopApps();
				createContentView();
				if (position > 0) {
					bgs[position].setVisibility(View.VISIBLE);
				}
			}

		}
	}

	@Override
	public void onClick(View v) {
		if (R.id.app_poster_9 == v.getId()) {
			// if (0 == HomeConstants.isLock) {
			// // 弹出保护框
			// PwdcheckWindow view = new PwdcheckWindow(mContext, v,
			// pwdcheckcallback);
			//
			// } else {
			// 所有应用
			Intent it = new Intent(mContext, ApplicationActivity.class);
			mContext.startActivity(it);
			// }
		} else {
			bootAnApp(position, v);
		}
	}

	PwdCheckCallBack pwdcheckcallback = new PwdCheckCallBack() {

		@Override
		public void check(String str) {
			if ("1".equals(str)) {
				// 直接进入
				Intent it = new Intent(mContext, ApplicationActivity.class);
				mContext.startActivity(it);
			}
		}

	};

	int selectedPosition = 0;

	/**
	 * 进入应用
	 * 
	 * @param postion
	 */
	private void bootAnApp(int postion, View v) {

		selectedPosition = position;

		if (this.topApps.get(postion) != null) {

			if (selectedPosition != 0) {
				try {
					// if (0 == HomeConstants.isLock) {
					// // 弹出保护框
					// PwdcheckWindow view = new PwdcheckWindow(mContext, v,
					// pwdcallback);
					// } else {
					goToApps();
					// }
				} catch (Exception e) {
					e.printStackTrace();
					CommonToast localItvToast = new CommonToast(this.mContext);
					localItvToast.setDuration(1);
					localItvToast.setIcon(R.drawable.toast_err);
					localItvToast.setText(R.string.toast_app_boot_err);
					localItvToast.show();
					appDb.removeRecode((AppBean) topApps.get(postion), postion);
					createContentView();
					return;
				}

			} else {
				goToApps();
			}
		} else {
			CommonToast localItvToast1 = new CommonToast(this.mContext);
			localItvToast1.setDuration(1);
			localItvToast1.setText(R.string.toast_app_boot_no);
			localItvToast1.show();
		}
	}

	private void goToApps() {
		// 直接进入
		Intent localIntent = mContext.getPackageManager()
				.getLaunchIntentForPackage(
						((AppBean) topApps.get(selectedPosition))
								.getPackageName());
		mContext.startActivity(localIntent);
	}

	PwdCheckCallBack pwdcallback = new PwdCheckCallBack() {

		@Override
		public void check(String str) {
			if ("1".equals(str)) {
				goToApps();
			}
		}

	};
}
