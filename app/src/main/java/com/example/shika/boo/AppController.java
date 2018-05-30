package com.example.shika.boo;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;


public class AppController extends Application {

    private static AppController mInstance;
    SharedPreferences sharedpreferences;
    Intent Backgroundmapservice;
    public void onCreate() {

        super.onCreate();
        mInstance = this;
        ServiceStart();


    }
    public void ServiceStart(){
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        Backgroundmapservice=new Intent(this, BackgroundMapService.class);
        if (sharedpreferences.getBoolean("logged in",false)) {


            startService(Backgroundmapservice);
            Log.e("appController","run succ");

        }
        else
            stopService(Backgroundmapservice);

    }
    public static synchronized AppController getInstance() {
        return mInstance;
    }
}
