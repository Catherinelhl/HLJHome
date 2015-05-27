package com.hlj.settings;

import com.hlj.HomeActivity.R;
import com.hlj.utils.StringTools;

import android.content.Context;
import android.os.SystemProperties;
import android.preference.EditTextPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MiniPrefrence extends EditTextPreference {

	public MiniPrefrence(Context context, AttributeSet attrs) {
		super(context, attrs);
		setDialogLayoutResource(R.layout.mini_edit);
	}

	private EditText editText;

	private TextView old_tv;
	private EditText new_tv;

	String result = SystemProperties.get("persist.sys.device_name", "MiniMax");

	@Override
	protected void onBindDialogView(View view) {

		editText = getEditText();

		old_tv = (TextView) view.findViewById(R.id.old_tv);
		new_tv = (EditText) view.findViewById(R.id.new_tv);

		old_tv.setText(result);

		super.onBindDialogView(view);
	}

	@Override
	protected void onDialogClosed(boolean positiveResult) {

		if (positiveResult) {
			if (!StringTools.isNullOrEmpty(new_tv.getText().toString())) {
				result = new_tv.getText().toString();
				putSysString(result);
			}

		}

		super.onDialogClosed(positiveResult);
	}

	public void putSysString(String result) {
		System.out.println("result:" + result);
		SystemProperties.set("persist.sys.device_name", result);
	}

}
