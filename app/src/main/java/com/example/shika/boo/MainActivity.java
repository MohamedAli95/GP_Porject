package com.example.shika.boo;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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

        setupAdapter();


    }

    public void card_trans(View view){
        Intent ba = new Intent(this,Try.class);
        startActivity(ba);
    }







    private void setupAdapter() {
        List<App> apps = getApps();

        SnapAdapter snapAdapter = new SnapAdapter();
        if (mHorizontal) {
            snapAdapter.addSnap(new Snap(Gravity.CENTER_HORIZONTAL, "Restaurants", apps));
            snapAdapter.addSnap(new Snap(Gravity.START, "Malls", apps));
            snapAdapter.addSnap(new Snap(Gravity.END, "Shops", apps));
            snapAdapter.addSnap(new Snap(Gravity.END, "Markets", apps));
        } else {
            snapAdapter.addSnap(new Snap(Gravity.CENTER_VERTICAL, "Restaurants", apps));
            snapAdapter.addSnap(new Snap(Gravity.TOP, "Snap top", apps));
            snapAdapter.addSnap(new Snap(Gravity.BOTTOM, "Snap bottom", apps));
        }

        mRecyclerView.setAdapter(snapAdapter);
    }

    private List<App> getApps() {
        List<App> apps = new ArrayList<>();
        apps.add(new App("Kentucky Fried Chicken", R.drawable.kfc, "4.6"));
        apps.add(new App("Macdonald", R.drawable.mac, "4.6"));
        apps.add(new App("Dominos pizza", R.drawable.domnos,"4.6"));
        apps.add(new App("Burger King", R.drawable.burger_king_arabic_logo, "4.6"));
        apps.add(new App("Pizza Hut", R.drawable.hut, "4.6"));
        apps.add(new App("Adidas", R.drawable.c4, "4.6"));
        apps.add(new App("H&M", R.drawable.c1, "4.6"));
        apps.add(new App("ZARA", R.drawable.c2, "4.6"));
        apps.add(new App("Lacoste", R.drawable.c7,"4.6"));
        apps.add(new App("W.Degla", R.drawable.c2, "4.6"));
        apps.add(new App("Zamalek SC", R.drawable.z1, "4.6"));
        return apps;
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
