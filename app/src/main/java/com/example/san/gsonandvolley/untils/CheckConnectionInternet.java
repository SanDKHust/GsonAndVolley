package com.example.san.gsonandvolley.untils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by san on 18/01/2018.
 */

public class CheckConnectionInternet {
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
