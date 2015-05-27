package com.hlj.view;

import com.hj.widget.CommonToast;
import com.hlj.HomeActivity.BeforePreActivity;
import com.hlj.HomeActivity.FavVideoActivity;
//import com.hlj.HomeActivity.PreActivity;
import com.hlj.HomeActivity.R;
import com.hlj.HomeActivity.StudyLearnActivity;
import com.hlj.HomeActivity.set.SettingAcademic;
import com.hlj.adapter.GradeSetAdapter;
import com.hlj.adapter.MovieAdapter;
import com.hlj.net.HttpHomeLoadDataTask;
import com.hlj.utils.ImageUtils;
import com.hlj.utils.Logger;
import com.hlj.widget.GradeSetPopWindow;
import com.hlj.widget.PwdcheckWindow;
import com.hlj.widget.PwdcheckWindow.PwdCheckCallBack;
import com.live.video.constans.Group;
import com.live.video.constans.HomeConstants;
import com.live.video.net.SetGradeRequest;
import com.live.video.net.callback.IUpdateData;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

/**
 * 学年设置的线性布局
 * 
 * @author hyc
 * 
 */
public class GradeLayout extends LinearLayout {

	private MovieAdapter adapter;
	private Context mContext;

	ScaleAnimEffect effect;

	public GradeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		this.effect = new ScaleAnimEffect();
	}

	int position;

	GradeSetPopWindow popupWindow;

	public void setAdapter(final GradeSetAdapter adapter) {
		Logger.log("count:" + adapter.getCount());
		for (int i = 0; i < adapter.getCount(); i++) {
			final View view = adapter.getView(i, null, null);
			view.setPadding(5, 10, 5, 10);
			view.setTag(i);
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					position = Integer.parseInt(view.getTag().toString());

					// 切换背景
					arg0.setBackgroundResource(R.drawable.grade_set_selector);

					Group group = (Group) adapter.getItem(position);

					// showSetGradeAlert(group.typeId);

					popupWindow = new GradeSetPopWindow(mContext, view);

					popupWindow.settvContent("是否更换为" + group.groupName);
					popupWindow.setConfirmBtn("确定");
					popupWindow.setCancelBtn("取消");

					popupWindow.setTypeId(group.typeId);
				}
			});
			this.setOrientation(HORIZONTAL);
			this.setGravity(Gravity.CENTER_HORIZONTAL);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			lp.setMargins(10, 10, 10, 10);
			this.addView(view, lp);

			view.setOnFocusChangeListener(new OnFocusChangeListener() {

				@Override
				public void onFocusChange(View v, boolean arg1) {
					// 切换背景
					if (arg1) {
						//
						showOnFocusAnimation(v);
						v.setBackgroundResource(R.drawable.grade_set_selector);
					} else {
						//
						showLooseFocusAinimation(v);
						v.setBackgroundResource(R.drawable.grade_set_selector);
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
		v.bringToFront();
		v.startAnimation(localAnimation);
	}

	public void showLooseFocusAinimation(View v) {
		this.effect.setAttributs(1.1F, 1.0F, 1.1F, 1.0F, 100L);
		v.startAnimation(this.effect.createAnimation());
	}

	private void showSetGradeAlert(final String typeId) {
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle("提示");
		builder.setMessage("是否更换EPG");
		// builder.setView(view);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				setGrade(typeId);
			}
		});

		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {

			}
		});

		builder.create();
		builder.show();
	}

	private void setGrade(String typeId) {
		// setTypeId
		SetGradeRequest request = new SetGradeRequest(mContext);
		request.typeId = typeId;
		new HttpHomeLoadDataTask(mContext, callBack1, false, "", false)
				.execute(request);
	}

	CommonToast commonToast;

	IUpdateData callBack1 = new IUpdateData() {

		@Override
		public void updateUi(Object o) {
			commonToast = new CommonToast(mContext);
			commonToast.setIcon(R.drawable.toast_smile);
			commonToast.setText("设置成功,请稍等......");
			commonToast.show();

			// 重启
			rebootApp();
		}

		@Override
		public void handleErrorData(String info) {
			commonToast = new CommonToast(mContext);
			commonToast.setIcon(R.drawable.toast_err);
			commonToast.setText("设置失败 ");
			commonToast.show();
		}

	};

	/**
	 * 重启指定的应用
	 */
	private void rebootApp() {
		Intent i = mContext.getPackageManager().getLaunchIntentForPackage(
				"com.hlj.HomeActivity");// 获取启动的包名
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		// PreActivity.isRestart = true;
		BeforePreActivity.isRestart = true;
		mContext.startActivity(i);
	}

}