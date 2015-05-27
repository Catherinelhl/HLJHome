package com.live.video.network;

import java.lang.ref.SoftReference;

import org.json.JSONObject;

import com.hlj.HomeActivity.R;
import com.hlj.encrypt.Base64;
import com.hlj.net.HttpHomeLoadDataTask;
import com.hlj.net.HttpHomeLoadDataTask.DialogCancleLisen;
import com.hlj.utils.CommonTools;
import com.hlj.utils.Logger;
import com.hlj.utils.StringTools;
import com.hlj.utils.StringUtils;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;

public class FinalTask {
	public static ProgressDialog mDialog;
	private FinalHttp finalHttp;
	private Context mContext;
	private AjaxParams params;
	private SoftReference<ProgressDialog> softReference;

	public FinalTask(Context context) {
		this.mContext = context;
	}

	public void post(String data, String url, final boolean isLoading,
			final GetCallBack getCallBack, final FailureCallBack failureCallBack) {
		mDialog = new ProgressDialog(mContext);
		softReference = new SoftReference(mDialog);
		finalHttp = new FinalHttp();
		params = new AjaxParams();
		params.put("data", data);

		finalHttp.post(url, params, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, strMsg);
				mDialog.dismiss();
				Logger.log("============failureCallBack===========" + strMsg);
				failureCallBack.failure(strMsg);
			}

			@Override
			public void onSuccess(Object o) {
				super.onSuccess(o);
				String str = String.valueOf(o);
				Logger.log("============onSuccess===========" + str);
				Object result = null;
				if (str != null) {
					try {
						JSONObject obj = new JSONObject(str);

						String mode = obj.getString("MODE");
						if (obj.getInt("CODE") == 0) {
							if (mode.equals("B64")) {
								String str2 = (String) obj.get("RESULT");
								byte[] b = Base64.decode(str2);
								result = new String(b, "utf-8");
							} else if (mode.equals("RAW")) {
								if (!StringTools.isNullOrEmpty(String
										.valueOf(obj.get("RESULT")))) {
									result = obj.getJSONObject("RESULT");
								}
							}
							getCallBack.post(String.valueOf(result));
						} else {
							JSONObject obj1 = obj.getJSONObject("MSG");
							Log.d("============MSG===========", "" + obj1);
							String cnStr = obj1.getString("cn");
							String enStr = obj1.getString("en");
							if (!StringTools.isNullOrEmpty(cnStr)) {
								failureCallBack.failure(cnStr);
							} else if (!StringTools.isNullOrEmpty(enStr)) {
								failureCallBack.failure(enStr);
							}
						}
					} catch (Exception e) {
						failureCallBack.failure(String.valueOf(result));
						e.printStackTrace();

					}
				}

			}

			@Override
			public void onLoading(long count, long current) {
				super.onLoading(count, current);
				if (isLoading) {
					mDialog = (ProgressDialog) softReference.get();
					// mDialog.setMessage("稍等片刻，精彩内容即将呈现......");
					mDialog.setCancelable(true);
					View dialogView = CommonTools.getView(mContext,
							R.layout.hlj_loading_dialog, null);
					mDialog.setView(dialogView);
					mDialog.show();
					mDialog.getWindow().setContentView(
							R.layout.hlj_loading_dialog);
				}
			}
		});

	}

	public static abstract interface FailureCallBack {
		public abstract void failure(String string);
	}

	public static abstract interface GetCallBack {
		public abstract void post(String string);
	}
}
