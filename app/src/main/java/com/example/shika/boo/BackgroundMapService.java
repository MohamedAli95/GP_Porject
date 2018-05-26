package com.example.shika.boo;



import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import models.nearbyoffers;

public class BackgroundMapService extends Service implements LocationListener {



    // flag for GPS status
    boolean isGPSEnabled = false;
    Handler handler = new Handler();
    // flag for network status
    boolean isNetworkEnabled = false;
    private static final String TAG = "MyBackgroundService";
    // flag for GPS status
    boolean canGetLocation = false;

    Location location; // location
    Double latitude; // latitude
    Double longitude; // longitude
    Double latitude1=0.0; // latitude
    Double longitude1=0.0; // longitude
    SharedPreferences sharedpreferences;
    int userid;
    String type;
    ArrayList<nearbyoffers> nearoffers= new ArrayList<nearbyoffers>();
    ArrayList<nearbyoffers> ExistedOffers= new ArrayList<nearbyoffers>();
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 60 * 1000; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;


    public BackgroundMapService() {

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "Service is On.....");
        scheduleSendLocation();
        schedulenearbyoffers();
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;

    }

    public void onCreate() {
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Log.e(TAG, "onCreate");
        if (sharedpreferences.getBoolean("logged in",false)) {


            userid = sharedpreferences.getInt("Id", 0);

        }


        getLocation();
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);

            Log.e(TAG, "getlocation");
            if(latitude!=null&& latitude1!=null &&longitude!=null&& longitude1!=null){
                Log.e(TAG, latitude.toString());
                Log.e(TAG, longitude.toString());
                Log.e(TAG, latitude1.toString());
                Log.e(TAG, longitude1.toString());
            }
            // getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;
                // First get location from Network Provider
                if (!isNetworkEnabled) {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return null;
                    }
                    Log.e(TAG,"enta btygy hnaaa ?!");
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return null;
                        }
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();


                            latitude1 = latitude;
                            longitude1 = longitude;


                        }
                    }
                }

                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        Log.e(TAG, "Location = null");
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                        Log.d(TAG, "GPS Enabled");
                    }
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);

                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();


                        }
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }
    public void scheduleSendLocation() {
        Log.e(TAG,"scheduleSendLocation");
        handler.postDelayed(new Runnable() {

            public void run() {
                getLocation();
                if(latitude.equals(latitude1) && longitude.equals(longitude1) ){
                    Log.e(TAG,"save location (network) about to start");
                    new savelocation().execute();


                }

                handler.postDelayed(this, 600* 1000);
            }
        }, 600 * 1000);
    }
    public void schedulenearbyoffers() {
        Log.e(TAG,"schedulenearbyoffers");
        handler.postDelayed(new Runnable() {

            public void run() {
                getLocation();
                type = "get nearby offers";
                new savelocation().execute(type);

                handler.postDelayed(this, 60 * 1000);
            }
        }, 60 * 1000);
    }


    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     * */

    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(BackgroundMapService.this);
        }
    }

    /**
     * Function to get latitude
     * */

    public Double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();

        }

        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     * */

    public Double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    /**
     * Function to check GPS/wifi enabled
     * @return boolean
     * */

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     * */

   /* public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
*/
    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
    private class savelocation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            Log.e(TAG,"Save Location Started");
            String login_url = "http://10.0.2.2:8090/offerall/UpdateUserLoction.php";
            String getnearbyoffers = "http://10.0.2.2:8090/offerall/NearByOffers.php";



            if(latitude!=null&&longitude!=null&&userid!=0 && type.equals("savelocation")) {
                try {

                    Log.e(TAG, "if in save location entered ");
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("longitude", "UTF-8") + "=" + URLEncoder.encode(longitude.toString(), "UTF-8") + "&"
                            + URLEncoder.encode("latitude", "UTF-8") + "=" + URLEncoder.encode(latitude.toString(), "UTF-8")+ "&"
                            + URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(userid), "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String result = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(type.equals("get nearby offers")){
                try {

                    Log.e(TAG, "we are hereeee");
                    URL url = new URL(getnearbyoffers);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    /*OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("longitude", "UTF-8") + "=" + URLEncoder.encode(longitude.toString(), "UTF-8") + "&"
                            + URLEncoder.encode("latitude", "UTF-8") + "=" + URLEncoder.encode(latitude.toString(), "UTF-8")+ "&"
                            + URLEncoder.encode("userid", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(userid), "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();*/
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String result = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }
                    Log.e(TAG, result);
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }




            return null;
        }

        protected void onPostExecute(String result) {

            JSONParser parser = new JSONParser();

            if (type.equals("get nearby offers")) {
                try {
                    if(result.equals("no offers found")){

                    }
                    else {

                        Object obj = parser.parse(result);
                        JSONArray array= (JSONArray) obj;
                        JSONObject offer_obj ;
                        nearoffers = new ArrayList<nearbyoffers>();
                        nearbyoffers offer;
                        for (int i =0 ; i<array.size();i++)
                        {
                            offer_obj= (JSONObject) array.get(i);
                            offer=new nearbyoffers();
                            offer.setBranch_name((String) offer_obj.get("Branch_name"));
                            offer.setTitle((String) offer_obj.get("Title"));
                            offer.setPlacename((String) offer_obj.get("PlaceName"));
                            offer.setLatitude(Double.parseDouble(offer_obj.get("latitude").toString()));
                            offer.setLongitude(Double.parseDouble(offer_obj.get("longitude").toString()));
                            nearoffers.add(offer);
                        }

                        CompareNearOffers();


                    }




                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }

        protected  void CompareNearOffers(){
            getLocation();
            Location templocation;
            Float distance;
            for(int i=0;i<nearoffers.size();i++)
            {

                templocation=new Location("");
                templocation.setLatitude(nearoffers.get(i).getLatitude());
                templocation.setLongitude(nearoffers.get(i).getLongitude());
                distance= templocation.distanceTo(location);
                if(distance<2000)
                {
                    ExistedOffers.add(nearoffers.get(i));
                }

            }

            Gson gson = new Gson();
            String json = gson.toJson(ExistedOffers);

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.remove("ExistedOffers");
            editor.putString("ExistedOffers", json);

            editor.commit();




        }
    }
}