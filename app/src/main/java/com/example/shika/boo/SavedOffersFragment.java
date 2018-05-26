package com.example.shika.boo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import angtrim.com.fivestarslibrary.FiveStarsDialog;
import angtrim.com.fivestarslibrary.NegativeReviewListener;
import angtrim.com.fivestarslibrary.ReviewListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class SavedOffersFragment extends android.app.Fragment {

    List<Product> productList;

    //the recyclerview
    RecyclerView recyclerView;
    public SavedOffersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vo= inflater.inflate(R.layout.fragment_contact_us, container, false);
        recyclerView = (RecyclerView) vo.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        MapsActivity.btn.setVisibility(View.GONE);



        //initializing the productlist
        productList = new ArrayList<>();


        //adding some items to our list
        productList.add(
                new Product(
                        1,
                        "T-shirt for Men - Half sleeve",
                        "EL Salam city.. beside bus station",
                        60.6,
                        49.99,
                        R.drawable.one));

        productList.add(
                new Product(
                        1,
                        "Hoodie with pant ",
                        "20 street , EL Maadi",
                        130.2,
                        110.7,
                        R.drawable.two));

        productList.add(
                new Product(
                        1,
                        "Coffe Mix Packet",
                        "14 street , EL Tahrir square",
                        83.8,
                        60.4,
                        R.drawable.three));

        ProductAdapter adapter = new ProductAdapter(getActivity(), productList);

        recyclerView.setAdapter(adapter);


        return vo;
    }

}
