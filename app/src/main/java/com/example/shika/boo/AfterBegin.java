package com.example.shika.boo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.shika.boo.merchant.Merchant_SideMenu;

public class AfterBegin extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(sharedpreferences.getBoolean("logged in",false)) {
            Intent too = new Intent(this, MapsActivity.class);
            startActivity(too);
        }
        else if(sharedpreferences.getBoolean("place logged in",false)){
            Intent too = new Intent(this, SecondHome.class);
            startActivity(too);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_begin);
        getSupportActionBar().hide();

        ImageButton imageButton = (ImageButton) findViewById(R.id.person);
        ImageButton imageButton2 = (ImageButton) findViewById(R.id.merchant);

        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent ino = new Intent(AfterBegin.this,SignIn.class);
                startActivity(ino);
            }
        });


        imageButton2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(AfterBegin.this,sign_in_merchant.class);
                startActivity(i);
            }
        });

    }
}
