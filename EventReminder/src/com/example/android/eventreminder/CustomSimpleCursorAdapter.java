package com.example.android.eventreminder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class CustomSimpleCursorAdapter extends SimpleCursorAdapter {
	 
	    private Context context;
	    private eventDB mDbHelper;
	    private int layout;
	    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd kk:mm:ss";
	    
	    public CustomSimpleCursorAdapter (Context context, int layout, Cursor c, String[] from, int[] to) {
	        super(context, layout, c, from, to);
	        this.context = context;
	        this.layout = layout;
	    	mDbHelper = new eventDB(context);
	    	mDbHelper.open(); 	
	    }
	 
	    @Override
	    public View newView(Context context, Cursor cursor, ViewGroup parent) {
	 	 
	        final LayoutInflater inflater = LayoutInflater.from(context);
	        View rowView = inflater.inflate(layout, parent, false);
	 
	        int nameCol = cursor.getColumnIndex(eventDB.KEY_TITLE);
	        String name = cursor.getString(nameCol);

	        TextView textView = (TextView) rowView.findViewById(R.id.eventrow);
	        TextView textView1 = (TextView) rowView.findViewById(R.id.eventrow1);

	        
			ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
			
			textView.setText(name);
			
	        String date = cursor.getString(cursor.getColumnIndex(eventDB.KEY_DATE_TIME));
	        String latitude = cursor.getString(cursor.getColumnIndex(eventDB.KEY_LATITUDE));
	        String longitude = cursor.getString(cursor.getColumnIndex(eventDB.KEY_LONGITUDE));
    		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
    		Date date1 = null; 

	        if(date != null)
	        	imageView.setImageResource(R.drawable.clock_icon);
	        else
	        	imageView.setImageResource(R.drawable.location_icon);
	        	
	        if(date != null)
	        {
				try {
					date1 = dateTimeFormat.parse(date);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				textView1.setText(date1.toString());
	        }
	        else
	        {
	        	Cursor c = mDbHelper.fetchLocation(latitude, longitude);
		        String location = c.getString(c.getColumnIndex(eventDB.KEY_LOCATION));
				textView1.setText("Location: " + location);	        	
	        }
	        return rowView;
	    }
}