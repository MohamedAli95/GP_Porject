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

import models.Branch2;


public class Branch2Adapter extends RecyclerView.Adapter<Branch2Adapter.branch2ViewHolder> {

    private Context mCtx;
    private List<Branch2> branch2list;

    public Branch2Adapter(Context mCtx, List<Branch2> branch2list) {
        this.mCtx = mCtx;
        this.branch2list = branch2list;
    }

    @NonNull
    @Override
    public Branch2Adapter.branch2ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.branch2adapter, null);
        branch2ViewHolder holder = new branch2ViewHolder(view);
        return holder;
    }


    public void onBindViewHolder(@NonNull Branch2Adapter.branch2ViewHolder holder, int position) {
        Branch2 branch2 =branch2list.get(position);
        Glide.with(mCtx)
                .load(branch2.getOffer_image())
                .into(holder.offerImage);
        holder.offerTitle.setText(branch2.getTitle());
        holder.offerPoints.setText(String.valueOf(branch2.getPoints()));
        holder.startdat.setText(String.valueOf(branch2.getStartDate()));
        holder.enddat.setText(String.valueOf(branch2.getEndDate()));

    }

    @Override
    public int getItemCount() {
        return branch2list.size();
    }

    class branch2ViewHolder extends RecyclerView.ViewHolder{

        TextView startdat,enddat,offerTitle,offerPoints;

        ImageView offerImage;
        public branch2ViewHolder(View itemView) {
            super(itemView);
            offerTitle =itemView.findViewById(R.id.offerTitle);
            offerPoints =itemView.findViewById(R.id.offerPoints);
            startdat =itemView.findViewById(R.id.startdat);
            enddat =itemView.findViewById(R.id.enddat);
            offerImage =itemView.findViewById(R.id.offerImage);
        }
    }
}
