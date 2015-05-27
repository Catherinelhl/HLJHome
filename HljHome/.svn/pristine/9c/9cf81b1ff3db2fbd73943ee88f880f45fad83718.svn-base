package com.hlj.net;

import android.content.Context;

import com.hlj.utils.DeviceCheck;
import com.live.video.constans.DeviceInfo;
import com.live.video.constans.HomeConstants;
import com.live.video.net.callback.IHomeCallBackRequest;
import org.json.JSONObject;

/**
 * 
 * @author hyc
 *
 */
public class GetPwdRequest extends BaseRequest implements IHomeCallBackRequest {
	public String deviceUsersUniqueId = DeviceCheck.shareDeviceUsersUniqueId();
	public String userID = DeviceCheck.shareUserId();
	public String lockPasswd;

	public String androidId;
	public String deviceTypeName = DeviceInfo.getDeviceName();
	public String androidVersion = DeviceInfo.getAndroidVersion();

	public GetPwdRequest(Context context) {
		androidId = DeviceCheck.getAndroidId(context);
	}

	@Override
	public String getInfo() {
		API = HomeConstants.FunctionTagTable.GETPWDINFO.getApi();
		VER = HomeConstants.FunctionTagTable.GETPWDINFO.getVer();
		MODE = HomeConstants.FunctionTagTable.GETPWDINFO.getMode();
		JSONObject dataJson = new JSONObject();
		try {
			dataJson.put("deviceUsersUniqueId", deviceUsersUniqueId);
			dataJson.put("userID", userID);
			dataJson.put("lockPasswd", lockPasswd);

			dataJson.put("androidId", androidId);
			dataJson.put("androidVersion", androidVersion);
			dataJson.put("deviceTypeName", deviceTypeName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getJsonString(dataJson); 
	}

	@Override
	public HomeConstants.FunctionTagTable getNetTag() {
		return HomeConstants.FunctionTagTable.GETPWDINFO; 
	}
}
