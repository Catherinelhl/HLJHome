package com.hlj.view;

import java.util.ArrayList;
import java.util.Map;

import net.tsz.afinal.FinalBitmap;

import com.hlj.HomeActivity.FavVideoActivity;
import com.hlj.HomeActivity.MainActivity;
import com.hlj.HomeActivity.R;
import com.hlj.HomeActivity.StudyLearnActivity;
import com.hlj.HomeActivity.TVVitaBackActivity;
import com.hlj.adapter.MusicAdapter;
import com.hlj.utils.ImageLoader;
import com.hlj.utils.ImageUtils;
import com.hlj.widget.PwdcheckWindow;
import com.hlj.widget.PwdcheckWindow.PwdCheckCallBack;
import com.live.video.constans.HomeConstants;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class MusicLayout extends LinearLayout implements LayoutInterface,
		View.OnFocusChangeListener, View.OnClickListener {

	private Context mContext;

	private ImageView[] backGrounds = new ImageView[3];
	private TextView colectionCount;
	private View colectionLayout;
	private ScaleAnimEffect effect;
	private FrameLayout[] fls = new FrameLayout[3];
	private ImageView[] infos = new ImageView[3];
	private ImageView[] posters = new ImageView[3];
	private ImageView refImageView;
	private TextView zhuiCount;
	private TextView zhuiName;

	private ImageView whiteBorder;

	ImageLoader imageLoader;

	FinalBitmap fb;

	public MusicLayout(Context context) {
		super(context);
		this.mContext = context;
		this.effect = new ScaleAnimEffect();
		imageLoader = new ImageLoader(context, 0);

		fb = FinalBitmap.create(context);
		fb.configLoadingImage(0);
	}

	MusicAdapter adapter;

	public void setMap(Map<String, Object> map) {
		// list.add(map);
		adapter.setMap(map);
	}

	public MusicLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		this.effect = new ScaleAnimEffect();
	}

	private void flyWhiteBorder(int paramInt1, int paramInt2,
			float paramFloat1, float paramFloat2) {
		if ((this.whiteBorder == null))
			return;
		this.whiteBorder.setVisibility(View.VISIBLE);
		int i = this.whiteBorder.getWidth();
		int j = this.whiteBorder.getHeight();
		ViewPropertyAnimator localViewPropertyAnimator = this.whiteBorder
				.animate();
		localViewPropertyAnimator.setDuration(150L);
		// localViewPropertyAnimator.scaleX(paramInt1 / i);
		// localViewPropertyAnimator.scaleY(paramInt2 / j);
		localViewPropertyAnimator.x(paramFloat1);
		localViewPropertyAnimator.y(paramFloat2);
		localViewPropertyAnimator.start();
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initView() {
		setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		setGravity(Gravity.CENTER_HORIZONTAL);
		// setOrientation(VERTICAL);

		addView(LayoutInflater.from(this.mContext).inflate(
				R.layout.play_colection, null));

		this.fls[0] = ((FrameLayout) findViewById(R.id.play_collect_fl_0));
		this.fls[1] = ((FrameLayout) findViewById(R.id.play_collect_fl_1));
		this.fls[2] = ((FrameLayout) findViewById(R.id.play_collect_fl_2));
		this.posters[0] = ((ImageView) findViewById(R.id.play_collect_post_0));
		this.posters[1] = ((ImageView) findViewById(R.id.play_collect_post_1));
		this.posters[2] = ((ImageView) findViewById(R.id.play_collect_post_2));
		this.backGrounds[0] = ((ImageView) findViewById(R.id.play_collect_bg_0));
		this.backGrounds[1] = ((ImageView) findViewById(R.id.play_collect_bg_1));
		this.backGrounds[2] = ((ImageView) findViewById(R.id.play_collect_bg_2));
		this.infos[0] = ((ImageView) findViewById(R.id.play_collect_info_0));
		this.infos[1] = ((ImageView) findViewById(R.id.play_collect_info_1));
		this.infos[2] = ((ImageView) findViewById(R.id.play_collect_info_2));
		this.colectionLayout = findViewById(R.id.play_colection_layout);
		this.refImageView = ((ImageView) findViewById(R.id.play_collect_reflected_img));
		this.colectionCount = ((TextView) findViewById(R.id.play_collect_tv_text_count));
		this.zhuiName = ((TextView) findViewById(R.id.play_collect_tv_text_tvname));
		this.zhuiCount = ((TextView) findViewById(R.id.play_collect_collected_count));

		for (int i = 0; i < 3; i++) {
			this.whiteBorder = ((ImageView) findViewById(R.id.white_boder));
			FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(
					292, 445);
			localLayoutParams.leftMargin = 100;
			localLayoutParams.topMargin = 0;
			this.whiteBorder.setLayoutParams(localLayoutParams);
			this.posters[i].setOnFocusChangeListener(this);
			this.posters[i].setOnClickListener(this);
			this.backGrounds[i].setVisibility(View.GONE);

			Bitmap localBitmap = ImageUtils.createCutReflectedImage(
					ImageUtils.convertViewToBitmap(colectionLayout), 80);
			this.refImageView.setImageBitmap(localBitmap);
		}

	}

	@Override
	public void loadData() {

	}

	ArrayList<VideoInfo> list = new ArrayList<VideoInfo>();

	public ArrayList<VideoInfo> getList() {
		return list;
	}

	public void setList(ArrayList<VideoInfo> list) {
		this.list = list;
	}

	@Override
	public void updateData() {
		if (null != list && list.size() > 0) {

			for (int i = 0; i < list.size(); i++) {
				VideoInfo info = (VideoInfo) list.get(i);
				// imageLoader.displayImage(info.imageUrl, posters[i]);
				fb.display(posters[i], info.imageUrl);
			}

		}
	}

	private void showLooseFocusAinimation(int paramInt) {
		this.effect.setAttributs(1.1F, 1.0F, 1.1F, 1.0F, 100L);
		this.posters[paramInt].startAnimation(this.effect.createAnimation());
		this.infos[paramInt].startAnimation(this.effect.createAnimation());
		this.backGrounds[paramInt].setVisibility(View.GONE);
	}

	private void showOnFocusAnimation(final int paramInt) {
		this.fls[paramInt].bringToFront();
		this.effect.setAttributs(1.0F, 1.1F, 1.0F, 1.1F, 100L);
		Animation localAnimation = this.effect.createAnimation();
		this.posters[paramInt].startAnimation(localAnimation);
		localAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				backGrounds[paramInt].setVisibility(View.VISIBLE);
				backGrounds[paramInt].startAnimation(effect.alphaAnimation(
						0.0F, 1.0F, 120L, 30L));
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub

			}
		});
		this.infos[paramInt].startAnimation(localAnimation);

	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		switch (v.getId()) {
		case R.id.play_collect_post_0:
			if (hasFocus) {
				showOnFocusAnimation(0);

				whiteBorder.getLayoutParams().width = 292;
				whiteBorder.getLayoutParams().height = 445;

				flyWhiteBorder(292, 445, 100.0F, 30.0F);
			} else {
				showLooseFocusAinimation(0);
				this.whiteBorder.setVisibility(View.INVISIBLE);
			}
			break;
		case R.id.play_collect_post_1:
			if (hasFocus) {
				showOnFocusAnimation(1);

				whiteBorder.getLayoutParams().width = 304;
				whiteBorder.getLayoutParams().height = 445;

				flyWhiteBorder(304, 445, 370.0F, 30.0F);
			} else {
				showLooseFocusAinimation(1);
				this.whiteBorder.setVisibility(View.INVISIBLE);
			}
			break;
		case R.id.play_collect_post_2:
			if (hasFocus) {
				showOnFocusAnimation(2);

				whiteBorder.getLayoutParams().width = 304;
				whiteBorder.getLayoutParams().height = 445;

				flyWhiteBorder(304, 445, 651.0F, 30.0F);
			} else {
				showLooseFocusAinimation(2);
				this.whiteBorder.setVisibility(View.INVISIBLE);
			}
			break;
		}

	}

	int position = 0;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.play_collect_post_0:
			position = 0;

			if (null != list && list.size() > 0) {
				if ("0".equals(list.get(position).childLock)) {
					Intent it = new Intent(mContext, StudyLearnActivity.class);
					it.putExtra("type", "系列学习");
					mContext.startActivity(it);
				} else {
					if (HomeConstants.HAS_LOCK
							.equals(HomeConstants.isVersionLock)
							&& HomeConstants.HAS_LOCK
									.equals(HomeConstants.isPrimaryLock)
							&& HomeConstants.HAS_LOCK
									.equals(HomeConstants.isTempLock)) {
						// 弹出保护框
						PwdcheckWindow view = new PwdcheckWindow(mContext, v,
								pwdcallback);
					} else {
						Intent it = new Intent(mContext,
								StudyLearnActivity.class);
						it.putExtra("type", "系列学习");
						mContext.startActivity(it);
					}
				}
			}

			break;
		case R.id.play_collect_post_1:
			position = 1;
			if (null != list && list.size() > 0) {
				if ("0".equals(list.get(position).childLock)) {
					Intent it1 = new Intent(mContext, FavVideoActivity.class);
					it1.putExtra("type", "播放历史");
					mContext.startActivity(it1);
				} else {
					if (HomeConstants.HAS_LOCK.equals(HomeConstants.isVersionLock)
							&& HomeConstants.HAS_LOCK
									.equals(HomeConstants.isPrimaryLock)
							&& HomeConstants.HAS_LOCK
									.equals(HomeConstants.isTempLock)) {
						// 弹出保护框
						PwdcheckWindow view = new PwdcheckWindow(mContext, v,
								pwdcallback);
					} else {
						Intent it1 = new Intent(mContext,
								FavVideoActivity.class);
						it1.putExtra("type", "播放历史");
						mContext.startActivity(it1);
					}
				}
			}
			break;
		case R.id.play_collect_post_2:
			position = 2;
			if (null != list && list.size() > 0) {
				if ("0".equals(list.get(position).childLock)) {
					Intent it2 = new Intent(mContext, FavVideoActivity.class);
					it2.putExtra("type", "我的收藏");
					mContext.startActivity(it2);
				} else {
					if (HomeConstants.HAS_LOCK.equals(HomeConstants.isVersionLock)
							&& HomeConstants.HAS_LOCK
									.equals(HomeConstants.isPrimaryLock)
							&& HomeConstants.HAS_LOCK
									.equals(HomeConstants.isTempLock)) {
						// 弹出保护框
						PwdcheckWindow view = new PwdcheckWindow(mContext, v,
								pwdcallback);
					} else {
						Intent it2 = new Intent(mContext,
								FavVideoActivity.class);
						it2.putExtra("type", "我的收藏");
						mContext.startActivity(it2);
					}

				}
			}
			break;
		}

	}

	PwdCheckCallBack pwdcallback = new PwdCheckCallBack() {

		@Override
		public void check(String str) {
			if ("1".equals(str)) {
				if (position == 0) {
					Intent it = new Intent(mContext, StudyLearnActivity.class);
					it.putExtra("type", "系列学习");
					mContext.startActivity(it);
				} else if (position == 1) {
					Intent it1 = new Intent(mContext, FavVideoActivity.class);
					it1.putExtra("type", "播放历史");
					mContext.startActivity(it1);
				} else if (position == 2) {
					Intent it2 = new Intent(mContext, FavVideoActivity.class);
					it2.putExtra("type", "我的收藏");
					mContext.startActivity(it2);
				}
			}
		}

	};

}
