package com.hlj.adapter;

import java.util.ArrayList;

import com.hlj.HomeActivity.R;
import com.live.video.constans.AppBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AppChoseListAdapter extends BaseAdapter {

	private ArrayList<AppBean> beans;
	private Context mContext;

	public AppChoseListAdapter(Context context, ArrayList<AppBean> list) {
		beans = list;
		mContext = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return beans.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return beans.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(this.mContext).inflate(
					R.layout.news_item, null);
			holder = new ViewHolder();
			holder.appIcon = (ImageView) convertView
					.findViewById(R.id.news_img);
			holder.appName = (TextView) convertView
					.findViewById(R.id.news_text);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		AppBean localAppBean = (AppBean) this.beans.get(position);

		holder.appIcon.setImageDrawable(localAppBean.getIcon());
		holder.appName.setText(localAppBean.getName());
		return convertView;
	}

	private class ViewHolder {
		private ImageView appIcon;
		private TextView appName;
	}

}
