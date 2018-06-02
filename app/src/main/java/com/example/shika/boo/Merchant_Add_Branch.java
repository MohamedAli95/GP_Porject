package com.example.shika.boo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class Merchant_Add_Branch extends AppCompatActivity {
    String[] Reward = {"UnAvailable","Available"};
    private EditText ET_email ,ET_password, ET_BrandName ;
    private RequestQueue requestQueue ;
    private StringRequest request ;
    private static String registerPlaceURL = "http://gp.sendiancrm.com/offerall/registerPlace.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant__add__branch);

        Button bu = (Button) findViewById(R.id.pickup_btn);
        bu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent ino = new Intent(Merchant_Add_Branch.this,Branch_MapActivity.class);
                startActivity(ino);
            }
        });


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,Reward);
        MaterialBetterSpinner betterSpinner = (MaterialBetterSpinner) findViewById(R.id.spenner);
        betterSpinner.setAdapter(arrayAdapter);


    }
}
