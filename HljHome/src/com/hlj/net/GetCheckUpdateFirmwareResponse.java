package com.hlj.net;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: ${shime}
 * Date: 13-11-26
 * Time: 下午2:12
 * To change this template use File | Settings | File Templates.
 */
public class GetCheckUpdateFirmwareResponse extends BaseResponse {
   public UpdateInfo updateInfo=new UpdateInfo();
   public class UpdateInfo{
	public String zipUrl;
	public String md5;
	public String ver;
	public String description;
	public String forceUpdateCode;

	   public UpdateInfo() {
	   }
   }


	@Override
	public void paseRespones(String js) {
		try {
			JSONObject obj=new JSONObject(js);
			updateInfo.md5=obj.getString("md5");
			updateInfo.zipUrl=obj.getString("zipUrl");
			updateInfo.ver=obj.getString("ver");
			updateInfo.description=obj.getString("description");
			//updateInfo.forceUpdateCode=obj.getString("forceUpdateCode");
		} catch (JSONException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}
}
