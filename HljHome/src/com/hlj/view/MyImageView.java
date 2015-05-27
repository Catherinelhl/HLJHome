package com.hlj.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 自定义ImageView实现边框效果
 * @author huangyuchao
 *
 */
public class MyImageView extends ImageView {

	static MyImageView instance;

	public MyImageView(Context context) {
		super(context);
		instance = this;
	}

	public MyImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		instance = this;
	}

	private int co;
	private int borderwidth;

	// 设置颜色
	public void setColor(int color) {
		co = color;
		instance.invalidate();
	}

	// 设置边框宽度
	public void setBorderWidth(int width) {

		borderwidth = width;
		instance.invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		// 画边框
		Rect rec = canvas.getClipBounds();
		// rec.bottom--;
		// rec.right--;

		Paint paint = new Paint();
		// 设置边框颜色
		paint.setColor(co);
		paint.setStyle(Paint.Style.STROKE);
		// 设置边框宽度
		paint.setStrokeWidth(borderwidth);
		canvas.drawRect(rec, paint);

	}

}
