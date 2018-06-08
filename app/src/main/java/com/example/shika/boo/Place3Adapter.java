package com.example.shika.boo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import models.Place3;

public class Place3Adapter extends RecyclerView.Adapter<Place3Adapter.Place3ViewHolder> {

    private Context mCtx;
    private List<Place3> place3list;

    public Place3Adapter(Context mCtx, List<Place3> place3list) {
        this.mCtx = mCtx;
        this.place3list = place3list;
    }

    @NonNull
    @Override
    public Place3Adapter.Place3ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.place3adapter, null);
        Place3ViewHolder holder = new Place3ViewHolder(view);
        return holder;
    }


    public void onBindViewHolder(@NonNull Place3Adapter.Place3ViewHolder holder, int position) {
        Place3 place3 =place3list.get(position);

        Glide.with(mCtx)
                .load(place3.getMenu_Image())
                .into(holder.menuImage);
        holder.menuTitle.setText(place3.getMenu_Title());
        holder.menuPrice.setText(String.valueOf(place3.getMenu_Price()));
    }

    @Override
    public int getItemCount() {
        return place3list.size();
    }

    class Place3ViewHolder extends RecyclerView.ViewHolder{

        TextView menuTitle;
        TextView menuPrice;
        ImageView menuImage;
        public Place3ViewHolder(View itemView) {
            super(itemView);

            menuTitle =itemView.findViewById(R.id.menuTitle);
            menuPrice =itemView.findViewById(R.id.menuPrice);
            menuImage =itemView.findViewById(R.id.menuImage);
        }
    }
}
