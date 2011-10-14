package com.example.android.eventreminder;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddReminder extends Activity {
	private Button mPickDateButton;
	private Button mPickTimeButton;
		
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;

    static final int DATE_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID = 1;
    
    private EditText mTitleText;
    private Button mSaveButton;
    private EditText mNoteText;
    private eventDB mDbHelper;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.add_reminder);

	mDbHelper = new eventDB(this);
	// capture our View elements
	mPickDateButton = (Button) findViewById(R.id.dateForReminder);
	mPickTimeButton = (Button) findViewById(R.id.timeForReminder);

	mSaveButton = (Button) findViewById(R.id.save);
	mTitleText = (EditText) findViewById(R.id.title);
	mNoteText =(EditText) findViewById(R.id.notes);
	
	// add a click listener to the date button
    mPickDateButton.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            showDialog(DATE_DIALOG_ID);
        }
    });

    // add a click listener to the time button
    mPickTimeButton.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            showDialog(TIME_DIALOG_ID);
        }
    });
    // get the current date
    final Calendar c = Calendar.getInstance();
    mYear = c.get(Calendar.YEAR);
    mMonth = c.get(Calendar.MONTH);
    mDay = c.get(Calendar.DAY_OF_MONTH);
    // get the current time
    mHour = c.get(Calendar.HOUR_OF_DAY);
    mMinute = c.get(Calendar.MINUTE);

    
 // display the current date (this method is below)
    updateDateButtonDisplay();
	
    // display the current date
    updateTimeButtonDisplay();

         
	}//End of onCreate()

	// updates the date in the TextView
    private void updateDateButtonDisplay() {
        mPickDateButton.setText(
            new StringBuilder()
                    // Month is 0 based so add 1
                    .append(mMonth + 1).append("-")
                    .append(mDay).append("-")
                    .append(mYear).append(" "));
    }
	
 // updates the time we display in the TextView
    private void updateTimeButtonDisplay() {
        mPickTimeButton.setText(
            new StringBuilder()
                    .append(pad(mHour)).append(":")
                    .append(pad(mMinute)));
    }

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
    
	// the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, 
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDateButtonDisplay();
                }
            };
            
  // the callback received when the user "sets" the time in the dialog
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
    	new TimePickerDialog.OnTimeSetListener() {
        	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        			mHour = hourOfDay;
                    mMinute = minute;
                    updateTimeButtonDisplay();
                    }
                };

            
    @Override
    protected Dialog onCreateDialog(int id) {
    	switch (id) {
        	case DATE_DIALOG_ID:
        		return new DatePickerDialog(this,
        				mDateSetListener,
                        mYear, mMonth, mDay);
        	case TIME_DIALOG_ID:
                return new TimePickerDialog(this,
                        mTimeSetListener, mHour, mMinute, false);
        		}
    	return null;
    	}
           
       	
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
    	super.onActivityResult(requestCode, resultCode, intent);
	
	}
}
