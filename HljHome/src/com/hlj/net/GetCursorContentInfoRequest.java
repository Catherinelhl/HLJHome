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
 * 获取课程的信息
 * 
 * @author huangyuchao
 * 
 */
public class GetCursorContentInfoRequest extends BaseRequest implements
		IHomeCallBackRequest {
	public String deviceUsersUniqueId = DeviceCheck.shareDeviceUsersUniqueId();

	public String randomString = "test";

	public String contentId;// 课程id
	public String grade;
	public String subject;// 科目
	public int catId;// 栏目id
	public String versions;

	public String androidId;
	public String deviceTypeName = DeviceInfo.getDeviceName();
	public String androidVersion = DeviceInfo.getAndroidVersion();

	public GetCursorContentInfoRequest(Context context) {
		androidId = DeviceCheck.getAndroidId(context);
	}

	@Override
	public String getInfo() {
		API = FunctionTagTable.GETCOURSECONTENTINFO.getApi();
		VER = FunctionTagTable.GETCOURSECONTENTINFO.getVer();
		MODE = FunctionTagTable.GETCOURSECONTENTINFO.getMode();
		JSONObject dataJson = new JSONObject();
		try {
			dataJson.put("deviceUsersUniqueId", deviceUsersUniqueId);
			dataJson.put("randomString", randomString);

			dataJson.put("contentId", contentId);
			dataJson.put("grade", grade);
			dataJson.put("subject", subject);
			dataJson.put("catId", catId);
			dataJson.put("versions", versions);

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
		return FunctionTagTable.GETCOURSECONTENTINFO;
	}
}
