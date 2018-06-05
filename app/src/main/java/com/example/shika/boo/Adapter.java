package com.example.shika.boo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by delaroy on 2/10/17.
 */


public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<App> mApps;
    private boolean mHorizontal;
    private boolean mPager;
    private Context context;
    int place_id;

    public Adapter(boolean horizontal, boolean pager, List<App> apps,Context context) {
        mHorizontal = horizontal;
        mApps = apps;
        mPager = pager;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mPager) {
            return new ViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_pager, parent, false));
        } else {
            return mHorizontal ? new ViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter, parent, false)) :
                    new ViewHolder(LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.adapter_vertical, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
         App app = mApps.get(position);
          place_id=app.getPlace_id();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ba = new Intent(context, Try.class);
                ba.putExtra("place_id",place_id);
                context.startActivity(ba);

            }
        });
        Picasso.get().load(app.getPlaceimage()).into(holder.imageView);
        holder.nameTextView.setText(app.getName());
        holder.ratingTextView.setText(String.valueOf(app.getRating()));

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mApps.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView nameTextView;
        public TextView ratingTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            ratingTextView = (TextView) itemView.findViewById(R.id.ratingTextView);
        }

    }

}