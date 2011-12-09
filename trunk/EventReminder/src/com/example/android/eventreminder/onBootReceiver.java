package com.example.android.eventreminder;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
/* AlarmManager does not persist alarms. When the device reboots, 
 * all the alarms need to set up all over again. This class handles
 * the boot notification from Android. When the broadcast for boot is received, 
 * the receiver schedules an alarm or add proximity alert for each event in the database.
 * for it. This class ensures that alarms/proximity alerts don’t get lost in the reboot.*/
public class onBootReceiver extends BroadcastReceiver{
	/* Called when receiver receives an intent */
	@Override
	public void onReceive(Context context, Intent intent) { 
		EventReminderManager reminderMgr = new EventReminderManager(context); 
		EventLocationManager locationMgr = new EventLocationManager(context); 
		
		eventDB dbHelper = new eventDB(context);
		dbHelper.open();
		
		Cursor cursor = dbHelper.fetchAllEvents(); 
		if(cursor != null) {
			cursor.moveToFirst(); 
			int rowIdColumnIndex = cursor.getColumnIndex(eventDB.KEY_ROWID);
			int dateTimeColumnIndex = cursor.getColumnIndex(eventDB.KEY_DATE_TIME);
			/* check is cursor passed the last record */
			while(cursor.isAfterLast() == false) {
				Long rowId = cursor.getLong(rowIdColumnIndex);
				Log.d("OnBootReceiver", "Adding alarm from boot.");
				Log.d("OnBootReceiver", "Row Id Column Index - " + rowIdColumnIndex);
				
				String dateTime = cursor.getString(dateTimeColumnIndex);
				if(dateTime != null)
				{
					Calendar cal = Calendar.getInstance();
					SimpleDateFormat format = new SimpleDateFormat(AddReminder.DATE_TIME_FORMAT);
					
					try {
						java.util.Date date = format.parse(dateTime); 
						cal.setTime(date); 
						
						reminderMgr.setReminder(rowId, cal);
					} catch (java.text.ParseException e) {
						Log.e("OnBootReceiver", e.getMessage(), e); 
					}
				}
				else
				{
					String latitude = cursor.getString(cursor.getColumnIndex(eventDB.KEY_LATITUDE));
					String longitude = cursor.getString(cursor.getColumnIndex(eventDB.KEY_LONGITUDE));
					
			    	Double dlatitude = Double.valueOf(latitude)/1E6;
			    	Double dlongitude = Double.valueOf(longitude)/1E6;
			    	
		    		Log.d("TAG","latitude: " + dlatitude + "longitude: " + dlongitude);
		    	
			    	locationMgr.addProximityAlert(rowId, dlatitude, dlongitude );

				}
				cursor.moveToNext(); 
			}//end of while
			cursor.close() ; 
		}//end of if
		dbHelper.close(); 
	}//end of onReceive()
}
