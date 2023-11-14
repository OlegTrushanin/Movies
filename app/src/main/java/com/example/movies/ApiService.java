package com.example.movies;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
   // @GET("movie?token=1AAP9ZP-2BD4ECK-QMNTWRZ-SMA0V61&rating.kp=7-10&sortField=votes.kp&sortType=-1&limit=40")
    @GET("movie?token=1AAP9ZP-2BD4ECK-QMNTWRZ-SMA0V61&rating.kp=4-5&sortField=votes.kp&sortType=-1&limit=100") // переменная часть запроса
    Single<MoviesRespont> loadMovies(@Query("page") int page); // для загрузки данных. С помощью @Query передаем дополнительный параметр который можно изменять
}
