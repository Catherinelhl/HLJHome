package com.live.video.constans;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created with IntelliJ IDEA.
 * User: ${shime}
 * Date: 13-9-26
 * Time: 下午2:24
 * To change this template use File | Settings | File Templates.
 */
public class WifiimplInfo {
	public 	static WifiManager mWifiManager=null;
	//定义一个WifiInfo对象
	public static WifiInfo mWifiInfo=null;
	//取得DhcpInfo对象
	public static DhcpInfo dhcpInfo=null;

	public static WifiManager getWifiManager(Context context){
	    mWifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		return mWifiManager;
	}

	public static WifiInfo getWifiInfo(Context context){

		if (mWifiInfo==null){
			mWifiInfo=getWifiManager(context).getConnectionInfo();
		}
		return mWifiInfo;
	}
	public  static DhcpInfo getDhcpInfo(Context context){
		if(dhcpInfo==null)
			dhcpInfo=getWifiManager(context).getDhcpInfo();
		return dhcpInfo;
	}

	/**
	 * haxe use
	 * mac地址
	 */
	public static String getMacAddress(Context context){
		String ipAddress="00.00.00.00.00.00";
		if(getWifiInfo(context)!=null){
			ipAddress=mWifiInfo.getMacAddress();
			return  ipAddress.trim();
		}
		return  ipAddress.trim();
	}

}
