package com.example.shika.boo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Setting extends AppCompatActivity {
    String Settingurl = "http://gp.sendiancrm.com/offerall/Settingupdate.php";

    RadioButton allnotfications;
    RadioButton MuteNotfications;
    RadioButton InteristsNotfications;
    SharedPreferences sharedpreferences;
    int user_id;
    String Setting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        allnotfications= (RadioButton) findViewById(R.id.allnotf);
        MuteNotfications= (RadioButton) findViewById(R.id.mutednotfications);
        InteristsNotfications= (RadioButton) findViewById(R.id.intristsnotf);
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
        user_id=sharedpreferences.getInt("Id",0);
        Setting = sharedpreferences.getString("Setting","null");
        if(Setting.equals("Get all Notfications")){
            allnotfications.setChecked(true);
        }
        else if(Setting.equals("According to Intersts")){
            InteristsNotfications.setChecked(true);
        }
        else{
            MuteNotfications.setChecked(true);
        }
    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.allnotf:
                if (checked) {
                    Setting = allnotfications.getText().toString();
                    Updatesetting(Setting, user_id);
                }
                    break;
            case R.id.mutednotfications:
                if (checked) {
                    Setting= MuteNotfications.getText().toString();
                    Updatesetting(Setting,user_id);
                }
            case R.id.intristsnotf:
                if (checked) {
                    Setting = InteristsNotfications.getText().toString();
                    Updatesetting(Setting, user_id);
                }

                    break;
        }
    }
    public void Updatesetting(final String Setting,final int user_id ) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Settingurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Setting.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                Log.e("errorsdsd",error.getMessage());
            }

        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("setting",""+Setting);
                params.put("user_id",""+user_id);


                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    protected void onDestroy() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        if(allnotfications.isChecked()){
            allnotfications.setChecked(true);
            editor.remove("Setting");
            editor.putString("Setting","Get all Notfications");
            editor.commit();
            Intent x = new Intent(this, BackgroundMapService.class);
            stopService(x);
            startService(x);
        }
        else if(InteristsNotfications.isChecked()){
            InteristsNotfications.setChecked(true);
            editor.remove("Setting");
            editor.putString("Setting","According to Intersts");
            editor.commit();
            Intent x = new Intent(this, BackgroundMapService.class);
            stopService(x);
            startService(x);
        }
        else{
            MuteNotfications.setChecked(true);
            editor.remove("Setting");
            editor.putString("Setting","Mute Notfications");
            editor.commit();
            Intent x = new Intent(this, BackgroundMapService.class);
            stopService(x);
        }

        super.onDestroy();
    }

    @Override
    protected void onResume() {
        if(Setting.equals("Get all Notfications")){
            allnotfications.setChecked(true);
        }
        else if(Setting.equals("According to Intersts")){
            InteristsNotfications.setChecked(true);
        }
        else{
            MuteNotfications.setChecked(true);
        }
        super.onResume();

    }


}
