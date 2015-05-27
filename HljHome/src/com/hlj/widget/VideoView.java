package com.hlj.widget;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.hlj.utils.CommonTools;
import com.hlj.utils.Logger;
import com.hlj.view.CustomProgressDialog;
import com.hlj.widget.MediaController.MediaPlayerControl;
import com.live.video.constans.Constants;
import com.live.video.constans.HomeConstants;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

/**
 * 针对普通播放的VideoView
 * 
 * @author huangyuchao
 * 
 */
public class VideoView extends SurfaceView implements MediaPlayerControl {

	private Context mContext;

	private String mUri = "";

	private int mDuration;

	// All the stuff we need for playing and showing a video
	private SurfaceHolder mSurfaceHolder = null;
	private MediaPlayer mMediaPlayer = null;
	private boolean mIsPrepared;
	public static int mVideoWidth;
	public static int mVideoHeight;
	private int mSurfaceWidth;
	private int mSurfaceHeight;
	private MediaController mMediaController;
	public OnCompletionListener mOnCompletionListener;
	private MediaPlayer.OnPreparedListener mOnPreparedListener;
	private int mCurrentBufferPercentage;
	private OnErrorListener mOnErrorListener;
	private boolean mStartWhenPrepared;
	private int mSeekWhenPrepared;

	private MySizeChangeLinstener mMyChangeLinstener;

	public int getVideoWidth() {
		return mVideoWidth;
	}

	public int getVideoHeight() {
		return mVideoHeight;
	}

	public void setVideoScale(int width, int height) {
		LayoutParams lp = getLayoutParams();
		lp.height = height;
		lp.width = width;
		setLayoutParams(lp);
	}

	public interface MySizeChangeLinstener {
		public void doMyThings();
	}

	public void setMySizeChangeLinstener(MySizeChangeLinstener l) {
		mMyChangeLinstener = l;
	}

	public VideoView(Context context) {
		super(context);
		mContext = context;
		initVideoView();
	}

