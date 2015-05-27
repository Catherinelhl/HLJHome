package com.hlj.net;

import android.content.Context;
import com.hlj.utils.DeviceCheck;
import com.hlj.utils.WeatherUtils;
import com.live.video.constans.DeviceInfo;
import com.live.video.constans.HomeConstants;
import com.live.video.net.callback.IHomeCallBackRequest;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: ${shime}
 * Date: 13-11-26
 * Time: 下午12:13
 * To change this template use File | Settings | File Templates.
 */
public class GetWeatherRequest extends BaseRequest implements IHomeCallBackRequest {
	public String userID= DeviceCheck.shareUserId();
	public String areaID;
	public String deviceUsersUniqueId= DeviceCheck.shareDeviceUsersUniqueId();
	
	public String androidId;
	public String deviceTypeName = DeviceInfo.getDeviceName();
	public String androidVersion = DeviceInfo.getAndroidVersion();

	public GetWeatherRequest(Context context) {
	   areaID= WeatherUtils.getCityCode(context);
	   
	   androidId = DeviceCheck.getAndroidId(context);
	}

	@Override
	public String getInfo() {
		API= HomeConstants.FunctionTagTable.GETWEATHER.getApi();
		VER= HomeConstants.FunctionTagTable.GETWEATHER.getVer();
		MODE= HomeConstants.FunctionTagTable.GETWEATHER.getMode();
		JSONObject dataJson=new JSONObject();
		try{
			dataJson.put("userID",userID);
			dataJson.put("areaID",areaID);
			dataJson.put("deviceUsersUniqueId",deviceUsersUniqueId);
			
			dataJson.put("androidId", androidId);
			dataJson.put("androidVersion", androidVersion);
			dataJson.put("deviceTypeName", deviceTypeName);
		}catch (Exception e){
			e.printStackTrace();
		}
		return getJsonString(dataJson);
	}

	@Override
	public HomeConstants.FunctionTagTable getNetTag() {
		return HomeConstants.FunctionTagTable.GETWEATHER;  //To change body of implemented methods use File | Settings | File Templates.
	}
}
