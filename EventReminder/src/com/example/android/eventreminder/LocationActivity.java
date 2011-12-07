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
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class LocationActivity extends ListActivity {
	private static final int ACTIVITY_CREATE=0;
	private static final int ACTIVITY_EDIT=1;
	
	private eventDB mDbHelper;
	private Long mRowId;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
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
    	new SimpleCursorAdapter(this, R.layout.list_row,
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

}