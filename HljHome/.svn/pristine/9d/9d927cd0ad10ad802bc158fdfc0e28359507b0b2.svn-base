package com.hlj.tuisongvideo;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class VideoInfo {

	public String title;
	public String action;
	public String link;
	public String user;

	public Super superVideo;
	public High highVideo;
	public Stand standVideo;

	public String[] items = new String[3];

	public VideoInfo() {
		superVideo = new Super();
		highVideo = new High();
		standVideo = new Stand();
	}

	public class Super {
		public String name;
		public ArrayList<String> urls = new ArrayList<String>();
	}

	public class High {
		public String name;
		public ArrayList<String> urls = new ArrayList<String>();
	}

	public class Stand {
		public String name;
		public ArrayList<String> urls = new ArrayList<String>();
	}

	public void parseJson(String s) {
		try {
			JSONObject jo = new JSONObject(s);

			title = jo.getString("title");
			action = jo.getString("action");
			link = jo.getString("link");
			user = jo.getString("user");

			if ("play".equals(action)) {
				JSONObject superJson = jo.getJSONObject("super");
				superVideo.name = superJson.getString("name");

				JSONArray superArray = superJson.getJSONArray("urls");
				for (int i = 0; i < superArray.length(); i++) {
					superVideo.urls.add(superArray.get(i).toString());
				}
				if (superVideo.urls.size() > 0) {
					items[2] = "超清";
				}

				JSONObject highJson = jo.getJSONObject("high");
				highVideo.name = highJson.getString("name");

				JSONArray highArray = highJson.getJSONArray("urls");
				for (int i = 0; i < highArray.length(); i++) {
					highVideo.urls.add(highArray.get(i).toString());
				}
				if (highVideo.urls.size() > 0) {
					items[1] = "高清";
				}

				JSONObject standJson = jo.getJSONObject("stand");
				standVideo.name = standJson.getString("name");

				JSONArray standArray = standJson.getJSONArray("urls");
				for (int i = 0; i < standArray.length(); i++) {
					standVideo.urls.add(standArray.get(i).toString());
				}
				if (standVideo.urls.size() > 0) {
					items[0] = "标清";
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
