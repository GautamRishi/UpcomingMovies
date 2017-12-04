package com.gautam.upcomingmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Gautam on 04/12/2017.
 */

public class PostersResponse {
    public List<Posters> getPosters() {
        return backdrops;
    }

    public void setPosters(List<Posters> posters) {
        this.backdrops = posters;
    }

    @SerializedName("backdrops")
    private List<Posters> backdrops;
}
