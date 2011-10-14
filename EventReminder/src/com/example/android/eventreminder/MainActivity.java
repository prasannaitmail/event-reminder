package com.example.android.eventreminder;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ListActivity {
	private static final int ACTIVITY_CREATE=0;
	String RowId;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /* Create fake data for testing purpose*/
        String[] listItems = new String[] {"Event 1","Event 2","Event 3","Event 4"};
        ArrayAdapter<String> adapter =
        new ArrayAdapter<String>(this, R.layout.list_row, R.id.eventrow, listItems); 
        setListAdapter(adapter);
    }//end of OnCreate
        
    /*Method for short clicks*/
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    super.onListItemClick(l, v, position, id);
    /*Start Edit Activity*/
    Intent intent = new Intent(this, AddReminder.class);
    intent.putExtra(RowId, id);
    startActivity(intent);
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
}