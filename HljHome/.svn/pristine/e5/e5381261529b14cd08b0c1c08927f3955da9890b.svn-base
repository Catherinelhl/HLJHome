package com.hlj.adapter;

import com.hlj.HomeActivity.R;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: ${shime}
 * Date: 13-10-28
 * Time: 下午5:37
 * To change this template use File | Settings | File Templates.
 */
public class CursorAdapters extends CursorAdapter {
	private LayoutInflater mInflater;
	private Context mContext;
	TextView textView;
	private Cursor cursor;
	public CursorAdapters(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		this.mContext = context;
		this.cursor=c;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
		return mInflater.inflate(R.layout.type_details_submenu,viewGroup,false);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		String name = cursor.getString(cursor.getColumnIndex("name"));
		textView = (TextView)view.findViewById(R.id.sunmenu_text);
		textView.setText(name);
	}

	@Override
	public View newDropDownView(Context context, Cursor cursor, ViewGroup parent) {

		return super.newDropDownView(context, cursor, parent);    //To change body of overridden methods use File | Settings | File Templates.
	}
}
