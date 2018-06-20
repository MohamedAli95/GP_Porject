package com.example.shika.boo;

/**
 * Created by Shika on 06/03/2018.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import java.util.ArrayList;

/**
 * Created by SONU on 25/09/15.
 */
public class Recyclerview_Adapter_Offers extends RecyclerView.Adapter<Recyclerview_Adapter_Offers.ViewHolder> {// Recyclerview will extend to
    // recyclerview adapter
    private java.util.List<OfferModel> arrayList;
    private Context context;

    public Recyclerview_Adapter_Offers(Context context,
                                java.util.List<OfferModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }

    @Override
    public int getItemCount() {
        return arrayList.size();

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final OfferModel model = arrayList.get(position);

        ViewHolder mainHolder = (ViewHolder) holder;// holder


        com.bumptech.glide.Glide.with(context)
                .load(model.getImage())
                .into(holder.ig);
        // setting title
        holder.Name.setText(model.getTitle());
        holder.Enddate.setText("End in:"+model.getDuration());
        holder.points.setText(Integer.toString(model.getBanner())+" points");
        holder.price.setText(Double.toString(model.getPrice())+" L.E");

        //   mainHolder.imageview.setImageBitmap(image);



    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        // This method will inflate the custom layout and return as viewholder
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.item_row, viewGroup, false);
        ViewHolder listHolder = new ViewHolder(mainGroup);
        return listHolder;

    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public SwipeLayout swipeLayout;
        public ImageView ig;
        public TextView Name;
        public TextView points;
        public TextView Enddate;
        public TextView price;

        public ViewHolder(View itemView) {
            super(itemView);

            ig = (ImageView) itemView.findViewById(R.id.image);
            Name = (TextView) itemView.findViewById(R.id.title);
            Enddate = (TextView) itemView.findViewById(R.id.offer);
            points = (TextView) itemView.findViewById(R.id.dur);
            price = (TextView) itemView.findViewById(R.id.money);

        }
    }
}
