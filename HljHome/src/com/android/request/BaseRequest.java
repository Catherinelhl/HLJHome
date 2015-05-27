package com.android.request;

import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;

import com.hlj.encrypt.Base64;
import com.hlj.utils.Logger;

import android.util.Log;

public class BaseRequest {
	private static String TAG = "BaseRequest================>";
	public static String API = "common";
	public static String VER = "1.3";
	public static String MODE = "B64";

	public String ToChangeJson(String GetJson) {
		String code_Result = null;

		try {
			JSONObject jsonObject = new JSONObject(GetJson);
			// result = jsonObject.keys().next().toString();
			int code = jsonObject.getInt("CODE");
			if (code != 0) {
				String Result = jsonObject.getString("RESULT");
				byte[] b = Base64.decode(Result);

				try {
					String result_Code = new String(b, "utf-8");
					JSONObject json_code = new JSONObject(result_Code);
					// result = jsonObject.keys().next().toString();
					code_Result = json_code.getString("code");
					Log.v(TAG, "and the code_Result is====>" + code_Result);

				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				Logger.log("得到的code不为o,为===》" + code);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return code_Result;

	}




}
