package com.hlj.adapter;

import java.util.List;
import com.hlj.HomeActivity.R;
import com.hlj.view.VideoInfo;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils.TruncateAt;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.TextView;

/**
 * 学习的每10集的一个list
 * 
 * @author huangyuchao
 * 
 */
public class DetailsKeyListAdapter extends BaseAdapter {

	private Context context;
	private List<VideoInfo> list;
	private LayoutInflater inflater;

	public DetailsKeyListAdapter(Context context, List<VideoInfo> list) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
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
		View localView = this.inflater.inflate(
				R.layout.video_details_choose_arts_item, null);
		TextView tv = (TextView) localView.findViewById(R.id.text);
		//tv.setText(list.get(position).title + "(第"+ list.get(position).lessonid + "课)");
		
		tv.setText(list.get(position).title);
		return localView;
	}

	public void setDataChanged(List<VideoInfo> list) {
		this.list = list;
		notifyDataSetChanged();
	}
}
