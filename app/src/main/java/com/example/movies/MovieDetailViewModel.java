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
import io.reactivex.rxjava3.functions.Action;
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

    private MutableLiveData<List<Review>> reviews = new MutableLiveData<>();

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    public LiveData<List<Review>> getReviews() {
        return reviews;
    }

    int page = 1;

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

    void loadReviews(int movieId){
        Boolean loading = isLoading.getValue();
        if(loading!=null && loading){
            return;
        }

        Disposable disposable1 = ApiFactory.apiService.loadReview(page, movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Throwable {
                        isLoading.setValue(true);
                    }
                })
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Throwable {
                        isLoading.setValue(false);
                    }
                })
                .subscribe(new Consumer<ReviewsList>() {
                    @Override
                    public void accept(ReviewsList reviewsList) throws Throwable {
                        List<Review> allReview = reviews.getValue();
                        if(allReview!=null){
                            allReview.addAll(reviewsList.getReviews());
                            reviews.setValue(allReview);

                        }else {
                            reviews.setValue(reviewsList.getReviews());
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {

                    }
                });
        ++page;

        compositeDisposable.add(disposable1);



    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
