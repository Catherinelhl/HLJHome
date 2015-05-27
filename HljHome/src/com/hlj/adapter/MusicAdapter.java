package com.hlj.adapter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

public class MusicAdapter extends BaseAdapter {

	private List<VideoInfo> videolist = new ArrayList<VideoInfo>();
	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	private Context mContext;
	private ImageLoader loader;

	public MusicAdapter(Context context) {
		//Logger.log("-----------------------MovieAdapter-------------------");
		mContext = context;
		loader = new ImageLoader(context, 0);
		// list = arrayList;
	}

	@Override
	public int getCount() {
		//Logger.log("-----------------------getCount-------------------");
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		//Logger.log("-----------------------getItem-------------------");
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		//Logger.log("-----------------------getItemId-------------------");
		// TODO Auto-generated method stub
		return arg0;
	}

	public void setMap(Map<String, Object> map) {
		//Logger.log("-----------------------setMap-------------------");
		list.add(map);
		notifyDataSetChanged();
	}

	public void ListClear() {
		list.clear();
		list = new ArrayList<Map<String, Object>>();
	}

	public void setList(ArrayList<VideoInfo> videoList) {
		//Logger.log("-----------------------setList-------------------");
		// for (VideoInfo info : videoList) {
		// list.add(info);
		// }
		this.videolist = videoList;
		notifyDataSetChanged();
	}

	@Override
	public View getView(int arg0, View contentView, ViewGroup arg2) {
		//Logger.log("-----------------------getView-------------------");
		if (contentView == null) {
			contentView = LayoutInflater.from(mContext).inflate(R.layout.movie,
					null);
			ImageView image = (ImageView) contentView
					.findViewById(R.id.movie_image);

			Map<String, Object> map = list.get(arg0);

			loader.displayImage(String.valueOf(map.get("imageUrl")), image);

			TextView tv = (TextView) contentView.findViewById(R.id.movie_text);

			if (!StringTools.isNullOrEmpty(String.valueOf(map.get("title")))) {
				tv.setVisibility(View.VISIBLE);
				tv.setText(String.valueOf(map.get("title")));
			} else {
				tv.setVisibility(View.GONE);
			}

			// final ImageView refImage = (ImageView) contentView
			// .findViewById(R.id.ref_movie_image);
			// try {
			//
			// // ImageThread th = new ImageThread(list.get(arg0).imageUrl);
			// // th.start();
			//
			// handler = new Handler() {
			//
			// @Override
			// public void handleMessage(Message msg) {
			//
			// Bitmap bitmap = (Bitmap) msg.obj;
			// Bitmap localBitmap = ImageUtils
			// .createCutReflectedImage(bitmap, 80);
			//
			// Logger.log("localBitmap:" + localBitmap);
			// refImage.setImageBitmap(localBitmap);
			// super.handleMessage(msg);
			// }
			// };
			//
			// } catch (Exception e) {
			// Logger.log("error:" + e.toString());
			// }

		}
		return contentView;
	}

	Handler handler;

	class ImageThread extends Thread {

		String imageUrl;

		public ImageThread(String url) {
			this.imageUrl = url;
		}

		@Override
		public void run() {
			try {
				URL url = new URL(imageUrl);
				Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());
				Logger.log("bitmap:" + bitmap);

				Message m = new Message();
				m.obj = bitmap;
				handler.sendMessage(m);

			} catch (Exception e) {
				Logger.log("error:" + e.toString());
			}

			super.run();
		}
	}

}
