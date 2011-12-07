package com.example.android.eventreminder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class ViewReminder extends Activity{

    private eventDB vDbHelper;
	String vRowID;
	private Long vRowId;
	private int vSnooze = 10;
	private String AlarmTime;
	
	private TextView vTitleText;
    private TextView vNotesText;
    private Button vSnoozeButton;
    private Button vDeleteButton;
    private RadioButton vRadioButton10;
    private RadioButton vRadioButton30;
    private RadioButton vRadioButton60;
    
    public static final String vDATE_TIME_FORMAT = "yyyy-MM-dd kk:mm:ss";
    private Calendar vCalendar;
	
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	
    	vDbHelper = new eventDB(this);
    	
    	setContentView(R.layout.view_reminder);
    	vCalendar = Calendar.getInstance();
    	
    	// capture our View elements
    	vTitleText = (TextView) findViewById(R.id.view_title);
    	vNotesText = (TextView) findViewById(R.id.view_notes);
    	vRadioButton10 = (RadioButton) findViewById(R.id.radio_for10minutes);
    	vRadioButton30 = (RadioButton) findViewById(R.id.radio_for30minutes);
    	vRadioButton60 = (RadioButton) findViewById(R.id.radio_for1hour);
    	vSnoozeButton = (Button) findViewById(R.id.view_snooze);
    	vDeleteButton = (Button) findViewById(R.id.view_delete);
    	
    	vRadioButton10.setChecked(true);
    	
    	/* Check instance state to see whether 
    	 * it contains any values for the mRowId. */
    	vRowId = savedInstanceState != null 
    	? savedInstanceState.getLong(eventDB.KEY_ROWID) : null;
    	
		registerButtonListenersAndSetDefaultTextForView();
    }//end of onCreate
    
	private void setRowIdFromIntent() { 
		if (vRowId == null) {
		Bundle extras = getIntent().getExtras();
		vRowId = extras != null	? extras.getLong(eventDB.KEY_ROWID)	: null;
		}
	}
    
    @Override
	protected void onPause() {
		super.onPause();
		vDbHelper.close(); 
	}
    
    @Override
	protected void onResume() { 
		super.onResume();
		vDbHelper.open(); 
		setRowIdFromIntent(); 
		showFieldsForView(); 
	}
    
    private void registerButtonListenersAndSetDefaultTextForView() {
     	
       	vRadioButton10.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				vSnooze = 10;				
			}       	
    	});
       	
       	vRadioButton30.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				// RadioButton rb = (RadioButton) v;
				vSnooze = 30;
			}       	
    	});
       	vRadioButton60.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				vSnooze = 60;
			}       	
    	});
    	/* Click listener to the Snooze button */
		vSnoozeButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {					
				saveEventForView(); 
				setResult(RESULT_OK); 
				Toast.makeText(ViewReminder.this, 
						getString(R.string.event_saved_message),
						Toast.LENGTH_SHORT).show();
				finish(); 
			}
		});
    
		/* Click listener to the Delete button */
		vDeleteButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				vDbHelper.deleteReminder(vRowId);
				Toast.makeText(ViewReminder.this, 
						getString(R.string.event_deleted_message),
						Toast.LENGTH_SHORT).show();
				finish(); 
			}
		});
    }//end of registerButtonListenersAndSetDefaultTextForView()
    
    private void showFieldsForView(){
    	Log.d("Tag", "vRowId is "+vRowId);
    	Cursor vReminder = vDbHelper.fetchEvent(vRowId); 
    	Log.d("TAG", "Cursor : "+vReminder.getCount());
    	if(vReminder.getCount() <= 0){
    		finish();
		}
    	startManagingCursor(vReminder);
		
		vTitleText.setText(vReminder.getString(
				vReminder.getColumnIndexOrThrow(eventDB.KEY_TITLE))); 
		vNotesText.setText(vReminder.getString(
				vReminder.getColumnIndexOrThrow(eventDB.KEY_NOTE))); 
		SimpleDateFormat vdateTimeFormat = new SimpleDateFormat(vDATE_TIME_FORMAT);
		Date vDate = null; 
		try {
			String dateString = vReminder.getString(
					vReminder.getColumnIndexOrThrow(
							eventDB.KEY_DATE_TIME)); 
			vDate = vdateTimeFormat.parse(dateString);
			vCalendar.setTime(vDate);
		} catch (java.text.ParseException e) { 
			Log.e("AddReminder", e.getMessage(), e); 
		}
		AlarmTime = vReminder.getString(
				vReminder.getColumnIndexOrThrow(eventDB.KEY_ALARMOPTION));
    
    }//end of showFieldsForView()
    
    /* Saves the event */
    private void saveEventForView() {
    	String title = vTitleText.getText().toString(); 
    	String body = vNotesText.getText().toString(); 
    	SimpleDateFormat dateTimeFormat = new SimpleDateFormat(vDATE_TIME_FORMAT); 
    	Log.d("TAG Time", "Old time: "+vCalendar);
    	vCalendar.add(vCalendar.MINUTE, vSnooze);
    	Log.d("TAG Time", "New time: "+vCalendar);
    	String eventDateTime = dateTimeFormat.format(vCalendar.getTime()); 
    	
    	vDbHelper.updateEvent(vRowId, title, body, eventDateTime, AlarmTime); 
    	
    	/*add alarm */
    	new EventReminderManager(this).setReminder(vRowId, vCalendar);
    }//end of saveEventForView() 
    
    /* Saves the mRowId instance state. This method is called before
    the activity is killed so that when the activity comes back in the
    future, it can be restored to a known state */
        
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
        if(vRowId != null)
        	outState.putLong(eventDB.KEY_ROWID, vRowId); 
        }
    
	protected void onActivityResult(int requestCode, int resultCode, Intent intent){
    	super.onActivityResult(requestCode, resultCode, intent);
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
