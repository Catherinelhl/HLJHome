package com.hlj.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hlj.utils.JsonTools;
import com.hlj.view.VideoInfo;
import com.live.video.constans.ChannelInfo;
import com.live.video.net.BaseResponse;

/**
 * 根据获取课程的信息
 * 
 * @author huangyuchao
 * 
 */
public class GetCourseInfoResponse extends BaseResponse {

	/**
	 * "contentid": 109, "lessonid": 109, "grade": 9, "subject": "english",
	 * "catId": 105, "title": "城市猎人", "versions": "adultEducation", "images":
	 * "http://images.is.ysten.com:8080/images/ysten/images/2013/10/cslr20131008.jpg"
	 * , "intro":
	 * "本片讲述了私家侦探孟波与死去搭档的妹妹惠香，受雇于报业大王今村，在寻找其失踪女儿清子时，发生的种种出人意料而又笑料百出的故事。",
	 * "price": "0", "address": { "versions": "adultEducation", "videoAddress":
	 * "http://hot.media.ysten.com/media/new/2013/10/08/hd_dy_cslr_20131008.ts",
	 * "order": "0" }
	 */

	public String contentId;
	public int lessonid;
	public int grade;
	public String subject;
	public int catId;
	public String title;
	public String versions;
	public String images;
	public String intro;
	public String price;

	public Address address;

	public class Address {
		public String versions;
		public String videoAddress;
		public int order;

	}

	public ArrayList<Address> list = new ArrayList<Address>();

	@Override
	public void paseRespones(String js) {

		try {
			JSONObject json = new JSONObject(js);
			contentId = JsonTools.getString(json, "contentId");
			lessonid = JsonTools.getInt(json, "lessonid");
			grade = JsonTools.getInt(json, "grade");
			subject = JsonTools.getString(json, "subject");
			catId = JsonTools.getInt(json, "catId");
			title = JsonTools.getString(json, "title");
			versions = JsonTools.getString(json, "versions");
			images = JsonTools.getString(json, "images");
			intro = JsonTools.getString(json, "intro");
			price = JsonTools.getString(json, "price");

			address = new Address();
			JSONObject addressJson = json.getJSONObject("address");
			address.versions = JsonTools.getString(addressJson, "versions");
			address.videoAddress = JsonTools.getString(addressJson,
					"videoAddress");
			address.order = JsonTools.getInt(addressJson, "order");

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
