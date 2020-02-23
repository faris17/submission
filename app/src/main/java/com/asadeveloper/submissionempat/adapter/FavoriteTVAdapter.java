package com.asadeveloper.submissionempat.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.asadeveloper.submissionempat.R;
import com.asadeveloper.submissionempat.activity.DetailActivity;
import com.asadeveloper.submissionempat.activity.DetailTvShow;
import com.asadeveloper.submissionempat.model.TvItems;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FavoriteTVAdapter extends RecyclerView.Adapter<FavoriteTVAdapter.ViewHolder>  {
    private ArrayList<TvItems> mData = new ArrayList<>() ;
    private final Context context;
    public FavoriteTVAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<TvItems> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(final TvItems item) {
        mData.add(item);
        notifyDataSetChanged();
    }
    public void clearData() {
        mData.clear();
    }

    @NonNull
    @Override
    public FavoriteTVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tvshow, viewGroup, false);
        return new FavoriteTVAdapter.ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteTVAdapter.ViewHolder holder, final int position) {
        final TvItems tv = mData.get(position);
        String uri = mData.get(position).getPoster_path();
        Glide.with(holder.imgView.getContext()).load(uri).into(holder.imgView);
        holder.title.setText(mData.get(position).getOriginal_name());
        holder.deskrip.setText(mData.get(position).getOverview());
        holder.tahun.setText(mData.get(position).getFirst_air_date());
        holder.rate.setText(mData.get(position).getVote_average());

        holder.btnWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detail = new Intent(view.getContext(), DetailTvShow.class);
                detail.putExtra(DetailTvShow.EXTRA_FILM, tv);
                view.getContext().startActivity(detail);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView;
        TextView title, deskrip, rate, tahun;
        Button btnWatch;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.img_photo);
            title = itemView.findViewById(R.id.txt_name);
            deskrip = itemView.findViewById(R.id.txt_description);
            tahun = itemView.findViewById(R.id.txt_tahun);
            rate = itemView.findViewById(R.id.txt_start);
            btnWatch = itemView.findViewById(R.id.btn_watch);
        }
    }
}
