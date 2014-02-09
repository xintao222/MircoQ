package com.unic.micsay.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.unic.micsay.activitys.LoginActivity;

public class LoginService extends IntentService {

	public LoginService() {
		super("LoginService");
	}

	public static final String ACTION_LOGIN = "ACTION_LOGIN";

	private String userName = null;

	private String passWord = null;

	@Override
	protected void onHandleIntent(Intent intent) {
		if (ACTION_LOGIN.equalsIgnoreCase(intent.getAction())) {
			Bundle extBundle = intent.getExtras();
			userName = extBundle.getString(MessageCenter.KEY_NAME);
			passWord = extBundle.getString(MessageCenter.KEY_PASSWORD);
		}

		if (null == userName || null == passWord) {
			Intent login = new Intent(this, LoginActivity.class);
			login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(login);
			return;
		}

		Intent message = new Intent(this, MessageCenter.class);
		message.setAction(MessageCenter.ACTION_START);
		Bundle bundle = new Bundle();
		bundle.putString(MessageCenter.KEY_NAME, userName);
		bundle.putString(MessageCenter.KEY_PASSWORD, passWord);
		message.putExtras(bundle);
		startService(message);
	}

}
