package com.hlj.utils;


import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

public class BitmapTask extends AsyncTask<String, Void, Bitmap> {

	ImageView mImageView;

	PostCallBack postCallBack;

	public BitmapTask(ImageView imageView, PostCallBack postCallBack) {
		mImageView = imageView;
		this.postCallBack = postCallBack;
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		Bitmap bitmap =null;
		//Bitmap bitmap = ImageUtils.getImage(params[0]);

		//HomeActivity.fb.display(mImageView, params[0]);
		return bitmap;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		//mImageView.setImageBitmap(result);
		
		if (postCallBack != null) {
			postCallBack.post(result);
		}
		super.onPostExecute(result);
	}

	public static abstract interface PostCallBack {
		public abstract void post(Bitmap paramBitmap);
	}

}
