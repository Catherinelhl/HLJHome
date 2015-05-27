package com.hlj.utils;

import android.graphics.Matrix;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class TVOffAnimation extends Animation {

	private int halfHeight;
	private int halfWidth;

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {

		Matrix localMatrix = t.getMatrix();

		super.applyTransformation(interpolatedTime, t);
		if (interpolatedTime < 0.8D) {
			localMatrix.preScale(1.0F + 0.625F * interpolatedTime,
					0.01F + (1.0F - interpolatedTime / 0.8F), this.halfWidth,
					this.halfHeight);
		} else {
			localMatrix.preScale(7.5F * (1.0F - interpolatedTime), 0.01F,
					this.halfWidth, this.halfHeight);
		}

		// while (true) {
		// return;
		// localMatrix.preScale(7.5F * (1.0F - interpolatedTime),
		// 0.01F,this.halfWidth, this.halfHeight);
		// }
	}

	@Override
	public void initialize(int width, int height, int parentWidth,
			int parentHeight) {
		// TODO Auto-generated method stub
		super.initialize(width, height, parentWidth, parentHeight);

		setDuration(300L);
		setFillAfter(true);
		this.halfWidth = (parentWidth / 2);
		this.halfHeight = (parentHeight / 2);
		setInterpolator(new AccelerateDecelerateInterpolator());
	}

}
