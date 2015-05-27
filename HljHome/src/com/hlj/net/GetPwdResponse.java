package com.hlj.net;

import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA. User: ${shime} Date: 13-11-18 Time: 下午2:19 To
 * change this template use File | Settings | File Templates.
 */
public class GetPwdResponse extends BaseResponse {
	public ControlData data = new ControlData();

	public class ControlData {
		public ControlData() {
		}

		public String login;
	}

	@Override
	public void paseRespones(String js) {
		try {
			JSONObject obj = new JSONObject(js);
			data.login = obj.getString("login");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
