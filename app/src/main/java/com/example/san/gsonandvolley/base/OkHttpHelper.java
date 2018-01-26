package com.example.san.gsonandvolley.base;

import android.os.AsyncTask;
import android.renderscript.RenderScript;
import android.util.Log;

import com.android.volley.toolbox.HttpHeaderParser;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.Parser;
import com.example.san.gsonandvolley.model.movie.Example;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by san on 19/01/2018.
 */

public class OkHttpHelper<T> extends AsyncTask<String,Void,T>{
    private IOkHttpResponse mResponseListener;
    private Class<T> clazz;
    private Gson gson;
    public OkHttpHelper(Class<T> clazz,IOkHttpResponse mResponseListener) {
        this.mResponseListener = mResponseListener;
        this.clazz = clazz;
        gson = new Gson();
    }

    @Override
    protected T doInBackground(String... strings) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        final Request request = new Request.Builder()
                .url(strings[0])
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            return gson.fromJson(response.body().string(), clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        AndroidNetworking.get(strings[0])
//                .setTag(this)
//                .setPriority(Priority.LOW)
//                .build()
//                .getAsParsed(new TypeToken<T>() {}, new ParsedRequestListener<T>() {
//                    @Override
//                    public void onResponse(T response) {
//
//                    }
//
//                    @Override
//                    public void onError(ANError anError) {
//                        Log.i("TAG", "onError: "+anError.toString());
//                    }
//                });
        return null;
    }

    @Override
    protected void onPostExecute(T t) {
        super.onPostExecute(t);
        if(t != null)mResponseListener.onSuccess(t);
        else mResponseListener.onError("Request Error");
    }

    ///*********Asynchronous********///
    //    public void get(String url, final Class<T> clazz, final IOkHttpResponse responseListener) {
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(15, TimeUnit.SECONDS)
//                .readTimeout(15, TimeUnit.SECONDS)
//                .writeTimeout(15, TimeUnit.SECONDS)
//                .retryOnConnectionFailure(true)
//                .build();
//
//        final Request request = new Request.Builder()
//                .url(url)
//                .build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                responseListener.onError(e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                responseListener.onSuccess(new Gson().fromJson(response.body().string(), clazz));
//            }
//        });
//    }


        public void get(String url, final Class<T> clazz, final IOkHttpResponse responseListener) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        final Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                responseListener.onError(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responseListener.onSuccess(new Gson().fromJson(response.body().string(), clazz));
            }
        });
    }

    public interface IOkHttpResponse<T> {
        void onSuccess(T response);
        void onError(String error);
    }
}
