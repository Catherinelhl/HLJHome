package com.live.video.net;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.hlj.net.BaseRequest;
import com.hlj.utils.DeviceCheck;
import com.hlj.utils.Logger;
import com.live.video.constans.DeviceInfo;
import com.live.video.constans.HomeConstants.FunctionTagTable;
import com.live.video.net.callback.ICallBackRequest;
import com.live.video.net.callback.IHomeCallBackRequest;

/**
 * 获取回看地址的请求
 * 
 * @author huangyuchao
 * 
 */
public class GetPlayUrlRequest extends BaseRequest implements
		IHomeCallBackRequest {
	public static String deviceUsersUniqueId = DeviceCheck
			.shareDeviceUsersUniqueId();

	// 默认CCTV1
	public static String channel = "cctv1";
	public static String starttime;
	public static String endtime;

	public String androidId;
	public String deviceTypeName = DeviceInfo.getDeviceName();
	public String androidVersion = DeviceInfo.getAndroidVersion();

	public GetPlayUrlRequest(Context context) {
		androidId = DeviceCheck.getAndroidId(context);
	}

	@Override
	public String getInfo() {
		API = FunctionTagTable.GETPLAYURL.getApi();
		VER = FunctionTagTable.GETPLAYURL.getVer();
		MODE = FunctionTagTable.GETPLAYURL.getMode();
		JSONObject dataJson = new JSONObject();
		try {
			dataJson.put("deviceUsersUniqueId", deviceUsersUniqueId);
			dataJson.put("channel", channel);
			dataJson.put("starttime", starttime);
			dataJson.put("endtime", endtime);

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
		return FunctionTagTable.GETPLAYURL;
	}
}
