package com.example.shika.boo;

/**
 * Created by Shika on 06/03/2018.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by SONU on 25/09/15.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder  {
    // View holder for gridview recycler view as we used in listview
    public TextView title,ofer,duration;
    public ImageView imageview;




    public RecyclerViewHolder(View view) {
        super(view);
        // Find all views ids

        this.title = (TextView) view
                .findViewById(R.id.title);
        this.ofer = (TextView) view
                .findViewById(R.id.offer);
        this.duration = (TextView) view
                .findViewById(R.id.dur);
        this.imageview = (ImageView) view
                .findViewById(R.id.image);


    }




}
