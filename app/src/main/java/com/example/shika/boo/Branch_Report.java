package com.example.shika.boo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


public class Branch_Report extends AppCompatActivity {

    RatingBar ratingBar;

    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.branch_report);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        ratingBar.setRating(Load());

        textView = (TextView) findViewById(R.id.textview);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                textView.setText(""+rating);

                Save(rating);
            }
        });
    }

    public void Save (float r){
        SharedPreferences sharedPreferences = getSharedPreferences("rate",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("rating",r);
        editor.commit();
    }

    public float Load (){
        SharedPreferences sharedPreferences = getSharedPreferences("rate", MODE_PRIVATE);
        float f = sharedPreferences.getFloat("rating", 0f);
        return  f;
    }
}