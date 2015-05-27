package com.hj.widget;

import java.io.IOException;

import com.hj.widget.MediaController.MediaPlayerControl;
import com.hlj.HomeActivity.R;
import com.hlj.HomeActivity.TVVitaBackActivity;
import com.hlj.utils.Logger;
import com.live.video.constans.ChapterInfo;
import com.live.video.constans.Constants;

import android.content.Context;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

/**
 * 针对回看的用这个VideoView
 * 
 * @author huangyuchao
 * 
 */
public class VitaVideoView extends SurfaceView implements MediaPlayerControl {

	private Context mContext;

	private String mUri = "";

	private long mDuration;

	// All the stuff we need for playing and showing a video
	private SurfaceHolder mSurfaceHolder = null;
	private MediaPlayer mMediaPlayer = null;
	public boolean mIsPrepared;
	public static int mVideoWidth;
	public static int mVideoHeight;
	private int mSurfaceWidth;
	private int mSurfaceHeight;
	public static MediaController mMediaController;
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

	public VitaVideoView(Context context) {
		super(context);
		mContext = context;
		initVideoView();
	}

	public VitaVideoView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initVideoView();
	}

	private WindowManager wm;
	private ProgressBar loadingBar;

	private void initVideoView() {
		mVideoWidth = 0;
		mVideoHeight = 0;
		getHolder().addCallback(mSHCallback);

		setFocusable(true);
		setFocusableInTouchMode(true);
		requestFocus();

		loadingBar = (ProgressBar) LayoutInflater.from(mContext).inflate(
				R.layout.player_buf_pro, null);
		wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

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
		lp.setMargins(370, 100, 0, 0);
		setLayoutParams(lp);
		getHolder().setFixedSize(width, height);
		// requestLayout();
		// invalidate();
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
					// mMediaController.show();
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

	private void openVideo() {
		if (mUri == null || mSurfaceHolder == null) {
			return;
		}

		if (mMediaPlayer != null) {
			mMediaPlayer.stop();
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
			mMediaPlayer.setDataSource(mUri);
			mMediaPlayer.setDisplay(mSurfaceHolder);
			// mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
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
				if (loadingBar.getParent() != null) {
					wm.addView(loadingBar, params);
				}
				break;
			case MediaPlayer.MEDIA_INFO_BUFFERING_END:
				if (loadingBar.getParent() != null) {
					wm.removeView(loadingBar);
				}
				break;
			case MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
				if (loadingBar.getParent() != null) {
					wm.removeView(loadingBar);
				}
				break;
			}
			return false;
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
							// mMediaController.show();
						}
					} else if (!isPlaying()
							&& (mSeekWhenPrepared != 0 || getCurrentPosition() > 0)) {
						if (mMediaController != null) {
							// Show the media controls when we're paused into a
							// video and make 'em stick.
							// mMediaController.show(0);
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

	private MediaPlayer.OnBufferingUpdateListener mBufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {
		public void onBufferingUpdate(MediaPlayer mp, int percent) {
			mCurrentBufferPercentage = percent;
		}
	};

	public void setOnPreparedListener(MediaPlayer.OnPreparedListener l) {
		mOnPreparedListener = l;
	}

	public void setOnCompletionListener(OnCompletionListener l) {
		mOnCompletionListener = l;
	}

	public void setOnErrorListener(OnErrorListener l) {
		mOnErrorListener = l;
	}

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
		// if (!mMediaController.isShowing()) {
		// mMediaController.show();
		// }
		// else {
		// mMediaController.show();
		// }
	}

	@Override
	public void start() {
		if (mMediaPlayer != null && mIsPrepared) {
			mMediaPlayer.start();
			mStartWhenPrepared = false;
		} else {
			mStartWhenPrepared = true;
		}
	}

	@Override
	public void pause() {
		if (mMediaPlayer != null && mIsPrepared) {
			if (mMediaPlayer.isPlaying()) {
				mMediaPlayer.pause();
			}
		}
		mStartWhenPrepared = false;
	}

	@Override
	public long getDuration() {
		if (mMediaPlayer != null && mIsPrepared) {
			if (mDuration > 0) {
				return mDuration;
			}
			// mDuration = mMediaPlayer.getDuration();
			// mDuration = Constants.totalLength * 1000;
			mDuration = TVVitaBackActivity.chapterList.size()
					* mMediaPlayer.getDuration();

			return mDuration;
		}
		mDuration = -1;
		return mDuration;
	}

	@Override
	public long getCurrentPosition() {
		if (mMediaPlayer != null && mIsPrepared) {

			ChapterInfo.startPosition = 0;
			ChapterInfo.endPosition = TVVitaBackActivity.playPosition
					* Constants.duration * 1000;

			// Logger.log("mMediaPlayer position:"
			// + mMediaPlayer.getCurrentPosition());
			//
			// Logger.log("allPosotion:" + (mMediaPlayer.getCurrentPosition()
			// + TVVitaBackActivity.playPosition * Constants.duration
			// * 1000));

			return mMediaPlayer.getCurrentPosition()
					+ TVVitaBackActivity.playPosition * Constants.duration
					* 1000;
		}

		return 0;
	}

	@Override
	public long seekTo(long pos) {
		if (mMediaPlayer != null && mIsPrepared) {
			mMediaPlayer.seekTo((int) pos);
		} else {
			mSeekWhenPrepared = (int) pos;
		}

		return pos;
	}

	@Override
	public boolean isPlaying() {
		if (mMediaPlayer != null && mIsPrepared) {
			return mMediaPlayer.isPlaying();
		}
		return false;
	}

	@Override
	public int getBufferPercentage() {
		if (mMediaPlayer != null) {
			return mCurrentBufferPercentage;
		}
		return 0;
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
	public long getPosition() {
		// TODO Auto-generated method stub
		return mMediaPlayer.getCurrentPosition();
	}

	public void stopPlayback() {
		if (mMediaPlayer != null) {
			mMediaPlayer.stop();
			mMediaPlayer.release();
			mMediaPlayer = null;
		}

		if (loadingBar.getParent() != null) {
			wm.removeView(loadingBar);
		}
	}

}
