package com.live.video.net;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hlj.utils.Logger;
import com.live.video.constans.EpgInfo;

/**
 * 获取频道返回
 * 
 * @author huangyuchao
 * 
 */
public class GetColumnResponse extends BaseResponse {

	public ArrayList<EpgInfo> list;
	public String date1;
	public String date2;
	public String date3;
	public String date4;
	public String date5;
	public String date6;
	public String date7;

	public HashMap<String, ArrayList> map = new HashMap<String, ArrayList>();

	public ArrayList<HashMap> columnList = new ArrayList<HashMap>();

	@Override
	public void paseRespones(String js) {

		// lookbacks
		JSONArray json;
		try {
			JSONObject jsonObj = new JSONObject(js);
			json = jsonObj.getJSONArray("channelItems");
			// for (int i = 0; i < 7; i++) {

			// 前6天
			JSONObject jsObject1 = json.getJSONObject(0);
			date1 = jsObject1.getString("date");
			JSONArray lookBackArray1 = (JSONArray) jsObject1.get("lookbacks");
			for (int j = 0; j < lookBackArray1.length(); j++) {
				JSONObject lookbackObject = (JSONObject) lookBackArray1.get(j);
				JSONArray itemArray = lookbackObject.getJSONArray("item");
				list = new ArrayList<EpgInfo>();
				for (int k = 0; k < itemArray.length(); k++) {
					JSONObject item = (JSONObject) itemArray.get(k);
					EpgInfo info = new EpgInfo();
					info.showTime = item.getString("showTime");
					info.title = item.getString("title");
					info.url = item.getString("url");
					info.starttime = item.getString("starttime");
					info.endtime = item.getString("endtime");
					info.date = date1;
					list.add(info);
					map.put(date1, list);
				}
				columnList.add(map);
			}

			// 前5天
			JSONObject jsObject2 = json.getJSONObject(1);
			date2 = jsObject2.getString("date");
			JSONArray lookBackArray2 = (JSONArray) jsObject2.get("lookbacks");
			for (int j = 0; j < lookBackArray2.length(); j++) {
				JSONObject lookbackObject = (JSONObject) lookBackArray2.get(j);
				JSONArray itemArray = lookbackObject.getJSONArray("item");
				list = new ArrayList<EpgInfo>();
				for (int k = 0; k < itemArray.length(); k++) {
					JSONObject item = (JSONObject) itemArray.get(k);
					EpgInfo info = new EpgInfo();
					info.showTime = item.getString("showTime");
					info.title = item.getString("title");
					info.url = item.getString("url");
					info.starttime = item.getString("starttime");
					info.endtime = item.getString("endtime");
					info.date = date2;
					list.add(info);
					map.put(date2, list);
				}
				columnList.add(map);
			}

			// 前4天
			JSONObject jsObject3 = json.getJSONObject(2);
			date3 = jsObject3.getString("date");
			JSONArray lookBackArray3 = (JSONArray) jsObject3.get("lookbacks");
			for (int j = 0; j < lookBackArray3.length(); j++) {
				JSONObject lookbackObject = (JSONObject) lookBackArray3.get(j);
				JSONArray itemArray = lookbackObject.getJSONArray("item");
				list = new ArrayList<EpgInfo>();
				for (int k = 0; k < itemArray.length(); k++) {
					JSONObject item = (JSONObject) itemArray.get(k);
					EpgInfo info = new EpgInfo();
					info.showTime = item.getString("showTime");
					info.title = item.getString("title");
					info.url = item.getString("url");
					info.starttime = item.getString("starttime");
					info.endtime = item.getString("endtime");
					info.date = date3;
					list.add(info);
					map.put(date3, list);
				}
				columnList.add(map);
			}

			// 前3天
			JSONObject jsObject4 = json.getJSONObject(3);
			date4 = jsObject4.getString("date");
			JSONArray lookBackArray4 = (JSONArray) jsObject4.get("lookbacks");
			for (int j = 0; j < lookBackArray4.length(); j++) {
				JSONObject lookbackObject = (JSONObject) lookBackArray4.get(j);
				JSONArray itemArray = lookbackObject.getJSONArray("item");
				list = new ArrayList<EpgInfo>();
				for (int k = 0; k < itemArray.length(); k++) {
					JSONObject item = (JSONObject) itemArray.get(k);
					EpgInfo info = new EpgInfo();
					info.showTime = item.getString("showTime");
					info.title = item.getString("title");
					info.url = item.getString("url");
					info.starttime = item.getString("starttime");
					info.endtime = item.getString("endtime");
					info.date = date4;
					list.add(info);
					map.put(date4, list);
				}
				columnList.add(map);
			}

			// 前2天
			JSONObject jsObject5 = json.getJSONObject(4);
			date5 = jsObject5.getString("date");
			JSONArray lookBackArray5 = (JSONArray) jsObject5.get("lookbacks");
			for (int j = 0; j < lookBackArray5.length(); j++) {
				JSONObject lookbackObject = (JSONObject) lookBackArray5.get(j);
				JSONArray itemArray = lookbackObject.getJSONArray("item");
				list = new ArrayList<EpgInfo>();
				for (int k = 0; k < itemArray.length(); k++) {
					JSONObject item = (JSONObject) itemArray.get(k);
					EpgInfo info = new EpgInfo();
					info.showTime = item.getString("showTime");
					info.title = item.getString("title");
					info.url = item.getString("url");
					info.starttime = item.getString("starttime");
					info.endtime = item.getString("endtime");
					info.date = date5;
					list.add(info);
					map.put(date5, list);
				}
				columnList.add(map);
			}

			// 前1天
			JSONObject jsObject6 = json.getJSONObject(5);
			date6 = jsObject6.getString("date");
			JSONArray lookBackArray6 = (JSONArray) jsObject6.get("lookbacks");
			for (int j = 0; j < lookBackArray6.length(); j++) {
				JSONObject lookbackObject = (JSONObject) lookBackArray6.get(j);
				JSONArray itemArray = lookbackObject.getJSONArray("item");
				list = new ArrayList<EpgInfo>();
				for (int k = 0; k < itemArray.length(); k++) {
					JSONObject item = (JSONObject) itemArray.get(k);
					EpgInfo info = new EpgInfo();
					info.showTime = item.getString("showTime");
					info.title = item.getString("title");
					info.url = item.getString("url");
					info.starttime = item.getString("starttime");
					info.endtime = item.getString("endtime");
					info.date = date6;
					list.add(info);
					map.put(date6, list);
				}
				columnList.add(map);
			}

			// 今天
			JSONObject jsObject7 = json.getJSONObject(6);
			date7 = jsObject7.getString("date");
			JSONArray lookBackArray7 = (JSONArray) jsObject7.get("lookbacks");
			for (int j = 0; j < lookBackArray7.length(); j++) {
				JSONObject lookbackObject = (JSONObject) lookBackArray7.get(j);
				JSONArray itemArray = lookbackObject.getJSONArray("item");
				list = new ArrayList<EpgInfo>();
				for (int k = 0; k < itemArray.length(); k++) {
					JSONObject item = (JSONObject) itemArray.get(k);
					EpgInfo info = new EpgInfo();
					info.showTime = item.getString("showTime");
					info.title = item.getString("title");
					info.url = item.getString("url");
					info.starttime = item.getString("starttime");
					info.endtime = item.getString("endtime");
					info.date = date7;
					list.add(info);
					map.put(date7, list);
				}
				columnList.add(map);
			}

			// }

		} catch (JSONException e) {
			Logger.log("paseRespones error ==" + e.getMessage());
		}
	}

}
