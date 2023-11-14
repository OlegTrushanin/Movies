package com.example.movies;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private int page = 1;


    MutableLiveData <List<Movie>> movies = new MutableLiveData<>();

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }




    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadMovies(){

        Disposable disposable = loadRx()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MoviesRespont>() {
                    @Override
                    public void accept(MoviesRespont moviesRespont) throws Throwable {

                        movies.setValue(moviesRespont.getMovies()); // присваиваем значения
                        page++;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {

                    }
                });
        compositeDisposable.add(disposable);

    }

    private Single<MoviesRespont> loadRx(){

        return ApiFactory.apiService.loadMovies(page);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }


}
