package com.example.movies;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rating implements Serializable {

    @SerializedName("kp")
    private double kp;

    @Override
    public String toString() {
        return "Rating{" +
                "rating=" + kp +
                '}';
    }

    public Rating(double rating) {
        this.kp = rating;
    }

    public double getKp() {
        return kp;
    }
}
