package com.unic.micsay.datasource;

import android.content.Context;
import android.database.Cursor;
import android.widget.BaseAdapter;

import com.unic.micsay.contentprovider.Tables;
import com.unic.micsay.models.User;
import com.unic.rainbow.datasource.DataSource;

public class ContactDataSource implements DataSource {

	private Cursor cursor = null;

	public ContactDataSource(Context context) {
		cursor = context.getContentResolver().query(Tables.Contact.CONTENT_URL,
				null, null, null, null);
	}

	@Override
	public int getCount() {
		return cursor.getCount();
	}

	@Override
	public Object getItem(int position) {
		cursor.moveToPosition(position);
		return new User(cursor);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public void onBind(BaseAdapter adapter) {

	}

	public void close() {
		this.cursor.close();
	}

}
