package com.anirudh24seven.popularmovies1.Models;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by anirudh on 20/02/16.
 */
public class Movie implements Serializable {
    public String title;
    public String imageUrl;
    public String overview;
    public String voteAverage;
    public String releaseDate;
}
