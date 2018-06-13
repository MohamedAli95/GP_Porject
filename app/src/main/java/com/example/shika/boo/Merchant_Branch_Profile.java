package com.example.shika.boo;

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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
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

public class Merchant_Branch_Profile extends AppCompatActivity {
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
    RecyclerView.Adapter recyclerViewadapter,recyclerViewadapter2;
    String cat;
    SharedPreferences sharedPreferences ;
    android.app.AlertDialog alertDialog;
    RecyclerView.LayoutManager layoutManagerOfrecyclerView;

    List<Data_Model> productList,rewardList;
    RecyclerView recyclerView,recyclerView2;
    View vv;

    List<String> ListOfdataAdapter;

    //arrayAdaptorHandle adaptor;
    StringRequest request ;
    RequestQueue requestQueue ;
    private static  final String listOfBranchesURL = "http://gp.sendiancrm.com/offerall/Branch_profile.php";
    private static  final String listOfBranchesURL2 = "http://gp.sendiancrm.com/offerall/Branch_profile2.php";
    public int RewardSystemAvailabilty=0;
    private int branchId ,strSaved  ;
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant__branch__profile);

   /*     View prof = findViewById(R.id.merchant_profile);
        TextView headerText1 = (TextView) prof.findViewById(R.id.desc);
        TextView headerText2 = (TextView) prof.findViewById(R.id.desc2);
        TextView headerText3 = (TextView) prof.findViewById(R.id.ctg);
        TextView headerText4 = (TextView) prof.findViewById(R.id.ctg2);
        TextView headerText5 = (TextView) prof.findViewById(R.id.bran);
      /*  View xx = (View) prof.findViewById(R.id.branches);
        ViewGroup parent = (ViewGroup) xx.getParent();
        parent.removeView(xx);*/

  /*      headerText1.setText("Category :");
        headerText2.setText("Clothes ");
        headerText3.setText("Reward System Availability :");
        headerText4.setText(" Available ");
        headerText5.setText("Branch Offers ");
*/

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
                        Toast.makeText(Merchant_Branch_Profile.this, "تبریک", Toast.LENGTH_LONG).show();
                        try {
                            JSONArray arr = new JSONArray(response);
                            JSONObject jObj = arr.getJSONObject(0);
                            name = jObj.getString("Branch_name");
                            cat = jObj.getString("Branch_phone");
                            placeimg = jObj.getString("Branch_image");
                            RewardSystemAvailabilty =Integer.valueOf(jObj.getString("RewardSystemAvailabilty"));

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
                            if (RewardSystemAvailabilty==1) {
                                //hena3
                                rewardList = new ArrayList<>();

                                recyclerView2 = (RecyclerView) findViewById(R.id.recyclerview);
                                recyclerView2.setHasFixedSize(true);
                                layoutManagerOfrecyclerView = new LinearLayoutManager(Merchant_Branch_Profile.this);

                                //   recyclerView.setLayoutManager(layoutManagerOfrecyclerView);

                                recyclerView2.setLayoutManager(new GridLayoutManager(Merchant_Branch_Profile.this,2));


                                loadbranch3(branchId);
                            }

                            //    JSONObject array=new JSONObject(response);

                            //    garden =  array.getString("PLaceName");
                            mCollapsingToolbarLayout.setTitle(name);
                            category.setText(cat);
                            Glide.with(Merchant_Branch_Profile.this).load(placeimg)
                                    .apply(new RequestOptions()
                                            .placeholder(R.drawable.placeholder)   // optional
                                            .error(R.drawable.error))
                                    .into(iv);


                          recyclerViewadapter = new RecyclerView_Adapter(Merchant_Branch_Profile.this,productList);

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
                        Toast.makeText(Merchant_Branch_Profile.this, "خطای اتصال به شبکه", Toast.LENGTH_LONG).show();
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
                                        branch3object.getInt("Points_num"),
                                        branch3object.getString("To_date"),
                                        branch3object.getString("Reward_image")

                                ));


                            }

                            recyclerViewadapter2 = new RecyclerView_Adapter(Merchant_Branch_Profile.this,rewardList);
                            recyclerView2.setAdapter(recyclerViewadapter2);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Merchant_Branch_Profile.this,error.getMessage(),Toast.LENGTH_SHORT).show();
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

}

