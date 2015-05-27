package com.hlj.widget;

import com.hj.widget.CommonToast;
import com.hlj.HomeActivity.BeforePreActivity;
//import com.hlj.HomeActivity.PreActivity;
import com.hlj.HomeActivity.R;
import com.hlj.HomeActivity.SpectialHomeActivity;
import com.hlj.net.HttpHomeLoadDataTask;
import com.hlj.utils.CommonTools;
import com.live.video.net.SetGradeRequest;
import com.live.video.net.callback.IUpdateData;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 学年设置弹出框
 * 
 * @author hyc
 * 
 */
public class GradeSetPopWindow extends View {

	private View view;
	private Context mContext;

	private View parentView;

	public GradeSetPopWindow(Context context, View parentView) {
		super(context);
		this.mContext = context;
		this.parentView = parentView;
		initView();

	}

	public GradeSetPopWindow(Context context, AttributeSet attrs,
			View parentView) {
		super(context, attrs);
		this.mContext = context;
		this.parentView = parentView;
		initView();

	}

	TextView pwd_tv_content;
	private PopupWindow popupWindow;

	private Button confirm, cancel;

	private void initView() {
		view = LayoutInflater.from(mContext).inflate(R.layout.pwdcheck_layout,
				null);
		pwd_tv_content = (TextView) view.findViewById(R.id.pwd_tv_content);
		confirm = (Button) view.findViewById(R.id.unlock);
		cancel = (Button) view.findViewById(R.id.exit);
		popupWindow = new PopupWindow(view);

		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		// 设置焦点
		popupWindow.setFocusable(true);
		// 设置点击其他地方 就消失
		popupWindow.setOutsideTouchable(true);

		popupWindow.showAtLocation(parentView, Gravity.CENTER,
				CommonTools.getScreenWidth(mContext),
				CommonTools.getScreenHeight(mContext));
		popupWindow.update(0, 0, 400, 230);

		confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
				setGrade(typeId);
			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
			}
		});
	}

	public void settvContent(String s) {
		pwd_tv_content.setText(s);
	}

	public void setConfirmBtn(String s) {
		confirm.setText(s);
	}

	public void setCancelBtn(String s) {
		cancel.setText(s);
	}

	String typeId;

	public void setTypeId(String typeId) {
		this.typeId = typeId;
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
			commonToast.setText("更换成功,请稍等......");
			commonToast.show();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// 重启
			rebootApp();
		}

		@Override
		public void handleErrorData(String info) {
			commonToast = new CommonToast(mContext);
			commonToast.setIcon(R.drawable.toast_err);
			commonToast.setText("更换失败 ");
			commonToast.show();
		}

	};

	/**
	 * 重启指定的应用
	 */
	private void rebootApp() {
		SpectialHomeActivity.instance.finish();
		Intent i = mContext.getPackageManager().getLaunchIntentForPackage(
				"com.hlj.HomeActivity");// 获取启动的包名
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		// PreActivity.isRestart = true;
		BeforePreActivity.isRestart = true;
		mContext.startActivity(i);
	}

}
