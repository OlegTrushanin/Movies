package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;
    MoviesAdapter moviesAdapter;

    RecyclerView recyclerViewMovies;

    ProgressBar progressBar;

    FloatingActionButton floatingActionButton;
    private static final String TEG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButton = findViewById(R.id.floatingActionButton);

        moviesAdapter = new MoviesAdapter();

        recyclerViewMovies = findViewById(R.id.recyclerViewMovies);

        progressBar = findViewById(R.id.progressBar);

        recyclerViewMovies.setAdapter(moviesAdapter);

        recyclerViewMovies.setLayoutManager(new GridLayoutManager(this,2)); // тип представления ресайклер вью. 2 колонки

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        //mainViewModel.loadMovies(); убираем во вью модель, чтобы не терять состаяние страницы при перевороте

        mainViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    progressBar.setVisibility(View.VISIBLE);
                }else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        mainViewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {

                moviesAdapter.setMovies(movies);

            }
        });

        moviesAdapter.setOnReachAndListener(new MoviesAdapter.OnReachAndListener() {
            @Override
            public void onReachAnd() {
                mainViewModel.loadMovies();
            }
        });

        moviesAdapter.setOnClickItemListener(new MoviesAdapter.OnClickItemListener() {
            @Override
            public void onClickItem(Movie movie) {
               Intent intent = MovieDetailActivity.newIntent(MainActivity.this, movie);
               startActivity(intent);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = FavoritesActivity.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });




    }
}