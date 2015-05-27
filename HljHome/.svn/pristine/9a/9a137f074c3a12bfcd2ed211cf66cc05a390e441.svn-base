package com.live.video.network;

import com.hlj.utils.Player;

import android.os.AsyncTask;

public class GetBaseIpTask extends AsyncTask<String, Integer, String> {

	ICallBack mIcallback;

	public GetBaseIpTask(ICallBack callback) {
		this.mIcallback = callback;
	}

	@Override
	protected String doInBackground(String... params) {
		return Player.getbaseip();
	}

	@Override
	protected void onPostExecute(String result) {
		if ((result != null) && (!"".equals(result))) {
			mIcallback.handleResult(result);
		}

		super.onPostExecute(result);
	}

}
