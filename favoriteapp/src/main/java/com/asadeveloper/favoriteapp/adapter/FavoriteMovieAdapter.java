package com.asadeveloper.favoriteapp.adapter;

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

import com.asadeveloper.favoriteapp.DetailActivity;
import com.asadeveloper.favoriteapp.MovieItems;
import com.asadeveloper.favoriteapp.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.ViewHolder>  {
    private ArrayList<MovieItems> mData = new ArrayList<>() ;
    private final Context context;
    public FavoriteMovieAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<MovieItems> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(final MovieItems item) {
        mData.add(item);
        notifyDataSetChanged();
    }
    public void clearData() {
        mData.clear();
    }

    public void removeItem(int position) {
        this.mData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mData.size());
    }

    @NonNull
    @Override
    public FavoriteMovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_favorite, viewGroup, false);
        return new FavoriteMovieAdapter.ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMovieAdapter.ViewHolder holder, final int position) {
        final MovieItems film = mData.get(position);
        final String uri = mData.get(position).getPoster_path();
        final String datatitle = mData.get(position).getOriginal_title();
        final String rilis = mData.get(position).getRelease_date();
        final String deskrip = mData.get(position).getOverview();

        Glide.with(holder.imgView.getContext()).load(uri).into(holder.imgView);
        holder.title.setText(datatitle);
        holder.deskrip.setText(deskrip);

        //detail click
        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detail = new Intent(view.getContext(), DetailActivity.class);
                detail.putExtra(DetailActivity.EXTRA_FILM, film);
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
        TextView title, deskrip;
        Button btnDetail, hapus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.img_photo);
            title = itemView.findViewById(R.id.txt_name);
            deskrip = itemView.findViewById(R.id.txt_description);
            btnDetail = itemView.findViewById(R.id.btn_detail);
        }
    }
}
