package com.example.android.eventreminder;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class LocationActivity extends ListActivity {
	
	private eventDB mDbHelper;
	private Long mRowId;
	
	private Button mAddLocationButton;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);
        
        mAddLocationButton = (Button) findViewById(R.id.addNewLocation);

    	/* click listener to the Add Event button*/
        mAddLocationButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
		    	Intent intent = new Intent(LocationActivity.this, AddLocationOnMap.class);
		    	LocationActivity.this.startActivity(intent);
			}
		});

		if (mRowId == null) {
		Bundle extras = getIntent().getExtras();
		mRowId = extras != null	? extras.getLong("LocationRowNumber")	: null;
    	Log.d("TAG", "rownumber LocationActivity: " + mRowId );
		}
        
        mDbHelper = new eventDB(this);
        mDbHelper.open();
        fillData();
        /* handle long-clicks or long-presses */
        registerForContextMenu(getListView());
    }//end of OnCreate
        
    
    private void fillData() {
    	Cursor LocationsCursor = mDbHelper.fetchAllLocations();
    	startManagingCursor(LocationsCursor);
    	// an array for the fields to show in the list row (now only the TITLE)
    	String[] from = new String[]{eventDB.KEY_LOCATION}; 
    	// and an array of the fields for each list row
    	int[] to = new int[]{R.id.eventrow}; 
    	// a simple cursor adapter and set it to display
    	SimpleCursorAdapter reminders =
    	new SimpleCursorAdapter(this, R.layout.list_location,
    			LocationsCursor, from, to); 
    	setListAdapter(reminders); 
    }
    
    /*Method for short clicks*/
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    super.onListItemClick(l, v, position, id);
    Intent intent=new Intent();  
    intent.putExtra("LocationRowNumber", id);
    setResult(RESULT_OK, intent);
    finish();
    }//end of onListItemClick
    

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
    super.onActivityResult(requestCode, resultCode, intent);
    fillData();
    }
    
    
	/* Create menu for location */
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	    super.onCreateOptionsMenu(menu);
	    MenuInflater mi = getMenuInflater();
	    mi.inflate(R.menu.location_menu, menu);
	    return true;
	    }
	    
	    @Override
	    public boolean onMenuItemSelected(int featureId, MenuItem item) { 
	    switch(item.getItemId()) {
	    case R.id.addNewLocation:
	    	Intent intent = new Intent(LocationActivity.this, AddLocationOnMap.class);
	    	LocationActivity.this.startActivity(intent);
	    	return true;
	    }
	    return super.onMenuItemSelected(featureId, item);
	    }
	    

		public boolean onPrepareOptionsMenu(Menu menu) {
	    	onPause();
	    	return true;
	    }
	    
	    public void onOptionsMenuClosed(Menu menu)
	    {
	    	onPause();
	    	onResume();
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
		}
		
	    @Override
	    public void onCreateContextMenu(ContextMenu menu, View v,
	    ContextMenuInfo menuInfo) {
	    	super.onCreateContextMenu(menu, v, menuInfo);
	    	MenuInflater mi = getMenuInflater();
	    	mi.inflate(R.menu.list_location_longpress, menu);
	    }
	    
	    @Override
	    public boolean onContextItemSelected(MenuItem item) {
	    	switch(item.getItemId()) {
	    	case R.id.menu_delete:
	    		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    			mDbHelper.deleteLocation(info.id); 
	    			fillData();
	    		
	    		return true;
	    	}
	    	return super.onContextItemSelected(item);
	    }
}