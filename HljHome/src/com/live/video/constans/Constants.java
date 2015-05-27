package com.live.video.constans;

/**
 * 请求地址和方法类
 * 
 * @author huangyuchao
 * 
 */
public class Constants {

	public static String homePath = "http://apidev.itvyun.com/";

	public static String path = "http://epg.cdltv.com/ctjson/listjson.php";

	public static String basePath = "http://192.168.150.74/wolfcms/updateData/getLookback.php";

	public static int duration = 300;

	public static int totalLength;

	public static int isDefaultPlay = 0;

	/**
	 * 您的ak
	 */
	public static String AK = "IhFfKS8QaEsBciFRaNU6x6eO";
	/**
	 * 您的sk的前16位
	 */
	public static String SK = "lNaYprMUeDTsDLl4OEhKDOywKStI60Mz";

	public static String title, infos, contract, isGrade;

	public static enum FunctionTagTable {

		GETCHANNEL(true, ""), // 获取频道
		GETCOLUMN(true, ""), // 获取节目
		GETPLAYURL(true, ""); // 获取回看地址

		private boolean isPost = false;// 传递方式
		private String functionname = "";// 方法名

		private FunctionTagTable(boolean isPost, String functionname) {
			this.isPost = isPost;
			this.functionname = functionname;
		}

		public void setPost(boolean isPost) {
			this.isPost = isPost;
		}

		public boolean isPost() {
			return isPost;
		}

		public void setFunctionname(String functionname) {
			this.functionname = functionname;
		}

		public String getFunctionname() {
			return functionname;
		}

	}

}
