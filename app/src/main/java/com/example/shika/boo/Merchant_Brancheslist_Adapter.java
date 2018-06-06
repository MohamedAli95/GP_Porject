package com.example.shika.boo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Belal on 10/18/2017.
 */


public class Merchant_Brancheslist_Adapter extends RecyclerView.Adapter<Merchant_Brancheslist_Adapter.ProductViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<Merchant_Branch_Model> productList;

    //getting the context and product list with constructor
    public  Merchant_Brancheslist_Adapter(Context mCtx, List<Merchant_Branch_Model> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.merchant_branch_item, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        //getting the product of the specified position
        Merchant_Branch_Model branch = productList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle.setText(branch.getTitle());
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewPrice);

        }
    }
}
