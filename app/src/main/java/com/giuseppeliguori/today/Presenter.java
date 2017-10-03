package com.giuseppeliguori.today;

import com.giuseppeliguori.todayapi.apiclass.Birth;
import com.giuseppeliguori.todayapi.apiclass.Death;
import com.giuseppeliguori.todayapi.apiclass.Event;
import com.giuseppeliguori.todayapi.api.TodayAPI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by giuseppeliguori on 10/3/17.
 */

public class Presenter implements Contract.Presenter {
    private static final String TAG = "Presenter";

    private Contract.View view;

    private TodayAPI todayApi;
    private boolean responseReceived = false;

    public Presenter(Contract.View view) {
        this.view = view;
        todayApi = TodayAPI.getInstance();
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
            public void onFailure() {
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
}
