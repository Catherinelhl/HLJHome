package com.hlj.HomeActivity.set;

import java.util.ArrayList;

import io.vov.vitamio.activity.InitActivity;

import com.hj.widget.CommonToast;
import com.hlj.HomeActivity.R;
import com.hlj.adapter.GradeSetAdapter;
import com.hlj.net.HttpHomeLoadDataTask;
import com.hlj.utils.Logger;
import com.hlj.view.GradeLayout;
import com.live.video.constans.Group;
import com.live.video.net.GetGradeRequest;
import com.live.video.net.GetGradeResponse;
import com.live.video.net.SetGradeRequest;
import com.live.video.net.callback.IUpdateData;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * 学年设置
 * 
 * @author hyc
 * 
 */
public class SettingAcademic extends Activity implements OnClickListener {

	View gradeView;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.setting_main);

		gradeView = ((ViewStub) findViewById(R.id.set_academic)).inflate();

		initView();

		initGrade();

	}

	// ArrayAdapter<String> adapter;
	GradeSetAdapter adapter;

	String setTypeId;
	TextView set_name1, set_name2;
	ImageView set_item_log;

	// GridView recommend_grid;

	GradeLayout gradeLayout;

	private void initView() {
		// recommend_grid = (GridView)
		// gradeView.findViewById(R.id.recommend_grid);

		// adapter = new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_1, groupNameList);
		// recommend_grid.setAdapter(adapter);
		//
		// recommend_grid.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> arg0, View arg1,
		// int position, long arg3) {
		// Group p = groupList.get(position);
		// setTypeId = p.typeId;
		// setGrade();
		// }
		// });

		set_name1 = (TextView) findViewById(R.id.set_name1);
		set_name2 = (TextView) findViewById(R.id.set_name2);
		set_item_log = (ImageView) findViewById(R.id.set_item_log);

		set_name1.setText("版本切换");
		set_name2.setText("版本切换");
		set_item_log.setImageResource(R.drawable.other_setup);

		gradeLayout = (GradeLayout) findViewById(R.id.recommend_grid);
		adapter = new GradeSetAdapter(this);
		gradeLayout.setAdapter(adapter);
	}

	public void initGrade() {
		GetGradeRequest request = new GetGradeRequest(this);
		new HttpHomeLoadDataTask(this, callBack, false, "", false)
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
					//设置背景
					
				}
			}
			adapter.setList(response.list);

			gradeLayout.setAdapter(adapter);
			adapter.notifyDataSetChanged();
		}

		@Override
		public void handleErrorData(String info) {
			// TODO Auto-generated method stub

		}

	};

	@Override
	public void onClick(View arg0) {

	}

	CommonToast commonToast;

	private void setGrade() {
		// setTypeId
		SetGradeRequest request = new SetGradeRequest(this);
		request.typeId = setTypeId;
		new HttpHomeLoadDataTask(this, callBack1, false, "", false)
				.execute(request);
	}

	IUpdateData callBack1 = new IUpdateData() {

		@Override
		public void updateUi(Object o) {

			commonToast = new CommonToast(SettingAcademic.this);
			commonToast.setIcon(R.drawable.toast_smile);
			commonToast.setText("设置成功,请重启相关设备");
			commonToast.show();
		}

		@Override
		public void handleErrorData(String info) {
			commonToast = new CommonToast(SettingAcademic.this);
			commonToast.setIcon(R.drawable.toast_err);
			commonToast.setText("设置失败 ");
			commonToast.show();
		}

	};

}
