package com.hlj.adapter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.hlj.HomeActivity.R;
import com.hlj.utils.ImageLoader;
import com.hlj.utils.ImageUtils;
import com.hlj.utils.Logger;
import com.hlj.utils.StringTools;
import com.hlj.view.VideoInfo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SwitchAdapter extends BaseAdapter {

	private List<VideoInfo> list = new ArrayList<VideoInfo>();
	private Context mContext;
	private ImageLoader loader;

	public SwitchAdapter(Context context, List<VideoInfo> arrayList) {
		mContext = context;
		loader = new ImageLoader(context, 0);
		list = arrayList;
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

	public void setList(ArrayList<VideoInfo> videoList) {
		// for (VideoInfo info : videoList) {
		// list.add(info);
		// }
		list = videoList;
		notifyDataSetChanged();
	}

	@Override
	public View getView(int arg0, View contentView, ViewGroup arg2) {
		if (contentView == null) {
			contentView = LayoutInflater.from(mContext).inflate(
					R.layout.switch_layout, null);
			ImageView image = (ImageView) contentView
					.findViewById(R.id.movie_image);
			// loader.displayImage(list.get(arg0).imageUrl, image);

			TextView tv = (TextView) contentView.findViewById(R.id.movie_text);

			if (!StringTools.isNullOrEmpty(list.get(arg0).groupName)) {
				tv.setVisibility(View.VISIBLE);
				tv.setText(list.get(arg0).groupName);
			} else {
				tv.setVisibility(View.GONE);
			}

		}
		return contentView;
	}
}
