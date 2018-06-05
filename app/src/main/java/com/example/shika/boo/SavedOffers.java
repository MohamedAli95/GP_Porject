package com.example.shika.boo;

import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.varunest.sparkbutton.SparkButton;

import java.util.ArrayList;
import java.util.List;

public class SavedOffers extends AppCompatActivity {

    //a list to store all the products
    List<Product> productList;

    //the recyclerview
    RecyclerView recyclerView;
    SparkButton sparkButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_offers);
       //  sparkButton = (SparkButton) findViewById(R.id.spark_button);
        //    sparkButton.setChecked(true);
       //TextView textView = (TextView) findViewById(R.id.textViewRating);
      // textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        //getting the recyclerview from xml
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        productList = new ArrayList<>();


        //adding some items to our list
       productList.add(
                new Product(
                        1,
                        "T-shirt for Men - Half sleeve",
                        "EL Salam city.. beside bus station",
                        60.6,
                        49.99,
                        R.drawable.one));

        productList.add(
                new Product(
                        1,
                        "Hoodie with pant ",
                        "20 street , EL Maadi",
                        130.2,
                        110.7,
                        R.drawable.two));

        productList.add(
                new Product(
                        1,
                        "Coffe Mix Packet",
                        "14 street , EL Tahrir square",
                        83.8,
                        60.4,
                        R.drawable.three));

        //creating recyclerview adapter
        ProductAdapter adapter = new ProductAdapter(this, productList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }
}