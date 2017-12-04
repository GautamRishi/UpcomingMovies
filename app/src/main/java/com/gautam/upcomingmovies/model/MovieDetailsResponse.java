package com.gautam.upcomingmovies.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Gautam on 04/12/2017.
 */

public class MovieDetailsResponse {
    @SerializedName("overview")
    private String overview;

    @SerializedName("popularity")
    private String popularity;

    @SerializedName("title")
    private String title;


    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
