package com.example.shika.boo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class sign_in_merchant extends AppCompatActivity {

    private EditText ET_password,ET_email ;
    RequestQueue requestQueue ;
    StringRequest request ;
    private  String merchantLoginURL = "http://gp.sendiancrm.com/offerall/loginPlace.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_merchant);
        ET_email = (EditText) findViewById(R.id.useremail);
        ET_password = (EditText) findViewById(R.id.userpassword);
        requestQueue = Volley.newRequestQueue(this) ;


    }

    public  Boolean validate(){

        boolean valid = true ;

        if (ET_email.getText().toString().matches("")){
            ET_email.setError("Enter Valid Email Address!");
            valid = false ;
        }

        if (ET_password.getText().toString().matches("")){
            ET_password.setError("Enter Valid Password!");
            valid = false ;
        }

        return valid ;
    }

    public void regerster(View view) {
        Intent intent = new Intent(getApplicationContext(), Merchant_signup.class);
        startActivity(intent);
    }

    public void login(View view) {

        if (validate()){
            String Email = ET_email.getText().toString() ;
            String Password = ET_password.getText().toString() ;
            loginDetails(Email,Password);
        }

    }
    public void loginDetails(final String email , final String password) {

        request = new StringRequest(Request.Method.POST, merchantLoginURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.names().get(0).equals("success"))
                    {
                        Toast.makeText(getApplicationContext(), ""+jsonObject.get("success"),
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),Merchant_Home.class);
                        startActivity(intent);
                    }else if (jsonObject.names().get(0).equals("error"))
                    {
                        Toast.makeText(getApplicationContext(), ""+jsonObject.get("error"),
                                Toast.LENGTH_LONG).show();
                    }
                    if (jsonObject.names().get(0).equals("place")){

                        JSONArray places=new JSONArray(response);
                        JSONObject place = places.getJSONObject(0);
                        String pLacename = place.getString("PLaceName");
                        String place_logoPhoto = place.getString("Place_LogoPhoto");
                        Toast.makeText(getApplicationContext(),pLacename , Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Something went wrong",Toast.LENGTH_LONG).show();
                error.printStackTrace();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("email", email);
                hashMap.put("password", password);
                return hashMap;
            }

        };
        requestQueue.add(request);
    }




}
