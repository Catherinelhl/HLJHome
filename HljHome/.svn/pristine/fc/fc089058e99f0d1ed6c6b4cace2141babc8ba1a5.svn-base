package com.hlj.HomeActivity.set;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.hlj.HomeActivity.R;
import com.live.video.constans.HomeConstants;
import com.live.video.net.HttpRequest;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 服务器选择
 * 
 * @author huangyuchao
 * 
 */
public class SettingServer extends Activity implements OnClickListener {

	View serverView;

	String[] serverArray = new String[4];
	private ArrayList<TextView> status;

	private Runnable downTask;

	private ExecutorService pool = Executors.newFixedThreadPool(2);

	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				status.get(msg.arg1).setText("在线");
				status.get(msg.arg1).setTextColor(
						getResources().getColor(R.color.green));
				break;
			case 1:
				status.get(msg.arg1).setText("休息");
				status.get(msg.arg1).setTextColor(
						getResources().getColor(R.color.detail_point));
				break;
			default:
				break;
			}

		};

	};

	public SettingServer() {
		serverArray[0] = HomeConstants.homePath1;
		serverArray[1] = HomeConstants.homePath2;
		serverArray[2] = HomeConstants.homePath3;
		serverArray[3] = HomeConstants.homePath4;

		status = new ArrayList<TextView>();

		downTask = new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < serverArray.length; i++) {
					if (pingHost(serverArray[i])) {
						Message msg = new Message();
						msg.what = 0;
						msg.arg1 = i;
						handler.sendMessage(msg);
					} else {
						Message msg = new Message();
						msg.what = 1;
						msg.arg1 = i;
						handler.sendMessage(msg);
					}
				}

			}
		};
		pool.execute(downTask);
	}

	private boolean pingHost(String serverAddress) {
		boolean isOnline = HttpRequest.getState(serverAddress);

		return isOnline;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_main);
		serverView = ((ViewStub) findViewById(R.id.set_server_set)).inflate();
		initServerView();
	}

	TextView set_name1, set_name2;
	ImageView set_item_log;

	LinearLayout server_server1, server_server2, server_server3,
			server_reserver;

	ImageView set_choose_log1, set_choose_log2, set_choose_log3,
			set_choose_log4;

	TextView set_item_status1, set_item_status2, set_item_status3,
			set_item_status4;

	private void initServerView() {
		set_name1 = (TextView) findViewById(R.id.set_name1);
		set_name2 = (TextView) findViewById(R.id.set_name2);
		set_item_log = (ImageView) findViewById(R.id.set_item_log);

		set_name1.setText("服务器选择");
		set_name2.setText("服务器选择");
		set_item_log.setImageResource(R.drawable.set_server);

		server_server1 = (LinearLayout) findViewById(R.id.server_server1);
		server_server1.setOnClickListener(this);
		server_server2 = (LinearLayout) findViewById(R.id.server_server2);
		server_server2.setOnClickListener(this);
		server_server3 = (LinearLayout) findViewById(R.id.server_server3);
		server_server3.setOnClickListener(this);
		server_reserver = (LinearLayout) findViewById(R.id.server_reserver);

		set_choose_log1 = (ImageView) findViewById(R.id.set_choose_log1);
		set_choose_log2 = (ImageView) findViewById(R.id.set_choose_log2);
		set_choose_log3 = (ImageView) findViewById(R.id.set_choose_log3);
		set_choose_log4 = (ImageView) findViewById(R.id.set_choose_log4);

		set_item_status1 = (TextView) findViewById(R.id.set_item_status1);
		set_item_status2 = (TextView) findViewById(R.id.set_item_status2);
		set_item_status3 = (TextView) findViewById(R.id.set_item_status3);
		set_item_status4 = (TextView) findViewById(R.id.set_item_status4);

		status.add(set_item_status1);
		status.add(set_item_status2);
		status.add(set_item_status3);
		status.add(set_item_status4);

		int num = getServerAddress();

		if (num == 0) {
			theOnlyCheckedis(R.id.server_server1);
		} else if (num == 1) {
			theOnlyCheckedis(R.id.server_server2);
		} else if (num == 2) {
			theOnlyCheckedis(R.id.server_server3);
		} else if (num == 3) {
			theOnlyCheckedis(R.id.server_reserver);
		}

	}

	private void theOnlyCheckedis(int id) {

		switch (id) {
		case R.id.server_server1:
			set_choose_log1.setVisibility(View.VISIBLE);
			set_choose_log2.setVisibility(View.INVISIBLE);
			set_choose_log3.setVisibility(View.INVISIBLE);
			set_choose_log4.setVisibility(View.INVISIBLE);

			saveServerAddress("SERVER", 0);
			break;
		case R.id.server_server2:
			set_choose_log1.setVisibility(View.INVISIBLE);
			set_choose_log2.setVisibility(View.VISIBLE);
			set_choose_log3.setVisibility(View.INVISIBLE);
			set_choose_log4.setVisibility(View.INVISIBLE);
			saveServerAddress("SERVER", 1);
			break;
		case R.id.server_server3:
			set_choose_log1.setVisibility(View.INVISIBLE);
			set_choose_log2.setVisibility(View.INVISIBLE);
			set_choose_log3.setVisibility(View.VISIBLE);
			set_choose_log4.setVisibility(View.INVISIBLE);
			saveServerAddress("SERVER", 2);
			break;
		default:
			set_choose_log1.setVisibility(View.INVISIBLE);
			set_choose_log2.setVisibility(View.INVISIBLE);
			set_choose_log3.setVisibility(View.INVISIBLE);
			set_choose_log4.setVisibility(View.VISIBLE);
			saveServerAddress("SERVER", 3);
			break;
		}
	}

	/**
	 * 存储
	 * 
	 * @param serverName
	 */
	private void saveServerAddress(String serverName, int i) {
		HomeConstants.homePath=serverArray[i];
		SharedPreferences.Editor editor = getSharedPreferences("settingServer",
				0).edit();
		editor.putString(serverName, serverArray[i]);
		editor.putInt("num", i);
		editor.commit();
	}

	private int getServerAddress() {
		int num = this.getSharedPreferences("settingServer", MODE_PRIVATE)
				.getInt("num", 0);
		return num;
	}

	@Override
	protected void onStart() {
		pool.execute(downTask);
		super.onStart();
	}

	@Override
	public void onClick(View v) {
		theOnlyCheckedis(v.getId());
	}

}
