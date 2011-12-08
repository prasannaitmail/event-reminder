package com.example.android.eventreminder;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class HelloItemizedOverlay extends ItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;
	private Drawable mdefaultMarker;
	private GeoPoint p1;
	
	public HelloItemizedOverlay(Drawable defaultMarker) {
		  super(boundCenterBottom(defaultMarker));
		  mdefaultMarker = defaultMarker;
		// TODO Auto-generated constructor stub
	}
	
	public void addOverlay(OverlayItem overlay) {
		mOverlays.clear();
	    mOverlays.add(overlay);
	    populate();
	}
	
	@Override
	protected OverlayItem createItem(int i) {
	  return mOverlays.get(i);
	}

	@Override
	public int size() {
	  return mOverlays.size();
	}
	
	public HelloItemizedOverlay(Drawable defaultMarker, Context context) 
	{
		super(boundCenterBottom(defaultMarker));
		mContext = context;
		mdefaultMarker = defaultMarker;
	}
	
	
	@Override
	public boolean onTap(GeoPoint p, MapView mapView)
	{
		p1 = p;
		mContext = mapView.getContext();
		System.out.println("OnTap1");
        Toast.makeText(mapView.getContext(), 
                p.getLatitudeE6() / 1E6 + "," + 
                p.getLongitudeE6() /1E6 , 
                Toast.LENGTH_SHORT).show();
		
    	List<Overlay> mapOverlays = mapView.getOverlays();
   	    HelloItemizedOverlay itemizedoverlay = new HelloItemizedOverlay( mdefaultMarker );    
	    OverlayItem overlayitem = new OverlayItem(p, "Hi!", "I'm in SFSU!");
	        	    
	    itemizedoverlay.addOverlay(overlayitem);
	    mapOverlays.clear();
	    mapOverlays.add(itemizedoverlay);
	    mapView.invalidate();
	    
	    AlertDialog.Builder locationDialog = new AlertDialog.Builder(mapView.getContext());
	    locationDialog.setMessage("Are you sure you want to save this location?")
	    .setTitle("Are you sure?")
	    .setCancelable(false) 
	    .setPositiveButton("Yes", 
	    new DialogInterface.OnClickListener() { 
	    public void onClick(DialogInterface dialog, int id) {
	    // Perform some action such as saving the location

	    	//Intent myIntent = new Intent(mContext, LocationPreferences.class);
	    	//mContext.startActivity(myIntent);
	    	
	    	Log.d("TAG","Point is " + p1.getLatitudeE6()+ ": " + p1.getLongitudeE6());
	    	
	    	
		    AlertDialog.Builder NameDialog = new AlertDialog.Builder(mContext);
		    NameDialog.setMessage("Enter Location")
		    .setTitle("Enter Location")
		    .setCancelable(false);
	    	final EditText input = new EditText(mContext);
		    NameDialog.setView(input);
		    
		    NameDialog.setPositiveButton("Yes", 
		    new DialogInterface.OnClickListener() { 
		    public void onClick(DialogInterface dialog, int id) {
		    	String location = input.getText().toString();
		    	//dialog.cancel();

		    	if(location.length() > 0)
		    	{
			    	eventDB mDbHelper = new eventDB(mContext);
			    	String latitude = String.valueOf(p1.getLatitudeE6());
			    	String longitude = String.valueOf(p1.getLongitudeE6());
			    	
			    	mDbHelper.open();
		    		mDbHelper.createLocation(location, latitude, longitude); 
			    	mDbHelper.close();
			    	((AddLocationOnMap) mContext).finish();
		    	}
		    	}
		    })
		    .setNegativeButton("No", new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int id) {
		    dialog.cancel(); 
		    }
		    });
		    NameDialog.create().show();	    	

	    	//dialog.cancel();  
	    	}
	    })
	    .setNegativeButton("No", new DialogInterface.OnClickListener() {
	    public void onClick(DialogInterface dialog, int id) {
	    dialog.cancel(); 
	    }
	    });
	    locationDialog.create().show();
		  return true;
	}
	
}
