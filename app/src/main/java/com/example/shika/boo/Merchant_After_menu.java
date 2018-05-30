package com.example.shika.boo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Merchant_After_menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant__after_menu);

        Button bm = (Button) findViewById(R.id.button4);
        Button b = (Button) findViewById(R.id.button5);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent ino = new Intent(Merchant_After_menu.this,MerchantMenu.class);
                startActivity(ino);
            }
        });



        bm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent ino = new Intent(Merchant_After_menu.this,Merchant_Upload_menu.class);
                startActivity(ino);
            }
        });
    }
}
