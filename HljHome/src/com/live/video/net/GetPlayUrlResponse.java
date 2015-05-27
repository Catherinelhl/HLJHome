package com.live.video.net;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hlj.utils.JsonTools;
import com.live.video.constans.ChapterInfo;

/**
 * 获取回看url的返回
 * 
 * @author huangyuchao
 * 
 */
public class GetPlayUrlResponse extends BaseResponse {

	public ArrayList<ChapterInfo> list = new ArrayList<ChapterInfo>();

	public int totalLength;

	@Override
	public void paseRespones(String js) {
		try {
			JSONObject json = new JSONObject(js);
			totalLength = JsonTools.getInt(json, "totalLength");

			JSONArray array = json.getJSONArray("chapters");
			for (int i = 0; i < array.length(); i++) {
				JSONObject item = array.getJSONObject(i);
				ChapterInfo info = new ChapterInfo();
				info.duration = item.getString("duration");
				info.url = item.getString("url");
				list.add(info);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
}
