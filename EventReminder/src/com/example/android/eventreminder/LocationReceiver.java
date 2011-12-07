package com.example.android.eventreminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
/* AlarmReceiver is an instance of BroadcastReceiver.
 * When the pending intent is broadcasted by the AlarmManager,
 * this class responds to the intent.
 * It accepts the intent, locks the CPU, and performs the necessary work.*/
public class LocationReceiver extends BroadcastReceiver{
	@Override
	public void onReceive(Context context, Intent intent) {
		long rowid = intent.getExtras().getLong(eventDB.KEY_ROWID); 
		Log.d("TAG", "eventId in AlarmReceiver: "+rowid);
	/* acquire static lock to keep device alive during work*/
	WakeReminderIntentService.acquireStaticLock(context); 
	/* start EventReminderService */
	Intent i = new Intent(context, EventReminderService.class); 
	i.putExtra(eventDB.KEY_ROWID, rowid); 
	context.startService(i); 
	}
}
