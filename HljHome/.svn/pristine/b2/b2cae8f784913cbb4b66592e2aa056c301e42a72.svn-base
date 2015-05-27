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
import com.live.video.constans.Group;

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

/**
 * 年级设置适配器
 * 
 * @author hyc
 * 
 */
public class GradeSetAdapter extends BaseAdapter {

	private List<Group> list;
	private Context mContext;
	private ImageLoader loader;

	public GradeSetAdapter(Context context) {
		mContext = context;
		loader = new ImageLoader(context, 0);
		list = new ArrayList<Group>();
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

	public void setList(ArrayList<Group> groupList) {
		for (Group info : groupList) {
			list.add(info);
		}
		notifyDataSetChanged();
	}

	@Override
	public View getView(int arg0, View contentView, ViewGroup arg2) {
		if (contentView == null) {
			contentView = LayoutInflater.from(mContext).inflate(
					R.layout.grade_set, null);

			TextView tv = (TextView) contentView.findViewById(R.id.grade_text);

			if (!StringTools.isNullOrEmpty(list.get(arg0).groupName)) {
				tv.setVisibility(View.VISIBLE);
				tv.setText(list.get(arg0).groupName);
			} else {
				tv.setVisibility(View.GONE);
			}

			if ("1".equals(list.get(arg0).isDefault)) {
				// contentView.setSelected(true);
				// contentView.setPressed(true);
				// contentView.setBackgroundResource(R.drawable.grade_set_selector);
				contentView.setVisibility(View.GONE);
			} else {
				contentView.setVisibility(View.VISIBLE);
				contentView
						.setBackgroundResource(R.drawable.grade_set_selector);
			}

			final ImageView refImage = (ImageView) contentView
					.findViewById(R.id.ref_movie_image);
			try {

				// ImageThread th = new ImageThread(list.get(arg0).imageUrl);
				// th.start();

				handler = new Handler() {

					@Override
					public void handleMessage(Message msg) {

						Bitmap bitmap = (Bitmap) msg.obj;
						Bitmap localBitmap = ImageUtils
								.createCutReflectedImage(bitmap, 80);

						Logger.log("localBitmap:" + localBitmap);
						refImage.setImageBitmap(localBitmap);
						super.handleMessage(msg);
					}
				};

			} catch (Exception e) {
				Logger.log("error:" + e.toString());
			}

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
