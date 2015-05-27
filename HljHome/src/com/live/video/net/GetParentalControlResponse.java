package com.live.video.net;

import android.util.Log;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: ${shime}
 * Date: 13-11-16
 * Time: 下午2:36
 * To change this template use File | Settings | File Templates.
 */
public class GetParentalControlResponse extends BaseResponse {
	public ControlData data=new ControlData();
	public class  ControlData{
		public ControlData() {
		}

		public String login;
	}

	@Override
	public void paseRespones(String js) {
		Log.d("=========GetParentalControlResponse========",""+js);
		try {
			JSONObject obj=new JSONObject(js);
			data.login=obj.getString("login");
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
