package com.hlj.net;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.live.video.net.BaseResponse;

public class GetCustomResponse extends BaseResponse {

	public int count;

	public ArrayList<Custom> list = new ArrayList<Custom>();

	public class Custom {
		public String metroID;
		public String title;
		public String mode;
		public String url;
	}

	@Override
	public void paseRespones(String js) {
		try {
			JSONObject json = new JSONObject(js);
			count = json.getInt("count");

			JSONArray array = json.getJSONArray("items");
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = (JSONObject) array.get(i);
				Custom info = new Custom();
				info.metroID = obj.getString("metroID");
				info.title = obj.getString("title");
				info.mode = obj.getString("mode");
				info.url = obj.getString("url");
				list.add(info);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
