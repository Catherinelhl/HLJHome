package com.hlj.net;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hlj.utils.JsonTools;
import com.hlj.utils.Logger;
import com.hlj.view.VideoInfo;

/**
 * 获取所有的app版本
 * 
 * @author hyc
 * 
 */
public class GetAllAppVersionResponse extends BaseResponse {

	public class Fireware {
		public String zipUrl;
		public String message;
		public String ver;
		public String md5;
		public String forceUpdateCode;
	}

	public class Launcher {
		public String zipUrl;
		public String message;
		public String ver;
		public String md5;
		public String forceUpdateCode;
	}

	public class LocalApps {
		public String zipUrl;
		public String message;
		public String ver;
		public String md5;
		public String forceUpdateCode;
	}

	public class ShopApps {
		public String zipUrl;
		public String message;
		public String ver;
		public String md5;
		public String forceUpdateCode;
	}

	public Fireware fireware = new Fireware();
	public Launcher launcher = new Launcher();
	public LocalApps localApps = new LocalApps();
	public ShopApps shopApps = new ShopApps();

	@Override
	public void paseRespones(String js) {

		try {
			JSONObject obj = new JSONObject(js);

			JSONObject items = obj.getJSONObject("items");

			JSONObject firewareJson = items.getJSONObject("fireware");
			fireware.zipUrl = JsonTools.getString(firewareJson, "zipUrl");
			fireware.message = JsonTools.getString(firewareJson, "description");
			fireware.ver = JsonTools.getString(firewareJson, "ver");
			fireware.md5 = JsonTools.getString(firewareJson, "md5");
			fireware.forceUpdateCode=JsonTools.getString(firewareJson, "forceUpdateCode");
			
			JSONObject launcherJson = items.getJSONObject("launcher");
			launcher.zipUrl = JsonTools.getString(launcherJson, "zipUrl");
			launcher.message = JsonTools.getString(launcherJson, "description");
			launcher.ver = JsonTools.getString(launcherJson, "ver");
			launcher.md5 = JsonTools.getString(launcherJson, "md5");
			launcher.forceUpdateCode=JsonTools.getString(launcherJson, "forceUpdateCode");

			JSONObject localAppsJson = items.getJSONObject("localApps");
			localApps.zipUrl = JsonTools.getString(localAppsJson, "zipUrl");
			localApps.message = JsonTools.getString(localAppsJson,
					"description");
			localApps.ver = JsonTools.getString(localAppsJson, "ver");
			localApps.md5 = JsonTools.getString(localAppsJson, "md5");
			localApps.forceUpdateCode=JsonTools.getString(localAppsJson, "forceUpdateCode");
			
			JSONObject shopAppsJson = items.getJSONObject("shopApps");
			shopApps.zipUrl = JsonTools.getString(shopAppsJson, "zipUrl");
			shopApps.message = JsonTools.getString(shopAppsJson, "description");
			shopApps.ver = JsonTools.getString(shopAppsJson, "ver");
			shopApps.md5 = JsonTools.getString(shopAppsJson, "md5");
			shopApps.forceUpdateCode=JsonTools.getString(shopAppsJson, "forceUpdateCode");

		} catch (Exception e) {
			Logger.log("GetAllAppVersionResponse:"+e.toString());
		}
	}

}
