package com.hlj.adapter;

import java.util.ArrayList;

import com.hlj.HomeActivity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TypeDetailSubMenuAdapter extends BaseAdapter {

	private Context mContext;

	private ArrayList<String> list;
	private int selected = -1;

	public TypeDetailSubMenuAdapter(Context context, ArrayList<String> list) {
		this.mContext = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.type_details_filter_item, null);

		TextView filter_name = (TextView) view.findViewById(R.id.filter_name);
		ImageView filter_gou = (ImageView) view.findViewById(R.id.filter_gou);

		if (selected == arg0) {
			filter_gou.setVisibility(View.VISIBLE);
			view.setBackgroundResource(R.drawable.filter_sleted);
		}
		filter_name.setText(list.get(arg0));
		return view;
	}

	public void setSelectItem(int position) {
		this.selected = position;
		notifyDataSetChanged();
	}

}
