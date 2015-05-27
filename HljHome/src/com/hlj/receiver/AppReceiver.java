package com.hlj.receiver;

import com.hlj.utils.Logger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

public class AppReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		PackageManager manager = context.getPackageManager();
		if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
			String packageName = intent.getData().getSchemeSpecificPart();
			// Toast.makeText(context, "安装成功" + packageName,
			// Toast.LENGTH_LONG).show();

			Logger.log("============ACTION_PACKAGE_ADDED==============");

			if ("com.hlj.HomeActivity".equals(packageName)) {
				Intent it = manager.getLaunchIntentForPackage(packageName);
				if (it != null) {
					context.startActivity(it);
				}
			}

		}
		if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) {
			String packageName = intent.getData().getSchemeSpecificPart();
			// Toast.makeText(context, "卸载成功" + packageName,
			// Toast.LENGTH_LONG).show();
		}
		if (intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)) {
			String packageName = intent.getData().getSchemeSpecificPart();
			// Toast.makeText(context, "替换成功" + packageName,
			// Toast.LENGTH_LONG).show();

			Logger.log("============ACTION_PACKAGE_REPLACED==============");
			if ("com.hlj.HomeActivity".equals(packageName)) {
				Intent it = manager.getLaunchIntentForPackage(packageName);
				if (it != null) {
					context.startActivity(it);
				}
			}
		}

	}
}
