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
 * 添加收藏请求
 * 
 * @author huangyuchao
 * 
 */
public class AddFavRequest extends BaseRequest implements IHomeCallBackRequest {
	public static String deviceUsersUniqueId = DeviceCheck
			.shareDeviceUsersUniqueId();

	Context mContext;
	public String wifiMac;
	public String ethernetMac;

	public AddFavRequest(Context context) {
		this.mContext = context;
		androidId = DeviceInfo.getOnlyId(mContext);
		wifiMac = DeviceCheck.getWifiMac(mContext);
		ethernetMac = DeviceCheck.getEthernetMac(mContext);
	}

	public String androidId;
	public String deviceBrand = DeviceInfo.getBrand();
	public String sourceID;
	public String url = "";
	public String type = "source";// (source|channel|push)
	public String title = "";
	public String userID = DeviceCheck.shareUserId();
	
	public String deviceTypeName = DeviceInfo.getDeviceName();
	public String androidVersion = DeviceInfo.getAndroidVersion();



	@Override
	public String getInfo() {
		API = FunctionTagTable.ADDFAV.getApi();
		VER = FunctionTagTable.ADDFAV.getVer();
		MODE = FunctionTagTable.ADDFAV.getMode();
		JSONObject dataJson = new JSONObject();
		try {
			dataJson.put("deviceUsersUniqueId", deviceUsersUniqueId);
			dataJson.put("wifiMac", wifiMac);
			dataJson.put("ethernetMac", ethernetMac);
			dataJson.put("androidId", androidId);

			dataJson.put("deviceBrand", deviceBrand);
			dataJson.put("sourceID", sourceID);
			dataJson.put("url", url);
			Logger.log("type:" + type);
			dataJson.put("type", type);
			dataJson.put("userID", userID);
			dataJson.put("title", title);
			dataJson.put("androidVersion", androidVersion);
			dataJson.put("deviceTypeName", deviceTypeName);
			
			
		} catch (JSONException e) {
			Logger.log("getInfo error" + e.getLocalizedMessage());
		}
		return getJsonString(dataJson);
	}

	@Override
	public FunctionTagTable getNetTag() {
		return FunctionTagTable.ADDFAV;
	}
}
