package com.hlj.HomeActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * 我的离线
 * 
 * @author huangyuchao
 * 
 */
public class LixianActivity extends Activity {

	Button lixian_login_logout_btn;
	
	EditText lixian_login_username_autotxt,lixian_login_password_autotxt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.lixian_video_list_layout);

		initView();
	}

	public void initView() {
		
		lixian_login_username_autotxt=(EditText)findViewById(R.id.lixian_login_username_autotxt);
		lixian_login_password_autotxt=(EditText)findViewById(R.id.lixian_login_password_autotxt);
		
		lixian_login_logout_btn = (Button) findViewById(R.id.lixian_login_logout_btn);
		lixian_login_logout_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
	}

}
