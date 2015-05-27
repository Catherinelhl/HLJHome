package com.hlj.net;

import android.content.Context;
import android.util.Log;
import com.hlj.utils.DeviceCheck;
import com.hlj.utils.NetUtils;
import com.live.video.constans.DeviceInfo;
import com.live.video.constans.HomeConstants;
import com.live.video.net.callback.IHomeCallBackRequest;
import org.json.JSONObject;

/**
 * 
 * @author hyc
 *
 */
public class GetAllAppVersionRequest extends BaseRequest implements
		IHomeCallBackRequest {
	public String updating_apk_version;
	public String deviceUsersUniqueId = DeviceCheck.shareDeviceUsersUniqueId();
	public String brand = DeviceInfo.getBrand();
	public String device = DeviceInfo.getDevice();
	public String board = DeviceInfo.getBoardName();
	public String firmware = DeviceInfo.getFirmWare();
	public String android = DeviceInfo.getAndroidVersion();
	public String time = DeviceInfo.getDeviceName();
	public String builder = DeviceInfo.getManufacturer();
	public String mac;
	public String deviceFingerPrint = DeviceInfo.getFingerPrint();
	public String service_type = "";
	public String para_serial = "";

	public String androidId;
	public String deviceTypeName = DeviceInfo.getDeviceName();
	public String androidVersion = DeviceInfo.getAndroidVersion();

	public GetAllAppVersionRequest(Context context) {
		updating_apk_version = DeviceInfo.getVerName(context);
		if (NetUtils.getAPNType(context) != -1) {
			if (NetUtils.isEthernetConnect(context)) {
				mac = DeviceCheck.getEthernetMac(context);
			} else if (NetUtils.isWifiConnect(context)) {
				mac = DeviceCheck.getWifiMac(context);
			}
		}
		androidId = DeviceCheck.getAndroidId(context);
	}

	@Override
	public String getInfo() {
		API = HomeConstants.FunctionTagTable.GETALLAPPVERSION.getApi();
		VER = HomeConstants.FunctionTagTable.GETALLAPPVERSION.getVer();
		MODE = HomeConstants.FunctionTagTable.GETALLAPPVERSION.getMode();
		JSONObject dataJson = new JSONObject();
		try {
			dataJson.put("updating_apk_version", updating_apk_version);
			dataJson.put("deviceUsersUniqueId", deviceUsersUniqueId);
			dataJson.put("brand", brand);
			dataJson.put("device", device);
			dataJson.put("board", board);
			dataJson.put("firmware", firmware);
			dataJson.put("android", android);
			dataJson.put("time", time);
			dataJson.put("builder", builder);
			dataJson.put("mac", mac);
			dataJson.put("deviceFingerPrint", deviceFingerPrint);
			dataJson.put("service_type", service_type);
			dataJson.put("para_serial", para_serial);
			dataJson.put("androidId", androidId);
			dataJson.put("androidVersion", androidVersion);
			dataJson.put("deviceTypeName", deviceTypeName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.d("======GetCheckUpdateLauncherRequest======", ""
				+ getJsonString(dataJson));
		return getJsonString(dataJson);
	}

	@Override
	public HomeConstants.FunctionTagTable getNetTag() {
		return HomeConstants.FunctionTagTable.GETALLAPPVERSION;
	}
}
