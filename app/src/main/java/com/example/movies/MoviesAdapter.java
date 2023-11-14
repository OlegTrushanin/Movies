package com.example.movies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter <MoviesAdapter.MoviesVieHolder>{

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    List<Movie> movies = new ArrayList<>();

    @NonNull
    @Override
    public MoviesVieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.movie_item,
                parent,
                false);

        return new MoviesVieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesVieHolder holder, int position) {
        Movie movie = movies.get(position);
        Glide.with(holder.itemView)
                .load(movie.getPoster().getUrl())
                .into(holder.imagePoster);

        double rating = movie.getRating().getKp();

        holder.ratingView.setText(String.format("%.1f",rating));

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


    public class MoviesVieHolder extends RecyclerView.ViewHolder{

        ImageView imagePoster;
        TextView ratingView;

        public MoviesVieHolder(@NonNull View itemView) {
            super(itemView);
            imagePoster = itemView.findViewById(R.id.imagePoster);
            ratingView = itemView.findViewById(R.id.ratingView);
            ;
        }
    }
}




