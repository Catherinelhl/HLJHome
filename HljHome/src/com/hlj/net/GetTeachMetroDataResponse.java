package com.hlj.net;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hlj.net.GetTeachMetroDataResponse.MetroData.ActionExtra;
import com.hlj.utils.JsonTools;
import com.hlj.utils.Logger;
import com.live.video.net.BaseResponse;

public class GetTeachMetroDataResponse extends BaseResponse {

	public int count;

	public ArrayList<MetroData> list = new ArrayList<MetroData>();;

	public class MetroData {
		public String shortTitle;
		public String Title;
		public String imageurl;
		public String mode;

		public class ActionExtra {
			// public int catId;
			public String url;
		}
	}

	@Override
	public void paseRespones(String js) {

		try {
			JSONObject json = new JSONObject(js);
			count = json.getInt("count");

			JSONArray array = json.getJSONArray("items");
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = (JSONObject) array.get(i);
				MetroData info = new MetroData();
				info.shortTitle = obj.getString("shortTitle");
				info.Title = obj.getString("Title");
				info.imageurl = obj.getString("imageurl");
				info.mode = obj.getString("mode");
				
				Logger.log("shortTitle"+info.shortTitle);
				Logger.log("Title"+info.shortTitle);
				Logger.log("imageurl"+info.imageurl);
				Logger.log("mode"+info.mode);
				
				//ActionExtra extra = info.new ActionExtra();
				// JSONObject extraJson = obj.getJSONObject("actionExtra");
				// extra.catId = extraJson.getInt("catId");
				// extra.url = JsonTools.getString(extraJson, "url");
				list.add(info);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
