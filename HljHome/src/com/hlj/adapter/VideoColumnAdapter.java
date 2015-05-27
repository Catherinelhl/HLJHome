package com.hlj.adapter;

import java.util.ArrayList;

import com.hlj.HomeActivity.R;
import com.live.video.constans.EpgInfo;
import com.live.video.constans.TVInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 节目列表适配器
 * 
 * @author huangyuchao
 * 
 */
public class VideoColumnAdapter extends BaseAdapter {

	Context mContext;
	ArrayList<EpgInfo> epgList;

	private LayoutInflater inflater;

	public VideoColumnAdapter(Context context, ArrayList<EpgInfo> epgList) {
		this.mContext = context;
		this.epgList = epgList;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return epgList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return epgList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.tv_column_item, null);
			viewHolder = new ViewHolder();
			viewHolder.tv_back_time = (TextView) convertView
					.findViewById(R.id.tv_back_time);
			viewHolder.tv_back_name = (TextView) convertView
					.findViewById(R.id.tv_back_name);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		EpgInfo info = (EpgInfo) epgList.get(position);
		// viewHolder.image.setImageURI(Uri.parse(c.thumb));
		viewHolder.tv_back_time.setText(info.time);
		viewHolder.tv_back_name.setText(info.detail);
		// viewHolder.title.setText(info.title);
		return convertView;
	}

	class ViewHolder {
		public TextView tv_back_time;
		public TextView tv_back_name;
	}

}
