package com.hlj.adapter;

import java.util.ArrayList;

import com.hlj.HomeActivity.R;
import com.live.video.constans.AppBean;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ApplicationAdapter extends BaseAdapter {

	Context mContext;
	ArrayList<AppBean> appBeans;

	public ApplicationAdapter(Context context, ArrayList<AppBean> appBeans) {
		this.mContext = context;
		this.appBeans = appBeans;
	}

	public void changeData(ArrayList<AppBean> paramArrayList) {
		this.appBeans = paramArrayList;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return appBeans.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return appBeans.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View localView = LayoutInflater.from(mContext).inflate(
				R.layout.app_list_item, null);
		ImageView localImageView = (ImageView) localView
				.findViewById(R.id.app_icon);
		TextView localTextView = (TextView) localView
				.findViewById(R.id.app_name);
		AppBean localAppBean = (AppBean) this.appBeans.get(position);
		localImageView.setImageDrawable(localAppBean.getIcon());
		localTextView.setText(localAppBean.getName());
		localView.setLayoutParams(new AbsListView.LayoutParams(160, 160));
		return localView;
	}
}
