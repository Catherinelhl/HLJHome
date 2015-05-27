package com.live.video.net;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hlj.net.GetRecmodMetroDataResponse.MetroData;
import com.hlj.utils.JsonTools;
import com.hlj.utils.Logger;
import com.live.video.net.GetFilmMetroDataResponse.MetroData.ActionExtra;
import com.live.video.net.GetFilmMetroDataResponse.MetroData.ActionExtra.Item;

public class GetFilmMetroDataResponse extends BaseResponse {

	public int count;

	public ArrayList<MetroData> list = new ArrayList<MetroData>();;

	public class MetroData {
		public String shortTitle;
		public String title;
		public String imageurl;
		public String mode;
		public String childLock;

		public ActionExtra extra;
		public Item item;

		MetroData info;

		public MetroData() {
			extra = new ActionExtra();
			item = extra.new Item();
		}

		public class ActionExtra {
			public String action;

			public class Item {
				public int catId;
				public String contentId;
				public String url;
			}

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
				info.shortTitle = JsonTools.getString(obj, "shortTitle");
				info.title = JsonTools.getString(obj, "title");
				info.imageurl = JsonTools.getString(obj, "imageurl");
				info.mode = JsonTools.getString(obj, "mode");
				info.childLock = JsonTools.getString(obj, "childLock");

				Logger.log("shortTitle:" + info.shortTitle);
				Logger.log("title:" + info.title);
				Logger.log("imageurl:" + info.imageurl);
				Logger.log("mode:" + info.mode);
				Logger.log("childLock:" + info.childLock);

				JSONObject extraJson = obj.getJSONObject("actionExtra");
				info.extra.action = extraJson.getString("action");

				JSONObject itemObj = extraJson.getJSONObject("item");
				info.item = info.extra.new Item();
				info.item.catId = JsonTools.getInt(itemObj, "catId");
				Logger.log("catId" + info.item.catId);
				info.item.contentId = JsonTools.getString(itemObj, "contentId");
				Logger.log("contentid" + info.item.contentId);
				info.item.url = JsonTools.getString(itemObj, "url");
				list.add(info);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
