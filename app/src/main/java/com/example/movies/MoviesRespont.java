package com.example.movies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesRespont {
    @SerializedName("docs") //ключ
    private List<Movie> movies;

    public MoviesRespont(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return "MoviesRespont{" +
                "movies=" + movies +
                '}';
    }

    public List <Movie> getMovies() {
        return movies;
    }
}
