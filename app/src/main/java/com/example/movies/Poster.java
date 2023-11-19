package com.example.movies;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Poster implements Serializable {

    public void setUrl(String url) {
        this.url = url;
    }

    @SerializedName("url")
    private String url;

    @Override
    public String toString() {
        return "Poster{" +
                "url='" + url + '\'' +
                '}';
    }

    public Poster(String url) {
        this.url = url;
    }

    public Poster() {

    }

    public String getUrl() {
        return url;
    }
}
