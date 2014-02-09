package com.unic.micsay.activitys;

import android.content.Intent;
import android.os.Bundle;

import com.unic.micsay.R;
import com.unic.micsay.services.LoginService;

public class LoadActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.load_activity);
		Intent loginservice = new Intent(this, LoginService.class);
		startService(loginservice);
	}

	@Override
	public void onLoginSuccess() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
