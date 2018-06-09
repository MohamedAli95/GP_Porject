package com.example.shika.boo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Place1;
import models.Place2;
import models.Place3;

public class Try extends AppCompatActivity {

    public static final String ORIENTATION = "orientation";
    private WebView mwebView;
    private RecyclerView mRecyclerView;
    private boolean mHorizontal;
    List<App> apps;
    Toolbar toolbar;


    //hena1
    SharedPreferences sharedpreferences;
    List<Place1> place1list;
    TextView categoryPlace,userPoint;
    CollapsingToolbarLayout collapsingToolbarLayout;


    //hena2
    RecyclerView place2recycleView;
    Place2Adapter adapter2;
    List<Place2> place2list;

    //hena3
    RecyclerView place3recycleView;
    Place3Adapter adapter3;
    List<Place3> place3list;



    private FrameLayout view_stub; //This is the framelayout to keep your content view
    private NavigationView navigation_view; // The new navigation view from Android Design Library. Can inflate menu resources. Easy
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Menu drawerMenu;
    int place_id;
    int user_id;
    TextView tv;

    private static final String Place1Url = "http://gp.sendiancrm.com/offerall/place1Query.php";
    private static final String Place2Url = "http://gp.sendiancrm.com/offerall/place2Query.php";
    private static final String Place3Url = "http://gp.sendiancrm.com/offerall/place3Query.php";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_try);
        Intent intent=getIntent();
        Bundle extras = intent.getExtras();
        place_id=extras.getInt("place_id");
        place_id=extras.getInt("place_id");
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
        user_id=sharedpreferences.getInt("Id",0);

        //hena1
        place1list =new ArrayList<>();
        categoryPlace=(TextView) findViewById(R.id.categoryPlace);
        userPoint=(TextView) findViewById(R.id.userPoint);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar99);


        loadplace1(place_id,user_id);



        //hena2
        place2list =new ArrayList<>();
        place2recycleView = (RecyclerView) findViewById(R.id.recyclerView2) ;
        place2recycleView.setHasFixedSize(true);
        place2recycleView.setLayoutManager(new LinearLayoutManager(this));

        loadplace2(place_id);




        //hena3
        place3list =new ArrayList<>();
        place3recycleView = (RecyclerView) findViewById(R.id.recyclerView3) ;
        place3recycleView.setHasFixedSize(true);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(Try.this, 2);
        place3recycleView.setLayoutManager(mGridLayoutManager);



        loadplace3(place_id);


    }


    //hena1
    public void loadplace1(final int place_id,final int user_id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Place1Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("response22",response);
                            Log.e("useridbta3elplace",String.valueOf(user_id));
                            JSONArray places1 =new JSONArray(response);

                            String Place_LogoPhoto="" ;
                            String PLaceName="";
                            String Name="";
                            int No_OF_points=0;

                            for(int i=0; i<places1.length();i++){
                                JSONObject place3object = places1.getJSONObject(i);

                                Place_LogoPhoto =place3object.getString("Place_LogoPhoto");
                                PLaceName=place3object.getString("PLaceName");
                                Name=place3object.getString("Name");
                                if(place3object.has("No_OF_points")) {
                                    No_OF_points = Integer.valueOf(place3object.getString("No_OF_points"));
                                }

                                //Place1 place1 =new Place1(Place_LogoPhoto,PLaceName,Name,No_OF_points);
                                //place1list.add(place1);

                            }
                            userPoint.setText(String.valueOf(No_OF_points));
                            categoryPlace.setText(Name);
                            collapsingToolbarLayout.setTitle(PLaceName);
                            Picasso.get().load(Place_LogoPhoto).into(new Target() {
                                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                @Override
                                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                    collapsingToolbarLayout.setBackground(new BitmapDrawable(bitmap));
                                }

                                @Override
                                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                                }


                                @Override
                                public void onPrepareLoad(Drawable placeHolderDrawable) {

                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Try.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }

        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("Place_ID",""+place_id);
                params.put("user_id",""+user_id);


                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }


    //hena2
    public void loadplace2(final int place_id ) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Place2Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray places2 =new JSONArray(response);

                            for(int i=0; i<places2.length();i++){
                                JSONObject place2object = places2.getJSONObject(i);
                                int branch_id =place2object.getInt("Branch_id");

                                String Branch_name=place2object.getString("Branch_name");

                                Place2 place2 =new Place2(branch_id,Branch_name);
                                place2list.add(place2);
                            }

                            adapter2 =new Place2Adapter(Try.this ,place2list);
                            place2recycleView.setAdapter(adapter2);
                            adapter2.setOnClick(new Place2Adapter.OnItemClicked(){
                                @Override
                                public void onItemClick(int position,int branchid) {
                                    Intent intent = new Intent(getApplicationContext(),BranchActivity.class);
                                    intent.putExtra("branchid",branchid);
                                    startActivity(intent);
                                }
                            });





                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Try.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }

        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Place_ID",""+place_id);


                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }


    //hena3
    public void loadplace3(final int place_id ) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Place3Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray places3 =new JSONArray(response);

                            for(int i=0; i<places3.length();i++){
                                JSONObject place3object = places3.getJSONObject(i);

                                int Menu_id =place3object.getInt("Menu_id");
                                String Menu_Title=place3object.getString("Menu_Title");
                                double Menu_Price=place3object.getDouble("Menu_Price");
                                String Menu_Image=place3object.getString("Menu_Image");

                                Place3 place3 =new Place3(Menu_id,Menu_Title,Menu_Price,Menu_Image);
                                place3list.add(place3);
                            }

                            adapter3 =new Place3Adapter(Try.this ,place3list);
                            place3recycleView.setAdapter(adapter3);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Try.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }

        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Place_ID",""+place_id);


                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }









    //m3rfshhsssssssssssssssss
    public void branch_trans(View view){
        Intent ba = new Intent(this,BranchActivity.class);
        startActivity(ba);
    }

    private void setupAdapter() {
        List<App> apps = getApps();

        SnapAdapter snapAdapter = new SnapAdapter(this);
        if (mHorizontal) {
            snapAdapter.addSnap(new Snap(Gravity.CENTER, "Our Branches", apps));

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
