package com.example.shika.boo;

        import android.content.Context;
        import android.support.annotation.NonNull;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.CheckBox;
        import android.widget.CompoundButton;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.squareup.picasso.Picasso;

        import java.util.List;

        import models.UserInterests;
        import models.favorits;

public class FavoritsAdapter extends  RecyclerView.Adapter<FavoritsAdapter.FavoritsAdapterholder>{
    private Context mCtx;
    private List<favorits> favorits;

    public FavoritsAdapter(Context mCtx, List<favorits> favorits) {
        this.mCtx = mCtx;
        this.favorits = favorits;
    }
    public interface OnItemClicked {
        void onItemClick(int favoritdid);
        void onItemClick1(int placeid);

    }
    private OnItemClicked onClick;


    @NonNull
    @Override
    public FavoritsAdapterholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.favlist, null);
        FavoritsAdapterholder holder = new FavoritsAdapterholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoritsAdapterholder holder, final int position) {
        final favorits favorit = favorits.get(position);
        final int placeid = favorits.get(position).getPlace_id();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick1(placeid);

            }
        });



        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {

                }
                else{
                    onClick.onItemClick(favorit.getFavoritid());
                }
            }
        });

        holder.placename.setText(favorit.getPlacename());
        Picasso.get().load(favorit.getPlacelogo()).into(holder.logo);
        holder.check.setChecked(true);

    }

    @Override
    public int getItemCount() {
        return favorits.size();
    }

    class FavoritsAdapterholder extends RecyclerView.ViewHolder{
        TextView placename;
        ImageView logo;
        CheckBox check;


        public FavoritsAdapterholder(View itemView) {
            super(itemView);
            placename= itemView.findViewById(R.id.placename);
            logo= itemView.findViewById(R.id.logo);
            check= itemView.findViewById(R.id.item_card_check_favorite);

        }
    }
    public void setOnClick(OnItemClicked onClick)
    {
        this.onClick=onClick;
    }

}

