package com.example.san.gsonandvolley.base;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class HttpConnectionHelper<T> extends AsyncTask<String,Void,T> {
    private IOkHttpResponse mResponseListener;
    private Class<T> clazz;
    private Gson gson;
    public HttpConnectionHelper(Class<T> clazz,IOkHttpResponse mResponseListener) {
        this.mResponseListener = mResponseListener;
        this.clazz = clazz;
        gson = new Gson();
    }

    @Override
    protected T doInBackground(String... strings) {
        HttpURLConnection httpURLConnection = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL(strings[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null){
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
            return gson.fromJson(stringBuilder.toString(), clazz);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpURLConnection.disconnect();
        }

        return null;
    }

    @Override
    protected void onPostExecute(T t) {
        super.onPostExecute(t);
        if(t != null)mResponseListener.onSuccess(t);
        else mResponseListener.onError("Request Error");
    }



    public interface IOkHttpResponse<T> {
        void onSuccess(T response);
        void onError(String error);
    }
}
