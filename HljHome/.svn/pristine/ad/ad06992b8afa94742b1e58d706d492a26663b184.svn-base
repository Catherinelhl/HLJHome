package com.live.video.net;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hlj.utils.JsonTools;
import com.hlj.view.VideoInfo;
import com.live.video.constans.ChapterInfo;

/**
 * 播放历史的返回
 * 
 * @author huangyuchao
 * 
 */
public class GetPlayListResponse extends BaseResponse {

	public ArrayList<VideoInfo> list = new ArrayList<VideoInfo>();

	@Override
	public void paseRespones(String js) {
		try {
			JSONObject json = new JSONObject(js);

			JSONArray array = json.getJSONArray("items");
			for (int i = 0; i < array.length(); i++) {
				JSONObject item = array.getJSONObject(i);
				VideoInfo info = new VideoInfo();
				info.contentId = JsonTools.getString(item, "contentId");
				info.title = JsonTools.getString(item, "title");
				info.imageUrl = JsonTools.getString(item, "images");
				info.price = JsonTools.getString(item, "price");
				info.childLock = JsonTools.getString(item, "childLock");
				list.add(info);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
}
