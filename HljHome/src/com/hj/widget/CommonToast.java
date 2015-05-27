package com.hj.widget;

import com.hlj.HomeActivity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 自定义Toast
 * 
 * @author huangyuchao
 * 
 */
public class CommonToast extends Toast {

	private Context mContext;
	private ImageView img;
	private TextView tv;

	public CommonToast(Context context) {
		super(context);
		mContext = context;

		View localView = LayoutInflater.from(context).inflate(
				R.layout.itv_toast, null);
		this.img = ((ImageView) localView.findViewById(R.id.itv_toast_img));
		this.tv = ((TextView) localView.findViewById(R.id.itv_toast_text));
		setView(localView);
	}

	public void setIcon(int paramInt) {
		this.img.setImageResource(paramInt);
	}

	public void setText(int paramInt) {
		this.tv.setText(paramInt);
	}

	public void setText(String text) {
		this.tv.setText(text);
	}
	
	@Override
	public void setDuration(int duration) {
		super.setDuration(duration);
	}

}
