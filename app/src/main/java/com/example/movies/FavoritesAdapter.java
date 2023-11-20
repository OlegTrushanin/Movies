package com.example.movies;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.HolderFavoritesAdapter> {

    public void setFavoritesList(List<Movie> favoritesList) {
        this.favoritesList = favoritesList;
        notifyDataSetChanged();
    }

    List<Movie> favoritesList = new ArrayList<>();

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    OnClickItemListener onClickItemListener;



    @NonNull
    @Override
    public HolderFavoritesAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.favorites_item,
                parent,
                false);


        return new HolderFavoritesAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderFavoritesAdapter holder, int position) {

        Movie movie = favoritesList.get(position);

        Glide.with(holder.itemView)
                .load(movie.getPoster().getUrl())
                .into(holder.imagePosterFav);

        double rating = movie.getRating().getKp();
        holder.ratingViewFav.setText(String.format("%.1f",rating));
        int backGroundId;
        if(rating>7){
            backGroundId = R.drawable.circle_green;
        }else if(rating>5){
            backGroundId = R.drawable.circle_orange;
        }else{
            backGroundId = R.drawable.circle_red;
        }

        Drawable backGround = ContextCompat.getDrawable(holder.itemView.getContext(), backGroundId);

        holder.ratingViewFav.setBackground(backGround);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickItemListener.onClickItem(movie);
            }
        });



    }

    @Override
    public int getItemCount() {
        return favoritesList.size();
    }

    interface OnClickItemListener {

        public void onClickItem(Movie movie);

    }

    public class HolderFavoritesAdapter extends RecyclerView.ViewHolder{

        ImageView imagePosterFav;
        TextView ratingViewFav;



        public HolderFavoritesAdapter(@NonNull View itemView) {
            super(itemView);
            imagePosterFav = itemView.findViewById(R.id.imagePosterFav);
            ratingViewFav = itemView.findViewById(R.id.ratingViewFav);

        }
    }

}
