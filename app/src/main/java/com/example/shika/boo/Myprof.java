package com.example.shika.boo;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.squareup.picasso.Picasso;

import angtrim.com.fivestarslibrary.FiveStarsDialog;
import angtrim.com.fivestarslibrary.NegativeReviewListener;
import angtrim.com.fivestarslibrary.ReviewListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class Myprof extends android.app.Fragment {
    private ViewPager viewPager;
  //  FragmentManager fragmentManager = getSupportFragmentManager();
    String[] listItems;
    TextView username;
    TextView email;

    TextView age;
    TextView gender;
    TextView phone;
    SharedPreferences sharedpreferences;
    CollapsingToolbarLayout collapsingToolbarLayout;
    CircleImageView profile_pic;
    ImageButton editprofile;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vi = inflater.inflate(R.layout.activity_myprof, container, false);
      //  getSupportActionBar().hide();
        MapsActivity.btn.setVisibility(View.GONE);



        editprofile= vi.findViewById(R.id.editProfile);
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent in = new Intent(getActivity(),editprofile.class);
                startActivity(in);

            }
        });
        //requestWindowFeature(Window.FEATURE_NO_TITLE);


        username = (TextView) vi.findViewById(R.id.usernametext);
        email = (TextView) vi.findViewById(R.id.useremailtext);

        age = (TextView) vi.findViewById(R.id.agetext);
        gender = (TextView) vi.findViewById(R.id.gendertext);
        phone = (TextView) vi.findViewById(R.id.userphonetext);
        profile_pic=(CircleImageView) vi.findViewById(R.id.profile_image);

        collapsingToolbarLayout = (CollapsingToolbarLayout) vi.findViewById(R.id.collapsing_toolbar);
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(vi.getContext());
        if (sharedpreferences.getBoolean("logged in", false)) {


            username.setText(sharedpreferences.getString("Name", "UserName"));
            collapsingToolbarLayout.setTitle(sharedpreferences.getString("Name", "UserName"));
            email.setText(sharedpreferences.getString("Email", "email"));
            age.setText(Integer.toString(sharedpreferences.getInt("Age", 123)));
            gender.setText(sharedpreferences.getString("gender", "gender"));
            phone.setText(sharedpreferences.getString("phone", "phone"));
            String profilepicurl = sharedpreferences.getString("profilepicture", null);
            Picasso.get().load(profilepicurl).into(profile_pic);
        }


        return vi;
    }
}





