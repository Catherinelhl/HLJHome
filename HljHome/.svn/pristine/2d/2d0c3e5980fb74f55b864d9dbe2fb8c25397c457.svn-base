package com.hlj.adapter;

import java.util.HashMap;

import com.hlj.HomeActivity.R;
import com.live.video.constans.AppBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AppShowTopAdapter extends BaseAdapter {

	private Context context;
	private HashMap<Integer, AppBean> topApps;

	public AppShowTopAdapter(Context paramContext,
			HashMap<Integer, AppBean> paramHashMap) {
		this.context = paramContext;
		this.topApps = paramHashMap;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 9;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return topApps.get(position);
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
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.app_dia_totop_item, null);
			holder.appIcon = (ImageView) convertView
					.findViewById(R.id.app_icon);
			holder.appName = (TextView) convertView.findViewById(R.id.app_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (topApps.get(position) != null) {
			holder.appIcon.setImageDrawable(topApps.get(
					Integer.valueOf(position)).getIcon());
			holder.appName.setText(topApps.get(Integer.valueOf(position))
					.getName());
		} else {
			holder.appIcon.setImageResource(R.drawable.add_apps);
			holder.appName.setText("（未添加）");
		}

		return convertView;
	}

	private class ViewHolder {

		private ImageView appIcon;
		private TextView appName;

	}

}
