package com.hlj.net;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.hlj.utils.Logger;
import com.live.video.constans.VodRecode;

/**
 * 关于我们返回
 * 
 * @author huangyuchao
 * 
 */
public class ResponseAboutUs extends BaseResponse {

	public String about;
	public String contact;

	@Override
	public void paseRespones(String js) {
		try {
			JSONObject json = new JSONObject(js);

			about = json.getString("about");
			contact = json.getString("contact");

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
