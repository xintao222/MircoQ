package com.unic.micsay.activitys;

import com.unic.micsay.services.MessageCenter;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;

public class BaseActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		IntentFilter intentFilter = new IntentFilter(
				MessageCenter.ACTION_LOGIN_SUCCESS);
		registerReceiver(loginReceiver, intentFilter);

	}

	public void onLoginSuccess() {

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(loginReceiver);
	}

	private BroadcastReceiver loginReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (MessageCenter.ACTION_LOGIN_SUCCESS.equalsIgnoreCase(intent
					.getAction())) {
				onLoginSuccess();
			}
		}
	};

}
