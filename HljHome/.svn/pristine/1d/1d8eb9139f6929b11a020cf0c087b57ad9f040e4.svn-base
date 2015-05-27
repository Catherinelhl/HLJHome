package com.hlj.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 带边框的Imageview
 * 
 * @author hyc
 * 
 */
public class ImageViewBorder extends ImageView {

	private String namespace = "http://com.hlj";
	private int color;

	public boolean isShowBorder;

	public ImageViewBorder(Context context, AttributeSet attrs) {
		super(context, attrs);
		color = Color.parseColor(attrs.getAttributeValue(namespace,
				"BorderColor"));
		// color = android.R.color.white;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ImageView#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);
		// 画边框 暂时去除小边框
		Rect rec = canvas.getClipBounds();
		rec.bottom--;
		rec.right--;
		Paint paint = new Paint();
		if (isShowBorder) {
			paint.setColor(color);
		} else {
			paint.setColor(Color.TRANSPARENT);
		}

		paint.setStyle(Paint.Style.STROKE);
		canvas.drawRect(rec, paint);
	}

}
