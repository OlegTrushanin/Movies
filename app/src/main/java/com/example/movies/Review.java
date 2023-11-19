package com.example.movies;

import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("author")
    String author;

    @SerializedName("review")
    String review;

    public String getAuthor() {
        return author;
    }

    public String getReview() {
        return review;
    }

    public Review(String author, String review) {
        this.author = author;
        this.review = review;
    }
}
