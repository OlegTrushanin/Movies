package com.example.movies;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "favorite_movies") //назване таблицы
public class Movie implements Serializable { // implements Serializable - нужен для перевода класса в поток байтов, чтобы мы могли объект Movie передать в Intent

    @PrimaryKey // указываем что это будет id для базы
    @SerializedName("id") // сохраняем значения для переменных. Нужно для обфускации
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("year")
    private int year;

    @Embedded // нужно чтобы в базу можно было записать объекты
    @SerializedName("poster")
    private Poster poster;

    @Embedded // нужно чтобы в базу можно было записать объекты
    @SerializedName("rating")
    private Rating rating;

    public Movie(int id, String name, String description, int year, Poster poster, Rating rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.year = year;
        this.poster = poster;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", year=" + year +
                ", poster=" + poster +
                ", rating=" + rating +
                '}';
    }

    public int getYear() {
        return year;
    }

    public Poster getPoster() {
        return poster;
    }

    public Rating getRating() {
        return rating;
    }
}
