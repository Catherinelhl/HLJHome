package com.live.video.net;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hlj.utils.JsonTools;
import com.hlj.view.VideoInfo;
import com.live.video.constans.ChapterInfo;
import com.live.video.constans.LiveInfo;

/**
 * 获取列表返回
 * 
 * @author huangyuchao
 * 
 */
public class GetTvListsResponse extends BaseResponse {

	public ArrayList<LiveInfo> list = new ArrayList<LiveInfo>();

	public String count;

	@Override
	public void paseRespones(String js) {
		try {
			JSONObject json = new JSONObject(js);

			count = JsonTools.getString(json, "count");

			JSONArray array = json.getJSONArray("items");
			for (int i = 0; i < array.length(); i++) {
				JSONObject item = array.getJSONObject(i);
				LiveInfo info = new LiveInfo();
				info.id = JsonTools.getInt(item, "id");
				info.title = JsonTools.getString(item, "title");
				info.url = JsonTools.getString(item, "url");
				info.mode = JsonTools.getInt(item, "mode");
				info.type = JsonTools.getInt(item, "type");
				info.channelID = JsonTools.getInt(item, "channelID");
				info.epgurl = JsonTools.getString(item, "epgurl");
				info.tvid = JsonTools.getInt(item, "tvid");
				info.isDefault = JsonTools.getInt(item, "isDefault");
				info.isCollected = JsonTools.getInt(item, "isCollected");

				list.add(info);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
}
