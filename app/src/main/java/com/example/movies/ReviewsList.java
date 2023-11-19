package com.example.movies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewsList {

    @SerializedName("docs")
    private List<Review> reviews;

    public List<Review> getReviews() {
        return reviews;
    }

    public ReviewsList(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "ReviewsList{" +
                "reviews=" + reviews +
                '}';
    }




}
