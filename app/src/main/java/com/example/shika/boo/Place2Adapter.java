package com.example.shika.boo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import models.Place2;

public class Place2Adapter extends RecyclerView.Adapter<Place2Adapter.Place2ViewHolder> {

    private Context mCtx;
    private List<Place2> place2list;
    private OnItemClicked onClick;


    public Place2Adapter(Context mCtx, List<Place2> place2list) {
        this.mCtx = mCtx;
        this.place2list = place2list;
    }
    public interface OnItemClicked {
        void onItemClick(int position,int branchid);

    }

    @NonNull
    @Override
    public Place2Adapter.Place2ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.place2adapter, null);
        Place2ViewHolder holder = new Place2ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Place2Adapter.Place2ViewHolder holder, final int position) {
     Place2 place2 =place2list.get(position);
     final int branchid = place2.getBranch_id();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(position,branchid);

            }
        });

     holder.textViewTitle.setText(place2.getBranch_name());
    }

    @Override
    public int getItemCount() {
        return place2list.size();
    }

    class Place2ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewTitle;

        public Place2ViewHolder(View itemView) {
            super(itemView);

            textViewTitle =itemView.findViewById(R.id.textViewTitle);
        }
    }
    public void setOnClick(OnItemClicked onClick)
    {
        this.onClick=onClick;
    }
}
