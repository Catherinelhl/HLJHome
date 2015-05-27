package com.hlj.utils;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.text.Html;

public class JsonTools {

	/**
	 * 
	 * @param root
	 * @param key
	 * @return
	 */
	public static int getInt(JSONObject root, String key) {
		try {
			if (root.isNull(key)) {
				return 0;
			} else {
				return root.getInt(key);
			}
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 
	 * @param root
	 * @param key
	 * @return
	 */
	public static String getString(JSONObject root, String key) {
		try {
			if (root.isNull(key)) {
				return "";
			} else {
				return String.valueOf(Html.fromHtml(root.optString(key)));
			}
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 
	 * @param root
	 * @param key
	 * @return
	 */
	public static double getDouble(JSONObject root, String key) {
		try {
			if (root.isNull(key)) {
				return 0;
			} else {
				return root.getDouble(key);
			}
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 
	 * @param root
	 * @param key
	 * @return
	 */
	public static JSONArray getJsonArray(JSONObject root, String key) {
		try {
			return root.getJSONArray(key);
		} catch (Exception e) {
			Logger.log("---" + e.getLocalizedMessage());
			return new JSONArray();
		}
	}

	/**
	 * 
	 * @param root
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(JSONObject root, String key) {
		try {
			String str = getString(root, key);
			if (str.equals("true"))
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static JSONObject getJsonObject(String jsonStr) {
		try {
			return new JSONObject(jsonStr);
		} catch (Exception e) {
			return new JSONObject();
		}
	}

	/**
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static JSONObject getJsonObject(JSONObject js, String key) {
		try {
			return js.getJSONObject(key);
		} catch (Exception e) {
			return new JSONObject();
		}
	}
}
