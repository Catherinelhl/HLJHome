package com.live.video.net;

import org.json.JSONObject;

import android.content.Context;

import com.hlj.net.BaseRequest;
import com.hlj.utils.DeviceCheck;
import com.live.video.constans.DeviceInfo;
import com.live.video.constans.HomeConstants.FunctionTagTable;
import com.live.video.net.callback.IHomeCallBackRequest;

/**
 * 获取组
 * 
 * @author hyc
 * 
 */
public class GetGradeRequest extends BaseRequest implements
		IHomeCallBackRequest {

	String deviceUsersUniqueId = DeviceCheck.shareDeviceUsersUniqueId();

	public String androidId;
	public String deviceTypeName = DeviceInfo.getDeviceName();
	public String androidVersion = DeviceInfo.getAndroidVersion();

	public GetGradeRequest(Context context) {
		androidId = DeviceCheck.getAndroidId(context);
	}

	@Override
	public String getInfo() {
		API = FunctionTagTable.GETGRADEEPG.getApi();
		VER = FunctionTagTable.GETGRADEEPG.getVer();
		MODE = FunctionTagTable.GETGRADEEPG.getMode();

		JSONObject dataJson = new JSONObject();

		try {
			dataJson.put("deviceUsersUniqueId", deviceUsersUniqueId);

			dataJson.put("androidId", androidId);
			dataJson.put("androidVersion", androidVersion);
			dataJson.put("deviceTypeName", deviceTypeName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return getJsonString(dataJson);
	}

	@Override
	public FunctionTagTable getNetTag() {
		return FunctionTagTable.GETGRADEEPG;
	}

}
