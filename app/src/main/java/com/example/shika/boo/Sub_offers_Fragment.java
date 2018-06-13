package com.example.shika.boo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Sub_offers_Fragment extends Fragment {
    private static RecyclerView recyclerView;

    //String and Integer array for Recycler View Items

    public static final String[] TITLES= {"Hood","Full Sleeve Shirt","Shirt","Jean Jacket","Jacket","zamalek","zamalek","zamalek","zamalek","zamalek","zamalek","zamalek","zamalek"};
    public static final String[] OFFER= {"%15 off your purchase","%15 off your purchase","%15 off your purchase","%15 off your purchase","%15 off your purchase","%15 off your purchase",
            "%15 off your purchase","%15 off your purchase","%15 off your purchase","%15 off your purchase","%15 off your purchase","%15 off your purchase","%15 off your purchase"};
    public static final String[] DURATION= {"4 dyas later","4 dyas later","4 dyas later","4 dyas later","4 dyas later","4 dyas later","4 dyas later","4 dyas later","4 dyas later","4 dyas later",
            "4 dyas later","4 dyas later","4 dyas later"};
    public static final Integer[] IMAGES= {R.drawable.one,R.drawable.two,R.drawable.three,R.drawable.four,R.drawable.five,
            R.drawable.r1,R.drawable.r2,R.drawable.r5,R.drawable.r6,
            R.drawable.mu1,R.drawable.mu2,R.drawable.mu3,R.drawable.mu4};

    View v;
    private static String navigateFrom;

    public Sub_offers_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       initViews();
  //      populatRecyclerView();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sub_offers_, container, false);
    }

    private void initViews() {
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);//Set Back Icon on Activity


        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        //Set RecyclerView type according to intent value

      //  getSupportActionBar().setTitle("Staggered GridLayout Manager");
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));// Here 2 is no. of columns to be displayed

    }



    // populate the list view by adding data to arraylist
  /*  private void populatRecyclerView() {
        ArrayList<Data_Model> arrayList = new ArrayList<>();
        for (int i = 0; i < TITLES.length; i++) {
            arrayList.add(new Data_Model(TITLES[i],OFFER[i],DURATION[i],IMAGES[i]));
        }
        RecyclerView_Adapter  adapter = new RecyclerView_Adapter(getActivity(), arrayList);
        recyclerView.setAdapter(adapter);// set adapter on recyclerview
        adapter.notifyDataSetChanged();// Notify the adapter

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
*/
}
