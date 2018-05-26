/*
 * Copyright (c) 2016 Lung Razvan
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.example.shika.boo;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FragmentD extends android.support.v4.app.Fragment  {
SimpleAdapter simpleAdapter;
    int img;
    public FragmentD(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vv = inflater.inflate(R.layout.fragment_d, container, false);
String[] dates= {"Sunday, April 22","Thursday, April 25","Monday, july 7","Sunday, March 14","Saturday, January 10"};
String [] details={"1 star earned from purchase","1 star earned from purchase","1 star earned from purchase",
        "1 star earned from purchase","1 star earned from purchase"};
 img = R.drawable.starb;

        // Each row in the list stores country name, currency and flag
        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < 5; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("txt",  details[i]);
            hm.put("like", dates[i]);
            aList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = {"like","txt"};

        // Ids of views in listview_layout
        int[] to = {R.id.starid,R.id.det};

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), aList, R.layout.profile_rewards_list, from, to);

        ListView  lv= (ListView) vv.findViewById(R.id.lost);
        // Setting the adapter to the listView
        lv.setAdapter(adapter);



   //     ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_expandable_list_item_1,values);


        // Setting the adapter to the listView
    //    lv.setAdapter(arrayAdapter);
        return vv;
    }

}
