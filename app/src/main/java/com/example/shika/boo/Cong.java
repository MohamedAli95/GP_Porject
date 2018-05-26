package com.example.shika.boo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class Cong extends AppCompatActivity {

    private TextView infoTextView;
    private BottomNavigationView bottomNavigationView;
    Intent too;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cong);


        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
       too = new Intent(this,MapsActivity.class);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.inicioItem) {
                          return true;
                }else if (item.getItemId() == R.id.camaraItem) {
                    return true;
                } else if (item.getItemId() == R.id.favoritosItem) {
                    startActivity(too);
                } else if (item.getItemId() == R.id.perfilItem) {
                    return true;
                }

                return true;
            }
        });
    }
}
