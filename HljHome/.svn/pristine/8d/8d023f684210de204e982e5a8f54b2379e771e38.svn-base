package com.hlj.adapter;

import java.util.ArrayList;

import com.hlj.HomeActivity.R;
import com.live.video.constans.TVInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 频道适配器
 * 
 * @author huangyuchao
 * 
 */
public class VideoListAdapter extends BaseAdapter {

	Context mContext;

	ArrayList<TVInfo> mList;

	private LayoutInflater inflater;

	public VideoListAdapter(Context context, ArrayList<TVInfo> list) {
		this.mContext = context;
		mList = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
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
			convertView = inflater.inflate(R.layout.chanitem, null);
			viewHolder = new ViewHolder();
			viewHolder.image = (ImageView) convertView
					.findViewById(R.id.chan_iv);
			viewHolder.title = (TextView) convertView
					.findViewById(R.id.chan_tv);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		TVInfo info = (TVInfo) mList.get(position);
		// viewHolder.image.setImageURI(Uri.parse(c.thumb));
		viewHolder.title.setText(position + 1 + " " + info.title);
		// viewHolder.title.setText(info.title);
		return convertView;
	}

	class ViewHolder {
		public ImageView image;
		public TextView title;
	}

}
