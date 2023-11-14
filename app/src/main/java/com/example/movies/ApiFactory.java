package com.example.movies;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {

    private static final String BASE_URL = "https://api.kinopoisk.dev/v1.4/";

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) //// преобразует JSON в экземпляр класса
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create()) // нужен чтобы возвращаемый тип был Single
            .build();

    public static final ApiService apiService = retrofit.create(ApiService.class); // реализует паттерн синглтон


}
