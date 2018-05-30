package com.example.shika.boo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Merchant_Add_Reward extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant__add__reward);

        View Reward = findViewById(R.id.merchant_Reward);
        TextView headerText2 = (TextView) Reward.findViewById(R.id.oftitle);
        TextView headerText3 = (TextView) Reward.findViewById(R.id.point);
        Button bn = (Button) Reward.findViewById(R.id.btn);

        headerText2.setText("Reward Title");
        headerText3.setText("Reward Points");
        bn.setText("Submit Reward");

    }
}
