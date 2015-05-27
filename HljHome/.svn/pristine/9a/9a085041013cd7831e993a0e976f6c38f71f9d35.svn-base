package com.hlj.view;

import com.hlj.HomeActivity.R;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * 自定义Dialog
 * 
 * @author huangyuchao
 * 
 */
public class CustomDialog extends Dialog {

	View contentView;
	Context mContext;

	public final static int TAG_SURE = 1;
	public final static int TAG_CANCLE = 2;

	EditText et_old_pass;
	EditText et_new_pass;
	EditText et_pass_confirm;

	public CustomDialog(Context context) {
		this(context, R.style.Theme_Dialog);
	}

	public CustomDialog(Context context, int theme) {
		super(context, theme);
		mContext = context;
		setCancelable(true);
		setCanceledOnTouchOutside(false);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
						| WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
	}

	public View initPassEditDialogView(String btn1Text, String btn2Text,
			View.OnClickListener onClickListener) {
		contentView = LayoutInflater.from(mContext).inflate(
				R.layout.edit_pass_dialog, null);

		et_old_pass = (EditText) contentView.findViewById(R.id.et_old_pass);
		et_new_pass = (EditText) contentView.findViewById(R.id.et_new_pass);
		et_pass_confirm = (EditText) contentView
				.findViewById(R.id.et_pass_confirm);

		Button retryBen = (Button) contentView.findViewById(R.id.retry_btn);
		retryBen.setOnClickListener(onClickListener);
		retryBen.setText(btn2Text);
		retryBen.setTag(TAG_SURE);

		Button cancleBtn = (Button) contentView.findViewById(R.id.exit_btn);
		cancleBtn.setOnClickListener(onClickListener);
		cancleBtn.setText(btn1Text);
		cancleBtn.setTag(TAG_CANCLE);

		return contentView;
	}
}
