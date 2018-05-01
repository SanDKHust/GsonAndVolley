package com.example.san.gsonandvolley.base;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by San on 1/12/2018.
 */

public class VolleyHelper<T> {
    public void get(Context context, String url, HashMap<String,String> header, Class<T> classType, final IVolleyResponse<T> responseListener){
        GsonRequest<T> request = new GsonRequest<T>(Request.Method.GET,url, classType, header, new Response.Listener<T>() {
            @Override
            public void onResponse(T response) {
                responseListener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                responseListener.onError(error);
            }
        });
        VolleyRequestQueue.getInstance(context).addToRequestQueue(request);
    }
    public void post(String url, JSONObject params){

    }
    public void put(String url, JSONObject params){

    }
    public void delete(String url, JSONObject params){

    }

    public interface IVolleyResponse<T>{
        void onSuccess(T response);
        void onError(VolleyError error);
    }
}
