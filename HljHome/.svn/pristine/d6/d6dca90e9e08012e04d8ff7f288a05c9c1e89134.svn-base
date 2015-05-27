package com.hlj.adapter;

import java.util.ArrayList;

import com.hlj.HomeActivity.R;
import com.hlj.adapter.VideoInfoAdapter.ViewHolder;
import com.hlj.net.BitmapWorkerTask;
import com.hlj.utils.ImageLoader;
import com.hlj.view.VideoInfo;
import com.live.video.constans.VodRecode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class VodRecodeAdapter extends BaseAdapter {

	public ImageLoader imageLoader;
	private Context mContext;

	private ArrayList<VodRecode> recodes;

	private LayoutInflater inflater;

	public VodRecodeAdapter(Context context, ArrayList<VodRecode> recodes) {
		this.mContext = context;
		this.recodes = recodes;
		inflater = LayoutInflater.from(mContext);
		imageLoader = new ImageLoader(context, 0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return recodes.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return recodes.get(position);
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
			convertView = inflater.inflate(R.layout.type_details_item, null);
			viewHolder = new ViewHolder();
			viewHolder.video_poster = (ImageView) convertView
					.findViewById(R.id.video_poster);
			viewHolder.video_superHD = (ImageView) convertView
					.findViewById(R.id.video_superHD);

			viewHolder.video_banben = (TextView) convertView
					.findViewById(R.id.video_banben);
			viewHolder.video_name = (TextView) convertView
					.findViewById(R.id.video_name);
			viewHolder.video_banben = (TextView) convertView
					.findViewById(R.id.video_banben);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		VodRecode info = (VodRecode) recodes.get(position);
		// viewHolder.image.setImageURI(Uri.parse(c.thumb));
		viewHolder.video_name.setText(info.title);
		viewHolder.video_banben.setText("评分：暂无");
		// viewHolder.title.setText(info.title);
		// imageLoader.displayImage(info.imgUrl, viewHolder.video_poster);
		new BitmapWorkerTask(mContext, viewHolder.video_poster, true, false,
				null).execute(info.imgUrl);

		return convertView;
	}

	class ViewHolder {
		private TextView video_banben;
		private ImageView video_poster;
		private ImageView video_superHD;
		private TextView video_name;
	}

}
