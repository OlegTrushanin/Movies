package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView imageViewPoster;
    private TextView textViewTitle;
    private TextView textViewYear;
    private TextView textDescription;

    public static final String EXTRA_MOVIE = "movie";

    MovieDetailViewModel movieDetailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initViews();

        movieDetailViewModel = new ViewModelProvider(this).get(MovieDetailViewModel.class);


        Movie movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE); // получаем объет из интента



        Glide.with(this)
                .load(movie.getPoster().getUrl())
                .into(imageViewPoster);

        textViewTitle.setText(movie.getName());
        textViewYear.setText(String.valueOf(movie.getYear()));
        textDescription.setText(movie.getDescription());

        movieDetailViewModel.loadTrailers(movie.getId());

        movieDetailViewModel.getTrailers().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(List<Trailer> trailers) {
                Log.d("MovieDetailActivity1", trailers.toString());
            }
        });




    }

    private void initViews() {
        imageViewPoster = findViewById(R.id.imageViewPoster);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewYear = findViewById(R.id.textViewYear);
        textDescription = findViewById(R.id.textDescription);
    }

    public static Intent newIntent(Context context, Movie movie){
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        return intent;
    }


}