package com.hlj.HomeActivity.set;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.view.*;
import android.widget.*;

import com.hj.widget.CommonToast;
import com.hlj.HomeActivity.BeforePreActivity;
import com.hlj.HomeActivity.FavVideoActivity;
//import com.hlj.HomeActivity.PreActivity;
import com.hlj.HomeActivity.R;
import com.hlj.HomeActivity.SpectialHomeActivity;
import com.hlj.HomeActivity.StudyLearnActivity;
import com.hlj.net.HttpHomeLoadDataTask;
import com.hlj.utils.*;
import com.hlj.widget.PwdcheckWindow;
import com.hlj.widget.PwdcheckWindow.PwdCheckCallBack;
import com.live.video.constans.HomeConstants;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.os.Bundle;
import android.view.View.OnClickListener;

import com.live.video.net.GetParentalControlRequest;
import com.live.video.net.GetParentalControlResponse;
import com.live.video.net.callback.IUpdateData;
import com.live.video.network.FinalTask;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

/**
 * 缓存清除(其它设置)
 * 
 * @author huangyuchao
 * 
 */
public class SettingClear extends Activity implements OnClickListener {

	View cacheView;
	private GetParentalControlResponse.ControlData controlData;
	CommonToast toast = null;
	private EditText newPwd, oldPwd, secNewPwd;
	// 设置密码view
	private View pwdSetting;
	private PopupWindow pwdSettingPop;
	private Button confirmPwd, cancelPwd;

