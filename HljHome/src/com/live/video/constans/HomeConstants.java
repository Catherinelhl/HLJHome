package com.live.video.constans;

public class HomeConstants {

	// --设备唯一id路径
	public static String deviceUsersUniqueIdPath = "mnt/sdcard/Android/data/deviceUsersUniqueId.txt";
	public static String deviceJudgeInfos = "mnt/sdcard/Android/deviceJudgeInfos.txt";
	public static String deviceSend = "mnt/sdcard/Android/deviceSend.txt";
	// --用户id保存路径
	public static String userIdPath = "mnt/sdcard/Android/data/userId.txt";

	// --版本锁路径
	public static String isVersionLockPath = "mnt/sdcard/Android/data/isVersionLock.txt";
	// --永久锁路径
	public static String isPrimaryLockPath = "mnt/sdcard/Android/data/isPrimaryLock.txt";
	// --临时锁路径
	public static String isTempLockPath = "mnt/sdcard/Android/data/isTempLock.txt";

	// http://edu.cdltv.com/,http://tv.eduott.com/,http://tv.eduyuntv.com/

	public static String homePath1 = "http://apidev.itvyun.com/";
	public static String homePath2 = "http://apidev.eduott.com/";
	public static String homePath3 = "http://apidev.itvyun.com/";
	public static String homePath4 = "http://apidev.eduott.com/";

	public static String homePath = homePath1;
	// public static String homePath = "";

	public static String RAW = "RAW";
	public static String B64 = "B64";

	public static final String HAS_LOCK = "0";
	public static final String NO_LOCK = "1";
	public static final String DONT_LOCK = "2";

	// 版本锁住状态 （0是有锁状态，1是无锁状态,2是不要锁状态）
	public static String isVersionLock = NO_LOCK;
	// 临时锁住状态（0是有锁状态，1是无锁状态,2是不要锁状态）
	public static String isTempLock = NO_LOCK;
	// 永久锁住状态 （0是有锁状态，1是无锁状态,2是不要锁状态）
	public static String isPrimaryLock = NO_LOCK;

	// public static String testAddress =
	// "http://192.168.150.74/wolfcms/updateData/";

	public static String aboutUsAddress = "http://apidev.itvyun.com/primary/static/v1/1.3/aboutUs.php";

	// 不能切换的
	// 注册
	public static String registerAddress = "";
	// 用户中心
	public static String userCenterAddress = "";
	// 收藏
	public static String FavAddress = "";

	public static enum FunctionTagTable {

		GETDEVICECHECK(true, "primary/device/v1/1.3/", "regDevice", "1.3", RAW), GETWEATHER(
				true, "primary/weather/v1/1.3/", "getWeather", "1.3", RAW),

		// 固件升级
		GETCHECKUPDATEFIREWARE(true, "primary/update/v1/1.3/",
				"checkUpdateFirewareTask", "1.3", RAW),
		// launcher升级
		GETCHECKUPDATELAUNCHER(true, "primary/update/v1/1.3/",
				"checkLauncherAppVersion", "1.3", RAW),

		// 所有app升级
		GETALLAPPVERSION(true, "primary/update/v1/1.3/", "checkAllVersion",
				"1.3", RAW),

		GETNAVTABS(true, "primary/desktop/v1/1.3/", "getNavTabs", "1.3", RAW), // 自定义导航Tab
		GETMETRODATA(true, "primary/desktop/v1/1.3/", "getMetroData", "1.3",
				RAW), // 自定义导航内容

		GETCHANNEL(true, "primary/tv/v1/1.3/", "getChannel", "1.3", B64), // 获取频道
		GETCOLUMN(true, "primary/tv/v1/1.3/", "getLookback", "1.3", B64), // 获取节目
		GETPLAYURL(true, "primary/tv/v1/1.3/", "getLookbackPlayUrl", "1.3", B64), // 获取回看地址

		GETLIVEVERSION(true, "primary/tv/v1/1.3/", "getLiveVersion", "1.3", B64), // 获取版本
		GETDEVICEDATA(true, "primary/device/v1/1.3/", "getDeviceData", "1.3",
				RAW), // 获取版本
		GETLIVECATAS(true, "primary/tv/v1/1.3/", "getLiveCatas", "1.3", B64), // 获取所有台的名称
		GETLIVECHANNELS(true, "primary/tv/v1/1.3/", "getLiveChannels", "1.3",
				B64), // 获取所有来源
		GETLIVETVS(true, "primary/tv/v1/1.3/", "getLiveTvs", "1.3", B64), // 获取直播电视列表

