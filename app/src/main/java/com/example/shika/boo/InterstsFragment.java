package com.example.shika.boo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class InterstsFragment extends android.app.Fragment {


    ListView listView;
    ImageButton favbtn;
    // Array of integers points to images stored in .... res/drawable
    int[] flags = new int[]{
            R.drawable.i1,
            R.drawable.i2,
            R.drawable.i3,
            R.drawable.i4,
            R.drawable.i5,
            R.drawable.i6,
            R.drawable.i7,
            R.drawable.i8,
            R.drawable.i9,
            R.drawable.i10,
            R.drawable.i11,
            R.drawable.i12,
            R.drawable.i13,
            R.drawable.i14,
            R.drawable.i15,
            R.drawable.i16,
            R.drawable.i17,
            R.drawable.i18,
            R.drawable.i19,
            R.drawable.i20,
            R.drawable.i21,
            R.drawable.i22

    };

    String[] follow = new String[] {
            "Fast food",
            "Chinese food",
            "Desserts",
            "Vegetables",
            "Casual clothes",
            "Formal clothes",
            "Female clothes",
            "Baby clothes",
            "Perfume",
            "Makeup",
            "Sports",
            "Gym tools",
            "Books",
            "Cars",
            "Bikes",
            "Musical instrument",
            "Male shoes",
            "Female shoes",
            "Bag",
            "Electronics",
            "Games",
            "Glasses"
    };
    public InterstsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vin = inflater.inflate(R.layout.fragment_intersts, container, false);
        MapsActivity.btn.setVisibility(View.GONE);

        // Each row in the list stores country name, currency and flag
        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < 21; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("txt",  follow[i]);
            hm.put("like", Integer.toString(flags[i]));
            aList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = {"like","txt"};

        // Ids of views in listview_layout
        int[] to = {R.id.vec,R.id.txtfollow};

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), aList, R.layout.interests_list, from, to);


        listView = (ListView) vin.findViewById(R.id.intrst_list);






        // Setting the adapter to the listView
        listView.setAdapter(adapter);

        return vin;
    }

}
