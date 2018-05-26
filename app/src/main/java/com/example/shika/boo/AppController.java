package com.example.shika.boo;
import android.app.Application;
import android.content.Intent;
import android.util.Log;


public class AppController extends Application {

    private static AppController mInstance;
    public void onCreate() {

        super.onCreate();

        mInstance = this;
        startService(new Intent(this, BackgroundMapService.class));
        Log.e("appController","run succ");
    }
    public static synchronized AppController getInstance() {
        return mInstance;
    }
}
