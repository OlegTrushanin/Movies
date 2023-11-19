package com.example.movies;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailViewModel extends AndroidViewModel {

    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Trailer>> getTrailers() {
        return trailers;
    }

    private MutableLiveData<List<Trailer>> trailers = new MutableLiveData<>();

    private CompositeDisposable compositeDisposable = new CompositeDisposable();



    void loadTrailers(int id){

       Disposable disposable = ApiFactory.apiService.loadTrailers(id)
                .subscribeOn(Schedulers.io())
               .map(new Function<TrailersRespont, List<Trailer>>() {// используется чтобы изменить тип данных получаемых методом accept в subscribe
                   @Override
                   public List<Trailer> apply(TrailersRespont trailersRespont) throws Throwable {
                       return trailersRespont.getMovieTrailer().getTrailers();
                   }
               })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Trailer>>() {
                    @Override
                    public void accept(List<Trailer> trailerList) throws Throwable {

                        trailers.setValue(trailerList);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {

                    }
                });

       compositeDisposable.add(disposable);




    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
