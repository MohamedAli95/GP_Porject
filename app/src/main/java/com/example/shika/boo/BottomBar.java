package com.example.shika.boo;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BottomBar extends Fragment {

    private TextView infoTextView;
    private BottomNavigationView bottomNavigationView;
    Intent too;

    public BottomBar() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        bottomNavigationView.getMenu().findItem(R.id.camaraItem).setChecked(true);

        return inflater.inflate(R.layout.fragment_bottom_bar, container, false);
    }

}
