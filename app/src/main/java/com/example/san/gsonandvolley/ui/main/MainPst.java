package com.example.san.gsonandvolley.ui.main;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.san.gsonandvolley.base.OkHttpHelper;
import com.example.san.gsonandvolley.base.VolleyHelper;
import com.example.san.gsonandvolley.model.movie.Example;
import com.example.san.gsonandvolley.model.movie.Movie;
import com.example.san.gsonandvolley.model.movie.ResponseError;
import com.example.san.gsonandvolley.ui.detail.IMovieDetail;
import com.example.san.gsonandvolley.untils.VolleyUntils;

import java.util.HashMap;

import static com.example.san.gsonandvolley.untils.Constants.KEY.I_PARAMETER;
import static com.example.san.gsonandvolley.untils.Constants.KEY.S_PARAMETER;
import static com.example.san.gsonandvolley.untils.Constants.URL.API_KEY;
import static com.example.san.gsonandvolley.untils.Constants.URL.HOST;


/**
 * Created by San on 1/13/2018.
 */

public class MainPst implements IMainPst {
    private IMainView mIMainView;
    private IMovieDetail mIMovieDetail;

    public MainPst(IMainView mIMainView) {
        this.mIMainView = mIMainView;
    }

    public MainPst(IMovieDetail mIMovieDetail) {
        this.mIMovieDetail = mIMovieDetail;
    }

    @Override
    public void getDataMovie(final Context context, String parameter, String keyword, final String page) {
        HashMap<String, String> params = new HashMap<>();
        params.put("apikey", API_KEY);
        params.put(parameter, keyword);
        params.put("page", page);
        final String url = VolleyUntils.makeUrl(HOST, params);
        if (parameter.equals(S_PARAMETER)) {
            new OkHttpHelper<Example>(Example.class, new OkHttpHelper.IOkHttpResponse<Example>() {
                @Override
                public void onSuccess(Example response) {
                    if(response.getResponse().equals("False")){
                        new OkHttpHelper<ResponseError>(ResponseError.class, new OkHttpHelper.IOkHttpResponse<ResponseError>() {
                            @Override
                            public void onSuccess(ResponseError response) {
                                mIMainView.onRequestSearhError(response.getError());
                            }

                            @Override
                            public void onError(String error) {
                                mIMainView.onRequestSearhError(error);
                            }
                        }).execute(new String[]{url});
                    }else mIMainView.onRequestSearhSuccess(response.getSearch(),response.getTotalResults());
                }

                @Override
                public void onError(String error) {
                    mIMainView.onRequestSearhError(error);
                }
            }).execute(new String[]{url});
///*********Asynchronous********///
//            new OkHttpHelper<Example>().get(url, Example.class, new OkHttpHelper.IOkHttpResponse<Example>() {
//                @Override
//                public void onSuccess(Example response) {
//                    if(response == null) mIMainView.onRequestSearhError("Request Error");
//                    else if (response.getResponse().equals("False")) {
//                        new OkHttpHelper<ResponseError>().get(url, ResponseError.class, new OkHttpHelper.IOkHttpResponse<ResponseError>() {
//                            @Override
//                            public void onSuccess(ResponseError response) {
//                                mIMainView.onRequestSearhError(response.getError());
//                            }
//
//                            @Override
//                            public void onError(String error) {
//                                mIMainView.onRequestSearhError(error);
//                            }
//                        });
//                    } else {
//                        mIMainView.onRequestSearhSuccess(response.getSearch(), response.getTotalResults());
//                    }
//                }
//
//                @Override
//                public void onError(String error) {
//                    mIMainView.onRequestSearhError(error);
//                }
//            });


///*********Volley********///
//            new VolleyHelper<Example>().get(context, url, null, Example.class, new VolleyHelper.IVolleyResponse<Example>() {
//                @Override
//                public void onSuccess(Example response) {
//                    if (response != null){
//                         if(response.getResponse().equals("False")){
//                            new VolleyHelper<ResponseError>().get(context, url, null, ResponseError.class, new VolleyHelper.IVolleyResponse<ResponseError>() {
//
//                                @Override
//                                public void onSuccess(ResponseError response) {
//                                    mIMainView.onRequestSearhError(response.getError());
//                                }
//
//                                @Override
//                                public void onError(VolleyError error) {
//                                    mIMainView.onRequestSearhError(error.getMessage());
//                                }
//                            });
//                        } else{
//                            mIMainView.onRequestSearhSuccess(response.getSearch(),response.getTotalResults());
//                        }
//                    }else {
//                        mIMainView.onRequestSearhError("Request Error");
//                    }
//                }
//
//                @Override
//                public void onError(VolleyError error) {
//                    Log.i("HAHA", "onError: ");
//                    String error_msg = error.getMessage();
//                    mIMainView.onRequestSearhError(error_msg);
//                }
//            });
        } else if (parameter.equals(I_PARAMETER)) {

            new OkHttpHelper<Movie>(Movie.class, new OkHttpHelper.IOkHttpResponse<Movie>() {
                @Override
                public void onSuccess(Movie response) {
                    mIMovieDetail.onRequestSearhSuccess(response);
                }

                @Override
                public void onError(String error) {
                    mIMovieDetail.onRequestSearhError(error);
                }
            }).execute(new String[]{url});

///*********Asynchronous********///
//            new OkHttpHelper<Movie>().get(url, Movie.class, new OkHttpHelper.IOkHttpResponse<Movie>() {
//                @Override
//                public void onSuccess(Movie response) {
//                    mIMovieDetail.onRequestSearhSuccess(response);
//                }
//
//                @Override
//                public void onError(String error) {
//                    mIMovieDetail.onRequestSearhError(error);
//                }
//            });

///*********Volley********///
//            new VolleyHelper<Movie>().get(context, url, null, Movie.class, new VolleyHelper.IVolleyResponse<Movie>() {
//                @Override
//                public void onSuccess(Movie response) {
//                    mIMovieDetail.onRequestSearhSuccess(response);
//                }
//
//                @Override
//                public void onError(VolleyError error) {
//                    String error_msg = error.getMessage();
//                    mIMovieDetail.onRequestSearhError(error_msg);
//                }
//            });
        }
    }
}
