package com.example.shika.boo;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;

import com.google.android.gms.location.LocationListener;

import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Map;

import eu.long1.spacetablayout.SpaceTabLayout;
import models.nearbyoffers;

public class MapsActivity extends BaseActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final String TAG ="LocationActivity" ;
    private GoogleMap mMap;
    Marker marker;

    private GoogleApiClient googleApiClient;
    private FrameLayout view_stub; //This is the framelayout to keep your content view
    private NavigationView navigation_view; // The new navigation view from Android Design Library. Can inflate menu resources. Easy
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private FavoritsFragment favoritsFragment;
    InterstsFragment interstsFragment;
    ProfileFragment profileFragment;
    SavedOffersFragment savedOffersFragment;
    Myprof myprof;
    private FragmentB fragmentB;
    private Map fragmentMap;
    Intent too;
    private Menu drawerMenu;
    String[] listItems;
    static Button btn;
    SharedPreferences sharedpreferences;
    SpaceTabLayout tabLayout;
    FragmentManager fragmentManager = getSupportFragmentManager();
    ViewPager viewPager;
    private LocationRequest mLocationRequest;
    ArrayList<nearbyoffers> NearOffers;
    private BottomNavigationView bottomNavigationView;
    private String longtitude;
    private String latitude;
    private static final long INTERVAL = 60000;
    private static final long FASTEST_INTERVAL = 60000;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private Location mCurrentLocation;
    Button Test;
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(MIN_DISTANCE_CHANGE_FOR_UPDATES);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (googleServicesAvailable()) {
            setContentView(R.layout.activity_maps);
            //  initMap();} else {

        }
        createLocationRequest();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        } else {
            showGPSDisabledAlertToUser();
        }


        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }






        fragmentB = new FragmentB();
        profileFragment = new ProfileFragment();
        savedOffersFragment = new SavedOffersFragment();
        //  fragmentMap =  new Map();
        myprof = new Myprof();


        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        //  too = new Intent(this,FavoritsFragment.class);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.inicioItem) {
                    finish();
                    startActivity(getIntent());
                    return true;
                } else if (item.getItemId() == R.id.camaraItem) {
                    interstsFragment = new InterstsFragment();
                    setFragment(interstsFragment);
                    return true;
                } else if (item.getItemId() == R.id.favoritosItem) {
                    favoritsFragment = new FavoritsFragment();
                    setFragment(favoritsFragment);
                    return true;
                } else if (item.getItemId() == R.id.perfilItem) {
                    setFragment(myprof);
                    // setFragment(savedOffersFragment);
                    return true;
                }

                return true;
            }
        });

    }


    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }


    @Override



    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            onConnected(null);
        } else {
            Toast.makeText(MapsActivity.this, "No Permitions Granted", Toast.LENGTH_SHORT).show();
        }
    }


    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }



    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment,"fragment");
        fragmentTransaction   .addToBackStack(null)
                .commit();
    }

    private void setFragments(android.support.v4.app.Fragment frag){
        android.support.v4.app.FragmentTransaction fragmentTrans= getSupportFragmentManager().beginTransaction();

        fragmentTrans.replace(R.id.main_frame,frag);
        fragmentTrans.commit();
    }



    public boolean googleServicesAvailable(){
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if(isAvailable == ConnectionResult.SUCCESS){
            return true;
        }else if(api.isUserResolvableError(isAvailable)){
            Dialog dialog = api.getErrorDialog(this,isAvailable,0);
            dialog.show();
        }else{
            Toast.makeText(this, "can't connect to play services", Toast.LENGTH_LONG).show();
        }
        return false;
    }



    private void goToLocation(double lat , double lng){
        LatLng ll = new LatLng(lat , lng);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(ll);
        mMap.moveCamera(cameraUpdate);
    }


    public boolean onNavigationItemSelected(MenuItem menuitem) {
        Intent x;
        Class fragmentClass;
        int id = menuitem.getItemId();
        if(id == R.id.set){

            x = new Intent(this,Setting.class);
            startActivity(x);}
        else if(id == R.id.ctg){
            x = new Intent(this, MainActivity.class);
            startActivity(x);}
       /* else if(id == R.id.likes){
            x = new Intent(this, SavedOffersFragment.class);
            startActivity(x);}
        else if(id == R.id.prize){
            x = new Intent(this, RewardsActivity.class);
            startActivity(x);}*/
        else if(id == R.id.login){
            sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);;
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.commit();
            AppController.getInstance().ServiceStart();
            x = new Intent(this, AfterBegin.class);
            x.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(x);

        }

        /*else if(id == R.id.pro){
            x = new Intent(this, Profile.class);
            startActivity(x);}*/
        else if(id == R.id.main){
            x = new Intent(this, MapsActivity.class);
            startActivity(x);}

        return false;

    }

    private void setupdrawercontent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            public boolean onNavigationItemSelected(MenuItem item){
                onNavigationItemSelected(item);
                return true;
            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }



    public void onConnectionSuspended(int i) {

    }
    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }
    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }
    @Override
    public void onResume() {
        super.onResume();
        if (googleApiClient.isConnected()) {
            startLocationUpdates();
            Log.d(TAG, "Location update resumed .....................");
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop fired ..............");
        googleApiClient.disconnect();
        Log.d(TAG, "isConnected ...............: " + googleApiClient.isConnected());
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Connection failed: " + connectionResult.toString());
    }
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        } else {
            Location userCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (userCurrentLocation != null) {
                startLocationUpdates();

            }

        }

    }

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, mLocationRequest, this);
        Log.d(TAG, "Location update started ..............: ");
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        updateUI();



    }

    private void updateUI() {
        Log.d(TAG, "UI update initiated .............");
        if (mCurrentLocation != null) {
            String latitude = String.valueOf(mCurrentLocation.getLatitude());
            String longtitude = String.valueOf(mCurrentLocation.getLongitude());
            LatLng latLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
            mMap.clear();

            mMap.addMarker(new MarkerOptions().position(latLng));

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));


        }
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                googleApiClient, this);
        Log.d(TAG, "Location update stopped .......................");
    }





}