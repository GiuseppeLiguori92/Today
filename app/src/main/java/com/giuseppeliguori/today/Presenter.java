package com.giuseppeliguori.today;

import android.app.Activity;
import android.content.Context;

import com.giuseppeliguori.todayapi.interfaces.OnNetworkChangedListener;
import com.giuseppeliguori.todayapi.apiclass.Birth;
import com.giuseppeliguori.todayapi.apiclass.Death;
import com.giuseppeliguori.todayapi.apiclass.Event;
import com.giuseppeliguori.todayapi.api.TodayAPI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by giuseppeliguori on 10/3/17.
 */

public class Presenter implements Contract.Presenter, OnNetworkChangedListener {
    private static final String TAG = "Presenter";

    private Contract.View view;
    private Context context;

    private TodayAPI todayApi;
    private boolean responseReceived = false;

    public Presenter(final Contract.View view) {
        this.view = view;
        context = ((Activity)view).getApplicationContext();

        todayApi = new TodayAPI(context);
    }

    @Override
    public void requestData() {
        view.onRequestDataStarted();
        todayApi.requestData(new TodayAPI.TodayCallback() {
            @Override
            public void onResponse() {
                responseReceived = true;
                view.onRequestDataSuccess();
            }

            @Override
            public void onFailure(TodayAPI.Failure reason) {
                switch (reason) {
                    case NO_NETWORK:
                        view.onConnectionLost();
                        break;
                    case UNKNOWN:
                        break;
                }
                view.onRequestDataFailure();
            }
        });
    }

    @Override
    public List<Event> requestEvents() {
        return responseReceived ? todayApi.getEvents() : new ArrayList<Event>();
    }

    @Override
    public List<Birth> requestBirths() {
        return responseReceived ? todayApi.getBirths() : new ArrayList<Birth>();
    }

    @Override
    public List<Death> requestDeaths() {
        return responseReceived ? todayApi.getDeath() : new ArrayList<Death>();
    }

    @Override
    public void onResume() {
        todayApi.registerNetworkBroadcast(this);
    }

    @Override
    public void onStop() {
        todayApi.unregisterNetworkBroadcast(this);
    }

    @Override
    public void onNetworkChanged(boolean isConnected) {
        if (isConnected) {
            view.onConnectionEstablished();
        } else {
            view.onConnectionLost();
        }
    }
}
