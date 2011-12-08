package com.example.android.eventreminder;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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

/* This is the class for main screen. It lists scheduled events.
 * The user can view scheduled tasks on the main screen. He can add new event using menu.  
 * It creates "options menu" to add new event and for help screen.
 * It also creates context menu to delete event.  It starts AddReminder activity to add new event 
 * and to edit event. For edit event, it passes rowId of an event as an extra information to
 * AddReminder activity. */

public class MainActivity extends ListActivity {
	private static final int ACTIVITY_CREATE=0;
	private static final int ACTIVITY_EDIT=1;
	
	private eventDB mDbHelper;
	String RowId;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mDbHelper = new eventDB(this);
        mDbHelper.open();
        fillData();
        /* handle long-clicks or long-presses */
        registerForContextMenu(getListView());
    }//end of OnCreate
        
    
    private void fillData() {
    	Cursor remindersCursor = mDbHelper.fetchAllEvents();
    	startManagingCursor(remindersCursor);
    	// an array for the fields to show in the list row (now only the TITLE)
    	String[] from = new String[]{eventDB.KEY_TITLE}; 
    	// and an array of the fields for each list row
    	int[] to = new int[]{R.id.eventrow}; 
    	// a simple cursor adapter and set it to display
    	SimpleCursorAdapter reminders =
    	new SimpleCursorAdapter(this, R.layout.list_row,
    	remindersCursor, from, to); 
    	setListAdapter(reminders); 
    }
    
    /*Method for short clicks*/
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    super.onListItemClick(l, v, position, id);
    /*Start Edit Activity*/
    Intent intent = new Intent(this, AddReminder.class);
    intent.putExtra(eventDB.KEY_ROWID, id);
    startActivityForResult(intent, ACTIVITY_EDIT);
    }//end of onListItemClick
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    MenuInflater mi = getMenuInflater();
    mi.inflate(R.menu.menu, menu);
    return true;
    }
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) { 
    switch(item.getItemId()) {
    case R.id.addEvent:
    	//Intent intent = new Intent(this, AddReminder.class);
        //startActivity(intent);
        createReminder();
    	return true;
    }
    return super.onMenuItemSelected(featureId, item);
    }
    
    /*Add new Event*/
    private void createReminder() {
    Intent i = new Intent(this, AddReminder.class);
    /* Result  for when the activity is completed */
     startActivityForResult(i, ACTIVITY_CREATE);
    }
    
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
    ContextMenuInfo menuInfo) {
    super.onCreateContextMenu(menu, v, menuInfo);
    MenuInflater mi = getMenuInflater();
    mi.inflate(R.menu.list_item_longpress, menu);
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
    	case R.id.menu_delete:
    		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    			mDbHelper.deleteReminder(info.id); 
    			fillData();
    		
    		return true;
    	}
    	return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
    super.onActivityResult(requestCode, resultCode, intent);
    fillData();
    }
}