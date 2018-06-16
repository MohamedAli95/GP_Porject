package com.example.shika.boo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MerchantMenu extends AppCompatActivity {

    private static final String productURL = "http://192.168.1.3/LBA/api.php";

    List<Merchant_Menu> productList;
    RecyclerView recyclerView;
    View vv;

    List<String> ListOfdataAdapter;

    ArrayList<String> listBranchNames ;

    String HTTP_JSON_URL = "http://gp.sendiancrm.com/offerall/ImageJsonData.php";
    String Image_Name_JSON = "image_name";
    String Image_URL_JSON = "image";
    JsonArrayRequest RequestOfJSonArray ;
    RequestQueue requestQueue ;
    View view ;

    SharedPreferences sharedPreferences ;
    android.app.AlertDialog alertDialog;
    //arrayAdaptorHandle adaptor;
    StringRequest request ;
    private static  final String listOfBranchesURL = "http://gp.sendiancrm.com/offerall/ImageJsonData.php";

    int RecyclerViewItemPosition ;
    RecyclerView.LayoutManager layoutManagerOfrecyclerView;
    RecyclerView.Adapter recyclerViewadapter;
    ArrayList<String> ImageTitleNameArrayListForClick;
    List<Merchant_Menu> lstBook ;
    int placeId;
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
        productList = new ArrayList<>();
        ImageTitleNameArrayListForClick = new ArrayList<>();

        ListOfdataAdapter = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_id);

        recyclerView.setHasFixedSize(true);

        layoutManagerOfrecyclerView = new LinearLayoutManager(this);

     //   recyclerView.setLayoutManager(layoutManagerOfrecyclerView);

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPreferences.getBoolean("place logged in", false)) {
            placeId = sharedPreferences.getInt("PID", Integer.parseInt("0"));
        }

       // showListOfBranchesFromDB(placeId);
     //   JSON_HTTP_CALL();

        // Implementing Click Listener on RecyclerView.
  /*      recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

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

*/

        StringRequest stringRequest=new StringRequest(Request.Method.POST, HTTP_JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // System.out.println(response);
                        Toast.makeText(MerchantMenu.this, "تبریک", Toast.LENGTH_LONG).show();
                        try {
                            JSONArray array=new JSONArray(response);
                            for (int i=0; i < array.length(); i++) {
                                JSONObject product=array.getJSONObject(i);

                                productList.add(new Merchant_Menu(
                                        product.getInt("Menu_id"),
                                        product.getString("Menu_Image"),
                                        product.getInt("Place_id")
                                ));

                            }
                            recyclerViewadapter = new Merchant_RecyclerViewAdapter(productList, MerchantMenu.this);

                            recyclerView.setAdapter(recyclerViewadapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MerchantMenu.this, "خطای اتصال به شبکه", Toast.LENGTH_LONG).show();
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


    }
