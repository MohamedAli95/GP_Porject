package com.example.shika.boo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Juned on 2/8/2017.
 */

public class Merchant_RecyclerViewAdapter extends RecyclerView.Adapter<Merchant_RecyclerViewAdapter.ViewHolder> {

    Context context;

    List<Merchant_Menu> dataAdapters;

    ImageLoader imageLoader;
String k,URL_UPDATE;
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
    public void onBindViewHolder(ViewHolder Viewholder, final int position) {

        Merchant_Menu dataAdapterOBJ =  dataAdapters.get(position);

        imageLoader = MerchantMenu_ImageAdapter.getInstance(context).getImageLoader();

        imageLoader.get(dataAdapterOBJ.getImageUrl(),
                ImageLoader.getImageListener(
                        Viewholder.VollyImageView,//Server Image
                        R.drawable.load,//Before loading server image the default showing image.
                        R.drawable.error //Error image if requested image dose not found on server.
                )
        );

        Viewholder.VollyImageView.setImageUrl(dataAdapterOBJ.getImageUrl(), imageLoader);


        Viewholder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    URL_UPDATE="http://gp.sendiancrm.com/offerall/Delete_menu.php";

                Merchant_Menu c= dataAdapters.get(position);
                k= Integer.toString(c.getId());
                StringRequest updateReq = new StringRequest(Request.Method.POST, URL_UPDATE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //    progressDialog.cancel();


                                //  startActivity( new Intent(Edit_Reward.this,Merchant_Rewards_list.class));
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                               // progressDialog.cancel();
                                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        // String intent_id = data.getStringExtra("id");

                        Map<String,String> map = new HashMap<>();
                        map.put("npm",k);
                        //   map.put("npm",rewardName.getText().toString());


                        return map;
                    }
                };
                //Creating a Request Queue

                //Adding request to the queue
                //   ino.putExtra("name",viewHolder.Name.getText().toString());
              //  mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                dataAdapters.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, dataAdapters.size());
             //   mItemManger.closeAllItems();


                Toast.makeText(v.getContext(), "Deleted " , Toast.LENGTH_SHORT).show();
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(updateReq);
            }
        });


    }

    @Override
    public int getItemCount() {

        return dataAdapters.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView ImageTitleTextView;
        public NetworkImageView VollyImageView ;
        public Button Delete;

        public ViewHolder(View itemView) {

            super(itemView);
            Delete = (Button) itemView.findViewById(R.id.Removebtn);

            if(context instanceof Merchant_Profile || context instanceof SecondHome){
                Button bd = (Button)itemView.findViewById(R.id.Removebtn);
                ViewGroup vg = (ViewGroup)(bd.getParent());
                vg.removeView(bd);
            }


            VollyImageView = (NetworkImageView) itemView.findViewById(R.id.VolleyImageView) ;

        }
    }
}