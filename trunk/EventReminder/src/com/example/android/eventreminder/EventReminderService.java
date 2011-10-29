package com.example.android.eventreminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

public class EventReminderService extends WakeReminderIntentService {
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
		Intent notificationIntent = new Intent(this, AddReminder.class); 
		notificationIntent.putExtra(eventDB.KEY_ROWID, rowId); 
		
		/* set pending intent */
		PendingIntent pi = PendingIntent.getActivity(this, 0, notificationIntent,
		PendingIntent.FLAG_ONE_SHOT);
		
		/* set notification for status bar */
		Notification note=new Notification(android.R.drawable.stat_sys_warning,
										getString(R.string.notify_new_task_message),
										System.currentTimeMillis());
		
		note.setLatestEventInfo(this, getString(R.string.notifiy_new_task_title),
								getString(R.string.notify_new_task_message), pi); 
		
		/* Set default notification sound */
		note.defaults |= Notification.DEFAULT_SOUND; 
		/* Cancel notification after selection by user */
		note.flags |= Notification.FLAG_AUTO_CANCEL; 
	
		int id = (int)((long)rowId); 
		/* produce notification on status bar */
		mgr.notify(id, note);
	}
}
