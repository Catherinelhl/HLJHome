package com.hlj.HomeActivity;

import java.util.ArrayList;
import java.util.HashMap;

import com.hj.widget.CommonToast;
import com.hlj.adapter.AppShowTopAdapter;
import com.hlj.adapter.ApplicationAdapter;
import com.hlj.utils.LauncherBiz;
import com.hlj.utils.Logger;
import com.hlj.view.ApplicationLayout;
import com.live.video.constans.AppBean;
import com.live.video.db.AppDb;
import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnLongClickListener;
import android.view.ViewPropertyAnimator;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * 所有应用
 * 
 * @author huangyuchao
 * 
 */
public class ApplicationActivity extends BaseActivity implements
		View.OnClickListener {

	private GridView appGrid;

	private ImageView whiteBorder;

	private ArrayList<AppBean> appBeans;
	private ApplicationAdapter adapter;

	AppReceiver receiver;

	AppDb appDb;

	private Dialog contentDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.app_list);

		initView();
		initListener();

		appDb = new AppDb(this);

		receiver = new AppReceiver();
		IntentFilter localIntentFilter = new IntentFilter();
		localIntentFilter.addAction("android.intent.action.PACKAGE_ADDED");
		localIntentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
		localIntentFilter.addDataScheme("package");
		registerReceiver(this.receiver, localIntentFilter);
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(receiver);
		super.onDestroy();
	}

	public void initView() {
		appGrid = (GridView) findViewById(R.id.app_grid_new);
		this.appGrid.setSelector(new ColorDrawable(0));

		this.whiteBorder = ((ImageView) findViewById(R.id.white_boder));
		FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(
				160, 160);
		localLayoutParams.leftMargin = 35;
		localLayoutParams.topMargin = 100;
		this.whiteBorder.setLayoutParams(localLayoutParams);
		appBeans = LauncherBiz.getLauncherApps(this);
		adapter = new ApplicationAdapter(this, appBeans);
		appGrid.setAdapter(adapter);
	}

	private int selectionPosition = 0;

	public void initListener() {
		appGrid.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				whiteBorder.setVisibility(View.VISIBLE);
				selectionPosition = position;
				flyWhiteBorder(160, 160, 35.0F + view.getX(),
						100.0F + view.getY());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				whiteBorder.setVisibility(View.INVISIBLE);
			}
		});

		appGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				selectionPosition = position;
				bootAnApp(position);
			}
		});

		appGrid.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				selectionPosition = position;
				showContentDialog();
				return false;
			}
		});

	}

	private void showContentDialog() {
		this.appGrid.clearFocus();
		if (contentDialog == null) {
			View localView = LayoutInflater.from(this).inflate(
					R.layout.app_dia_contentmenu, null);
			((Button) localView.findViewById(R.id.app_top))
					.setOnClickListener(this);
			((Button) localView.findViewById(R.id.app_boot))
					.setOnClickListener(this);
			((Button) localView.findViewById(R.id.app_detail))
					.setOnClickListener(this);
			((Button) localView.findViewById(R.id.app_delete))
					.setOnClickListener(this);
			this.contentDialog = new Dialog(this);
			this.contentDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			this.contentDialog.setContentView(localView);
			Window localWindow = this.contentDialog.getWindow();
			WindowManager.LayoutParams localLayoutParams = localWindow
					.getAttributes();
			localWindow.setGravity(Gravity.CENTER);
			localLayoutParams.width = 250;
			localLayoutParams.height = 230;
			localWindow.setAttributes(localLayoutParams);
		}
		contentDialog.show();
	}

	private void flyWhiteBorder(int paramInt1, int paramInt2,
			float paramFloat1, float paramFloat2) {
		if (this.whiteBorder == null)
			return;
		int i = this.whiteBorder.getWidth();
		int j = this.whiteBorder.getHeight();
		ViewPropertyAnimator localViewPropertyAnimator = this.whiteBorder
				.animate();
		localViewPropertyAnimator.setDuration(200L);
		localViewPropertyAnimator.scaleX(paramInt1 / i);
		localViewPropertyAnimator.scaleY(paramInt2 / j);
		localViewPropertyAnimator.x(paramFloat1);
		localViewPropertyAnimator.y(paramFloat2);
		localViewPropertyAnimator.start();
	}

	/**
	 * 进入应用
	 * 
	 * @param postion
	 */
	private void bootAnApp(int postion) {
		if (this.appBeans.get(postion) != null) {
			try {
				Intent localIntent = this.getPackageManager()
						.getLaunchIntentForPackage(
								((AppBean) appBeans.get(postion))
										.getPackageName());
				startActivity(localIntent);
			} catch (Exception e) {
				e.printStackTrace();
				CommonToast localItvToast = new CommonToast(this);
				localItvToast.setDuration(1);
				localItvToast.setIcon(R.drawable.toast_err);
				localItvToast.setText(R.string.toast_app_boot_err);
				localItvToast.show();
				appBeans.remove(postion);
				this.adapter.notifyDataSetChanged();
				return;
			}
		} else {
			CommonToast localItvToast1 = new CommonToast(this);
			localItvToast1.setDuration(1);
			localItvToast1.setText(R.string.toast_app_boot_no);
			localItvToast1.show();
		}
	}

	private class AppReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			Logger.log("action=" + action);
			String packageName = intent.getDataString();
			Logger.log("packageName=" + packageName);
			if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
				refrushView();
			} else if ("android.intent.action.PACKAGE_REMOVED".equals(action)) {
				appDb.deleteApp(packageName);
				refrushView();
				sendBroadcast(new Intent(ApplicationLayout.CHANEACTION));
			}

		}
	}

	private void refrushView() {
		this.appBeans = LauncherBiz.getLauncherApps(this);
		// adapter = new ApplicationAdapter(this, appBeans);
		// appGrid.setAdapter(adapter);
		this.adapter.changeData(this.appBeans);
		this.adapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {

		this.contentDialog.dismiss();
		this.appGrid.requestFocus();
		switch (v.getId()) {
		case R.id.app_top:
			showOnTop();
			break;
		case R.id.app_boot:
			bootAnApp(selectionPosition);
			break;
		case R.id.app_detail:
			showAppDetail(selectionPosition);
			break;
		case R.id.app_delete:
			unInstallApp(selectionPosition);
			break;
		default:
			break;
		}
	}

	private Dialog positionDialog;

	private HashMap<Integer, AppBean> topApps;

	AppShowTopAdapter appShowTopAdapter;

	private void showOnTop() {
		if (positionDialog == null) {
			View localView = LayoutInflater.from(this).inflate(
					R.layout.app_dia_totop, null);

			GridView gridView = (GridView) localView
					.findViewById(R.id.top_app_position_grid);
			gridView.setSelector(new ColorDrawable(0));
			this.topApps = this.appDb.queryTopApps();
			appShowTopAdapter = new AppShowTopAdapter(this, topApps);
			gridView.setAdapter(appShowTopAdapter);

			gridView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					AppBean appBean = appBeans.get(selectionPosition);

					if (topApps != null && null != topApps.get(position)) {
						appDb.removeRecode(appBean, position);
					}
					appDb.recordApp(appBean, position);
					topApps.put(position, appBean);
					appShowTopAdapter.notifyDataSetChanged();
					positionDialog.dismiss();
					sendBroadcast(new Intent(ApplicationLayout.CHANEACTION));
				}
			});

			positionDialog = new Dialog(this);
			positionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			positionDialog.setContentView(localView);
			Window localWindow = this.positionDialog.getWindow();
			WindowManager.LayoutParams localLayoutParams = localWindow
					.getAttributes();
			localWindow.setGravity(Gravity.CENTER);
			localLayoutParams.width = 350;
			localLayoutParams.height = 250;
			localWindow.setAttributes(localLayoutParams);
		}
		positionDialog.show();
	}

	private void showAppDetail(int position) {
		Intent intent = new Intent(
				"android.settings.APPLICATION_DETAILS_SETTINGS");
		intent.setData(Uri.parse("package:"
				+ appBeans.get(position).getPackageName()));
		startActivity(intent);
	}

	private void unInstallApp(int position) {
		startActivity(new Intent("android.intent.action.DELETE",
				Uri.parse("package:" + appBeans.get(position).getPackageName())));
	}

}
