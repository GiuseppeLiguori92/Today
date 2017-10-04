package com.giuseppeliguori.todayapi.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.giuseppeliguori.todayapi.interfaces.OnNetworkChangedListener;
import com.giuseppeliguori.todayapi.managers.NetworkManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by giuseppeliguori on 10/3/17.
 */

public class NetworkBroadcastReceiver extends BroadcastReceiver {

    private List<OnNetworkChangedListener> networkChangedListeners = new ArrayList<>();

    public NetworkBroadcastReceiver(OnNetworkChangedListener onNetworkChangedListener) {
        addListener(onNetworkChangedListener);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            informListener(context);
        }
    }

    public void informListener(Context context) {
        for (OnNetworkChangedListener networkChangedListener : networkChangedListeners) {
            networkChangedListener.onNetworkChanged(NetworkManager.getInstance().isConnected(context));
        }
    }

    public void addListener(OnNetworkChangedListener onNetworkChangedListener) {
        if (!networkChangedListeners.contains(onNetworkChangedListener)) {
            networkChangedListeners.add(onNetworkChangedListener);
        }
    }

    public void removeListener(OnNetworkChangedListener onNetworkChangedListener) {
        if (networkChangedListeners.contains(onNetworkChangedListener)) {
            networkChangedListeners.remove(onNetworkChangedListener);
        }
    }
}
