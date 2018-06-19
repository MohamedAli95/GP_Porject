package com.example.shika.boo;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import android.content.SharedPreferences;


public class SwipeRecyclerViewAdapter extends RecyclerSwipeAdapter<SwipeRecyclerViewAdapter.SimpleViewHolder> {

    private Context mContext;
    private java.util.List<YoutuberModel> studentList;

    private Button buttonChoose , buttonInsert;
    private ImageView imageView;
    private EditText rewardName, rewardPoints,from,to;
    DatePickerDialog picker;
    ProgressDialog progressDialog;
    String k;
    String URL_UPDATE;
    public static final String MyPREFERENCES = "MyPrefs" ;

    SharedPreferences sharedpreferences;

    public SwipeRecyclerViewAdapter(Context context, java.util.List<YoutuberModel> objects) {
        this.mContext = context;
        this.studentList = objects;

    }


    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.swipe_layout, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
        final YoutuberModel item = studentList.get(position);

        com.bumptech.glide.Glide.with(mContext)
                .load(item.getImage())
                .into(viewHolder.ig);
        viewHolder.Name.setText(item.getName() );
        viewHolder.Startdate.setText("Start date: "+item.getSdate());
        viewHolder.Enddate.setText("End date: "+item.getEdate());
        viewHolder.Point.setText("Points : "+ item.getPoints());


        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        //dari kiri
    //    viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper1));

        //dari kanan
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.bottom_wraper));



        viewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {

            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });

        viewHolder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, " Click : " + item.getName() + " \n" + item.getName(), Toast.LENGTH_SHORT).show();
            }
        });





        viewHolder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YoutuberModel c= studentList.get(position);

                //   sharedpreferences = mContext.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                //   SharedPreferences.Editor editor = sharedpreferences.edit();


                // editor.putString("rewName",   viewHolder.Name.getText().toString());
                //editor.putInt("idName", c.getId());

                //editor.commit();

                if(mContext instanceof Merchant_Rewards_list){
                    Intent too = new Intent(mContext, Edit_Reward.class);
                    too.putExtra("Rewarname",viewHolder.Name.getText().toString());
                    too.putExtra("Rewarfrom",item.getSdate());
                    too.putExtra("Rewarto",item.getEdate());
                    too.putExtra("Rewarimage",item.getImage());
                    too.putExtra("Rewarpoints",item.getPoints());

                    too.putExtra("Rewarid",c.getId());
                    mContext.startActivity(too);
                }else if(mContext instanceof Merchant_Offers){
                    Intent too = new Intent(mContext, Edit_offer.class);
                    too.putExtra("offername",viewHolder.Name.getText().toString());
                    too.putExtra("offerfrom",item.getSdate());
                    too.putExtra("offerto",item.getEdate());
                    too.putExtra("offerimage",item.getImage());
                    too.putExtra("offerpoints",item.getPoints());

                    too.putExtra("offerid",c.getId());
                    mContext.startActivity(too);                }

                //     int x = item.getId();
                // String id = Integer.toString(x);

                Toast.makeText(view.getContext(), "Clicked on Edit  " + viewHolder.Name.getText().toString(), Toast.LENGTH_SHORT).show();

            }
        });

        viewHolder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mContext instanceof Merchant_Rewards_list){
                    URL_UPDATE="http://gp.sendiancrm.com/offerall/Delete_reward.php";
                }else if(mContext instanceof Merchant_Offers){
                    URL_UPDATE="http://gp.sendiancrm.com/offerall/Delete_offer.php";
                }
                YoutuberModel c= studentList.get(position);
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
                                progressDialog.cancel();
                                Toast.makeText(mContext, error.toString(), Toast.LENGTH_SHORT).show();
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
                mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                studentList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, studentList.size());
                mItemManger.closeAllItems();


                Toast.makeText(v.getContext(), "Deleted " + viewHolder.Name.getText().toString(), Toast.LENGTH_SHORT).show();
                RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                requestQueue.add(updateReq);
            }
        });

        mItemManger.bindView(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder{
        public SwipeLayout swipeLayout;
        public ImageView ig;
        public TextView Name;
        public TextView Point;
        public TextView Startdate;
        public TextView Enddate;
        public TextView Delete;
        public TextView Edit;
        public ImageButton btnLocation;
        public SimpleViewHolder(View itemView) {
            super(itemView);

            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            ig = (ImageView) itemView.findViewById(R.id.imageid);
            Name = (TextView) itemView.findViewById(R.id.Name);
            Startdate = (TextView) itemView.findViewById(R.id.startd);
            Enddate = (TextView) itemView.findViewById(R.id.endd);
            Point = (TextView) itemView.findViewById(R.id.po);
            Delete = (TextView) itemView.findViewById(R.id.Delete);
            Edit = (TextView) itemView.findViewById(R.id.Edit);
          //  btnLocation = (ImageButton) itemView.findViewById(R.id.btnLocation);
        }
    }


    private void Update_data(final String i)
    {
        progressDialog.setMessage("Update Data");
        progressDialog.setCancelable(false);
        progressDialog.show();


        //   AppController.getInstance().addToRequestQueue(updateReq);
    }
}
