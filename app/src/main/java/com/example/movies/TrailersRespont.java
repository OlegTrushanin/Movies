package com.example.movies;

import com.google.gson.annotations.SerializedName;

public class TrailersRespont {
    @Override
    public String toString() {
        return "TrailersRespont{" +
                "trailersList=" + trailersList +
                '}';
    }

    @SerializedName("videos")
    private TrailersList trailersList;

    public TrailersList getMovieTrailer() {
        return trailersList;
    }

    public TrailersRespont(TrailersList trailersList) {
        this.trailersList = trailersList;
    }
}
