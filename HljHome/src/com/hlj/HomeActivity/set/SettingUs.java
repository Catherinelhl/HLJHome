package com.hlj.HomeActivity.set;

import java.util.HashMap;

import org.json.JSONObject;

import com.hlj.HomeActivity.R;
import com.hlj.net.GetUserInfoRequest;
import com.hlj.net.HttpHomeLoadDataTask;
import com.hlj.utils.ConnectUrl;
import com.hlj.utils.DeviceCheck;
import com.hlj.utils.GsonTools;
import com.hlj.utils.JsonTools;
import com.hlj.utils.Logger;
import com.live.video.constans.UserInfo;
import com.live.video.net.callback.IUpdateData;
import com.live.video.network.FinalTask;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.view.ViewStub;

public class SettingUs extends Activity {
	private TextView setName1, setName2, userId, userName, userPoint;
	private ImageView setItemLog, portrait, warm_user_image;
	private Button back;
	private FinalTask finalTask;
	private HashMap map = new HashMap();
	private UserInfo userInfo = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_main);
		((ViewStub) findViewById(R.id.set_login_regest)).inflate();
		initView();
	}

	private void initView() {
		setName1 = (TextView) findViewById(R.id.set_name1);
		setName2 = (TextView) findViewById(R.id.set_name2);
		setItemLog = (ImageView) findViewById(R.id.set_item_log);
		setName1.setText("会员管理");
		setName2.setText("会员管理");
		setItemLog.setImageResource(R.drawable.reg_setup);
		portrait = (ImageView) findViewById(R.id.portrait);
		warm_user_image = (ImageView) findViewById(R.id.warm_user_image);
		userId = (TextView) findViewById(R.id.user_id);
		userName = (TextView) findViewById(R.id.user_name);
		userPoint = (TextView) findViewById(R.id.user_point);
		back = (Button) findViewById(R.id.setting_user_back);
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		/*
		 * userId.setText("id"); userName.setText("name");
		 * userPoint.setText("point");
		 */
		initData();
	}

	private void initData() {
		finalTask = new FinalTask(this);
		map.put("deviceUsersUniqueId", DeviceCheck.shareDeviceUsersUniqueId());
		String data = GsonTools.parseToJson("getUserInfo", "1.0", "RAW", map);
		Logger.log("data:" + data);

		GetUserInfoRequest request = new GetUserInfoRequest(getBaseContext());

		new HttpHomeLoadDataTask(this, callBack, false, "",
				true).execute(request);

		// finalTask.post(data, ConnectUrl.userServer + "getUserInfo.php",
		// false,
		// new FinalTask.GetCallBack() {
		// @Override
		// public void post(String string) {
		// String str = string.toString();
		// userInfo = getUserInfo(str);
		// updateView();
		// /*
		// * userId.setText(userInfo.userID);
		// * userName.setText(userInfo.username);
		// * userPoint.setText(userInfo.point);
		// */
		// }
		// }, new FinalTask.FailureCallBack() {
		// @Override
		// public void failure(String string) {
		// }
		// });
	}

	IUpdateData callBack = new IUpdateData() {

		@Override
		public void updateUi(Object o) {
			String str = o.toString();
			userInfo = getUserInfo(str);
			updateView();

			userId.setText(userInfo.userID);
			userName.setText(userInfo.username);
			userPoint.setText(userInfo.point);

		}

		@Override
		public void handleErrorData(String info) {
			// TODO Auto-generated method stub

		}
	};

	private void updateView() {
		userId.setText(userInfo.userID);
		userName.setText("未设置");
		userPoint.setText(userInfo.point);
	}

	private UserInfo getUserInfo(String data) {
		UserInfo useInfo = null;
		if (data != null) {
			useInfo = new UserInfo();
			try {
				JSONObject obj = new JSONObject(data);
				JSONObject object = obj.getJSONObject("userInfo");
				useInfo.userID = object.getString("userid");
				useInfo.username = object.getString("username");
				useInfo.point = object.getString("point");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return useInfo;
	}
}