package com.example.shika.boo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Merchant_Profile extends AppCompatActivity {

    public static final String ORIENTATION = "orientation";
    private WebView mwebView;
    private RecyclerView mRecyclerView;
    private boolean mHorizontal;
    List<App> apps;
    Toolbar toolbar;

    private FrameLayout view_stub; //This is the framelayout to keep your content view
    private NavigationView navigation_view; // The new navigation view from Android Design Library. Can inflate menu resources. Easy
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Menu drawerMenu;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant__profile);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
//tv = (TextView) findViewById(R.id.bran);
        if (savedInstanceState == null) {
            mHorizontal = true;
        } else {
            mHorizontal = savedInstanceState.getBoolean(ORIENTATION);
        }



        setupAdapter();

    }

    public void branch_trans(View view){
        Intent ba = new Intent(this,BranchActivity.class);
        startActivity(ba);
    }

    private void setupAdapter() {
        List<App> apps = getApps();

        SnapAdapter snapAdapter = new SnapAdapter();
        if (mHorizontal) {
            snapAdapter.addSnap(new Snap(Gravity.CENTER, "", apps));

        } else {
            snapAdapter.addSnap(new Snap(Gravity.CENTER_VERTICAL, "Snap center", apps));
            snapAdapter.addSnap(new Snap(Gravity.TOP, "Snap top", apps));
            snapAdapter.addSnap(new Snap(Gravity.BOTTOM, "Snap bottom", apps));
        }

        mRecyclerView.setAdapter(snapAdapter);
    }

    private List<App> getApps() {
        apps = new ArrayList<>();
        apps.add(new App("Zara le merange", R.drawable.new1, "22 street ,EL Dokki   (Cairo)"));
        apps.add(new App("Zara do2do2", R.drawable.new2, "22 street ,EL Dokki   (Cairo)"));
        apps.add(new App("Modern zara", R.drawable.new3, "22 street ,EL Dokki   (Cairo)"));
        apps.add(new App("Zara females", R.drawable.new4, "22 street ,EL Dokki   (Cairo)"));
        apps.add(new App("Zara community", R.drawable.new6, "22 street ,EL Dokki   (Cairo)"));
        return apps;
    }


}
