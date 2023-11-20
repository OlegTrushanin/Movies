package com.example.movies;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class FavoritesActivityViewModel extends AndroidViewModel {


    public LiveData<List<Movie>> getFavoritesMovie() {
        return favoritesMovie;
    }

    private LiveData<List<Movie>> favoritesMovie = new MutableLiveData<>();
    private MovieDao movieDao;


    public FavoritesActivityViewModel(@NonNull Application application) {
        super(application);
        movieDao = DataBase.getInstance(application).movieDao();

    }

    void loafFavoritesMovies(){

        favoritesMovie = movieDao.getAllFavoriteMovies();

    }

















}
