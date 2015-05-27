package com.hlj.view;

import java.util.ArrayList;

import com.hlj.HomeActivity.R;
import com.hlj.adapter.MovieAdapter;
import com.hlj.adapter.SwitchAdapter;
import com.hlj.net.HttpHomeLoadDataTask;
import com.hlj.utils.Logger;
import com.live.video.constans.Group;
import com.live.video.net.GetGradeRequest;
import com.live.video.net.GetGradeResponse;
import com.live.video.net.callback.IUpdateData;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

/**
 * 版本切换的视图
 * 
 * @author hyc
 * 
 */
public class VersionSwitchLayout extends LinearLayout implements
		LayoutInterface, View.OnFocusChangeListener, View.OnClickListener {

	private Context mContext;

	public VersionSwitchLayout(Context context) {
		super(context);
		this.mContext = context;

		initView();

		initGrade();
	}

	public VersionSwitchLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;

		initView();

		initGrade();
	}

	public void initGrade() {
		GetGradeRequest request = new GetGradeRequest(mContext);
		new HttpHomeLoadDataTask(mContext, callBack, false, "", false)
				.execute(request);
	}

	ArrayList<Group> groupList = new ArrayList<Group>();
	ArrayList<String> groupNameList = new ArrayList<String>();

	IUpdateData callBack = new IUpdateData() {

		@Override
		public void updateUi(Object o) {
			GetGradeResponse response = new GetGradeResponse();
			response.paseRespones(o.toString());

			groupList = response.list;

			for (int i = 0; i < groupList.size(); i++) {
				Group p = groupList.get(i);
				groupNameList.add(p.groupName);
				// 表示当前默认的
				if ("1".equals(p.isDefault)) {
					// recommend_grid.setSelection(i);
					// 设置背景

				}
				VideoInfo info = new VideoInfo();
				info.typeId = p.typeId;
				info.groupName = p.groupName;
				info.isDefault = p.isDefault;

				list.add(info);

			}
			adapter.setList(list);

			switchLayout.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		}

		@Override
		public void handleErrorData(String info) {
			// TODO Auto-generated method stub

		}

	};

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFocusChange(View arg0, boolean arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub

	}

	SwitchLayout switchLayout;

	ImageView play_collect_reflected_img;

	SwitchAdapter adapter;

	ArrayList<VideoInfo> list = new ArrayList<VideoInfo>();

	@Override
	public void initView() {
		setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		setGravity(Gravity.CENTER_HORIZONTAL);

		View view = LayoutInflater.from(mContext).inflate(
				R.layout.version_switch, null);

		switchLayout = (SwitchLayout) view.findViewById(R.id.versionLayout);

		play_collect_reflected_img = (ImageView) view
				.findViewById(R.id.play_collect_reflected_img);

		addView(view);

		adapter = new SwitchAdapter(mContext, list);
		switchLayout.setAdapter(adapter);

	}

	@Override
	public void loadData() {
		Logger.log("-----------loadData--------------");
	}

	@Override
	public void updateData() {
		Logger.log("-----------updateData--------------");
		switchLayout.setAdapter(adapter);
	}

}
