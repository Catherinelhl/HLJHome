/*
 * Copyright (C) 2013 yixia.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hlj.HomeActivity;

import java.util.ArrayList;

import com.hlj.HomeActivity.R;
import com.hlj.utils.Logger;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnBufferingUpdateListener;
import io.vov.vitamio.MediaPlayer.OnCompletionListener;
import io.vov.vitamio.MediaPlayer.OnErrorListener;
import io.vov.vitamio.MediaPlayer.OnPreparedListener;
import io.vov.vitamio.MediaPlayer.OnVideoSizeChangedListener;
import io.vov.vitamio.utils.StringUtils;
import io.vov.vitamio.widget.MediaController;

/**
 * 视频类
 * 
 * @author huangyuchao
 * 
 */
public class MediaPlayerVideo extends Activity implements
		OnBufferingUpdateListener, OnCompletionListener, OnPreparedListener,
		OnVideoSizeChangedListener, OnErrorListener, SurfaceHolder.Callback {

	private static final String TAG = "MediaPlayerDemo";
	private int mVideoWidth;
	private int mVideoHeight;
	private MediaPlayer mMediaPlayer;
	private SurfaceView mPreview;
	private SurfaceHolder holder;
	private String path = "";
	private Bundle extras;
	private static final String MEDIA = "media";
	private static final int LOCAL_AUDIO = 1;
	private static final int STREAM_AUDIO = 2;
	private static final int RESOURCES_AUDIO = 3;
	private static final int LOCAL_VIDEO = 4;
	private static final int STREAM_VIDEO = 5;
	protected static final int PROGRESS_CHANGED = 0;
	private boolean mIsVideoSizeKnown = false;
	private boolean mIsVideoReadyToBePlayed = false;

	SeekBar seekbar;

	MediaController mMediaController;

	TextView title, currentTime, totalTime;

	String url;

	ArrayList<String> list;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		if (!LibsChecker.checkVitamioLibs(this))
			return;
		setContentView(R.layout.mediaplayer_2);
		mPreview = (SurfaceView) findViewById(R.id.surface);
		seekbar = (SeekBar) findViewById(R.id.seekbar);

		title = (TextView) findViewById(R.id.title);
		currentTime = (TextView) findViewById(R.id.currentTime);
		totalTime = (TextView) findViewById(R.id.totalTime);

		holder = mPreview.getHolder();
		holder.addCallback(this);
		holder.setFormat(PixelFormat.RGBA_8888);
		extras = getIntent().getExtras();

		url = this.getIntent().getStringExtra("url");
		Logger.log("url:"+url);
		list = new ArrayList<String>();
		list.add(url);
		setDataSource(list);

		seekbar.setOnSeekBarChangeListener(new mOnSeekChangeListener());

		// mMediaController = new MediaController(this);
		// mMediaController.setAnchorView(mPreview.getRootView());

		this.registerForContextMenu(mPreview);
	}

	static String mUris[] = new String[] {};

	public static void setDataSource(final ArrayList<String> list) {

		mUris = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			mUris[i] = list.get(i);
			Logger.log("======================" + mUris[i]
					+ "======================");
		}

	}

	Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case PROGRESS_CHANGED:
				if (null != mMediaPlayer) {
					mCurrentPosition = mMediaPlayer.getCurrentPosition();
				}
				currentTime.setText(StringUtils.generateTime(mCurrentPosition));
				seekbar.setProgress((int) mCurrentPosition);
				sendEmptyMessage(PROGRESS_CHANGED);
				break;
			default:
				break;
			}

		};

	};

	class mOnSeekChangeListener implements OnSeekBarChangeListener {

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
			// seekBar.setProgress(progress);
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			// mMediaPlayer.seekTo(seekBar.getProgress());
		}

	}

	private void playVideo(Integer Media, long currentPostion) {
		doCleanUp();
		try {

			switch (Media) {
			case LOCAL_VIDEO:
				/*
				 * TODO: Set the path variable to a local media file path.
				 */
				path = "";
				if (path == "") {
					// Tell the user to provide a media file URL.
					Toast.makeText(
							MediaPlayerVideo.this,
							"Please edit MediaPlayerDemo_Video Activity, "
									+ "and set the path variable to your media file path."
									+ " Your media file must be stored on sdcard.",
							Toast.LENGTH_LONG).show();
					return;
				}
				break;
			case STREAM_VIDEO:
				/*
				 * TODO: Set path variable to progressive streamable mp4 or 3gpp
				 * format URL. Http protocol should be used. Mediaplayer can
				 * only play "progressive streamable contents" which basically
				 * means: 1. the movie atom has to precede all the media data
				 * atoms. 2. The clip has to be reasonably interleaved.
				 */
				path = "http://220.196.51.28/33/7/26/letv-uts/6637227-AVC-536714-AAC-31586-5899520-433284185-44f93098eff9934526a5e1e135c6a0b2-1377780478452.letv?crypt=51aa7f2e142&b=587&nlh=3072&nlt=5&bf=19&gn=738&p2p=1&video_type=flv&opck=1&check=0&tm=1381639200&key=b6bf7fb15ad1579b7be4163edba7e4cc&proxy=2007487116,2071812435&cips=112.65.228.90&geo=CN-9-123-2&lgn=letv&mmsid=3073389&platid=1&splatid=101&playid=0&tss=no&host=www_letv_com";
				if (path == "") {
					// Tell the user to provide a media file URL.
					Toast.makeText(
							MediaPlayerVideo.this,
							"Please edit MediaPlayerDemo_Video Activity,"
									+ " and set the path variable to your media file URL.",
							Toast.LENGTH_LONG).show();
					return;
				}

				break;

			}
			if (null != mMediaPlayer) {
				mMediaPlayer.release();
				mMediaPlayer = null;
			}

			// Create a new media player and set the listeners
			mMediaPlayer = new MediaPlayer(this);
			// mMediaPlayer.setDataSource(path);

			String cacheName = getCacheDir().toString();
			Logger.log(cacheName);
			// 不能有空
			mMediaPlayer.setDataSegments(mUris, cacheName);

			mMediaPlayer.setDisplay(holder);
			mMediaPlayer.prepare();
			mMediaPlayer.setOnBufferingUpdateListener(this);
			mMediaPlayer.setOnCompletionListener(this);
			mMediaPlayer.setOnPreparedListener(this);
			mMediaPlayer.setOnVideoSizeChangedListener(this);
			mMediaPlayer.getMetadata();
			setVolumeControlStream(AudioManager.STREAM_MUSIC);

			System.out.println(mMediaPlayer.getDuration() + "<><><><><><>");

			seekbar.setMax((int) mMediaPlayer.getDuration());

			totalTime.setText(StringUtils.generateTime(seekbar.getMax()));
			currentTime.setText("00:00");

			mMediaPlayer.seekTo(currentPostion);

		} catch (Exception e) {
			Log.e(TAG, "error: " + e.getMessage(), e);
		}
	}

	public void onBufferingUpdate(MediaPlayer arg0, int percent) {
		Log.d(TAG, "onBufferingUpdate percent:" + percent);

	}

	public void onCompletion(MediaPlayer arg0) {
		Log.d(TAG, "onCompletion called");
	}

	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
		Log.v(TAG, "onVideoSizeChanged called");
		if (width == 0 || height == 0) {
			Log.e(TAG, "invalid video width(" + width + ") or height(" + height
					+ ")");
			return;
		}
		mIsVideoSizeKnown = true;
		mVideoWidth = width;
		mVideoHeight = height;
		if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
			startVideoPlayback();
		}
	}

	public void onPrepared(MediaPlayer mediaplayer) {
		Log.d(TAG, "onPrepared called");
		mIsVideoReadyToBePlayed = true;
		if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
			startVideoPlayback();
		}
	}

	public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k) {
		Log.d(TAG, "surfaceChanged called");

	}

	public void surfaceDestroyed(SurfaceHolder surfaceholder) {
		Log.d(TAG, "surfaceDestroyed called");
	}

	public void surfaceCreated(SurfaceHolder holder) {
		Log.d(TAG, "surfaceCreated called");
		// playVideo(extras.getInt(MEDIA));

		playVideo(STREAM_VIDEO, 0);
	}

	@Override
	protected void onPause() {
		super.onPause();
		releaseMediaPlayer();
		doCleanUp();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		releaseMediaPlayer();
		doCleanUp();
		handler.removeMessages(PROGRESS_CHANGED);
		unregisterForContextMenu(mPreview);
	}

	private void releaseMediaPlayer() {
		if (mMediaPlayer != null) {
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}

	private void doCleanUp() {
		// mVideoWidth = 0;
		// mVideoHeight = 0;
		mIsVideoReadyToBePlayed = false;
		mIsVideoSizeKnown = false;
	}

	private void startVideoPlayback() {
		Log.v(TAG, "startVideoPlayback");
		holder.setFixedSize(mVideoWidth, mVideoHeight);
		mMediaPlayer.start();
		handler.sendEmptyMessage(PROGRESS_CHANGED);
	}

	long mCurrentPosition;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_DPAD_LEFT:
			mCurrentPosition = mMediaPlayer.getCurrentPosition();
			mCurrentPosition -= 15 * 1000;
			mMediaPlayer.seekTo(mCurrentPosition);
			seekbar.setProgress((int) mCurrentPosition);
			handler.sendEmptyMessage(PROGRESS_CHANGED);
			return true;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			mCurrentPosition = mMediaPlayer.getCurrentPosition();
			mCurrentPosition += 15 * 1000;
			mMediaPlayer.seekTo(mCurrentPosition);
			seekbar.setProgress((int) mCurrentPosition);
			handler.sendEmptyMessage(PROGRESS_CHANGED);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public String toTime(long time) {

		time /= 1000;
		long minute = time / 60;

		long second = time % 60;
		return String.format("%02d:%02d", minute, second);
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		releaseMediaPlayer();
		doCleanUp();
		return false;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		Logger.log("onCreateOptionsMenu 执行====================");

		// createDialog();

		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Logger.log("onCreateOptionsMenu 执行====================");
		// createDialog();

		return false;
	}

}
