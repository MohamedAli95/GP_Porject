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

        import models.SearchPlace;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.searchViewHolder> {

    private Context mCtx;
    private List<SearchPlace> searchlist;
    private OnItemClicked onClick;

    public SearchAdapter(Context mCtx, List<SearchPlace> searchlist) {
        this.mCtx = mCtx;
        this.searchlist = searchlist;
    }
    public interface OnItemClicked {
        void onItemClick(int position,int Place_ID);

    }
    @NonNull
    @Override
    public SearchAdapter.searchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.searchadapter, null);
        searchViewHolder holder = new searchViewHolder(view);
        return holder;
    }


    public void onBindViewHolder(@NonNull SearchAdapter.searchViewHolder holder, final int position) {
        SearchPlace search =searchlist.get(position);
        final int Place_ID = search.getPlace_ID();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onItemClick(position,Place_ID);

            }
        });

        Glide.with(mCtx)
                .load(search.getPlace_LogoPhoto())
                .into(holder.logo);
        holder.placename.setText(search.getPLaceName());
    }

    @Override
    public int getItemCount() {
        return searchlist.size();
    }

    class searchViewHolder extends RecyclerView.ViewHolder{

        TextView placename;
        ImageView logo;

        public searchViewHolder(View itemView) {
            super(itemView);
            placename =itemView.findViewById(R.id.placename);
            logo =itemView.findViewById(R.id.logo);
        }
    }
    public void setOnClick(SearchAdapter.OnItemClicked onClick)
    {
        this.onClick=onClick;
    }
}
