package com.giuseppeliguori.today;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.giuseppeliguori.todayapi.apiclass.Birth;
import com.giuseppeliguori.todayapi.apiclass.Death;
import com.giuseppeliguori.todayapi.apiclass.Event;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Contract.View {
    private static final String TAG = "MainActivity";

    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new Presenter(this);

        presenter.requestData();
    }

    @Override
    public void onRequestDataStarted() {

    }

    @Override
    public void onRequestDataSuccess() {
        List<Event> events = presenter.requestEvents();
        List<Birth> births = presenter.requestBirths();
        List<Death> deaths = presenter.requestDeaths();

        for (int i = 0; i < events.size(); i++) {
            Log.d(TAG, "onRequestDataSuccess: " + events.get(i).getText());
        }

        for (int i = 0; i < births.size(); i++) {
            Log.d(TAG, "onRequestDataSuccess: " + births.get(i).getText().substring(0, 25));
        }
        for (int i = 0; i < deaths.size(); i++) {
            Log.d(TAG, "onRequestDataSuccess: " + deaths.get(i).getText().substring(0, 25));
        }
    }

    @Override
    public void onRequestDataFailure() {

    }
}
