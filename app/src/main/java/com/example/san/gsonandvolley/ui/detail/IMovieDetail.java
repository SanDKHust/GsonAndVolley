package com.example.san.gsonandvolley.ui.detail;


import com.example.san.gsonandvolley.model.movie.Movie;

/**
 * Created by San on 1/14/2018.
 */

public interface IMovieDetail {
    void onRequestSearhSuccess(Movie movie);
    void onRequestSearhError(String msg);
}
