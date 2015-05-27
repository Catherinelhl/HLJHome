package com.live.video.net;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.live.video.constans.ChannelInfo;

/**
 * 获取频道列表的返回
 * 
 * @author huangyuchao
 * 
 */
public class GetChannelResponse extends BaseResponse {

	public ArrayList<ChannelInfo> list = new ArrayList<ChannelInfo>();

	@Override
	public void paseRespones(String js) {

		try {
			JSONObject json = new JSONObject(js);
			JSONArray array = json.getJSONArray("items");
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = (JSONObject) array.get(i);
				ChannelInfo info = new ChannelInfo();
				info.channel = obj.getString("channel");
				info.name = obj.getString("name");
				list.add(info);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
