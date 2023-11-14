package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;
    MoviesAdapter moviesAdapter;

    RecyclerView recyclerViewMovies;
    private static final String TEG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviesAdapter = new MoviesAdapter();

        recyclerViewMovies = findViewById(R.id.recyclerViewMovies);

        recyclerViewMovies.setAdapter(moviesAdapter);

        recyclerViewMovies.setLayoutManager(new GridLayoutManager(this,2)); // тип представления ресайклер вью. 2 колонки

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mainViewModel.loadMovies();

        mainViewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {

                moviesAdapter.setMovies(movies);

            }
        });




    }
}