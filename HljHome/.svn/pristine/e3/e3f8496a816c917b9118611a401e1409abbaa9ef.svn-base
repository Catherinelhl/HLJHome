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
 * 根据条件获取栏目下的子栏目
 * 
 * @author huangyuchao
 * 
 */
public class GetCategoryListRequest extends BaseRequest implements
		IHomeCallBackRequest {
	public String deviceUsersUniqueId = DeviceCheck.shareDeviceUsersUniqueId();

	public int catId;
	public String randomString = DeviceCheck.getRandomUUID();

	public int page = 1;
	public int pageSize = 9999;
	public String[] cItem;

	public String androidId;
	public String deviceTypeName = DeviceInfo.getDeviceName();
	public String androidVersion = DeviceInfo.getAndroidVersion();

	public GetCategoryListRequest(Context context) {
		androidId = DeviceCheck.getAndroidId(context);
	}

	@Override
	public String getInfo() {
		API = FunctionTagTable.GETCATEGORYLIST.getApi();
		VER = FunctionTagTable.GETCATEGORYLIST.getVer();
		MODE = FunctionTagTable.GETCATEGORYLIST.getMode();
		JSONObject dataJson = new JSONObject();
		try {
			dataJson.put("deviceUsersUniqueId", deviceUsersUniqueId);
			dataJson.put("catId", catId);
			dataJson.put("randomString", randomString);
			dataJson.put("page", page);
			dataJson.put("pageSize", pageSize);
			dataJson.put("cItem", cItem);

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
		return FunctionTagTable.GETCATEGORYLIST;
	}
}
