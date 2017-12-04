package com.gautam.upcomingmovies.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Gautam on 03/12/2017.
 */

public interface ApiInterface {
    @GET("movie/upcoming")
    Call<UpcomingMoviesResponse> getUpcomingMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<MovieDetailsResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("movie/{id}/images")
    Call<PostersResponse> getMoviePosters(@Path("id") int id, @Query("api_key") String apiKey);
}
