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

public class SecondHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

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
    private static  final String listOfBranchesURL = "http://gp.sendiancrm.com/offerall/Place_profile.php";
    private static  final String listOfBranchesURL2 = "http://gp.sendiancrm.com/offerall/showListOfBranches.php";

    private int placeId ,strSaved  ;
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

        productList = new ArrayList<>();
        ListOfdataAdapter = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_id);
        recyclerView.setHasFixedSize(true);
        layoutManagerOfrecyclerView = new LinearLayoutManager(this);

        //   recyclerView.setLayoutManager(layoutManagerOfrecyclerView);

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        requestQueue = Volley.newRequestQueue(this);

//tv = (TextView) findViewById(R.id.bran);
        if (savedInstanceState == null) {
            mHorizontal = true;
        } else {
            mHorizontal = savedInstanceState.getBoolean(ORIENTATION);
        }

        //  setupAdapter();


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.getBoolean("place logged in", false)) {
            placeId = sharedPreferences.getInt("PID", Integer.parseInt("0"));
        }
        apps = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        loadbranch3(placeId);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, listOfBranchesURL,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResponse(String response) {
                        // System.out.println(response);
                        //     Toast.makeText(Merchant_Profile.this, "تبریک", Toast.LENGTH_LONG).show();
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
                            for (int i=0; i < arr.length(); i++) {
                                JSONObject product=arr.getJSONObject(i);

                                productList.add(new Merchant_Menu(
                                        product.getInt("Menu_id"),
                                        product.getString("Menu_Image"),
                                        product.getInt("Place_id")
                                ));

                            }

                            //    JSONObject array=new JSONObject(response);

                            //    garden =  array.getString("PLaceName");


                            recyclerViewadapter = new Merchant_RecyclerViewAdapter(productList, SecondHome.this);

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
                        Toast.makeText(SecondHome.this, "خطای اتصال به شبکه", Toast.LENGTH_LONG).show();
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


    public void loadbranch3(final int place_id ) {

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
                                apps.add(new App(

                                        branch3object.getString("Branch_name"),
                                        branch3object.getString("Branch_image"),
                                        branch3object.getString("Branch_name"),
                                        branch3object.getInt("Branch_id"),
                                        branch3object.getInt("Place_id")


                                ));


                            }

                            SnapAdapter snapAdapter = new SnapAdapter(SecondHome.this);

                            snapAdapter.addSnap(new Snap(Gravity.CENTER, "", apps));



                            mRecyclerView.setAdapter(snapAdapter);


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
                params.put("placeId",""+place_id);


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
