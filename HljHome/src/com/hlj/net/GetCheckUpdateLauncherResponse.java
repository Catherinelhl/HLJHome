package com.hlj.net;

import org.json.JSONObject;

import com.hlj.utils.JsonTools;

/**
 * 
 * @author hyc
 *
 */
public class GetCheckUpdateLauncherResponse extends BaseResponse {
	public UpdateLaInfo updateLaInfo = new UpdateLaInfo();

	public class UpdateLaInfo {
		public String zipUrl;
		public String message;
		public String ver;
		public String md5;
		public String forceUpdateCode;

		public UpdateLaInfo() {
		}
	}

	@Override
	public void paseRespones(String js) {
		try {
			JSONObject obj = new JSONObject(js);
			updateLaInfo.zipUrl = JsonTools.getString(obj, "zipUrl");
			updateLaInfo.message = JsonTools.getString(obj, "description");
			updateLaInfo.ver = JsonTools.getString(obj, "ver");
			updateLaInfo.md5 = JsonTools.getString(obj, "md5");
			updateLaInfo.forceUpdateCode = JsonTools.getString(obj,
					"forceUpdateCode");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
