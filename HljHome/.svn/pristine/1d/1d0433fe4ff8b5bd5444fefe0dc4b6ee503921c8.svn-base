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
 * 取消收藏请求
 * 
 * @author huangyuchao
 * 
 */
public class DeleteFavRequest extends BaseRequest implements
		IHomeCallBackRequest {
	public static String deviceUsersUniqueId = DeviceCheck
			.shareDeviceUsersUniqueId();

	Context mContext;
	public String wifiMac;
	public String ethernetMac;
	public String deviceTypeName = DeviceInfo.getDeviceName();
	public String androidVersion = DeviceInfo.getAndroidVersion();

	public DeleteFavRequest(Context context) {
		this.mContext = context;
		androidId = DeviceInfo.getOnlyId(mContext);
		wifiMac = DeviceCheck.getWifiMac(mContext);
		ethernetMac = DeviceCheck.getEthernetMac(mContext);
	}

	public String androidId;
	public String deviceBrand = DeviceInfo.getBrand();
	public String userID = DeviceCheck.shareUserId();
	public int id;

	@Override
	public String getInfo() {
		API = FunctionTagTable.DELFAV.getApi();
		VER = FunctionTagTable.DELFAV.getVer();
		MODE = FunctionTagTable.DELFAV.getMode();
		JSONObject dataJson = new JSONObject();
		try {
			dataJson.put("deviceUsersUniqueId", deviceUsersUniqueId);
			dataJson.put("wifiMac", wifiMac);
			dataJson.put("ethernetMac", ethernetMac);
			dataJson.put("androidId", androidId);
			dataJson.put("androidVersion", androidVersion);
			dataJson.put("deviceTypeName", deviceTypeName);

			dataJson.put("deviceBrand", deviceBrand);
			dataJson.put("id", id);
			dataJson.put("userID", userID);
		} catch (JSONException e) {
			Logger.log("getInfo error" + e.getLocalizedMessage());
		}
		return getJsonString(dataJson);
	}

	@Override
	public FunctionTagTable getNetTag() {
		return FunctionTagTable.DELFAV;
	}
}
