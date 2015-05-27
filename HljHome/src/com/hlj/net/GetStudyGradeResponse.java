package com.hlj.net;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.hlj.utils.JsonTools;
import com.hlj.utils.Logger;
import com.hlj.view.VideoInfo;

public class GetStudyGradeResponse extends BaseResponse {

	public ArrayList<VideoInfo> list = new ArrayList<VideoInfo>();

	public int count;
	public String grade;
	public String subject;

	@Override
	public void paseRespones(String js) {
		try {
			JSONObject json = new JSONObject(js);

			count = JsonTools.getInt(json, "count");
			grade = JsonTools.getString(json, "grade");
			subject = JsonTools.getString(json, "subject");
			JSONArray array = json.getJSONArray("items");
			for (int i = 0; i < array.length(); i++) {
				JSONArray objArray = (JSONArray) array.get(i);
				for (int j = 0; j < objArray.length(); j++) {
					JSONObject obj = (JSONObject) objArray.get(i);
					VideoInfo info = new VideoInfo();
					info.contentId = JsonTools.getString(obj, "contentId");
					info.title = JsonTools.getString(obj, "title");
					info.images = JsonTools.getString(obj, "images");
					info.intro = JsonTools.getString(obj, "intro");
					info.grade = JsonTools.getString(obj, "grade");
					info.subjects = JsonTools.getString(obj, "subjects");
					info.lessonid = JsonTools.getString(obj, "lessonid");
					info.versions = JsonTools.getString(obj, "versions");
					info.address = JsonTools.getString(obj, "address");
					info.catId = JsonTools.getInt(obj, "catId");

					info.childLock = JsonTools.getString(obj, "childLock");

					info.versionsname = JsonTools
							.getString(obj, "versionsname");
					info.subjectsname = JsonTools
							.getString(obj, "subjectsname");
					list.add(info);
				}
			}

		} catch (Exception e) {
			Logger.log("GetStudyGradeResponse error=" + e.toString());
		}

	}
}
