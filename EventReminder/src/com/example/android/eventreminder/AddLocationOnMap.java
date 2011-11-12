package com.example.android.eventreminder;

import android.app.Activity;
import android.os.Bundle;
import com.google.android.maps.MapActivity;

public class AddLocationOnMap extends MapActivity{
	 @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	  }
	  
	  @Override
	  protected boolean isRouteDisplayed() {
	    return false;
	  }

}
