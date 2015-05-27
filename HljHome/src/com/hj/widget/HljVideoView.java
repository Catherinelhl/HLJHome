package com.hj.widget;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.VideoView;

/**
 * 自定义VideoView
 * 
 * @author huangyuchao
 * 
 */
public class HljVideoView extends VideoView {

	public static int mVideoWidth;
	public static int mVideoHeight;
	
	private MediaPlayer.OnPreparedListener mOnPreparedListener;

	public HljVideoView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = getDefaultSize(mVideoWidth, widthMeasureSpec);
		int height = getDefaultSize(mVideoHeight, heightMeasureSpec);

		System.out.println("width---view--" + width);
		System.out.println("height---view--" + height);

		setMeasuredDimension(width, height);

		// super.onMeasure(width, height);
	}

	public void setVideofullScale(int width, int height) {
		FrameLayout.LayoutParams lp = (android.widget.FrameLayout.LayoutParams) getLayoutParams();
		lp.height = height;
		lp.width = width;
		lp.setMargins(0, 0, 0, 0);
		setLayoutParams(lp);
		getHolder().setFixedSize(width, height);
		// requestLayout();
		// invalidate();
	}

	public void setVideoDefaultScale(int width, int height) {
		FrameLayout.LayoutParams lp = (android.widget.FrameLayout.LayoutParams) getLayoutParams();
		lp.height = height;
		lp.width = width;
		lp.setMargins(258, 85, 0, 0);
		setLayoutParams(lp);
		getHolder().setFixedSize(width, height);
		// requestLayout();
		// invalidate();
	}
	
	public void setOnPreparedListener(MediaPlayer.OnPreparedListener l) {
		mOnPreparedListener = l;
	}


}
