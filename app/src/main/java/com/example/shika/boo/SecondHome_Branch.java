package com.example.shika.boo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SecondHome_Branch extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
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
    TextView tv,lo,passtxt,mobtxt,title;
    RecyclerView.Adapter recyclerViewadapter,recyclerViewadapter2;
    String cat,pass,hna;
    SharedPreferences sharedPreferences ;
    android.app.AlertDialog alertDialog;
    RecyclerView.LayoutManager layoutManagerOfrecyclerView;

    List<Data_Model> productList,rewardList;
    RecyclerView recyclerView,recyclerView2;
    View vv;
    NavigationView navigationView;
    List<String> ListOfdataAdapter;
    Geocoder geocoder;
    List<Address> addresses;
    double lat,lan;
    //arrayAdaptorHandle adaptor;
    StringRequest request ;
    RequestQueue requestQueue ;
    private static  final String listOfBranchesURL = "http://gp.sendiancrm.com/offerall/Branch_profile.php";
    private static  final String listOfBranchesURL2 = "http://gp.sendiancrm.com/offerall/Branch_profile2.php";
    public int RewardSystemAvailabilty=0;
    private int branchId ,strSaved  ;
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_home__branch);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerz);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

         navigationView = (NavigationView) findViewById(R.id.navigation_viewz);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        iv = (ImageView) header.findViewById(R.id.merchantiv);
        title = (TextView) header.findViewById(R.id.headertx);

        Intent iu = getIntent();
        int z = iu.getIntExtra("decide",0);

        if(z==2) {
            hideItem();
        }

        productList = new ArrayList<>();
        ListOfdataAdapter = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_id);
        recyclerView.setHasFixedSize(true);
        layoutManagerOfrecyclerView = new LinearLayoutManager(this);

        //   recyclerView.setLayoutManager(layoutManagerOfrecyclerView);

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        // loca = (TextView) findViewById(R.id.ctg2);
        requestQueue = Volley.newRequestQueue(this);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.getBoolean("place logged in", false)) {
            branchId = sharedPreferences.getInt("BId", Integer.parseInt("0"));
        }

        rewardList = new ArrayList<>();

        recyclerView2 = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView2.setHasFixedSize(true);
        layoutManagerOfrecyclerView = new LinearLayoutManager(SecondHome_Branch.this);

        //   recyclerView.setLayoutManager(layoutManagerOfrecyclerView);

        recyclerView2.setLayoutManager(new GridLayoutManager(SecondHome_Branch.this,2));


        loadbranch3(branchId);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, listOfBranchesURL,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResponse(String response) {
                        // System.out.println(response);
                        Toast.makeText(SecondHome_Branch.this, "تبریک", Toast.LENGTH_LONG).show();
                        try {
                            JSONArray arr = new JSONArray(response);
                            JSONObject jObj = arr.getJSONObject(0);
                            placeimg = jObj.getString("Branch_image");
                            hna = jObj.getString("Branch_name");
                            Glide.with(SecondHome_Branch.this).load(placeimg)
                                    .apply(new RequestOptions()
                                            .placeholder(R.drawable.placeholder)   // optional
                                            .error(R.drawable.error))
                                    .into(iv);
                            title.setText(hna);
                            for (int i=0; i < arr.length(); i++) {
                                JSONObject product=arr.getJSONObject(i);

                                productList.add(new Data_Model(

                                        product.getString("Title"),
                                        product.getInt("points"),
                                        product.getString("EndDate"),
                                        product.getString("Offer_image")

                                ));

                             /*   else{
                                    TextView bran;
                                    bran=(TextView) findViewById(R.id.bran);
                                    bran.setText(" No Available Rewards Now  ");
                                }*/
                            }
                    /*        if (RewardSystemAvailabilty==1) {
                                //hena3
                                rewardList = new ArrayList<>();

                                recyclerView2 = (RecyclerView) findViewById(R.id.recyclerview);
                                recyclerView2.setHasFixedSize(true);
                                layoutManagerOfrecyclerView = new LinearLayoutManager(SecondHome_Branch.this);

                                //   recyclerView.setLayoutManager(layoutManagerOfrecyclerView);

                                recyclerView2.setLayoutManager(new GridLayoutManager(SecondHome_Branch.this,2));


                                loadbranch3(branchId);
                            }*/

                            //    JSONObject array=new JSONObject(response);

                            //    garden =  array.getString("PLaceName");


                            recyclerViewadapter = new RecyclerView_Adapter(SecondHome_Branch.this,productList);

                            recyclerView.setAdapter(recyclerViewadapter);
//                            recyclerViewadapter2 = new RecyclerView_Adapter(Merchant_Branch_Profile.this,rewardList);

                            //                          recyclerView2.setAdapter(recyclerViewadapter2);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        // tv.setText(date);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SecondHome_Branch.this, "خطای اتصال به شبکه", Toast.LENGTH_LONG).show();
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



    public void loadbranch3(final int branch_id ) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, listOfBranchesURL2 ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray branches3 =new JSONArray(response);

                            for(int i=0; i<branches3.length();i++){
                                JSONObject branch3object = branches3.getJSONObject(i);

                                rewardList.add(new Data_Model(

                                        branch3object.getString("Title"),
                                        branch3object.getInt("No_OF_points"),
                                        branch3object.getString("EndDate"),
                                        branch3object.getString("Reward_image")

                                ));


                            }

                            recyclerViewadapter2 = new RecyclerView_Adapter(SecondHome_Branch.this,rewardList);
                            recyclerView2.setAdapter(recyclerViewadapter2);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SecondHome_Branch.this,error.getMessage(),Toast.LENGTH_SHORT).show();
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

            x = new Intent(this,SecondHome_Branch.class);
            startActivity(x);}
        else if(id == R.id.ctgz){
            x = new Intent(this, Merchant_Branch_ManageOffer.class);
            startActivity(x);}
        else if(id == R.id.brlist){
            x = new Intent(this, Merchant_Reward_main.class);
            startActivity(x);}

        else if(id == R.id.pf){
            x = new Intent(this, Merchant_Branch_Profile.class);
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


    private void hideItem()
    {
        navigationView = (NavigationView) findViewById(R.id.navigation_viewz);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.brlist).setVisible(false);
    }


}

