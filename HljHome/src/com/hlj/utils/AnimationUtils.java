package com.hlj.utils;

import com.hlj.HomeActivity.R;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class AnimationUtils {

	private static int num;

	// num1:menu_key
	// num2:menu_key_blue
	public static void setMenuAnimation(final ImageView imageView,
			final int bg1, final int bg2) {

		imageView.setImageResource(bg1);

		AlphaAnimation animation = new AlphaAnimation(0.8F, 1.0F);
		animation.setDuration(1000);
		animation.setRepeatCount(-1);
		animation.setInterpolator(new AccelerateInterpolator());
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
				num += 1;
				if (num == 10) {
					num = 0;
				}
				if (num % 2 == 0) {
					imageView.setImageResource(bg1);
				} else {
					imageView.setImageResource(bg2);
				}

			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub

			}
		});
		imageView.startAnimation(animation);
	}
}
