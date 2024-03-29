package com.hlj.widget;

import java.io.File;
import java.io.IOException;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.HttpHandler;

//import com.hlj.HomeActivity.HomeActivity;
import com.hlj.HomeActivity.R;
import com.hlj.utils.FileUtils;
import com.hlj.utils.LauncherBiz;
import com.hlj.utils.Logger;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UpdateDialog extends Dialog implements View.OnClickListener {

	private Context mContext;

	public UpdateDialog(Context context) {
		super(context, R.style.CustomDialog);
		mContext = context;

		init();
	}

	private String messageStr;

	public UpdateDialog(Context context, String message, String url,
			String fileName, String extensionName) {
		super(context, R.style.CustomDialog);
		mContext = context;

		messageStr = message;
		this.url = url;
		this.fileName = fileName;
		this.extensionName = extensionName;
		init();
	}

	Button update_dialog_comfire, update_dialog_cancle, update_dialog_forgive;

	TextView update_dialog_msg;

	private void init() {
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.itv_update_dialog, null);

		update_dialog_comfire = (Button) view
				.findViewById(R.id.update_dialog_comfire);
		update_dialog_cancle = (Button) view
				.findViewById(R.id.update_dialog_cancle);
		update_dialog_forgive = (Button) view
				.findViewById(R.id.update_dialog_forgive);

		update_dialog_msg = (TextView) view
				.findViewById(R.id.update_dialog_msg);

		update_dialog_comfire.setOnClickListener(this);
		update_dialog_cancle.setOnClickListener(this);
		update_dialog_forgive.setOnClickListener(this);

		update_dialog_msg.setText(messageStr);

		setContentView(view);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.update_dialog_comfire:
			downloadapk();
			dismiss();
			break;
		case R.id.update_dialog_cancle:
			dismiss();
			break;
		case R.id.update_dialog_forgive:
			dismiss();
			break;
		}

	}

	FinalHttp finalHttp = new FinalHttp();
	HttpHandler httpHandler;

	String target = "/mnt/sdcard/";
	String extensionName;
	String fileName;
	String url;
	// String forceUpdateCode;
	String ver;
	String message;

	/**
	 * 下载
	 * 
	 * @param 
	 */
	public void downloadapk() {
		// 判断文件是否存在(如果存在则删除)
		try {
			boolean isFileExist = FileUtils.delectFile(target + fileName);
			Logger.log("isFileExist:" + isFileExist);
		} catch (IOException e) {
			Logger.log("deleteFoder failed:" + e.getLocalizedMessage());
		}

		httpHandler = finalHttp.download(url, target + fileName, true,
				new AjaxCallBack<File>() {
					@Override
					public void onStart() {
						Logger.log("开始下载");
					}

					@Override
					public void onLoading(long count, long current) {
						Logger.log(current + "/" + count);
					}

					@Override
					public void onSuccess(final File t) {
						Logger.log("下载成功");
						Logger.log(t.getAbsolutePath() + "<><><><><><>");

						// 如果是apk的话就执行安装
						if (".apk".equals(extensionName)) {
							// if ("yes".equals(forceUpdateCode)) {
							// // 强制安装
							// boolean isInstall = LauncherBiz
							// .execCommand("system/bin/pm install -r "
							// + "sdcard/" + fileName);
							//
							// Logger.log("isInstall:" + isInstall);
							// } else {
							// 用户安装
							LauncherBiz.installPackage(mContext, target
									+ fileName);
							// }
						} else {
							Logger.log("下载文件不是apk文件");
						}
					}

					@Override
					public void onFailure(Throwable t, String strMsg) {
						Logger.log("下载失败");
						Logger.log(strMsg + "=====================");
					}

				});
	}

}
