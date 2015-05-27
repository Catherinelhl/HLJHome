package com.hlj.adapter;

import java.util.ArrayList;

import net.tsz.afinal.FinalBitmap;

import com.hlj.HomeActivity.R;
import com.hlj.adapter.VideoListAdapter.ViewHolder;
import com.hlj.net.BitmapWorkerTask;
import com.hlj.view.MyImageView;
import com.hlj.view.VideoInfo;
import com.live.video.constans.TVInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HotVideoAdapter extends BaseAdapter {

	private Context mContext;

	private ArrayList<VideoInfo> list;

	private LayoutInflater inflater;

	private FinalBitmap fb;

	public HotVideoAdapter(Context mContext, ArrayList<VideoInfo> list) {
		this.mContext = mContext;
		this.list = list;
		inflater = LayoutInflater.from(mContext);
		//fb = FinalBitmap.create(mContext, mContext.getCacheDir().toString());
		//fb.configLoadingImage(R.drawable.hao260x366);
		//fb.configLoadfailImage(R.drawable.hao260x366);
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
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = inflater.inflate(
					R.layout.video_details_recommend_item, null);
			viewHolder = new ViewHolder();
			viewHolder.video_poster = (ImageView) convertView
					.findViewById(R.id.details_recommend_poster);

			viewHolder.video_name = (TextView) convertView
					.findViewById(R.id.details_recommend_name);

			viewHolder.video_ref = (ImageView) convertView
					.findViewById(R.id.details_recommend_ref);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		VideoInfo info = (VideoInfo) list.get(position);
		// viewHolder.image.setImageURI(Uri.parse(c.thumb));
		viewHolder.video_name.setText(info.title);
		// viewHolder.title.setText(info.title);

		// this.fb.display(viewHolder.video_poster, info.imageUrl);
		new BitmapWorkerTask(mContext, viewHolder.video_poster, true, false,
				null).execute(info.imageUrl);

		return convertView;
	}

	public void changePosterImg() {

	}

	class ViewHolder {
		private ImageView video_poster;
		private TextView video_name;
		private ImageView video_ref;
	}

}
