package com.hlj.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hlj.HomeActivity.R;
import com.hlj.utils.CommonTools;
import com.live.video.constans.HomeConstants;

/**
 * Created with IntelliJ IDEA. User: ${shime} Date: 13-11-18 Time: 下午3:44 To
 * change this template use File | Settings | File Templates.
 */
public class PwdcheckWindow extends View {
	private View parentView;
	private View view;
	private Context mContext;
	private PopupWindow popupWindow;
	private PwdPopWindow pwdpopWindow;
	private Button unlock, exit;
	private PwdCheckCallBack pwdCheckCallBack;

	public PwdcheckWindow(Context context, View view,
			PwdCheckCallBack pwdCheckCallBack) {
		super(context);
		this.mContext = context;
		this.parentView = view;
		this.pwdCheckCallBack = pwdCheckCallBack;
		initView();
	}

	public PwdcheckWindow(Context context, View view,
			PwdCheckCallBack pwdCheckCallBack, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		this.parentView = view;
		this.pwdCheckCallBack = pwdCheckCallBack;
		initView();
	}

	public PwdcheckWindow(Context context, View view,
			PwdCheckCallBack pwdCheckCallBack, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
		this.parentView = view;
		this.pwdCheckCallBack = pwdCheckCallBack;
		initView();
	}

	TextView pwd_tv_content;

	private void initView() {
		view = LayoutInflater.from(mContext).inflate(R.layout.pwdcheck_layout,
				null);

		pwd_tv_content = (TextView) view.findViewById(R.id.pwd_tv_content);

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
		unlock = (Button) view.findViewById(R.id.unlock);
		exit = (Button) view.findViewById(R.id.exit);
		unlock.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
				pwdpopWindow = new PwdPopWindow(mContext, v, callback);
			}
		});
		exit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
			}
		});
	}

	public void settvContent(String s) {
		pwd_tv_content.setText(s);
	}

	PwdPopWindow.PwdCallBack callback = new PwdPopWindow.PwdCallBack() {
		@Override
		public void call(String str) {
			pwdCheckCallBack.check(str);
		}
	};

	public static abstract interface PwdCheckCallBack {
		public abstract void check(String str);
	}

}
