package com.hlj.view;

import java.util.ArrayList;
import java.util.Map;

import com.hlj.HomeActivity.R;
import com.hlj.adapter.MovieAdapter;
import com.hlj.utils.ImageUtils;
import com.hlj.utils.Logger;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

/**
 * 收藏视图
 * 
 * @author hyc
 * 
 */
public class FavRecodLayout extends LinearLayout implements LayoutInterface,
		View.OnFocusChangeListener, View.OnClickListener {

	private Context mContext;

	MovieLayout movieLayout;

	View rlView;

	ImageView play_collect_reflected_img;

	public FavRecodLayout(Context context) {
		super(context);
		Logger.log("-----------FavRecodLayout-------context-------");
		this.mContext = context;
	}

	public FavRecodLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		Logger.log("-----------FavRecodLayout-----context------attrs---");
		this.mContext = context;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFocusChange(View arg0, boolean arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		movieLayout = null;
		play_collect_reflected_img = null;
		rlView = null;
	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initView() {
		Logger.log("-----------initView--------------");
		setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		setGravity(Gravity.CENTER_HORIZONTAL);

		View view = LayoutInflater.from(mContext).inflate(R.layout.fav_recod,
				null);

		movieLayout = (MovieLayout) view.findViewById(R.id.movieLayout);

		rlView = view.findViewById(R.id.play_colection_layout);

		play_collect_reflected_img = (ImageView) view
				.findViewById(R.id.play_collect_reflected_img);

		addView(view);

		adapter = new MovieAdapter(mContext);
		// movieLayout.setAdapter(adapter);
	}

	// ArrayList<VideoInfo> list = new ArrayList<VideoInfo>();

	ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	MovieAdapter adapter;

	public void setMap(Map<String, Object> map) {
		// list.add(map);
		adapter.setMap(map);
	}

	public void setList(ArrayList<VideoInfo> videoList) {
		Logger.log("-----------setList--------------");

		// for (VideoInfo info : videoList) {
		// list.add(info);
		// }

		adapter.setList(videoList);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// super.onDraw(canvas);
		// invalidate();
	}

	public void listClear() {
		adapter.ListClear();
	}

	@Override
	public void loadData() {
		Logger.log("-----------loadData--------------");

	}

	public void updateTestData() {
		Logger.log("-----------updateTestData--------------");
		adapter.notifyDataSetChanged();
	}

	@Override
	public void updateData() {
		Logger.log("-----------updateData--------------");
		movieLayout.setAdapter(adapter);
		// adapter.notifyDataSetChanged();

//		Bitmap localBitmap = ImageUtils.createCutReflectedImage(
//				ImageUtils.convertViewToBitmap(rlView), 80);
//		this.play_collect_reflected_img.setImageBitmap(localBitmap);
	}
}
