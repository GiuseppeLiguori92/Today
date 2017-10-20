package com.giuseppeliguori.today;

import android.app.Activity;
import android.content.Context;

import com.giuseppeliguori.todayapi.api.TodayAPI;
import com.giuseppeliguori.todayapi.apiclass.Birth;
import com.giuseppeliguori.todayapi.apiclass.Data;
import com.giuseppeliguori.todayapi.apiclass.Death;
import com.giuseppeliguori.todayapi.apiclass.Event;
import com.giuseppeliguori.todayapi.interfaces.OnNetworkChangedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    boolean isConnected = true;

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

    private List<Event> requestEvents() {
        return responseReceived ? todayApi.getEvents() : new ArrayList<Event>();
    }

    private List<Birth> requestBirths() {
        return responseReceived ? todayApi.getBirths() : new ArrayList<Birth>();
    }

    private List<Death> requestDeaths() {
        return responseReceived ? todayApi.getDeath() : new ArrayList<Death>();
    }

    public Data getData() { return responseReceived ? todayApi.getData() : null; }

    @Override
    public void requestDate(Date date) {
        SimpleDateFormat monthDayFormat = new SimpleDateFormat("MMMM d");
        SimpleDateFormat dayYearFormat = new SimpleDateFormat("EEEE, yyyy");
        view.setDateView(monthDayFormat.format(date), dayYearFormat.format(date));
    }

    private boolean isCalendarVisible = false;
    @Override
    public void onClickDateMenuItem() {
        if (!isCalendarVisible) {
            view.showCalendarWithAnimation();
        } else {
            view.hideCalendarWithAnimation();
        }

        isCalendarVisible = !isCalendarVisible;
    }

    @Override
    public void reverseOrderList() {

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
        if (this.isConnected != isConnected || !isConnected) {
            this.isConnected = isConnected;
            if (isConnected) {
                view.onConnectionEstablished();
            } else {
                view.onConnectionLost();
            }
        }
    }
}
