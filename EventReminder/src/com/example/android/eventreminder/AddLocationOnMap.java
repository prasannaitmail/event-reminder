package com.example.android.eventreminder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MapController;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.Geocoder;
import android.location.Address;
import com.google.android.maps.Overlay;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.View.OnClickListener;

public class AddLocationOnMap extends MapActivity{
	private MapView mapView;
	private MapController mc;
	private LocationManager locationManager;
	private LocationListener locationListener;
	
	 @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    System.out.println("In Map activity");
	    setContentView(R.layout.map);
	    
	    mapView = (MapView) findViewById(R.id.mapView);
	    	    
	    mapView.setBuiltInZoomControls(true);
	    
	    mc = mapView.getController();
	    mc.setZoom(16); 
        mc = mapView.getController();
	    
    	List<Overlay> mapOverlays = mapView.getOverlays();
	    Drawable drawable = this.getResources().getDrawable(R.drawable.pin_icon);
	    HelloItemizedOverlay itemizedoverlay = new HelloItemizedOverlay(drawable, this);

        String coordinates[] = {"37.723836", "-122.476869"};
        double lat = Double.parseDouble(coordinates[0]);
        double lng = Double.parseDouble(coordinates[1]);
 
        GeoPoint point = new GeoPoint(
            (int) (lat * 1E6), 
            (int) (lng * 1E6));

	    OverlayItem overlayitem = new OverlayItem(point, "Hi!", "I'm in SFSU!");
	    
	    
	    itemizedoverlay.addOverlay(overlayitem);
	    mapOverlays.add(itemizedoverlay);
	    
        mc.animateTo(point);
        mc.setZoom(17);
                
        mapView.invalidate();
	  }
	  
	  @Override
	  protected boolean isRouteDisplayed() {
	    return false;
	  }	  	  
}
