package com.example.shika.boo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class Merchant_Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant__home);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.tomen);
       // LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.toreward);

        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.topro);
        LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.tobranches);
        LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.toreport);

       // LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.tomenu);


        linearLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent ino = new Intent(Merchant_Home.this,Merchant_After_menu.class);
                startActivity(ino);
            }
        });

        linearLayout1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent inoz = new Intent(Merchant_Home.this,Merchant_Branches_list.class);
                startActivity(inoz);
            }
        });

        linearLayout3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent ino = new Intent(Merchant_Home.this,Merchant_Add_Branch.class);
                startActivity(ino);
            }
        });

       /* linearLayout1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent ino = new Intent(Merchant_Home.this,Merchant_Reward_main.class);
                startActivity(ino);
            }
        });*/

        linearLayout2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent ino = new Intent(Merchant_Home.this,Merchant_Profile.class);
                startActivity(ino);
            }
        });

        /*linearLayout3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent ino = new Intent(Merchant_Home.this,Merchant_addmenu.class);
                startActivity(ino);
            }
        });*/
    }
}
