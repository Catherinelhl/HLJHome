package com.hlj.net;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.hlj.utils.DeviceCheck;
import com.hlj.utils.Logger;
import com.live.video.constans.DeviceInfo;
import com.live.video.constans.HomeConstants.FunctionTagTable;
import com.live.video.net.callback.IHomeCallBackRequest;

/**
 * 获取时间请求
 * 
 * @author hyc
 * 
 */
public class GetTimeRequest extends BaseRequest implements IHomeCallBackRequest {
	public String deviceUsersUniqueId = DeviceCheck.shareDeviceUsersUniqueId();
	public String androidId;
	public String deviceTypeName = DeviceInfo.getDeviceName();
	public String androidVersion = DeviceInfo.getAndroidVersion();

	public GetTimeRequest(Context context) {
		androidId = DeviceCheck.getAndroidId(context);
	}

	@Override
	public String getInfo() {
		API = FunctionTagTable.GETTIME.getApi();
		VER = FunctionTagTable.GETTIME.getVer();
		MODE = FunctionTagTable.GETTIME.getMode();
		JSONObject dataJson = new JSONObject();
		try {
			dataJson.put("deviceUsersUniqueId", deviceUsersUniqueId);
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
		// TODO Auto-generated method stub
		return FunctionTagTable.GETTIME;
	}

}
