package com.example.movies;

import com.google.gson.annotations.SerializedName;

public class Rating {

    @SerializedName("kp")
    private String kp;

    @Override
    public String toString() {
        return "Rating{" +
                "rating=" + kp +
                '}';
    }

    public Rating(String rating) {
        this.kp = rating;
    }

    public String getKp() {
        return kp;
    }
}
