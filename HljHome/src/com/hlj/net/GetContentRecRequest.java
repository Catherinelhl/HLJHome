package com.hlj.net;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.hlj.utils.DeviceCheck;
import com.hlj.utils.Logger;
import com.live.video.constans.DeviceInfo;
import com.live.video.constans.HomeConstants.FunctionTagTable;
import com.live.video.net.callback.ICallBackRequest;
import com.live.video.net.callback.IHomeCallBackRequest;

/**
 * 根据条件获取影视推荐信息
 * 
 * @author huangyuchao
 * 
 */
public class GetContentRecRequest extends BaseRequest implements
		IHomeCallBackRequest {
	public String deviceUsersUniqueId = DeviceCheck.shareDeviceUsersUniqueId();

	public String randomString = "test";
	public String size = "7";
	public String title;
	public String type;
	public String actor;
	public int catId;
	public String contentId;

	public String androidId;
	public String deviceTypeName = DeviceInfo.getDeviceName();
	public String androidVersion = DeviceInfo.getAndroidVersion();

	public GetContentRecRequest(Context context) {
		androidId = DeviceCheck.getAndroidId(context);
	}

	@Override
	public String getInfo() {
		API = FunctionTagTable.GETCONTENTRECOMMEND.getApi();
		VER = FunctionTagTable.GETCONTENTRECOMMEND.getVer();
		MODE = FunctionTagTable.GETCONTENTRECOMMEND.getMode();
		JSONObject dataJson = new JSONObject();
		try {
			dataJson.put("deviceUsersUniqueId", deviceUsersUniqueId);
			dataJson.put("randomString", randomString);
			dataJson.put("size", size);
			dataJson.put("title", title);
			dataJson.put("type", type);
			dataJson.put("actor", actor);
			dataJson.put("catId", catId);
			dataJson.put("contentId", contentId);

			dataJson.put("androidId", androidId);
			dataJson.put("androidVersion", androidVersion);
			dataJson.put("deviceTypeName", deviceTypeName);
		} catch (JSONException e) {
			Logger.log("getInfo error" + e.getLocalizedMessage());
		}
		return getJsonString(dataJson);
	}

	@Override
	public FunctionTagTable getNetTag() {
		return FunctionTagTable.GETCONTENTRECOMMEND;
	}
}
