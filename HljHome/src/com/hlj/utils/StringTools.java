package com.hlj.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;

import com.hlj.HomeActivity.R;

/**
 * String转换的工具
 * 
 * @author huangyuchao
 * 
 */
public class StringTools {

	/**
	 * @param isostr
	 * @return 默认值变成utf-8类型
	 */
	public static String defaultToUtf(String isostr) {
		if (isostr != null) {
			try {
				isostr = new String(isostr.getBytes(), "UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return isostr;
	}

	/**
	 * 检测字符串是否为空或无内容
	 * 
	 * @param srcString
	 * @return
	 */
	public static boolean isNullOrEmpty(String srcString) {
		if (srcString != null && !srcString.equals("")
				&& !srcString.equals("null")) {
			return false;
		} else {
			return true;
		}
	}

	public static String getSystemTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Date date = new Date();
		String time = sdf.format(date);
		return time;
	}

	public static String getCurrentGrade(String currentGrade) {
		String numGrage = "";
		if ("一年级".equals(currentGrade)) {
			numGrage = "1";
		} else if ("二年级".equals(currentGrade)) {
			numGrage = "2";
		} else if ("三年级".equals(currentGrade)) {
			numGrage = "3";
		} else if ("四年级".equals(currentGrade)) {
			numGrage = "4";
		} else if ("五年级".equals(currentGrade)) {
			numGrage = "5";
		} else if ("六年级".equals(currentGrade)) {
			numGrage = "6";
		} else if ("初一".equals(currentGrade)) {
			numGrage = "7";
		} else if ("初二".equals(currentGrade)) {
			numGrage = "8";
		} else if ("初三".equals(currentGrade)) {
			numGrage = "9";
		} else if ("高一".equals(currentGrade)) {
			numGrage = "10";
		} else if ("高二".equals(currentGrade)) {
			numGrage = "11";
		} else if ("高三".equals(currentGrade)) {
			numGrage = "12";
		}
		return numGrage;
	}

	public static int getTermResource(String currentTerm) {
		int resource = 0;
		if ("上学期".equals(currentTerm)) {
			resource = R.drawable.home_grade_up;
		} else if ("下学期".equals(currentTerm)) {
			resource = R.drawable.home_grade_down;
		}
		return resource;
	}

	/**
	 * 获取上传的grade
	 * 
	 * @param context
	 * @return
	 */
	public static int getCurrentGrade(Context context) {
		String strGrade = PrefrenceHandler.getGradeStr(context);
		String strTerm = PrefrenceHandler.getTermStr(context);
		int numGrade = 0;
		if ("一年级".equals(strGrade)) {
			if ("上学期".equals(strTerm)) {
				numGrade = 1;
			} else if ("下学期".equals(strTerm)) {
				numGrade = 2;
			}
		} else if ("二年级".equals(strGrade)) {
			if ("上学期".equals(strTerm)) {
				numGrade = 3;
			} else if ("下学期".equals(strTerm)) {
				numGrade = 4;
			}
		} else if ("三年级".equals(strGrade)) {
			if ("上学期".equals(strTerm)) {
				numGrade = 5;
			} else if ("下学期".equals(strTerm)) {
				numGrade = 6;
			}
		} else if ("四年级".equals(strGrade)) {
			if ("上学期".equals(strTerm)) {
				numGrade = 7;
			} else if ("下学期".equals(strTerm)) {
				numGrade = 8;
			}
		} else if ("五年级".equals(strGrade)) {
			if ("上学期".equals(strTerm)) {
				numGrade = 9;
			} else if ("下学期".equals(strTerm)) {
				numGrade = 10;
			}
		} else if ("六年级".equals(strGrade)) {
			if ("上学期".equals(strTerm)) {
				numGrade = 11;
			} else if ("下学期".equals(strTerm)) {
				numGrade = 12;
			}
		} else if ("初一".equals(strGrade)) {
			if ("上学期".equals(strTerm)) {
				numGrade = 13;
			} else if ("下学期".equals(strTerm)) {
				numGrade = 14;
			}
		} else if ("初二".equals(strGrade)) {
			if ("上学期".equals(strTerm)) {
				numGrade = 15;
			} else if ("下学期".equals(strTerm)) {
				numGrade = 16;
			}
		} else if ("初三".equals(strGrade)) {
			if ("上学期".equals(strTerm)) {
				numGrade = 17;
			} else if ("下学期".equals(strTerm)) {
				numGrade = 18;
			}
		} else if ("高一".equals(strGrade)) {
			if ("上学期".equals(strTerm)) {
				numGrade = 19;
			} else if ("下学期".equals(strTerm)) {
				numGrade = 20;
			}
		} else if ("高二".equals(strGrade)) {
			if ("上学期".equals(strTerm)) {
				numGrade = 21;
			} else if ("下学期".equals(strTerm)) {
				numGrade = 22;
			}
		} else if ("高三".equals(strGrade)) {
			if ("上学期".equals(strTerm)) {
				numGrade = 23;
			} else if ("下学期".equals(strTerm)) {
				numGrade = 24;
			}
		}
		return numGrade;
	}

	/**
	 * 由返回的年级数确定年级
	 * 
	 * @param context
	 * @return
	 */
	public static String getStrGrade(int grade) {
		String strGrade = "";
		if (grade == 1) {
			strGrade = "一年级";
		} else if (grade == 2) {
			strGrade = "一年级";
		} else if (grade == 3) {
			strGrade = "二年级";
		} else if (grade == 4) {
			strGrade = "二年级";
		} else if (grade == 5) {
			strGrade = "三年级";
		} else if (grade == 6) {
			strGrade = "三年级";
		} else if (grade == 7) {
			strGrade = "四年级";
		} else if (grade == 8) {
			strGrade = "四年级";
		} else if (grade == 9) {
			strGrade = "五年级";
		} else if (grade == 10) {
			strGrade = "五年级";
		} else if (grade == 11) {
			strGrade = "六年级";
		} else if (grade == 12) {
			strGrade = "六年级";
		} else if (grade == 13) {
			strGrade = "初一";
		} else if (grade == 14) {
			strGrade = "初一";
		} else if (grade == 15) {
			strGrade = "初二";
		} else if (grade == 16) {
			strGrade = "初二";
		} else if (grade == 17) {
			strGrade = "初三";
		} else if (grade == 18) {
			strGrade = "初三";
		} else if (grade == 19) {
			strGrade = "高一";
		} else if (grade == 20) {
			strGrade = "高一";
		} else if (grade == 21) {
			strGrade = "高二";
		} else if (grade == 22) {
			strGrade = "高二";
		} else if (grade == 23) {
			strGrade = "高三";
		} else if (grade == 24) {
			strGrade = "高三";
		}
		return strGrade;
	}

	/**
	 * 由返回的年级数确定年级
	 * 
	 * @param context
	 * @return
	 */
	public static String getStrGrade(String strgrade) {
		int grade = Integer.parseInt(strgrade);
		String strGrade = "";
		if (grade == 1) {
			strGrade = "一年级";
		} else if (grade == 2) {
			strGrade = "一年级";
		} else if (grade == 3) {
			strGrade = "二年级";
		} else if (grade == 4) {
			strGrade = "二年级";
		} else if (grade == 5) {
			strGrade = "三年级";
		} else if (grade == 6) {
			strGrade = "三年级";
		} else if (grade == 7) {
			strGrade = "四年级";
		} else if (grade == 8) {
			strGrade = "四年级";
		} else if (grade == 9) {
			strGrade = "五年级";
		} else if (grade == 10) {
			strGrade = "五年级";
		} else if (grade == 11) {
			strGrade = "六年级";
		} else if (grade == 12) {
			strGrade = "六年级";
		} else if (grade == 13) {
			strGrade = "初一";
		} else if (grade == 14) {
			strGrade = "初一";
		} else if (grade == 15) {
			strGrade = "初二";
		} else if (grade == 16) {
			strGrade = "初二";
		} else if (grade == 17) {
			strGrade = "初三";
		} else if (grade == 18) {
			strGrade = "初三";
		} else if (grade == 19) {
			strGrade = "高一";
		} else if (grade == 20) {
			strGrade = "高一";
		} else if (grade == 21) {
			strGrade = "高二";
		} else if (grade == 22) {
			strGrade = "高二";
		} else if (grade == 23) {
			strGrade = "高三";
		} else if (grade == 24) {
			strGrade = "高三";
		}
		return strGrade;
	}

	/**
	 * 由年级数确定学期
	 * 
	 * @param grade
	 * @return
	 */
	public static String getStrTerm(int grade) {
		String strTerm = "";
		if (grade % 2 == 1) {
			strTerm = "上学期";
		} else {
			strTerm = "下学期";
		}
		return strTerm;
	}

	/**
	 * 由年级数确定学期
	 * 
	 * @param grade
	 * @return
	 */
	public static String getStrTerm(String strgrade) {
		int grade = Integer.parseInt(strgrade);
		String strTerm = "";
		if (grade % 2 == 1) {
			strTerm = "上学期";
		} else {
			strTerm = "下学期";
		}
		return strTerm;
	}
}
