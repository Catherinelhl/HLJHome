package com.hlj.HomeActivity.set;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.hlj.HomeActivity.BaseActivity;
import com.hlj.HomeActivity.R;
import com.hlj.net.HttpHomeLoadDataTask;
import com.hlj.net.RequestAboutUs;
import com.hlj.net.ResponseAboutUs;
import com.hlj.utils.ACache;
import com.hlj.utils.ConnectUrl;
import com.hlj.utils.Logger;
import com.hlj.utils.PrefrenceHandler;
import com.hlj.utils.StringTools;
import com.hlj.utils.WeatherUtils;
import com.live.video.constans.Constants;
import com.live.video.constans.HomeConstants;
import com.live.video.net.callback.IUpdateData;
import com.live.video.network.ICallBack;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 关于我们
 * 
 * @author huangyuchao
 * 
 */
public class SettingAboutUs extends BaseActivity implements OnClickListener {

	TextView set_about_version;

	TextView aboutustitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_about);
		initView();
	}

	String title, about, contact;

	TextView aboutustv;

	TextView tvconct;

	ACache cache;

	private void initView() {

		set_about_version = (TextView) findViewById(R.id.set_about_version);

		aboutustitle = (TextView) findViewById(R.id.aboutustitle);
		aboutustv = (TextView) findViewById(R.id.aboutustv);
		tvconct = (TextView) findViewById(R.id.tvconct);

		set_about_version.setText("软件版本号：" + getAPKVersionName());

		// cache = ACache.get(this);

		// about = cache.getAsString("infos");
		// contact = cache.getAsString("contact");

		// about = PrefrenceHandler.getInfos(this);
		// contact = PrefrenceHandler.getContract(this);

		about = Constants.infos;
		contact = Constants.contract;
		title = Constants.title;
		Logger.log("title:" + title + "<><><><><><>");

		if (!StringTools.isNullOrEmpty(about)) {
			aboutustv.setText(about);
		}
		if (!StringTools.isNullOrEmpty(contact)) {
			tvconct.setText(contact);
		}
		if (!StringTools.isNullOrEmpty(title)) {
			aboutustitle.setText(title);
		} else {
			//
			getAboutUs();
		}
		// getAboutUs();

	}

	private void getAboutUs() {
		// RequestAboutUs request = new RequestAboutUs();
		// new HttpHomeLoadDataTask(this, callBack, false).execute(request);

		FinalHttp finalHttp = new FinalHttp();
		finalHttp.post(HomeConstants.aboutUsAddress, null,
				new AjaxCallBack<Object>() {
					@Override
					public void onSuccess(Object o) {
						super.onSuccess(o);

						Logger.log("=== aboutus ===:" + o.toString());

						ResponseAboutUs response = new ResponseAboutUs();
						response.paseRespones(o.toString());
						about = response.about;
						contact = response.contact;

						Logger.log("about:" + about);
						Logger.log("contact:" + contact);

						aboutustv.setText(about);
						tvconct.setText(contact);
					}

					@Override
					public void onFailure(Throwable t, String strMsg) {
						super.onFailure(t, strMsg);
					}
				});

	}

	IUpdateData callBack = new IUpdateData() {

		@Override
		public void updateUi(Object o) {

		}

		@Override
		public void handleErrorData(String info) {
			// TODO Auto-generated method stub

		}

	};

	/**
	 * 获取版本名称
	 * 
	 * @return
	 */
	private String getAPKVersionName() {
		PackageManager localPackageManager = getPackageManager();

		PackageInfo packageInfo;
		try {
			packageInfo = localPackageManager.getPackageInfo(getPackageName(),
					0);
			return packageInfo.versionName;
		} catch (Exception e) {
			e.printStackTrace();
			packageInfo = null;
			return "";
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
