package com.gautam.upcomingmovies.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Gautam on 04/12/2017.
 */

public class Posters {

    @SerializedName("file_path")
    private String filePath;


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
