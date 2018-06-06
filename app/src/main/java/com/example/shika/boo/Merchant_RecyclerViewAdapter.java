package com.example.shika.boo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

/**
 * Created by Juned on 2/8/2017.
 */

public class Merchant_RecyclerViewAdapter extends RecyclerView.Adapter<Merchant_RecyclerViewAdapter.ViewHolder> {

    Context context;

    List<Merchant_Menu> dataAdapters;

    ImageLoader imageLoader;

    public Merchant_RecyclerViewAdapter(List<Merchant_Menu> getDataAdapter, Context context){

        super();
        this.dataAdapters = getDataAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.merchant_menu_cardview, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder Viewholder, int position) {

        Merchant_Menu dataAdapterOBJ =  dataAdapters.get(position);

        imageLoader = MerchantMenu_ImageAdapter.getInstance(context).getImageLoader();

        imageLoader.get(dataAdapterOBJ.getImageUrl(),
                ImageLoader.getImageListener(
                        Viewholder.VollyImageView,//Server Image
                        R.drawable.menuholder,//Before loading server image the default showing image.
                        android.R.drawable.ic_dialog_alert //Error image if requested image dose not found on server.
                )
        );

        Viewholder.VollyImageView.setImageUrl(dataAdapterOBJ.getImageUrl(), imageLoader);


    }

    @Override
    public int getItemCount() {

        return dataAdapters.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView ImageTitleTextView;
        public NetworkImageView VollyImageView ;

        public ViewHolder(View itemView) {

            super(itemView);


            VollyImageView = (NetworkImageView) itemView.findViewById(R.id.VolleyImageView) ;

        }
    }
}