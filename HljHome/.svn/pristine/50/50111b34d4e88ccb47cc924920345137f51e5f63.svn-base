package com.hlj.tuisongvideo;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnCompletionListener;
import io.vov.vitamio.MediaPlayer.OnErrorListener;
import io.vov.vitamio.MediaPlayer.OnPreparedListener;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import java.util.ArrayList;

import com.hlj.HomeActivity.R;
import com.hlj.utils.Logger;

import android.app.Activity;
import android.os.Bundle;

public class VideoActivity extends Activity {

	// HljVideoView mVideoView;

	VideoView mVideoView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (!LibsChecker.checkVitamioLibs(this))
			return;
		setContentView(R.layout.video_main);

		mVideoView = (VideoView) findViewById(R.id.myVideoView);

		String urls = this.getIntent().getStringExtra("urls");

		VideoInfo info = new VideoInfo();
		info.parseJson(urls);

		if (null != info.superVideo.urls && info.superVideo.urls.size() > 0) {
			setDataSource(info.superVideo.urls);
		} else if (null != info.highVideo.urls
				&& info.highVideo.urls.size() > 0) {
			setDataSource(info.highVideo.urls);
		} else if (null != info.standVideo.urls
				&& info.standVideo.urls.size() > 0) {
			setDataSource(info.standVideo.urls);
		}
	}

	int position = 0;
	String url = "";

	String[] mUris;

	public void setDataSource(final ArrayList<String> list) {
		// url = list.get(position);
		mVideoView.requestFocus();

		mUris = new String[list.size()];

		for (int i = 0; i < list.size(); i++) {
			mUris[i] = list.get(i);
		}

		Logger.log("======================" + url + "======================");

		//mVideoView.setVideoSegments(mUris);

		mVideoView.setVideoPath(url);

		mVideoView.setMediaController(new MediaController(this));
		// mVideoView.start();

		mVideoView.setOnPreparedListener(new OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer mp) {
				mVideoView.start();
			}
		});

		mVideoView.start();
		mVideoView.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				position++;
				if (position < list.size()) {
					url = list.get(position);
					mVideoView.setVideoPath(url);
				}

			}
		});
		mVideoView.setOnErrorListener(new OnErrorListener() {

			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				// TODO Auto-generated method stub
				return false;
			}
		});
	}

}
