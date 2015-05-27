package com.hlj.widget;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.hlj.HomeActivity.R;
import com.hlj.utils.Logger;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.net.TrafficStats;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 直播条
 * 
 * @author office
 * 
 */
public class LiveControl extends LinearLayout {

	Context mContext;
	Handler mHandler = new Handler();

	public LiveControl(Context context) {
		super(context);
		mContext = context;
		init();
	}

	public LiveControl(Context context, Handler handler) {
		super(context);
		this.mContext = context;
		this.mHandler = handler;
		init();
	}

	TextView channleName, channleNum, channleSource;

	TextView channleEPG1, channleEPG2;

	TextView realSpeed, sysTime, cwTime;

	ProgressBar bufImg;
	ImageView gifBuff;

	private AnimationDrawable anim;

	private void init() {
		initFloatingWindowLayout();
		View view = ((LayoutInflater) mContext
				.getSystemService("layout_inflater")).inflate(
				R.layout.live_contrl_new, this);
		this.channleName = ((TextView) view
				.findViewById(R.id.live_control_new_channle_name));
		this.channleSource = ((TextView) view
				.findViewById(R.id.live_control_new_channle_source));
		this.channleEPG1 = ((TextView) view
				.findViewById(R.id.live_control_new_epg_current));
		this.channleEPG2 = ((TextView) view
				.findViewById(R.id.live_control_new_epg_next));
		this.channleNum = ((TextView) view
				.findViewById(R.id.live_control_new_channle_index));
		this.realSpeed = ((TextView) view
				.findViewById(R.id.live_control_new_speed));
		this.sysTime = ((TextView) view
				.findViewById(R.id.live_control_new_systime));
		this.cwTime = ((TextView) view
				.findViewById(R.id.live_control_new_cwtime));
		this.gifBuff = ((ImageView) view
				.findViewById(R.id.live_control_new_bufgif));
		this.bufImg = ((ProgressBar) view
				.findViewById(R.id.live_control_new_bufimg));
		this.anim = ((AnimationDrawable) gifBuff.getDrawable());
	}

	private WindowManager manager;
	private WindowManager.LayoutParams mDecorLayoutParams;

	private void initFloatingWindowLayout() {
		manager = ((WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE));
		mDecorLayoutParams = new WindowManager.LayoutParams();

		mDecorLayoutParams.gravity = 80;
		mDecorLayoutParams.height = -2;
		mDecorLayoutParams.format = -3;
		mDecorLayoutParams.flags = (0x18 | mDecorLayoutParams.flags);

	}

	/**
	 * 设置频道名
	 * 
	 * @param channelName
	 */
	public void setChannelName(String channelName) {

		this.channleName.setText(channelName);
	}

	public void setchannleEPG1(String currentEpg) {
		this.channleEPG1.setText(currentEpg);
	}

	public void setchannleEPG2(String nextEpg) {
		this.channleEPG2.setText(nextEpg);
	}

	public void setchannleNum(String channelNum) {
		this.channleNum.setText(channelNum);
	}

	public void setchannelSource(String channelSource) {
		this.channleSource.setText(channelSource);
	}

	public void setcwTime(String cwTime) {
		this.cwTime.setText(cwTime);
	}

	/**
	 * 设置状态
	 * 
	 * @param flag
	 */
	public void setState(int flag) {
		if (flag == 0) {
			this.anim.start();
			this.gifBuff.setVisibility(View.VISIBLE);
			this.bufImg.setVisibility(View.GONE);
		} else if (flag == 1) {
			this.anim.stop();
			this.gifBuff.setVisibility(View.GONE);
			this.bufImg.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 展示
	 */
	public void show() {

		if (getParent() == null) {
			manager.addView(this, mDecorLayoutParams);
		}

		setSysTime(new SimpleDateFormat("HH:mm").format(new Date()));
		mHandler.postDelayed(autoHide, 5 * 1000);
	}

	/**
	 * 设置时间
	 * 
	 * @param strTime
	 */
	public void setSysTime(String strTime) {
		this.sysTime.setText(strTime);
	}

	private long rxByte;
	private long currentTime;

	public void startTestSpeed() {
		rxByte = TrafficStats.getTotalRxBytes();
		currentTime = System.currentTimeMillis();
		this.mHandler.postDelayed(speed, 1000L);
	}

	Runnable speed = new Runnable() {

		@Override
		public void run() {
			long time1 = System.currentTimeMillis();
			long rxByte1 = TrafficStats.getTotalRxBytes();

			// Logger.log(Math.abs(rxByte1 - rxByte) + "<><><><><><>");
			// Logger.log(time1 - currentTime + "<><><><><><>");
			long speed1 = 0;
			if ((time1 - currentTime) != 0) {
				speed1 = (Math.abs(rxByte1 - rxByte)) / (time1 - currentTime)
						* 1000L / 1024L;
			}

			speed1 = speed1 / 5;

			Logger.log("speed1:" + speed1);
			String strSpeed = speed1 + "KB/S";
			if (speed1 > 1000L) {
				speed1 = speed1 / 1024L;
				strSpeed = new DecimalFormat("#.##").format(speed1) + "MB/S";
			}
			realSpeed.setText(strSpeed);
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

	/**
	 * 移除线程和view
	 */
	public void dismiss() {
		this.mHandler.removeCallbacks(autoHide);
		this.mHandler.removeCallbacks(speed);
		if (getParent() != null)
			manager.removeView(this);
	}

}
