package com.example.shika.boo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Merchant_Branch_Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant__branch__home);

     /*   View home = findViewById(R.id.merchant_home);
        TextView headerText1 = (TextView) home.findViewById(R.id.title);
        TextView headerText2 = (TextView) home.findViewById(R.id.topleft);
        TextView headerText3 = (TextView) home.findViewById(R.id.topright);
        TextView headerText4 = (TextView) home.findViewById(R.id.middleleft);
        TextView headerText5 = (TextView) home.findViewById(R.id.middleright);
        TextView headerText6 = (TextView) home.findViewById(R.id.last);

        headerText1.setText("Branch Home");
        headerText2.setText("Manage Offers");
        headerText3.setText("Branch Report");
        headerText4.setText("Profile");
        headerText5.setText("Manage Rewards");
        headerText6.setText("Logout to place");*/
        Intent iu = getIntent();
        int z = iu.getIntExtra("decide",0);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.tobranches);
        //  LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.tomen);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.toreport);
        LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.topro);

        if(z==2){
            ViewGroup vg = (ViewGroup)(linearLayout3.getParent());
            vg.removeView(linearLayout3);
            linearLayout2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1.0f));
        }
        linearLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent ino = new Intent(Merchant_Branch_Home.this,Merchant_Branch_ManageOffer.class);
                startActivity(ino);
            }
        });



        linearLayout2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent ino = new Intent(Merchant_Branch_Home.this,Merchant_Branch_Profile.class);
                startActivity(ino);
            }
        });


        linearLayout3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent ino = new Intent(Merchant_Branch_Home.this,Merchant_Reward_main.class);
                startActivity(ino);
            }
        });
    }
}
