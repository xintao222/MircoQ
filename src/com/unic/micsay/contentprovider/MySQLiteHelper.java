package com.unic.micsay.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {

	public MySQLiteHelper(Context context, String name, int version) {
		super(context, name, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(Tables.Passport.SQL_CREATE);

		db.execSQL(Tables.Contact.SQL_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int version) {
		db.execSQL("DROP TABLE IF EXISTS " + Tables.Passport.tableName);
		db.execSQL(Tables.Passport.SQL_CREATE);

		db.execSQL("DROP TABLE IF EXISTS " + Tables.Contact.tableName);
		db.execSQL(Tables.Contact.SQL_CREATE);
	}

}
