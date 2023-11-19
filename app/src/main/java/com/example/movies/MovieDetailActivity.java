package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView imageViewPoster;
    private TextView textViewTitle;
    private TextView textViewYear;
    private TextView textDescription;

    public static final String EXTRA_MOVIE = "movie";

    MovieDetailViewModel movieDetailViewModel;

    MovieDetailAdapter movieDetailAdapter;

    ReviewAdapter reviewAdapter;

    RecyclerView recyclerViewTrailer;

    RecyclerView recyclerViewReview;

    ImageView imageViewStar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initViews();

        movieDetailAdapter = new MovieDetailAdapter();

        reviewAdapter = new ReviewAdapter();

        recyclerViewTrailer.setAdapter(movieDetailAdapter);

        recyclerViewReview.setAdapter(reviewAdapter);

        //recyclerViewTrailer.setLayoutManager(new GridLayoutManager(this,1));

        recyclerViewTrailer.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewReview.setLayoutManager(new LinearLayoutManager(this));


        movieDetailViewModel = new ViewModelProvider(this).get(MovieDetailViewModel.class);


        Movie movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE); // получаем объет из интента



        Glide.with(this)
                .load(movie.getPoster().getUrl())
                .into(imageViewPoster);

        textViewTitle.setText(movie.getName());
        textViewYear.setText(String.valueOf(movie.getYear()));
        textDescription.setText(movie.getDescription());

        movieDetailViewModel.loadTrailers(movie.getId());

        movieDetailViewModel.loadReviews(movie.getId());

        movieDetailViewModel.getTrailers().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(List<Trailer> trailers) {
               // Log.d("MovieDetailActivity1", trailers.toString());
                movieDetailAdapter.setTrailerList(trailers);
            }
        });

        movieDetailViewModel.getReviews().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                reviewAdapter.setReviewList(reviews);
            }
        });

        movieDetailAdapter.setOnKlick(new MovieDetailAdapter.OnKlickItemListener() {
            @Override
            public void onKlickItemListener(Trailer trailer) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(trailer.getUrl()));
                startActivity(intent);
            }
        });

//        reviewAdapter.setOnReachAndList(new ReviewAdapter.OnReachAndList() {
//            @Override
//            public void onReachAndList() {
//                movieDetailViewModel.loadReviews(movie.getId());
//            }
//        });
        Drawable starrOff = ContextCompat.getDrawable(MovieDetailActivity.this, android.R.drawable.star_big_off);
        Drawable starrOn = ContextCompat.getDrawable(MovieDetailActivity.this, android.R.drawable.star_big_on);

        movieDetailViewModel.getFavoriteMovie(movie.getId()).observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movieFromDb) {

                if(movieFromDb == null){

                    imageViewStar.setImageDrawable(starrOff);

                    imageViewStar.setOnClickListener(new View.OnClickListener() { // слушатель клика
                        @Override
                        public void onClick(View view) {
                            movieDetailViewModel.insertMovie(movie);
                        }
                    });

                }else{

                    imageViewStar.setImageDrawable(starrOn);
                    imageViewStar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            movieDetailViewModel.deleteMovie(movie.getId());
                        }
                    });

                }

            }
        });






    }

    private void initViews() {
        imageViewPoster = findViewById(R.id.imageViewPoster);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewYear = findViewById(R.id.textViewYear);
        textDescription = findViewById(R.id.textDescription);
        recyclerViewTrailer = findViewById(R.id.recyclerViewTrailer);
        recyclerViewReview = findViewById(R.id.recyclerViewReview);
        imageViewStar = findViewById(R.id.imageViewStar);

    }

    public static Intent newIntent(Context context, Movie movie){
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        return intent;
    }





}