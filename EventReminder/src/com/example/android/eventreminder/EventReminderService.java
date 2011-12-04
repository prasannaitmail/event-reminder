package com.example.android.eventreminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

public class EventReminderService extends WakeReminderIntentService {
	private eventDB mDbHelper;
	private String notificationTitle;
	
	public EventReminderService() {
		super("EventReminderService");
	}
	
	@Override
	void doReminderWork(Intent intent) { 
		/* Status bar notification */
		/* when the notification is selected from status bar,
		 * AddReminder activity will be started using rowId as part of 
		 * pending intent. AddReminder activity will be used to read 
		 * and display event data to the user*/
		Long rowId = intent.getExtras().getLong(eventDB.KEY_ROWID); 
		
		NotificationManager mgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		
		/* Add reminder activity will started when notification is selected */
		Intent notificationIntent = new Intent(this, ViewReminder.class); 
		notificationIntent.putExtra(eventDB.KEY_ROWID, rowId); 
		
		int rowID = rowId.intValue();
		/* set pending intent */
		PendingIntent pi = PendingIntent.getActivity(this, rowID, notificationIntent,
		PendingIntent.FLAG_ONE_SHOT);
		Log.d("TAG", "PI in ERSer for: "+rowID);
		/* set notification for status bar */
		Notification note=new Notification(android.R.drawable.stat_sys_warning,
										getString(R.string.notify_new_task_message),
										System.currentTimeMillis());
		
		
		mDbHelper = new eventDB(this);
		mDbHelper.open();
		Cursor notificationData = mDbHelper.fetchEvent(rowId); 
		/* get title of the event*/
		notificationTitle = notificationData.getString(
				notificationData.getColumnIndexOrThrow(eventDB.KEY_TITLE)); 
		
		note.setLatestEventInfo(this, notificationTitle,
								getString(R.string.notify_new_task_message), pi); 
		 
		notificationData.close();
		mDbHelper.close();
		
		/* Set default notification sound */
		note.defaults |= Notification.DEFAULT_SOUND; 
		/* Cancel notification after selection by user */
		note.flags |= Notification.FLAG_AUTO_CANCEL; 
	
		int id = (int)((long)rowId); 
		/* produce notification on status bar */
		mgr.notify(id, note);
	}
}
