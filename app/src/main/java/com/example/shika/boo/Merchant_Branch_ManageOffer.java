package com.example.shika.boo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Merchant_Branch_ManageOffer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant__branch__manage_offer);

        Button b1 = (Button) findViewById(R.id.button4);
        Button b2 = (Button) findViewById(R.id.button5);

        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent ino = new Intent(Merchant_Branch_ManageOffer.this,Merchant_add_offer.class);
                startActivity(ino);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent ino = new Intent(Merchant_Branch_ManageOffer.this,Merchant_Offers.class);
                startActivity(ino);
            }
        });
    }
}
