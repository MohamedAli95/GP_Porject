package com.example.shika.boo;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.github.siyamed.shapeimageview.RoundedImageView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import models.nearbyoffers;

public class Offer_Page extends AppCompatActivity implements OnMapReadyCallback {

  //  private static RecyclerView recyclerView;

    //String and Integer array for Recycler View Items
  //  public static final String[] TITLES= {"Hood","Full Sleeve Shirt","Shirt","Jean Jacket","Jacket"};
  //  public static final Integer[] IMAGES= {R.drawable.one,R.drawable.two,R.drawable.three,R.drawable.four,R.drawable.five,};
    private static final LatLng MOUNTAIN_VIEW = new LatLng(37.4, -122.1);
    SharedPreferences sharedpreferences;

    TextView Startdate;
    TextView Enddate;
    TextView No_ofpoints;
    TextView Title;
    nearbyoffers Theoffer;
    int Item_id ;
    CollapsingToolbarLayout collapsingToolbarLayout; /*ht7tang de*/
    RoundedImageView placeimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer__page);
        Startdate= (TextView)findViewById(R.id.item_startdate);
        Enddate= (TextView)findViewById(R.id.item_enddate);
        Title =(TextView)findViewById(R.id.item_title);
        No_ofpoints=(TextView)findViewById(R.id.no_of_points);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar1);// w de
        placeimage= (RoundedImageView) findViewById(R.id.Place_image_notf);
        Intent intent=getIntent();
        Bundle extras = intent.getExtras();

        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String json = sharedpreferences.getString("ExistedOffers", "");
        Gson gson = new Gson();
        Type type = new TypeToken<List<nearbyoffers>>(){}.getType();
        ArrayList<nearbyoffers> nearoffers= gson.fromJson(json, type);
        Item_id=extras.getInt("k");
        Item_id=extras.getInt("k");
        for(int i=0;i<nearoffers.size();i++){
            if(Item_id==nearoffers.get(i).getOffer_id())
                Theoffer=nearoffers.get(i);

        }
        if(Theoffer!=null){
            Startdate.setText(Theoffer.getStartdate());
            Enddate.setText(Theoffer.getEnddate());
            Title.setText(Theoffer.getTitle()); // de 3shan el asm ex(zara brand)
            No_ofpoints.setText(String.valueOf(Theoffer.getNo_ofpoints()));
            collapsingToolbarLayout.setTitle(Theoffer.getBranch_name());
            Picasso.get().load("http://gp.sendiancrm.com/offerall/images/20180529084501.jpg").into(new Target() {
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
            }); // de 3shan el sora el back ground
            Picasso.get().load("http://gp.sendiancrm.com/offerall/images/20180529084501.jpg").into(placeimage);
        }



     /*   TextView textView = (TextView) findViewById(R.id.original);
        textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);*/

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
     //   initViews();
     //   populatRecyclerView();
    }

 /*   private void initViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//Set Back Icon on Activity


        recyclerView = (RecyclerView)
                findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        //Set RecyclerView type according to intent value

            getSupportActionBar().setTitle("Staggered GridLayout Manager");
            recyclerView
                    .setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));// Here 2 is no. of columns to be displayed

        }

    private void populatRecyclerView() {
        ArrayList<Data_Model> arrayList = new ArrayList<>();
        for (int i = 0; i < TITLES.length; i++) {
            arrayList.add(new Data_Model(TITLES[i],IMAGES[i]));
        }
        RecyclerView_Adapter  adapter = new RecyclerView_Adapter(Offer_Page.this, arrayList);
        recyclerView.setAdapter(adapter);// set adapter on recyclerview
        adapter.notifyDataSetChanged();// Notify the adapter

    }*/

    public boolean googleServicesAvailable(){
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if(isAvailable == ConnectionResult.SUCCESS){
            return true;
        }else if(api.isUserResolvableError(isAvailable)){
            Dialog dialog = api.getErrorDialog(this,isAvailable,0);
            dialog.show();
        }else{
            Toast.makeText(this, "can't connect to play services", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    public void onMapReady(GoogleMap map) {

        Location l= new Location("");
        l.setLongitude(Theoffer.getLongitude());
        l.setLongitude(Theoffer.getLatitude());
        LatLng latLng = new LatLng(Theoffer.getLatitude(),Theoffer.getLongitude());
     

        map.addMarker(new MarkerOptions().position(latLng));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
