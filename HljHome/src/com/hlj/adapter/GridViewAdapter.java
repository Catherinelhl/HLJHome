package com.hlj.adapter;

import java.util.ArrayList;
import java.util.List;

import com.hlj.HomeActivity.R;
import com.hlj.HomeActivity.RecomdVideoDetailsActivity;
import com.hlj.HomeActivity.VideoPlayActivity;
import com.hlj.net.GetContentInfoResponse.Item.PolymAddress.Video;
import com.hlj.utils.CommonTools;
import com.hlj.view.VideoInfo;
import com.live.video.constans.Constants;

import android.R.integer;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils.TruncateAt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GridViewAdapter extends BaseAdapter {
	private Context con;
	// private ArrayList<VideoInfo> list;
	// private ArrayList<String> list;
	ArrayList<Video> list;
	LayoutInflater inflater;

	// public GridViewAdapter(Context context, ArrayList<String> mList) {
	// con = context;
	// list = mList;
	// inflater = LayoutInflater.from(context);
	//
	// }

	// public GridViewAdapter(Context context, ArrayList<VideoInfo> recList) {
	// con = context;
	// list = recList;
	// inflater = LayoutInflater.from(context);
	// }

	public GridViewAdapter(Context context, ArrayList<Video> videoList) {
		con = context;
		list = videoList;
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

	private TextView mTextView;
	private ImageView mImageView;
	private Button btn_title;
	int count;
	private LinearLayout ll;

	@Override
	public View getView(int position, View convertview, ViewGroup parent) {
		if (convertview == null) {
			convertview = inflater.inflate(R.layout.view_grid_cell, null);
			ll = (LinearLayout) convertview.findViewById(R.id.ll);
			btn_title = (Button) convertview.findViewById(R.id.btn_title);
		}
		final String numText = (position + 1) + ":" + list.get(position).name;

		// mTextView.setText(list.get(position).toString());
		btn_title.setText(numText);
		// for (int j = 0; j < list.size(); j++) {
		// ll.addView(createSetBtn(j, list.get(j).name));
		// }
		//
		btn_title.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				System.out.println("setOnClickListener===>");
				// Toast.makeText(
				// con,
				// "haha, you dot the itemis "
				// + RecomdVideoDetailsActivity.videoList,
				// Toast.LENGTH_SHORT).show();
				// // TODO Auto-generated method stub

				String str = btn_title.getText().toString().trim()
						.substring(0, 1);
				System.out.println("==========>我选择的是==》第" + str + "集");
				count = Integer.parseInt(str);
				RecomdVideoDetailsActivity.videoaddress = RecomdVideoDetailsActivity.videoList
						.get(count).videoAddress;
				RecomdVideoDetailsActivity.videoId = RecomdVideoDetailsActivity.videoList
						.get(count).videoId;
				//
				// // 存储title
				CommonTools.saveTitle(list.get(count).name);
				CommonTools.setVideoName(list.get(count).name);
				Constants.isDefaultPlay = RecomdVideoDetailsActivity.videoList
						.get(count).isDefaultPlay;
				if (Constants.isDefaultPlay == 0
						|| Constants.isDefaultPlay == 1
						|| Constants.isDefaultPlay == 4) {
					play();
				} else if (Constants.isDefaultPlay == 2) {
				}
			}
		});
		return convertview;
	}

	private Button createSetBtn(final int i, final String name) {
		Button button = new Button(con);
		button.setWidth(LayoutParams.MATCH_PARENT);
		button.setHeight(50);

		final String numText = (i + 1) + ":" + name;

		button.setText(numText);
		button.setTextSize(17.0F);
		// button.setMaxLines(2);
		button.setSingleLine(true);
		button.setEllipsize(TruncateAt.MARQUEE);
		button.setMarqueeRepeatLimit(3);
		button.setTag(Integer.valueOf(i));
		button.setBackgroundResource(R.drawable.video_details_btn_selector);
		button.setTextColor(Color.WHITE);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String videoaddress = list.get(i).videoAddress;
				String videoId = list.get(i).videoId;

				// 存储title
				CommonTools.saveTitle(RecomdVideoDetailsActivity.recList
						.get(count).title);
				CommonTools.setVideoName(name);

				Constants.isDefaultPlay = list.get(i).isDefaultPlay;

				if (Constants.isDefaultPlay == 0
						|| Constants.isDefaultPlay == 1
						|| Constants.isDefaultPlay == 4) {
					System.out.println("hello,我执行的是androidPlay。。。。");
					play();
				} else if (Constants.isDefaultPlay == 2) {
					System.out.println("hello,我执行的是vlcPlay。。。。");
				}

			}
		});
		return button;
	}

	private void play() {
		Intent it = new Intent(con, VideoPlayActivity.class);
		// it.putStringArrayListExtra("list", detailsKeyList);
		// it.putExtra("numDrama", numDrama);
		it.putExtra("name", 1);
		it.putExtra("url",
				RecomdVideoDetailsActivity.videoList.get(count).videoAddress);
		it.putExtra("title",
				RecomdVideoDetailsActivity.recList.get(count).title);
		it.putExtra("contentId",
				RecomdVideoDetailsActivity.recList.get(count).contentId);
		it.putExtra("videoId",
				RecomdVideoDetailsActivity.videoList.get(count).videoId);
		it.putExtra("numText",
				RecomdVideoDetailsActivity.recList.get(count).title);
		it.putExtra("videoSourceName",
				RecomdVideoDetailsActivity.recList.get(count).versionsname);
		con.startActivity(it);
		VideoPlayActivity.instance.finish();
	}

}
