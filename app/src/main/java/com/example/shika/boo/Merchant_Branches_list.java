package com.example.shika.boo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.varunest.sparkbutton.SparkButton;

import java.util.ArrayList;
import java.util.List;

public class Merchant_Branches_list extends AppCompatActivity {


    SparkButton sparkButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant__branches_list);



        Button toaddbran = (Button) findViewById(R.id.addbran);

        toaddbran.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent in = new Intent(Merchant_Branches_list.this,Merchant_Add_Branch.class);
                startActivity(in);
            }
        });

    }
    public void toValidate(View vi){
        Intent ba = new Intent(Merchant_Branches_list.this,Merchant_Branch_Pass.class);
        startActivity(ba);
    }
}
