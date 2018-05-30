package com.example.shika.boo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MerchantMenu extends AppCompatActivity {

    private static final String productURL = "http://192.168.1.3/LBA/api.php";

    List<Merchant_Menu> productList;
    RecyclerView recyclerView;
    View vv;

    List<Merchant_Menu> ListOfdataAdapter;


    String HTTP_JSON_URL = "http://gp.sendiancrm.com/offerall/ImageJsonData.php";
    String Image_Name_JSON = "image_name";
    String Image_URL_JSON = "image_path";
    JsonArrayRequest RequestOfJSonArray ;
    RequestQueue requestQueue ;
    View view ;
    int RecyclerViewItemPosition ;
    RecyclerView.LayoutManager layoutManagerOfrecyclerView;
    RecyclerView.Adapter recyclerViewadapter;
    ArrayList<String> ImageTitleNameArrayListForClick;
    List<Merchant_Menu> lstBook ;
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_menu);
/*
        lstBook = new ArrayList<>();
        lstBook.add(new Merchant_Menu(R.drawable.mu1));
        lstBook.add(new Merchant_Menu(R.drawable.mu2));
        lstBook.add(new Merchant_Menu(R.drawable.mu3));
        lstBook.add(new Merchant_Menu(R.drawable.mu4));
        lstBook.add(new Merchant_Menu(R.drawable.mu2));
        lstBook.add(new Merchant_Menu(R.drawable.mu1));



        RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id);
        Merchant_RecyclerViewAdapter myAdapter = new Merchant_RecyclerViewAdapter(this,lstBook);
        myrv.setLayoutManager(new GridLayoutManager(this,2));
        myrv.setAdapter(myAdapter);
*/

        ImageTitleNameArrayListForClick = new ArrayList<>();

        ListOfdataAdapter = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_id);

        recyclerView.setHasFixedSize(true);

        layoutManagerOfrecyclerView = new LinearLayoutManager(this);

     //   recyclerView.setLayoutManager(layoutManagerOfrecyclerView);

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        JSON_HTTP_CALL();

        // Implementing Click Listener on RecyclerView.
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(MerchantMenu.this, new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent motionEvent) {

                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                view = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if(view != null && gestureDetector.onTouchEvent(motionEvent)) {

                    //Getting RecyclerView Clicked Item value.
                    RecyclerViewItemPosition = Recyclerview.getChildAdapterPosition(view);

                    // Showing RecyclerView Clicked Item value using Toast.
                    Toast.makeText(MerchantMenu.this, ImageTitleNameArrayListForClick.get(RecyclerViewItemPosition), Toast.LENGTH_LONG).show();
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


    }

    public void JSON_HTTP_CALL(){

        RequestOfJSonArray = new JsonArrayRequest(HTTP_JSON_URL,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        ParseJSonResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue = Volley.newRequestQueue(MerchantMenu.this);

        requestQueue.add(RequestOfJSonArray);
    }

    public void ParseJSonResponse(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            Merchant_Menu GetDataAdapter2 = new Merchant_Menu();

            JSONObject json = null;
            try {

                json = array.getJSONObject(i);


                // Adding image title name in array to display on RecyclerView click event.
                ImageTitleNameArrayListForClick.add(json.getString(Image_Name_JSON));

                GetDataAdapter2.setImageUrl(json.getString(Image_URL_JSON));

            } catch (JSONException e) {

                e.printStackTrace();
            }
            ListOfdataAdapter.add(GetDataAdapter2);
        }

        recyclerViewadapter = new Merchant_RecyclerViewAdapter(ListOfdataAdapter, this);

        recyclerView.setAdapter(recyclerViewadapter);
    }
}