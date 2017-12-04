package com.gautam.upcomingmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Gautam on 03/12/2017.
 */

public class UpcomingMoviesResponse {
    @SerializedName("results")
    private List<UpcomingMovies> results;
    
    public List<UpcomingMovies> getResults() {
        return results;
    }

    public void setResults(List<UpcomingMovies> results) {
        this.results = results;
    }


}
