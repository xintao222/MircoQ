package com.unic.micsay.activitys;

import com.unic.micsay.R;
import com.unic.micsay.services.LoginService;
import com.unic.micsay.services.MessageCenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends Activity {

	private TextView userNameView = null;
	private TextView passWordView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		userNameView = (TextView) findViewById(R.id.userName);
		passWordView = (TextView) findViewById(R.id.passWord);
		findViewById(R.id.loginBtn).setOnClickListener(loginBtn);
	}

	private View.OnClickListener loginBtn = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(LoginActivity.this, LoginService.class);
			intent.setAction(LoginService.ACTION_LOGIN);
			Bundle bundle = new Bundle();
			bundle.putString(MessageCenter.KEY_NAME, userNameView.getText()
					.toString());
			bundle.putString(MessageCenter.KEY_PASSWORD, passWordView.getText()
					.toString());
			intent.putExtras(bundle);
			startService(intent);
			finish();
		}
	};

}
