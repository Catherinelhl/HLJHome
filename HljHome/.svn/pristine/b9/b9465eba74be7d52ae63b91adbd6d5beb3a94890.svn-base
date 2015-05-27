package com.hlj.view;

import com.hlj.HomeActivity.R;
import com.hlj.adapter.MovieAdapter;
import com.hlj.adapter.SwitchAdapter;
import com.hlj.utils.Logger;
import com.hlj.widget.GradeSetPopWindow;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

/**
 * 版本切换
 * 
 * @author hyc
 * 
 */
public class SwitchLayout extends LinearLayout {

	private SwitchAdapter adapter;
	private Context mContext;

	ScaleAnimEffect effect;

	public SwitchLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		this.effect = new ScaleAnimEffect();
	}

	int position;

	GradeSetPopWindow popupWindow;

	int[] colorArray = new int[] { R.color.color1, R.color.color2,
			R.color.color3, R.color.color4, R.color.color5 };

	public void setAdapter(SwitchAdapter adapter) {
		this.adapter = adapter;
		Logger.log("count:" + adapter.getCount());
		for (int i = 0; i < adapter.getCount(); i++) {
			final VideoInfo info = (VideoInfo) adapter.getItem(i);
			final View view = adapter.getView(i, null, null);
			view.setPadding(10, 10, 10, 10);
			view.setTag(i);
			if (i < 5) {
				view.setBackgroundColor(getResources().getColor(colorArray[i]));
			}
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					position = Integer.parseInt(view.getTag().toString());

					popupWindow = new GradeSetPopWindow(mContext, view);

					popupWindow.settvContent("是否更换为" + info.groupName);
					popupWindow.setConfirmBtn("确定");
					popupWindow.setCancelBtn("取消");

					popupWindow.setTypeId(info.typeId);
				}
			});

			this.setOrientation(HORIZONTAL);
			this.setGravity(Gravity.CENTER_HORIZONTAL);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			lp.setMargins(10, 10, 10, 10);

			if (!"1".equals(info.isDefault)) {
				this.addView(view, lp);
			}

			view.setOnFocusChangeListener(new OnFocusChangeListener() {

				@Override
				public void onFocusChange(View v, boolean arg1) {
					// 切换背景
					if (arg1) {
						// v.setBackgroundResource(R.drawable.grade_set_selector);
						// 放大
						// 获得焦点变大
						showOnFocusAnimation(v);
					} else {
						// v.setBackgroundResource(R.drawable.grade_set_selector);
						// 还原
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
}
