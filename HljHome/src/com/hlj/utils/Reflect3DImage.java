package com.hlj.utils;

import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;

public class Reflect3DImage {

	public static Bitmap skewImage(Bitmap paramBitmap, int paramInt1,
			int paramInt2, int paramInt3) {

		Bitmap scaleBitmap = Bitmap.createScaledBitmap(paramBitmap, paramInt1,
				paramInt2, true);
		Bitmap reflectImage = createReflectedImage(scaleBitmap, paramInt3);

		Camera camera = new Camera();
		camera.save();
		Matrix matrix = new Matrix();
		camera.rotateY(15.0F);
		camera.getMatrix(matrix);
		camera.restore();
		matrix.preTranslate(-reflectImage.getWidth() >> 1,
				-reflectImage.getHeight() >> 1);
		Bitmap bitmap2 = Bitmap
				.createBitmap(reflectImage, 0, 0, reflectImage.getWidth(),
						reflectImage.getHeight(), matrix, true);
		Bitmap bitmap3 = Bitmap.createBitmap(bitmap2.getWidth(),
				bitmap2.getHeight(), Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmap3);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		canvas.drawBitmap(bitmap2, 0.0F, 0.0F, paint);
		bitmap2.recycle();
		return bitmap3;
	}

	public static Bitmap createReflectedImage(Bitmap paramBitmap, int paramInt) {

		int i = paramBitmap.getWidth();
		int j = paramBitmap.getHeight();

		Matrix localMatrix = new Matrix();
		localMatrix.preScale(1.0F, -1.0F);
		Bitmap bitmap1 = Bitmap.createBitmap(paramBitmap, 0, j - paramInt, i,
				paramInt, localMatrix, false);
		Bitmap bitmap2 = Bitmap.createBitmap(i, j + paramInt,
				Bitmap.Config.ARGB_8888);
		Canvas localCanvas = new Canvas(bitmap2);
		Paint paint1 = new Paint();
		localCanvas.drawBitmap(paramBitmap, 0.0F, 0.0F, paint1);
		localCanvas.drawBitmap(bitmap1, 0.0F, j, paint1);

		Paint paint2 = new Paint();
		paint2.setShader(new LinearGradient(0.0F, paramBitmap.getHeight(),
				0.0F, bitmap2.getHeight(), 0x70ffffff, 0x00ffffff,
				TileMode.MIRROR));
		paint2.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		localCanvas.drawRect(0.0F, j, i, bitmap2.getHeight(), paint2);
		bitmap1.recycle();
		return bitmap2;
	}

}
