package com.hlj.widget;

import java.text.DecimalFormat;

import com.hlj.HomeActivity.R;
import com.hlj.utils.Logger;

import android.R.integer;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.net.TrafficStats;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 播放界面上弹出视图
 * 
 * @author office
 * 
 */
public class VodCtrTop extends PopupWindow {

	Context mContext;
	Handler mHandler;
	int autoDelayTime;

	public VodCtrTop(Context context, Handler handler) {
		this(context, handler, 3 * 1000);
	}

	public VodCtrTop(Context context, Handler handler, int autoHideTime) {
		this.mContext = context;
		this.mHandler = handler;
		this.autoDelayTime = autoHideTime;
		init();
	}

	TextView vod_play_video_name, vod_play_speed;

	ImageView vod_play_source, vod_play_scale, vod_play_sharp;

	private void init() {
		// setBackgroundDrawable(new BitmapDrawable());
		setFocusable(true);
		setWindowLayoutMode(ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		View contentView = ((LayoutInflater) mContext
				.getSystemService("layout_inflater")).inflate(
				R.layout.vod_play_top, null);
		vod_play_video_name = (TextView) contentView
				.findViewById(R.id.vod_play_video_name);
		vod_play_speed = (TextView) contentView
				.findViewById(R.id.vod_play_speed);

		vod_play_source = (ImageView) contentView
				.findViewById(R.id.vod_play_source);
		vod_play_scale = (ImageView) contentView
				.findViewById(R.id.vod_play_scale);
		vod_play_sharp = (ImageView) contentView
				.findViewById(R.id.vod_play_sharp);

		setContentView(contentView);
	}

	public void setSourceTag(int resourceId) {
		this.vod_play_source.setImageResource(resourceId);
	}

	public void setVideoName(String videoName) {
		this.vod_play_video_name.setText(videoName);
	}

	@Override
	public void showAtLocation(View parent, int gravity, int x, int y) {
		this.mHandler.removeCallbacks(autoHide);
		this.mHandler.postDelayed(this.autoHide, this.autoDelayTime);
		startTestSpeed();
		super.showAtLocation(parent, gravity, x, y);
	}

	private long rxByte;
	private long currentTime;

	private void startTestSpeed() {
		rxByte = TrafficStats.getTotalRxBytes();
		currentTime = System.currentTimeMillis();
		this.mHandler.postDelayed(speed, 1000L);
	}

	public void dismiss() {
		this.mHandler.removeCallbacks(autoHide);
		this.mHandler.removeCallbacks(speed);
		super.dismiss();
	}

	Runnable speed = new Runnable() {

		@Override
		public void run() {
			long time1 = System.currentTimeMillis();
			long rxByte1 = TrafficStats.getTotalRxBytes();

			// Logger.log(Math.abs(rxByte1 - rxByte) + "<><><><><><>");
			// Logger.log(time1 - currentTime + "<><><><><><>");

			long speed1 = (Math.abs(rxByte1 - rxByte)) / (time1 - currentTime)
					* 1000L / 1024L;

			speed1 = speed1 / 8;

			Logger.log("speed1:" + speed1);
			String strSpeed = speed1 + "KB/S";
			if (speed1 > 1000L) {
				speed1 = speed1 / 1024L;
				strSpeed = new DecimalFormat("#.##").format(speed1) + "MB/S";
			}
			vod_play_speed.setText(strSpeed);
			rxByte = TrafficStats.getTotalRxBytes();
			currentTime = System.currentTimeMillis();
			mHandler.postDelayed(speed, 1000);
		}

	};

	Runnable autoHide = new Runnable() {

		@Override
		public void run() {
			dismiss();
		}

	};

}
