package com.example.shika.boo;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import models.nearbyoffers;

//import eu.long1.spacetablayout.SpaceTabLayout;

public class BaseActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    Toolbar toolbar;
    FragmentManager fragmentManager;
    Bundle savedInstanceState;

    private FrameLayout view_stub; //This is the framelayout to keep your content view
    private NavigationView navigation_view; // The new navigation view from Android Design Library. Can inflate menu resources. Easy
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Menu drawerMenu;
    Menu menuoo;
    TextView textCartItemCount;
    int mCartItemCount = 10;
    View actionView;
    ActionBar actionbar;
    TextView textview;
    Menu menuu;
    FrameLayout.LayoutParams layoutparams;
    SharedPreferences sharedpreferences;
    SearchView searchView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);

/*
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
*/
    }


/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

*/
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       MenuInflater mi =  this.getMenuInflater();
       mi.inflate(R.menu.menu_main, menu);

     final MenuItem menuItem = menu.findItem(R.id.action_cart);

        actionView = MenuItemCompat.getActionView(menuItem);
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
   /* public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_cart: {
                PopupMenu pp = new PopupMenu(this,(View)textCartItemCount);
                pp.getMenuInflater().inflate(R.menu.option_submenu, pp.getMenu());
                pp.show();
                return true;
            }
        }



        return super.onOptionsItemSelected(item);
    }

*//*
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_add_contact:
                Toast.makeText(this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_settings:
                Toast.makeText(this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_about_us:
                Toast.makeText(this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);

    }
*/

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.searchzzz);
        SearchManager searchManager = (SearchManager) BaseActivity.this.getSystemService(Context.SEARCH_SERVICE);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(BaseActivity.this.getComponentName()));
            searchView.setIconified(false);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String s) {
                    Log.e("Search",s);
                    Intent intent1 = new Intent(getApplicationContext(),Search_Place.class);
                    intent1.putExtra("Querry",s);
                    startActivity(intent1);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
        return true;
    }

    //  this is to access hardware menu button
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU:
                View menuItemView = findViewById(R.id.action_dropdown);
                PopupMenu popupMenu = new PopupMenu(this, menuItemView);
                popupMenu.getMenuInflater().inflate(R.menu.option_submenu,
                        popupMenu.getMenu());
                popupMenu
                        .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            public boolean onMenuItemClick(MenuItem item) {
                                Toast.makeText(getApplicationContext(),
                                        "You Clicked : " + item.getTitle(),
                                        Toast.LENGTH_SHORT).show();
                                return true;
                            }
                        });
                popupMenu.show();
                return true;
        }
        return super.onKeyUp(keyCode, event);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_dropdown:
                ArrayList<Integer> Items_id= new ArrayList<Integer>();
                View menuItemView = findViewById(R.id.action_dropdown);
                PopupMenu popupMenu = new PopupMenu(this, menuItemView);
                String json = sharedpreferences.getString("ExistedOffers", "");
                Gson gson = new Gson();
                Type type = new TypeToken<List<nearbyoffers>>(){}.getType();
                ArrayList<nearbyoffers> nearoffers= gson.fromJson(json, type);
                if(nearoffers!=null) {
                    for (int i = 0; i < nearoffers.size(); i++) {
                        Items_id.add(i);
                        popupMenu.getMenu().add(0, nearoffers.get(i).getOffer_id(), Menu.NONE, nearoffers.get(i).getTitle());

                    }
                }


                popupMenu.getMenuInflater().inflate(R.menu.option_submenu,
                        popupMenu.getMenu());
                popupMenu
                        .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            public boolean onMenuItemClick(MenuItem item) {



                                Intent nn = new Intent(getApplicationContext(),Offer_Page.class);
                                Log.e("item_id", String.valueOf(item.getItemId()));
                                int item_id=item.getItemId();
                                nn.putExtra("k", item_id);
                                startActivity(nn);

                                return true;
                            }
                        });
                popupMenu.show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setupBadge() {

        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }

    }

/*
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
*/


}