	private View view;
	public String pwd;
	public String userPasswdNew1, userPasswdNew2, userPasswdOld;

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				newPwd.setText("");
				oldPwd.setText("");
				secNewPwd.setText("");
				pwdSettingPop.dismiss();
				break;
			case 1:
				newPwd.setText("");
				oldPwd.setText("");
				secNewPwd.setText("");
				break;
			}
			super.handleMessage(msg); // To change body of overridden methods
										// use File | Settings | File Templates.
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.setting_main);
		cacheView = ((ViewStub) findViewById(R.id.set_clearcache)).inflate();

		toast = new CommonToast(this);

		pwdSetting = LayoutInflater.from(this).inflate(
				R.layout.parental_control, null);
		pwdSettingPop = new PopupWindow(pwdSetting);

		newPwd = (EditText) pwdSetting.findViewById(R.id.parent_new_pwd);
		oldPwd = (EditText) pwdSetting.findViewById(R.id.parent_old_pwd);
		secNewPwd = (EditText) pwdSetting.findViewById(R.id.parent_new_sec_pwd);
		confirmPwd = (Button) pwdSetting.findViewById(R.id.parent_confirm);
		cancelPwd = (Button) pwdSetting.findViewById(R.id.parent_cancel);
		// 必须设置背景
		pwdSettingPop.setBackgroundDrawable(new BitmapDrawable());
		// 设置焦点
		pwdSettingPop.setFocusable(true);
		// 设置点击其他地方 就消失
		pwdSettingPop.setOutsideTouchable(true);
		initView();
	}

	TextView set_name1, set_name2;
	ImageView set_item_log;

	Button setting_teach_pass_btn;
	Button setting_edit_pass_btn;
	Button setting_clear_all_btn;
	Button setting_reboot;
	Button setting_cancel_pass;

	private void initView() {
		set_name1 = (TextView) findViewById(R.id.set_name1);
		set_name2 = (TextView) findViewById(R.id.set_name2);
		set_item_log = (ImageView) findViewById(R.id.set_item_log);
		set_name1.setText("其它设置");
		set_name2.setText("其它设置");
		set_item_log.setImageResource(R.drawable.claer_setup);

		initCacheView();
	}

	private void initCacheView() {
		setting_clear_all_btn = (Button) findViewById(R.id.setting_clear_all_btn);
		setting_clear_all_btn.setOnClickListener(this);

		setting_teach_pass_btn = (Button) findViewById(R.id.setting_teach_pass_btn);
		setting_teach_pass_btn.setOnClickListener(this);

		setting_edit_pass_btn = (Button) findViewById(R.id.setting_edit_pass_btn);
		setting_edit_pass_btn.setOnClickListener(this);

		setting_reboot = (Button) findViewById(R.id.setting_reboot);
		setting_reboot.setOnClickListener(this);

		setting_cancel_pass = (Button) findViewById(R.id.setting_cancel_pass);
		setting_cancel_pass.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.setting_teach_pass_btn:
			HomeConstants.isPrimaryLock = "0";
			// SharedPreferences.Editor editor = getSharedPreferences("setLOCK",
			// Context.MODE_MULTI_PROCESS).edit();
			// editor.putInt("isPrimaryLock", HomeConstants.isPrimaryLock);
			// editor.commit();

			HomeConstants.isTempLock = "0";
			// editor.putInt("isTempLock", HomeConstants.isTempLock);
			// editor.commit();

			try {
				FileUtils.saveData(HomeConstants.isPrimaryLock,
						HomeConstants.isPrimaryLockPath);
				FileUtils.saveData(HomeConstants.isTempLock,
						HomeConstants.isTempLockPath);
			} catch (IOException e) {
				Logger.log(e.toString());
			}

			showSuccesToast("加锁成功");
			break;
		case R.id.setting_edit_pass_btn:
			// 修改密码
			showEditPassDialog();
			break;
		case R.id.setting_clear_all_btn:
			DataClearTools.cleanApplicationData(this, null);

			toast.setText("缓存已清除");
			toast.setIcon(R.drawable.toast_smile);
			toast.show();
			break;
		case R.id.setting_reboot:

			// reboot();

			// stopApp();

			rebootApp();

			break;
		case R.id.setting_cancel_pass:
			// 解除密码控制

			cancelPass(v);

			break;
		}

	}

	private void cancelPass(View v) {
		if (HomeConstants.HAS_LOCK.equals(HomeConstants.isPrimaryLock)
				|| HomeConstants.HAS_LOCK.equals(HomeConstants.isTempLock)) {
			// 弹出保护框
			PwdcheckWindow view = new PwdcheckWindow(this, v, pwdcallback);
			view.settvContent("是否解锁家长控制");
		} else {
			toast.setText("家长控制已解锁");
			toast.setIcon(R.drawable.toast_smile);
			toast.show();
		}
	}

	PwdCheckCallBack pwdcallback = new PwdCheckCallBack() {

		@Override
		public void check(String str) {
			if ("1".equals(str)) {
				HomeConstants.isPrimaryLock = HomeConstants.NO_LOCK;

				// SharedPreferences.Editor editor = SettingClear.this
				// .getSharedPreferences("setLOCK",
				// Context.MODE_MULTI_PROCESS).edit();
				// editor.putInt("isPrimaryLock", HomeConstants.isPrimaryLock);
				// editor.commit();

				try {
					FileUtils.saveData(HomeConstants.isPrimaryLock,
							HomeConstants.isPrimaryLockPath);
				} catch (IOException e) {
					e.printStackTrace();
				}

				// toast.setText("家长控制解锁成功");
				// toast.setIcon(R.drawable.toast_smile);
				// toast.show();
			} else {

			}
		}

	};

	ActivityManager am;

	List<RunningAppProcessInfo> list;

	/**
	 * 强行停止该应用
	 */
	private void stopApp() {
		am = (ActivityManager) getSystemService(this.ACTIVITY_SERVICE);

		list = am.getRunningAppProcesses();
		for (RunningAppProcessInfo info : list) {
			if ("com.hlj.HomeActivity".equals(info.processName)) {
				int pid = info.pid;
				try {
					Method method = Class
							.forName("android.app.ActivityManager").getMethod(
									"forceStopPackage", String.class);
					method.invoke(am, "com.hlj.HomeActivity");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 重启机器
	 */
	private void reboot() {
		PowerManager pm = (PowerManager) getApplicationContext()
				.getSystemService(Context.POWER_SERVICE);
		pm.reboot(null);
	}

	/**
	 * 重启指定的应用
	 */
	private void rebootApp() {
		// Intent i = this.getPackageManager().getLaunchIntentForPackage(
		// this.getPackageName());
		SpectialHomeActivity.instance.finish();
		Intent i = this.getPackageManager().getLaunchIntentForPackage(
				"com.hlj.HomeActivity");// 获取启动的包名
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		// PreActivity.isRestart = true;
		BeforePreActivity.isRestart = true;
		startActivity(i);
	}

	private void showEditPassDialog() {
		pwdSettingPop.showAtLocation(cacheView, Gravity.CENTER,
				CommonTools.getScreenWidth(this),
				CommonTools.getScreenHeight(this));
		pwdSettingPop.update(0, 0, CommonTools.getScreenWidth(this) / 2,
				CommonTools.getScreenHeight(this) / 2);
		confirmPwd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				userPasswdNew1 = newPwd.getText().toString();
				userPasswdNew2 = secNewPwd.getText().toString();
				userPasswdOld = oldPwd.getText().toString();
				boolean b1, b2, b3;
				b1 = StringTools.isNullOrEmpty(userPasswdOld);
				b2 = StringTools.isNullOrEmpty(userPasswdNew1);
				b3 = StringTools.isNullOrEmpty(userPasswdNew2);
				if (b1) {
					showFailToast("原密码不能为空");
				} else if (b2) {
					showFailToast("新密码不能为空");
				} else if (b3) {
					showFailToast("请再次输入新密码");
				} else if (!userPasswdNew1.equals(userPasswdNew2)) {
					showFailToast("两次输入的密码不一致");
				}
				if (!StringTools.isNullOrEmpty(userPasswdNew1)
						&& !StringTools.isNullOrEmpty(userPasswdNew2)
						&& !StringTools.isNullOrEmpty(userPasswdOld)
						&& userPasswdNew1.equals(userPasswdNew2)) {
					getParentalControlInfo();
				}
			}
		});

		cancelPwd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pwdSettingPop.dismiss();
			}
		});

	}

	private void getParentalControlInfo() {
		GetParentalControlRequest request = new GetParentalControlRequest(this);
		request.userPasswdNew = userPasswdNew1;
		request.userPasswdOld = userPasswdOld;
		new HttpHomeLoadDataTask(this, parentalControlCallBack, false, "", true)
				.execute(request);
	}

	IUpdateData parentalControlCallBack = new IUpdateData() {
		@Override
		public void updateUi(Object o) {
			GetParentalControlResponse response = new GetParentalControlResponse();
			Log.d("==========parentalControlCallBack============",
					"" + o.toString());
			response.paseRespones(o.toString());
			String s = response.data.login;
			if (s.equals("1")) {
				showSuccesToast("密码修改成功");
			} else if (s.equals("0")) {
				showFailToast1("密码修改失败");
			}
		}

		@Override
		public void handleErrorData(String info) {
			Log.d("==========getParentalControlInfo============", "" + info);
			showFailToast1("密码修改失败");
		}
	};

	@Override
	protected void onDestroy() {
		newPwd.setText("");
		oldPwd.setText("");
		secNewPwd.setText("");
		super.onDestroy(); // To change body of overridden methods use File |
							// Settings | File Templates.
	}

	private void showSuccesToast(String str) {
		toast = new CommonToast(this);
		toast.setText(str);
		toast.setIcon(R.drawable.toast_smile);
		toast.show();
		handler.sendEmptyMessage(0);
	}

	private void showFailToast(String str) {
		toast = new CommonToast(this);
		toast.setText(str);
		toast.setIcon(R.drawable.toast_err);
		toast.show();
	}

	private void showFailToast1(String str) {
		toast = new CommonToast(this);
		toast.setText(str);
		toast.setIcon(R.drawable.toast_err);
		toast.show();
		handler.sendEmptyMessage(0);
	}
}