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
 * 获取全部课程信息
 * 
 * @author huangyuchao
 * 
 */
public class GetCourseAllRequest extends BaseRequest implements
		IHomeCallBackRequest {
	public String deviceUsersUniqueId = DeviceCheck.shareDeviceUsersUniqueId();

	public String userID = DeviceCheck.shareDeviceUsersUniqueId();
	public String randomString = DeviceCheck.getRandomUUID();

	public String androidId;
	public String deviceTypeName = DeviceInfo.getDeviceName();
	public String androidVersion = DeviceInfo.getAndroidVersion();

	public GetCourseAllRequest(Context context) {
		androidId = DeviceCheck.getAndroidId(context);
	}

	@Override
	public String getInfo() {
		API = FunctionTagTable.GETCOURSEALL.getApi();
		VER = FunctionTagTable.GETCOURSEALL.getVer();
		MODE = FunctionTagTable.GETCOURSEALL.getMode();
		JSONObject dataJson = new JSONObject();
		try {
			dataJson.put("deviceUsersUniqueId", deviceUsersUniqueId);
			dataJson.put("userID", userID);
			dataJson.put("randomString", randomString);
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
		return FunctionTagTable.GETCOURSEALL;
	}
}
