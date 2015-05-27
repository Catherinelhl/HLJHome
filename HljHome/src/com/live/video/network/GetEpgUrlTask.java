package com.live.video.network;

import java.util.ArrayList;

import com.hlj.utils.EpgHelper;

import android.os.AsyncTask;

public class GetEpgUrlTask extends AsyncTask<String, Integer, ArrayList> {

	ICallBackResult mIcallback;

	// public String mEpgUrl;
	public int mId;
	public int mType;

	public GetEpgUrlTask(ICallBackResult callback, int id, int type) {
		this.mIcallback = callback;
		mId = id;
		mType = type;
	}

	@Override
	protected ArrayList doInBackground(String... params) {
		ArrayList al = EpgHelper.getEpgInfo(params[0], mId, mType);
		return al;
	}

	@Override
	protected void onPostExecute(ArrayList result) {
		mIcallback.handleResult(result);
		super.onPostExecute(result);
	}

}
