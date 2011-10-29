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
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddReminder extends Activity {
	String RowID;
	private Long mRowId;
	
	private Calendar mCalendar;
		
	private Button mPickDateButton;
	private Button mPickTimeButton;
		
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "kk:mm";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd kk:mm:ss";

    static final int DATE_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID = 1;
    
    private EditText mTitleText;
    private EditText mNoteText;
    private Button mSaveButton;
    private Button mCancelButton;
    private eventDB mDbHelper;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	mDbHelper = new eventDB(this);
	
	setContentView(R.layout.add_reminder);

	mCalendar = Calendar.getInstance();
	// capture our View elements
	mPickDateButton = (Button) findViewById(R.id.dateForReminder);
	mPickTimeButton = (Button) findViewById(R.id.timeForReminder);
	mTitleText = (EditText) findViewById(R.id.title);
	mNoteText =(EditText) findViewById(R.id.notes);
	mSaveButton = (Button) findViewById(R.id.save);
	mCancelButton = (Button) findViewById(R.id.cancel);
	
	mRowId = savedInstanceState != null 
	? savedInstanceState.getLong(eventDB.KEY_ROWID)
	: null;
	
		if(getIntent()!= null){
			Bundle extras = getIntent().getExtras();
			int rowID = extras != null ? extras.getInt(RowID) : -1;
		}
	
		registerButtonListenersAndSetDefaultText();
	}//End of onCreate()
	
	private void setRowIdFromIntent() { 
		if (mRowId == null) {
		Bundle extras = getIntent().getExtras();
		mRowId = extras != null	? extras.getLong(eventDB.KEY_ROWID)	: null;
		}
		
		if(mRowId == null)
			setTitle("Add New Event");
		else
			setTitle("Edit Event");
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
	}
	
    private void registerButtonListenersAndSetDefaultText() {
		// TODO Auto-generated method stub
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
		
		/* Click listener to the Save button */
		mSaveButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				saveEvent(); 
				setResult(RESULT_OK); 
				Toast.makeText(AddReminder.this, 
						getString(R.string.event_saved_message),
						Toast.LENGTH_SHORT).show();
				finish(); 
			}
		});
		
		/* Click listener to the Save button */
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
    
    private void showFields(){ 
    	if (mRowId != null) {
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
    			date = dateTimeFormat.parse(dateString);
    			mCalendar.setTime(date);
    		} catch (java.text.ParseException e) { 
    			Log.e("AddReminder", e.getMessage(), e); 
    		}
    	}
    	updateDateButtonText();
    	updateTimeButtonText();
    }
   
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	outState.putLong(eventDB.KEY_ROWID, mRowId); 
    }
    
    /* Saves the event */
    private void saveEvent() {
    	String title = mTitleText.getText().toString(); 
    	String body = mNoteText.getText().toString(); 
    	SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT); 
    	String eventDateTime = dateTimeFormat.format(mCalendar.getTime()); 
    	if (mRowId == null) {
    		long id = mDbHelper.createEvent(title, body, eventDateTime); 
    		if (id > 0) { 
    			mRowId = id; 
    		}
    	}
    	else {
    		mDbHelper.updateEvent(mRowId, title, body, eventDateTime); 
    	}
    	/*add alarm */
    	new EventReminderManager(this).setReminder(mRowId, mCalendar);
    }//end of saveEvent
      
	protected void onActivityResult(int requestCode, int resultCode, Intent intent){
    	super.onActivityResult(requestCode, resultCode, intent);
	
	}
}
