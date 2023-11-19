package com.example.movies;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rating implements Serializable {

    public void setKp(double kp) {
        this.kp = kp;
    }

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

    public Rating() {

    }

    public double getKp() {
        return kp;
    }
}
