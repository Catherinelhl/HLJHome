package com.hlj.HomeActivity.set;

import com.hlj.HomeActivity.R;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.View;
import android.view.ViewStub;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 播放选项
 * 
 * @author huangyuchao
 * 
 */

public class SettingDisPlayer extends PreferenceActivity implements
		OnClickListener {

	// View playView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_display);
		// playView = ((ViewStub) findViewById(R.id.set_display)).inflate();

		initView();

		addPreferencesFromResource(R.xml.display_settings);
	}

	TextView set_name1, set_name2;
	ImageView set_item_log;

	private void initView() {
		set_name1 = (TextView) findViewById(R.id.set_name1);
		set_name2 = (TextView) findViewById(R.id.set_name2);
		set_item_log = (ImageView) findViewById(R.id.set_item_log);
		set_name1.setText("播放首选项");
		set_name2.setText("播放首选项");
		set_item_log.setImageResource(R.drawable.vod_setup);

		// initPlayView();
	}

	LinearLayout server_server1, server_server2, server_server3,
			server_reserver;

	private void initPlayView() {
		server_server1 = (LinearLayout) findViewById(R.id.server_server1);
		server_server1.setOnClickListener(this);
		server_server2 = (LinearLayout) findViewById(R.id.server_server2);
		server_server2.setOnClickListener(this);
		server_server3 = (LinearLayout) findViewById(R.id.server_server3);
		server_server3.setOnClickListener(this);
		server_reserver = (LinearLayout) findViewById(R.id.server_reserver);
		server_reserver.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.server_server1:
			// 亮度

			break;
		case R.id.server_server2:
			// 对比度

			break;
		case R.id.server_server3:
			// 饱和度

			break;
		case R.id.server_reserver:
			// 屏幕切边

			break;
		}

	}

}
