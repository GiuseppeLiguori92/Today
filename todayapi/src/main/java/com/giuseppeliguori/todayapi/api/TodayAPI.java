package com.giuseppeliguori.todayapi.api;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.giuseppeliguori.todayapi.apiclass.Birth;
import com.giuseppeliguori.todayapi.apiclass.Data;
import com.giuseppeliguori.todayapi.apiclass.Death;
import com.giuseppeliguori.todayapi.apiclass.Event;
import com.giuseppeliguori.todayapi.apiclass.Header;
import com.giuseppeliguori.todayapi.interfaces.OnNetworkChangedListener;
import com.giuseppeliguori.todayapi.managers.NetworkManager;
import com.giuseppeliguori.todayapi.receivers.NetworkBroadcastReceiver;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by giuseppeliguori on 9/26/17.
 */

public class TodayAPI {

    private static final String ROOT_URL = "http://history.muffinlabs.com";

    private Context context;

    private Header header;

    private MuffinlabsAPI muffinlabsAPI;

    private NetworkBroadcastReceiver networkBroadcastReceiver;

    public TodayAPI(Context context) {
        this.context = context;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        muffinlabsAPI = retrofit.create(MuffinlabsAPI.class);
    }

    public void requestData(final TodayCallback todayCallback, Date date) {
        handleCall(todayCallback, date);
    }

    public void requestData(final TodayCallback todayCallback) {
        handleCall(todayCallback, null);
    }

    @VisibleForTesting
    void handleCall(final TodayCallback todayCallback, Date date) {
        if (isConnected()) {
            getCall(muffinlabsAPI, date).enqueue(getCallback(todayCallback));
        } else {
            todayCallback.onFailure(Failure.NO_NETWORK);
        }
    }

    @VisibleForTesting
    @NonNull
    Callback<Header> getCallback(final TodayCallback todayCallback) {
        return new Callback<Header>() {
            @Override
            public void onResponse(Call<Header> call, Response<Header> response) {
                if (response.isSuccessful()) {
                    header = response.body();
                    todayCallback.onResponse();
                } else {
                    todayCallback.onFailure(Failure.UNKNOWN);
                }
            }

            @Override
            public void onFailure(Call<Header> call, Throwable t) {
                t.printStackTrace();
                todayCallback.onFailure(Failure.UNKNOWN);
            }
        };
    }

    @VisibleForTesting
    boolean isConnected() {return NetworkManager.getInstance().isConnected(context);}

    public enum Failure {
        NO_NETWORK,
        UNKNOWN
    }

    @VisibleForTesting
    Call<Header> getCall(MuffinlabsAPI muffinlabsAPI, @Nullable Date date) {
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return muffinlabsAPI.getData(calendar.get(calendar.MONTH), calendar.get(calendar.DAY_OF_MONTH));
        } else {
            return muffinlabsAPI.getData();
        }
    }

    public List<Event> getEvents() {
        return header == null ? null : header.getData().getEvents();
    }

    public List<Birth> getBirths() {
        return header == null ? null : header.getData().getBirths();
    }

    public List<Death> getDeath() {
        return header == null ? null : header.getData().getDeaths();
    }

    public Data getData() {
        return header == null ? null : header.getData();
    }

    public void registerNetworkBroadcast(OnNetworkChangedListener onNetworkChangedListener) {
        if (networkBroadcastReceiver == null) {
            networkBroadcastReceiver = new NetworkBroadcastReceiver(onNetworkChangedListener);
        } else {
            networkBroadcastReceiver.addListener(onNetworkChangedListener);
        }

        context.registerReceiver(
                networkBroadcastReceiver,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    public void unregisterNetworkBroadcast(OnNetworkChangedListener onNetworkChangedListener) {
        networkBroadcastReceiver.removeListener(onNetworkChangedListener);
        context.unregisterReceiver(networkBroadcastReceiver);
    }

    public interface TodayCallback {
        void onResponse();
        void onFailure(Failure reason);
    }
}