	public VideoView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		mContext = context;
		initVideoView();
	}

	public VideoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		initVideoView();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// Log.i("@@@@", "onMeasure");
		int width = getDefaultSize(mVideoWidth, widthMeasureSpec);
		int height = getDefaultSize(mVideoHeight, heightMeasureSpec);
		setMeasuredDimension(width, height);
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

	public int resolveAdjustedSize(int desiredSize, int measureSpec) {
		int result = desiredSize;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		switch (specMode) {
		case MeasureSpec.UNSPECIFIED:

			result = desiredSize;
			break;

		case MeasureSpec.AT_MOST:

			result = Math.min(desiredSize, specSize);
			break;

		case MeasureSpec.EXACTLY:
			// No choice. Do what we are told.
			result = specSize;
			break;
		}
		return result;
	}

	private CustomProgressDialog dialog;

	private WindowManager wm;

	private void initVideoView() {
		mVideoWidth = 0;
		mVideoHeight = 0;
		getHolder().addCallback(mSHCallback);
		getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		setFocusable(true);
		setFocusableInTouchMode(true);
		requestFocus();

		//wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		dialog = new CustomProgressDialog(mContext);
	}

	public void setVideoPath(String path) {
		setVideoURI(path);
	}

	public void setVideoURI(String uri) {
		mUri = uri;
		mStartWhenPrepared = false;
		mSeekWhenPrepared = 0;
		openVideo();
		requestLayout();
		invalidate();
	}

	public void stopPlayback() {
		if (mMediaPlayer != null) {
			mMediaPlayer.stop();
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}

	private void openVideo() {
		if (mUri == null || mSurfaceHolder == null) {

			return;
		}

		if (mMediaPlayer != null) {
			mMediaPlayer.reset();
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
		try {
			mMediaPlayer = new MediaPlayer();
			mMediaPlayer.setOnPreparedListener(mPreparedListener);
			mMediaPlayer.setOnVideoSizeChangedListener(mSizeChangedListener);
			mMediaPlayer.setOnInfoListener(mInfoListener);
			mIsPrepared = false;
			Logger.log("reset duration to -1 in openVideo");
			mDuration = -1;
			mMediaPlayer.setOnCompletionListener(mCompletionListener);
			mMediaPlayer.setOnErrorListener(mErrorListener);
			mMediaPlayer.setOnBufferingUpdateListener(mBufferingUpdateListener);
			mCurrentBufferPercentage = 0;
			// mMediaPlayer.setDataSource(mUri);

			if (Constants.isDefaultPlay == 4 || mUri.contains("tongzhuo100")) {
				Map<String, String> headers = new HashMap<String, String>();
				headers.put("Cookie", CommonTools.getCookie(mContext));
				// headers.put("fastweb_title", "fastwebtongzhuo100");
				mMediaPlayer.setDataSource(mUri, headers);
			} else {
				mMediaPlayer.setDataSource(mUri);
			}

			mMediaPlayer.setDisplay(mSurfaceHolder);
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mMediaPlayer.setScreenOnWhilePlaying(true);
			mMediaPlayer.prepareAsync();
			attachMediaController();
		} catch (IOException ex) {
			Logger.log("Unable to open content: " + mUri + ex.toString());
			return;
		} catch (IllegalArgumentException ex) {
			Logger.log("Unable to open content: " + mUri + ex.toString());
			return;
		}
	}

	MediaPlayer.OnInfoListener mInfoListener = new MediaPlayer.OnInfoListener() {

		@Override
		public boolean onInfo(MediaPlayer mp, int what, int extra) {
			WindowManager.LayoutParams params = new WindowManager.LayoutParams();
			params.gravity = Gravity.CENTER;
			params.width = LayoutParams.WRAP_CONTENT;
			params.height = LayoutParams.WRAP_CONTENT;
			Logger.log("what:" + what + ",extra:" + extra);
			switch (what) {
			case MediaPlayer.MEDIA_INFO_BUFFERING_START:
				if (dialog != null && !dialog.isShowing()) {
					dialog.show();
				}
				break;
			case MediaPlayer.MEDIA_INFO_BUFFERING_END:
				if (dialog != null && dialog.isShowing()) {
					dialog.cancel();
				}
				break;
			case MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
				if (dialog != null && dialog.isShowing()) {
					dialog.cancel();
				}
				break;
			}
			return true;
		}

	};

	public void setMediaController(MediaController controller) {
		if (mMediaController != null) {
			mMediaController.hide();
		}
		mMediaController = controller;
		attachMediaController();
	}

	private void attachMediaController() {
		if (mMediaPlayer != null && mMediaController != null) {
			mMediaController.setMediaPlayer(this);
			View anchorView = this.getParent() instanceof View ? (View) this
					.getParent() : this;
			mMediaController.setAnchorView(anchorView);
			mMediaController.setEnabled(mIsPrepared);
		}
	}

	MediaPlayer.OnVideoSizeChangedListener mSizeChangedListener = new MediaPlayer.OnVideoSizeChangedListener() {
		public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
			mVideoWidth = mp.getVideoWidth();
			mVideoHeight = mp.getVideoHeight();

			if (mMyChangeLinstener != null) {
				mMyChangeLinstener.doMyThings();
			}

			if (mVideoWidth != 0 && mVideoHeight != 0) {
				getHolder().setFixedSize(mVideoWidth, mVideoHeight);
			}
		}
	};

	MediaPlayer.OnPreparedListener mPreparedListener = new MediaPlayer.OnPreparedListener() {
		public void onPrepared(MediaPlayer mp) {
			// briefly show the mediacontroller
			mIsPrepared = true;
			if (mOnPreparedListener != null) {
				mOnPreparedListener.onPrepared(mMediaPlayer);
			}
			if (mMediaController != null) {
				mMediaController.setEnabled(true);
			}
			mVideoWidth = mp.getVideoWidth();
			mVideoHeight = mp.getVideoHeight();
			if (mVideoWidth != 0 && mVideoHeight != 0) {
				// Log.i("@@@@", "video size: " + mVideoWidth +"/"+
				// mVideoHeight);
				getHolder().setFixedSize(mVideoWidth, mVideoHeight);
				if (mSurfaceWidth == mVideoWidth
						&& mSurfaceHeight == mVideoHeight) {
					// We didn't actually change the size (it was already at the
					// size
					// we need), so we won't get a "surface changed" callback,
					// so
					// start the video here instead of in the callback.
					if (mSeekWhenPrepared != 0) {
						mMediaPlayer.seekTo(mSeekWhenPrepared);
						mSeekWhenPrepared = 0;
					}
					if (mStartWhenPrepared) {
						mMediaPlayer.start();
						mStartWhenPrepared = false;
						if (mMediaController != null) {
							mMediaController.show();
						}
					} else if (!isPlaying()
							&& (mSeekWhenPrepared != 0 || getCurrentPosition() > 0)) {
						if (mMediaController != null) {
							// Show the media controls when we're paused into a
							// video and make 'em stick.
							mMediaController.show(0);
						}
					}
				}
			} else {
				// We don't know the video size yet, but should start anyway.
				// The video size might be reported to us later.
				if (mSeekWhenPrepared != 0) {
					mMediaPlayer.seekTo(mSeekWhenPrepared);
					mSeekWhenPrepared = 0;
				}
				if (mStartWhenPrepared) {
					mMediaPlayer.start();
					mStartWhenPrepared = false;
				}
			}
		}
	};

	private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
		public void onCompletion(MediaPlayer mp) {
			if (mMediaController != null) {
				mMediaController.hide();
			}
			if (mOnCompletionListener != null) {
				mOnCompletionListener.onCompletion(mMediaPlayer);
			}
		}
	};

	private MediaPlayer.OnErrorListener mErrorListener = new MediaPlayer.OnErrorListener() {
		public boolean onError(MediaPlayer mp, int framework_err, int impl_err) {
			Logger.log("Error: " + framework_err + "," + impl_err);
			if (mMediaController != null) {
				mMediaController.hide();
			}

			/* If an error handler has been supplied, use it and finish. */
			if (mOnErrorListener != null) {
				if (mOnErrorListener.onError(mMediaPlayer, framework_err,
						impl_err)) {
					return true;
				}
			}
			if (getWindowToken() != null) {
				Resources r = mContext.getResources();
				int messageId;
			}
			return true;
		}
	};

	private MediaPlayer.OnBufferingUpdateListener mBufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {
		public void onBufferingUpdate(MediaPlayer mp, int percent) {
			mCurrentBufferPercentage = percent;
		}
	};

	/**
	 * Register a callback to be invoked when the media file is loaded and ready
	 * to go.
	 * 
	 * @param l
	 *            The callback that will be run
	 */
	public void setOnPreparedListener(MediaPlayer.OnPreparedListener l) {
		mOnPreparedListener = l;
	}

	/**
	 * Register a callback to be invoked when the end of a media file has been
	 * reached during playback.
	 * 
	 * @param l
	 *            The callback that will be run
	 */
	public void setOnCompletionListener(OnCompletionListener l) {
		mOnCompletionListener = l;
	}

	/**
	 * Register a callback to be invoked when an error occurs during playback or
	 * setup. If no listener is specified, or if the listener returned false,
	 * VideoView will inform the user of any errors.
	 * 
	 * @param l
	 *            The callback that will be run
	 */
	public void setOnErrorListener(OnErrorListener l) {
		mOnErrorListener = l;
	}

	SurfaceHolder.Callback mSHCallback = new SurfaceHolder.Callback() {
		public void surfaceChanged(SurfaceHolder holder, int format, int w,
				int h) {
			mSurfaceWidth = w;
			mSurfaceHeight = h;
			if (mMediaPlayer != null && mIsPrepared && mVideoWidth == w
					&& mVideoHeight == h) {
				if (mSeekWhenPrepared != 0) {
					mMediaPlayer.seekTo(mSeekWhenPrepared);
					mSeekWhenPrepared = 0;
				}
				mMediaPlayer.start();
				if (mMediaController != null) {
					mMediaController.show();
				}
			}
		}

		public void surfaceCreated(SurfaceHolder holder) {
			mSurfaceHolder = holder;
			openVideo();
		}

		public void surfaceDestroyed(SurfaceHolder holder) {
			// after we return from this we can't use the surface any more
			mSurfaceHolder = null;
			if (mMediaController != null)
				mMediaController.hide();
			if (mMediaPlayer != null) {
				mMediaPlayer.reset();
				mMediaPlayer.release();
				mMediaPlayer = null;
			}
		}
	};

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (mIsPrepared && mMediaPlayer != null && mMediaController != null) {
			toggleMediaControlsVisiblity();
		}
		return false;
	}

	@Override
	public boolean onTrackballEvent(MotionEvent ev) {
		if (mIsPrepared && mMediaPlayer != null && mMediaController != null) {
			toggleMediaControlsVisiblity();
		}
		return false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (mIsPrepared && keyCode != KeyEvent.KEYCODE_BACK
				&& keyCode != KeyEvent.KEYCODE_VOLUME_UP
				&& keyCode != KeyEvent.KEYCODE_VOLUME_DOWN
				&& keyCode != KeyEvent.KEYCODE_MENU
				&& keyCode != KeyEvent.KEYCODE_CALL
				&& keyCode != KeyEvent.KEYCODE_ENDCALL && mMediaPlayer != null
				&& mMediaController != null) {
			if (keyCode == KeyEvent.KEYCODE_HEADSETHOOK
					|| keyCode == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE) {
				if (mMediaPlayer.isPlaying()) {
					pause();
					mMediaController.show();
				} else {
					start();
					mMediaController.hide();
				}
				return true;
			} else if (keyCode == KeyEvent.KEYCODE_MEDIA_STOP
					&& mMediaPlayer.isPlaying()) {
				pause();
				mMediaController.show();
			} else {
				toggleMediaControlsVisiblity();
			}
		}

		return super.onKeyDown(keyCode, event);
	}

	private void toggleMediaControlsVisiblity() {
		// if (mMediaController.isShowing()) {
		// mMediaController.hide();
		// } else {
		mMediaController.show();
		// }
	}

	public void start() {
		if (mMediaPlayer != null && mIsPrepared) {
			mMediaPlayer.start();
			mStartWhenPrepared = false;
		} else {
			mStartWhenPrepared = true;
		}
	}

	public void pause() {
		if (mMediaPlayer != null && mIsPrepared) {
			if (mMediaPlayer.isPlaying()) {
				mMediaPlayer.pause();
			}
		}
		mStartWhenPrepared = false;
	}

	public long getDuration() {
		if (mMediaPlayer != null && mIsPrepared) {
			if (mDuration > 0) {
				return mDuration;
			}
			mDuration = mMediaPlayer.getDuration();
			return mDuration;
		}
		mDuration = -1;
		return mDuration;
	}

	public long getCurrentPosition() {
		if (mMediaPlayer != null && mIsPrepared) {
			return mMediaPlayer.getCurrentPosition();
		}
		return 0;
	}

	public void seekTo(int msec) {
		if (mMediaPlayer != null && mIsPrepared) {
			mMediaPlayer.seekTo(msec);
		} else {
			mSeekWhenPrepared = msec;
		}
	}

	public boolean isPlaying() {
		if (mMediaPlayer != null && mIsPrepared) {
			return mMediaPlayer.isPlaying();
		}
		return false;
	}

	public int getBufferPercentage() {
		if (mMediaPlayer != null) {
			return mCurrentBufferPercentage;
		}
		return 0;
	}

	public final static int A_DEFALT = 0; // 原始比例
	public final static int A_4X3 = 1;
	public final static int A_16X9 = 2;
	public final static int A_RAW = 4; // 原始大小

	/**
	 * 全屏状态才可以使用选择比例
	 * 
	 * @param flg
	 */
	public void selectScales(int flg) {
		if (getWindowToken() != null) {
			Rect rect = new Rect();
			getWindowVisibleDisplayFrame(rect);

			Logger.log("Rect = " + rect.top + ":" + rect.bottom + ":"
					+ rect.left + ":" + rect.right);

			double height = rect.bottom - rect.top;
			double width = rect.right - rect.left;
			Logger.log("diplay = " + width + ":" + height);

			Logger.log("diplay video = " + mVideoWidth + ":" + mVideoHeight);

			if (height <= 0.0 || width <= 0.0 || mVideoHeight <= 0.0
					|| mVideoWidth <= 0.0) {
				return;
			}
			ViewGroup.LayoutParams param = getLayoutParams();

			switch (flg) {
			case A_DEFALT:
				if (width / height >= mVideoWidth / mVideoHeight) { //
					// 屏幕 宽了以屏幕高为基础
					param.height = (int) height;
					param.width = (int) (mVideoWidth * height / mVideoHeight);
				} else { // 屏幕 高了 以宽为基础
					param.width = (int) width;
					param.height = (int) (mVideoHeight * width / mVideoWidth);
				}
				Logger.log("A_DEFALT === " + param.width + ":" + param.height);
				setLayoutParams(param);
				break;

			case A_4X3:
				if (width / height >= 4.0 / 3.0) {
					// 屏幕 宽了 以屏幕高为基础
					param.height = (int) height;
					param.width = (int) (4 * height / 3);
				} else { // 屏幕 高了 以宽为基础
					param.width = (int) width;
					param.height = (int) (3 * width / 4);
				}
				Logger.log("A_4X3 === " + param.width + ":" + param.height);
				setLayoutParams(param);
				break;
			case A_16X9:
				if (width / height >= 16.0 / 9.0) {
					// 屏幕 宽了 以屏幕高为基础
					param.height = (int) height;
					param.width = (int) (16 * height / 9);
				} else { // 屏幕 高了 以宽为基础
					param.width = (int) width;
					param.height = (int) (9 * width / 16);
				}
				Logger.log("A_16X9 === " + param.width + ":" + param.height);
				setLayoutParams(param);
				break;
			}

		}

	}

	@Override
	public boolean canPause() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canSeekBackward() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canSeekForward() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void seekTo(long pos) {
		if (mMediaPlayer != null && mIsPrepared) {
			mMediaPlayer.seekTo((int) pos);
		} else {
			mSeekWhenPrepared = (int) pos;
		}
	}

}
