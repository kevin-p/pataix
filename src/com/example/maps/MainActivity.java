package com.example.maps;

import pataix.geoloc.APosition;
import android.os.Bundle;
import com.google.android.maps.*;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;



public class MainActivity extends MapActivity {
	
	private MapView mapView;
	private MapController mc;
	private GeoPoint location;

	private Button refresh ;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		

		this.setContentView(com.example.maps.R.layout.activity_main);

		mapView = (MapView) this.findViewById(com.example.maps.R.id.mapView);
 		this.mapView.setClickable(true);
 		mc = this.mapView.getController();
 		double latitude = 44.5596380;
 		double longitude = 6.0797580;
 		location = new GeoPoint((int) (latitude * 1000000.0),(int) (longitude * 1000000.0));
 		mc.setCenter(this.location);
 		mc.setZoom(17);
 		mapView.setSatellite(true);
 		mapView.invalidate();
 		
 		refresh  = (Button)findViewById(com.example.maps.R.id.button1);
 		final TextView txtLongitude = (TextView)findViewById(R.id.xlongitude);
 		final TextView txtLatitude = (TextView)findViewById(R.id.xlatitude);
 		txtLatitude.setText(""+latitude);
 		txtLongitude.setText(""+longitude);

 		final APosition pos = new APosition(this);
 		
 		refresh.setOnClickListener(new OnClickListener() 
		{			
			
			@Override
			public void onClick(View v) 
			{
		 		double latitude  = pos.getPosition().getLatitude();
		 		double longitude = pos.getPosition().getLongitude();
				
		 		mc.setCenter(new GeoPoint((int) (latitude * 1000000.0),(int) (longitude * 1000000.0)));
		 		mc.setZoom(17);
		 		
		 		txtLatitude.setText("goooo"+latitude);
		 		txtLongitude.setText(""+longitude);
				
			}
		});
 		
 		mapView.setBuiltInZoomControls(true);
	}

	public GeoPoint getLocation() {
		return location;
	}

	public void setLocation(GeoPoint location) {
		this.location = location;
		this.mc.setCenter(this.location);
		this.mapView.invalidate();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

}
