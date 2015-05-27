package com.live.video.constans;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ${shime}
 * Date: 13-10-17
 * Time: 下午1:56
 * To change this template use File | Settings | File Templates.
 */
public class PostInfo {
	private String API;
	private String VER;
	private String MODE;
	private Map<String,String> DATA;

	public PostInfo(String API, String VER, String MODE, Map<String, String> DATA) {
		this.API = API;
		this.VER = VER;
		this.MODE = MODE;
		this.DATA = DATA;
	}

	public String getAPI() {
		return API;
	}

	public void setAPI(String API) {
		this.API = API;
	}

	public String getVER() {
		return VER;
	}

	public void setVER(String VER) {
		this.VER = VER;
	}

	public String getMODE() {
		return MODE;
	}

	public void setMODE(String MODE) {
		this.MODE = MODE;
	}

	public Map<String, String> getDATA() {
		return DATA;
	}

	public void setDATA(Map<String, String> DATA) {
		this.DATA = DATA;
	}
}
