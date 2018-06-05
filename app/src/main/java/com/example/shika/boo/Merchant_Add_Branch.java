package com.example.shika.boo;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Merchant_Add_Branch extends AppCompatActivity {

    String[] Reward = {"UnAvailable","Available"};

    private EditText ET_password, ET_BrandName,ET_phone ;
    Button btn_addBranch ;
    private RequestQueue requestQueue ;
    private StringRequest request ;
    private int rewordSystem =1;
    SharedPreferences sharedPreferences ;
    AlertDialog alertDialog;
    private  int placeId ;

    private static String registerBranchURL = "http://gp.sendiancrm.com/offerall/addBranch.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant__add__branch);

        ET_BrandName = (EditText)findViewById(R.id.branchName);
        ET_password =(EditText)findViewById(R.id.branchpassword);
        ET_phone =(EditText)findViewById(R.id.branchPhone);
        btn_addBranch = (Button) findViewById(R.id.btn_registerBranche);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        alertDialog = new AlertDialog.Builder(this).create();

        btn_addBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate())
                {
                    addNewBranch();
                }

            }
        });

        Button bu = (Button) findViewById(R.id.pickup_btn);
        bu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent ino = new Intent(Merchant_Add_Branch.this,Branch_MapActivity.class);
                startActivity(ino);
            }
        });


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,Reward);
        MaterialBetterSpinner betterSpinner = (MaterialBetterSpinner) findViewById(R.id.spenner);
        betterSpinner.setAdapter(arrayAdapter);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.getBoolean("logged in", false)) {
            placeId = sharedPreferences.getInt("PID", Integer.parseInt("0"));
        }

    }

   public  void addNewBranch(){

        request = new StringRequest(Request.Method.POST, registerBranchURL, new Response.Listener<String>() {
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Something went wrong",Toast.LENGTH_LONG).show();
                alertDialog.setMessage("حدث خطأ بالاتصال بالشبكه؟" +"\n"+"يجب عليك فتح النت؟");
                alertDialog.show();
                error.printStackTrace();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap =new HashMap<>();
                hashMap.put("branchName",ET_BrandName.getText().toString());
                hashMap.put("branchPhone",ET_phone.getText().toString());
                hashMap.put("password",ET_password.getText().toString());
                hashMap.put("checkedRewordSystem", ""+rewordSystem);
                hashMap.put("place_id",""+placeId);

                return  hashMap;
            }
        };
     requestQueue.add(request);
   }

    public boolean validate() {
        boolean valid = true;
        if(ET_BrandName.getText().toString().matches("")||ET_BrandName.length()>32){
            ET_BrandName.setError("Please Enter Valid Name");
            valid=false;
        }
        if(ET_password.getText().toString().matches("")||ET_password.length()<8){
            ET_password.setError("Enter password At Least 8 CharS ");
            valid=false;
        }

        if(ET_phone.getText().toString().matches("")){
            ET_phone.setError("Enter Valid Phone");
            valid=false;
        }

        return valid;
    }

}
