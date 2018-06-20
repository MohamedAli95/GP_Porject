package com.example.shika.boo;

/**
 * Created by delaroy on 2/10/17.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.chahinem.pageindicator.PageIndicator;

import java.util.ArrayList;
import java.util.List;

public class SnapAdapter extends RecyclerView.Adapter<SnapAdapter.ViewHolder> implements Adapter.OnItemClicked {


    public static final int HORIZONTAL = 1;


    private ArrayList<Snap> mSnaps;
    private Context context;
    Adapter adapter;
    // Disable touch detection for parent recyclerView if we use vertical nested recyclerViews


    public SnapAdapter(Context context) {
        mSnaps = new ArrayList<>();
        this.context=context;
    }

    public void addSnap(Snap snap) {
        mSnaps.add(snap);
    }

    @Override
    public int getItemViewType(int position) {
        Snap snap = mSnaps.get(position);
        switch (snap.getGravity()) {
            case Gravity.CENTER_VERTICAL:
                return HORIZONTAL;
            case Gravity.CENTER_HORIZONTAL:
                return HORIZONTAL;
            case Gravity.START:
                return HORIZONTAL;
            case Gravity.TOP:
                return HORIZONTAL;
            case Gravity.END:
                return HORIZONTAL;
            case Gravity.BOTTOM:
                return HORIZONTAL;
        }
        return HORIZONTAL;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_snap, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Snap snap = mSnaps.get(position);

        holder.snapTextView.setText(snap.getText());

        if (snap.getGravity() == Gravity.START || snap.getGravity() == Gravity.END) {
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder
                    .recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            holder.recyclerView.setOnFlingListener(null);
        } else if (snap.getGravity() == Gravity.CENTER_HORIZONTAL) {
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder
                    .recyclerView.getContext(), snap.getGravity() == Gravity.CENTER_HORIZONTAL ?
                    LinearLayoutManager.HORIZONTAL : LinearLayoutManager.VERTICAL, false));
            holder.recyclerView.setOnFlingListener(null);
            new LinearSnapHelper().attachToRecyclerView(holder.recyclerView);
        } else if (snap.getGravity() == Gravity.CENTER) { // Pager snap
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder
                    .recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            holder.recyclerView.setOnFlingListener(null);
            new PagerSnapHelper().attachToRecyclerView(holder.recyclerView);

        } else { // Top / Bottom
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder
                    .recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            holder.recyclerView.setOnFlingListener(null);
        }


         adapter=new Adapter(snap.getGravity() == Gravity.START
                || snap.getGravity() == Gravity.END
                || snap.getGravity() == Gravity.CENTER_HORIZONTAL,
                snap.getGravity() == Gravity.CENTER, snap.getApps(),context);






        holder.recyclerView.setAdapter(adapter);
        adapter.setOnClick(this);
    }



    @Override
    public int getItemCount() {
        return mSnaps.size();
    }

    public void onSnap(int position) {
        Log.d("Snapped: ", position + "");
    }

    @Override
    public void onItemClick(int position,int placeid) {

        Intent intent = new Intent(context,Try.class);
        intent.putExtra("place_id",placeid);
        context.startActivity(intent);


    }

    public  class ViewHolder extends RecyclerView.ViewHolder {

        public  TextView snapTextView;
        public RecyclerView recyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            snapTextView = (TextView) itemView.findViewById(R.id.snapTextView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);

            if(context instanceof Merchant_Profile || context instanceof SecondHome){
                // Button bd = (Button)itemView.findViewById(R.id.Removebtn);
                ViewGroup vg = (ViewGroup)(snapTextView.getParent());
                vg.removeView(snapTextView);
            }

        }

    }
}


