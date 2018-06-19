package com.example.shika.boo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
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

import java.util.ArrayList;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Merchant_Branch_AddPoints extends AppCompatActivity {

    EditText FirstName, LastName, Email ;
 MaterialBetterSpinner betterSpinner;
    // Creating button;
    Button InsertButton;
    int strSavedMem;
String [] s_name,s_gender,va;
    // Creating Volley RequestQueue.
    RequestQueue requestQueue;
    Map<String,String> params;
    // Create string variable to hold the EditText Value.
    String FirstNameHolder, LastNameHolder, EmailHolder , RewardHolder , lat , lon ;
    // Creating Progress dialog.
    ProgressDialog progressDialog;
    Spinner spinner;
    String URL="http://gp.sendiancrm.com/offerall/Users_ids.php";
    ArrayList<String> CountryName;
    // Storing server url into String variable.
    String HttpUrl = "http://gp.sendiancrm.com/offerall/Add_userpoints.php";
String  ses,ID;
int pos;
    String trick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant__branch__add_points);

         // Assigning ID's to EditText.
      //  FirstName = (EditText) findViewById(R.id.uid);
        LastName = (EditText) findViewById(R.id.poin);

       SharedPreferences sharedPreferences = android.preference.PreferenceManager.getDefaultSharedPreferences(this);
       // strSavedMem1 = sharedPreferences.getString("Name", "");
         strSavedMem = sharedPreferences.getInt("BId",0);
          ses = Integer.toString(strSavedMem);

                         // Assigning ID's to Button.
        InsertButton = (Button) findViewById(R.id.addpoin);

        CountryName=new ArrayList<>();
        spinner=(Spinner)findViewById(R.id.country_Name);
        loadSpinnerData(URL);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              pos=  spinner.getSelectedItemPosition();
            trick =s_gender[pos];
            //    ID=   spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
              //  Toast.makeText(getApplicationContext(),country,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(Merchant_Branch_AddPoints.this);

        progressDialog = new ProgressDialog(Merchant_Branch_AddPoints.this);

        // Adding click listener to button.
        InsertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Showing progress dialog at user registration time.
                progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
                progressDialog.show();

                // Calling method to get] value from EditText.
                GetValueFromEditText();

                // Creating string request with post method.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String ServerResponse) {

                                // Hiding the progress dialog after all task complete.
                                progressDialog.dismiss();

                                // Showing response message coming from server.
                                Toast.makeText(Merchant_Branch_AddPoints.this, ServerResponse, Toast.LENGTH_LONG).show();
                                Intent inoz = new Intent(Merchant_Branch_AddPoints.this,Merchant_Reward_main.class);
                                inoz.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(inoz);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {

                                // Hiding the progress dialog after all task complete.
                                progressDialog.dismiss();

                                // Showing error message if something goes wrong.
                                Toast.makeText(Merchant_Branch_AddPoints.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {

                        // Creating Map String Params.
                        Map<String, String> params = new HashMap<String, String>();

                        // Adding All values to Params.
          //             ses = Integer.toString(strSavedMem);

                        params.put("User_id", trick);
                       params.put("Place_id", ses);

                        params.put("No_OF_points", LastNameHolder);


                        return params;
                    }

                };

                // Creating RequestQueue.
                RequestQueue requestQueue = Volley.newRequestQueue(Merchant_Branch_AddPoints.this);

                // Adding the StringRequest object into requestQueue.
                requestQueue.add(stringRequest);

            }
        });

    }

    // Creating method to get value from EditText.
    public void GetValueFromEditText(){

      //  FirstNameHolder = FirstName.getText().toString().trim();
        LastNameHolder = LastName.getText().toString().trim();
//       EmailHolder = Email.getText().toString().trim();

    }


    private void loadSpinnerData(String url) {
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);

                        JSONArray jsonArray=jsonObject.getJSONArray("users");
                    s_name   = new String[jsonArray.length()];
                    s_gender = new String[jsonArray.length()];
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            s_name[i]=jsonObject1.getString("UserName");
                            s_gender[i] = jsonObject1.getString("UserId");
                         //   String country=jsonObject1.getString("UserId");
                          //  CountryName.add(country);
                        }

                    for(int i = 0; i<s_name.length; i++)
                    {
                        CountryName.add(s_name[i]+" : "+s_gender[i]);
                    }
                    spinner.setAdapter(new ArrayAdapter<String>(Merchant_Branch_AddPoints.this, android.R.layout.simple_spinner_dropdown_item, CountryName));
                }catch (JSONException e){e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    }

