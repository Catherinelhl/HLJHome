package com.hlj.adapter;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class AllPagesAdapter extends PagerAdapter {

	private ArrayList<View> views;

	public AllPagesAdapter(ArrayList<View> paramArrayList) {
		this.views = paramArrayList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View localView = (View) this.views.get(position);
		container.addView(localView);
		return localView;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View)object);
	}

}
