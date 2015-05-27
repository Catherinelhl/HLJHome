package com.live.video.network;

import com.hlj.utils.Player;

import android.os.AsyncTask;

/**
 * 重定向ip
 * 
 * @author huangyuchao
 * 
 */
public class GetRedicitIpTask extends AsyncTask<String, Integer, String> {

	ICallBack mIcallback;

	public GetRedicitIpTask(ICallBack callback) {
		this.mIcallback = callback;
	}

	@Override
	protected String doInBackground(String... params) {
		return Player.getredirect(params[0]);
	}

	@Override
	protected void onPostExecute(String result) {
		if ((result != null) && (!"".equals(result))) {
			mIcallback.handleResult(result);
		} else {
			mIcallback.handleResult("");
		}
		super.onPostExecute(result);
	}

}
