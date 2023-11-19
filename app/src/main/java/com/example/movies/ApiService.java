package com.example.movies;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
   // @GET("movie?token=1AAP9ZP-2BD4ECK-QMNTWRZ-SMA0V61&rating.kp=7-10&sortField=votes.kp&sortType=-1&limit=40")
    //@GET("movie?token=1AAP9ZP-2BD4ECK-QMNTWRZ-SMA0V61&rating.kp=4-5&sortField=votes.kp&sortType=-1&limit=100") // переменная часть запроса
   @GET("movie?token=1AAP9ZP-2BD4ECK-QMNTWRZ-SMA0V61&rating.kp=7-10&sortField=votes.kp&sortType=-1&limit=30")
    Single<MoviesRespont> loadMovies(@Query("page") int page); // для загрузки данных. С помощью @Query передаем дополнительный параметр который можно изменять

    @GET("movie/{idFilms}?token=1AAP9ZP-2BD4ECK-QMNTWRZ-SMA0V61")
    Single<TrailersRespont> loadTrailers(@Path("idFilms") int id);

//    @GET("review?token=1AAP9ZP-2BD4ECK-QMNTWRZ-SMA0V61&rating.kp&limit=10&selectFields=review&selectFields=author&movieId={movieId}&type=Негативный&type=Нейтральный&type=Позитивный")
//    Single<ReviewsList> loadReview(@Query("page") int page, @Path("movieId") int idFilm);

    @GET("review?token=1AAP9ZP-2BD4ECK-QMNTWRZ-SMA0V61&rating.kp&limit=10&selectFields=review&selectFields=author&type=Негативный&type=Нейтральный&type=Позитивный")
    Single<ReviewsList> loadReview(@Query("page") int page, @Query("movieId") int idFilm);
}
