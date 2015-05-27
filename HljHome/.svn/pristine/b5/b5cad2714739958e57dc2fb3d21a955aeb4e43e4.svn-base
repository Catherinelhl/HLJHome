package com.hlj.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SwissArmyKnife {

	private static String[] CPU_archs;
	private static final int ELF_HEADER_SIZE = 52;
	private static final int EM_386 = 3;
	private static final int EM_ARM = 40;
	private static final int EM_MIPS = 8;
	private static final int SECTION_HEADER_SIZE = 40;
	private static final int SHT_ARM_ATTRIBUTES = 1879048195;
	private static String TAG;
	private static String errorMsg;
	protected static char[] hexDigits = { 48, 49, 50, 51, 52, 53, 54, 55, 56,
			57, 97, 98, 99, 100, 101, 102 };
	private static boolean isCompatible;

	static {
		TAG = "SK";
		CPU_archs = new String[] { "*Pre-v4", "*v4", "*v4T", "v5T", "v5TE",
				"v5TEJ", "v6", "v6KZ", "v6T2", "v6K", "v7", "*v6-M", "*v6S-M",
				"*v7E-M", "*v8" };
		errorMsg = null;
		isCompatible = false;
	}

	public static String md5(String paramString) {
		try {
			MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
			localMessageDigest.update(paramString.getBytes());
			String str = bufferToHex(localMessageDigest.digest());
			return str;
		} catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
			localNoSuchAlgorithmException.printStackTrace();
		}
		return "";
	}

	private static String bufferToHex(byte[] paramArrayOfByte) {
		return bufferToHex(paramArrayOfByte, 0, paramArrayOfByte.length);
	}

	private static String bufferToHex(byte[] paramArrayOfByte, int paramInt1,
			int paramInt2) {
		StringBuffer localStringBuffer = new StringBuffer(paramInt2 * 2);
		int i = paramInt1 + paramInt2;
		for (int j = paramInt1;; j++) {
			if (j >= i)
				return localStringBuffer.toString();
			appendHexPair(paramArrayOfByte[j], localStringBuffer);
		}
	}

	private static void appendHexPair(byte paramByte,
			StringBuffer paramStringBuffer) {
		char c1 = hexDigits[((paramByte & 0xF0) >> 4)];
		char c2 = hexDigits[(paramByte & 0xF)];
		paramStringBuffer.append(c1);
		paramStringBuffer.append(c2);
	}

	public static int getWeekOfDate() {
		Calendar locaCalendar = Calendar.getInstance();
		locaCalendar.setTime(new Date());
		int i = locaCalendar.get(7) - 1;
		if (i < 0)
			i = 0;
		if (i == 0)
			i = 7;
		return i;
	}

	/**
	 * 获取当前的日期
	 * 
	 * @return
	 */
	public static String getDate() {
		Date localDate = new Date();
		return new SimpleDateFormat("yyyyMMdd").format(localDate);
	}

	/**
	 * 两个时间的比较(与当前时间)
	 * 
	 * @param paramString
	 * @return
	 */
	public static long getTimeDiff(String paramString) {

		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("HH:mm");
		try {
			Date localDate = localSimpleDateFormat.parse(getNowHHmm());
			long l1 = localSimpleDateFormat.parse(paramString).getTime();
			long l2 = localDate.getTime();
			return l1 - l2;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1L;
	}

	/**
	 * 两个日期的比较
	 * 
	 * @param dateStr
	 * @return
	 */
	public static boolean getDateDiff(String dateStr) {
		Date nowDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
		boolean flag = false;

		try {
			Date d = sdf.parse(dateStr);
			// flag = d.before(nowDate);
			if (d.getTime() < nowDate.getTime()) {
				flag = true;
			} else {
				flag = false;
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 截字符串比较时间
	 * @param dateStr
	 * @return
	 */
	public static boolean getDateDiffOther(String dateStr) {
		Date nowDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		boolean flag = false;
		try {
			String[] dateArray = dateStr.split("-");
			int year = Integer.parseInt(dateArray[0]);
			int month = Integer.parseInt(dateArray[1]);
			int day = Integer.parseInt(dateArray[2]);

			String strNowDate = sdf.format(nowDate);
			String[] nowdateArray = strNowDate.split("-");
			int nowyear = Integer.parseInt(nowdateArray[0]);
			int nowmonth = Integer.parseInt(nowdateArray[1]);
			int nowday = Integer.parseInt(nowdateArray[2]);

			if (year > nowyear) {
				flag = true;
			} else {
				if (month > nowmonth) {
					flag = true;
				} else {
					if (day >= nowday) {
						flag = true;
					} else {
						flag = false;
					}
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 得到当前的时和分
	 * 
	 * @return
	 */
	public static String getNowHHmm() {
		return new SimpleDateFormat("HH:mm").format(new Date());
	}

}
