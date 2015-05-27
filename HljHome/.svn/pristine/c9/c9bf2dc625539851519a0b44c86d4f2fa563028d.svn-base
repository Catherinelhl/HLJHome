package com.live.video.net;

import com.hlj.net.BaseRequest;
import com.hlj.utils.DeviceCheck;
import com.live.video.constans.HomeConstants;
import com.live.video.net.callback.IHomeCallBackRequest;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA. User: ${shime} Date: 13-11-18 Time: 下午2:03 To
 * change this template use File | Settings | File Templates.
 */
public class GetPwdRequest extends BaseRequest implements IHomeCallBackRequest {
	public String deviceUsersUniqueId = DeviceCheck.shareDeviceUsersUniqueId();
	public String userID = DeviceCheck.shareUserId();
	public String lockPasswd;

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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getJsonString(dataJson); // To change body of implemented methods
										// use File | Settings | File Templates.
	}

	@Override
	public HomeConstants.FunctionTagTable getNetTag() {
		return HomeConstants.FunctionTagTable.GETPWDINFO; // To change body of
															// implemented
															// methods use File
															// | Settings | File
															// Templates.
	}
}
