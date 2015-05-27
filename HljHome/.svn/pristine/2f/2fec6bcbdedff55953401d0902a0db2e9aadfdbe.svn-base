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
 * 获取来源返回
 * 
 * @author huangyuchao
 * 
 */
public class GetLiveChannelResponse extends BaseResponse {

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
				info.channelId = JsonTools.getInt(item, "channelId");
				info.channelName = JsonTools.getString(item, "channelName");
				list.add(info);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
}
