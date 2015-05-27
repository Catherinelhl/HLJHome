package com.hlj.view;

import java.util.ArrayList;
import java.util.Random;

import com.hj.widget.CommonToast;
//import com.hlj.HomeActivity.HomeActivity;
import com.hlj.HomeActivity.LixianActivity;
import com.hlj.HomeActivity.R;
import com.hlj.HomeActivity.RecomdTypeDetailsActivity;
import com.hlj.HomeActivity.RecomdVideoDetailsActivity;
import com.hlj.HomeActivity.StudyTypeDetailsActivity;
import com.hlj.HomeActivity.StudyVideoDetailsActivity;
import com.hlj.net.BitmapWorkerTask;
import com.hlj.net.BitmapWorkerTask.ImageCallBack;
import com.hlj.utils.BitmapTask;
import com.hlj.utils.CommonTools;
import com.hlj.utils.ImageLoader;
import com.hlj.utils.StringTools;
import com.hlj.utils.BitmapTask.PostCallBack;
import com.hlj.utils.ImageUtils;
import com.hlj.widget.PwdcheckWindow;
import com.hlj.widget.PwdcheckWindow.PwdCheckCallBack;
import com.live.video.constans.HomeConstants;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class StudyLayout extends LinearLayout implements LayoutInterface,
		View.OnClickListener, View.OnFocusChangeListener {

	private Context mContext;

	private static int[] bgSelector = { R.drawable.blue_no_shadow,
			R.drawable.dark_no_shadow, R.drawable.green_no_shadow,
			R.drawable.orange_no_shadow, R.drawable.pink_no_shadow,
			R.drawable.red_no_shadow, R.drawable.yellow_no_shadow,
			R.drawable.pink_no_shadowa, R.drawable.pink_no_s1hadow,
			R.drawable.pink_no_shad2ow };

	ScaleAnimEffect animEffect = new ScaleAnimEffect();
	private ImageView[] backGrounds = new ImageView[7];
	private FrameLayout[] fls = new FrameLayout[7];
	private ImageView[] typeLogs = new ImageView[7];
	private HorizontalScrollView scrollView;
	private ImageView[] posts = new ImageView[3];
	private ImageView[] refImg = new ImageView[4];
	int refIndex = 0;
	
	// private ArrayList<VideoTypeInfo> typePageData;
	private ImageView whiteBorder;

	private ImageLoader imageLoader;

	CommonToast toast;

	StudyLayout instance;

	public StudyLayout(Context context) {
		super(context);
		mContext = context;
		imageLoader = new ImageLoader(mContext, 0);
		toast = new CommonToast(mContext);
		instance = this;
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

	private void showOnFocusAnimation(final int position) {
		fls[position].bringToFront();
		animEffect.setAttributs(1.0F, 1.1F, 1.0F, 1.1F, 100L);
		Animation animation = animEffect.createAnimation();
		animation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationEnd(Animation paramAnimation) {
				backGrounds[position].startAnimation(animEffect.alphaAnimation(
						0.0F, 1.0F, 150L, 0L));
				backGrounds[position].setVisibility(View.VISIBLE);
				titletv[position].startAnimation(animEffect.alphaAnimation(
						0.0F, 1.0F, 150L, 0L));
				if (StringTools.isNullOrEmpty(titletv[position].getText()
						.toString())) {
					titletv[position].setVisibility(View.GONE);
				} else {
					titletv[position].setVisibility(View.VISIBLE);
				}

			}

			@Override
			public void onAnimationRepeat(Animation paramAnimation) {
			}

			@Override
			public void onAnimationStart(Animation paramAnimation) {
			}
		});
		typeLogs[position].startAnimation(animation);

		if (position == 0) {
			posts[position].startAnimation(animation);
		} else if (position == 3) {
			posts[1].startAnimation(animation);
		} else if (position == 4) {
			posts[2].startAnimation(animation);
		}
	}

	private void showLooseFocusAinimation(int position) {
		animEffect.setAttributs(1.1F, 1.0F, 1.1F, 1.0F, 100L);
		typeLogs[position].startAnimation(animEffect.createAnimation());
		backGrounds[position].setVisibility(View.GONE);
		titletv[position].setVisibility(View.GONE);

		if (position == 0) {
			posts[position].startAnimation(animEffect.createAnimation());
		} else if (position == 3) {
			posts[1].startAnimation(animEffect.createAnimation());
		} else if (position == 4) {
			posts[2].startAnimation(animEffect.createAnimation());
		}
	}

	@Override
	public void destroy() {
		for (int i = 0; i < posts.length; i++) {
			posts[i] = null;
		}
		for (int i = 0; i < refImg.length; i++) {
			refImg[i] = null;
		}
		for (int i = 0; i < fls.length; i++) {
			backGrounds[i] = null;
			typeLogs[i] = null;
			fls[i] = null;
		}
	}

	@Override
	public void initListener() {
		// if (!isFirst) {
		for (int i = 0; i < 7; i++) {
			typeLogs[i].setOnFocusChangeListener(this);
		}
		// } else {
		isFirst = false;
		// }

	}

	private TextView[] titletv = new TextView[7];

	@Override
	public void initView() {
		setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		setGravity(Gravity.CENTER_HORIZONTAL);
		setOrientation(VERTICAL);

		this.scrollView = (HorizontalScrollView) LayoutInflater.from(mContext)
				.inflate(R.layout.latest_study, null);
		addView(scrollView);

		// scrollView.smoothScrollTo(50, 300);

		refImg[0] = (ImageView) findViewById(R.id.video_type_refimg_0);
		refImg[1] = (ImageView) findViewById(R.id.video_type_refimg_1);
		refImg[2] = (ImageView) findViewById(R.id.video_type_refimg_2);
		refImg[3] = (ImageView) findViewById(R.id.video_type_refimg_3);

		fls[0] = (FrameLayout) findViewById(R.id.video_type_fl_0);
		fls[1] = (FrameLayout) findViewById(R.id.video_type_fl_1);
		fls[2] = (FrameLayout) findViewById(R.id.video_type_fl_2);
		fls[3] = (FrameLayout) findViewById(R.id.video_type_fl_3);
		fls[4] = (FrameLayout) findViewById(R.id.video_type_fl_4);
		fls[5] = (FrameLayout) findViewById(R.id.video_type_fl_6);
		fls[6] = (FrameLayout) findViewById(R.id.video_type_fl_8);

		// fls[0].setFocusable(false);

		posts[0] = (ImageView) findViewById(R.id.video_type_post_0);
		posts[1] = (ImageView) findViewById(R.id.video_type_post_1);
		posts[2] = (ImageView) findViewById(R.id.video_type_post_2);

		// posts[0].setFocusable(false);

		typeLogs[0] = (ImageView) findViewById(R.id.video_type_log_0);
		typeLogs[1] = (ImageView) findViewById(R.id.video_type_log_1);
		typeLogs[2] = (ImageView) findViewById(R.id.video_type_log_2);
		typeLogs[3] = (ImageView) findViewById(R.id.video_type_log_3);
		typeLogs[4] = (ImageView) findViewById(R.id.video_type_log_4);
		typeLogs[5] = (ImageView) findViewById(R.id.video_type_log_6);
		typeLogs[6] = (ImageView) findViewById(R.id.video_type_log_8);

		// typeLogs[0].setFocusable(false);

		backGrounds[0] = (ImageView) findViewById(R.id.video_type_bg_0);
		backGrounds[1] = (ImageView) findViewById(R.id.video_type_bg_1);
		backGrounds[2] = (ImageView) findViewById(R.id.video_type_bg_2);
		backGrounds[3] = (ImageView) findViewById(R.id.video_type_bg_3);
		backGrounds[4] = (ImageView) findViewById(R.id.video_type_bg_4);
		backGrounds[5] = (ImageView) findViewById(R.id.video_type_bg_6);
		backGrounds[6] = (ImageView) findViewById(R.id.video_type_bg_8);

		titletv[0] = (TextView) findViewById(R.id.latest_recommend_text_0);
		titletv[1] = (TextView) findViewById(R.id.latest_recommend_text_1);
		titletv[2] = (TextView) findViewById(R.id.latest_recommend_text_2);
		titletv[3] = (TextView) findViewById(R.id.latest_recommend_text_3);
		titletv[4] = (TextView) findViewById(R.id.latest_recommend_text_4);
		titletv[5] = (TextView) findViewById(R.id.latest_recommend_text_5);
		titletv[6] = (TextView) findViewById(R.id.latest_recommend_text_6);

		// backGrounds[0].setFocusable(false);

		whiteBorder = (ImageView) findViewById(R.id.white_boder);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(292, 445);
		params.leftMargin = 101;
		params.topMargin = 0;
		whiteBorder.setLayoutParams(params);

		int size = bgSelector.length;

		int[] newRandomArray = CommonTools.getRandomList(bgSelector);
		for (int i = 0; i < 7; i++) {
			typeLogs[i].setOnClickListener(this);
			// typeLogs[i].setOnFocusChangeListener(this);
			backGrounds[i].setVisibility(View.GONE);
			titletv[i].setVisibility(View.GONE);

			if (i == 0 || i == 3 || i == 4) {

			} else {
				typeLogs[i].setBackgroundResource(newRandomArray[i]);
			}
		}

		initListener();
	}

	// 第一次进入
	public boolean isFirst = true;

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// if (!isFirst) {
		switch (v.getId()) {
		case R.id.video_type_log_0:

			if (hasFocus) {
				this.scrollView.smoothScrollTo(0, 0);
				showOnFocusAnimation(0);

				whiteBorder.getLayoutParams().width = 292;
				whiteBorder.getLayoutParams().height = 445;

				flyWhiteBorder(292, 445, 101.0F, 0.0F);
			} else {
				showLooseFocusAinimation(0);
				this.whiteBorder.setVisibility(View.INVISIBLE);
			}

			break;
		case R.id.video_type_log_1:
			if (hasFocus) {
				this.scrollView.smoothScrollTo(0, 0);
				showOnFocusAnimation(1);

				whiteBorder.getLayoutParams().width = 220;
				whiteBorder.getLayoutParams().height = 220;

				flyWhiteBorder(220, 220, 374.0F, 10.0F);
			} else {
				showLooseFocusAinimation(1);
				this.whiteBorder.setVisibility(View.INVISIBLE);
			}
			break;
		case R.id.video_type_log_2:
			if (hasFocus) {
				this.scrollView.smoothScrollTo(0, 0);
				showOnFocusAnimation(2);
				whiteBorder.getLayoutParams().width = 220;
				whiteBorder.getLayoutParams().height = 220;

				flyWhiteBorder(220, 220, 579.0F, 10.0F);
			} else {
				showLooseFocusAinimation(2);
				this.whiteBorder.setVisibility(View.INVISIBLE);
			}
			break;
		case R.id.video_type_log_3:
			if (hasFocus) {
				showOnFocusAnimation(3);
				whiteBorder.getLayoutParams().width = 448;
				whiteBorder.getLayoutParams().height = 220;

				flyWhiteBorder(220, 220, 364.0F, 215.0F);
			} else {
				showLooseFocusAinimation(3);
				this.whiteBorder.setVisibility(View.INVISIBLE);
			}
			break;
		case R.id.video_type_log_4:
			if (hasFocus) {
				showOnFocusAnimation(4);
				whiteBorder.getLayoutParams().width = 300;
				whiteBorder.getLayoutParams().height = 445;
				flyWhiteBorder(300, 445, 780.0F, 0.0F);

			} else {
				showLooseFocusAinimation(4);
				this.whiteBorder.setVisibility(View.INVISIBLE);
			}
			break;
		case R.id.video_type_log_6:
			if (hasFocus) {
				this.scrollView.smoothScrollTo(1432, 0);
				showOnFocusAnimation(5);
				whiteBorder.getLayoutParams().width = 220;
				whiteBorder.getLayoutParams().height = 220;

				flyWhiteBorder(220, 220, 1063.0F, 10.0F);
			} else {
				showLooseFocusAinimation(5);
				this.whiteBorder.setVisibility(View.INVISIBLE);
			}
			break;
		case R.id.video_type_log_8:
			if (hasFocus) {
				this.scrollView.smoothScrollTo(1432, 0);
				showOnFocusAnimation(6);
				whiteBorder.getLayoutParams().width = 220;
				whiteBorder.getLayoutParams().height = 220;

				flyWhiteBorder(220, 220, 1063.0F, 215.0F);
			} else {
				showLooseFocusAinimation(6);
				this.whiteBorder.setVisibility(View.INVISIBLE);
			}
			break;
		}

		// } else {
		// isFirst = false;
		// }

	}

	String action = "";

	int position = 0;
	int catId;
	String contentid;
	String url = "";
	String shortTitle;
	String title = "";
	String type = "";
	String grade = "";
	String versions = "";
	String subject = "";
	String childLock;
	String imgUrl;

	@Override
	public void onClick(View v) {

		if (null != list && list.size() > 0) {
			switch (v.getId()) {
			case R.id.video_type_log_0:
				position = 0;
				break;
			case R.id.video_type_log_1:
				position = 1;
				break;
			case R.id.video_type_log_2:
				position = 2;
				break;
			case R.id.video_type_log_3:
				position = 3;
				break;
			case R.id.video_type_log_4:
				position = 4;
				break;
			case R.id.video_type_log_6:
				position = 5;
				break;
			case R.id.video_type_log_8:
				position = 6;
				break;
			}

			action = list.get(position).action;
			catId = list.get(position).catId;
			contentid = list.get(position).contentId;
			url = list.get(position).url;
			title = list.get(position).title;
			shortTitle = list.get(position).shortTilte;
			type = list.get(position).type;
			grade = list.get(position).grade;
			versions = list.get(position).versions;
			subject = list.get(position).subject;
			childLock = list.get(position).childLock;
			imgUrl = list.get(position).imageUrl;

			if ("0".equals(childLock)) {
				goToNext();
			} else if ("1".equals(childLock)) {
				if (HomeConstants.HAS_LOCK.equals(HomeConstants.isVersionLock)
						&& HomeConstants.HAS_LOCK
								.equals(HomeConstants.isPrimaryLock)
						&& HomeConstants.HAS_LOCK
								.equals(HomeConstants.isTempLock)){
					// 弹出保护框
					PwdcheckWindow view = new PwdcheckWindow(mContext,
							instance, pwdcallback);
				} else {
					goToNext();
				}

			}

		} else {
			toast.setIcon(R.drawable.toast_err);
			toast.setText("无内容");
			toast.show();
		}

	}

	PwdCheckCallBack pwdcallback = new PwdCheckCallBack() {

		@Override
		public void check(String str) {
			if ("1".equals(str)) {
				// 直接进入
				goToNext();
			}
		}

	};

	@Override
	public void loadData() {
		for (int i = 0; i < 4; i++) {
			VideoInfo localVideoInfo = list.get(i);
			// posts[i].setImageResource(localVideoInfo.img);

			// HomeActivity.fb.display(posts[i], localVideoInfo.imageUrl);

			// BitmapTask task = new BitmapTask(posts[i], callback);
			// task.execute(localVideoInfo.imageUrl);

			new BitmapWorkerTask(mContext, posts[i], i, true, true,
					new com.hlj.net.BitmapWorkerTask.NewImageCallBack() {

						@Override
						public void post(Bitmap bitmap, int i) {

							if (i == 0) {
								Bitmap reflectionBitmap = ImageUtils.createCutReflectedImage(
										ImageUtils.convertViewToBitmap(fls[0]),
										0);
								refImg[i].setImageBitmap(reflectionBitmap);
							} else if (i == 1) {
								Bitmap reflectionBitmap2 = ImageUtils.createCutReflectedImage(
										ImageUtils.convertViewToBitmap(fls[3]),
										0);
								refImg[i].setImageBitmap(reflectionBitmap2);
							} else if (i == 2) {
								Bitmap reflectionBitmap3 = ImageUtils.createCutReflectedImage(
										ImageUtils.convertViewToBitmap(fls[4]),
										0);
								refImg[i].setImageBitmap(reflectionBitmap3);
							} else if (i == 3) {
								Bitmap reflectionBitmap4 = ImageUtils.createCutReflectedImage(
										ImageUtils.convertViewToBitmap(fls[6]),
										0);
								refImg[i].setImageBitmap(reflectionBitmap4);

							}
						}
					}).execute(localVideoInfo.imageUrl);

			// handler.sendEmptyMessageDelayed(0, 2*1000);

			// HomeActivity.imageLoader.displayImage(localVideoInfo.imageUrl,posts[i]);

		}

	}

	Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			handlerReflectionBitmap();
		};
	};

	private void handlerReflectionBitmap() {
		Bitmap reflectionBitmap = ImageUtils.createCutReflectedImage(
				ImageUtils.convertViewToBitmap(fls[0]), 0);
		refImg[0].setImageBitmap(reflectionBitmap);

		Bitmap reflectionBitmap2 = ImageUtils.createCutReflectedImage(
				ImageUtils.convertViewToBitmap(fls[3]), 0);
		refImg[1].setImageBitmap(reflectionBitmap2);

		Bitmap reflectionBitmap3 = ImageUtils.createCutReflectedImage(
				ImageUtils.convertViewToBitmap(fls[4]), 0);
		refImg[2].setImageBitmap(reflectionBitmap3);

		Bitmap reflectionBitmap4 = ImageUtils.createCutReflectedImage(
				ImageUtils.convertViewToBitmap(fls[6]), 0);
		refImg[3].setImageBitmap(reflectionBitmap4);

		// invalidate();
	}

	PostCallBack callback = new PostCallBack() {

		@Override
		public void post(Bitmap paramBitmap) {
			handlerReflectionBitmap();
		}
	};

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
				if (!StringTools.isNullOrEmpty(list.get(i).title)) {
					// titletv[i].setVisibility(View.VISIBLE);
					titletv[i].setText(list.get(i).title);
				} else {
					titletv[i].setVisibility(View.GONE);
				}

				if (i == 0) {
					new BitmapWorkerTask(mContext, posts[0], true, true,
							new ImageCallBack() {

								@Override
								public void post(Bitmap bitmap) {
									Bitmap reflectionBitmap = ImageUtils.createCutReflectedImage(
											ImageUtils
													.convertViewToBitmap(fls[0]),
											0);
									refImg[0].setImageBitmap(reflectionBitmap);

								}
							}).execute(info.imageUrl);

					// imageLoader.displayImage(info.imageUrl, posts[0]);
					// HomeActivity.fb.display(posts[0], info.imageUrl);
				} else if (i == 3) {
					// imageLoader.displayImage(info.imageUrl, posts[1]);
					new BitmapWorkerTask(mContext, posts[1], true, true,
							new ImageCallBack() {

								@Override
								public void post(Bitmap bitmap) {
									Bitmap reflectionBitmap1 = ImageUtils.createCutReflectedImage(
											ImageUtils
													.convertViewToBitmap(fls[3]),
											0);
									refImg[1].setImageBitmap(reflectionBitmap1);

								}
							}).execute(info.imageUrl);

					// HomeActivity.fb.display(posts[1], info.imageUrl);
				} else if (i == 4) {
					// imageLoader.displayImage(info.imageUrl, posts[2]);
					new BitmapWorkerTask(mContext, posts[2], true, true,
							new ImageCallBack() {

								@Override
								public void post(Bitmap bitmap) {
									Bitmap reflectionBitmap3 = ImageUtils.createCutReflectedImage(
											ImageUtils
													.convertViewToBitmap(fls[4]),
											0);
									refImg[2].setImageBitmap(reflectionBitmap3);
								}
							}).execute(info.imageUrl);
					// HomeActivity.fb.display(posts[2], info.imageUrl);
				}

				else if (i == 1) {
					new BitmapWorkerTask(mContext, typeLogs[i], true, true,
							null).execute(info.imageUrl);
					// imageLoader.displayImage(info.imageUrl, typeLogs[i]);
					// HomeActivity.fb.display(posts[i], info.imageUrl);
				} else if (i == 2) {
					new BitmapWorkerTask(mContext, typeLogs[i], true, true,
							null).execute(info.imageUrl);
					// imageLoader.displayImage(info.imageUrl, typeLogs[i]);
					// HomeActivity.fb.display(posts[i], info.imageUrl);
				}

				else if (i == 5) {
					new BitmapWorkerTask(mContext, typeLogs[i], true, true,
							null).execute(info.imageUrl);
					// imageLoader.displayImage(info.imageUrl, typeLogs[i]);
					// HomeActivity.fb.display(typeLogs[i], info.imageUrl);
				} else if (i == 6) {
					new BitmapWorkerTask(mContext, typeLogs[i], true, true,
							new ImageCallBack() {

								@Override
								public void post(Bitmap bitmap) {
									Bitmap reflectionBitmap4 = ImageUtils.createCutReflectedImage(
											ImageUtils
													.convertViewToBitmap(fls[6]),
											0);
									refImg[3].setImageBitmap(reflectionBitmap4);
								}
							}).execute(info.imageUrl);
					// imageLoader.displayImage(info.imageUrl, typeLogs[i]);
					// HomeActivity.fb.display(typeLogs[i], info.imageUrl);
				}
			}

			// loadData();
		}

	}

	private void goToNext() {
		if ("list".equals(action)) {

			if ("study".equals(type)) {
				Intent it = new Intent(mContext, StudyTypeDetailsActivity.class);
				it.putExtra("catId", catId);
				it.putExtra("url", url);
				it.putExtra("title", title);
				it.putExtra("shortTitle", shortTitle);
				it.putExtra("grade", grade);
				it.putExtra("subject", subject);
				it.putExtra("versions", versions);
				mContext.startActivity(it);
			} else {
				Intent it = new Intent(mContext,
						RecomdTypeDetailsActivity.class);
				it.putExtra("catId", catId);
				it.putExtra("url", url);
				it.putExtra("title", title);
				it.putExtra("shortTitle", shortTitle);
				it.putExtra("action", action);
				mContext.startActivity(it);
			}

		} else if ("info".equals(action)) {
			if ("study".equals(type)) {
				Intent it = new Intent(mContext,
						StudyVideoDetailsActivity.class);
				it.putExtra("catId", catId);
				it.putExtra("contentid", contentid);
				it.putExtra("url", url);
				it.putExtra("grade", grade);
				it.putExtra("subject", subject);
				it.putExtra("versions", versions);
				it.putExtra("imgUrl", imgUrl);
				mContext.startActivity(it);
			} else {
				Intent it = new Intent(mContext,
						RecomdVideoDetailsActivity.class);
				it.putExtra("catId", catId);
				it.putExtra("url", url);
				it.putExtra("contentid", contentid);
				mContext.startActivity(it);
			}
		}
	}

}