		GETCATEGORYALL(true, "primary/filmTv/v1/1.3/", "getCategoryAll", "1.3",
				RAW), // 获取所有影视分类
		GETCATEGORYCONTENTLIST(true, "primary/filmTv/v1/1.3/",
				"getCategoryContentList", "1.3", RAW), // 根据栏目catId获取影视内容信息
		GETCATEGORYLIST(true, "primary/filmTv/v1/1.3/", "getCategoryList",
				"1.3", RAW), // 根据条件获取栏目下的子栏目
		GETCONTENTINFO(true, "primary/filmTv/v1/1.3/", "getContentInfo", "1.3",
				RAW), // 根据栏目contentid获取影视内容信息及视频栏目列表
		GETCONTENTRECOMMEND(true, "primary/filmTv/v1/1.3/",
				"getContentRecommend", "1.3", RAW), // 根据条件获取影视推荐信息

		GETCOURSECONTENTINFO(true, "primary/teaching/v1/1.3/",
				"getCourseContentInfo", "1.3", RAW), // 根据获取课程的信息
		GETCOURSERECOMMMEND(true, "primary/teaching/v1/1.3/",
				"getCourseRecommend", "1.3", RAW), // 根据条件获取学习推荐信息
		GETCOURSESUBJECT(true, "primary/teaching/v1/1.3/", "getCourseSubject",
				"1.3", RAW), // 获取本科目所有年级的课程列表
		GETCOURSEALL(true, "primary/teaching/v1/1.3/", "getCourseAll", "1.3",
				RAW), // 获取全部课程
		GETSUBJECTGRADELIST(true, "primary/teaching/v1/1.3/",
				"getSubjecGradeList", "1.3", RAW), // 根据科目及年级获取课程列表
		GETCOURSECATEGORYSEARCH(true, "primary/teaching/v1/1.3/",
				"getCourseCategorySearch", "1.3", RAW), // 获取推荐
		GETSTUDYGRADELIST(true, "primary/teaching/v1/1.3/",
				"getStudyGradeList", "1.3", RAW), // 根据教材版本获取本年级课程列表，分类
		GETSTUDYVERSIONS(true, "primary/teaching/v1/1.3/", "getStudyVersions",
				"1.3", RAW), // 获取教材的全部版本

		GETSTUDYSUBJECT(true, "primary/teaching/v1/1.3/",
				"getStudySubjectList", "1.3", RAW), // 根据教材版本获取本年级课程列表，分类

		GETFAVLIST(true, "primary/favorites/v1/1.3/", "favList", "1.3", RAW), // 获取收藏列表
		ADDFAV(true, "primary/favorites/v1/1.3/", "addFav", "1.3", RAW), // 添加收藏
		DELFAV(true, "primary/favorites/v1/1.3/", "delFav", "1.3", RAW), // 取消收藏
		GETFAV(true, "primary/favorites/v1/1.3/", "getFav", "1.3", RAW), // 获取单条收藏的详细信息

		ADDPLAYINFOS(true, "primary/playRecords/v1/1.3/", "addPlayInfos",
				"1.3", RAW), // 回报视频播放记录
		GETPLAYINFOLIST(true, "primary/playRecords/v1/1.3/", "getPlayInfoList",
				"1.3", RAW), // 获取视频播放记录列表

		GETGRADEEPG(true, "primary/settings/v1/1.3/", "getGradeEpg", "1.3", RAW), // 获取可选择的组
		SETGRADEEPG(true, "primary/settings/v1/1.3/", "setGradeEpg", "1.3", RAW), // 设置选择组

		ABOUTUS(false, "primary/static/v1/1.3/aboutUs.php", "aboutUs", "1.3",
				RAW), // 获取关于我们

		GETTIME(true, "primary/time/v1/1.3/", "getTime", "1.3", RAW), // 获取时间

		UPDATEUSERINFO(true, "primary/user/v1/1.3/", "updateUserInfo", "1.3",
				RAW), // 更新用户信息
		GETPARENTALCONTROLINFO(true, "primary/user/v1/1.3/",
				"changeLockPassword", "1.3", RAW), //

		GETPWDINFO(true, "primary/user/v1/1.3/", "loginLockPassword", "1.3",
				RAW), GETUSERINFO(true, "primary/user/v1/1.3/", "getUserInfo",
				"1.3", RAW);

		private boolean isPost = false;// 传递方式
		private String address = "";// 后缀地址
		private String api = "";// 方法名
		private String ver = "";// 版本名
		private String mode = "";// 数据加密方式

		private FunctionTagTable(boolean isPost, String address, String API,
				String VER, String MODE) {
			this.isPost = isPost;
			this.address = address;
			this.api = API;
			this.ver = VER;

			this.mode = MODE;
		}

		public void setApi(String api) {
			this.api = api;
		}

		public String getApi() {
			return api;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public boolean isPost() {
			return isPost;
		}

		public void setPost(boolean isPost) {
			this.isPost = isPost;
		}

		public String getVer() {
			return ver;
		}

		public void setVer(String ver) {
			this.ver = ver;
		}

		public String getMode() {
			return mode;
		}

		public void setMode(String mode) {
			this.mode = mode;
		}

	}

}
