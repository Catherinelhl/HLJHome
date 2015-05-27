package com.hlj.widget;

import java.util.ArrayList;
import com.hlj.HomeActivity.R;
import com.hlj.view.VideoInfo;
import android.app.Dialog;
import android.content.Context;

/**
 * 自定义选集Dialog
 * 
 * @author hyc
 * 
 */
public class ChooseSetDialog extends Dialog {

	private Context mContext;
	private boolean isStudy;

	private int currentSet;

	ArrayList<VideoInfo> list;

	public ChooseSetDialog(Context context, ArrayList<VideoInfo> list,
			boolean isStudy, int currentSet) {
		super(context, R.style.MyDialog);
		mContext = context;
		this.isStudy = isStudy;
		this.currentSet = currentSet;
		this.list = list;
	}

}
