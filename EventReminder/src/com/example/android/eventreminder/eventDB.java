package com.example.android.eventreminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class eventDB {
	private static final String DATABASE_NAME = "eventData"; 
	private static final String DATABASE_TABLE = "events";
	private static final int DATABASE_VERSION = 1;
	public static final String KEY_TITLE = "title";
	public static final String KEY_NOTE = "notes";
	public static final String KEY_ROWID = "id";
	
	private SQLiteDatabase mDB;
	private DatabaseHelper mDbHelper; //SQLiteOpen-	Helper class
	
	private static final String DATABASE_CREATE = 
		"create table " + DATABASE_TABLE + " ("
		+ KEY_ROWID + " integer primary key autoincrement, "
		+ KEY_TITLE + " text not null, "
		+ KEY_NOTE + " text not null, );";
	
	
	private final Context mCtx; 
	
	public eventDB(Context ctx) { 
	this.mCtx = ctx;
	}
	
	public eventDB open() throws android.database.SQLException {
		mDbHelper = new DatabaseHelper(mCtx);
		mDB = mDbHelper.getWritableDatabase();
		return this;
		}
	
	/* Close the database */
	public void close() {
		mDbHelper.close();
	}
	
	public long createEvent(String title, String body) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_TITLE, title);
		initialValues.put(KEY_NOTE, body);
		return mDB.insert(DATABASE_TABLE, null, initialValues);
	}
	
	private static class DatabaseHelper extends SQLiteOpenHelper { 
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION); 
		}
		
		/* called when the database is created for the first time. */
		@Override
		public void onCreate(SQLiteDatabase db) { 
		/* creates database and database table. */
			db.execSQL(DATABASE_CREATE); 
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion,
		int newVersion) { 
		
		}
	}
	
}
