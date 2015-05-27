package com.hlj.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {
	protected static char[] hexDigits = { 48, 49, 50, 51, 52, 53, 54, 55, 56,
			57, 97, 98, 99, 100, 101, 102 };
	protected static MessageDigest messagedigest = null;

	static {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
			System.err.println(Md5Utils.class.getName()
					+ "初始化失败，MessageDigest不支持MD5Util。");
			localNoSuchAlgorithmException.printStackTrace();
		}
	}

	private static void appendHexPair(byte paramByte,
			StringBuffer paramStringBuffer) {
		char c1 = hexDigits[((paramByte & 0xF0) >> 4)];
		char c2 = hexDigits[(paramByte & 0xF)];
		paramStringBuffer.append(c1);
		paramStringBuffer.append(c2);
	}

	private static String bufferToHex(byte[] paramArrayOfByte) {
		return bufferToHex(paramArrayOfByte, 0, paramArrayOfByte.length);
	}

	private static String bufferToHex(byte[] paramArrayOfByte, int paramInt1,
			int paramInt2) {
		StringBuffer localStringBuffer = new StringBuffer(paramInt2 * 2);
		int i = paramInt1 + paramInt2;
		for (int j = paramInt1;; ++j) {
			if (j >= i)
				return localStringBuffer.toString();
			appendHexPair(paramArrayOfByte[j], localStringBuffer);
		}
	}

	public static boolean checkPassword(String paramString1, String paramString2) {
		return getMD5String(paramString1).equals(paramString2);
	}

	public static String getFileMD5String(File paramFile) throws IOException {
		FileInputStream localFileInputStream = new FileInputStream(paramFile);
		byte[] arrayOfByte = new byte[1024];
		while (true) {
			int i = localFileInputStream.read(arrayOfByte);
			if (i <= 0) {
				localFileInputStream.close();
				return bufferToHex(messagedigest.digest());
			}
			messagedigest.update(arrayOfByte, 0, i);
		}
	}

	public static String getFileMD5String_old(File paramFile)
			throws IOException {
		MappedByteBuffer localMappedByteBuffer = new FileInputStream(paramFile)
				.getChannel().map(FileChannel.MapMode.READ_ONLY, 0L,
						paramFile.length());
		messagedigest.update(localMappedByteBuffer);
		return bufferToHex(messagedigest.digest());
	}

	public static String getMD5String(String paramString) {
		return getMD5String(paramString.getBytes());
	}

	public static String getMD5String(byte[] paramArrayOfByte) {
		messagedigest.update(paramArrayOfByte);
		return bufferToHex(messagedigest.digest());
	}

}
