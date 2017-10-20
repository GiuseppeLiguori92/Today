package com.giuseppeliguori.today;

import com.giuseppeliguori.todayapi.apiclass.Birth;
import com.giuseppeliguori.todayapi.apiclass.Death;
import com.giuseppeliguori.todayapi.apiclass.Event;

import java.util.Date;
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

        void setDateView(String dateMonthDay, String dateDayYear);

        void hideCalendarWithAnimation();

        void showCalendarWithAnimation();
    }

    interface Presenter {
        void onResume();
        void onStop();

        void requestData();

        void requestDate(Date date);

        void onClickDateMenuItem();

        void reverseOrderList();
    }
}
