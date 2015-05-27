package com.hlj.HomeActivity.set;

import java.util.ArrayList;

import com.hj.widget.CommonToast;
import com.hlj.HomeActivity.R;
import com.hlj.net.GetStudyVersionResponse;
import com.hlj.net.GetStudyVersionsRequest;
import com.hlj.net.HttpHomeLoadDataTask;
import com.hlj.net.UpdateUserInfoRequest;
import com.hlj.net.UpdateUserInfoRequest.UserTeachingMeterial;
import com.hlj.utils.Logger;
import com.hlj.utils.PrefrenceHandler;
import com.hlj.utils.StringTools;
import com.live.video.net.HttpLoadDataTask;
import com.live.video.net.callback.IUpdateData;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 学年设置
 * 
 * @author huangyuchao
 * 
 */
public class SettingGrade extends Activity implements OnClickListener {

	View gradeView;

	public ArrayList gradeList = new ArrayList();
	public ArrayList termList = new ArrayList();

	PopupWindow gradePopup = null;

	PopupWindow versionPopup = null;

	CommonToast toast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_main);
		gradeView = ((ViewStub) findViewById(R.id.set_grade)).inflate();

		initGrade();

		initServerView();

	}

	TextView set_name1, set_name2;
	ImageView set_item_log;

	TextView setting_grade, setting_term, setting_language, setting_math,
			setting_english;

	ListView gradeListView, termListView, languageListView, mathListView,
			englishListView;

	ArrayAdapter<String> gradeAdapter, termAdapter, languageAdapter,
			mathAdapter, englishAdapter;

	int gradeSelection, termSelection, languageSelection, mathSelection,
			englishSelection;

	String currentGrade, currentTerm, currentLanguege, currentMath,
			currentEnglish;

	String currentLanguageId, currentMathId, currentEnglishId;

	Button setting_ok;

	private void initServerView() {
		gradePopup = new PopupWindow(this);
		gradePopup.setWidth(215);
		gradePopup.setHeight(280);
		gradePopup.setFocusable(true);
		gradePopup.setOutsideTouchable(true);

		versionPopup = new PopupWindow(this);
		versionPopup.setWidth(150);
		versionPopup.setHeight(150);
		versionPopup.setFocusable(true);
		versionPopup.setOutsideTouchable(true);

		set_name1 = (TextView) findViewById(R.id.set_name1);
		set_name2 = (TextView) findViewById(R.id.set_name2);
		set_item_log = (ImageView) findViewById(R.id.set_item_log);

		set_name1.setText("学年设置");
		set_name2.setText("学年设置");
		set_item_log.setImageResource(R.drawable.other_setup);

		setting_ok = (Button) findViewById(R.id.setting_ok);
		setting_ok.setOnClickListener(this);

		setting_grade = (TextView) findViewById(R.id.setting_grade);
		setting_term = (TextView) findViewById(R.id.setting_term);
		setting_language = (TextView) findViewById(R.id.setting_language);
		setting_math = (TextView) findViewById(R.id.setting_math);
		setting_english = (TextView) findViewById(R.id.setting_english);

		SharedPreferences preferences = getSharedPreferences("settingGrade",
				MODE_PRIVATE);
		currentGrade = preferences.getString("grade", "一年级");
		currentTerm = preferences.getString("term", "上学期");
		currentLanguege = preferences.getString("language", "");
		currentMath = preferences.getString("math", "");
		currentEnglish = preferences.getString("english", "");

		setting_grade.setOnClickListener(this);
		setting_grade.setText(currentGrade);
		setting_term.setOnClickListener(this);
		setting_term.setText(currentTerm);

		setting_language.setOnClickListener(this);
		setting_language.setText(currentLanguege);

		setting_math.setOnClickListener(this);
		setting_math.setText(currentMath);

		setting_english.setOnClickListener(this);
		setting_english.setText(currentEnglish);

		gradeListView = new ListView(this);
		gradeListView.setBackgroundResource(R.drawable.submenu_list_bg);
		gradeAdapter = new ArrayAdapter<String>(this,
				R.layout.type_details_submenu, R.id.sunmenu_text, gradeList);
		gradeListView.setAdapter(gradeAdapter);

		gradeListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				gradeSelection = position;
				currentGrade = gradeAdapter.getItem(position);
				setting_grade.setText(currentGrade);
				gradePopup.dismiss();
			}
		});

		termListView = new ListView(this);
		termListView.setBackgroundResource(R.drawable.submenu_list_bg);
		termAdapter = new ArrayAdapter<String>(this,
				R.layout.type_details_submenu, R.id.sunmenu_text, termList);
		termListView.setAdapter(termAdapter);
		termListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				termSelection = position;
				currentTerm = termAdapter.getItem(position);
				setting_term.setText(currentTerm);
				gradePopup.dismiss();
			}
		});

		languageListView = new ListView(this);
		languageListView.setBackgroundResource(R.drawable.submenu_list_bg);
		languageAdapter = new ArrayAdapter<String>(this,
				R.layout.type_details_submenu, R.id.sunmenu_text, versionList);
		languageListView.setAdapter(languageAdapter);
		languageListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				languageSelection = position;
				currentLanguege = languageAdapter.getItem(position);

				currentLanguageId = versionIdList.get(position);
				setting_language.setText(currentLanguege);
				versionPopup.dismiss();

			}
		});

		mathListView = new ListView(this);
		mathListView.setBackgroundResource(R.drawable.submenu_list_bg);
		mathAdapter = new ArrayAdapter<String>(this,
				R.layout.type_details_submenu, R.id.sunmenu_text, versionList);
		mathListView.setAdapter(mathAdapter);
		mathListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				mathSelection = position;
				currentMath = mathAdapter.getItem(position);

				currentMathId = versionIdList.get(position);
				setting_math.setText(currentMath);
				versionPopup.dismiss();

			}
		});

		englishListView = new ListView(this);
		englishListView.setBackgroundResource(R.drawable.submenu_list_bg);
		englishAdapter = new ArrayAdapter<String>(this,
				R.layout.type_details_submenu, R.id.sunmenu_text, versionList);
		englishListView.setAdapter(englishAdapter);
		englishListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				englishSelection = position;
				currentEnglish = englishAdapter.getItem(position);

				currentEnglishId = versionIdList.get(position);
				setting_english.setText(currentEnglish);
				versionPopup.dismiss();

			}
		});

		currentLanguageId = PrefrenceHandler.getLanguageId(this);
		currentMathId = PrefrenceHandler.getMathId(this);
		currentEnglishId = PrefrenceHandler.getEnglishId(this);

		getStudyVersions();
	}

	/**
	 * 获取教材的全部版本
	 */
	private void getStudyVersions() {
		GetStudyVersionsRequest request = new GetStudyVersionsRequest(this);
		new HttpHomeLoadDataTask(this, callBack, false, "", false)
				.execute(request);
	}

	ArrayList<String> versionList = new ArrayList<String>();
	ArrayList<String> versionIdList = new ArrayList<String>();

	IUpdateData callBack = new IUpdateData() {

		@Override
		public void updateUi(Object o) {
			// Logger.log(o.toString());
			GetStudyVersionResponse response = new GetStudyVersionResponse();
			response.paseRespones(o.toString());

			for (int i = 0; i < response.list.size(); i++) {
				String versionname = response.list.get(i).versionname;
				String versionid = response.list.get(i).versionid;
				versionList.add(versionname);
				versionIdList.add(versionid);
			}
			languageAdapter.notifyDataSetChanged();
			mathAdapter.notifyDataSetChanged();
			englishAdapter.notifyDataSetChanged();

		}

		@Override
		public void handleErrorData(String info) {
			Logger.log(info);

		}
	};

	private void initGrade() {
		gradeList.add("一年级");
		gradeList.add("二年级");
		gradeList.add("三年级");
		gradeList.add("四年级");
		gradeList.add("五年级");
		gradeList.add("六年级");

		// gradeList.add("初一");
		// gradeList.add("初二");
		// gradeList.add("初三");

		// gradeList.add("高一");
		// gradeList.add("高二");
		// gradeList.add("高三");

		termList.add("上学期");
		termList.add("下学期");

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.setting_grade:
			gradeListView.setSelection(gradeSelection);
			gradePopup.setContentView(gradeListView);
			gradePopup.showAsDropDown(setting_grade);

			break;
		case R.id.setting_term:
			termListView.setSelection(termSelection);
			gradePopup.setContentView(termListView);
			gradePopup.showAsDropDown(setting_term);
			break;
		case R.id.setting_language:
			languageListView.setSelection(languageSelection);
			versionPopup.setContentView(languageListView);
			versionPopup.showAsDropDown(setting_language);
			break;
		case R.id.setting_math:
			mathListView.setSelection(mathSelection);
			versionPopup.setContentView(mathListView);
			versionPopup.showAsDropDown(setting_math);
			break;

		case R.id.setting_english:
			englishListView.setSelection(englishSelection);
			versionPopup.setContentView(englishListView);
			versionPopup.showAsDropDown(setting_english);
			break;

		case R.id.setting_ok:
			Logger.log("currentGrade=" + currentGrade);
			Logger.log("currentTerm=" + currentTerm);
			Logger.log("currentLanguege=" + currentLanguege);
			Logger.log("currentMath=" + currentMath);
			Logger.log("currentEnglish=" + currentEnglish);

			Logger.log("currentLanguageId=" + currentLanguageId);
			Logger.log("currentMathId=" + currentMathId);
			Logger.log("currentEnglishId=" + currentEnglishId);
			// 存储年级和学期
			SharedPreferences.Editor editor = getSharedPreferences(
					"settingGrade", MODE_PRIVATE).edit();
			editor.putString("grade", currentGrade);
			editor.putString("term", currentTerm);
			editor.putString("language", currentLanguege);
			editor.putString("math", currentMath);
			editor.putString("english", currentEnglish);

			editor.putString("languageId", currentLanguageId);
			editor.putString("mathId", currentMathId);
			editor.putString("englishId", currentEnglishId);
			editor.putString("isSet", "true");
			editor.commit();

			// 更新用户信息
			updateUserInfo();

			break;
		}
	}

	ArrayList<UserTeachingMeterial> list = new ArrayList<UserTeachingMeterial>();

	private void updateUserInfo() {
		list.clear();
		UpdateUserInfoRequest request = new UpdateUserInfoRequest(this);
		request.userGrade = StringTools.getCurrentGrade(this) + "";

		UserTeachingMeterial teach1 = new UpdateUserInfoRequest(this).new UserTeachingMeterial();
		teach1.subject = "chinese";
		teach1.versions = PrefrenceHandler.getLanguageId(this);
		list.add(teach1);

		UserTeachingMeterial teach2 = new UpdateUserInfoRequest(this).new UserTeachingMeterial();
		teach2.subject = "maths";
		teach2.versions = PrefrenceHandler.getMathId(this);
		list.add(teach2);

		UserTeachingMeterial teach3 = new UpdateUserInfoRequest(this).new UserTeachingMeterial();
		teach3.subject = "english";
		teach3.versions = PrefrenceHandler.getEnglishId(this);
		list.add(teach3);

		request.list = list;
		new HttpHomeLoadDataTask(this, iUpdateData, false, "", false)
				.execute(request);
	}

	IUpdateData iUpdateData = new IUpdateData() {

		@Override
		public void updateUi(Object o) {
			// 学年设置成功
			toast = new CommonToast(SettingGrade.this);
			toast.setText("学年设置成功,可到系列学习里查看。");
			toast.setIcon(R.drawable.toast_smile);
			toast.show();
		}

		@Override
		public void handleErrorData(String info) {
			// 学年设置失败
			toast = new CommonToast(SettingGrade.this);
			toast.setText("学年设置失败");
			toast.setIcon(R.drawable.toast_err);
			toast.show();
		}

	};

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
