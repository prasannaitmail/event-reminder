package com.example.android.eventreminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class eventDB {
	private static final String DATABASE_NAME = "eventData"; 
	private static final String DATABASE_TABLE = "events";
	private static final int DATABASE_VERSION = 1;
	public static final String KEY_TITLE = "title";
	public static final String KEY_NOTE = "notes";
	public static final String KEY_DATE_TIME = "reminder_date_time";
	public static final String KEY_ROWID = "_id";
	
	private SQLiteDatabase mDB;
	private DatabaseHelper mDbHelper; //SQLiteOpen-	Helper class
	
	private static final String DATABASE_CREATE = 
		"create table " + DATABASE_TABLE + " ("
		+ KEY_ROWID + " integer primary key autoincrement, "
		+ KEY_TITLE + " text not null, "
		+ KEY_NOTE + " text not null, "
		+ KEY_DATE_TIME + " text not null);";
	
	
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
	/*Create event*/
	public long createEvent(String title, String body, String eventDateTime) {
		/* Content values will store set of values */
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_TITLE, title);
		initialValues.put(KEY_NOTE, body);
		initialValues.put(KEY_DATE_TIME, eventDateTime);
		
		return mDB.insert(DATABASE_TABLE, null, initialValues);
	}
	
	/* Delete event */
	public boolean deleteReminder(long rowId) { 
		return mDB.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0; 
		}	
	
	public Cursor fetchAllEvents() { 
		return mDB.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TITLE,
		KEY_NOTE, KEY_DATE_TIME}, null, null, null, null, null);
		}
	
	public Cursor fetchEvent(long rowId) throws SQLException { 
		Cursor mCursor = 
			mDB.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
					KEY_TITLE, KEY_NOTE, KEY_DATE_TIME}, KEY_ROWID + "=" +
					rowId, null, null, null, null, null); 
			if (mCursor != null) {
				mCursor.moveToFirst(); 
			}
		return mCursor;
	}
	
	public boolean updateEvent(long rowId, String title, String body, String
				reminderDateTime) { 
			ContentValues args = new ContentValues(); 
			args.put(KEY_TITLE, title);
			args.put(KEY_NOTE, body);
			args.put(KEY_DATE_TIME, reminderDateTime);
		return mDB.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0; 
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
	}//end of class DatabaseHelper
	
}
