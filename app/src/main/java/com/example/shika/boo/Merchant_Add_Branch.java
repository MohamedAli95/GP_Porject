package com.example.shika.boo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;

import java.util.HashMap;
import java.util.Map;
import android.app.ProgressDialog;
import android.os.Bundle;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;
import android.widget.EditText;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.HashMap;
import java.util.Map;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import java.util.HashMap;
import java.util.Map;

public class Merchant_Add_Branch extends AppCompatActivity {

    private final int REQUEST_CODE_PLACEPICKER = 1;
    double  latitude,longitude  ;
    // Creating EditText.
    EditText FirstName, LastName, Email ;
    MaterialBetterSpinner betterSpinner;
    // Creating button;
    Button InsertButton;
    int strSavedMem1;
    // Creating Volley RequestQueue.
    RequestQueue requestQueue;

    // Create string variable to hold the EditText Value.
    String FirstNameHolder, LastNameHolder, EmailHolder , RewardHolder , lat , lon ;
    String[] Reward = {"UnAvailable","Available"};
    // Creating Progress dialog.
    ProgressDialog progressDialog;

    // Storing server url into String variable.
    String HttpUrl = "http://gp.sendiancrm.com/offerall/Add_branch.php";
    String  ses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant__add__branch);

        // Assigning ID's to EditText.
        FirstName = (EditText) findViewById(R.id.bname);
        LastName = (EditText) findViewById(R.id.bpass);
        Email = (EditText) findViewById(R.id.bphone);

        SharedPreferences sharedPreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(this);
        // strSavedMem1 = sharedPreferences.getString("Name", "");
        strSavedMem1 = sharedPreferences.getInt("PID",0);
        ses = Integer.toString(strSavedMem1);


        Button bu = (Button) findViewById(R.id.pickup_btn);
        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlacePickerActivity();
            }
        });
        bu.setText(Integer.toString(strSavedMem1));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,Reward);
        betterSpinner = (MaterialBetterSpinner) findViewById(R.id.sp1);
        betterSpinner.setAdapter(arrayAdapter);

        // Assigning ID's to Button.
        InsertButton = (Button) findViewById(R.id.addbtn);

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(Merchant_Add_Branch.this);

        progressDialog = new ProgressDialog(Merchant_Add_Branch.this);

        // Adding click listener to button.
        InsertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Showing progress dialog at user registration time.
              //  progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
               // progressDialog.show();

                if(validate())
                {
                    addNewBranch();
                }

                // Calling method to get value from EditText.


            }
        });

    }

    public void addNewBranch(){
        GetValueFromEditText();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing response message coming from server.
                        Toast.makeText(Merchant_Add_Branch.this, ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(Merchant_Add_Branch.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.

                params.put("Branch_name", FirstNameHolder);
                params.put("Branch_Password", LastNameHolder);
                params.put("Branch_phone", EmailHolder);
                params.put("RewardSystemAvailabilty", RewardHolder);
                params.put("latitude", lat);
                params.put("longitude", lon);
                params.put("Place_id", ses);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(Merchant_Add_Branch.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    // Creating method to get value from EditText.
    public void GetValueFromEditText(){

        FirstNameHolder = FirstName.getText().toString().trim();
        LastNameHolder = LastName.getText().toString().trim();
        EmailHolder = Email.getText().toString().trim();
        RewardHolder =  betterSpinner.getText().toString();

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

        // String name = placeSelected.getName().toString();
        String address = placeSelected.getAddress().toString();
        latitude = placeSelected.getLatLng().latitude;
        lat = String.valueOf(latitude);
        longitude = placeSelected.getLatLng().longitude;
        lon = String.valueOf(longitude);

        android.widget.TextView enterCurrentLocation = (android.widget.TextView) findViewById(R.id.loctex);
        enterCurrentLocation.setText("location:"+ address);
    }

    protected  void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PLACEPICKER && resultCode == RESULT_OK) {
            displaySelectedPlaceFromPlacePicker(data);
        }
    }



    public boolean validate() {
        boolean valid = true;
        if(FirstName.getText().toString().matches("")||FirstName.length()>32){
            FirstName.setError("Please Enter Valid Name");
            valid=false;
        }
        if(LastName.getText().toString().matches("")||LastName.length()<8){
            LastName.setError("Enter password At Least 8 CharS ");
            valid=false;
        }

        if(Email.getText().toString().matches("")){
            Email.setError("Enter Valid Phone");
            valid=false;
        }

        return valid;
    }
}