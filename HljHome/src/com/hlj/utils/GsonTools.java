package com.hlj.utils;

import java.util.Map;

import com.google.gson.Gson;
import com.live.video.constans.PostInfo;

public class GsonTools {

	// ----json工具方法

	public static String parseToJson(String api, String ver, String mode,
			Map map) {
		PostInfo postInfo = new PostInfo(api, ver, mode, map);
		Gson gson = new Gson();
		String beanToJson = gson.toJson(postInfo);
		return beanToJson;
	}

}
