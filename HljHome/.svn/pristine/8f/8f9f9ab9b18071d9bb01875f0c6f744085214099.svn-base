package com.hlj.net;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.hlj.net.GetCourseGradeResponse.Item.PolymAddress;
import com.hlj.net.GetCourseGradeResponse.Item.PolymAddress.Video;
import com.hlj.utils.JsonTools;
import com.hlj.utils.Logger;

public class GetCourseGradeResponse extends BaseResponse {

	public Item info;

	public PolymAddress address;

	public class Item {
		public String contentId;
		public int catId;
		public String type;
		public String title;
		public String images;
		public String intro;
		public String actor;
		public String timeLength;
		public String language;
		public String director;
		public String releaseDate;
		public String zone;
		public String childLock;
		public String subject;
		public String grade;
		public String isCharge;
		public String fileType;
		public String subjectsname;
		public String series;

		public class PolymAddress {
			public String videoSource;

			public class Video {
				public String videoId;
				public String videoAddress;
				public String title;
				
				public int isDefaultPlay;
				public String version;
				public String lesson;
				public String information;
				public String grade;
				public String subject;
				public String person;
				public String childLock;

			}

			public HashMap<String, ArrayList> hashMap;
			public ArrayList<Video> videoList;
		}
	}

	public ArrayList<PolymAddress> list = new ArrayList<PolymAddress>();

	@Override
	public void paseRespones(String js) {
		try {
			JSONObject json = new JSONObject(js);

			JSONObject obj = json.getJSONObject("item");
			info = new Item();
			info.contentId = JsonTools.getString(obj, "contentId");
			info.catId = JsonTools.getInt(obj, "catId");
			info.type = JsonTools.getString(obj, "type");
			info.title = JsonTools.getString(obj, "title");
			info.images = JsonTools.getString(obj, "images");
			info.intro = JsonTools.getString(obj, "intro");
			info.actor = JsonTools.getString(obj, "actor");
			info.timeLength = JsonTools.getString(obj, "timeLength");
			info.language = JsonTools.getString(obj, "language");
			info.director = JsonTools.getString(obj, "director");
			info.releaseDate = JsonTools.getString(obj, "releaseDate");
			info.zone = JsonTools.getString(obj, "zone");
			info.childLock = JsonTools.getString(obj, "childLock");
			info.subject = JsonTools.getString(obj, "subject");
			info.grade = JsonTools.getString(obj, "grade");

			info.isCharge = JsonTools.getString(obj, "isCharge");
			info.fileType = JsonTools.getString(obj, "fileType");
			info.subjectsname = JsonTools.getString(obj, "subjectsname");
			info.series = JsonTools.getString(obj, "series");

			JSONObject movieObj = obj.getJSONObject("movieurls");

			info.series = JsonTools.getString(movieObj, "sum");

			JSONArray jsonArray = movieObj.getJSONArray("polymAddress");
			for (int j = 0; j < jsonArray.length(); j++) {
				JSONObject polyJson = (JSONObject) jsonArray.get(j);
				address = info.new PolymAddress();
				address.videoSource = polyJson.getString("videoSource");
				address.videoList = new ArrayList<Video>();
				address.hashMap = new HashMap<String, ArrayList>();
				JSONArray videoArray = polyJson.getJSONArray("videos");
				for (int i = 0; i < videoArray.length(); i++) {
					JSONObject videoJson = (JSONObject) videoArray.get(i);
					Video video = address.new Video();
					video.videoId = JsonTools.getString(videoJson, "videoid");
					video.videoAddress = JsonTools.getString(videoJson,
							"videoAddress");
					video.title = JsonTools.getString(videoJson, "title");
					
					video.isDefaultPlay= JsonTools.getInt(videoJson, "isDefaultPlay");
					video.version = JsonTools.getString(videoJson, "version");
					video.lesson = JsonTools.getString(videoJson, "lesson");
					video.information = JsonTools.getString(videoJson,
							"information");
					video.grade = JsonTools.getString(videoJson, "grade");
					video.subject = JsonTools.getString(videoJson, "subject");

					video.person = JsonTools.getString(videoJson, "person");
					video.childLock = JsonTools.getString(videoJson,
							"childLock");

					address.videoList.add(video);
				}
				address.hashMap.put(address.videoSource, address.videoList);

				list.add(address);
			}

		} catch (Exception e) {
			Logger.log("GetCourseGradeResponse error=" + e.toString());
		}

	}
}
