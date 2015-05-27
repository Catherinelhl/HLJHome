package com.hj.widget;

import com.hlj.HomeActivity.R;
import com.hlj.HomeActivity.set.SettingAboutUs;
import com.hlj.utils.Logger;
import com.hlj.utils.StringUtils;
import com.hlj.widget.BackVideoView;
import com.hlj.widget.ListVideoView;
import com.hlj.widget.VideoView;

import android.R.integer;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * 播放控制
 * 
 * @author office
 * 
 */
public class VodBackCtrBot extends PopupWindow {

	private Context mContext;
	//private ListVideoView mVideoView;
	private BackVideoView mVideoView;
	private Handler mHandler;

	// public VodBackCtrBot(Context context, ListVideoView videoView,
	// Handler handler) {
	// this.mContext = context;
	// this.mVideoView = videoView;
	// this.mHandler = handler;
	// init();
	// }
	
	public VodBackCtrBot(Context context, BackVideoView videoView,
			Handler handler) {
		this.mContext = context;
		this.mVideoView = videoView;
		this.mHandler = handler;
		init();
	}

	private View contentView;

	private SeekBar vod_seek_new_seekbar;
	private TextView vod_seek_new_curr;
	private TextView vod_seek_new_dura;
	private TextView play_bottom_tip_time;

	private void init() {
		setBackgroundDrawable(new BitmapDrawable());
		setFocusable(true);
		setWindowLayoutMode(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		this.contentView = ((LayoutInflater) mContext
				.getSystemService("layout_inflater")).inflate(
				R.layout.vod_seek_layout, null);

		vod_seek_new_seekbar = (SeekBar) contentView
				.findViewById(R.id.vod_seek_new_seekbar);
		vod_seek_new_curr = (TextView) contentView
				.findViewById(R.id.vod_seek_new_curr);
		vod_seek_new_dura = (TextView) contentView
				.findViewById(R.id.vod_seek_new_dura);
		play_bottom_tip_time = (TextView) contentView
				.findViewById(R.id.play_bottom_tip_time);

		vod_seek_new_seekbar.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View arg0, int arg1, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN
						&& arg1 == KeyEvent.KEYCODE_DPAD_LEFT) {
					// Logger.log("ACTION_DOWN----------KEYCODE_DPAD_LEFT");
					setDraggingProgress(101);

				} else if (event.getAction() == KeyEvent.ACTION_DOWN
						&& arg1 == KeyEvent.KEYCODE_DPAD_RIGHT) {
					// Logger.log("ACTION_DOWN----------KEYCODE_DPAD_RIGHT");
					setDraggingProgress(100);

				} else if (event.getAction() == KeyEvent.ACTION_UP) {

					// Logger.log("draggingDura:"
					// + StringUtils.generateTime(draggingDura));
					if (isDragging && mVideoView.isPlaying()) {
						mVideoView.seekTo((int) draggingDura);
					}
				}

				return true;
			}
		});

		setTipTimeLocation(1L);

		setContentView(contentView);
	}

	private long draggingDura;

	public boolean isDragging = false;

	public void setDraggingProgress(int num) {
		play_bottom_tip_time.setVisibility(View.VISIBLE);
		mHandler.removeCallbacks(hideTip);
		mHandler.postDelayed(hideTip, 2 * 1000);

		if (!isDragging && mVideoView.isPlaying()) {
			draggingDura = mVideoView.getCurrentPosition();
			isDragging = true;
		}

		// Logger.log("draggingDura:" + draggingDura);
		if (duration > 0) {

			long l = 1000L * draggingDura / duration;
			vod_seek_new_seekbar.setProgress((int) l);
			setTipTimeLocation(l);
			play_bottom_tip_time
					.setText(StringUtils.generateTime(draggingDura));
			switch (num) {
			case 100:
				draggingDura += 15 * 1000;
				if (draggingDura >= duration) {
					draggingDura = duration;
				}
				// Logger.log("draggingDura:" + draggingDura);
				break;

			case 101:
				draggingDura -= 15 * 1000;

				if (draggingDura <= 0) {
					draggingDura = 0;
				}

				break;
			}
		}
	}

	public void show() {
		System.out.print("Seek Show============");
		showAtLocation(this.mVideoView, 17, 0, 200);
		setProgress();
	}

	@Override
	public void showAtLocation(View parent, int gravity, int x, int y) {
		// TODO Auto-generated method stub
		super.showAtLocation(parent, gravity, x, y);
		mHandler.post(updateProgress);
		mHandler.removeCallbacks(hide);
		mHandler.postDelayed(hide, 5 * 1000);
		setProgress();
	}

	private long duration;

	private int setProgress() {
		if (mVideoView != null && mVideoView.isPlaying()) {
			long currentPosition = mVideoView.getCurrentPosition();
			duration = mVideoView.getDuration();

			if (duration != 0) {
				long position = 1000L * currentPosition / this.duration;
				vod_seek_new_seekbar.setProgress((int) position);

				int bufferProgress = mVideoView.getBufferPercentage();
				vod_seek_new_seekbar.setSecondaryProgress(bufferProgress);
				vod_seek_new_curr.setText(StringUtils
						.generateTime(currentPosition));
				vod_seek_new_dura.setText(StringUtils.generateTime(duration));
				play_bottom_tip_time.setText(StringUtils
						.generateTime(currentPosition));
				setTipTimeLocation(position);
			}

		}

		return 0;
	}

	private void setTipTimeLocation(long position) {
		int[] array = new int[2];
		this.contentView.getLocationOnScreen(array);
		this.vod_seek_new_seekbar.getLocationOnScreen(array);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.WRAP_CONTENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);
		params.setMargins(
				(int) ((int) position * vod_seek_new_seekbar.getWidth() / 1000L)
						+ array[0] - play_bottom_tip_time.getWidth() / 2, 0, 0,
				0);
		play_bottom_tip_time.setLayoutParams(params);
	}

	private Runnable updateProgress = new Runnable() {

		@Override
		public void run() {
			if (isShowing()) {
				if (mVideoView.isPlaying()) {
					long i = mVideoView.getCurrentPosition();
					vod_seek_new_curr.setText(StringUtils.generateTime(i));
					setProgress();
					mHandler.postDelayed(updateProgress, 1000 - i % 1000);
				}
			}

		}
	};

	private Runnable hideTip = new Runnable() {

		@Override
		public void run() {
			if (play_bottom_tip_time.getVisibility() == View.VISIBLE) {
				play_bottom_tip_time.setVisibility(View.INVISIBLE);
			}

		}
	};

	private Runnable hide = new Runnable() {

		@Override
		public void run() {
			if (VodBackCtrBot.this.isShowing()) {
				dismiss();
			}

		}
	};

}
