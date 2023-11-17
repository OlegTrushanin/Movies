package com.example.movies;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private int page = 1;


    MutableLiveData <List<Movie>> movies = new MutableLiveData<>();

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    MutableLiveData <Boolean> isLoading = new MutableLiveData<>(false);

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }



    public MainViewModel(@NonNull Application application) {
        super(application);
        loadMovies(); // перенесли загрузку данных их активити
    }



    public void loadMovies(){
        Boolean loading = isLoading.getValue(); // проверка идет ли сейчас загрузка, чтобы не было лишних загрухок страниц
        if(loading != null && loading){
            return;
        }
        Disposable disposable = loadRx()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() { // Выполняется при начале загрузки
                    @Override
                    public void accept(Disposable disposable) throws Throwable {
                        isLoading.setValue(true);
                    }
                })
                .doAfterTerminate(new Action() { // выполняется после загрузки не зависимо от ее успешности
                    @Override
                    public void run() throws Throwable {
                        isLoading.setValue(false);
                    }
                })
                .subscribe(new Consumer<MoviesRespont>() {
                    @Override
                    public void accept(MoviesRespont moviesRespont) throws Throwable {
                        List<Movie> loadedMovies = movies.getValue();
                        if (loadedMovies != null) {
                            loadedMovies.addAll(moviesRespont.getMovies());
                            movies.setValue(loadedMovies);

                        } else {
                            movies.setValue(moviesRespont.getMovies()); // присваиваем значения

                        }
                        Log.d("MainViewModel","page "+page);
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
