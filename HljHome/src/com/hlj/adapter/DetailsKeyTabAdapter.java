package com.hlj.adapter;

import java.util.List;
import com.hlj.HomeActivity.R;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils.TruncateAt;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;

public class DetailsKeyTabAdapter extends BaseAdapter {

	private Context context;
	private List<String> list;

	public DetailsKeyTabAdapter(Context context, List<String> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Button button = new Button(context);
		button.setTextColor(Color.WHITE);
		button.setTextSize(20.0F);
		button.setSingleLine(true);
		button.setText(String.valueOf(list.get(position)));
		button.setEllipsize(TruncateAt.MARQUEE);
		button.setMarqueeRepeatLimit(3);
		button.setLayoutParams(new Gallery.LayoutParams(120, 55));
		button.setGravity(Gravity.CENTER);
		button.setBackgroundResource(R.drawable.video_details_btn10_selector);
		return button;
	}

}
