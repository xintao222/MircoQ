package com.unic.micsay.services;

import org.jivesoftware.smack.XMPPConnection;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;

import com.unic.micsay.actions.LoginAction;
import com.unic.micsay.actions.XMPPChannel;
import com.unic.micsay.contentprovider.Tables;
import com.unic.rainbow.workstation.WorkStation;

public class MessageCenter extends Service {

	public static final String KEY_NAME = "KEY_NAME";

	public static final String KEY_PASSWORD = "KEY_PASSWORD";

	public static final String ACTION_START = "ACTION_START";

	public static final String ACTION_SHUTDOWN = "ACTION_SHUTDOWN";

	public static final String ACTION_LOGIN_SUCCESS = "ACTION_LOGIN_SUCCESS";

	public static final String ACTION_LOGIN_FAILED = "ACTION_LOGIN_FAILED";

	private WorkStation workstation = null;

	private Uri account = null;

	private XMPPChannel channel = null;

	@Override
	public void onCreate() {
		super.onCreate();
		workstation = new WorkStation(this);
		workstation.prepare();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (ACTION_START.equalsIgnoreCase(intent.getAction())) {
			Bundle bundle = intent.getExtras();
			final String userName = bundle.getString(KEY_NAME);
			final String passWord = bundle.getString(KEY_PASSWORD);
			LoginAction task = new LoginAction(userName, passWord, this);
			workstation.dispatch(task);
		}

		if (ACTION_SHUTDOWN.equalsIgnoreCase(intent.getAction())) {
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return channel;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		workstation.release();
	}

	private void insertAccount(String userName, String passWord) {
		ContentValues values = new ContentValues();
		values.put(Tables.Passport.Columns.USERNAME, userName);
		values.put(Tables.Passport.Columns.PASSWORD, passWord);
		this.account = getContentResolver().insert(Tables.Passport.CONTENT_URL,
				values);
	}

	public void onLoginSuccess(XMPPConnection connection, String userName,
			String passWord) {
		Cursor cursor = this.getContentResolver().query(
				Tables.Passport.CONTENT_URL,
				null,
				Tables.Passport.Columns.USERNAME + "=? and "
						+ Tables.Passport.Columns.PASSWORD + "=?",
				new String[] { userName, passWord }, null);
		if (null == cursor) {
			insertAccount(userName, passWord);
		} else {
			if (!cursor.moveToFirst() || cursor.getCount() < 1) {
				insertAccount(userName, passWord);
			} else {
				final int idIndex = cursor
						.getColumnIndex(Tables.Passport.Columns.ID);
				String id = cursor.getString(idIndex);
				this.account = Uri
						.parse(Tables.Passport.CONTENT_URL + "/" + id);
			}
			cursor.close();
		}
		channel = new XMPPChannel(this, connection, account);
		workstation.dispatch(channel);
		Intent intent = new Intent(ACTION_LOGIN_SUCCESS);
		sendBroadcast(intent);
	}

	public void onLoginFailed() {
		Intent intent = new Intent(ACTION_LOGIN_FAILED);
		sendBroadcast(intent);
	}

}
