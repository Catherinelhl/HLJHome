package com.hlj.adapter;

import java.util.ArrayList;

import net.tsz.afinal.FinalBitmap;

import com.hlj.HomeActivity.R;
import com.hlj.adapter.VideoListAdapter.ViewHolder;
import com.hlj.net.BitmapWorkerTask;
import com.hlj.utils.ImageLoader;
import com.hlj.utils.StringTools;
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

public class VideoInfoAdapter extends BaseAdapter {

	private Context mContext;

	private ArrayList<VideoInfo> list;

	private LayoutInflater inflater;

	// private FinalBitmap fb;

	ImageLoader imageLoader;

	public VideoInfoAdapter(Context mContext, ArrayList<VideoInfo> list) {
		this.mContext = mContext;
		this.list = list;
		inflater = LayoutInflater.from(mContext);
		// fb = FinalBitmap.create(mContext, mContext.getCacheDir().toString());
		// fb.configLoadingImage(R.drawable.default_film_img);
		// fb.configLoadfailImage(R.drawable.default_film_img);

		imageLoader = new ImageLoader(mContext, R.drawable.hao260x366);
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

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		VideoInfo info = (VideoInfo) list.get(position);
		// viewHolder.image.setImageURI(Uri.parse(c.thumb));
		viewHolder.video_name.setText(info.title);

		if (StringTools.isNullOrEmpty(info.price) || "0".equals(info.price)) {
			viewHolder.video_banben.setText("评分：暂无");
		} else {
			viewHolder.video_banben.setText("评分：" + info.price + "");
		}

		// viewHolder.title.setText(info.title);

		// this.fb.display(viewHolder.video_poster, info.imageUrl);

		imageLoader.displayImage(info.imageUrl, viewHolder.video_poster);

		// new BitmapWorkerTask(mContext, viewHolder.video_poster, true,
		// false,null).execute(info.imageUrl);

		return convertView;
	}

	public void changePosterImg() {

	}

	class ViewHolder {
		private TextView video_banben;
		private ImageView video_poster;
		private ImageView video_superHD;
		private TextView video_name;
	}

}
