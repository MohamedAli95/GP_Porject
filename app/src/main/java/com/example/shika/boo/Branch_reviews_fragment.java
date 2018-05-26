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
public class Branch_reviews_fragment extends Fragment {
    ListView listView;
    ImageButton favbtn;
    // Array of integers points to images stored in .... res/drawable


    public Branch_reviews_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vr= inflater.inflate(R.layout.fragment_branch_reviews_fragment, container, false);
     /*   String[] flags = new String[]{
                "shika",
                "Mohamed shika",
                "mahmoud"
        };

        String[] follow = new String[] {
                "Awesome branch , i have many assets from\n" +
                        "it's offers .. contuniue .",
                "Awesome branch , i have many assets from\n" +
                        "it's offers .. contuniue .",
                "Awesome branch , i have many assets from\n" +
                        "it's offers .. contuniue .",
                "Awesome branch , i have many assets from\n" +
                        "it's offers .. contuniue .",
                "Awesome branch , i have many assets from\n" +
                        "it's offers .. contuniue .",
                "Awesome branch , i have many assets from\n" +
                        "it's offers .. contuniue .",
                "Awesome branch , i have many assets from\n" +
                        "it's offers .. contuniue .",
                "Awesome branch , i have many assets from\n" +
                        "it's offers .. contuniue .",
                "Awesome branch , i have many assets from\n" +
                        "it's offers .. contuniue ."
        };
        // Each row in the list stores country name, currency and flag
        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < 3; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("like", flags[i]);
            aList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = {"like"};

        // Ids of views in listview_layout
        int[] to = {R.id.nameo};

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), aList, R.layout.reviews_list, from, to);


        listView = (ListView) vr.findViewById(R.id.rev_list);

        // Setting the adapter to the listView
        listView.setAdapter(adapter);

*/
        return vr;
    }

}
