package com.hlj.net;

import org.json.JSONException;
import org.json.JSONObject;

import com.hlj.encrypt.Base64;
import com.hlj.utils.Logger;
import com.live.video.constans.HomeConstants;

/**
 * 请求基类
 * 
 * @author huangyuchao
 * 
 */
public class BaseRequest {

	public static String API;
	public static String VER;
	public static String MODE;

	public static final String DATA = "DATA";

	public String getJsonString(JSONObject dataJson) {

		JSONObject json = new JSONObject();

		Logger.log("API:" + API);
		Logger.log("VER:" + VER);
		Logger.log("MODE:" + MODE);
		try {
			json.put("API", API);
			json.put("VER", VER);
			json.put("MODE", MODE);

			if (HomeConstants.RAW.equals(MODE)) {
				json.put(DATA, dataJson);
			} else if (HomeConstants.B64.equals(MODE)) {
				String base64Str = Base64
						.encode(dataJson.toString().getBytes());
				Logger.log(base64Str);
				json.put(DATA, base64Str);
			}

			//json.put(DATA, dataJson);
		} catch (JSONException e) {
			Logger.log("getJsonString error" + e.getLocalizedMessage());
		}

		Logger.log(json.toString());
		return json.toString();
	}
}
