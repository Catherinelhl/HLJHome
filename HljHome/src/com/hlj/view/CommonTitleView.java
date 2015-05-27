package com.hlj.view;


import com.hlj.HomeActivity.R;
import com.hlj.utils.AnimationUtils;
import com.hlj.utils.CommonTools;
import com.hlj.utils.ImageLoader;
import com.hlj.utils.PrefrenceHandler;
import com.hlj.utils.StringTools;
import com.live.video.constans.Constants;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CommonTitleView extends LinearLayout {

	private Context context;
	private View view;

	public static CommonTitleView instance;

	public CommonTitleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		view = CommonTools.getView(context, R.layout.top_title_view, this);

		instance = this;
		init();
	}

	ImageView top_weather_log1, top_weather_log2, top_net_type;
	TextView top_time_info;
	Button top_grade;

	TextView type_details_type, type_details_sum;
	ImageView type_details_type_iv;

	LinearLayout type_details_menuHint_ll;
	ImageView detail_menu_key;

	private void init() {
		top_net_type = (ImageView) view.findViewById(R.id.top_net_type);
		top_weather_log1 = (ImageView) view.findViewById(R.id.top_weather_log1);
		top_weather_log2 = (ImageView) view.findViewById(R.id.top_weather_log2);
		top_time_info = (TextView) view.findViewById(R.id.top_time_info);
		// 设置时间
		top_time_info.setText(StringTools.getSystemTime());

		top_grade = (Button) view.findViewById(R.id.top_grade);

		setGrageText();
		setTermResource();

		type_details_type = (TextView) view
				.findViewById(R.id.type_details_type);
		type_details_type_iv = (ImageView) view
				.findViewById(R.id.type_details_type_iv);

		type_details_sum = (TextView) view.findViewById(R.id.type_details_sum);

		type_details_menuHint_ll = (LinearLayout) view
				.findViewById(R.id.type_details_menuHint_ll);

		detail_menu_key = (ImageView) findViewById(R.id.detail_menu_key);

		imageLoader = new ImageLoader(context, R.color.transparent);
	}

	ImageLoader imageLoader;

	public void setLogo(String url) {
		imageLoader.displayImage(url, top_net_type);

	}

	public void setGrageText() {
		String currentGrade = PrefrenceHandler.getGradeStr(context);

		top_grade.setText(StringTools.getCurrentGrade(currentGrade));
	}

	public void setTopInvisible() {
		if ("1".equals(Constants.isGrade)) {
			top_grade.setVisibility(View.INVISIBLE);
		} else {
			top_grade.setVisibility(View.VISIBLE);
		}
	}

	public void setTermResource() {
		String currentTerm = PrefrenceHandler.getTermStr(context);
		top_grade.setBackgroundResource(StringTools
				.getTermResource(currentTerm));
	}

	public void setWeather1Img(int resId) {
		top_weather_log1.setVisibility(View.VISIBLE);
		top_weather_log1.setImageResource(resId);
	}

	public void setWeather2Img(int resId) {
		top_weather_log2.setVisibility(View.VISIBLE);
		top_weather_log2.setImageResource(resId);
	}

	public void setTime(String time) {
		top_time_info.setText(time);
	}

	public void setTypeDetailsText(String type) {
		type_details_type.setVisibility(View.VISIBLE);
		type_details_type_iv.setVisibility(View.VISIBLE);
		type_details_type.setText(type);
	}

	public void setSumNumberText(String sum) {
		type_details_sum.setVisibility(View.VISIBLE);
		type_details_sum.setText(sum);
	}

	public void setMenuTextVisible() {
		type_details_menuHint_ll.setVisibility(View.VISIBLE);
		AnimationUtils.setMenuAnimation(detail_menu_key, R.drawable.menu_key,
				R.drawable.menu_key_blue);
	}

	public void setFouseble() {
		instance.setFocusable(true);
		instance.requestFocus();
		instance.setFocusableInTouchMode(true);
	}

}
