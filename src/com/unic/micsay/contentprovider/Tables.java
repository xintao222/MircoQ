package com.unic.micsay.contentprovider;

import android.net.Uri;

public class Tables {

	public static class Passport {

		public static String tableName = "passport";

		public static final Uri CONTENT_URL = Uri.parse(IContentProvider.URL
				+ "/" + tableName);

		public class Columns {
			public static final String ID = "id";
			public static final String USERNAME = "USERNAME";
			public static final String PASSWORD = "PASSWORD";
		}

		public static final String SQL_DROP = "drop table " + tableName;

		public static final String SQL_CREATE = "CREATE TABLE " + tableName
				+ "(" + Columns.ID + " integer primary key," + Columns.USERNAME
				+ " varchar(16)," + Columns.PASSWORD + " varchar(16));";

	}

	public static class Contact {

		public static String tableName = "contact";

		public static final Uri CONTENT_URL = Uri.parse(IContentProvider.URL
				+ "/" + tableName);

		public class Columns {
			public static final String ID = "id";
			public static final String EMAIL = "EMAIL";
			public static final String ALIAS = "ALIAS";
			public static final String OWNER = "OWNER";
		}

		public static final String SQL_DROP = "drop table " + tableName;

		public static final String SQL_CREATE = "CREATE TABLE " + tableName
				+ "(" + Columns.ID + " integer primary key," + Columns.EMAIL
				+ " varchar(16)," +Columns.OWNER + " integer,"+ Columns.ALIAS + " varchar(16));";

	}

}
