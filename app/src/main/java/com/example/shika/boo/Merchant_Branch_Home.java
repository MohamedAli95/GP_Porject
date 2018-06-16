package com.example.shika.boo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Merchant_Branch_Home extends AppCompatActivity {
    SharedPreferences sharedPreferences ;
int branchId;
    ImageView iv;
    Toolbar toolbar;
    String name,placeimg;
    private static  final String listOfBranchesURL = "http://gp.sendiancrm.com/offerall/Branch_profile.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant__branch__home);

     /*   View home = findViewById(R.id.merchant_home);
        TextView headerText1 = (TextView) home.findViewById(R.id.title);
        TextView headerText2 = (TextView) home.findViewById(R.id.topleft);
        TextView headerText3 = (TextView) home.findViewById(R.id.topright);
        TextView headerText4 = (TextView) home.findViewById(R.id.middleleft);
        TextView headerText5 = (TextView) home.findViewById(R.id.middleright);
        TextView headerText6 = (TextView) home.findViewById(R.id.last);

        headerText1.setText("Branch Home");
        headerText2.setText("Manage Offers");
        headerText3.setText("Branch Report");
        headerText4.setText("Profile");
        headerText5.setText("Manage Rewards");
        headerText6.setText("Logout to place");*/
        Intent iu = getIntent();
        int z = iu.getIntExtra("decide",0);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.tobranches);
        //  LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.tomen);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.toreport);
        LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.topro);

        if(z==2){
            ViewGroup vg = (ViewGroup)(linearLayout3.getParent());
            vg.removeView(linearLayout3);
            linearLayout2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1.0f));
        }
        linearLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent ino = new Intent(Merchant_Branch_Home.this,Merchant_Branch_ManageOffer.class);
                startActivity(ino);
            }
        });



        linearLayout2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent ino = new Intent(Merchant_Branch_Home.this,Merchant_Branch_Profile.class);
                startActivity(ino);
            }
        });


        linearLayout3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent ino = new Intent(Merchant_Branch_Home.this,Merchant_Reward_main.class);
                startActivity(ino);
            }
        });

        iv = (ImageView) findViewById(R.id.homeback);
       // requestQueue = Volley.newRequestQueue(this);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.getBoolean("place logged in", false)) {
            branchId = sharedPreferences.getInt("BId", Integer.parseInt("0"));
        }
        StringRequest stringRequest=new StringRequest(Request.Method.POST, listOfBranchesURL,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResponse(String response) {
                        // System.out.println(response);
                        Toast.makeText(Merchant_Branch_Home.this, "تبریک", Toast.LENGTH_LONG).show();
                        try {
                            JSONArray arr = new JSONArray(response);
                            JSONObject jObj = arr.getJSONObject(0);

                            placeimg = jObj.getString("Branch_image");

                            Glide.with(Merchant_Branch_Home.this).load(placeimg)
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
                        Toast.makeText(Merchant_Branch_Home.this, "خطای اتصال به شبکه", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params=new HashMap<String, String>();

                params.put("ID", Integer.toString(branchId));


                return params;
            }

        };
        Volley.newRequestQueue(this).add(stringRequest);

    }
}
