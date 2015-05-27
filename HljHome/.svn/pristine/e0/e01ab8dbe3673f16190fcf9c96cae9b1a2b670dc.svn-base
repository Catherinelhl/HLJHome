package com.hlj.net;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hlj.utils.JsonTools;
import com.hlj.view.VideoInfo;
import com.live.video.constans.ChannelInfo;
import com.live.video.net.BaseResponse;

/**
 * 获取影视推荐内容信息的返回
 * 
 * @author huangyuchao
 * 
 */
public class GetContentRecResponse extends BaseResponse {

	public ArrayList<VideoInfo> list = new ArrayList<VideoInfo>();

	public int count;

	@Override
	public void paseRespones(String js) {

		try {
			JSONObject json = new JSONObject(js);

			count = JsonTools.getInt(json, "count");

			JSONArray array = json.getJSONArray("items");
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = (JSONObject) array.get(i);
				VideoInfo info = new VideoInfo();
				info.contentId = JsonTools.getString(obj, "contentId");
				info.type = JsonTools.getString(obj, "type");
				info.title = JsonTools.getString(obj, "title");
				info.imageUrl = JsonTools.getString(obj, "images");
				// info.price = JsonTools.getString(obj, "price");

				// 学习类增加的
				// info.lessonid = JsonTools.getString(obj, "lessionid");
				// info.catId = JsonTools.getInt(obj, "catId");
				info.grade = JsonTools.getString(obj, "grade");
				info.subject = JsonTools.getString(obj, "subject");
				info.intro = JsonTools.getString(obj, "intro");
				info.versionsname = JsonTools.getString(obj, "versionsname");
				info.subjectsname = JsonTools.getString(obj, "subjectsname");
				info.catId = JsonTools.getInt(obj, "catId");
				info.url = JsonTools.getString(obj, "url");

				list.add(info);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
