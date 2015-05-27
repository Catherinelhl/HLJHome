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
 * 根据栏目contentid获取影视内容信息及视频栏目列表
 * 
 * @author huangyuchao
 * 
 */
public class GetContentInfoRequest extends BaseRequest implements
		IHomeCallBackRequest {
	public String deviceUsersUniqueId = DeviceCheck.shareDeviceUsersUniqueId();

	public String contentId;
	public String randomString = DeviceCheck.getRandomUUID();

	public int teleplayPage;
	public int teleplayPageSize;

	public String androidId;
	public String deviceTypeName = DeviceInfo.getDeviceName();
	public String androidVersion = DeviceInfo.getAndroidVersion();

	public GetContentInfoRequest(Context context) {
		androidId = DeviceCheck.getAndroidId(context);
	}

	@Override
	public String getInfo() {
		API = FunctionTagTable.GETCONTENTINFO.getApi();
		VER = FunctionTagTable.GETCONTENTINFO.getVer();
		MODE = FunctionTagTable.GETCONTENTINFO.getMode();
		JSONObject dataJson = new JSONObject();
		try {
			dataJson.put("deviceUsersUniqueId", deviceUsersUniqueId);
			dataJson.put("contentId", contentId);
			dataJson.put("randomString", randomString);
			dataJson.put("teleplayPage", teleplayPage);
			dataJson.put("teleplayPageSize", teleplayPageSize);

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
		return FunctionTagTable.GETCONTENTINFO;
	}
}
