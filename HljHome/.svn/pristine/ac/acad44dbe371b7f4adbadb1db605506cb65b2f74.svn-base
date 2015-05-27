package com.hlj.tuisongvideo;

import com.hlj.HomeActivity.R;

import io.vov.vitamio.LibsChecker;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (!LibsChecker.checkVitamioLibs(this))
			return;

		Intent it = new Intent(this, PushService.class);
		startService(it);
		
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.activity_main, menu);
		return false;
	}

}
