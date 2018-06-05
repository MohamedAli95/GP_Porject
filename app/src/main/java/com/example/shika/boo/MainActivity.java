package com.example.shika.boo;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import models.categorypage;
import models.nearbyoffers;
import models.placeCategorypage;

public class MainActivity extends AppCompatActivity {

    public static final String ORIENTATION = "orientation";
    private WebView mwebView;
    private RecyclerView mRecyclerView;
    private boolean mHorizontal;
    android.widget.Toolbar toolbar;

    private FrameLayout view_stub; //This is the framelayout to keep your content view
    private NavigationView navigation_view; // The new navigation view from Android Design Library. Can inflate menu resources. Easy
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Menu drawerMenu;
    private cateogry catg;
    private placecateogry placecatg;

    String Type;
    String Type1;
    ArrayList<categorypage> catgorylist;
    ArrayList<placeCategorypage> placecatgorylist;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        catgorylist= new ArrayList<categorypage>();
        placecatgorylist=new ArrayList<placeCategorypage>();

        catg=new cateogry();
        placecatg= new placecateogry();
        Type="CategoryData";
        catg.execute(Type);
        Type1="placedata";
        placecatg.execute(Type1);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mRecyclerView.setHasFixedSize(true);

        if (savedInstanceState == null) {
            mHorizontal = true;
        } else {
            mHorizontal = savedInstanceState.getBoolean(ORIENTATION);
        }




    }

    /*public void card_trans(View view) {
        Intent ba = new Intent(this, Try.class);
        startActivity(ba);
    }*/


    private void setupAdapter() {
        ArrayList<ArrayList<App>> apps = getApps();

        SnapAdapter snapAdapter = new SnapAdapter(this);
        if (mHorizontal) {
            for(int i=0 ;i<catgorylist.size();i++){
                for(int j=0 ;j<apps.get(i).size();j++) {
                    if(apps.get(i).get(j).getCategoryid()==catgorylist.get(i).getCategory_id()) {
                        snapAdapter.addSnap(new Snap(Gravity.START, catgorylist.get(i).getCategorytitle(), apps.get(i)));

                        break;

                    }
                }
            }


        } else {

        }

        mRecyclerView.setAdapter(snapAdapter);
    }

    private ArrayList<ArrayList<App>> getApps() {


        ArrayList<ArrayList<App>> apps = new ArrayList<ArrayList<App>>();
        for(int i=0 ;i<catgorylist.size();i++) {
            ArrayList<App> app = new ArrayList<App>();
            for(int j=0 ;j<placecatgorylist.size();j++) {

                if(catgorylist.get(i).getCategory_id()==placecatgorylist.get(j).getCategory_id()) {

                    app.add(new App(placecatgorylist.get(j).getPlacename(),placecatgorylist.get(j).getLogoUrl() , String.valueOf(placecatgorylist.get(j).getRate()),placecatgorylist.get(j).getCategory_id(),placecatgorylist.get(j).getPlace_id()));


                }

            }
            apps.add(app);


        }

        return apps;
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private class cateogry extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            Log.e("cateogry", "cateogry Started");
            String categoryurl = "http://gp.sendiancrm.com/offerall/category.php";



            if (Type.equals("CategoryData")) {
                try {


                    URL url = new URL(categoryurl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String result = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }
                    Log.e("CategoryResult", result);
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return  null;
        }
        protected void onPostExecute(String result) {


            JSONParser parser = new JSONParser();

            if (Type.equals("CategoryData")) {
                try {
                    if(result.equals("Data not  found")){

                    }
                    else {

                        Object obj = parser.parse(result);
                        JSONArray array= (JSONArray) obj;
                        JSONObject category_obj ;

                        categorypage category;
                        for (int i =0 ; i<array.size();i++)
                        {
                            category_obj= (JSONObject) array.get(i);
                            category=new categorypage();
                            category.setCategorytitle((String)category_obj.get("Name"));
                            category.setCategory_id(Integer.valueOf((String)category_obj.get("Category_ID")));

                            catgorylist.add(category);
                        }




                    }




                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

        }

    }
    private class placecateogry extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... params) {
            Log.e("cateogry", "cateogry Started");
            String placecategoryurl = "http://gp.sendiancrm.com/offerall/categoryPlaces.php";
             if (Type1.equals("placedata")) {
                try {


                    URL url = new URL(placecategoryurl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String result = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }
                    Log.e("placeCategoryResult", result);
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        protected void onPostExecute(String result) {
            JSONParser parser = new JSONParser();
              if (Type1.equals("placedata")) {
                try {
                    if(result.equals("Data not  found")){

                    }
                    else {

                        Object obj = parser.parse(result);
                        JSONArray array= (JSONArray) obj;
                        JSONObject category_obj ;

                        placeCategorypage PLacecategory;
                        for (int i =0 ; i<array.size();i++)
                        {
                            category_obj= (JSONObject) array.get(i);
                            PLacecategory=new placeCategorypage();
                            PLacecategory.setCategory_id(Integer.valueOf((String)category_obj.get("Category_id")));
                            PLacecategory.setLogoUrl((String)category_obj.get("Place_LogoPhoto"));
                            PLacecategory.setPlacename((String)category_obj.get("PLaceName"));

                            if(category_obj.get("PLaceRate")==null){
                                PLacecategory.setRate(0);
                            }
                            else
                                PLacecategory.setRate(Float.valueOf((String)category_obj.get("PLaceRate")));

                            PLacecategory.setPlace_id(Integer.valueOf((String)category_obj.get("Place_ID")));



                            placecatgorylist.add(PLacecategory);
                        }




                    }




                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
            setupAdapter();
        }

        }
        }



