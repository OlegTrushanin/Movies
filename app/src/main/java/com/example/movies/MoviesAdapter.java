package com.example.movies;

import android.graphics.drawable.Drawable;
import android.util.Log;
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

public class MoviesAdapter extends RecyclerView.Adapter <MoviesAdapter.MoviesVieHolder>{

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    List<Movie> movies = new ArrayList<>();

    public void setOnReachAndListener(OnReachAndListener onReachAndListener) {
        this.onReachAndListener = onReachAndListener;
    }

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    private OnReachAndListener onReachAndListener;



    private OnClickItemListener onClickItemListener;

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

        Log.d("MoviesAdapter", "onBindViewHolder" + position);

        Movie movie = movies.get(position);
        Glide.with(holder.itemView)
                .load(movie.getPoster().getUrl())
                .into(holder.imagePoster);

        double rating = movie.getRating().getKp();
        holder.ratingView.setText(String.format("%.1f",rating));

        int backGroundId;
        if(rating>7){
            backGroundId = R.drawable.circle_green;
        }else if(rating>5){
            backGroundId = R.drawable.circle_orange;
        }else{
            backGroundId = R.drawable.circle_red;
        }

        Drawable backGround = ContextCompat.getDrawable(holder.itemView.getContext(), backGroundId);

        holder.ratingView.setBackground(backGround);

        if(position >= movies.size()-10&& onReachAndListener != null){

            onReachAndListener.onReachAnd();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() { // вешаем слушателя клика
            @Override
            public void onClick(View view) {
                if(onClickItemListener != null) {
                    onClickItemListener.onClickItem(movie);
                }
            }
        });


    }

    interface OnReachAndListener{ // нужен для колбэка, чтобы подгрузить фильмы когда долистаем в приложении до конца
        void onReachAnd(); // потом мы этод метод переопределим в MainActivity
    }

    interface OnClickItemListener{
       void onClickItem(Movie movie);
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




