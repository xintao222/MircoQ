package com.unic.micsay.models;

import com.unic.micsay.contentprovider.Tables;

import android.database.Cursor;

public class User {

	public User(Cursor cursor) {
		alias = cursor.getString(cursor
				.getColumnIndex(Tables.Contact.Columns.ALIAS));
		email = cursor.getString(cursor
				.getColumnIndex(Tables.Contact.Columns.EMAIL));
	}

	public String getAlias() {
		return alias;
	}

	public String getEmail() {
		return email;
	}

	private String alias = null;
	private String email = null;

}
