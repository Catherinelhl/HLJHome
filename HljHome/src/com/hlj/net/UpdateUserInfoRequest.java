package com.hlj.net;

import java.util.ArrayList;

import org.json.JSONArray;
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
 * 更新用户信息请求
 * 
 * @author huangyuchao
 * 
 */
public class UpdateUserInfoRequest extends BaseRequest implements
		IHomeCallBackRequest {
	public static String deviceUsersUniqueId = DeviceCheck
			.shareDeviceUsersUniqueId();
	public String userID = DeviceCheck.shareUserId();

	public String userGrade;

	public ArrayList<UserTeachingMeterial> list = new ArrayList<UserTeachingMeterial>();

	public class UserTeachingMeterial {
		public String subject;
		public String versions;
	}

	public String androidId;
	public String deviceTypeName = DeviceInfo.getDeviceName();
	public String androidVersion = DeviceInfo.getAndroidVersion();

	public UpdateUserInfoRequest(Context context) {
		androidId = DeviceCheck.getAndroidId(context);
	}

	@Override
	public String getInfo() {
		API = FunctionTagTable.UPDATEUSERINFO.getApi();
		VER = FunctionTagTable.UPDATEUSERINFO.getVer();
		MODE = FunctionTagTable.UPDATEUSERINFO.getMode();
		JSONObject dataJson = new JSONObject();
		try {
			dataJson.put("deviceUsersUniqueId", deviceUsersUniqueId);
			dataJson.put("userID", userID);
			dataJson.put("userGrade", userGrade);

			dataJson.put("androidId", androidId);
			dataJson.put("androidVersion", androidVersion);
			dataJson.put("deviceTypeName", deviceTypeName);

			JSONArray array = new JSONArray();
			for (int i = 0; i < list.size(); i++) {
				JSONObject json = new JSONObject();
				json.put("subject", list.get(i).subject);
				json.put("versions", list.get(i).versions);
				array.put(json);
			}
			dataJson.put("userTeachingMaterial", array);
		} catch (JSONException e) {
			Logger.log("getInfo error" + e.getLocalizedMessage());
		}
		return getJsonString(dataJson);
	}

	@Override
	public FunctionTagTable getNetTag() {
		return FunctionTagTable.UPDATEUSERINFO;
	}
}
