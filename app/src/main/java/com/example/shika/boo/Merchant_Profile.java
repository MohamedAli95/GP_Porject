package com.example.shika.boo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.widget.Button;

import static android.view.Gravity.apply;

public class Merchant_Profile extends AppCompatActivity {

    public static final String ORIENTATION = "orientation";
    private WebView mwebView;
    private RecyclerView mRecyclerView;
    private boolean mHorizontal;
ImageView iv;
    List<App> apps;
    Toolbar toolbar;
    String name,placeimg;
    private FrameLayout view_stub; //This is the framelayout to keep your content view
    private NavigationView navigation_view; // The new navigation view from Android Design Library. Can inflate menu resources. Easy
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Menu drawerMenu;
    TextView tv,category;
    RecyclerView.Adapter recyclerViewadapter;
    String cat;
    SharedPreferences sharedPreferences ;
    android.app.AlertDialog alertDialog;
    RecyclerView.LayoutManager layoutManagerOfrecyclerView;

    List<Merchant_Menu> productList;
    RecyclerView recyclerView;
    View vv;

    List<String> ListOfdataAdapter;

    //arrayAdaptorHandle adaptor;
    StringRequest request ;
    RequestQueue requestQueue ;
    private static  final String listOfBranchesURL = "http://gp.sendiancrm.com/offerall/Place_profile.php";
    private int placeId ,strSaved  ;
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    //String [] branchNames = {"Alex","Cairo","Giza","Elbadrashen"};
    ArrayList<String> listBranchNames ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant__profile);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        productList = new ArrayList<>();
        ListOfdataAdapter = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_id);
        recyclerView.setHasFixedSize(true);
        layoutManagerOfrecyclerView = new LinearLayoutManager(this);

        //   recyclerView.setLayoutManager(layoutManagerOfrecyclerView);

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
iv = (ImageView) findViewById(R.id.ivg);

        mCollapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        tv = (TextView) findViewById(R.id.desc);
        category = (TextView) findViewById(R.id.ctg2);
        requestQueue = Volley.newRequestQueue(this);

//tv = (TextView) findViewById(R.id.bran);
        if (savedInstanceState == null) {
            mHorizontal = true;
        } else {
            mHorizontal = savedInstanceState.getBoolean(ORIENTATION);
        }
        setupAdapter();


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.getBoolean("logged in", false)) {
            placeId = sharedPreferences.getInt("PID", Integer.parseInt("0"));
        }

        StringRequest stringRequest=new StringRequest(Request.Method.POST, listOfBranchesURL,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResponse(String response) {
                        // System.out.println(response);
                        Toast.makeText(Merchant_Profile.this, "تبریک", Toast.LENGTH_LONG).show();
                        try {
                            JSONArray arr = new JSONArray(response);
                            JSONObject jObj = arr.getJSONObject(0);
                            name = jObj.getString("PLaceName");
                             cat = jObj.getString("Name");
                             placeimg = jObj.getString("Place_LogoPhoto");
                            for (int i=0; i < arr.length(); i++) {
                                JSONObject product=arr.getJSONObject(i);

                                productList.add(new Merchant_Menu(
                                        product.getInt("menu_id"),
                                        product.getString("image"),
                                        product.getInt("placid")
                                ));

                            }

                        //    JSONObject array=new JSONObject(response);

                      //    garden =  array.getString("PLaceName");
                            mCollapsingToolbarLayout.setTitle(name);
                            category.setText(cat);
                          Glide.with(Merchant_Profile.this).load(placeimg)
                                  .apply(new RequestOptions()
                                 .placeholder(R.drawable.placeholder)   // optional
                                  .error(R.drawable.error))
                                   .into(iv);


                            recyclerViewadapter = new Merchant_RecyclerViewAdapter(productList, Merchant_Profile.this);

                            recyclerView.setAdapter(recyclerViewadapter);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        // tv.setText(date);

                        }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Merchant_Profile.this, "خطای اتصال به شبکه", Toast.LENGTH_LONG).show();
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





    public void branch_trans(View view){
        Intent ba = new Intent(this,BranchActivity.class);
        startActivity(ba);
    }

    private void setupAdapter() {
        List<App> apps = getApps();

        SnapAdapter snapAdapter = new SnapAdapter(this);
        if (mHorizontal) {
            snapAdapter.addSnap(new Snap(Gravity.CENTER, "", apps));

        } else {
            snapAdapter.addSnap(new Snap(Gravity.CENTER_VERTICAL, "Snap center", apps));
            snapAdapter.addSnap(new Snap(Gravity.TOP, "Snap top", apps));
            snapAdapter.addSnap(new Snap(Gravity.BOTTOM, "Snap bottom", apps));
        }

        mRecyclerView.setAdapter(snapAdapter);
    }

    private List<App> getApps() {
        apps = new ArrayList<>();
       /* apps.add(new App("Zara le merange", R.drawable.new1, "22 street ,EL Dokki   (Cairo)"));
        apps.add(new App("Zara do2do2", R.drawable.new2, "22 street ,EL Dokki   (Cairo)"));
        apps.add(new App("Modern zara", R.drawable.new3, "22 street ,EL Dokki   (Cairo)"));
        apps.add(new App("Zara females", R.drawable.new4, "22 street ,EL Dokki   (Cairo)"));
        apps.add(new App("Zara community", R.drawable.new6, "22 street ,EL Dokki   (Cairo)"));*/
        return apps;
    }


}
