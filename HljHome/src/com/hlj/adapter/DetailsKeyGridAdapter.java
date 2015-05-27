package com.hlj.adapter;

import java.util.List;
import com.hlj.HomeActivity.R;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils.TruncateAt;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.TextView;

public class DetailsKeyGridAdapter extends BaseAdapter {

	private Context context;
	private List<String> list;

	public DetailsKeyGridAdapter(Context context, List<String> list) {
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
		TextView textView = new TextView(context);
		textView.setTextColor(Color.WHITE);
		textView.setTextSize(20.0F);
		textView.setSingleLine(true);
		textView.setText(String.valueOf(list.get(position)));
		textView.setEllipsize(TruncateAt.MARQUEE);
		textView.setMarqueeRepeatLimit(3);
		textView.setLayoutParams(new AbsListView.LayoutParams(100, 50));
		textView.setGravity(Gravity.CENTER);
		textView.setBackgroundResource(R.drawable.study_details_btn10_selector);
		return textView;
	}

}
