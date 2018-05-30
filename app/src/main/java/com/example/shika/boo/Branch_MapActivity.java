package com.example.shika.boo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;


public class Branch_MapActivity extends AppCompatActivity  {
    private final int REQUEST_CODE_PLACEPICKER = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch__map);

      Button bo = (Button) findViewById(R.id.go_to_button);
        bo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startPlacePickerActivity();

            }
        });

    }

    private void startPlacePickerActivity() {
        PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
        // this would only work if you have your Google Places API working

        try {
            Intent intent = intentBuilder.build(this);
            startActivityForResult(intent, REQUEST_CODE_PLACEPICKER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displaySelectedPlaceFromPlacePicker(Intent data) {
        Place placeSelected = PlacePicker.getPlace(data, this);

        String name = placeSelected.getName().toString();
        String address = placeSelected.getAddress().toString();

        TextView enterCurrentLocation = (TextView) findViewById(R.id.show_selected_location);
        enterCurrentLocation.setText(name + ", " + address);
    }

    protected  void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PLACEPICKER && resultCode == RESULT_OK) {
            displaySelectedPlaceFromPlacePicker(data);
        }
    }
}