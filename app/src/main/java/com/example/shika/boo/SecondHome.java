package com.example.shika.boo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
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
import android.view.MenuItem;
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
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.communication.IOnItemFocusChangedListener;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.widget.Button;

import static android.view.Gravity.apply;

public class SecondHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static final String ORIENTATION = "orientation";
    private WebView mwebView;
    private RecyclerView mRecyclerView;
    private boolean mHorizontal;
    ImageView iv;
    Toolbar toolbar;
    String name,placeimg;
    private FrameLayout view_stub; //This is the framelayout to keep your content view
    private NavigationView navigation_view; // The new navigation view from Android Design Library. Can inflate menu resources. Easy
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Menu drawerMenu;
    TextView tv,category,pass,title;
    RecyclerView.Adapter recyclerViewadapter;
    String cat,password,email,hna;
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
    List<Integer> apps;


    int placeId;

    int ones = 0;
    int zeroes= 0;

    private static  final String listOfBranchesURL = "http://gp.sendiancrm.com/offerall/Place_profile.php";
    private static  final String listOfBranchesURL2 = "http://gp.sendiancrm.com/offerall/showListOfBranches.php";


    private int strSaved  ;
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    FloatingActionButton floatingActionButton;
    //String [] branchNames = {"Alex","Cairo","Giza","Elbadrashen"};
    ArrayList<String> listBranchNames ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_home);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerz);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_viewz);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

iv = (ImageView) header.findViewById(R.id.merchantiv);
title = (TextView) header.findViewById(R.id.headertx);




        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.getBoolean("place logged in", false)) {
            placeId = sharedPreferences.getInt("PID", Integer.parseInt("0"));
        }
        apps = new ArrayList<>();


        loadbranch3(placeId);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, listOfBranchesURL2 ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(SecondHome.this, "branches response", Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray branches3 =jsonObject.getJSONArray("branchs");

                            for(int i=0; i<branches3.length();i++){
                                JSONObject branch3object = branches3.getJSONObject(i);
                                apps.add(


                                        branch3object.getInt("RewardSystemAvailabilty")


                                );


                            }

                            ones = Collections.frequency(apps, 1);
                            zeroes = Collections.frequency(apps, 0);

                            PieChart mPieChart = (PieChart) findViewById(R.id.piechart);

                            mPieChart.addPieSlice(new PieModel("Branches with Reward System", ones, Color.parseColor("#FE6DA8")));
                            mPieChart.addPieSlice(new PieModel("Branches without Reward System", zeroes, Color.parseColor("#56B7F1")));
                            //     mPieChart.addPieSlice(new PieModel("Work", 35, Color.parseColor("#CDA67F")));
                            //     mPieChart.addPieSlice(new PieModel("Eating", 9, Color.parseColor("#FED70E")));

                            mPieChart.startAnimation();

                            mPieChart.setOnItemFocusChangedListener(new IOnItemFocusChangedListener() {
                                @Override
                                public void onItemFocusChanged(int _Position) {
//                Log.d("PieChart", "Position: " + _Position);
                                }
                            });

                            //   tv.setText(Integer.toString(ones));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SecondHome.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }

        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("placeId",""+placeId);


                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);

    }



/*
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
        apps.add(new App("Zara le merange", "Zara le merange", "22 street ,EL Dokki   (Cairo)",1,3));
        apps.add(new App("Zara do2do2", "Zara le merange", "22 street ,EL Dokki   (Cairo)",2,5));
        apps.add(new App("Modern zara", "Zara le merange", "22 street ,EL Dokki   (Cairo)",3,8));
        apps.add(new App("Zara females", "Zara le merange", "22 street ,EL Dokki   (Cairo)",5,7));
        apps.add(new App("Zara community", "Zara le merange", "22 street ,EL Dokki   (Cairo)",6,2));
       return apps;
    }
*/



    public void loadbranch3(final int branch_id ) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, listOfBranchesURL ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray arr = new JSONArray(response);
                            JSONObject jObj = arr.getJSONObject(0);
                            placeimg = jObj.getString("Place_LogoPhoto");
                            hna = jObj.getString("PLaceName");
                            Glide.with(SecondHome.this).load(placeimg)
                                    .apply(new RequestOptions()
                                            .placeholder(R.drawable.placeholder)   // optional
                                            .error(R.drawable.error))
                                    .into(iv);
                            title.setText(hna);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SecondHome.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }

        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("ID",""+branch_id);


                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }




    public boolean onNavigationItemSelected(MenuItem menuitem) {
        Intent x;
        Class fragmentClass;
        int id = menuitem.getItemId();
        if(id == R.id.mainz){

            x = new Intent(this,SecondHome.class);
            startActivity(x);}
        else if(id == R.id.ctgz){
            x = new Intent(this, Merchant_After_menu.class);
            startActivity(x);}
        else if(id == R.id.brlist){
            x = new Intent(this, Merchant_Branches_list.class);
            startActivity(x);}
        else if(id == R.id.brplus){
            x = new Intent(this, Merchant_Add_Branch.class);
            startActivity(x);}
        else if(id == R.id.pf){
            x = new Intent(this, Merchant_Profile.class);
            startActivity(x);}
       /* else if(id == R.id.likes){
            x = new Intent(this, SavedOffersFragment.class);
            startActivity(x);}
        else if(id == R.id.prize){
            x = new Intent(this, RewardsActivity.class);
            startActivity(x);}*/


        return false;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }



}
