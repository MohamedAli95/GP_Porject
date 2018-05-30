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

    //a list to store all the products
    List<Merchant_Branch_Model> productList;

    //the recyclerview
    RecyclerView recyclerView;
    SparkButton sparkButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant__branches_list);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button toaddbran = (Button) findViewById(R.id.addbran);
        //initializing the productlist
        productList = new ArrayList<>();


        productList.add(
                new Merchant_Branch_Model(
                        "Branch EL-maadi "
                     ));

        productList.add(
                new Merchant_Branch_Model(
                        "ELtahrir square branch "
                ));

        productList.add(
                new Merchant_Branch_Model(
                        "Branch shikaa "
                ));


        //creating recyclerview adapter
        Merchant_Brancheslist_Adapter adapter = new Merchant_Brancheslist_Adapter(this, productList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);


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
