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
 * 获取收藏列表请求
 * 
 * @author huangyuchao
 * 
 */
public class GetFavListRequest extends BaseRequest implements
		IHomeCallBackRequest {
	public static String deviceUsersUniqueId = DeviceCheck
			.shareDeviceUsersUniqueId();

	Context mContext;
	public String wifiMac;
	public String ethernetMac;
	public String deviceTypeName = DeviceInfo.getDeviceName();
	public String androidVersion = DeviceInfo.getAndroidVersion();

	public GetFavListRequest(Context context) {
		this.mContext = context;
		androidId = DeviceInfo.getOnlyId(mContext);
		wifiMac = DeviceCheck.getWifiMac(mContext);
		ethernetMac = DeviceCheck.getEthernetMac(mContext);
	}

	public String androidId;
	public String deviceBrand = DeviceInfo.getBrand();
	public String type = "source";

	public String userID = DeviceCheck.shareUserId();

	@Override
	public String getInfo() {
		API = FunctionTagTable.GETFAVLIST.getApi();
		VER = FunctionTagTable.GETFAVLIST.getVer();
		MODE = FunctionTagTable.GETFAVLIST.getMode();
		JSONObject dataJson = new JSONObject();
		try {
			dataJson.put("deviceUsersUniqueId", deviceUsersUniqueId);
			dataJson.put("wifiMac", wifiMac);
			dataJson.put("ethernetMac", ethernetMac);
			dataJson.put("androidId", androidId);
			dataJson.put("androidVersion", androidVersion);
			dataJson.put("deviceTypeName", deviceTypeName);

			dataJson.put("deviceBrand", deviceBrand);
			dataJson.put("type", type);
			dataJson.put("userID", userID);
		} catch (JSONException e) {
			Logger.log("getInfo error" + e.getLocalizedMessage());
		}
		return getJsonString(dataJson);
	}

	@Override
	public FunctionTagTable getNetTag() {
		return FunctionTagTable.GETFAVLIST;
	}
}
