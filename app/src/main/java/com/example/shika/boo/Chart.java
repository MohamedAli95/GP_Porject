package com.example.shika.boo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.communication.IOnItemFocusChangedListener;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import org.eazegraph.showcase.R;

public class Chart extends AppCompatActivity {
    List<Integer> apps;
    Toolbar toolbar;
    String name,placeimg;
    private FrameLayout view_stub; //This is the framelayout to keep your content view
    private NavigationView navigation_view; // The new navigation view from Android Design Library. Can inflate menu resources. Easy
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Menu drawerMenu;
    TextView tv,category,pass;
    RecyclerView.Adapter recyclerViewadapter;
    String cat,password,email;
    SharedPreferences sharedPreferences ;
    android.app.AlertDialog alertDialog;
    RecyclerView.LayoutManager layoutManagerOfrecyclerView;
int placeId;
    List<Merchant_Menu> productList;
    RecyclerView recyclerView;
    View vv;
int ones = 0;
int zeroes= 0;
    List<String> ListOfdataAdapter;

    //arrayAdaptorHandle adaptor;
    StringRequest request ;
    RequestQueue requestQueue ;
    private static  final String listOfBranchesURL = "http://gp.sendiancrm.com/offerall/Place_profile.php";
    private static  final String listOfBranchesURL2 = "http://gp.sendiancrm.com/offerall/showListOfBranches.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

       // tv = (TextView) findViewById(R.id.za);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.getBoolean("place logged in", false)) {
            placeId = sharedPreferences.getInt("PID", Integer.parseInt("0"));
        }
        apps = new ArrayList<>();



        StringRequest stringRequest = new StringRequest(Request.Method.POST, listOfBranchesURL2 ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Chart.this, "branches response", Toast.LENGTH_LONG).show();

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
                Toast.makeText(Chart.this,error.getMessage(),Toast.LENGTH_SHORT).show();
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


//loadData();
    }

  /*  private void loadData() {
        PieChart mPieChart = (PieChart) findViewById(R.id.piechart);

        mPieChart.addPieSlice(new PieModel("Freetime", 15, Color.parseColor("#FE6DA8")));
        mPieChart.addPieSlice(new PieModel("Sleep", 25, Color.parseColor("#56B7F1")));
        mPieChart.addPieSlice(new PieModel("Work", 35, Color.parseColor("#CDA67F")));
        mPieChart.addPieSlice(new PieModel("Eating", 9, Color.parseColor("#FED70E")));

        mPieChart.startAnimation();

        mPieChart.setOnItemFocusChangedListener(new IOnItemFocusChangedListener() {
            @Override
            public void onItemFocusChanged(int _Position) {
//                Log.d("PieChart", "Position: " + _Position);
            }
        });


    }
*/



  private PieChart mPieChart;
}