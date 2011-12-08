package com.example.android.eventreminder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.ParseException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;

/* This class is used to add and edit the event. It uses data from incoming intent to
 * decide weather it is request to add new event or edit event. */
public class AddReminder extends Activity {
	String RowID;
	private Long mRowId;
	private Long mLocationRowId = 0L;
	private boolean mTimeupdate = true;
	
	private Calendar mCalendar;
		
	private Button mPickDateButton;
	private Button mPickTimeButton;
	private Button mPickAlarmButton;
	private Button mSetTimeUpdateButton;
	private Button mSetLocationUpdateButton;
	private Button mSetPickLocationButton;
    private Button mSaveButton;
    private Button mCancelButton;
	
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "kk:mm";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd kk:mm:ss";

    static final int DATE_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID = 1;
    private int AlarmTime = 0;
    private int[] arrAlarmTime = {0, 5, 15, 30, 60};
    
    private EditText mTitleText;
    private EditText mNoteText;
    private eventDB mDbHelper;
    private String mLatitude;
    private String mLongitude;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	mDbHelper = new eventDB(this);
	
	setContentView(R.layout.add_reminder);

	mCalendar = Calendar.getInstance();
	
	LinearLayout locationUpdateLayout = (LinearLayout)findViewById(R.id.locationUpdateLayout);
	LinearLayout timeUpdateLayout = (LinearLayout)findViewById(R.id.timeUpdateLayout);
	LinearLayout defaultLayout2 = (LinearLayout)findViewById(R.id.defaultLayout2);
	// capture our View elements
	mSetTimeUpdateButton =(Button) findViewById(R.id.timeUpdateButton);
	mSetLocationUpdateButton = (Button) findViewById(R.id.locationUpdateButton);
	mPickDateButton = (Button) findViewById(R.id.dateForReminder);
	mPickTimeButton = (Button) findViewById(R.id.timeForReminder);
	mPickAlarmButton = (Button) findViewById(R.id.alarmForReminder);
	mSetPickLocationButton = (Button) findViewById(R.id.useSavedLocation);
	mTitleText = (EditText) findViewById(R.id.title);
	mNoteText =(EditText) findViewById(R.id.notes);
	mSaveButton = (Button) findViewById(R.id.save);
	mCancelButton = (Button) findViewById(R.id.cancel);
	
	/* Check instance state to see whether 
	 * it contains any values for the mRowId. */
	mRowId = savedInstanceState != null 
	? savedInstanceState.getLong(eventDB.KEY_ROWID): null;
	
		/*if(getIntent()!= null){
			Bundle extras = getIntent().getExtras();
			int rowID = extras != null ? extras.getInt(RowID) : -1;
		}*/
		/* Make layout elements invisible */
		timeUpdateLayout.setVisibility(View.GONE);
		locationUpdateLayout.setVisibility(View.GONE);
		defaultLayout2.setVisibility(View.GONE);
		
