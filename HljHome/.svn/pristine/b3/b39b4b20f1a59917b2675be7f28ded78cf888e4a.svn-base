package com.hlj.net;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hlj.net.GetRecmodMetroDataResponse.MetroData.ActionExtra;
import com.hlj.net.GetRecmodMetroDataResponse.MetroData.ActionExtra.Item;
import com.hlj.utils.JsonTools;
import com.hlj.utils.Logger;
import com.live.video.net.BaseResponse;

public class GetRecmodMetroDataResponse extends BaseResponse {

	public int count;

	public ArrayList<MetroData> list = new ArrayList<MetroData>();;

	MetroData info;

	public GetRecmodMetroDataResponse() {

	}

	public class MetroData {
		public String shortTitle;
		public String title;
		public String imageurl;
		public String mode;

		public String childLock;

		public ActionExtra extra;
		public Item item;

		public MetroData() {
			extra = new ActionExtra();
			item = extra.new Item();
		}

		public class ActionExtra {
			public String action;
			public String url;

			public class Item {
				public int catId;
				public String contentId;
				public String type;
				public String grade;
				public String subject;
				public String versions;
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
				info = new MetroData();
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

				if ("openUrl".equals(info.mode)) {
					if (JSONObject.NULL == extraJson.get("url")) {

					} else {
						info.extra.url = JsonTools.getString(extraJson, "url");
					}
				} else {
					if (JSONObject.NULL == extraJson.get("action")) {

					} else {
						info.extra.action = JsonTools.getString(extraJson,
								"action");
					}

					if (JSONObject.NULL == extraJson.get("url")) {

					} else {
						info.extra.url = JsonTools.getString(extraJson, "url");
					}

					if (JSONObject.NULL == extraJson.get("item")) {

					} else {
						JSONObject itemObj = extraJson.getJSONObject("item");
						info.item = info.extra.new Item();
						info.item.catId = JsonTools.getInt(itemObj, "catId");
						Logger.log("catId:" + info.item.catId);
						info.item.contentId = JsonTools.getString(itemObj,
								"contentId");
						Logger.log("contentId:" + info.item.contentId);
						info.item.type = JsonTools.getString(itemObj, "type");
						Logger.log("type:" + info.item.type);
						info.item.grade = JsonTools.getString(itemObj, "grade");
						Logger.log("grade:" + info.item.grade);

						info.item.subject = JsonTools.getString(itemObj,
								"subject");
						Logger.log("subject:" + info.item.subject);
						info.item.versions = JsonTools.getString(itemObj,
								"versions");
						Logger.log("versions:" + info.item.versions);
					}
				}

				list.add(info);
			}

		} catch (JSONException e) {
			Logger.log("GetRecmodMetroDataResponse:" + e.toString());
		}

	}
}
