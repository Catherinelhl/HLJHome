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
 * 获取自定义导航内容
 * 
 * @author huangyuchao
 * 
 */
public class GetCustomMetroDataRequest extends BaseRequest implements
		IHomeCallBackRequest {

	public String deviceUsersUniqueId = DeviceCheck.shareDeviceUsersUniqueId();

	public String metroID;

	public String androidId;
	public String deviceTypeName = DeviceInfo.getDeviceName();
	public String androidVersion = DeviceInfo.getAndroidVersion();

	public GetCustomMetroDataRequest(Context context) {

		androidId = DeviceCheck.getAndroidId(context);
	}

	@Override
	public String getInfo() {
		API = FunctionTagTable.GETMETRODATA.getApi();
		VER = FunctionTagTable.GETMETRODATA.getVer();
		MODE = FunctionTagTable.GETMETRODATA.getMode();

		JSONObject dataJson = new JSONObject();
		try {
			dataJson.put("deviceUsersUniqueId", deviceUsersUniqueId);
			dataJson.put("metroID", metroID);

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
		return FunctionTagTable.GETMETRODATA;
	}

}