		registerButtonListenersAndSetDefaultText();
	}//End of onCreate()
	
	private void setRowIdFromIntent() { 
		if (mRowId == null) {
		Bundle extras = getIntent().getExtras();
		mRowId = extras != null	? extras.getLong(eventDB.KEY_ROWID)	: null;
		}
		
		if(mRowId == null)
		{
			setTitle("Add New Event");
		}
		else
		{
			setTitle("Edit Event");
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		mDbHelper.close(); 
	}
	@Override
	protected void onResume() { 
		super.onResume();
		mDbHelper.open(); 
		setRowIdFromIntent(); 
		showFields(); 
		updatePickLocationButtonText();
	}
	
    private void registerButtonListenersAndSetDefaultText() {
		/* Click listener to the set time update button */
		mSetTimeUpdateButton.setOnClickListener(new View.OnClickListener() {
			LinearLayout timeUpdateLayout = (LinearLayout)findViewById(R.id.timeUpdateLayout);
			LinearLayout defaultLayout2 = (LinearLayout)findViewById(R.id.defaultLayout2);
			LinearLayout EventOptionsLayout = (LinearLayout)findViewById(R.id.EventOptionsLayout);
			@Override
			public void onClick(View v) {
				mTimeupdate=true;
				timeUpdateLayout.setVisibility(View.VISIBLE);
				defaultLayout2.setVisibility(View.VISIBLE);
				EventOptionsLayout.setVisibility(View.GONE);
			}
		});	
		
		/* Click listener to the set Location update button */
		mSetLocationUpdateButton.setOnClickListener(new View.OnClickListener() {
			LinearLayout locationUpdateLayout = (LinearLayout)findViewById(R.id.locationUpdateLayout);
			LinearLayout defaultLayout2 = (LinearLayout)findViewById(R.id.defaultLayout2);
			LinearLayout EventOptionsLayout = (LinearLayout)findViewById(R.id.EventOptionsLayout);
			@Override
			public void onClick(View v) {
				mTimeupdate=false;
				locationUpdateLayout.setVisibility(View.VISIBLE);
				defaultLayout2.setVisibility(View.VISIBLE);
				EventOptionsLayout.setVisibility(View.GONE);
			}
		});
		
    	/* click listener to the Date button*/
    	mPickDateButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(DATE_DIALOG_ID);
			}
		});

		/* Click listener to the Time button */
		mPickTimeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			showDialog(TIME_DIALOG_ID);
			}
		});
		
		/* Click listener to the Alarm button */
		mPickAlarmButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AlarmDialog();
			}
		});
		
		mSetPickLocationButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Intent intent = new Intent(this, LocationActivity.class);
				//startActivity(intent);
		    	Intent intent = new Intent(AddReminder.this, LocationActivity.class);
		    	intent.putExtra("LocationRowNumber", 0L);
		        final int result=1;
		        AddReminder.this.startActivityForResult(intent, result);
				
			}
		});
		
		/* Click listener to the Save button */
		mSaveButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				boolean result = saveEvent();
				if( result)
				{
				setResult(RESULT_OK); 
				Toast.makeText(AddReminder.this, 
						getString(R.string.event_saved_message),
						Toast.LENGTH_SHORT).show();
				finish(); 
				}
			}
		});
		
		/* Click listener to the Cancel button */
		mCancelButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Toast.makeText(AddReminder.this, 
						getString(R.string.event_canceled_message),
						Toast.LENGTH_SHORT).show();
				finish(); 
			}
		});
		
		
		updateDateButtonText();
		updateTimeButtonText();
	}

    @Override
    protected Dialog onCreateDialog(int id) { 
    	switch(id) {
    		case DATE_DIALOG_ID: 
    			return showDatePicker();
    		case TIME_DIALOG_ID:
    			return showTimePicker();
    	}
    	return super.onCreateDialog(id);
    }
    
    private DatePickerDialog showDatePicker() { 
    	DatePickerDialog datePicker = new DatePickerDialog(AddReminder.this,
    	new DatePickerDialog.OnDateSetListener() {
    		@Override
    		public void onDateSet(DatePicker view, int year, int monthOfYear,
    		int dayOfMonth) { 
    		mCalendar.set(Calendar.YEAR, year); 
    		mCalendar.set(Calendar.MONTH, monthOfYear);
    		mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    		updateDateButtonText(); 
    		}
    	},mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
    	mCalendar.get(Calendar.DAY_OF_MONTH)); 
    	return datePicker;
    }
    
    private TimePickerDialog showTimePicker() {
    	TimePickerDialog timePicker = new TimePickerDialog(this, new
    	TimePickerDialog.OnTimeSetListener() { 
    	@Override
    	public void onTimeSet(TimePicker view, int hourOfDay, int minute){ 
    		mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay); 
    		mCalendar.set(Calendar.MINUTE, minute); 
    		updateTimeButtonText(); 
    	}
    	}, mCalendar.get(Calendar.HOUR_OF_DAY), 
    		mCalendar.get(Calendar.MINUTE), true); 
    	return timePicker;
    	}
    
    private void updateDateButtonText() { 
    	SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT); 
    	String dateForButton = dateFormat.format(mCalendar.getTime()); 
    	mPickDateButton.setText(dateForButton);
    	}
    
    private void updateTimeButtonText() {
    	SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT); 
    	String timeForButton = timeFormat.format(mCalendar.getTime()); 
    	mPickTimeButton.setText(timeForButton); 
    	}

    private void updateAlarmButtonText() {
    	int i = AlarmTime;
    	if(i == 0)
    		mPickAlarmButton.setText("On Due Time");
    	else
    		mPickAlarmButton.setText("Before " + arrAlarmTime[i] + " Mins"); 
    }
    
    private void updatePickLocationButtonText() {
		if(mLocationRowId > 0)
		{
    		Cursor LocationCursor = mDbHelper.fetchLocation(mLocationRowId);
    		startManagingCursor(LocationCursor);
   			String title = LocationCursor.getString(
    					LocationCursor.getColumnIndexOrThrow(
    							eventDB.KEY_LOCATION)); 
   			Log.d("TAG", "title: " + title);
   			mSetPickLocationButton.setText("Location: " + title);
		}
		
   	}
    
    /*Alarm options */
    private void AlarmDialog(){
    	final String[] AlarmOptions = {"On due time",
    									"Before 5 minutes",
    									"Before 15 minutes",
    									"Before 30 minutes",
    									"Before 1 hour"
    									};
    	AlertDialog alert = new AlertDialog.Builder(AddReminder.this)
       // .setIconAttribute(android.R.attr.alertDialogIcon)
        .setTitle("Alarm Time")
        .setSingleChoiceItems(AlarmOptions, getAlarmTime(), new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int whichButton) {

                /* User clicked on a radio button to set alarm time */
            	Log.d("Alarm", "Button " + whichButton);
            	setAlarmTime(whichButton);
            }
        })
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                /* User clicked Yes so do some stuff */
            	Log.d("Alarm", "Button OK " + whichButton);
            	updateAlarmButtonText();

            }
        })
       .create();
    	alert.show();
    
    }
    
    /* For edit event, this methods assigns values to respective form fields */
    private void showFields(){ 
    	if (mRowId != null) {
    		Log.d("TAG", "mRowId is "+mRowId);
    		/* catch the linear layouts for setting visibility */
    		LinearLayout timeUpdateLayout = (LinearLayout)findViewById(R.id.timeUpdateLayout);
			LinearLayout defaultLayout2 = (LinearLayout)findViewById(R.id.defaultLayout2);
			LinearLayout EventOptionsLayout = (LinearLayout)findViewById(R.id.EventOptionsLayout);
			LinearLayout locationUpdateLayout = (LinearLayout)findViewById(R.id.locationUpdateLayout);
			timeUpdateLayout.setVisibility(View.VISIBLE);
			defaultLayout2.setVisibility(View.VISIBLE);
			EventOptionsLayout.setVisibility(View.GONE);
    		
    		Cursor reminder = mDbHelper.fetchEvent(mRowId); 
    		startManagingCursor(reminder);
    		mTitleText.setText(reminder.getString(
    				reminder.getColumnIndexOrThrow(eventDB.KEY_TITLE))); 
    		mNoteText.setText(reminder.getString(
    				reminder.getColumnIndexOrThrow(eventDB.KEY_NOTE))); 
    		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
    		Date date = null; 
    		try {
    			String dateString = reminder.getString(
    					reminder.getColumnIndexOrThrow(
    							eventDB.KEY_DATE_TIME));
    			if( dateString != null)
    			{
    				date = dateTimeFormat.parse(dateString);
    				mCalendar.setTime(date);
       			}
    			else
    			{
    				mTimeupdate = false;	
    				timeUpdateLayout.setVisibility(View.GONE);
    				locationUpdateLayout.setVisibility(View.VISIBLE);
    			}
    		} catch (java.text.ParseException e) { 
    			Log.e("AddReminder", e.getMessage(), e); 
    		}
    		if(mTimeupdate)
    		{
    			setAlarmTime (Integer.parseInt(reminder.getString(
    				reminder.getColumnIndexOrThrow(eventDB.KEY_ALARMOPTION)))); 
    		}
    		else
    		{
    			mLatitude = reminder.getString(
        				reminder.getColumnIndexOrThrow(eventDB.KEY_LATITUDE));
    			mLongitude = reminder.getString(
        				reminder.getColumnIndexOrThrow(eventDB.KEY_LONGITUDE));
    			Cursor LocationCursor = mDbHelper.fetchLocation(mLatitude, mLongitude); 
        		startManagingCursor(LocationCursor);
        		if(LocationCursor != null)
        		{
        			if(mLocationRowId <= 0)
        			{
        				mLocationRowId = (long) LocationCursor.getInt(
        						reminder.getColumnIndexOrThrow(eventDB.KEY_ROWID));
        			}
        			Log.d("TAG","mLocationRowId is " + mLocationRowId);
        			if ( mLocationRowId > 0)
        				updatePickLocationButtonText();
        		}
    		}
    	}// if (mRowId != null)
    	if(mTimeupdate)
    	{
    		updateDateButtonText();
    		updateTimeButtonText();
    		updateAlarmButtonText();
    	}
    }//end of showFields()
   
   /* Saves the mRowId instance state. This method is called before
the activity is killed so that when the activity comes back in the
future, it can be restored to a known state */
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	if(mRowId != null)
    		outState.putLong(eventDB.KEY_ROWID, mRowId); 
    }
    
    /* Saves the event */
    private boolean saveEvent() {
    	String title = mTitleText.getText().toString(); 
    	if(title.length() <= 0)
    	{
			Toast.makeText(AddReminder.this, 
					getString(R.string.no_title_message),
					Toast.LENGTH_SHORT).show();
    		return false;
    	}
    	String body = mNoteText.getText().toString(); 

    	if(mTimeupdate)
    	{
    		Log.d("TAG","Time alarm");
        	String alarmOption = String.valueOf(AlarmTime);

        	
	    	SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
	    	mCalendar.set(mCalendar.SECOND, 0);
	    	Log.d("TAG Time", "Old time for Alarm, Second: "+mCalendar.get(mCalendar.SECOND));
	    	String eventDateTime = dateTimeFormat.format(mCalendar.getTime()); 

	    	String latitude = null;
	    	String longitude = null;
	    	if (mRowId == null) {
	    		long id = mDbHelper.createEvent(title, body, eventDateTime, alarmOption, latitude, longitude); 
	    		if (id > 0) { 
	    			mRowId = id; 
	    		}
	    	}
	    	else {
	    		mDbHelper.updateEvent(mRowId, title, body, eventDateTime, alarmOption, latitude, longitude); 
	    	}
	    	Log.d("TAG", "Alarm in AR for: "+mRowId);
	    	
	    	/*add alarm */
	    	mCalendar.set(mCalendar.MINUTE, (mCalendar.get(mCalendar.MINUTE) - getAlarmTime())); 
	    	Log.d("TAG Time", "New time for Alarm2: "+mCalendar.get(mCalendar.MINUTE));
	    	Log.d("TAG Time", "New time for Alarm, Second: "+mCalendar.get(mCalendar.SECOND));
	    	new EventReminderManager(this).setReminder(mRowId, mCalendar);
    	}
    	else
    	{
    		Log.d("TAG","Location alarm");
    		
    		if(mLocationRowId <= 0)
    		{
				Toast.makeText(AddReminder.this, 
						getString(R.string.no_pick_message),
						Toast.LENGTH_SHORT).show();
    			
    			return false;
    		}
    		
    		Cursor LocationCursor = mDbHelper.fetchLocation(mLocationRowId);
    		
   			String latitude = LocationCursor.getString(
    					LocationCursor.getColumnIndexOrThrow(
    							eventDB.KEY_LATITUDE)); 
   			String longitude = LocationCursor.getString(
    					LocationCursor.getColumnIndexOrThrow(
    							eventDB.KEY_LONGITUDE)); 
    		
    		String eventDateTime = null;
    		String alarmOption = null;
    		Log.d("TAG","latitude: " + latitude + "longitude: " + longitude);

	    	if (mRowId == null) {
	    		long id = mDbHelper.createEvent(title, body, eventDateTime, alarmOption, latitude, longitude); 
	    		if (id > 0) { 
	    			mRowId = id; 
	    		}
	    	}
	    	else {
	    		mDbHelper.updateEvent(mRowId, title, body, eventDateTime, alarmOption, latitude, longitude); 
	    	}
	    	/*add promixityalert alarm */
	    	Double dlatitude = Double.valueOf(latitude)/1E6;
	    	Double dlongitude = Double.valueOf(longitude)/1E6;
	    	
    		Log.d("TAG","latitude: " + dlatitude + "longitude: " + dlongitude);
    	
	    	new EventLocationManager(this).addProximityAlert(mRowId, dlatitude, dlongitude );
    	}
    	
    	return true;
    }//end of saveEvent
      
	protected void onActivityResult(int requestCode, int resultCode, Intent intent){
    	super.onActivityResult(requestCode, resultCode, intent);

    	if( resultCode == RESULT_OK )
    	{
    		mLocationRowId = intent.getLongExtra("LocationRowNumber", 0L);
    	}
    	
    	Log.d("TAG", "LocationRowNumber1: " + mLocationRowId );
    	
	}

	public void setAlarmTime(int alarmTime) {
		AlarmTime = alarmTime;
	}

	public int getAlarmTime() {
		return arrAlarmTime[AlarmTime];
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	@Override
	public void onStop() {
	
		super.onStop();
	}
		    
}
