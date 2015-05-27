package com.hlj.view;

import java.util.Map;

import com.hlj.HomeActivity.FavVideoActivity;
import com.hlj.HomeActivity.R;
import com.hlj.HomeActivity.StudyLearnActivity;
import com.hlj.adapter.MovieAdapter;
import com.hlj.utils.ImageUtils;
import com.hlj.utils.Logger;
import com.hlj.widget.PwdcheckWindow;
import com.hlj.widget.PwdcheckWindow.PwdCheckCallBack;
import com.live.video.constans.HomeConstants;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;

/**
 * 收藏的线性布局
 * 
 * @author hyc
 * 
 */
public class MovieLayout extends LinearLayout {

	private MovieAdapter adapter;
	private Context mContext;

	ScaleAnimEffect effect;

	public MovieLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		this.effect = new ScaleAnimEffect();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		// super.onDraw(canvas);
	}

	static int position;

	public void setAdapter(MovieAdapter adapter) {
		this.adapter = adapter;
		Logger.log("count:" + adapter.getCount());

		for (int i = 0; i < adapter.getCount(); i++) {
			final Map<String, Object> info = (Map<String, Object>) adapter
					.getItem(i);
			final View view = adapter.getView(i, null, null);
			view.setPadding(10, 10, 10, 10);
			view.setTag(i);
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					// Toast.makeText(context, "您点击了"+view.getTag(),
					// Toast.LENGTH_SHORT).show();
					position = Integer.parseInt(view.getTag().toString());
					Logger.log("position:" + position);

					Intent it = new Intent(mContext, FavVideoActivity.class);
					it.putExtra("url", String.valueOf(info.get("url")));
					it.putExtra("catId",
							Integer.valueOf(String.valueOf(info.get("catId"))));
					it.putExtra("requestType", String.valueOf(info.get("type")));

					it.putExtra("type", String.valueOf(info.get("title")));

					if ("0".equals(info.get("childLock"))) {
						mContext.startActivity(it);
					} else {
						if (HomeConstants.HAS_LOCK.equals(HomeConstants.isVersionLock)
								&& HomeConstants.HAS_LOCK
										.equals(HomeConstants.isPrimaryLock)
								&& HomeConstants.HAS_LOCK
										.equals(HomeConstants.isTempLock)){
							// 弹出保护框
							PwdcheckWindow view = new PwdcheckWindow(mContext,
									v, pwdcallback);
						} else {
							mContext.startActivity(it);
						}
					}

				}
			});
			this.setOrientation(HORIZONTAL);
			this.setGravity(Gravity.CENTER_HORIZONTAL);
			this.addView(view, new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

			final ImageViewBorder imageView = (ImageViewBorder) view
					.findViewById(R.id.movie_image);

			view.setOnFocusChangeListener(new OnFocusChangeListener() {

				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					if (hasFocus) {
						// imageView.isShowBorder = true;
						// 获得焦点变大
						showOnFocusAnimation(v);
					} else {
						// imageView.isShowBorder = false;
						// 回到之前
						showLooseFocusAinimation(v);
					}

				}
			});

		}

	}

	public void showOnFocusAnimation(View v) {
		this.effect.setAttributs(1.0F, 1.1F, 1.0F, 1.1F, 100L);
		Animation localAnimation = this.effect.createAnimation();
		localAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

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
		// v.bringToFront();
		v.startAnimation(localAnimation);
	}

	public void showLooseFocusAinimation(View v) {
		this.effect.setAttributs(1.1F, 1.0F, 1.1F, 1.0F, 100L);
		v.startAnimation(this.effect.createAnimation());
	}

	PwdCheckCallBack pwdcallback = new PwdCheckCallBack() {

		@Override
		public void check(String str) {

			if ("1".equals(str)) {
				Map<String, Object> info = (Map<String, Object>) adapter
						.getItem(position);
				Intent it = new Intent(mContext, FavVideoActivity.class);
				it.putExtra("url", String.valueOf(info.get("url")));
				it.putExtra("catId",
						Integer.valueOf(String.valueOf(info.get("catId"))));
				it.putExtra("requestType", String.valueOf(info.get("type")));

				it.putExtra("type", String.valueOf(info.get("title")));

				mContext.startActivity(it);
				// mContext.startActivityForResult(it,0);
			}
		}

	};

}