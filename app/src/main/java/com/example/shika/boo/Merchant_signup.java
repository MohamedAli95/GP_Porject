package com.example.shika.boo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class Merchant_signup extends AppCompatActivity {

    String[] Gender = {"male", "female"};
    String[] location = {"cairo", "Alexandria", "Aswan", "Sinai", "Giza"};
    Button siup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, Gender);
        MaterialBetterSpinner betterSpinner = (MaterialBetterSpinner) findViewById(R.id.sp1);
        betterSpinner.setAdapter(arrayAdapter);

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, location);
        //MaterialBetterSpinner betterSpinner2 = (MaterialBetterSpinner) findViewById(R.id.sp2);
        //betterSpinner2.setAdapter(arrayAdapter2);

        siup = (Button) findViewById(R.id.button_signup);
    }

    public void to_Home(View v) {
        Intent next = new Intent(this, MainActivity.class);
        startActivity(next);
    }
}