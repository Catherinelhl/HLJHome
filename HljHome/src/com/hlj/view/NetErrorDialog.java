package com.hlj.view;

import com.hlj.HomeActivity.BaseActivity;
import com.hlj.HomeActivity.BeforePreActivity;
import com.hlj.HomeActivity.R;
import com.hlj.utils.Logger;

import android.R.anim;
import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 网络错误
 * 
 * @author office
 * 
 */
public class NetErrorDialog extends Dialog {

	Context mContext;
	private TextView mtext;

	private String str;
	private int coun;

	public NetErrorDialog(Context context, String string, int i) {
		super(context, R.style.CustomDialog);
		mContext = context;
		str = string;
		coun = i;

		setCancelable(true);
		setCanceledOnTouchOutside(false);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
						| WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		initView();
	}

	private View initView() {
		View contentView = LayoutInflater.from(mContext).inflate(
				R.layout.hlj_neterror_dialog, null);

		LinearLayout retryBen = (LinearLayout) contentView
				.findViewById(R.id.itv_exit_ok);
		mtext = (TextView) contentView.findViewById(R.id.wrong_title);
		mtext.setText(str + "");
		retryBen.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				String sdkVersion = android.os.Build.VERSION.SDK;
				Logger.log("sdkVersion:" + sdkVersion);
				Intent intent;
				if (Integer.valueOf(sdkVersion) > 10) {
//					if (coun == 1) {
//						BeforePreActivity.instance.finish();
//
//					} else {
//						BaseActivity.dialog.cancel();
//					}
					dismiss();
					intent = new Intent(
							android.provider.Settings.ACTION_SETTINGS);
					mContext.startActivity(intent);

				} else {
					// if (coun == 1) {
					// BeforePreActivity.instance.finish();
					// } else {
					// BaseActivity.dialog.cancel();
					// }
					dismiss();
					intent = new Intent();
					ComponentName comp = new ComponentName(
							"com.android.settings",
							"com.android.settings.Settings");
					intent.setComponent(comp);
					intent.setAction("android.intent.action.VIEW");
					mContext.startActivity(intent);

				}

			}

		});

		LinearLayout cancleBtn = (LinearLayout) contentView
				.findViewById(R.id.itv_exit_cancle);
		cancleBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (coun == 1) {
					// cou += 1;
					// if (cou > 1) {
					//
					// } else {
					BeforePreActivity.instance.finish();
					// }
					// BeforePreActivity.instance.notify();
				} else {
					BaseActivity.dialog.cancel();
				}
				// Intent it = new Intent(mContext, MinHomeActivity.class);
				dismiss();
				Intent it = new Intent(mContext, BeforePreActivity.class);
				mContext.startActivity(it);

			}
		});
		setContentView(contentView);
		return contentView;
	}

	int cou = 0;
}
