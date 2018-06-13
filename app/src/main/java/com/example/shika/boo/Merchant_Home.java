package com.example.shika.boo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Merchant_Home extends AppCompatActivity {

    ImageView iv;
    Toolbar toolbar;
    String name,placeimg;
    SharedPreferences sharedPreferences ;

    //arrayAdaptorHandle adaptor;
    StringRequest request ;
    RequestQueue requestQueue ;
    private static  final String listOfBranchesURL = "http://gp.sendiancrm.com/offerall/Place_profile.php";
    private int placeId ,strSaved  ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant__home);

       /* Bundle getDataFromSignIn = getIntent().getExtras();
        String pName =getDataFromSignIn.getString("PlName");
        String pLogo =getDataFromSignIn.getString("Plphoto");

        Toast.makeText(this, pName, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, pLogo, Toast.LENGTH_SHORT).show();*/

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.tomen);
        // LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.toreward);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.topro);
        LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.tobranches);
        LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.toreport);
        LinearLayout linearLayoutLOGOUT = (LinearLayout) findViewById(R.id.toprof);


        // LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.tomenu);


        linearLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent ino = new Intent(Merchant_Home.this,Merchant_After_menu.class);
                startActivity(ino);
            }
        });

        linearLayout1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent inoz = new Intent(Merchant_Home.this,Merchant_Branches_list.class);
                startActivity(inoz);
            }
        });

        linearLayout3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent ino = new Intent(Merchant_Home.this,Merchant_Add_Branch.class);
                startActivity(ino);
            }
        });

       /* linearLayout1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent ino = new Intent(Merchant_Home.this,Merchant_Reward_main.class);
                startActivity(ino);
            }
        });*/

        linearLayout2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent ino = new Intent(Merchant_Home.this,Merchant_Profile.class);
                startActivity(ino);
            }
        });
        linearLayoutLOGOUT.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(Merchant_Home.this);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
                Intent x = new Intent(Merchant_Home.this, AfterBegin.class);
                x.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(x);

            }
        });

        /*linearLayout3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent ino = new Intent(Merchant_Home.this,Merchant_addmenu.class);
                startActivity(ino);
            }
        });*/
        iv = (ImageView) findViewById(R.id.homeback);
        requestQueue = Volley.newRequestQueue(this);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.getBoolean("place logged in", false)) {
            placeId = sharedPreferences.getInt("PID", Integer.parseInt("0"));
        }

        StringRequest stringRequest=new StringRequest(Request.Method.POST, listOfBranchesURL,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResponse(String response) {
                        // System.out.println(response);
                        Toast.makeText(Merchant_Home.this, "تبریک", Toast.LENGTH_LONG).show();
                        try {
                            JSONArray arr = new JSONArray(response);
                            JSONObject jObj = arr.getJSONObject(0);

                            placeimg = jObj.getString("Place_LogoPhoto");

                            Glide.with(Merchant_Home.this).load(placeimg)
                                    .apply(new RequestOptions()
                                            .placeholder(R.drawable.placeholder)   // optional
                                            .error(R.drawable.error))
                                    .into(iv);

                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        // tv.setText(date);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Merchant_Home.this, "خطای اتصال به شبکه", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params=new HashMap<String, String>();

                params.put("ID", Integer.toString(placeId));


                return params;
            }

        };
        Volley.newRequestQueue(this).add(stringRequest);

    }

}
