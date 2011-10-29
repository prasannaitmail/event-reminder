package com.example.android.eventreminder;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

/* base class for EventReminderService class.
 * Handles acquiring and releasing CPU wake lock */
public abstract class WakeReminderIntentService extends IntentService{
	abstract void doReminderWork(Intent intent);
	
	public static final String LOCK_NAME_STATIC="com.example.android.eventReminder.Static"; 
	private static PowerManager.WakeLock lockStatic = null; 
	
	public static void acquireStaticLock(Context context) {
		getLock(context).acquire(); 
	}
	/* Inform Android the device needs to be on for some work  */
	synchronized private static PowerManager.WakeLock getLock(Context context) { 
		if (lockStatic==null) {
			PowerManager mgr=(PowerManager)context.getSystemService(Context.POWER_SERVICE); 
			/* create new wakelock 
			 * PARTIAL_WAKE_LOCK :CPU needs to be on, but the screen does not have to be on*/
			lockStatic=mgr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,LOCK_NAME_STATIC); 
			lockStatic.setReferenceCounted(true);
		}
		/* return wakelock */
		return(lockStatic);
	}
	/* constructor */
	public WakeReminderIntentService(String name) { 
			super(name);
	}
	
	@Override
	final protected void onHandleIntent(Intent intent) { 
		try {
			doReminderWork(intent); 
		} finally {
			getLock(this).release(); 
		}
	}
}
