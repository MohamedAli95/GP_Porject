package com.example.shika.boo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import models.UserInterests;

public class Interestsadapter extends  RecyclerView.Adapter<Interestsadapter.interstsadapterholder>{
    private Context mCtx;
    private List<UserInterests> userInterests;

    public Interestsadapter(Context mCtx, List<UserInterests> userInterests) {
        this.mCtx = mCtx;
        this.userInterests = userInterests;
    }
    public interface OnItemClicked {
        void onItemClick(int status,int categoryid);

    }
    private OnItemClicked onClick;


    @NonNull
    @Override
    public interstsadapterholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.interests_list, null);
        interstsadapterholder holder = new interstsadapterholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final interstsadapterholder holder, final int position) {
        final UserInterests interest = userInterests.get(position);
        final int categorid = userInterests.get(position).getCategory_id();
        if(interest.getInteriststatus()==1)
        holder.check.setChecked(true);
        else
            holder.check.setChecked(false);

        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                onClick.onItemClick(1,categorid);
                }
                else{
                    onClick.onItemClick(0,categorid);
                }
            }
        });

        holder.interist.setText(interest.getCategorytitle());

    }

    @Override
    public int getItemCount() {
        return userInterests.size();
    }

    class interstsadapterholder extends RecyclerView.ViewHolder{
        TextView interist;
        CheckBox check;


        public interstsadapterholder(View itemView) {
            super(itemView);
            interist= itemView.findViewById(R.id.txtfollow);
            check= itemView.findViewById(R.id.item_card_check_favorite);

        }
    }
    public void setOnClick(OnItemClicked onClick)
    {
        this.onClick=onClick;
    }
}
