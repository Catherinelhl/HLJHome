package com.hlj.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.hlj.HomeActivity.R;
import com.hlj.utils.SwissArmyKnife;
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
public class VideoBackColumnAdapter extends BaseAdapter {

	Context mContext;
	ArrayList<EpgInfo> epgList;

	private LayoutInflater inflater;

	public VideoBackColumnAdapter(Context context, ArrayList<EpgInfo> epgList) {
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
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.tv_back_column_item, null);
			
			viewHolder.tv_back_time = (TextView) convertView
					.findViewById(R.id.tv_back_time);
			viewHolder.tv_back_name = (TextView) convertView
					.findViewById(R.id.tv_back_name);
			viewHolder.tv_back_tip = (TextView) convertView
					.findViewById(R.id.tv_back_tip);
			viewHolder.tv_back_log = (ImageView) convertView
					.findViewById(R.id.tv_back_log);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		EpgInfo info = (EpgInfo) epgList.get(position);
		// viewHolder.image.setImageURI(Uri.parse(c.thumb));
		viewHolder.tv_back_time.setText(info.showTime);
		viewHolder.tv_back_name.setText(info.title);

		if (null != info.textMap && info.textMap.size() > 0) {
			String text = info.textMap.get("text");
			viewHolder.tv_back_tip.setText(text);
		}else{
			viewHolder.tv_back_tip.setText("");
		}

		String date = info.date;
		String time = info.showTime;

		// date = date.replaceAll("-", "/");

		// 大于当前时间隐藏
		if (SwissArmyKnife.getDateDiffOther(date)
				&& SwissArmyKnife.getTimeDiff(time) > 0) {
			viewHolder.tv_back_log.setVisibility(View.GONE);
		} else {
			viewHolder.tv_back_log.setVisibility(View.VISIBLE);
			if (null != info.imageMap && info.imageMap.size() > 0) {
				int drawableId = info.imageMap.get("img");
				viewHolder.tv_back_log.setBackgroundResource(drawableId);
			}else{
				viewHolder.tv_back_log.setBackgroundResource(R.drawable.huikan);
			}
		}

		// viewHolder.title.setText(info.title);
		return convertView;
	}

	class ViewHolder {
		public TextView tv_back_time;
		public TextView tv_back_name;

		public TextView tv_back_tip;
		public ImageView tv_back_log;
	}

}
