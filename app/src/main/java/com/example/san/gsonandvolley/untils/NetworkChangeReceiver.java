package com.example.san.gsonandvolley.untils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by san on 21/01/2018.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {
    private static NetworkChangeListener mNetworkChangeListener;

    public NetworkChangeReceiver(NetworkChangeListener networkChangeListener) {
        this.mNetworkChangeListener = networkChangeListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mNetworkChangeListener.onNetworkChange(CheckConnectionInternet.isNetworkConnected(context));
    }


    public interface NetworkChangeListener {
        void onNetworkChange(boolean isConnected);
    }
}
