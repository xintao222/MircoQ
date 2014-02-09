package com.unic.micsay.activitys;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.unic.micsay.R;

public class MainActivity extends FragmentActivity {

	private int userId = 0;

	public int getUserId() {
		return userId;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

}
