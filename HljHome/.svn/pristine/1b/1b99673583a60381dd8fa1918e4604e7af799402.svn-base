package com.live.video.constans;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import softwinner.os.ChipInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.SystemProperties;
import android.provider.Settings;
/**
 * 
 * @author hyc
 *
 */
public class DeviceInfo {

	public static final String UNKNOWN = "unknown";

	/**
	 * 固件版本
	 */
	public static String getFirmWare() {
		String firmware = getString("ro.product.firmware");
		return firmware;
	}

	/**
	 * launcher版本
	 */
	public static String getVerName(Context context) {
		String str = null;
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			str = info.versionName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 唯一标示
	 * 
	 * @param context
	 * @return
	 */
	public static String getOnlyId(Context context) {

		return Settings.Secure.getString(context.getContentResolver(),
				Settings.Secure.ANDROID_ID);

	}

	/**
	 * 获取CPU的ID
	 * 
	 * @return
	 */
	public static String getCpuID() {
		return ChipInfo.getChipID();
	}

	public static String getCpuIDHex() {
		return ChipInfo.getChipIDHex();
	}

	/**
	 * 获取Cpu名字
	 * 
	 * @return
	 */
	public static String getCpuName() {
		try {
			FileReader fr = new FileReader("/proc/cpuinfo");
			BufferedReader br = new BufferedReader(fr);
			String text = br.readLine();
			String[] array = text.split(":\\s+", 2);
			for (int i = 0; i < array.length; i++) {
			}
			return array[1];
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	// 设备基板名称
	public static String getBoardName() {
		return Build.BOARD;
	}

	// 设备引导程序版本号
	public static String getBootloader() {
		return Build.BOOTLOADER;
	}

	// 设备品牌
	public static String getBrand() {
		return Build.BRAND;
	}

	// 设备显示的版本包
	public static String getDisplay() {
		return Build.DISPLAY;
	}

	// 设备的唯一标识。由设备的多个信息拼接合成
	public static String getFingerPrint() {
		return Build.FINGERPRINT;
	}

	// 设备硬件名称
	public static String getHardware() {
		return Build.HARDWARE;
	}

	// 设备主机地址
	public static String getHost() {
		return Build.HOST;
	}

	// 设备版本号
	public static String getId() {
		return Build.ID;
	}

	// 型号
	public static String getModel() {
		return Build.MODEL;
	}

	// 设备制造商
	public static String getManufacturer() {
		return Build.MANUFACTURER;
	}

	// 整个产品的名称
	public static String getProductName() {
		return Build.PRODUCT;
	}

	// 设备标签
	public static String getTags() {
		return Build.TAGS;
	}

	// 设备版本类型
	public static String getType() {
		return Build.TYPE;
	}

	// 设备用户名
	public static String getUser() {
		return Build.USER;
	}

	// 设备当前的系统开发代号
	public static String getCodeName() {
		return Build.VERSION.CODENAME;
	}

	// 系统源代码控制值
	public static String getIncremental() {
		return Build.VERSION.INCREMENTAL;
	}

	// android 版本
	public static String getAndroidVersion() {
		return Build.VERSION.RELEASE;
	}

	// 设备名称
	public static String getDeviceName() {
		return Build.MODEL;
	}

	// 系统版本
	public static String getSystemVersion() {
		return Build.DISPLAY;
	}

	// 系统设备驱动
	public static String getDevice() {
		return Build.DEVICE;
	}

	private static String getString(String property) {
		return SystemProperties.get(property, UNKNOWN);
	}

}
