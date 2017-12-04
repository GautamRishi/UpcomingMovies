package com.gautam.upcomingmovies;

import android.app.Application;

/**
 * Created by Gautam on 04/12/2017.
 */

public class MyApplication extends Application {

    public static String key = "b7cd3340a794e5a2f35e3abb820b497f";
    public static String upcoming_movie_url = "http://api.themoviedb.org/3/";
    public static String poster_url = "https://image.tmdb.org/t/p/w150";
    public static String full_poster_url = "https://image.tmdb.org/t/p/w500";
    public static String movie_id="movie_id";
    public static String movie_title="movie_title";
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
