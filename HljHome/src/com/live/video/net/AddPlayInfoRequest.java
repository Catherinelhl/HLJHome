package com.live.video.net;

import org.json.JSONObject;

import android.content.Context;

import com.hlj.net.BaseRequest;
import com.hlj.utils.DeviceCheck;
import com.live.video.constans.DeviceInfo;
import com.live.video.constans.HomeConstants.FunctionTagTable;
import com.live.video.net.callback.IHomeCallBackRequest;

public class AddPlayInfoRequest extends BaseRequest implements
		IHomeCallBackRequest {
	String deviceUsersUniqueId = DeviceCheck.shareDeviceUsersUniqueId();

	String userID = DeviceCheck.shareUserId();
	public String contentId;
	public String videoId;
	public String catId;
	public String grade;
	public String lessionId;
	public String versionId;
	public String subjectId;
	public String source;

	public String androidId;
	public String deviceTypeName = DeviceInfo.getDeviceName();
	public String androidVersion = DeviceInfo.getAndroidVersion();

	public AddPlayInfoRequest(Context context) {
		androidId = DeviceCheck.getAndroidId(context);
	}

	@Override
	public String getInfo() {
		API = FunctionTagTable.ADDPLAYINFOS.getApi();
		VER = FunctionTagTable.ADDPLAYINFOS.getVer();
		MODE = FunctionTagTable.ADDPLAYINFOS.getMode();

		JSONObject dataJson = new JSONObject();

		try {
			dataJson.put("deviceUsersUniqueId", deviceUsersUniqueId);
			dataJson.put("userID", userID);
			dataJson.put("contentId", contentId);
			dataJson.put("videoId", videoId);
			dataJson.put("catId", catId);
			dataJson.put("grade", grade);
			dataJson.put("lessionId", lessionId);
			dataJson.put("versionId", versionId);
			dataJson.put("subjectId", subjectId);
			dataJson.put("source", source);

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
		return FunctionTagTable.ADDPLAYINFOS;
	}
}
