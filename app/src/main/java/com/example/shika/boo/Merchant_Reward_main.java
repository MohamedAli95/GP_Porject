package com.example.shika.boo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Merchant_Reward_main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant__reward_main);


        Button btn = (Button) findViewById(R.id.pts);
        Button btn1 = (Button) findViewById(R.id.rew);


        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent ino = new Intent(Merchant_Reward_main.this,Merchant_points.class);
                startActivity(ino);
            }
        });


        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent ino = new Intent(Merchant_Reward_main.this,Merchant_RewardsSystem.class);
                startActivity(ino);
            }
        });

    }
}
