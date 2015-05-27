package com.live.video.net;

import android.content.Context;

import com.hlj.net.BaseRequest;
import com.hlj.utils.DeviceCheck;
import com.live.video.constans.DeviceInfo;
import com.live.video.constans.HomeConstants;
import com.live.video.net.callback.IHomeCallBackRequest;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA. User: ${shime} Date: 13-11-16 Time: 下午2:11 To
 * change this template use File | Settings | File Templates.
 */
public class GetParentalControlRequest extends BaseRequest implements
		IHomeCallBackRequest {
	public String deviceUsersUniqueId = DeviceCheck.shareDeviceUsersUniqueId();
	public String userID = DeviceCheck.shareUserId();
	public String userPasswdOld;
	public String userPasswdNew;

	public String androidId;
	public String deviceTypeName = DeviceInfo.getDeviceName();
	public String androidVersion = DeviceInfo.getAndroidVersion();

	public GetParentalControlRequest(Context context) {
		androidId = DeviceCheck.getAndroidId(context);
	}

	@Override
	public String getInfo() {
		API = HomeConstants.FunctionTagTable.GETPARENTALCONTROLINFO.getApi();
		VER = HomeConstants.FunctionTagTable.GETPARENTALCONTROLINFO.getVer();
		MODE = HomeConstants.FunctionTagTable.GETPARENTALCONTROLINFO.getMode();
		JSONObject dataJson = new JSONObject();
		try {
			dataJson.put("deviceUsersUniqueId", deviceUsersUniqueId);
			dataJson.put("userID", userID);
			dataJson.put("userPasswd", userPasswdOld);
			dataJson.put("userPasswdNew", userPasswdNew);

			dataJson.put("androidId", androidId);
			dataJson.put("androidVersion", androidVersion);
			dataJson.put("deviceTypeName", deviceTypeName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getJsonString(dataJson); // To change body of implemented methods
										// use File | Settings | File Templates.
	}

	@Override
	public HomeConstants.FunctionTagTable getNetTag() {
		return HomeConstants.FunctionTagTable.GETPARENTALCONTROLINFO; // To
																		// change
																		// body
																		// of
																		// implemented
																		// methods
																		// use
																		// File
																		// |
																		// Settings
																		// |
																		// File
																		// Templates.
	}
}
