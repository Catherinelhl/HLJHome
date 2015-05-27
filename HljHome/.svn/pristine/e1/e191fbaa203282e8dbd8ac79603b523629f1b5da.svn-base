package com.live.video.constans;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.hlj.utils.StringTools;
import com.softwinner.SystemMix;

/**
 * Created with IntelliJ IDEA. User: ${shime} Date: 13-9-26 Time: 下午2:29 To
 * change this template use File | Settings | File Templates.
 */
public class EthernetInfo {
	// haxe use
	public static String getMacAddress() {
		String ipAddress = "00.00.00.00.00.00";
		try {
			ipAddress = loadFileAsString("/sys/class/net/eth0/address")
					.toUpperCase().substring(0, 17);

			if (StringTools.isNullOrEmpty(ipAddress)) {
				ipAddress = SystemMix.getCmdPara("mac_addr");
			}

			return ipAddress;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String loadFileAsString(String filePath) throws IOException {
		StringBuffer fileData = new StringBuffer(1024);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
		}
		reader.close();
		return fileData.toString();
	}
}
