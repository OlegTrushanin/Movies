package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    FavoritesActivityViewModel favoritesActivityViewModel;

    RecyclerView recyclerViewFavorites;

    FavoritesAdapter favoritesAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        favoritesActivityViewModel = new ViewModelProvider(this).get(FavoritesActivityViewModel.class);

        favoritesActivityViewModel.loafFavoritesMovies();

        recyclerViewFavorites = findViewById(R.id.recyclerViewFavorites);

        favoritesAdapter = new FavoritesAdapter();

        recyclerViewFavorites.setAdapter(favoritesAdapter);

        recyclerViewFavorites.setLayoutManager(new GridLayoutManager(this,2));




        favoritesActivityViewModel.getFavoritesMovie().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {

                favoritesAdapter.setFavoritesList(movies);



            }
        });

        favoritesAdapter.setOnClickItemListener(new FavoritesAdapter.OnClickItemListener() {
            @Override
            public void onClickItem(Movie movie) {
                Intent intent = MovieDetailActivity.newIntent(FavoritesActivity.this, movie);
                startActivity(intent);
            }
        });





    }

    public static Intent newIntent(Context context){
        return new Intent(context, FavoritesActivity.class);
    }



}