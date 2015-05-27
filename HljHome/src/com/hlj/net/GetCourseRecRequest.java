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
 * 根据条件获取学习推荐信息
 * 
 * @author huangyuchao
 * 
 */
public class GetCourseRecRequest extends BaseRequest implements
		IHomeCallBackRequest {
	public String deviceUsersUniqueId = DeviceCheck.shareDeviceUsersUniqueId();

	public String randomString = DeviceCheck.getRandomUUID();
	public String size = "7";
	public String grade;
	public String subject;
	public int catId;
	public String versions;
	public String type;

	public String contentId;

	public String androidId;
	public String deviceTypeName = DeviceInfo.getDeviceName();
	public String androidVersion = DeviceInfo.getAndroidVersion();

	public GetCourseRecRequest(Context context) {
		androidId = DeviceCheck.getAndroidId(context);
	}

	@Override
	public String getInfo() {
		API = FunctionTagTable.GETCOURSERECOMMMEND.getApi();
		VER = FunctionTagTable.GETCOURSERECOMMMEND.getVer();
		MODE = FunctionTagTable.GETCOURSERECOMMMEND.getMode();
		JSONObject dataJson = new JSONObject();
		try {
			dataJson.put("deviceUsersUniqueId", deviceUsersUniqueId);
			dataJson.put("randomString", randomString);
			dataJson.put("size", size);
			dataJson.put("grade", grade);
			dataJson.put("subject", subject);
			dataJson.put("catId", catId);
			dataJson.put("type", type);
			dataJson.put("versions", versions);
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
		return FunctionTagTable.GETCOURSERECOMMMEND;
	}
}
