package com.live.video.net;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.hlj.utils.JsonTools;
import com.hlj.utils.Logger;
import com.live.video.constans.Group;
import com.live.video.constans.VodRecode;

public class GetGradeResponse extends BaseResponse {

	public ArrayList<Group> list = new ArrayList<Group>();

	public int count;

	@Override
	public void paseRespones(String js) {
		try {
			JSONObject json = new JSONObject(js);
			count = json.getInt("count");

			JSONArray array = json.getJSONArray("items");
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = (JSONObject) array.get(i);
				Group info = new Group();

				info.typeId = JsonTools.getString(obj, "typeId");
				info.groupName = JsonTools.getString(obj, "groupName");
				info.isDefault = JsonTools.getString(obj, "default");

				Logger.log("typeId:" + info.typeId);
				Logger.log("groupName:" + info.groupName);
				Logger.log("default:" + info.isDefault);

				list.add(info);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
