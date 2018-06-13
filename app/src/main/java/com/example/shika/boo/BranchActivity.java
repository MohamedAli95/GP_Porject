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

import models.Branch1;
import models.Branch2;
import models.Branch3;
import models.Place1;
import models.Place2;
import models.Place3;

public class BranchActivity extends AppCompatActivity {

    public static final String ORIENTATION = "orientation";
    private WebView mwebView;
    private RecyclerView mRecyclerView;
    private boolean mHorizontal;
    List<App> apps;
    Toolbar toolbar;



    //hena1
    SharedPreferences sharedpreferences;
    List<Branch1> branch1list;
    TextView placeName;
    CollapsingToolbarLayout collapsingToolbarLayout;
    public int RewardSystemAvailabilty=0;






    //hena2
    RecyclerView branch2recycleView;
    Branch2Adapter adapter2;
    List<Branch2> branch2list;

    //hena3
    RecyclerView branch3recycleView;
    Branch3Adapter adapter3;
    List<Branch3> branch3list;



    private FrameLayout view_stub; //This is the framelayout to keep your content view
    private NavigationView navigation_view; // The new navigation view from Android Design Library. Can inflate menu resources. Easy
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Menu drawerMenu;
    int branch_id;
    int place_id;
    TextView tv;

    private static final String Branch1Url = "http://gp.sendiancrm.com/offerall/branch1Query.php";
    private static final String Branch2Url = "http://gp.sendiancrm.com/offerall/branch2Query.php";
    private static final String Branch3Url = "http://gp.sendiancrm.com/offerall/branch3Query.php";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch);
        Intent intent=getIntent();
        Bundle extras = intent.getExtras();
        branch_id=extras.getInt("Branch_id");
        branch_id=extras.getInt("Branch_id");
        place_id = extras.getInt("place_id");
        place_id = extras.getInt("place_id");



        //hena1
        branch1list =new ArrayList<>();
        placeName=(TextView) findViewById(R.id.placeName);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar99);


        loadbranch1(branch_id);



        //hena2
        branch2list =new ArrayList<>();
        branch2recycleView = (RecyclerView) findViewById(R.id.recyclerView2) ;
        branch2recycleView.setHasFixedSize(true);
        branch2recycleView.setLayoutManager(new LinearLayoutManager(this));


        loadbranch2(branch_id);



       /* if (RewardSystemAvailabilty==1) {
            //hena3
            branch3list = new ArrayList<>();
            branch3recycleView = (RecyclerView) findViewById(R.id.recyclerView3);
            branch3recycleView.setHasFixedSize(true);
            branch3recycleView.setLayoutManager(new LinearLayoutManager(this));


            loadbranch3(branch_id);
        }*/


    }


    //hena1
    public int loadbranch1(final int branch_id ) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Branch1Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                           // Log.e("response22",response);
                          //  Log.e("useridbta3elplace",String.valueOf(user_id));
                            JSONArray branches1 =new JSONArray(response);

                            String Place_LogoPhoto="" ;
                            String PLaceName="";
                            String Branch_name="";
                            Double latitude=0.0;
                            Double longitude=0.0;



                            for(int i=0; i<branches1.length();i++){
                                JSONObject branch1object = branches1.getJSONObject(i);

                                Place_LogoPhoto =branch1object.getString("Place_LogoPhoto");
                                PLaceName=branch1object.getString("PLaceName");
                                Branch_name=branch1object.getString("Branch_name");
                                if(branch1object.has("latitude")) {
                                    latitude = branch1object.getDouble("latitude");
                                }
                                if(branch1object.has("latitude")) {
                                    longitude = branch1object.getDouble("longitude");
                                }
                                RewardSystemAvailabilty = Integer.valueOf(branch1object.getString("RewardSystemAvailabilty"));
                            }

                            placeName.setText(PLaceName);
                            collapsingToolbarLayout.setTitle(Branch_name);
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
                            if (RewardSystemAvailabilty==1) {
                                //hena3
                                branch3list = new ArrayList<>();
                                branch3recycleView = (RecyclerView) findViewById(R.id.recyclerView3);
                                branch3recycleView.setHasFixedSize(true);
                                branch3recycleView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


                                loadbranch3(branch_id);
                            }
                            else if(RewardSystemAvailabilty!=1){
                                TextView bran;
                                bran=(TextView) findViewById(R.id.bran);
                                bran.setText(" No Available Rewards Now  ");
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BranchActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }

        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Branch_Id",""+branch_id);


                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
        return RewardSystemAvailabilty;
    }



    //hena2
    public void loadbranch2(final int branch_id ) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Branch2Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray branches2 =new JSONArray(response);

                            for(int i=0; i<branches2.length();i++){
                                JSONObject branch2object = branches2.getJSONObject(i);

                                String Offer_image=branch2object.getString("Offer_image");
                                String Title=branch2object.getString("Title");
                                int points=branch2object.getInt("points");
                                String StartDate=branch2object.getString("StartDate");
                                String EndDate=branch2object.getString("EndDate");

                                Branch2 branch2=new Branch2(Offer_image,StartDate,EndDate,Title,points);
                                branch2list.add(branch2);
                            }

                            adapter2 =new Branch2Adapter(BranchActivity.this ,branch2list);
                            branch2recycleView.setAdapter(adapter2);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BranchActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }

        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Branch_Id",""+branch_id);


                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }




    //hena3
    public void loadbranch3(final int branch_id ) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Branch3Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray branches3 =new JSONArray(response);

                            for(int i=0; i<branches3.length();i++){
                                JSONObject branch3object = branches3.getJSONObject(i);

                                String Reward_image=branch3object.getString("Reward_image");
                                String Title=branch3object.getString("Title");
                                int No_OF_points=branch3object.getInt("No_OF_points");
                                String StartDate=branch3object.getString("StartDate");
                                String EndDate=branch3object.getString("EndDate");

                                Branch3 branch3=new Branch3(Title,No_OF_points,Reward_image,StartDate,EndDate);
                                branch3list.add(branch3);
                            }

                            adapter3 =new Branch3Adapter(BranchActivity.this ,branch3list);
                            branch3recycleView.setAdapter(adapter3);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BranchActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }

        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Branch_Id",""+branch_id);


                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }



}
