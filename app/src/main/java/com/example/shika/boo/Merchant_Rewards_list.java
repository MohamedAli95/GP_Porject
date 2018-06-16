package com.example.shika.boo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.daimajia.swipe.util.Attributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Merchant_Rewards_list extends AppCompatActivity {
    private TextView tvEmptyTextView;
    private RecyclerView mRecyclerView;
    private java.util.List<YoutuberModel> mDataSet;
    RequestQueue rq;
    RecyclerView.Adapter mAdapter;
    SharedPreferences sharedPreferences;
    int placeId;
    private static final String request_url = "http://gp.sendiancrm.com/offerall/Fetch_Rewards.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant__rewards_list);
        rq = Volley.newRequestQueue(this);

        tvEmptyTextView = (TextView) findViewById(R.id.empty_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDataSet = new ArrayList<>();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.getBoolean("place logged in", false)) {
            placeId = sharedPreferences.getInt("BId", Integer.parseInt("0"));
        }



        loadProducts(placeId);
    /*    if(mDataSet.isEmpty()){
            mRecyclerView.setVisibility(View.GONE);
            tvEmptyTextView.setVisibility(View.VISIBLE);
        }else{
            mRecyclerView.setVisibility(View.VISIBLE);
            tvEmptyTextView.setVisibility(View.GONE);
        }
*/

/*        SwipeRecyclerViewAdapter mAdapter = new SwipeRecyclerViewAdapter(this, mDataSet);

        ((SwipeRecyclerViewAdapter) mAdapter).setMode(Attributes.Mode.Single);

        mRecyclerView.setAdapter(mAdapter);

       mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e("RecyclerView", "onScrollStateChanged");
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });*/
    }
  /*  public void loadData() {

        for (int i = 0; i <= 12; i++) {
            mDataSet.add(new YoutuberModel("Product " + i, "12/8/2018" , "20/5/2019", R.drawable.bb2));

        }
    }
*/

    private void loadProducts(final int place_Id) {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */


        StringRequest stringRequest = new StringRequest(Request.Method.POST,request_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                mDataSet.add(new YoutuberModel(
                                        product.getInt("id"),
                                        product.getString("Title"),
                                        product.getInt("Points_num"),
                                        product.getString("From_date"),
                                        product.getString("To_date"),
                                        product.getString("Reward_image")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            SwipeRecyclerViewAdapter adapter = new SwipeRecyclerViewAdapter(Merchant_Rewards_list.this, mDataSet);
                            mRecyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params=new HashMap<String, String>();

                params.put("ID", Integer.toString(place_Id));


                return params;
            }

        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
}


