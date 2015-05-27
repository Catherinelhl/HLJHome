package com.hlj.view;

import com.hlj.HomeActivity.R;
import com.hlj.HomeActivity.VideoPlayActivity;
import com.hlj.utils.Logger;

import android.R.integer;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.os.Handler;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 播放menu
 * 
 * @author office
 * 
 */
public class PlayerMenuControl extends PopupWindow implements
		View.OnKeyListener, View.OnClickListener {

	private Context mContext;

	private Handler mHandler;
	private int autoDismissTime = 5 * 1000;

	private AudioManager audioManager;

	Runnable autoHide = new Runnable() {

		@Override
		public void run() {
			dismiss();

		}

	};

	public WindowManager wm;

	public PlayerMenuControl(Context context, Handler handler) {
		mContext = context;
		mHandler = handler;

		audioManager = (AudioManager) mContext
				.getSystemService(Context.AUDIO_SERVICE);
		init();
	}

	LinearLayout player_menu_voice, player_menu_sclar, player_menu_chooseSet;

	private ImageView[] voiceLevel = new ImageView[10];

	private TextView player_menu_sclar_tv;

	private void init() {
		setBackgroundDrawable(new BitmapDrawable());
		setFocusable(true);
		setWindowLayoutMode(ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);

		View contentView = ((LayoutInflater) mContext
				.getSystemService("layout_inflater")).inflate(
				R.layout.player_menu_contrl, null);

		player_menu_voice = (LinearLayout) contentView
				.findViewById(R.id.player_menu_voice);
		player_menu_sclar = (LinearLayout) contentView
				.findViewById(R.id.player_menu_sclar);

		player_menu_chooseSet = (LinearLayout) contentView
				.findViewById(R.id.player_menu_chooseSet);

		this.voiceLevel[0] = ((ImageView) player_menu_voice
				.findViewById(R.id.menu_voice_1));
		this.voiceLevel[1] = ((ImageView) player_menu_voice
				.findViewById(R.id.menu_voice_2));
		this.voiceLevel[2] = ((ImageView) player_menu_voice
				.findViewById(R.id.menu_voice_3));
		this.voiceLevel[3] = ((ImageView) player_menu_voice
				.findViewById(R.id.menu_voice_4));
		this.voiceLevel[4] = ((ImageView) player_menu_voice
				.findViewById(R.id.menu_voice_5));
		this.voiceLevel[5] = ((ImageView) player_menu_voice
				.findViewById(R.id.menu_voice_6));
		this.voiceLevel[6] = ((ImageView) player_menu_voice
				.findViewById(R.id.menu_voice_7));
		this.voiceLevel[7] = ((ImageView) player_menu_voice
				.findViewById(R.id.menu_voice_8));
		this.voiceLevel[8] = ((ImageView) player_menu_voice
				.findViewById(R.id.menu_voice_9));
		this.voiceLevel[9] = ((ImageView) player_menu_voice
				.findViewById(R.id.menu_voice_10));
		player_menu_voice.setOnKeyListener(this);
		player_menu_sclar.setOnKeyListener(this);

		player_menu_sclar_tv = (TextView) player_menu_sclar
				.findViewById(R.id.player_menu_sclar_tv);

		setContentView(contentView);
	}

	@Override
	public void showAtLocation(View parent, int gravity, int x, int y) {
		mHandler.removeCallbacks(autoHide);
		mHandler.postDelayed(autoHide, autoDismissTime);
		super.showAtLocation(parent, gravity, x, y);
	}

	@Override
	public void dismiss() {
		mHandler.removeCallbacks(autoHide);
		super.dismiss();
	}

	@Override
	public void onClick(View arg0) {
		Logger.log("onClick:---执行---" + arg0.getId() + "<><><><><><>");

		switch (arg0.getId()) {
		case R.id.player_menu_sclar:
			((VideoPlayActivity) mContext).changeScales(MODE_4_3);
			break;
		}

	}

	private void setVoice(int type) {
		// 减小音量
		if (AudioManager.ADJUST_LOWER == type) {
			audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, type,
					AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
		}
		// 增大音量
		else if (AudioManager.ADJUST_RAISE == type) {
			audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, type,
					AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
		}
		showVoiceLevel(getVoice());
	}

	public void showVoiceLevel(int level) {
		Logger.log("level:" + level + "<><><><><><>");
		if (level <= 0) {
			for (ImageView img : voiceLevel) {
				img.setImageResource(R.drawable.menu_voice_outuse);
			}
		} else if (level > 0 && level <= 10) {
			for (int k = 0; k < 10; k++) {
				if (k < level) {
					voiceLevel[k].setImageResource(R.drawable.menu_voice_inuse);
				} else {
					voiceLevel[k]
							.setImageResource(R.drawable.menu_voice_outuse);
				}
			}
		} else {
			for (ImageView img : voiceLevel) {
				img.setImageResource(R.drawable.menu_voice_inuse);
			}
		}

	}

	public int getVoice() {
		int currentVolume = audioManager
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		int maxVolume = audioManager
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		return (int) (((float) currentVolume / maxVolume) * 10);
	}

	public static final int MODE_ORIGN = 0;
	public static final int MODE_4_3 = 1;
	public static final int MODE_16_9 = 2;

	int mode = MODE_16_9;

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		mHandler.removeCallbacks(autoHide);
		mHandler.postDelayed(autoHide, autoDismissTime);
		Logger.log("PlayerMenuControl:" + keyCode + ",action:"
				+ event.getAction());
		if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			Logger.log("PlayerMenuControl----KEYCODE_DPAD_LEFT------执行了");
			switch (v.getId()) {
			case R.id.player_menu_voice:
				setVoice(AudioManager.ADJUST_LOWER);
				break;
			case R.id.player_menu_sclar:
				mode--;
				if (mode < MODE_ORIGN) {
					mode = MODE_16_9;
				}
				Logger.log("mode:" + mode);
				changeScele(mode);
				break;
			}
		}

		if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			Logger.log("PlayerMenuControl----KEYCODE_DPAD_RIGHT------执行了");
			switch (v.getId()) {
			case R.id.player_menu_voice:
				setVoice(AudioManager.ADJUST_RAISE);
				break;
			case R.id.player_menu_sclar:
				mode++;
				if (mode > MODE_16_9) {
					mode = MODE_ORIGN;
				}
				Logger.log("mode:" + mode);
				changeScele(mode);
				break;
			}
		}
		return false;
	}

	public void changeScele(int mode) {
		int num = mode;

		switch (mode) {
		case MODE_ORIGN:
			player_menu_sclar_tv.setText("原始比例");
			break;
		case MODE_4_3:
			player_menu_sclar_tv.setText(" 4:3 ");
			break;
		case MODE_16_9:
			player_menu_sclar_tv.setText(" 16:9 ");
			break;
		}
		((VideoPlayActivity) mContext).changeScales(mode);
	}
}
