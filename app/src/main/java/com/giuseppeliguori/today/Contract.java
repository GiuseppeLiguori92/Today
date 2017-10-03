package com.giuseppeliguori.today;

import com.giuseppeliguori.todayapi.apiclass.Birth;
import com.giuseppeliguori.todayapi.apiclass.Death;
import com.giuseppeliguori.todayapi.apiclass.Event;

import java.util.List;

/**
 * Created by giuseppeliguori on 10/3/17.
 */

public interface Contract {
    interface View {
        void onConnectionEstablished();
        void onConnectionLost();

        void onRequestDataStarted();
        void onRequestDataSuccess();
        void onRequestDataFailure();
    }

    interface Presenter {
        void onResume();
        void onStop();

        void requestData();
        List<Event> requestEvents();
        List<Birth> requestBirths();
        List<Death> requestDeaths();
    }
}
