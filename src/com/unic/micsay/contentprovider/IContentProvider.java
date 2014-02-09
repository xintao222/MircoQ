package com.unic.micsay.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class IContentProvider extends ContentProvider {

	public static final String PROVIDER_NAME = "com.unic.micsay";
	public static final String URL = "content://" + PROVIDER_NAME;
	public static final Uri CONTENT_URI = Uri.parse(URL);

	private final static int PASSPORT_CODE = 1;

	private final static int PASSPORT_ID_CODE = 2;

	private final static int CONTACT_CODE = 3;

	private final static int CONTACT_ID_CODE = 4;

	static final UriMatcher uriMatcher;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(PROVIDER_NAME, Tables.Passport.tableName,
				PASSPORT_CODE);
		uriMatcher.addURI(PROVIDER_NAME, Tables.Passport.tableName + "/#",
				PASSPORT_ID_CODE);
		
		uriMatcher.addURI(PROVIDER_NAME, Tables.Contact.tableName,
				CONTACT_CODE);
		uriMatcher.addURI(PROVIDER_NAME, Tables.Contact.tableName + "/#",
				CONTACT_ID_CODE);
	}

	private MySQLiteHelper mysqliteHelper = null;

	@Override
	public boolean onCreate() {
		mysqliteHelper = new MySQLiteHelper(this.getContext(), getContext()
				.getPackageName(), 3);
		return true;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = this.mysqliteHelper.getWritableDatabase();
		final int what = uriMatcher.match(uri);
		String table = null;
		switch (what) {
		case PASSPORT_CODE: {
			table = Tables.Passport.tableName;
		}
			break;
		case CONTACT_CODE: {
			table = Tables.Contact.tableName;
		}
			break;
		default:
			break;
		}
		long rows = db.insert(table, null, values);
		Uri nuri = ContentUris.withAppendedId(uri, rows);
		getContext().getContentResolver().notifyChange(nuri, null);
		return nuri;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		final int what = uriMatcher.match(uri);
		switch (what) {
		case PASSPORT_CODE: {
			qb.setTables(Tables.Passport.tableName);
		}
			break;
		case CONTACT_CODE: {
			qb.setTables(Tables.Contact.tableName);
		}
			break;
		default:
			break;
		}
		SQLiteDatabase db = mysqliteHelper.getWritableDatabase();
		Cursor c = qb.query(db, projection, selection, selectionArgs, null,
				null, sortOrder);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		return 0;
	}

}
