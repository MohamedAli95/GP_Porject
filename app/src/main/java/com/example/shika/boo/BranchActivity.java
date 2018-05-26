package com.example.shika.boo;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import angtrim.com.fivestarslibrary.FiveStarsDialog;
import angtrim.com.fivestarslibrary.NegativeReviewListener;
import angtrim.com.fivestarslibrary.ReviewListener;

public class BranchActivity extends AppCompatActivity {
    private ViewPager viewPager;
    FragmentManager fragmentManager = getSupportFragmentManager();
    String[] listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch);
        getSupportActionBar().hide();

        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        Button shrbn = (Button) findViewById(R.id.share_btn);
        shrbn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                listItems = new String[]{"Via Email", "Via Facebook", "Via sms"};
                AlertDialog.Builder mbuilder = new AlertDialog.Builder(BranchActivity.this);
                mbuilder.setTitle("Share branch ");
                mbuilder.setIcon(R.drawable.radi);
                int checkeditem;
                mbuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //  dialogInterface.dismiss();
                    }
                });
                mbuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                mbuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog mDialog = mbuilder.create();
                mDialog.show();
            }

        });


        AHBottomNavigation bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
      //  bottomNavigation.setOnTabSelectedListener(this);

// Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("details", R.drawable.ic_details);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Offers", R.drawable.ic_offer);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Reviews", R.drawable.ic_rev);

// Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);

// Set background color
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#ECECEC"));
        bottomNavigation.setAccentColor(Color.parseColor("#674172"));
        bottomNavigation.setInactiveColor(Color.parseColor("#747474"));
     //   bottomNavigation.setForceTint(true);
        bottomNavigation.setTranslucentNavigationEnabled(true);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
        getSupportFragmentManager().beginTransaction().replace(R.id.cont, new Branch_details_fragment()).commit();

        bottomNavigation.setCurrentItem(0);

        //bottomNavigation.setNotification("1", 3);


        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (position == 0) {
                    Branch_details_fragment crimeFragment = new Branch_details_fragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.cont, crimeFragment).commit();
                } else if (position == 1) {
                    Branch_offers_fragment dramaFragment = new Branch_offers_fragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.cont, dramaFragment).commit();
                } else if (position == 2) {
                    Branch_reviews_fragment documentaryFragment = new Branch_reviews_fragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.cont, documentaryFragment).commit();
                }
                return wasSelected;
            }
            //return true;

        });
        bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
            @Override
            public void onPositionChange(int y) {
                // Manage the new y position
            }
        });

    }
    }





