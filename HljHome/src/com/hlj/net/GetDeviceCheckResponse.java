package com.hlj.net;

import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * User: ${shime}
 * Date: 13-11-26
 * Time: 上午10:05
 * To change this template use File | Settings | File Templates.
 */
public class GetDeviceCheckResponse extends BaseResponse  {
    public DeviceData deviceData=new DeviceData();
	public class DeviceData{
		public DeviceData() {
		}
		public String deviceUsersUniqueId;
		public String user_id;
		public String nick_id;
		public String session_id;
		public String has_update;
		public String tags;
	}



	@Override
	public void paseRespones(String js) {
		try {
			JSONObject obj=new JSONObject(js);
			deviceData.deviceUsersUniqueId=obj.getString("deviceUsersUniqueId");
			deviceData.user_id=obj.getString("user_id");
			deviceData.nick_id=obj.getString("nick_id");
			deviceData.session_id=obj.getString("session_id");
			deviceData.has_update=obj.getString("has_update");
			deviceData.tags=obj.getString("tags");
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
