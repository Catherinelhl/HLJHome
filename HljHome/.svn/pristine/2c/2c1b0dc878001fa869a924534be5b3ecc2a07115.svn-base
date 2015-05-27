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
 * 获取影视内容信息的返回
 * 
 * @author huangyuchao
 * 
 */
public class GetCategoryContentListResponse extends BaseResponse {

	public ArrayList<VideoInfo> list = new ArrayList<VideoInfo>();

	public int count;

	@Override
	public void paseRespones(String js) {

		try {
			JSONObject json = new JSONObject(js);

			count = json.getInt("count");

			JSONArray array = json.getJSONArray("items");
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = (JSONObject) array.get(i);
				VideoInfo info = new VideoInfo();
				info.contentId = JsonTools.getString(obj, "contentId");
				info.title = JsonTools.getString(obj, "title");
				info.imageUrl = JsonTools.getString(obj, "images");
				info.price = JsonTools.getString(obj, "price");
				info.childLock = JsonTools.getString(obj, "childLock");
				list.add(info);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
