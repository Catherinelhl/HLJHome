package com.hlj.net;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hlj.utils.JsonTools;
import com.hlj.utils.Logger;
import com.hlj.utils.StringTools;
import com.hlj.view.VideoInfo;
import com.live.video.constans.HomeConstants;

public class GetDeviceResponse extends BaseResponse {

	public String infos;
	public String contact;
	public String logo;
	public String startBg;
	public String pageBg;

	public String groupTitle;

	public String isGrade;

	public String startLogo;
	public String epgId;
	public String epglock;

	@Override
	public void paseRespones(String js) {

		try {
			JSONObject json = new JSONObject(js);

			JSONObject obj = json.getJSONObject("item");
			// infos = obj.getString("infos");
			// contact = obj.getString("contact");
			infos = JsonTools.getString(obj, "infos");
			contact = JsonTools.getString(obj, "contact");
			logo = JsonTools.getString(obj, "logo");
			startBg = JsonTools.getString(obj, "startBg");
			pageBg = JsonTools.getString(obj, "pageBg");

			isGrade = JsonTools.getString(obj, "isGrade");
			groupTitle = JsonTools.getString(obj, "groupTitle");

			startLogo = JsonTools.getString(obj, "startLogo");
			epgId = JsonTools.getString(obj, "epgId");
			epglock = JsonTools.getString(obj, "epglock");// 0是加锁，1不加锁
			
			//如果为空表示有锁
			if(StringTools.isNullOrEmpty(epglock)){
				epglock=HomeConstants.HAS_LOCK;
			}
			
			Logger.log("infos:" + infos);
			Logger.log("contact:" + contact);
			Logger.log("logo:" + logo);
			Logger.log("startBg:" + startBg);
			Logger.log("pageBg:" + pageBg);
			Logger.log("isGrade:" + isGrade);
			Logger.log("groupTitle:" + groupTitle);
			Logger.log("startLogo:" + startLogo);
			Logger.log("epgId:" + epgId);
			Logger.log("epglock:" + epglock);

		} catch (JSONException e) {
			Logger.log(e.toString());
		}

	}

}
