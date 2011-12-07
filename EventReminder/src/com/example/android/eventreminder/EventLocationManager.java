package com.example.android.eventreminder;


import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class EventLocationManager {

	private Context mContext;
	private LocationManager mLocationManager;
	
	private static final long POINT_RADIUS = 1000; // in Meters
	
	public EventLocationManager(Context context) { 
		mContext = context;
		/* Get alarm manager */
		mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
	}
	
	public void addProximityAlert(Long eventId, double latitude, double longitude) 
	{
		Intent intent = new Intent(mContext, LocationReceiver.class);
		intent.putExtra(eventDB.KEY_ROWID, (long)eventId); 
		
		int rowID = eventId.intValue();
		PendingIntent proximityIntent = PendingIntent.getBroadcast(mContext, rowID, intent, PendingIntent.FLAG_ONE_SHOT);
		
		mLocationManager.addProximityAlert(
				latitude, // the latitude of the central point of the alert region
				longitude, // the longitude of the central point of the alert region
		        POINT_RADIUS, // the radius of the central point of the alert region, in meters
		        -1, // time for this proximity alert, in milliseconds, or -1 to indicate no expiration
		        proximityIntent // will be used to generate an Intent to fire when entry to or exit from the alert region is detected
		);
		
		/*IntentFilter filter = new IntentFilter(AlarmReceiver.class); 
		registerReceiver(new ProximityIntentReceiver(), filter);*/
			        
	}

}
