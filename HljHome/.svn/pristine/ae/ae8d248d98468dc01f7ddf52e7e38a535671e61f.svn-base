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
 * 获取本年级的课程列表
 * 
 * @author huangyuchao
 * 
 */
public class GetSubjectGradeRequest extends BaseRequest implements
		IHomeCallBackRequest {
	public static String deviceUsersUniqueId = DeviceCheck
			.shareDeviceUsersUniqueId();
	public String userID = DeviceCheck.shareUserId();
	public String grade;
	public String versions;
	public String subject;
	public String contentId;

	public int catId;

	public String randomString = DeviceCheck.getRandomUUID();

	public String androidId;
	public String deviceTypeName = DeviceInfo.getDeviceName();
	public String androidVersion = DeviceInfo.getAndroidVersion();

	public GetSubjectGradeRequest(Context context) {
		androidId = DeviceCheck.getAndroidId(context);
	}

	@Override
	public String getInfo() {
		API = FunctionTagTable.GETSUBJECTGRADELIST.getApi();
		VER = FunctionTagTable.GETSUBJECTGRADELIST.getVer();
		MODE = FunctionTagTable.GETSUBJECTGRADELIST.getMode();
		JSONObject dataJson = new JSONObject();
		try {
			dataJson.put("deviceUsersUniqueId", deviceUsersUniqueId);
			dataJson.put("userID", userID);
			dataJson.put("grade", grade);
			dataJson.put("versions", versions);
			dataJson.put("catId", catId);
			dataJson.put("contentId", contentId);
			dataJson.put("subject", subject);
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
		return FunctionTagTable.GETSUBJECTGRADELIST;
	}
}
