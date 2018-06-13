package com.example.shika.boo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Place2;
import models.SearchPlace;

public class Search_Place extends AppCompatActivity {


    RecyclerView searchrecycleView;
    SearchAdapter adapter;
    List<SearchPlace> searchlist;
    String Querry;
    private static final String SearchUrl = "http://gp.sendiancrm.com/offerall/searchQuery.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__place);
        Intent intent1=getIntent();
        Bundle extras = intent1.getExtras();

        Querry = extras.getString("Querry");
        Querry = extras.getString("Querry");

        searchlist =new ArrayList<>();
        searchrecycleView = (RecyclerView) findViewById(R.id.recyclerViewss) ;
        searchrecycleView.setHasFixedSize(true);
        searchrecycleView.setLayoutManager(new LinearLayoutManager(this));

        loadsearch(Querry);

    }


    //hena2
    public void loadsearch(final String Querry ) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SearchUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray search =new JSONArray(response);

                            for(int i=0; i<search.length();i++){
                                JSONObject searchobject = search.getJSONObject(i);
                                int Place_ID =searchobject.getInt("Place_ID");

                                String PLaceName=searchobject.getString("PLaceName");
                                String Place_LogoPhoto=searchobject.getString("Place_LogoPhoto");


                                SearchPlace Search =new SearchPlace(Place_ID,PLaceName,Place_LogoPhoto);
                                searchlist.add(Search);
                            }

                            adapter =new SearchAdapter(Search_Place.this ,searchlist);
                            searchrecycleView.setAdapter(adapter);
                            adapter.setOnClick(new SearchAdapter.OnItemClicked(){
                                @Override
                                public void onItemClick(int position,int Place_ID) {
                                    Intent intent = new Intent(getApplicationContext(),Try.class);
                                    intent.putExtra("place_id",Place_ID);
                                    //intent.putExtra("place_id",place_id);
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
                Toast.makeText(Search_Place.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }

        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("searchQuery",Querry);


                return params;
            }
        };
        Volley.newRequestQueue(this).add(stringRequest);
    }

}
