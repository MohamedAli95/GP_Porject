package com.example.shika.boo;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import models.SearchPlace;

public class ForgotPassword extends AppCompatActivity {

    private static final String forgotpasswordurl = "http://gp.sendiancrm.com/offerall/forgotPassword.php";
    EditText email;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        email = (EditText) findViewById(R.id.useremailpassword);
        alertDialog= new AlertDialog.Builder(this).create();
    }
    public void submit(View view) {
        if(validate()) {
            forgotpassword(email.getText().toString());
        }
    }

    public void forgotpassword(final String email ) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, forgotpasswordurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       if(response.equals("Please check your email!")){
                           Intent too = new Intent(getApplicationContext(),ResetPassword.class);
                           too.putExtra("email",email);
                           startActivity(too);
                       }
                       else {
                           alertDialog.setMessage(response);
                           alertDialog.show();
                       }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }

        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email",email);


                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }


    public boolean validate() {
        boolean valid = true;
        if(email.getText().equals("")||!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()){
            email.setError("Please Enter Valid Email Adress");
            valid=false;
        }

        return valid;
    }

}
