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
	private static final String LOCATION_TABLE = "location";
	private static final int DATABASE_VERSION = 1;
	public static final String KEY_TITLE = "title";
	public static final String KEY_NOTE = "notes";
	public static final String KEY_DATE_TIME = "reminder_date_time";
	public static final String KEY_ALARMOPTION = "alarmoption";
	public static final String KEY_ROWID = "_id";
	public static final String KEY_LATITUDE = "latitude";
	public static final String KEY_LONGITUDE = "longitude";
	public static final String KEY_LOCATION = "location";

	
	private SQLiteDatabase mDB;
	private DatabaseHelper mDbHelper; //SQLiteOpen-	Helper class
	
	private static final String DATABASE_CREATE = 
		"create table " + DATABASE_TABLE + " ("
		+ KEY_ROWID + " integer primary key autoincrement, "
		+ KEY_TITLE + " text not null, "
		+ KEY_NOTE + " text not null, "
		+ KEY_DATE_TIME + " text, "
		+ KEY_ALARMOPTION + " text, "
		+ KEY_LATITUDE + " text, "
		+ KEY_LONGITUDE + " text);";
	

	private static final String LOCATION_CREATE = 
			"create table " + LOCATION_TABLE + " ("
			+ KEY_ROWID + " integer primary key autoincrement, "
			+ KEY_LOCATION + " text not null, "
			+ KEY_LATITUDE + " text not null, "
			+ KEY_LONGITUDE + " text not null);";

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
	public long createEvent(String title, String body, String eventDateTime, String alarmOption,
			String latitude, String longitude) {
		/* Content values will store set of values */
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_TITLE, title);
		initialValues.put(KEY_NOTE, body);
		initialValues.put(KEY_DATE_TIME, eventDateTime);
		initialValues.put(KEY_ALARMOPTION, alarmOption);
		initialValues.put(KEY_LATITUDE, latitude);
		initialValues.put(KEY_LONGITUDE, longitude);
		
		return mDB.insert(DATABASE_TABLE, null, initialValues);
	}
	
	/* Delete event */
	public boolean deleteReminder(long rowId) { 
		return mDB.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0; 
		}	
	
	public Cursor fetchAllEvents() { 
		return mDB.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TITLE,
		KEY_NOTE, KEY_DATE_TIME, KEY_ALARMOPTION, KEY_LATITUDE, KEY_LONGITUDE}, null, null, null, null, null);
		}
	
	public Cursor fetchEvent(long rowId) throws SQLException { 
		Cursor mCursor = 
			mDB.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
					KEY_TITLE, KEY_NOTE, KEY_DATE_TIME, KEY_ALARMOPTION, KEY_LATITUDE, KEY_LONGITUDE}, KEY_ROWID + "=" +
					rowId, null, null, null, null, null); 
			if (mCursor != null) {
				mCursor.moveToFirst(); 
			}
		return mCursor;
	}
	
	public boolean updateEvent(long rowId, String title, String body, String
				reminderDateTime, String alarmOption, String latitude, String longitude) { 
			ContentValues args = new ContentValues(); 
			args.put(KEY_TITLE, title);
			args.put(KEY_NOTE, body);
			args.put(KEY_DATE_TIME, reminderDateTime);
			args.put(KEY_ALARMOPTION, alarmOption);
			args.put(KEY_LATITUDE, latitude);
			args.put(KEY_LONGITUDE, longitude);

			return mDB.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0; 
	}

	
	
	/*Create event*/
	public long createLocation(String location, String latitude, String longitude) {
		/* Content values will store set of values */
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_LOCATION, location);
		initialValues.put(KEY_LATITUDE, latitude);
		initialValues.put(KEY_LONGITUDE, longitude);
		
		return mDB.insert(LOCATION_TABLE, null, initialValues);
	}
	
	/* Delete event */
	public boolean deleteLocation(long rowId) { 
		return mDB.delete(LOCATION_TABLE, KEY_ROWID + "=" + rowId, null) > 0; 
		}	
	
	public Cursor fetchAllLocations() { 
		return mDB.query(LOCATION_TABLE, new String[] {KEY_ROWID, KEY_LOCATION, KEY_LATITUDE, KEY_LONGITUDE}, null, null, null, null, null);
		}
	
	public Cursor fetchLocation(long rowId) throws SQLException { 
		Cursor mCursor = 
			mDB.query(true, LOCATION_TABLE, new String[] {KEY_ROWID, KEY_LOCATION, KEY_LATITUDE, KEY_LONGITUDE}, KEY_ROWID + "=" +
					rowId, null, null, null, null, null); 
			if (mCursor != null) {
				mCursor.moveToFirst(); 
			}
		return mCursor;
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
			db.execSQL(LOCATION_CREATE); 
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion,
		int newVersion) { 
		
		}
	}//end of class DatabaseHelper
	
}
