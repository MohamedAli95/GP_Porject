package com.example.shika.boo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class AfterBegin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                Intent ino = new Intent(AfterBegin.this,Merchant_Home.class);
                startActivity(ino);
            }
        });
    }
}
