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

import models.Branch3;


public class Branch3Adapter extends RecyclerView.Adapter<Branch3Adapter.branch3ViewHolder> {

    private Context mCtx;
    private List<Branch3> branch3list;

    public Branch3Adapter(Context mCtx, List<Branch3> branch3list) {
        this.mCtx = mCtx;
        this.branch3list = branch3list;
    }

    @NonNull
    @Override
    public Branch3Adapter.branch3ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.branch3adapter, null);
        branch3ViewHolder holder = new branch3ViewHolder(view);
        return holder;
    }


    public void onBindViewHolder(@NonNull Branch3Adapter.branch3ViewHolder holder, int position) {
        Branch3 branch3 =branch3list.get(position);
        Glide.with(mCtx)
                .load(branch3.getReward_image())
                .into(holder.rewardImage);
        holder.rewardTitle.setText(branch3.getTitle());
        holder.rewardPoints.setText(String.valueOf(branch3.getNo_OF_points()));
        holder.startdat.setText(String.valueOf(branch3.getStartDate()));
        holder.enddat.setText(String.valueOf(branch3.getEndDate()));

    }

    @Override
    public int getItemCount() {
        return branch3list.size();
    }

    class branch3ViewHolder extends RecyclerView.ViewHolder{

        TextView startdat,enddat,rewardTitle,rewardPoints;

        ImageView rewardImage;
        public branch3ViewHolder(View itemView) {
            super(itemView);
            rewardTitle =itemView.findViewById(R.id.rewardTitle);
            rewardPoints =itemView.findViewById(R.id.rewardPoints);
            startdat =itemView.findViewById(R.id.startdat);
            enddat =itemView.findViewById(R.id.enddat);
            rewardImage =itemView.findViewById(R.id.rewardImage);
        }
    }
}
