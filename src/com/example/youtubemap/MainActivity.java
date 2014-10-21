package com.example.youtubemap;

import java.io.IOException;
import java.util.List;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.app.Dialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends FragmentActivity implements
	GooglePlayServicesClient.ConnectionCallbacks,
	GooglePlayServicesClient.OnConnectionFailedListener, LocationListener {
	
	private static final int GPS_ERRORDAILOG_REQUEST = 9001;
//	private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9002;
	GoogleMap mMap;
    
	private static final float DEFAULTZOOM = 15;
	
	LocationClient mLocationClient;
	Marker marker;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        
        if (servicesOK()) {       	   	
        	setContentView(R.layout.activity_mapview);
        	
//        	mMapView = (MapView) findViewById(R.id.map);       
//        	mMapView.onCreate(savedInstanceState);
        	
        	if (initMap()) {
        		Toast.makeText(this, "Ready to map!", Toast.LENGTH_SHORT).show();
        		mMap.setMyLocationEnabled(true);
        		mLocationClient = new LocationClient(this, this, this);
        		mLocationClient.connect();
        	}
        	else {
        		Toast.makeText(this, "Map not available!", Toast.LENGTH_SHORT).show();
        	}
        }
        else {
        	setContentView(R.layout.activity_main);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
//    	getMenuInflater().inflate(R.menu.main, menu);    	
//        MenuItem searchItem = menu.findItem(R.id.editText1);
//        SearchView searchView = (SearchView) searchItem.getActionView();
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
//        searchView.setSearchableInfo(info);
        
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView = (SearchView) menu.findItem(R.id.txtSearch).getActionView();
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        return true;
    	return super.onCreateOptionsMenu(menu);
    }
    
    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {    	
    	switch (item.getItemId()) {
    	case R.id.mapTypeNone:
    		mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
    		break;
    	case R.id.mapTypeNormal:
    		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    		break;
    	case R.id.mapTypeSatellite:
    		mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    		break;
    	case R.id.mapTypeTerrain:
    		mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    		break;
    	case R.id.mapTypeHybrid:
    		mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    	case R.id.myLocation:
    		gotoCurrentLocation();
    		break;
    	default:
    		break;
    	}
    	
    	return super.onOptionsItemSelected(item);
    }
    
    public boolean servicesOK() {
    	int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
    	
    	if (isAvailable == ConnectionResult.SUCCESS) {
    		return true;
    	}
    	else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {
    		Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable, this, GPS_ERRORDAILOG_REQUEST);
    		dialog.show();    		
    	}
    	else {
    		Toast.makeText(this, "Can't connect to Google Play services", Toast.LENGTH_SHORT).show();
    	}
    	return false;
    }
    
    private boolean initMap() {
    	if (mMap == null) { 
    		SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    		
    		mMap = mapFrag.getMap();
    		mMap.getUiSettings().setMyLocationButtonEnabled(false);
    		mMap.getUiSettings().setZoomControlsEnabled(false);
    		mMap.getUiSettings().setCompassEnabled(false);
    	
    	}
    	return (mMap != null);
    }

    /*
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mMapView.onDestroy();
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
		mMapView.onLowMemory();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mMapView.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mMapView.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);
	}	
	*/
    
    private void gotoLocation(double lat, double lng, float defZoom) {
    	LatLng ll = new LatLng(lat, lng);
    	CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll,defZoom);
    	mMap.moveCamera(update);    	
    }
    
    public void geoLocate(View v) throws IOException {
    	    /*	
    	EditText et = (EditText) findViewById(R.id.editText1);
    	String location = et.getText().toString();
    	if (location.length() == 0) {
    		Toast.makeText(this, "Please enter a location", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	
    	hideSoftKeyboard(v);
    	
    	Geocoder gc = new Geocoder(this);
    	List<Address> list = gc.getFromLocationName(location, 1);
    	Address add = list.get(0);
    	String locality = add.getLocality();
    	
    	Toast.makeText(this, locality, Toast.LENGTH_LONG).show();    	
    	
    	double lat = add.getLatitude();
    	double lng = add.getLongitude();
    	
    	gotoLocation(lat, lng, DEFAULTZOOM);
    	
//    	if (marker != null) {
//    		marker.remove();
//    	}
//    	
//    	MarkerOptions options = new MarkerOptions()
//    	.title(locality)
//    	.position(new LatLng(lat, lng));
//    	marker = mMap.addMarker(options);
    	
    	setMarker(locality, lat, lng);
    	*/
    }
    
    private void hideSoftKeyboard(View v) {
    	InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    	imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
    
   @Override
    protected void onStop() {
    	super.onStop();
    	MapStateManager mgr = new MapStateManager(this);
    	mgr.saveMapState(mMap);    	
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	MapStateManager mgr = new MapStateManager(this);
    	CameraPosition position = mgr.getSavedCameraPosition();
    	if (position != null) {
    		CameraUpdate update = CameraUpdateFactory.newCameraPosition(position);
    		mMap.moveCamera(update);
    	}
    }
    
    protected void gotoCurrentLocation() {
    	Location currentLocation = mLocationClient.getLastLocation();
    	if (currentLocation == null) {
    		Toast.makeText(this, "Current location isn't available", Toast.LENGTH_LONG).show();    		
    	}
    	else {
    		LatLng ll = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
    		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, DEFAULTZOOM);
    		mMap.animateCamera(update);
    		
    		setMarker("Current Location", currentLocation.getLatitude(), currentLocation.getLongitude());
    	}
    }
    
    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
    	
    }
    
    @Override
    public void onConnected(Bundle arg0) {
    	Toast.makeText(this, "Connected to location service", Toast.LENGTH_SHORT).show();
    	
    	LocationRequest request = LocationRequest.create();
    	request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    	request.setInterval(60000);
    	request.setFastestInterval(1000);
    	
    	mLocationClient.requestLocationUpdates(request, this);    	
    }
    
    @Override
    public void onDisconnected() {    	
    }

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		String msg = "Location: " + location.getLatitude() + ", " + location.getLongitude();
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
	private void setMarker(String locality, double lat, double lng) {
		if (marker != null) {
			marker.remove();			
		}
		
		MarkerOptions options = new MarkerOptions()
		.title(locality)
		.position(new LatLng(lat, lng))
		.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_flag));
//		.icon(BitmapDescriptorFactory.defaultMarker(
//				BitmapDescriptorFactory.HUE_ORANGE));
		
		marker = mMap.addMarker(options);
	}
	
	   /* The click listner for ListView in the navigation drawer */
//    private class DrawerItemClickListener implements ListView.OnItemClickListener {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            selectItem(position);
//        }
//    }
}
