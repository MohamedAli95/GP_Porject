package com.example.shika.boo;



import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.json.JSONException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import models.nearbyoffers;

public class BackgroundMapService extends Service implements LocationListener {

    private static final String userInterests = "http://gp.sendiancrm.com/offerall/userInterests.php";
    String resulto;
    public List<Integer> userinterests = new ArrayList<Integer>();

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
    int startservice_flag;
    String type;
    ArrayList<nearbyoffers> nearoffers= new ArrayList<nearbyoffers>();
    ArrayList<nearbyoffers> ExistedOffers= new ArrayList<nearbyoffers>();
    String Setting;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1 * 1000; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;
    NotificationCompat.Builder notfication;

    public BackgroundMapService() {

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "Service is On.....");
        startservice_flag=1;
        scheduleSendLocation();
        schedulenearbyoffers();
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;

    }

    public void onCreate() {
        startservice_flag=1;
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Log.e(TAG, "onCreate");
        if (sharedpreferences.getBoolean("logged in",false)) {


            userid = sharedpreferences.getInt("Id", 0);
            Setting = sharedpreferences.getString("Setting","null");

        }







        notfication = new NotificationCompat.Builder(this);
        notfication.setAutoCancel(true);
       /* SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove("ExistedOffers");*//*


        editor.commit();*/

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
        if(startservice_flag==1) {

            handler.postDelayed(new Runnable() {

                public void run() {
                    getLocation();
                    if (latitude.equals(latitude1) && longitude.equals(longitude1)) {
                        Log.e(TAG, "save location (network) about to start");
                        new savelocation().execute();


                    }

                    handler.postDelayed(this, 600 * 1000);
                }
            }, 600 * 1000);
        }
        else{
            handler.removeCallbacksAndMessages(null);
        }
    }
    public void schedulenearbyoffers() {
        Log.e(TAG,"schedulenearbyoffers");
        if(startservice_flag==1) {
            handler.postDelayed(new Runnable() {

                public void run() {
                    getLocation();
                    type = "get nearby offers";
                    new savelocation().execute(type);

                    handler.postDelayed(this, 60 * 1000);
                }
            }, 60 * 1000);
        }
        else{
            handler.removeCallbacksAndMessages(null);
        }
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
            String login_url = "http://gp.sendiancrm.com/offerall/UpdateUserLoction.php";
            String getnearbyoffers = "http://gp.sendiancrm.com/offerall/NearByOffers.php";



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
            try {





            if (type.equals("get nearby offers")) {

                    if (result.equals("no offers found")) {

                    } else {


                        JSONArray array = new JSONArray(result);
                        JSONObject offer_obj;
                        nearoffers = new ArrayList<nearbyoffers>();
                        nearbyoffers offer;
                        for (int i = 0; i < array.length(); i++) {
                            offer_obj = (JSONObject) array.get(i);
                            offer = new nearbyoffers();
                            if (offer_obj.has("latitude") && offer_obj.has("Title") && offer_obj.has("longitude") && offer_obj.has("StartDate") && offer_obj.has("PlaceName") && offer_obj.has("EndDate")) {
                                offer.setBranch_name(offer_obj.getString("Branch_name"));
                                offer.setTitle(offer_obj.getString("Title"));
                                offer.setPlacename(offer_obj.getString("PlaceName"));
                                if (offer_obj.has("latitude")&&!offer_obj.getString("latitude").equals("null"))
                                    offer.setLatitude(Double.parseDouble(offer_obj.getString("latitude")));
                                else {
                                    continue;
                                }
                                if (offer_obj.has("longitude")&&!offer_obj.getString("longitude").equals("null"))
                                    offer.setLongitude(Double.parseDouble(offer_obj.getString("longitude")));
                                else {
                                    continue;
                                }
                                offer.setOffer_id(Integer.parseInt(offer_obj.getString("Offer_id")));
                                offer.setStartdate(offer_obj.getString("StartDate"));
                                offer.setEnddate( offer_obj.getString("EndDate"));
                                offer.setNo_ofpoints(Integer.parseInt(offer_obj.getString("points")));
                                offer.setCatgoryid(Integer.parseInt(offer_obj.getString("Category_ID")));
                                offer.setCategory(offer_obj.getString("Name"));
                                nearoffers.add(offer);
                            }
                            else
                                continue;
                        }

                        if(Setting.equals("Get all Notfications")){
                            CompareNearOffers();
                        }
                        else if(Setting.equals("According to Intersts")){
                            try {
                                resulto= new loadInterestsRetrive().execute().get();
                                if (resulto.equals("Date not  found")) {

                                } else {
                                     userinterests = new ArrayList<Integer>();
                                    org.json.JSONArray categories = new org.json.JSONArray(resulto);


                                    for (int i = 0; i < categories.length(); i++) {
                                        org.json.JSONObject categoryobject = categories.getJSONObject(i);
                                        int userinterest = Integer.valueOf(categoryobject.getString("Category_id"));

                                        userinterests.add(userinterest);




                                    }




                                }

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Compareoffersforinterists();

                        }
                        else{
                            Intent x = new Intent(getApplicationContext(), BackgroundMapService.class);
                            stopService(x);
                        }



                    }



            }
        }
            catch (Exception e){
                Toast.makeText(getApplication(),e.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("error backgroundservice",e.toString());
                Log.e("error backgroundservice",e.getMessage());

            }
        }

        protected  void Compareoffersforinterists(){
            getLocation();
            Location templocation;
            Float distance;
            ArrayList<nearbyoffers> newoffers= new ArrayList<nearbyoffers>();
            ArrayList<nearbyoffers> interistsoffers= new ArrayList<nearbyoffers>();
            for(int i=0;i<nearoffers.size();i++){
                for(int j=0;j<userinterests.size();j++){
                    if(nearoffers.get(i).getCatgoryid()==userinterests.get(j)){
                        interistsoffers.add(nearoffers.get(i));
                    }
                }
            }

            for(int i=0;i<interistsoffers.size();i++)
            {

                templocation=new Location("");
                templocation.setLatitude(interistsoffers.get(i).getLatitude());
                templocation.setLongitude(interistsoffers.get(i).getLongitude());
                distance= templocation.distanceTo(location);
                if(distance<2000)
                { int flag =1;
                    if(ExistedOffers.size()==0){
                    ExistedOffers.add(interistsoffers.get(i));
                    newoffers.add(interistsoffers.get(i));
                }else{
                    for(int j=0;j<ExistedOffers.size();j++) {
                        if (ExistedOffers.get(j).getOffer_id() == interistsoffers.get(i).getOffer_id()) {
                           flag=0;
                        }
                    }
                    if(flag==1){
                        ExistedOffers.add(interistsoffers.get(i));
                        newoffers.add(interistsoffers.get(i));
                    }
                    }

                }

            }

            if(newoffers.size()!=0){
                for(int i=0 ;i<newoffers.size();i++){
                    Random rand = new Random();
                    int  n = rand.nextInt(1000) + 1;
                    notfication.setSmallIcon(R.drawable.ngm);
                    notfication.setTicker("this is a ticker");
                    notfication.setWhen(System.currentTimeMillis());
                    notfication.setContentTitle(newoffers.get(i).getTitle());
                    notfication.setContentText("you have an offer from"+" "+newoffers.get(i).getPlacename()+" "+"("+newoffers.get(i).getBranch_name()+")");
                    int offer_id=newoffers.get(i).getOffer_id();
                    Intent intent = new Intent(getApplicationContext(),Offer_Page.class);
                    intent.putExtra("k", offer_id);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                    notfication.setContentIntent(pendingIntent);
                    NotificationManager nm =(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    nm.notify(n,notfication.build());
                }
            }

            Gson gson = new Gson();
            String json = gson.toJson(ExistedOffers);

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.remove("ExistedOffers");
            editor.putString("ExistedOffers", json);

            editor.commit();




        }

        protected void CompareNearOffers(){
            getLocation();
            Location templocation;
            Float distance;
            ArrayList<nearbyoffers> newoffers= new ArrayList<nearbyoffers>();
            for(int i=0;i<nearoffers.size();i++)
            {

                templocation=new Location("");
                templocation.setLatitude(nearoffers.get(i).getLatitude());
                templocation.setLongitude(nearoffers.get(i).getLongitude());
                distance= templocation.distanceTo(location);
                if(distance<2000)
                { int flag =1;
                    if(ExistedOffers.size()==0){
                        ExistedOffers.add(nearoffers.get(i));
                        newoffers.add(nearoffers.get(i));
                    }else{
                        for(int j=0;j<ExistedOffers.size();j++) {
                            if (ExistedOffers.get(j).getOffer_id() == nearoffers.get(i).getOffer_id()) {
                                flag=0;
                            }
                        }
                        if(flag==1){
                            ExistedOffers.add(nearoffers.get(i));
                            newoffers.add(nearoffers.get(i));
                        }
                    }

                }

            }

            if(newoffers.size()!=0){
                for(int i=0 ;i<newoffers.size();i++){
                    Random rand = new Random();
                    int  n = rand.nextInt(1000) + 1;
                    notfication.setSmallIcon(R.drawable.ngm);
                    notfication.setTicker("this is a ticker");
                    notfication.setWhen(System.currentTimeMillis());
                    notfication.setContentTitle(newoffers.get(i).getTitle());
                    notfication.setContentText("you have an offer from"+" "+newoffers.get(i).getPlacename()+" "+"("+newoffers.get(i).getBranch_name()+")");
                    int offer_id=newoffers.get(i).getOffer_id();
                    Intent intent = new Intent(getApplicationContext(),Offer_Page.class);
                    intent.putExtra("k", offer_id);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                    notfication.setContentIntent(pendingIntent);
                    NotificationManager nm =(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    nm.notify(n,notfication.build());
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
    @Override
    public void onDestroy() {
        startservice_flag=0;
        scheduleSendLocation();
        schedulenearbyoffers();
        Log.i(TAG,  "service stopped...");
    }


    private class loadInterestsRetrive extends AsyncTask<String, Void, String> {




        @Override
        protected String doInBackground(String... Strings) {

                try {

                    URL url = new URL(userInterests);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(userid), "UTF-8");
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



            return null;

        }



    }

}