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


import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FragmentE extends android.support.v4.app.Fragment {
    SimpleAdapter simpleAdapter;
    int img;
    FloatingActionButton fab;
    public FragmentE() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vv = inflater.inflate(R.layout.fragment_e, container, false);
        String[] txv = {"KFC", "Code", "Virgin", "blue line", "Box"};
        int[] img = new int[]{
                R.drawable.b1,
                R.drawable.b5,
                R.drawable.b2,
                R.drawable.b11,
                R.drawable.b13};

        // Each row in the list stores country name, currency and flag
        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < 5; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("like", Integer.toString(img[i]));
            aList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = {"like"};

        // Ids of views in listview_layout
        int[] to = {R.id.logo};

        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), aList, R.layout.profile_favorites_list, from, to);

        ListView lv = (ListView) vv.findViewById(R.id.lst_fav);
        // Setting the adapter to the listView
        lv.setAdapter(adapter);

         fab = vv.findViewById(R.id.fab);

        fab.setOnTouchListener(new View.OnTouchListener() {
            PointF DownPT = new PointF(); PointF StartPT = new PointF();
            @Override public boolean onTouch(View v, MotionEvent event) {
                int eid = event.getAction();
                switch (eid) {
                    case MotionEvent.ACTION_MOVE : PointF mv = new PointF( event.getX() - DownPT.x, event.getY() - DownPT.y);
                    fab.setX((int)(StartPT.x+mv.x));
                    fab.setY((int)(StartPT.y+mv.y));
                    StartPT = new PointF( fab.getX(), fab.getY() );
                    break;
                    case MotionEvent.ACTION_DOWN : DownPT.x = event.getX(); DownPT.y = event.getY();
                    StartPT = new PointF( fab.getX(), fab.getY() );
                    break; case MotionEvent.ACTION_UP : break;
                    default : break;
                } return true; } });

         fab.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent in = new Intent(getActivity(),Favourites.class);
                 startActivity(in);
             }
         });



        //     ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_expandable_list_item_1,values);


        // Setting the adapter to the listView
        //    lv.setAdapter(arrayAdapter);
        return vv;
    }






}
