package com.hlj.net;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hlj.utils.Logger;

public class GetStudyVersionResponse extends BaseResponse {

	public ArrayList<Item> list = new ArrayList<Item>();

	public class Item {
		public String versionid;
		public String versionname;
		public String inputtime;
		public String updatetime;
		public String year;
		public String stauts;
	}

	@Override
	public void paseRespones(String js) {
		try {
			JSONObject json = new JSONObject(js);

			JSONArray array = json.getJSONArray("items");
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = (JSONObject) array.get(i);
				Item info = new Item();
				info.versionid = obj.getString("versionid");
				info.versionname = obj.getString("versionname");
				info.inputtime = obj.getString("inputtime");
				info.updatetime = obj.getString("updatetime");
				info.year = obj.getString("year");
				info.stauts = obj.getString("stauts");

				Logger.log("versionid:" + info.versionid);
				Logger.log("versionname:" + info.versionname);
				Logger.log("inputtime:" + info.inputtime);
				Logger.log("updatetime:" + info.updatetime);
				Logger.log("year:" + info.year);
				Logger.log("stauts:" + info.stauts);

				list.add(info);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
