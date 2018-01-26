package com.example.san.gsonandvolley.base;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by San on 1/13/2018.
 */

public class VolleyRequestQueue {
    private static VolleyRequestQueue sInstance;
    private RequestQueue mRequestQueue;
    private static Context sContext;

    private VolleyRequestQueue(Context context) {
        sContext = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized VolleyRequestQueue getInstance(Context context){
        if(sInstance == null){
            sInstance = new VolleyRequestQueue(context);
        }
        return sInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null){
            mRequestQueue =  Volley.newRequestQueue(sContext);
        }
        return mRequestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
