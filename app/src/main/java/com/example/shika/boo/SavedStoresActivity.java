package com.example.shika.boo;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SavedStoresActivity extends AppCompatActivity {
    SimpleAdapter simpleAdapter;
    int img;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_stores);


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
        SimpleAdapter adapter = new SimpleAdapter(this, aList, R.layout.profile_favorites_list, from, to);

        ListView lv = (ListView) findViewById(R.id.lst_fav);
        // Setting the adapter to the listView
        lv.setAdapter(adapter);

        fab = findViewById(R.id.fab);

        fab.setOnTouchListener(new View.OnTouchListener() {
            PointF DownPT = new PointF(); PointF StartPT = new PointF();
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
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
                Intent in = new Intent(SavedStoresActivity.this,Favourites.class);
                startActivity(in);
            }
        });



    }
}
