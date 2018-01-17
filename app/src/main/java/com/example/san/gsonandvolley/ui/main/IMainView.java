package com.example.san.gsonandvolley.ui.main;



import com.example.san.gsonandvolley.model.movie.Search;

import java.util.List;


/**
 * Created by San on 1/13/2018.
 */

public interface IMainView {
    void onRequestSearhSuccess(List<Search> movies, String totalResults);
    void onRequestSearhError(String msg);
}
